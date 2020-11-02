/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;

/**
 *
 * @author Ruben Kindt R0656495
 */
@Remote
public interface ManagerSessionRemote {
    int getNrOfReservationsByCarTypeInCompany(String CompanyName, CarType cartype);
    int getNrOfReservationsByClient(String clientName);
    
}
