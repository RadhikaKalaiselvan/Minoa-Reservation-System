package MakeReservation;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reservation {
	
	 static int reservId = 0;
	 String startDate;
	 String endDate;
	 int totalCost;
	 int custId;
	 Payment p;
         Guest g;
         public Reservation(String s, String e) {
		startDate = s;
		endDate = e;
	}
         
	void  mapGuest(int gid){}
	
	int getCharges(){
		return 1;
	}
	
	void updateAvailability(){
		
	}
        
        public int searchRecord(Guest guest){
             try {
                 g = guest;
                 ArrayList<String> row = new ArrayList<String>();
                 row = DBUtil.searchResvRecords(g.name, g.phoneNumber, startDate);
                 if(!row.isEmpty())
                 {
                	return Integer.parseInt( p.retrievePayment(g.name, g.phoneNumber, startDate));
                 }
             } catch (Exception ex) {
                 Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
             }
             return -1;
}
        
        public Payment initPayment(){
            p = new Payment();
            return p;
        }
        
        public int retrievePayment(Guest g){
        	if(p!=null)
            return Integer.parseInt(p.retrievePayment(g.name, g.phoneNumber, startDate));
        	else{
        		System.out.println("error with p");
        		return 1;
        	}
        }
	
}
