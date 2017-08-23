/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MakeReservation;

/**
 *
 * @author uks160030
 */
public class Controller {
    Reservation r;
    Guest g;
    Payment p;
    CreditCard cc;
    boolean flag = false;
   
    int restype;
    
    public int paymentNeeded(){
        return restype;
    }
    public void init60DayAdv(String startDate, String endDate){
        r = new Days60Advance(startDate, endDate);
        restype = 2;
    }
    
    public int enterGuestDetails(String name,String email, String phone){
        if(r != null){
            g = new Guest(name, email, phone);
            int charges = r.getCharges();//runtime
            int gid = g.store();
            r.mapGuest(gid); //runtime           
            return charges;
        }
        return -1;
    }
    
    public void initPrepaid(String startDate, String endDate){
        r = new PrepaidReservation(startDate, endDate);
        restype = 1;
    }
    
    public void initConventional(String startDate, String endDate){
        r = new ConventionalReservation(startDate, endDate);
        restype = 3;
    }
    
    public void initIncentive(String startDate, String endDate){
        r = new IncentiveReservation(startDate, endDate);
        restype = 4;
    }
    
    public int checkAvailability(String startDate, String endDate){
    	System.out.println(" call check avail"+startDate+" "+endDate);
        long daysrem = RoomManager.checkAvailability(startDate, endDate);
    	System.out.println(" days "+daysrem);
        return (int)daysrem;
    }
    
    public void enterCreditCard(String cardname, String cardno, String expiry){
        cc = new CreditCard(cardname, cardno, expiry);
        g.mapCC(cc);
    }
    
    public boolean processPayment(){
        if(p!= null){
            return (p.processPayment(g, cc));
        }
        return false;
    }
    
    public int searchRecord(String name, String phone, String email){
        if(g == null){
           g = new Guest(name, email, phone);
        }
        if(p!=null){
        	int charges = r.searchRecord(g);
           if(charges!=-1)
                return charges;
           return -1;
        }
        return -1;
    }
    
    public void confirmReservation(){
        if(r != null && !flag){
            r.updateAvailability();
        }	
    }
    
    public void initPayment(String startDate, String endDate){
        if(r==null){
            r = new Reservation(startDate, endDate);
            flag = true;
        }
        p = r.initPayment();
        if(p!= null) System.out.println("success");	
    }
    
    public void confirmCharges(){
        r.retrievePayment(g);
    }
}
