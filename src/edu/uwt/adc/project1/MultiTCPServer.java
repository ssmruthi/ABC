package edu.uwt.adc.project1;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultiTCPServer {

	public static void main(String[] args) throws IOException {

		Scanner sc= new Scanner(System.in);
		System.out.print("Enter the port number to connect : ");
		int portNumber;//sc.nextInt();
		sc.close();

		ServerSocket serverSocket = new ServerSocket(8080); //server socket at port enabled
		Socket clientSocket = null; 

		while (true) {
			
			try {				
				clientSocket = serverSocket.accept(); // server socket starts accepting client request
				new ServerAppThread(clientSocket).start();		// every client request is handled as a separate thread to maintain concurrency		
									
			} catch (IOException e) {
				System.out.println(e);
			}
		}

		
	}
}