import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
public class Client implements Runnable{
	
	static Socket socket;
	static PrintWriter pr;
	static String query;
	String name;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int port;
		int clientQuantity;
		
		ArrayList<Thread> clients = new ArrayList<Thread>();
		
		System.out.print("Enter a port: ");
		port = in.nextInt();
		in.nextLine();
		
		try {
			socket = new Socket("139.62.210.153", port);
			pr = new PrintWriter(socket.getOutputStream());	
			
			System.out.println("Available commands: Time ");
			System.out.print("Enter a command: ");		
			query = in.nextLine();
			
			System.out.print("Number of clients: ");
			clientQuantity = in.nextInt();
			
			for(int i = 0; i < clientQuantity; i++) {
				clients.add(new Thread(new Client("Client " + (i + 1))));
			}
			
			for(int i = 0; i < clientQuantity; i++) {
				clients.get(i).start();
			}
					    
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Client(String x) {
		name = x;
	}
	
	public void run() {
		pr.println(query);
	    pr.flush();	    
	    
		try {
			InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader bf = new BufferedReader(inStreamReader);
	    
			String message = bf.readLine();
			System.out.println(name + ": " + message);    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
