import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.*;

public class Server {

	public static void main(String[] args) {

		// Scanner object for taking user input of port number
		Scanner in = new Scanner(System.in);

		// Local variable declarations
		int port;
		String query;
		String message = "";
		
		// Prompts user for a port number and stores input in port variable
		do {
			System.out.print("Enter a port # in range 1025 - 4998: ");
			port = in.nextInt();	
		}while(port < 1025 || port > 4998);

		try {

			// Creates Server socket object attached to the requested port
			ServerSocket serverSocket = new ServerSocket(port);
			// Displays which port number the server is listening on
			System.out.println("Server is listening on port: " + port);

			// While loop that assures that as long as the server is up, it is listening for
			// client requests
			while (true) {

				// Starts listening for requests from clients
				Socket socket = serverSocket.accept();

				// Initializes the input stream reader object needed for the Buffered Reader
				// object
				InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());

				// Buffered Reader object used for reading queries from the client
				BufferedReader queryReader = new BufferedReader(inStreamReader);

				// Print Writer object used for sending information to the client
				PrintWriter pr = new PrintWriter(socket.getOutputStream());

				// Reads a query from the client
				query = queryReader.readLine(); // check converting this straight to lowercase

				query = query.toLowerCase();

				// Determines which command is being requested, gets the command output from the
				// getCommand function and stores it in message
				switch (query) {
				case "date and time":
					message = getCommand("date");
					break;
				case "memory use":
					message = "\n" + getCommand("free -h");
					break;
				case "current users":
					message = getCommand("w");
					break;
				case "running processes":
					message = "\n" + getCommand("ps -aux");
					break;
				case "uptime":
				case "netstat":
					message = getCommand(query);
					break;
				default:
					message = "Invalid query.";
					break;
				}

				pr.println(message);
				pr.flush();

				// Flushes the print writer stream

				// Closes the socket
				socket.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Passes in the linux command as a string and outputs the linux command output
	// as a stringF
	private static String getCommand(String query) {

		// String builder object declaration, will be used for creating message that
		// will be sent back to user
		StringBuilder message = new StringBuilder();

		try {

			// Process object which calls for Linux command and stores it
			Process process = Runtime.getRuntime().exec(query);

			// Buffered reader which reads the Linux command
			BufferedReader commandOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			// String which will be used to temporarily store each line of the linux command
			// output
			String line;

			// While there is still a Linux command output line to be read, store it in line
			// and append that line to the message variable followed by a new line
			while ((line = commandOutputReader.readLine()) != null) {
				message.append(line + "\n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message.toString();

	}
}