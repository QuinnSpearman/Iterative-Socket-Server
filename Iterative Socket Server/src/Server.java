import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Server {

	public static void main(String[] args) {
			
		try {
			ServerSocket serverSocket = new ServerSocket(2020);
			Socket socket = serverSocket.accept();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			System.out.println("Client Connected.");
		
		
		

	}
	private static void dateAndTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		System.out.println(dateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")));		
	}
}
