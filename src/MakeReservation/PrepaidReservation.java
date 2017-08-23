package MakeReservation;

public class PrepaidReservation extends Reservation{
	
	public PrepaidReservation(String s, String e){
		super(s,e);
	}
	
	public void mapGuest(int gid)
	{
		//get instance of guest and then associate
		custId = gid;
		try {
			DBUtil.storeReservation(custId, startDate, endDate, 1, "unpaid", totalCost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public int getCharges()
	{
		//get base rate from room manager and compute. Send it back to the controller
		//RoomManager rm = new RoomManager();
		totalCost =(int)( 0.75 * RoomManager.getRoomCost());
		System.out.println(" cost "+totalCost+" from manager "+RoomManager.getRoomCost());
		return totalCost; //return to controller
	}
	
	public void updateAvailability()
	{
		//RoomManager rm = new RoomManager();
		RoomManager.updateRooms(startDate, endDate, custId, 1, "paid", totalCost);
	}
}
