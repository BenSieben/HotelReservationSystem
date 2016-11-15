import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
 
public class HotelReservationSystem
{
 
  public static void main(String[] argv) {
	  
      DataSource ds = DataSourceFactory.getMySQLDataSource();     
      
      Connection connection =  null; 
      
      try {
    	  connection = ds.getConnection(); 
      	}catch (SQLException e) {
	  		System.out.println("Connection Failed! Check output console");
	  		e.printStackTrace();
	  		return;
	  	}
	   
	  	if (connection != null) {
	        HotelModel model  = new HotelModel(connection);

	        HotelView view = new HotelView();

	        HotelController controller = new HotelController(model, view);
	        controller.init();
	        
	  	} else {
	  		System.out.println("Failed to make connection!");
	  	}
  }
}