import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class HotelModel {
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;

    public HotelModel(Connection connection) {
        this.conn = connection;
    }

    public boolean signUp(HashMap<String, Object> data) {
        try {
            String sql =
                    "INSERT INTO Customer " +
                            "(first_name, last_name, username, password, age) VALUES " +
                            "(?,?,?,?,?);";
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


            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
