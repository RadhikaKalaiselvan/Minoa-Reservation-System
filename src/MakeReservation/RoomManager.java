/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MakeReservation;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Platform.exit;

/**
 *
 * @author Preethi
 */
public class RoomManager {
    static int number_of_available_rooms;
    static int base_rate= 1200;
    static long checkAvailability(String startDate,String endDate)
    {  
        try{
            number_of_available_rooms= DBUtil.checkAvailability(startDate,endDate);
            Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            Date cdate=new Date();
            Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(df.format(cdate));
            long days= Math.abs((date2.getTime() - date1.getTime())/86400000);
            System.out.println(days);
            return days;
        }catch(Exception pe){}
        return -1;
    }
    static void updateRooms(String startDate,String endDate, int custId, int type, String paystatus, int totalCost)
    {
       
        try {
            DBUtil.updateRooms();
        } catch (Exception ex) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("System could not update the number of rooms");
        }
        
    }
    static int getRoomCost()
    {
        return base_rate;
    }
}
