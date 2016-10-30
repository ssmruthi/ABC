package edu.uwt.adc.project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class UDPClient {
	
	private static DatagramSocket udp = null;// Creates client UDP socket
	
	private static byte[] buf;// Initiating buffer
	
	private static String port;

	
	public static void main(String arg[]) throws UnknownHostException {
		
		
		try {		
			
			InetAddress address;
			Scanner SC = new Scanner(System.in);

			System.out.print("\nPlease enter a host name or IP address to connect:");
			String host = SC.next();// Gets IP address or host name from user
			address = InetAddress.getByName(host);

			System.out.print("\nPlease enter a port number to connect:");
			port = SC.next();// Gets port number from user
			int serport = Integer.parseInt(port);

			udp = new DatagramSocket();// Creates Socket to connect

			buf = new byte[1000];// Sets the buffer value

			System.out.println("\nInitiating request at " + new Timestamp(System.currentTimeMillis()) + "\n");

			ArrayList<Long> put = new ArrayList<Long>();
			ArrayList<Long> get = new ArrayList<Long>();
			ArrayList<Long> delete = new ArrayList<Long>();

			Scanner sc = new Scanner(new File("src/kvp-operations.csv"));
			
			String message;

			while (sc.hasNext()) {
				message = sc.nextLine();
				DatagramPacket packet;// Creates packet to send
				packet = new DatagramPacket(message.getBytes(), message.length(), address, serport);// Sends
																									// data
																									// calculating
																									// the
																									// length
																									// that
																									// should
																									// not
																									// exceed
																									// uffer
																									// value

				Timestamp sendpacket = new Timestamp(System.currentTimeMillis());
				long sendtime = sendpacket.getTime();
				udp.send(packet);// Sends data
				udp.setSoTimeout(10000);

				DatagramPacket pack = new DatagramPacket(buf, buf.length);// Creates
																			// packet
																			// to
																			// receive
				udp.receive(pack);// Receives data
				String result = new String(pack.getData(), 0, pack.getLength());
				System.out.println(result);
				Timestamp recievepack = new Timestamp(System.currentTimeMillis());// Calculating
																					// the
																					// timestamp
				long recievetime = recievepack.getTime();
				long diff = recievetime - sendtime;
				// Depends on operation difference in timestamp is placed in the
				// array
				if (result.contains("PUT request completed")) {
					put.add(diff);
				} else if (result.contains("GET request completed")) {
					get.add(diff);
				} else if (result.contains("DELETE request completed")) {
					delete.add(diff);
				} else {
					System.out.println("Time Calculation failure");
				}
			}
			// Calculating the average and standard deviation
			CalculationsUtility calc = new CalculationsUtility();
			
			double puttime = calc.average(put);
			System.out.println("Average time taken for total PUT operations using UDP is " + puttime);
			double putstd = calc.standardDeviation(put);
			System.out.println("Standard deviation of total PUT operations using UDP is " + putstd + "\n");

			double gettime = calc.average(get);
			System.out.println("Average time taken for total GET operations using UDP is " + gettime);
			double getstd = calc.standardDeviation(get);
			System.out.println("Standard deviation of total GET operations using UDP is " + getstd + "\n");

			double deletetime = calc.average(delete);
			System.out.println("Average time taken for total DELETE operations using UDP is " + deletetime);
			double deletestd = calc.standardDeviation(delete);
			System.out.println("Standard deviation of total DELETE operations using UDP is " + deletestd + "\n");

			System.out.println(
					"\n Request successfully completed at " + new Timestamp(System.currentTimeMillis()) + "\n");

		} catch (java.net.UnknownHostException e1) {
			System.out.println("Host not found. Please try again.");

		} catch (SocketTimeoutException e1) {
			System.out.println("Connection Timed out occured. Please try again.");
		} catch (SocketException e1) {
			System.out.println("Error occured. Please try again.");
		} catch (FileNotFoundException e1) {
			System.out.println("File not found error. Please try again.");
		} catch (IOException e1) {
			System.out.println("Error occured. Pleae try again.");
		} catch (Exception e1) {
			System.out.println("Unrecoverable error occured. Please try again.");
		}
	}
}