package com.example.erowidexperiencevault;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchActivity extends ListActivity{
	SubAdapter mSubAdapter;
	ArrayList<Substance> mSubList;
	ProgressBar loading;
	ProgressBar loading2;
	LinearLayout mainLayout;
	LinearLayout noCon;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		loading = (ProgressBar) findViewById(R.id.progressBar);
		loading2 = (ProgressBar) findViewById(R.id.proBar2);
		mainLayout = (LinearLayout) findViewById(R.id.linear);
		EditText search = (EditText) findViewById(R.id.search_term);
		noCon = (LinearLayout) findViewById(R.id.noCon);
        Button refresh = (Button) findViewById(R.id.refresh);
		
		if(isNetworkConnected()){
			new SubstanceNames().execute();
		}else{
			loading.setVisibility(View.GONE);
			noCon.setVisibility(View.VISIBLE);
		}
		
		
		refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isNetworkConnected()){
					noCon.setVisibility(View.GONE);
					loading.setVisibility(View.VISIBLE);
					new SubstanceNames().execute();
				}else{
					loading2.setVisibility(View.VISIBLE);
				}
				
			}
		});
		
		
		search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {};
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {};
			
			@Override
			public void afterTextChanged(Editable s){
				if(s.length() > 0){
					ArrayList<Substance> newSubList = new ArrayList<Substance>();
					for(int i = 0; i < mSubList.size(); i++){
						if((mSubList.get(i).getName().toLowerCase()).contains((s.toString().toLowerCase()))){
							newSubList.add(mSubList.get(i));
						}
					}
					mSubAdapter = new SubAdapter(SearchActivity.this, R.layout.sub_item, R.id.sub_name, newSubList);
					SearchActivity.this.setListAdapter(mSubAdapter);
				}else{
					mSubAdapter = new SubAdapter(SearchActivity.this, R.layout.sub_item, R.id.sub_name, mSubList);
					SearchActivity.this.setListAdapter(mSubAdapter);
					
				}
			};
		});
		
	}
	
	private class SubAdapter extends ArrayAdapter<Substance>{
		
		public SubAdapter(Context context, int resource, int textViewResourceId, List<Substance> objects){
			super(context, resource, textViewResourceId, objects);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			RelativeLayout layout = (RelativeLayout) super.getView(position, convertView, parent);
			//View thumbnail = layout.findViewById(R.id.colorThumbnail);
			//thumbnail.setBackgroundColor(getItem(position).mColor);
			TextView name = (TextView) layout.findViewById(R.id.sub_name);
			name.setText(getItem(position).getName());
			return layout;
		}
	
	}
	
	public void onListItemClick(ListView listview, View view, int position, long id){
		Substance sub = mSubAdapter.getItem(position);
		
		Intent i = new Intent(this, StoryListActivity.class);
		i.putExtra("id", sub.getId());
		i.putExtra("name", sub.getName());
		startActivity(i);
	}
	
	
	public boolean isNetworkConnected() {
         final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
         return (activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED);
    }
	
	
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
			
			if(beg < 1 || fin < 1){
				return null;
			}
			
			
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
			
			mSubList = names;
			
			mSubAdapter = new SubAdapter(SearchActivity.this, R.layout.sub_item, R.id.sub_name, mSubList);
			SearchActivity.this.setListAdapter(mSubAdapter);
			return names;
		}
		
    	protected void onPostExecute(ArrayList<Substance> result){
			loading.setVisibility(View.GONE);
			mainLayout.setVisibility(View.VISIBLE);
    	}
    	
    	protected void onPreExecute(){
			loading.setVisibility(View.VISIBLE);
			mainLayout.setVisibility(View.GONE);
    	}
	}
	
	
}

