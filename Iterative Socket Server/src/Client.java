import java.io.*;
import java.net.*;
public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("139.62.210.153", 2020);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
