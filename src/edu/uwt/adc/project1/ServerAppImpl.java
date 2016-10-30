package edu.uwt.adc.project1;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ServerAppImpl implements ServerAppInterface
{ 
	public static Map<String,String> keyValueStore;
	String message="";
	
    public Map<String, String> getKeyValueStore() {
		return keyValueStore;
	}


    
    public String delete(String key) {    				
    	
    	   	
    	if(keyValueStore==null){
    		keyValueStore= new HashMap<String, String>();
    	}
		
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

