package edu.uwt.adc.project1;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class RPCServerAppImpl extends UnicastRemoteObject implements RPCServerAppInterface
{ 
	public static Map<String,String> keyValueStore;
	String message="";
	
    public Map<String, String> getKeyValueStore() {
		return keyValueStore;
	}

	public RPCServerAppImpl() throws RemoteException {	
		
	}
    
    public String delete(String key) throws RemoteException{    				
    	
    	   	
    	if(keyValueStore==null){
    		keyValueStore= new HashMap<String, String>();
    	}
		
    	if(keyValueStore.containsKey(key)){
    		message =new Timestamp(System.currentTimeMillis())+"\tDELETE Request completed\t\t"+key+" , "+ keyValueStore.get(key);   
    			keyValueStore.remove(key);    
    		}
    		else
        		message=new Timestamp(System.currentTimeMillis())+"\tDELETE Request completed \t\tNo VALUE found for "+key;
    	
    	try {
    		message="Client IP : "+RemoteServer.getClientHost()+"  "+message;
    	} catch (ServerNotActiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println(message);
    	return message;
    }
    
    public String put(String key, String value)throws RemoteException{
    			
    	if(keyValueStore==null){
    		keyValueStore= new HashMap<String, String>();
    	}
    	
    		keyValueStore.put(key, value);
    		if(keyValueStore.containsKey(key)){    		
    			message=new Timestamp(System.currentTimeMillis())+"\tPUT Request completed\t\t"+key+" , "+value;	
    		}else{
    			message="WARNING!\t"+new Timestamp(System.currentTimeMillis())+"\t\t\tEntry already exists for the key.("+key+","+value+") OVERWRITTEN";
    		}
    		try {
    			message="Client IP : "+RemoteServer.getClientHost()+"  "+message;
    		} catch (ServerNotActiveException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
    		System.out.println(message);
    	return message;
    }
    
    public String get(String key)throws RemoteException{
    	
    	   	
    	if(keyValueStore==null){
    		keyValueStore= new HashMap<String, String>();
    	}
		    	
    	if(keyValueStore.containsKey(key))
    		message =new Timestamp(System.currentTimeMillis())+"\tGET Request completed\t\t"+key+" , "+ keyValueStore.get(key);   
    	else
    		message=new Timestamp(System.currentTimeMillis())+"\tGET Request completed \t\tNo VALUE found for "+key;
    	
    	try {
    		message="Client IP : "+RemoteServer.getClientHost()+"  "+message;
    		
    	} catch (ServerNotActiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println(message);
    	return message;
    	
    }    
}

