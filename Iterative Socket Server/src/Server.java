import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.Runtime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Server {
	
	public static void main(String[] args) {
			
		Scanner in = new Scanner(System.in);
		int port;
		String query;
		
		// Prompts user for a port number and stores input in port variable
		System.out.print("Enter a port: ");
		port = in.nextInt();
		

		try {
			// Creates new Server Socket object and 
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server is listening on port: " + port);
			
			// While loop that assures that as long as the server is up, it is listening for client requests 
			while(true) {				
				
				StringBuilder message = new StringBuilder();
				
				// Starts listening for requests from clients
				Socket socket = serverSocket.accept();
				
				// Initializes the input stream reader object needed for the Buffered Reader object
				InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());
				// Buffered Reader object used for reading input from the client
				BufferedReader bf = new BufferedReader(inStreamReader);
				// Print Writer object used for sending information to the client
				PrintWriter pr = new PrintWriter(socket.getOutputStream());
				
				// Notifies the server administrator that a new client has been connected 
				// (only prints on the server and mostly used for debugging purposes)
				System.out.println("New client connected.");
				
				// Reads a query from the client
				query = bf.readLine(); //check converting this straight to lowercase
				
				query = query.toLowerCase();
				
				
				switch(query) {
					case "date and time":
						query = "date";
						break;
					case "memory use":
						query = "free -h";
						message.append("\n");
						break;
					case "current users":
						query = "w";
						break;
					case "running processes":
						query = "ps -aux";
						message.append("\n");
					case "uptime":
					case "netstat":
						break;
				}
					
					//ProcessBuilder processBuilder = new ProcessBuilder();
					
					//processBuilder.command(query);
					
					Process process = Runtime.getRuntime().exec(query);
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));		
					
					String line;
					while((line = reader.readLine()) != null) {
						message.append(line + "\n");
					}
				
				pr.println(message.toString());
				
				// Checks which query was requested by the user
				/*if(query.equalsIgnoreCase("Time")) {
					// Sends the date and time to the user
					
					processBuilder.command("date");
					
					Process process = processBuilder.start();
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					
					String line;
					while((line = reader.readLine()) != null) {
						message += line;
					}
					
					pr.println(message);
				}
				else if(query.equalsIgnoreCase("Uptime")) {
					// Sends the uptime to the user
					//pr.println(uptime());
				}
				else if(query.equalsIgnoreCase("Memory Usage")) {
					pr.println(memoryUsage());
				}*/
				
				// Flushes the Print Reader stream
				pr.flush();
				
				
				// Closes the socket and notifies the server administrator of the closure of the socket
				socket.close();
				System.out.println("Socket closed.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

	}
	
	// Returns a string with the current date and time on the server
	private static String dateAndTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
	}
	
	// Returns a string with the elapsed time which the server has been running
	
	private static String memoryUsage() {
		return "Memory usage " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024  + "Kb";
	}
	
}