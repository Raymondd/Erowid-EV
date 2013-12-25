package com.example.erowidexperiencevault;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubstanceList extends ListActivity{
	SubAdapter mSubAdapter;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);
		
		ProgressBar loading = (ProgressBar) findViewById(R.id.progress);
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linear);
		
        ArrayList<Substance> subList = new ArrayList<Substance>();
        
        try {
			subList = (new SubstanceNames().execute()).get();
			loading.setVisibility(View.GONE);
			mainLayout.setVisibility(View.VISIBLE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
        
		mSubAdapter = new SubAdapter(this, R.layout.sub_item, R.id.sub_name, subList);
		setListAdapter(mSubAdapter);
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
		/*Color color = mColorAdapter.getItem(position);
		int hexColor = color.mColor;
		
		Intent i = new Intent();
		i.putExtra("color", hexColor);
		setResult(Activity.RESULT_OK, i); //(resultCode, data)
		finish();*/
	}

}
