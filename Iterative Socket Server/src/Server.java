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
			Socket socket = serverSocket.accept();
			
			System.out.println("Client Connected.");
			
			InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader bf = new BufferedReader(inStreamReader);
			
			while(true) {
				query = bf.readLine();
				if(query == null) {
					break;
				}
				
				PrintWriter pr = new PrintWriter(socket.getOutputStream());
				
				if(query.equalsIgnoreCase("Time")) {
					pr.println(dateAndTime());
				}
				pr.flush();
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

