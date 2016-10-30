package edu.uwt.adc.project1;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * This a serverApp Thread for each client Request. The instructions from each client is monitored here
 */
class ServerAppThread extends Thread {

  private PrintStream toClient = null;
  private Socket clientSocket = null;

  public static Map<String,String> keyValueStore;
	String message="";
	
  public Map<String, String> getKeyValueStore() {
		return keyValueStore;
	}
  
  public ServerAppThread(Socket clientSocket){
    this.clientSocket = clientSocket;
  }

  public void run() {
    
    try {    	
      Scanner sc= new Scanner(clientSocket.getInputStream());	// prepares stream to read client instructions. Eg: PUT 1 ABC
      toClient = new PrintStream(clientSocket.getOutputStream()); // prepares stream for  data from server to client 
       
      String line="";
		
		
		
      while (sc.hasNext()) {
        String str = sc.next();
        if (line.startsWith("exit")) {
          break;
        }
        
        System.out.print("Request from Client "+clientSocket.getInetAddress().getHostName()+"\t");
        
        if(str.startsWith("PUT")){							
			line=put(sc.next(), sc.nextLine());

		}else if(str.startsWith("GET")){
			line=get(sc.next());
			sc.nextLine();
			
		}else if(str.startsWith("DELETE")){						
			line=delete(sc.next());
			sc.nextLine();

		}else{
			line=new Timestamp(System.currentTimeMillis())+"\t\tInvalid input from IP : "+clientSocket.getInetAddress().getHostName()+"\t";
		}

        System.out.println(line);

       // sc.nextLine();
        toClient.println(line);
      }
      
      
      	
      //Closing streams and sockets for each thread while server keeps listening
      toClient.close(); 
      clientSocket.close();
      
    } catch (IOException e) {
    }
  }
  
  
  
  
  public String delete(String key) {    				
		
  	if(keyValueStore.containsKey(key)){
  		message =new Timestamp(System.currentTimeMillis())+"\t\tDELETE Request completed\t\t"+key+" , "+ keyValueStore.get(key);   
  			keyValueStore.remove(key);    
  		}
  		else
      		message=new Timestamp(System.currentTimeMillis())+"\t\tDELETE Request completed \t\tNo VALUE found for "+key;
  
  	
  	return message;
  }
  
  public String put(String key, String value){
  			
  	if(keyValueStore==null){
  		keyValueStore= new HashMap<String, String>();
  	}
  	
  		keyValueStore.put(key, value);
  		if(keyValueStore.containsKey(key)){    		
  			message=new Timestamp(System.currentTimeMillis())+"\t\tPUT Request completed\t\t"+key+" , "+value;	
  		}else{
  			message="WARNING!\t"+new Timestamp(System.currentTimeMillis())+"\t\t\tEntry already exists for the key.("+key+","+value+") OVERWRITTEN";
  		}

  	return message;
  }
  
  public String get(String key){
  	
  	   	
  	if(keyValueStore==null){
  		keyValueStore= new HashMap<String, String>();
  	}
		    	
  	if(keyValueStore.containsKey(key))
  		message =new Timestamp(System.currentTimeMillis())+"\t\tGET Request completed\t\t"+key+" , "+ keyValueStore.get(key);   
  	else
  		message=new Timestamp(System.currentTimeMillis())+"\t\tGET Request completed \t\tNo VALUE found for key"+key;
  	
  	return message;
  	
  }
  
}