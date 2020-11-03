package session;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface ReservationSessionRemote {

    void setName(String name);
    String getName();
    Set<String> getAllRentalCompanies();
    Set<CarType> getAvailableCarTypes(Date start, Date end);
    void createQuote(ReservationConstraints constr)throws ReservationException;
    List<Quote> getCurrentQuotes();
    List<Reservation> confirmQuotes()throws ReservationException;
    
}


