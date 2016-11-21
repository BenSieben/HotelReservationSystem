import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

public class HotelModel {
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private HashMap<String, Object> userSession = null;
    public HotelModel(Connection connection) {
        this.conn = connection;
    }

    public boolean signUp(HashMap<String, Object> data) {
        try {
            String sql ="INSERT INTO Customer " +"(first_name, last_name, username, password, age) VALUES " + "(?,?,?,?,?);";
            this.preparedStatement = conn.prepareStatement(sql);
            this.preparedStatement.setObject(1, data.get("first_name"));
            this.preparedStatement.setObject(2, data.get("last_name"));
            this.preparedStatement.setObject(3, data.get("username"));
            this.preparedStatement.setObject(4, data.get("password"));
            this.preparedStatement.setObject(5, data.get("age"));
            this.preparedStatement.executeUpdate();


            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResultSet signIn(HashMap<String, Object> data) {
        try {
            ResultSet result = null;
            String sql = "SELECT * FROM Customer WHERE username = ? AND password = ?;";
            this.preparedStatement = conn.prepareStatement(sql);
            this.preparedStatement.setObject(1, data.get("username"));
            this.preparedStatement.setObject(2, data.get("password"));
            result = this.preparedStatement.executeQuery();


            if ( result.next() == true) {
            	//user found, store id into user session
            	userSession = new HashMap<String, Object>();
            	userSession.put("customer_id", result.getInt("customer_id"));
            }
            result.beforeFirst();//move cursor back to beginning
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /*
     * Nullify user session
     */
    public void signOut(){
    	userSession = null;
    }

    /*
     * This function should be called only when user is logged in
     * Input: none, user session already has customer_id
     * Output: Booking time/changes, start datetime, end datetime, guests #, room #, room details
     */
    public ResultSet viewReservation() {
        try {
        	//Cannot access unless logged in
        	if(userSession == null){
        		return null;
        	}

            ResultSet result = null;
            String sql = "SELECT t2.updated_at, t4.start_date, t4.end_date, t5.guests, t6.room_number, t8.* " +
            		 "FROM booking_customer t1, booking t2, booking_period t3, period t4, booking_room t5, room t6, room_details t7, details t8 " +
            		"WHERE ? = t1.customer_id AND t1.booking_id = t2.booking_id AND t2.booking_id = t3.booking_id AND t3.period_id = t4.period_id " +
            		 "AND t5.booking_id = t2.booking_id AND t5.room_id = t6.room_id AND t6.room_id = t7.room_id AND t7.details_id = t8.details_id;";
            this.preparedStatement = conn.prepareStatement(sql);
            this.preparedStatement.setObject(1, userSession.get("customer_id"));
            result = this.preparedStatement.executeQuery();

            ResultSetMetaData rsmd = result.getMetaData();//for debugging
            int columnsNumber = rsmd.getColumnCount(); //for debugging

            //for debugging
            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = result.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

            result.beforeFirst();//move cursor back to beginning
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
