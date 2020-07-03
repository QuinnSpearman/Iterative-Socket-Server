import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Client implements Runnable {

	// Global variable declarations
	static String query;
	int clientNum;
	static int port;
	static int clientQuantity;
	static int totalRuntime = 0;
	static String[] runtimes;
	static int numOfRuntimesRecorded;
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		// ArrayList which is used to store each thread
		ArrayList<Thread> clients = new ArrayList<Thread>();

		// User enters a port number which is stored in port variable
		do {
			System.out.print("Enter a port # in range 1025 - 4998: ");
			port = in.nextInt();
			in.nextLine();
		}while(port < 1025 || port > 4998);
		
		// User enters which query they want to request
		System.out.println("Available commands: Date and Time | Uptime | Memory Use | Netstat | Current Users | Running Processes");
		System.out.print("Enter a command: ");
		query = in.nextLine();		

		// User enters the number of clients they want to spawn
		System.out.print("Number of clients: ");
		clientQuantity = in.nextInt();
		
		runtimes = new String[clientQuantity];

		// Creates a thread object and adds it to the clients array
		// (one for each client)
		for (int i = 1; i <= clientQuantity; i++) {
			clients.add(new Thread(new Client(i)));
		}

		// Runs the run function once for each client
		// (Threads are run simultaneously)
		for (int i = 0; i < clientQuantity; i++) {
			clients.get(i).start();
		}
	}

	// Client object constructor
	public Client(int num) {
		clientNum = num;
	}

	// Run function which runs a separate thread for every client simultaneously
	public void run() {
		
		// Stores runtime of client query
		long runtime;
		
		// Helps build the string of the message sent from the server
		StringBuilder message = new StringBuilder();
		
		try {
			// Stores the current system time in nanoseconds 
			long start = System.currentTimeMillis();
			// Creates a socket object
			Socket socket = new Socket("139.62.210.153", port);
			// Creates a Print Writer object
			PrintWriter querySender = new PrintWriter(socket.getOutputStream());

			// Sends the query to the server and flushes the stream
			querySender.println(query);
			querySender.flush();

			// Initializes the input stream reader object needed for the Buffered Reader
			// object
			InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());
			// Buffered Reader object used for reading messages from the server
			BufferedReader bf = new BufferedReader(inStreamReader);

			// String variable for reading each line from server message individually
			String line;
			
			//Checks if the line being read from the server is not null, if so append the line to the message with a new line
			while((line = bf.readLine()) != null) {
				message.append(line + "\n");
			}
			
			// closes the socket
			socket.close();

			// Stores the system time in milliseconds
			long stop = System.currentTimeMillis();
			
			// Displays the message
			System.out.println("Client " + clientNum + ": " + message);

			// Calculates the runtime of the query in milliseconds
			runtime = stop - start;
			
			// Sums the total runtime of all clients
			totalRuntime += runtime;
			
			// Adds the runtime to the runtimes array
			runtimes[clientNum - 1] = "Client " + clientNum + " runtime: " + runtime + "ms";
			++numOfRuntimesRecorded;
			
			// If this is the final client request, display all of the runtimes
			if (clientQuantity <= numOfRuntimesRecorded) {
				displayRuntimes();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Displays the individual, total, and average runtimes
	private static void displayRuntimes() {
		
		// Sorts all client query runtimes in order of client number
		//Collections.sort(runtimes);
		
		// displays all of the client runtimes
		for (int i = 0; i < clientQuantity; i++) {					
			System.out.println(runtimes[i]);
		}
		
		// displays total and average runtimes of clients
		System.out.println("Total runtime: " + totalRuntime + "ms");
		System.out.println("Average runtime: " + totalRuntime / clientQuantity + "ms");
	}
}