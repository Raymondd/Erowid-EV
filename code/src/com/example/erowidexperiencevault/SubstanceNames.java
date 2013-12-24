package com.example.erowidexperiencevault;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class SubstanceNames extends AsyncTask<Void, Void, ArrayList<Substance>>{
	private final String URL = "http://www.erowid.org/experiences/";
	
	protected ArrayList<Substance> doInBackground(Void... arg0){	
		String pageText = "";
    	
    	HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(URL);
		try{
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			
			if(null != entity){
				pageText = EntityUtils.toString(entity);
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		ArrayList<Substance> names = new ArrayList<Substance>();
		
		int beg = pageText.indexOf("select");
		int fin = pageText.indexOf("</select>");
		
		
		String options = pageText.substring(beg, fin);
		
		
		int start = options.indexOf("<");
		int end = 0;
		while(true){
			
			start = options.indexOf("\"", start) + 1;
			end = options.indexOf("\"", start);
			if(start == 0){
				break;
			}
			int id = Integer.parseInt(options.substring(start, end));
			
			start = options.indexOf(">", end) + 1;
			end = options.indexOf("<", start);
			if(start == 0){
				break;
			}
			String name = options.substring(start, end);
			
			start = end;
			
			names.add(new Substance(name, id));
		}
		
		return names;
	}
}