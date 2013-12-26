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
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class FullStoryActivity extends Activity {
	ProgressBar loading;
	LinearLayout mainLayout;
	WebView webView;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_story);

		String link = getIntent().getStringExtra("link");
		
		//loading = (ProgressBar) findViewById(R.id.proBar);
		mainLayout = (LinearLayout) findViewById(R.id.mainView);
		webView = (WebView) findViewById(R.id.mainWeb);
		
		String storyHTML = "";

		try {
			storyHTML = (new getFullStory().execute(link)).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		final String mimeType = "text/html";
		final String encoding = "UTF-8";
		webView.loadDataWithBaseURL(null, storyHTML, mimeType, encoding, null);

		//webView.loadUrl(link);
	}

	
	public class getFullStory extends AsyncTask<String, Void, String> {
			String cssURL = "http://www.erowid.org/experiences/includes/exp_view.css";
		
		protected String doInBackground(String... link) {
			String URL = link[0];
			String pageText = "";
			String css = "";

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(URL);
			try {
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();

				if (null != entity) {
					pageText = EntityUtils.toString(entity);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//getting the css for the page
			client = new DefaultHttpClient();
			get = new HttpGet(cssURL);
			try {
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();

				if (null != entity) {
					css = EntityUtils.toString(entity);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String fullStory = "";

			
			Log.e("OUTPUT***", "START");
			
			int start = pageText.indexOf("</head>");
			fullStory += pageText.substring(0, start);
			Log.e("OUTPUT1", fullStory);
			
			fullStory += "<style>" + css + "</style>";
			Log.e("OUTPUT2", fullStory);
			
			int end = pageText.indexOf("<table");
			fullStory += pageText.substring(start, end);
			Log.e("OUTPUT3", fullStory);
			
			
			start = pageText.indexOf("</table>") + 8;
			fullStory += pageText.substring(start);
			Log.e("OUTPUT4", fullStory);
			
			
			//Log.e("OUTPUT", fullStory);
			
			return fullStory;
		}

		protected void onPostExecute(ArrayList<Story> result) {
			//loading.setVisibility(View.GONE);
			//mainLayout.setVisibility(View.VISIBLE);
		}

		protected void onPreExecute() {
			//loading.setVisibility(View.VISIBLE);
			//mainLayout.setVisibility(View.GONE);

		}
	}
}
