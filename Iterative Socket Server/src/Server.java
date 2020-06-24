import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Server {
	
	static long uptimeStart = System.nanoTime();
	
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
				query = bf.readLine();
		
				// Checks which query was requested by the user
				if(query.equalsIgnoreCase("Time")) {
					// Sends the date and time to the user
					pr.println(dateAndTime());
				}
				else if(query.equalsIgnoreCase("Uptime")) {
					// Sends the uptime to the user
					pr.println(uptime());
				}
				
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
	private static String uptime() {
		int seconds;
		int minutes;
		int hours;
		int days;
		String totalTime = "Uptime: ";
		
		seconds = (int)((System.nanoTime() - uptimeStart) / 1000000000);
		
		days = seconds / 86400;
		
		if(days > 0) {
			totalTime += days + "d ";
		}
		
		seconds %= 86400;
		
		hours = seconds / 3600;
		
		if(hours > 0) {
			totalTime += hours + "h ";
		}
		
		seconds %= 3600;
		
		minutes = seconds / 60;
		
		
		if(minutes > 0) {
			totalTime += minutes + "m ";
		}
		
		seconds %= 60;
		
		if(seconds > 0) {
			totalTime += seconds + "s ";
		}
		
		return totalTime;
		
	}
	
}