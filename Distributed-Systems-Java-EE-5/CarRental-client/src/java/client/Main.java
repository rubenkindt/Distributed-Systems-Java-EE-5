package client;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import rental.Reservation;
import rental.ReservationConstraints;
import session.ReservationSessionRemote;
import session.ManagerSessionRemote;

@Remote
public class Main extends AbstractTestAgency<ReservationSessionRemote, ManagerSessionRemote> {
    //session 5 @PersistenceContext
    //session 5 private EntityManager em;
    
    
    @EJB
    static ReservationSessionRemote resSession;
    //ReservationSessionRemote session = (ReservationSessionRemote)InitialContext.lookup("java:global/CarRental/ReservationSessionRemote");
    
    //@EJB
    //static ManagerSessionRemote msession;
    
     public Main(String scriptFile) {
        super(scriptFile);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new Main("simpleTrips").run();
        //System.out.println("found rental companies: "+resSession.getAllRentalCompanies());
    }

   

    @Override
    protected ReservationSessionRemote getNewReservationSession(String name) throws Exception {
        InitialContext context = new InitialContext();
        //throws NamingExeption
        
        ReservationSessionRemote session2 = (ReservationSessionRemote)context.lookup(ReservationSessionRemote.class.getName());    
        session2.setName(name);
        //String str =session2.getName();
        return session2; 
    }

    @Override
    protected ManagerSessionRemote getNewManagerSession(String name) throws Exception {
        InitialContext context = new InitialContext();
        //throws NamingExeption
        
        ManagerSessionRemote session2 = (ManagerSessionRemote)context.lookup(ManagerSessionRemote.class.getName());    
        session2.setName(name);
        
        return session2; 
    }

    @Override
    protected void getAvailableCarTypes(ReservationSessionRemote session, Date start, Date end) throws Exception {
        
        session.getAvailableCarTypes(start, end);
    }

    @Override
    protected void createQuote(ReservationSessionRemote session, String name, Date start, Date end, String carType, String region) throws Exception {
        if (session.getName().equals(name)){
            
            session.createQuote(  name,  start,  end,  carType,  region);
        }
        else{
            throw new Exception("Name does not match: "+session.getName()+" Vs: "+name);
        }
    }

    @Override
    protected List<Reservation> confirmQuotes(ReservationSessionRemote session, String name) throws Exception {
        if (session.getName().equals(name)){
            return session.confirmQuotes();
        }
        else{
            throw new Exception("Name does not match: "+session.getName()+" Vs: "+name);
        }
    }

    @Override
    protected int getNumberOfReservationsBy(ManagerSessionRemote ms, String clientName) throws Exception {
        return ms.getNrOfReservationsByClient(clientName);
    }

    @Override
    protected int getNumberOfReservationsForCarType(ManagerSessionRemote ms, String carRentalName, String carType) throws Exception {
        
        return ms.getNrOfReservationsByCarTypeInCompany(carRentalName, carType);
    }

}
