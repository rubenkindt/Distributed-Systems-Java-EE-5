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
    
    void setName(String Name);
    int getNrOfReservationsByCarTypeInCompany(String CompanyName, String cartype)throws Exception;
    int getNrOfReservationsByClient(String clientName)throws Exception;
    
}
