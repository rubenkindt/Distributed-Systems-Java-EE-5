package session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return RentalStore.getAvailableCarTypes(start,end);
    }

    @Override
    public Quote createQuote(ReservationConstraints constr) throws ReservationException{
        Quote quote =RentalStore.createQuote(constr);
        quoteList.add(quote);
        return quote;
    }

    @Override
    public List<Quote> getCurrentQuotes() {
        return quoteList;
        
    }

    @Override
    public void confirmQuotes() throws ReservationException {
        resList.addAll(RentalStore.confirmQuotes(quoteList));
    }
}
