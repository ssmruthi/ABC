package edu.uwt.adc.project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiTCPClient {

	// The client socket
	private static Socket clientSocket = null;
	// The output stream
	private static PrintStream toServer = null;
	private static BufferedReader fromServer = null;

	private static Scanner sc = null;
	public static void main(String[] args) {

		sc= new Scanner(System.in);
		System.out.print("Enter the server address to connect : ");		
		// The default port.
		String server = sc.nextLine();
		
		System.out.print("\nEnter the port number to connect : ");
		// The default host.
		int portNumber= sc.nextInt();

		List<Long> putTime=new ArrayList<Long>();
		List<Long> getTime=new ArrayList<Long>();
		List<Long> delTime=new ArrayList<Long>();

		String line = "";
		long timestamp1=0;


		/*
		 * Open a socket on a given hos t and port. Open input and output
		 * streams.
		 */
		try {

			clientSocket = new Socket(server, portNumber); // socket  for server 
			clientSocket.setSoTimeout(10000);
			sc = new Scanner(new File("src/edu/uwt/adc/project1/dataScript")); // reading a file for put, get, delete instructions
	
			toServer = new PrintStream(clientSocket.getOutputStream()); // data from client to server
			fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //  data from server to client

			//Parse through input file (dataScript) to call server for each instruction			
			if (clientSocket != null && toServer != null) {
			System.out.println("Connecting to server.");
				while (sc.hasNextLine()) {
					line = sc.nextLine(); //reads each line of file
					if (line.equals("exit"))
						break;
					timestamp1=System.currentTimeMillis();					 
					toServer.println(line); //writes to client output stream which is same as server's input stream
				

				//Getting response from server
				String responseLine;
				if ((responseLine = fromServer.readLine()) != null) {
					timestamp1=System.currentTimeMillis()-timestamp1;
					System.out.println(responseLine);			
				
				if(line.startsWith("PUT")){							
					putTime.add(timestamp1);
				}else if(line.startsWith("GET")){
					getTime.add(timestamp1);							
				}else if(line.startsWith("DELETE")){						
					delTime.add(timestamp1);
				}
				}
			}
			}
			
			
			System.out.println("\n\t\tTCP Connection\n");
	      		System.out.println("\n\t\tPUT TRANSACTIONS\n");
			System.out.println("Number of Transactions:"+putTime.size());
			System.out.println("Average time taken(ms) :"+CalculationsUtility.average(putTime.toArray(new Long[0])));
			System.out.println("Standard Deviation :"+CalculationsUtility.standardDeviation(putTime.toArray(new Long[0])));


			System.out.println("\n\t\tGET TRANSACTIONS\n");
			System.out.println("Number of Transactions:"+getTime.size());
			System.out.println("Average time taken(ms) :"+CalculationsUtility.average(getTime.toArray(new Long[0])));
			System.out.println("Standard Deviation :"+CalculationsUtility.standardDeviation(getTime.toArray(new Long[0])));

			System.out.println("\n\t\tDELETE TRANSACTIONS\n");
			System.out.println("Number of Transactions:"+delTime.size());
			System.out.println("Average time taken(ms) :"+CalculationsUtility.average(delTime.toArray(new Long[0])));
			System.out.println("Standard Deviation :"+CalculationsUtility.standardDeviation(delTime.toArray(new Long[0])));
	 
		
      	
	        }catch(java.net.UnknownHostException e1){
        		System.out.println("Host not found. Please try again.");

		}catch (SocketTimeoutException e1) {
        		System.out.println("Connection Timed out occured. Please try again.");
	        } catch (SocketException e1) {
	        	System.out.println("Error occured. Please try again.");
        	} catch (FileNotFoundException e1) {
        		System.out.println("File not found error. Please try again.");
	        } catch (IOException e1) {
        		System.out.println("Error occured. Pleae try again.");
	        }catch (Exception e) {
			System.err.println("Error occurred during processing. Process aborted!" + e.getMessage());
			
		}
	}

}