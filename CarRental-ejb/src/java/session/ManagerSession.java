/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import rental.CarRentalCompany;
import rental.CarType;
import rental.RentalStore;
import rental.Reservation;

/**
 *
 * @author Ruben Kindt R0656495
 */
@DeclareRoles("Manager")
@Stateless
public class ManagerSession implements ManagerSessionRemote {
    private String name; 
  
    //@RolesAllowed("Manager")
    @Override
    public int getNrOfReservationsByCarTypeInCompany(String companyName, String carType) throws Exception{
        int i=0;
        System.out.println("company: "+companyName+ " carType: "+carType);
        for (Iterator<Map.Entry<String, CarRentalCompany>> entries = RentalStore.getRentals().entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<String, CarRentalCompany> compMap= entries.next();
            CarRentalCompany compa= compMap.getValue();
            
            if (compa.getName().equals(companyName)){
                
                i=compa.getNumberOfReservationsForCarType(carType);
                
            }
        }
        if (i==0){
            throw new Exception("RentalStore (getNrOfReservationsByCarTypeInCompany): Wrong cartype");
        }else{
            return i;
        }
    }
    
    //@RolesAllowed("Manager")
    @Override
    public int getNrOfReservationsByClient(String clientName) {
        Set<Reservation> res=new HashSet<Reservation>();
        for (Iterator<Map.Entry<String, CarRentalCompany>> entries = RentalStore.getRentals().entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<String, CarRentalCompany> compMap= entries.next();
            CarRentalCompany compa= compMap.getValue();
            
            res.addAll(compa.getReservationsBy(clientName));
        }
        return res.size();
    }

    @Override
    public void setName(String Name) {
        this.name=name;
    }
    
}
