/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MakeReservation;

/**
 *
 * @author Preethi
 */
class CreditCard {
    int guestId;
    String cardname;
    String cardNo;
    int cvv;
    String expiryDate;
    public CreditCard(String cardname, String cardno, String expiry){
        this.cardname = cardname;
        this.cardNo=cardno;
        this.expiryDate=expiry;
    }
    static void charge()
    {
        System.out.println("ok");
    }
    
}
