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
            if(argv.length == 0 || argv[0].equals("console")) {  // Use console when no arguments given or argument = "console"
                useConsole(connection);
            }
            else {  // Use GUI when user specifies argument which is not "console"
                useGUI(connection);
            }
        }
        else {
            System.err.println("Failed to make connection!");
        }
    }

    // Use console view to run program
    private static void useConsole(Connection connection) {
        HotelModel model = new HotelModel(connection);

        HotelView view = new HotelView();
        view.setVisible(false);  // do not let the GUI pop up for console mode

        HotelController controller = new HotelController(model, view);
        controller.init();
        view.dispose(); // completely dispose the view after init is done (so execution ends after init)
    }

    // Use GUI view to run program
    private static void useGUI(Connection connection) {
        HotelModel model = new HotelModel(connection);

        HotelView view = new HotelView();

        HotelController controller = new HotelController(model, view);
        controller.initializeViewListeners();
    }
}