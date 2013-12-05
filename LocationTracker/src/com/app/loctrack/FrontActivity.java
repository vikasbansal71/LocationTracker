package com.app.loctrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FrontActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front);
		Button bt=(Button)findViewById(R.id.frontb);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent intent=new Intent(FrontActivity.this,MainActivity.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu., menu);
		return true;
	}

}
