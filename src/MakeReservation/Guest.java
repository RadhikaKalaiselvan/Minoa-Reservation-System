/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MakeReservation;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Preethi
 */
public class Guest {
    int id;
    String name;
    String phoneNumber;
    String email;
   // CreditCard c;
    
    public Guest(String name,String email, String phno){
        this.name=name;
        this.phoneNumber=phno;
        this.email=email;
    }
    int store()
    {
        try {
            id = DBUtil.storeGuest(name,phoneNumber,email);
            return id;
        } catch (Exception ex) {
            Logger.getLogger(Guest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    void mapCC(CreditCard cc)
    {
        try {
          /*  c.cardNo = cc.cardNo;
            c.expiryDate = cc.expiryDate;
            c.guestId = cc.guestId; */
            //DBUtil.createcardforGuest(id,cardNo,cvv,expiryDate);
            DBUtil.addCreditCardDetails(id,cc);       ////change method signature in dbutil
        } catch (Exception ex) {
            Logger.getLogger(Guest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
