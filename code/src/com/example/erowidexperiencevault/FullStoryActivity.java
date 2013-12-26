package com.example.erowidexperiencevault;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class FullStoryActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_story);
		
		String link = getIntent().getStringExtra("link");
		
		TextView storyView = (TextView) findViewById(R.id.title);
		
		/*loading = (ProgressBar) findViewById(R.id.progress);
		mainLayout = (LinearLayout) findViewById(R.id.linear);
		TextView titleBar = (TextView) findViewById(R.id.name);
		titleBar.setText(sub_name);
		
        mStoryList = new ArrayList<Story>();
        */
        
		String storyText = "";
		
        try {
			storyText = (new getFullStory().execute(link)).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        storyView.setText(storyText);
		
	}
	
	public class getFullStory extends AsyncTask<String, Void, String>{
		
		protected String doInBackground(String... link){	
			String URL = "http://www.erowid.org/experiences/" + link[0];
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
			
			String fullStory = pageText;
			
			/*ArrayList<Story> stories = new ArrayList<Story>();
			
			
			int start = 0;
			int end = 0;
			
			int i = 0;
			while(true){
			
				i++;
				if(i > 50){
					break;
				}
				
				start = pageText.indexOf("<tr class=\"\">", start);
				if(start <= 0){
					break;
				}
				
				
				//checking if an image exists for the rating
				int temp_start = pageText.indexOf("d>", start);
				int temp_end = pageText.indexOf("</td>", start);
				
				String temp_rat = pageText.substring(temp_start, temp_end);
				
				String rating;
				if(temp_rat.indexOf("img") > 0){
					temp_start = pageText.indexOf("star_", temp_start) + 5;
					temp_end = pageText.indexOf(".", temp_start);
					
					rating = pageText.substring(temp_start, temp_end);
					
				}else{
					rating = "0";
				}
				
				
				start = pageText.indexOf("href=\"", start) + 6;
				end = pageText.indexOf("\"", start);
				String link = pageText.substring(start, end);
				
				start = pageText.indexOf(">", start) + 1;
				end = pageText.indexOf("<", start);
				String title = pageText.substring(start, end);
				
				start = pageText.indexOf("<td>", start) + 4;
				end = pageText.indexOf("<", start);
				String author = pageText.substring(start, end);
				
				start = pageText.indexOf("<td>", start) + 4;
				end = pageText.indexOf("<", start);
				String subs = pageText.substring(start, end);
				
				start = pageText.indexOf("\">", start) + 2;
				end = pageText.indexOf("<", start);
				String date = pageText.substring(start, end);
				
				//Example of the table structure from the Erowid Stories
				//<tr class=""><td> <img align="right" src="images/exp_star_2.gif" alt="Highly Recommended" border="0"></td><td><a href="exp.php?ID=53780">I Am Not Hardcore: Or Important Safety Tip</a></td><td>maison</td><td>Alcohol, Ketamine & 1,4-butanediol</td><td align="right">May 24 2007</td>
			
				
				start = end;
				
				//Log.e("OUTPUT", "(" + rating + ", " + link + ", " + title + ", " + author + ", " + subs + ", " + date + ")");
				
				stories.add(new Story(rating, link, title, author, subs, date));
			}*/
			
			return fullStory;
		}
		
    	protected void onPostExecute(ArrayList<Story> result){
			//loading.setVisibility(View.GONE);
			//mainLayout.setVisibility(View.VISIBLE);
    	}
    	
    	protected void onPreExecute(){
			//loading.setVisibility(View.VISIBLE);
			//mainLayout.setVisibility(View.GONE);
    		
    	}
	}
}
