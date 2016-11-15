import java.util.HashMap;
import java.util.Scanner;

public class HotelView {
	
	public HashMap<String, Object> signUpView(){
		try{
			String first, last, username, password = null;
			int age = 0;
			HashMap<String, Object> userInput = new HashMap<String, Object>();
	        Scanner scanner = new Scanner(System.in);
	        
			System.out.println("--------SignUp-------");
			
			System.out.println("Enter First Name:");
			first = scanner.nextLine();
						
			System.out.println("Enter Last Name:");
			last = scanner.nextLine();
			
			System.out.println("Enter Username:");
			username = scanner.nextLine();
			
			System.out.println("Enter Password:");
			password = scanner.nextLine();
			
			System.out.println("Enter Age:");
			age = Integer.parseInt(scanner.nextLine());
			

			userInput.put("first_name", first);
			userInput.put("last_name", last);
			userInput.put("username", username);
			userInput.put("password", password);
			userInput.put("age", age);
			
			return userInput;
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Object> signInView(){
		try{
			String username, password = null;
			HashMap<String, Object> userInput = new HashMap<String, Object>();
	        Scanner scanner = new Scanner(System.in);
	        
			System.out.println("--------SignIn-------");
			
			System.out.println("Enter Username:");
			username = scanner.nextLine();
			
			System.out.println("Enter Password:");
			password = scanner.nextLine();
			

			userInput.put("username", username);
			userInput.put("password", password);
			
			return userInput;
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void displayError(String arg){
		System.out.println("Error! "+ arg);
	} 
	
	public void displaySuccess(String arg){
		System.out.println("Success! "+ arg);
	} 
}
