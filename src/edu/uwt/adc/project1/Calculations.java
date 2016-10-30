package edu.uwt.adc.project1;

import java.util.ArrayList;

public  class Calculations {
	public static double average(ArrayList<Long> a){
		
		double value=0.0;
		for(int i=0;i<a.size ();i++) {
			value += a.get (i);
		}
		if(a.size ()==0)
        	return 0;
        else
        	return value/a.size ();
		
	}
	
	public static double standardDeviation(ArrayList<Long> a){

		double stdDev=Math.sqrt(getVariance(a));
		return stdDev;
		
	}
	
	public static double getVariance(ArrayList<Long> a)
    {
        double avg = average(a);
        double temp = 0;
        for(int i=0;i<a.size ();i++)
            temp += (a.get (i)-avg)*(a.get (i)-avg);
        
        if(a.size ()==0)
        	return 0;
        else
        	return temp/a.size ();
    }
}
