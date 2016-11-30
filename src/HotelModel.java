import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

public class HotelModel {
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private HashMap<String, Object> userSession = null;
	private static final String checkOutTime = " 14:00:00";
	private static final String checkInTime = " 10:00:00";

	public HotelModel(Connection connection) {
		this.conn = connection;
	}

	public boolean signUp(HashMap<String, Object> data) {
		try {
			String sql = "INSERT INTO Customer " + "(first_name, last_name, username, password, age) VALUES "
					+ "(?,?,?,?,?);";
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

			if (result.next() == true) {
				// user found, store id into user session
				userSession = new HashMap<String, Object>();
				userSession.put("customer_id", result.getInt("customer_id"));
			}
			result.beforeFirst();// move cursor back to beginning
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/*
	 * Nullify user session
	 */
	public void signOut() {
		userSession = null;
	}

	/*
	 * This function should be called only when user is logged in Input: none,
	 * user session already has customer_id Return: Booking time/changes, start
	 * datetime, end datetime, guests #, room #, room details
	 */
	public ResultSet viewReservation() {
		try {
			// Cannot access unless logged in
			if (userSession == null) {
				return null;
			}

			ResultSet result = null;
			String sql = "SELECT t2.*, t4.start_date, t4.end_date, t5.guests, t6.room_number, t8.* "
					+ "FROM booking_customer t1, booking t2, booking_period t3, period t4, booking_room t5, room t6, room_details t7, details t8 "
					+ "WHERE ? = t1.customer_id AND t1.booking_id = t2.booking_id AND t2.booking_id = t3.booking_id AND t3.period_id = t4.period_id "
					+ "AND t5.booking_id = t2.booking_id AND t5.room_id = t6.room_id AND t6.room_id = t7.room_id AND t7.details_id = t8.details_id;";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, userSession.get("customer_id"));
			result = this.preparedStatement.executeQuery();

			ResultSetMetaData rsmd = result.getMetaData();// for debugging
			int columnsNumber = rsmd.getColumnCount(); // for debugging

			// for debugging
			while (result.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = result.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}

			result.beforeFirst();// move cursor back to beginning
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	// TODO
	/*
	 * This function should be called only when user is logged in Input: none,
	 * user session already has customer_id Return: Booking time/changes, start
	 * datetime, end datetime, guests #, room #, room details
	 */
	public ResultSet viewArchivedReservation() {
		try {
			// Cannot access unless logged in
			if (userSession == null) {
				return null;
			}

			ResultSet result = null;
			String sql = "SELECT t2.*, t4.start_date, t4.end_date, t5.guests, t6.room_number, t8.* "
					+ "FROM booking_customer t1, booking t2, booking_period t3, period t4, booking_room t5, room t6, room_details t7, details t8, booking_archive t9, archive t10 "
					+ "WHERE ? = t1.customer_id AND t9.booking_id = t2.booking_id AND t10.archive_id = t9.archive_id AND t1.booking_id = t2.booking_id AND t2.booking_id = t3.booking_id AND t3.period_id = t4.period_id "
					+ "AND t5.booking_id = t2.booking_id AND t5.room_id = t6.room_id AND t6.room_id = t7.room_id AND t7.details_id = t8.details_id ";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, userSession.get("customer_id"));
			result = this.preparedStatement.executeQuery();

			ResultSetMetaData rsmd = result.getMetaData();// for debugging
			int columnsNumber = rsmd.getColumnCount(); // for debugging

			// for debugging
			while (result.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = result.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}

			result.beforeFirst();// move cursor back to beginning
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/*
	 * After user enter desired date, this function shall display the available
	 * rooms. Input: start and end date in HashMap data type, using 'YYYY-MM-DD'
	 * format Return: All available rooms (+room details) that do not conflict
	 * with the input dates.
	 */
	public ResultSet getAvailableRooms(HashMap<String, Object> data) {
		try {
			// Cannot access unless logged in
			if (userSession == null) {
				return null;
			}
			ResultSet result = null;
			String sql = "SELECT p1.*, p3.* FROM room p1, room_details p2, details p3 WHERE p1.room_id = p2.room_id AND p2.details_id = p3.details_id "
					+ "AND p1.room_id NOT IN "
					+ "(SELECT t5.room_id FROM period t1, booking_period t2, booking t3, booking_room t4, room t5, room_details t6, details t7 " // subquery
					+ "WHERE t7.details_id = t6.details_id AND t6.room_id = t5.room_id AND t5.room_id = t4.room_id AND t4.booking_id = t3.booking_id "
					+ "AND t3.booking_id = t2.booking_id AND t2.period_id = t1.period_id AND "
					+ "((t1.start_date BETWEEN ? AND ?) OR (t1.end_date BETWEEN ? AND ?)));";

			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, data.get("start_date") + checkInTime);
			this.preparedStatement.setObject(2, data.get("end_date") + checkOutTime);
			this.preparedStatement.setObject(3, data.get("start_date") + checkInTime);
			this.preparedStatement.setObject(4, data.get("end_date") + checkInTime);

			result = this.preparedStatement.executeQuery();

			ResultSetMetaData rsmd = result.getMetaData();// for debugging
			int columnsNumber = rsmd.getColumnCount(); // for debugging

			// for debugging
			/*
			 * while (result.next()) { for (int i = 1; i <= columnsNumber; i++)
			 * { if (i > 1) System.out.print(",  "); String columnValue =
			 * result.getString(i); System.out.print(columnValue + " " +
			 * rsmd.getColumnName(i)); } System.out.println(""); }
			 */

			result.beforeFirst();// move cursor back to beginning
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/*
	 * Get payment type by parsing an enum column in payment table. Input: none
	 * Return: Array of Payment types
	 */
	public String[] getAllPaymentTypes() {
		try {
			ResultSet result = null;
			String sql = "SELECT TRIM(TRAILING ')' FROM TRIM(LEADING '(' FROM TRIM(LEADING 'enum' FROM column_type))) column_type "
					+ "FROM information_schema.columns "
					+ "WHERE table_schema = 'hotel' AND table_name = 'payment' AND column_name = 'payment_type';";

			this.preparedStatement = conn.prepareStatement(sql);
			result = this.preparedStatement.executeQuery();

			// Capitalize the first letter, and remove , and '' from result.
			// Store each into an array.
			if (result.next()) {
				String[] split = result.getString("column_type").replace("'", "").split(",");
				String[] finalResult = new String[split.length];
				for (int i = 0; i < split.length; i++) {
					finalResult[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
				}
				return finalResult;
			}

			return null; // if result not found
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/*
	 * This function finalize the booking by performing multiple inserts into
	 * the schema Input: payment_type, amount, start_date, end_date, room_id,
	 * guests Return: false if error occurs, true if successful
	 */
	public boolean reserveRoom(HashMap<String, Object> data) {
		try {
			// Cannot access unless logged in
			if (userSession == null) {
				return false;
			}

			ResultSet result = null;

			// Insert into Booking table
			String sql = "INSERT INTO Booking (updated_at) VALUES (NOW());";
			this.preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			this.preparedStatement.executeUpdate();
			result = this.preparedStatement.getGeneratedKeys();
			int bookingId = 0;

			if (result.next()) {
				bookingId = result.getInt(1);
			}

			// Insert into Payment table
			sql = "INSERT INTO Payment (payment_type, amount, updated_at) VALUES (?,?,NOW());";
			this.preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			this.preparedStatement.setObject(1, data.get("payment_type"));
			this.preparedStatement.setObject(2, data.get("amount"));
			this.preparedStatement.executeUpdate();

			result = this.preparedStatement.getGeneratedKeys();
			int paymentId = 0;

			if (result.next()) {
				paymentId = result.getInt(1);
			}

			// Insert into Period table
			sql = "INSERT INTO Period (start_date, end_date) VALUES (?,?);";
			this.preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			this.preparedStatement.setObject(1, data.get("start_date") + checkInTime);
			this.preparedStatement.setObject(2, data.get("end_date") + checkOutTime);
			this.preparedStatement.executeUpdate();

			result = this.preparedStatement.getGeneratedKeys();
			int periodId = 0;

			if (result.next()) {
				periodId = result.getInt(1);
			}

			// Insert into Booking_customer table
			sql = "INSERT INTO Booking_customer (booking_id, customer_id) VALUES (?,?);";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, bookingId);
			this.preparedStatement.setObject(2, userSession.get("customer_id"));
			this.preparedStatement.executeUpdate();

			// Insert into Booking_payment table
			sql = "INSERT INTO Booking_payment (booking_id, payment_id) VALUES (?,?);";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, bookingId);
			this.preparedStatement.setObject(2, paymentId);
			this.preparedStatement.executeUpdate();

			// Insert into Booking_period table
			sql = "INSERT INTO Booking_period (booking_id, period_id) VALUES (?,?);";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, bookingId);
			this.preparedStatement.setObject(2, periodId);
			this.preparedStatement.executeUpdate();

			// Insert into Booking_room table
			sql = "INSERT INTO Booking_room (booking_id, room_id, guests) VALUES (?,?,?);";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, bookingId);
			this.preparedStatement.setObject(2, data.get("room_id"));
			this.preparedStatement.setObject(3, data.get("guests"));
			this.preparedStatement.executeUpdate();

			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/*
	 * Delete specific reservation from a customer's reservation. Frontend
	 * determines if user has access. Input: booking ID to be deleted Return:
	 * true if successful, false if error occurs
	 */
	public boolean deleteReservation(HashMap<String, Object> data) {
		try {
			String sql = "";

			// Delete from payment first
			sql = "DELETE pm FROM Payment pm JOIN Booking_payment bp ON pm.payment_id = bp.payment_id WHERE booking_id = ?;";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, data.get("booking_id"));
			this.preparedStatement.executeUpdate();

			// Delete from period
			sql = "DELETE pd FROM Period pd JOIN Booking_period bp ON pd.period_id = bp.period_id WHERE booking_id = ?;";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, data.get("booking_id"));
			this.preparedStatement.executeUpdate();

			// Delete from booking will also delete the foreign keys
			sql = "DELETE FROM Booking WHERE booking_id = ?;";
			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, data.get("booking_id"));
			this.preparedStatement.executeUpdate();

			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/*
	 * Update the number of guests from an existing reservation. Input: number
	 * of guests, booking ID that needs to be updated Return: true if
	 * successful, false if error occurs
	 */
	public boolean updateReservation(HashMap<String, Object> data) {
		try {
			String sql = "UPDATE booking_room SET guests= ? WHERE booking_id = ?; ";

			this.preparedStatement = conn.prepareStatement(sql);
			this.preparedStatement.setObject(1, data.get("guests"));
			this.preparedStatement.setObject(2, data.get("booking_id"));
			this.preparedStatement.executeUpdate();

			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/*
	 * In case front-end system loses current user ID Input: none Return:
	 * customer_id if exists, if not exists, returns -1
	 */
	public int getCurrentUserID() {
		try {

			if (userSession == null) {
				return -1;
			} else {
				return (Integer) userSession.get("customer_id");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;

		}
	}

}
