import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class HotelReservationSystem {

    public static void main(String[] argv) {

        DataSource ds = DataSourceFactory.getMySQLDataSource();

        Connection connection = null;

        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            System.err.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if(connection != null) {
            HotelModel model = new HotelModel(connection);

            HotelView view = new HotelView();

            HotelController controller = new HotelController(model, view);
            controller.initializeViewListeners();
        }
        else {
            System.err.println("Failed to make connection!");
        }
    }
}