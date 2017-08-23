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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class DBUtil {


	public static Connection getConnection(){
		Connection conn = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/MinoaReservation?user=root&password=mysqlpass");
		} catch(Exception e){
			System.out.println("Exception occured while get connection call :"+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(Connection con) throws Exception {
		try{
			con.close();
		} catch(Exception e){
			System.out.println("Exception while closing the connection "+e);
		}
	}

/*
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String sql ="Select * from Reservation";
		ResultSet rs=null;
		Connection conn = null;
		try
		{

		conn = getConnection();
		Statement s = conn.createStatement();
         rs = s.executeQuery(sql);
        } catch(Exception e){
		  System.out.println("Exception occured while get connection call :"+e.getMessage());
		  e.printStackTrace();
		}
		closeConnection(conn);
	System.out.println(checkAvailability("2017-05-01","2017-05-04")+"");
	System.out.println(store("Mathew","3456789233","testmail@yahoo.com")+"");
	System.out.println(storeReservation(1,"2017-06-13","2017-06-20",1,"N",840));
	 System.out.println(getMailingList().get(0).get(1)+"");
	changePaymentStatus("Mathew","3456789233","2017-05-01");
		addCreditCardDetails(1);

	}*/

	public static int checkAvailability(String startDate,String endDate) throws Exception{
		//default room type=1, change be changed if passed as argument
		int roomId=1,availableRooms=0;
		Connection conn = null;
		String query="SELECT count(*) AS C FROM Reservation WHERE '"+endDate+"' <= EndDate AND '"+startDate+"' >= StartDate";
		conn = getConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query);
		rs.next();
		int blockedRooms=rs.getInt(1);
		String query1="select totalRooms as rooms from Rooms where RoomID="+roomId;
		ResultSet rs1 = s.executeQuery(query1);
		rs1.next();

		int totalRooms=rs1.getInt(1);
		closeConnection(conn);
		//System.out.println("total  "+totalRooms+" blocked "+blockedRooms);
		if(totalRooms>blockedRooms){
			availableRooms=totalRooms-blockedRooms;
		}
		System.out.println(availableRooms);
		return availableRooms;
	}

	/*Store the Guest details in DB
	 *Needs changes if invoked with Guest Class object
	 * 
	 */
	public static int storeGuest(String name,String phoneNum,String emailID) throws Exception{
		String query = " insert into Customer (CustomerName,PhoneNumber,EmailID,CreditCardID)"
				+ " values (?, ?, ?, ?)";
		Connection conn=getConnection();
		PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setString (1,name);
		preparedStmt.setString (2,phoneNum);
		preparedStmt.setString (3,emailID);
		preparedStmt.setNull(4, Types.INTEGER);
		preparedStmt.execute();
		ResultSet keys = preparedStmt.getGeneratedKeys();    
		keys.next();  
		int guestID = keys.getInt(1);
		closeConnection(conn);
		return guestID;
	}


	public static int getRoomCost() throws Exception{
		Connection conn = null;
		String query="select BaseRate as br from Rooms where RoomID=1";
		conn = getConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query);
		rs.next();
		int roomCost= rs.getInt(1);
		closeConnection(conn);
		return roomCost;
	}
	
	public static void updateRooms() throws Exception{
		String q="update Rooms set totalRooms=totalRooms-1 where RoomID=1";
		Connection conn = getConnection();
		PreparedStatement preparedStmt1 = conn.prepareStatement(q);
		preparedStmt1.executeUpdate();
		closeConnection(conn);
	}

	public static boolean storeReservation(int custID,String startDate,String endDate,int type,String paymentStatus,int cost) throws Exception{
		String query1="select ResvID from Reservation where CustID="+custID+" and StartDate='"+startDate+"' and EndDate='"+endDate+"'";
		System.out.println(query1);
		Connection conn = getConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query1);
	
		if(!rs.next()){
		String query = " insert into Reservation (CustID,StartDate,EndDate,Type,PaymentStatus,Cost)"
				+ " values (?,?,?,?,?,?)";
		 conn=getConnection();
		PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setInt (1,custID);
		preparedStmt.setString (2,startDate);
		preparedStmt.setString (3,endDate);
		preparedStmt.setInt(4,type);
		preparedStmt.setString (5,"N");
		preparedStmt.setInt (6,cost);
		preparedStmt.execute();
		ResultSet keys = preparedStmt.getGeneratedKeys();    
		keys.next(); 
                try{
                    int resvID= keys.getInt(1);
                    closeConnection(conn);
                    return true;
                }catch(Exception e){}
		} 
		return false;
	}

	public static ArrayList<ArrayList<String>> getMailingList() throws Exception{
		ArrayList<ArrayList<String>> mailingList=new ArrayList<ArrayList<String>>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 45);
		String futureDate=dateFormat.format(c.getTime());
		System.out.println(" future date "+futureDate);
		String query="select CustomerName,PhoneNumber,EmailID,Cost,PaymentStatus "
				+"from Reservation inner join Customer on Reservation.CustID=Customer.CustID where startDate='"+futureDate+"'";
		Connection conn=getConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query);
		while(rs.next()){
			ArrayList<String> row=new ArrayList<String>();
			row.add(rs.getString(1));
			row.add(rs.getString(2));
			row.add(rs.getString(3));
			row.add(rs.getString(4));
			row.add(rs.getString(5));
			mailingList.add(row);
		}
		closeConnection(conn);
		return mailingList;		
	}

	public static void changePaymentStatus(String name,String phNum,String startDate) throws Exception{
		
		String query="select ResvID from Reservation inner join Customer on Reservation.CustID=Customer.CustID "+
				"where CustomerName='"+name+"' and PhoneNumber='"+phNum+"' and startDate='"+startDate+"'";
		System.out.println(query);
		Connection conn = getConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query);
		rs.next();
		int resvID=rs.getInt(1);
		String updateQuery="update Reservation set PaymentStatus='Y' where ResvID="+resvID;
		PreparedStatement preparedStmt = conn.prepareStatement(updateQuery);
		preparedStmt.executeUpdate();
		String updateQuery1="update Reservation set Cost=0 where ResvID="+resvID;
		PreparedStatement preparedStmt1 = conn.prepareStatement(updateQuery1);
		preparedStmt1.executeUpdate();
		closeConnection(conn);

	}

	public static ArrayList<String> searchResvRecords(String name,String phNum,String startDate) throws Exception{
		ArrayList<String> row=new ArrayList<String>();
		System.out.println("name "+name+" "+phNum+" "+startDate);
		String query="select ResvID,Customer.CustID,startDate,EndDate,Type,PaymentStatus,Cost from Reservation inner join Customer on Reservation.CustID=Customer.CustID "+
				"where CustomerName='"+name+"' and PhoneNumber='"+phNum+"' and startDate='"+startDate+"'";
		System.out.println(" query"+query);
		Connection conn = getConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query);
		while(rs.next()){
			row.add(rs.getString(1)); //ResvID
			row.add(rs.getString(2)); //CustID
			row.add(rs.getString(3)); //StartDate
			row.add(rs.getString(4)); //EndDate
			row.add(rs.getString(5)); //Reservation Type
			row.add(rs.getString(6));  //PaymentStatus
			row.add(rs.getString(7));  //Cost
		}
		for(String r:row)
			System.out.println(r);
		return row;
		
	}

	/*
	 * Second argument should be CreditCard class object
	 * change the card expiration date in the query itself
	 */
	public static int  addCreditCardDetails(int custID, CreditCard cc) throws Exception{
		String query = " insert into CreditCard(CustID, CardHolderName,CardNumber,CVV,ExpirationDate) values"
				+"(?,?,?,?,STR_TO_DATE('04/30/2019','%m/%d/%Y'))";
		Connection conn=getConnection();
		PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStmt.setInt (1,custID);
		preparedStmt.setString (2,cc.cardname);
		preparedStmt.setString (3,cc.cardNo);
		preparedStmt.setString(4,"446");
		preparedStmt.execute();
		ResultSet keys = preparedStmt.getGeneratedKeys();    
		keys.next();  
		int ccID= keys.getInt(1);
		closeConnection(conn);
		return ccID;

	}
}


