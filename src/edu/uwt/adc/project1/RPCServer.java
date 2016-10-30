package edu.uwt.adc.project1;

import java.rmi.Naming;
import java.util.Scanner;

public class RPCServer {

	static RPCServerAppInterface obj; 

	public static void main(String args[]) 
    { 
        try 
        { 
        	Scanner sc= new Scanner(System.in);

        	        	
        	System.out.print("\nPlease enter a port number to connect:");
        	int port= sc.nextInt();
        	
        	if(obj==null){
        		obj= new RPCServerAppImpl();
        	}
        	
        	port=1099;

            java.rmi.registry.LocateRegistry.createRegistry(port);
            Naming.rebind("rmi://:"+port+"/RPCServer",obj);        
        } 
        catch (Exception e) 
        { 
            System.out.println("ERROR OCCURRED DURING PROCESSING. PLEASE RETRY PROCESS." + e.getMessage()); 
        } 
    } 
}
