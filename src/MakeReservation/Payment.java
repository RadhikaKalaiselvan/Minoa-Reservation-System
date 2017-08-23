/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MakeReservation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author csk12
 */
public class Payment {
    String custName;
    String Phone;
    String start;
    
   public void Payment(String name, String phno, String startDate){
       custName=name;
       Phone=phno;
       start=startDate;
   }
   
   public String retrievePayment(String custName, String Phone, String s){
       start = s;
       ArrayList<String> row = new ArrayList<>();
        try {
            row = DBUtil.searchResvRecords(custName,Phone,start);
        } catch (Exception ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
        }
       return row.get(6);
   }
   
   public boolean processPayment(Guest g, CreditCard c){
        try {
            //c.charge(0);
            DBUtil.changePaymentStatus(g.name,g.phoneNumber, start);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
   }
   
}
