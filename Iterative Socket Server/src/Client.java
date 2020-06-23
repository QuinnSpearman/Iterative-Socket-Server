import java.io.*;
import java.net.*;
import java.util.*;
public class Client implements Runnable{
	
	static String query;
	String name;
	static int port;
	//static ArrayList<String> runtimes = new ArrayList<String>();
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int clientQuantity;
		ArrayList<Thread> clients = new ArrayList<Thread>();
		
		System.out.print("Enter a port: ");
		port = in.nextInt();
		in.nextLine();
		

			
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
			
			
			
			//Collections.sort(runtimes);
			
			/*for(int i = 0; i < clientQuantity; i++) {
				System.out.println(runtimes.get(i));
			}*/
											 		
	}
	
	public Client(String x) {
		name = x;
	}
	
	public void run() {

	    
		try {
			long start = System.nanoTime();
			Socket socket = new Socket("139.62.210.153", port);
			PrintWriter pr = new PrintWriter(socket.getOutputStream());	
			
			pr.println(query);
		    pr.flush();
			
			InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader bf = new BufferedReader(inStreamReader);
	    
			String message = bf.readLine();
			long stop = System.nanoTime();
			System.out.println(name + ": " + message);
			System.out.println(name + ": " + ((stop - start) / 1000000) + "ms");
			//runtimes.add(name + ": " + ((stop - start) / 1000000) + "ms");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}