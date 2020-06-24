import java.io.*;
import java.net.*;
import java.util.*;
public class Client implements Runnable{
	
	// Public variable declarations
	static String query;
	String name;
	static int port;
	//static ArrayList<String> runtimes = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		
		Scanner in = new Scanner(System.in);
		
		// Number of clients being used
		int clientQuantity;
		
		//ArrayList which is used to store each thread
		ArrayList<Thread> clients = new ArrayList<Thread>();
		
		// User enters a port number which is stored in port
		System.out.print("Enter a port: ");
		port = in.nextInt();
		in.nextLine();
	
			// User enters which query they want to request
			System.out.println("Available commands: Time ");
			System.out.print("Enter a command: ");		
			query = in.nextLine();
			
			// User enters the number of clients they want to spawn
			System.out.print("Number of clients: ");
			clientQuantity = in.nextInt();
			
			
			// Creates a thread object and adds it to the clients array
			// (one for each client)
			for(int i = 0; i < clientQuantity; i++) {
				clients.add(new Thread(new Client("Client " + (i + 1))));
			}
			
			// Runs the run function once for each client
			// Threads are run simultaneously 
			for(int i = 0; i < clientQuantity; i++) {
				clients.get(i).start();
			}			
											 		
	}
	
	// Client object constructor
	public Client(String x) {
		name = x;
	}
	
	// Run function which runs a separate thread for every client
	public void run() {

		try {
			
			// Starts a system timer
			long start = System.nanoTime();
			// Creates a socket object
			Socket socket = new Socket("139.62.210.153", port);
			// Creates a Print Writer object
			PrintWriter pr = new PrintWriter(socket.getOutputStream());	
			
			// Sends the query to the server and flushes the stream
			pr.println(query);
		    pr.flush();
			
		    // Initializes the input stream reader object needed for the Buffered Reader object
			InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());
			// Buffered Reader object used for reading messages from the server
			BufferedReader bf = new BufferedReader(inStreamReader);
	    
			// Reads the message from the server
			String message = bf.readLine();
			// Stops the timer
			long stop = System.nanoTime();
			// Displays the message 
			System.out.println(name + ": " + message);
			// Calculates and displays the runtime in ms of each client query
			System.out.println(name + ": " + ((stop - start) / 1000000) + "ms");
			//runtimes.add(name + ": " + ((stop - start) / 1000000) + "ms");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}