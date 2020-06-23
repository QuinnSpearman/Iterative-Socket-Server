import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Server {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int port;
		System.out.print("Enter a port: ");
		port = in.nextInt();
		String query;

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server is listening on port: " + port);
			
			
			while(true) {				
				Socket socket = serverSocket.accept();
				
				InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());
				BufferedReader bf = new BufferedReader(inStreamReader);
				PrintWriter pr = new PrintWriter(socket.getOutputStream());
				
				System.out.println("New client connected.");
				
				query = bf.readLine();
		
				if(query.equalsIgnoreCase("Time")) {
					pr.println(dateAndTime());
				}
				pr.flush();
				
				socket.close();
				System.out.println("Socket closed.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private static String dateAndTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
	}
	

}