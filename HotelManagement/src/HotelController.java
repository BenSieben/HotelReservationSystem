import java.sql.ResultSet;
import java.util.HashMap;

public class HotelController {
	private HotelModel model;
	private HotelView view;
	
	public HotelController(HotelModel model, HotelView view){
	      this.model = model;
	      this.view = view;
	}
	
	public void init() {
		signUp();
		signIn();
	}
	
	public void signUp(){
		try{
			HashMap<String, Object> userData = new HashMap<String, Object>();
			userData = this.view.signUpView();
			
			if(userData == null){
				this.view.displayError("Sign-up cannot get info.");
			}else{
				boolean isSuccessful = this.model.signUp(userData);
				if(isSuccessful){
					this.view.displaySuccess("Sign-up complete.");
				}else{
					this.view.displayError("Sign-up query unsuccessful.");
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private void signIn() {
		try{
			HashMap<String, Object> userData = new HashMap<String, Object>();
			userData = this.view.signInView();
			
			if(userData == null){
				this.view.displayError("Sign-in cannot get info.");
			}else{
				ResultSet result = this.model.signIn(userData);
				if(result.next() == true){ //check if result has any data, false means no
					this.view.displaySuccess("Welcome " + result.getString("first_name") + " " + result.getString("last_name") + ".");
				}else{
					this.view.displayError("Sign-in query unsuccessful.");
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
}
