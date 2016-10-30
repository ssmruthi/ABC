package edu.uwt.adc.project1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Scanner;

public class UDPServer extends Thread{
    DatagramSocket udp = null;//Creates Server Socket
    
    public static void main(String arg[]){
        UDPServer server = new UDPServer();// Creating a thread
        server.start ();//Starts thread
    }
    
    
    public UDPServer(){
        int PORT;

        Scanner sc = new Scanner (System.in);//Declaring the Scanner
        System.out.println ("Please enter the port you want to open:");
        String output = sc.next ();// Initiating the PORT to ind on server
        PORT = Integer.parseInt (output);
                
            try {
                udp = new DatagramSocket (PORT);//Keeping the sockets open
            } catch (SocketException e) {
                System.out.println ("Socket already in use.Please try again later"+e.getMessage ());
            }
        System.out.println ("Server Started.\t Waiting for client");
    }
        public void run(){
            HashMap<String, String> Map = new HashMap ();// Hashmap Declaration
            while (true){
                try {
                    byte[] buffer = new byte[1000];//Sets byte value

                    DatagramPacket mail = new DatagramPacket (buffer, buffer.length);////Creates packet to receive data
                    udp.receive (mail);//Receives data
                    String message = new String (mail.getData (), 0, mail.getLength ());

                    byte[] buf;
                    //Hash map operation depends on receiving data
                    String[] operation = message.split (",");
                    if (operation[0].contentEquals ("GET")) {
                        String getdata = Map.get (operation[1]);
                        if(getdata==null){
                            buf = ("GET request completed.No VALUE found for"+operation[1]+" key \n").getBytes ();
                            System.out.println (new Timestamp (System.currentTimeMillis ())+ "\tGET Request completed \t\tNo VALUE found for key");
                        }else {
                            buf = ("GET request completed. The value of " + operation[1] + " is " + getdata + "\n").getBytes ();
                            System.out.println ("\n" + new Timestamp (System.currentTimeMillis ()) + "\t Incoming GET Request from IP : " + mail.getAddress ());
                        }
                    } else if (operation[0].contentEquals ("DELETE")) {
                        Map.remove (operation[1]);
                        buf = ("Delete request completed \n").getBytes ();
                        System.out.println ("\n" + new Timestamp (System.currentTimeMillis ()) + "\t Incoming DELETE Request from IP : " + mail.getAddress ());
                    } else if (operation[0].contentEquals ("PUT")) {
                        Map.put (operation[1], operation[2]);
                        buf = ("PUT request completed \n").getBytes ();
                        System.out.println ("\n" + new Timestamp (System.currentTimeMillis ()) + "\t Incoming PUT Request from IP : " + mail.getAddress ());
                    } else {
                        buf = ("Request " + message + " Failed. Try again later").getBytes ();
                    }
                    DatagramPacket out = new DatagramPacket (buf, buf.length, mail.getAddress (), mail.getPort ());//Creates packet to send data
                    udp.send (out);//Sends data
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
}