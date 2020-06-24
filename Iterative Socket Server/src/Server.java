import java.io.*;
import java.net.*;
import java.util.Scanner;
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
					// Sends the date and time back to the user
					pr.println(dateAndTime());
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
	
	
	private static String dateAndTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
	}
	

}