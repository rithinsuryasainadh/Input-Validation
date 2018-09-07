	package validation;
	import java.sql.*;
	import java.util.Scanner;

	public class val {
		
		public static boolean isFullname(String str) {
			String expression = "^[A-Z]\\'?([a-zA-Z]*?\\'?[a-zA-Z]*?\\,?[ ]?\\'?\\-?\\.?){1,3}|[\\p{L}\\s.’\\-,]+$";

			return str.matches(expression);        
		}
		public static boolean isPhone(String s) {
			String pattern = "(^\\d{5}$)|(^\\d{5}\\.\\d{5}$)|(^\\+[1-9]{1,2}[ ]?\\(|^[1][ ]?\\(|^[0][1][1][ ][1]?[ ]?\\(?|^\\(?)([1-9]\\d{1,2})?\\)?[- ]?(\\d{3})[-| ](\\d{4})$";
			return s.matches(pattern);
		}


		public static void main(String args[]){  
			
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(       /*MySQL connection */
					"jdbc:mysql://localhost:3306/validation","root","");  
				String name = "";
				while(true){
					
					String line = args[0];
					
					System.out.println(line);                     
					String command[] = line.split(" ");     /* SPLITTING THE INPUT "SEARCH INPUT" TO "INPUT" FOR DATABASE LOOKUP*/
					if (line.startsWith("/quit")) {
						break;
					}

					if(command[0].equals("ADD")) {
						String myString1 = args[1];   
						String myString2=args[2];
						if (isFullname(myString1)) {
							System.out.println("valid name");
							
							if (isPhone(myString2)){
								System.out.println("valid number");
							}
						}
						Statement stmt=con.createStatement();  
						stmt.executeUpdate("INSERT INTO valid VALUES ("+myString1+","+myString2+");");  /* SQL statement for server lookup*/
						System.out.println("name and phone number added");
					}
					
					if(command[0].equals("DEL")) {
						String myString1 = args[1];   
						String myString2=args[2];
						if (isFullname(myString1)) {
							System.out.println("valid name");
						}
						Statement stmt=con.createStatement();  
						stmt.executeUpdate("DELETE FROM valid WHERE name='myString1' or phonenumber='myString1';");  /* SQL statement for server lookup*/
						System.out.println("row deleted");
					}
					
					if(command[0].equals("LIST")) {
					//String myString1 = scanner.next();   
						
						Statement stmt=con.createStatement();  
						ResultSet rs=stmt.executeQuery("SELECT * FROM valid");  /* SQL statement for server lookup*/
						System.out.println("name      number");
						
						while (rs.next()) {
			            //int id = rs.getInt("id");
							String names = rs.getString("name");
							String number = rs.getString("phonenumber");
							System.out.println(names+"   "+number);
						}
						
					}
				}
			}
			
			catch(Exception e){ e.printStackTrace();}
			
			
		}
	}  