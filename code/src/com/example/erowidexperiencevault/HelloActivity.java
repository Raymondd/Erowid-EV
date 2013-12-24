package com.example.erowidexperiencevault;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelloActivity extends Activity{
	ArrayList<Substance> mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);
		
        mList = new ArrayList<Substance>();
        
        try {
			mList = (new SubstanceNames().execute()).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        TextView main = (TextView) findViewById(R.id.main);
        
	}

}
