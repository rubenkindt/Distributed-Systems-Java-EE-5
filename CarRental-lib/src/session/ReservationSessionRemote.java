package session;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;
import rental.Quote;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface ReservationSessionRemote {

    Set<String> getAllRentalCompanies();
    Set<CarType> getAvailableCarTypes(Date start, Date end);
    Quote createQuote(ReservationConstraints constr)throws ReservationException;
    List<Quote> getCurrentQuotes();
    void confirmQuotes()throws ReservationException;
    
}


