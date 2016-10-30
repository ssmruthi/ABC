package edu.uwt.adc.project1;

import java.rmi.Remote;
import java.util.Map;

public interface RPCServerAppInterface extends Remote {
	
	public Map<String, String> getKeyValueStore()throws java.rmi.RemoteException ;
	public String put(String key, String value)throws java.rmi.RemoteException ;
	public String get(String key)throws java.rmi.RemoteException ;
	public String delete(String key)throws java.rmi.RemoteException ;
}