package session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import rental.CarRentalCompany;
import rental.CarType;
import rental.Quote;
import rental.RentalStore;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Stateful
public class ReservationSession implements ReservationSessionRemote {

    private String name;
    private List<Quote> quoteList=new ArrayList<Quote>();
    private List<Reservation> resList=new ArrayList<Reservation>();
    

    public ReservationSession() {
         this.name="";
    }
    
    
    public ReservationSession(String name){
        this.name=name;
    }
    
    @Override
    public Set<String> getAllRentalCompanies() {
        return new HashSet<String>(RentalStore.getRentals().keySet());
    }

    @Override
    public Set<CarType> getAvailableCarTypes(Date start, Date end) {
        Set<CarType> ctList=new HashSet<CarType>();
        
        for (Iterator<Map.Entry<String, CarRentalCompany>> entries = RentalStore.getRentals().entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<String, CarRentalCompany> compMap= entries.next();
            ctList.addAll(compMap.getValue().getAvailableCarTypes(start, end));
        }
        return ctList;
    }
    @Override
    public void createQuote(ReservationConstraints constr)throws ReservationException{
        Set<CarType> ctList=new HashSet<CarType>();
        Quote quote=null;
        for (Iterator<Map.Entry<String, CarRentalCompany>> entries = RentalStore.getRentals().entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<String, CarRentalCompany> compMap= entries.next();
            try {
                quote=compMap.getValue().createQuote(constr, this.name);
            } catch (ReservationException ex) {
                
            }
        }
        if (quote==null){
            throw new ReservationException("No company can provide quote");
        }
        quoteList.add(quote);
    }

    @Override
    public List<Quote> getCurrentQuotes() {
        return quoteList;
    }

    @Override
    public List<Reservation> confirmQuotes() throws ReservationException {
        List<Reservation> reservationList=new ArrayList<Reservation>();
		
        int j=quoteList.size();
        boolean succes=true;
        for (int quote=0;quote<quoteList.size();quote++) {

            String compName=quoteList.get(quote).getRentalCompany();
            for (Iterator<Map.Entry<String, CarRentalCompany>> entries = RentalStore.getRentals().entrySet().iterator(); entries.hasNext(); ) {
                Map.Entry<String, CarRentalCompany> compMap= entries.next();
                CarRentalCompany compa= compMap.getValue();
                if (compa.getName().equals(compName)){
                    try {
                        reservationList.add(compa.confirmQuote(quoteList.get(quote)));
                    }
                    catch (ReservationException e){
                            succes=false;
                    }
                    break;
                }
            }
        }
        if (succes==false) {
            CarRentalCompany compa=null;
            for (int i=0;i<reservationList.size();i++) {
                String compaName=reservationList.get(i).getRentalCompany();
                for (Iterator<Map.Entry<String, CarRentalCompany>> entries = RentalStore.getRentals().entrySet().iterator(); entries.hasNext(); ) {
                    Map.Entry<String, CarRentalCompany> compMap= entries.next();
                    compa=compMap.getValue();
                    if (compa.getName().equals(compaName)) {
                        compa.cancelReservation(reservationList.get(i));
                        break;
                    }
                }
            }
            reservationList.clear();
            throw new ReservationException("Can't complete all reservations");
        }
        
        
        resList.addAll(reservationList);
        return reservationList;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}
