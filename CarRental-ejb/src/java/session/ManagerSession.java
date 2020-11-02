/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import rental.CarType;

/**
 *
 * @author Ruben Kindt R0656495
 */
@DeclareRoles("Manager")
@Stateless
public class ManagerSession implements ManagerSessionRemote {

  
    @RolesAllowed("Manager")
    @Override
    public int getNrOfReservationsByCarTypeInCompany(String CompanyName, CarType cartype) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @RolesAllowed("Manager")
    @Override
    public int getNrOfReservationsByClient(String clientName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
