package com.app.loctrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView textLat;
	TextView textLong;
WebView position;

	private static final int RESULT_SETTINGS = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textLat=(TextView)findViewById(R.id.textLat);
		textLong=(TextView)findViewById(R.id.textLong);
		
		position=(WebView)findViewById(R.id.webview);
		position.getSettings().setJavaScriptEnabled(true);
		
		LocationManager LocManager=(LocationManager)
		getSystemService(Context.LOCATION_SERVICE);
		Location locc=LocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


		try{
			
		textLong.setText(" "+" Longitude= "+ locc.getLongitude());
		}
		catch(Exception e){
			
			Toast.makeText(MainActivity.this, "Exception " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			
		}
		
		LocationListener LocLIstener=new myLocationListener();
		LocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,LocLIstener);
		
		}
	private class myLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location loc) {
			loc.getLatitude();
			loc.getLongitude();
			String text="My updated location is: "+"Latitude= "+loc.getLatitude() + "Longitude= "+ loc.getLongitude();
			Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();
		String url="http://maps.google.com/staticmap?center="+loc.getLatitude() + "," +loc.getLongitude()+"&Zoom=14&size=512*512&maotype=moblile/&markers="+loc.getLatitude() + "," +loc.getLongitude();
		position.loadUrl(url);
		
		
		
		//Location loc = new Location("point A");

		//loc.setLatitude(latA);
	  //  loc.setLongitude(lngA);

		Location locationB = new Location("point B");
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		String longi1=sharedPrefs.getString("preflongi", "NULL");
		String lati1=sharedPrefs.getString("preflati", "NULL");
		String mobile1=sharedPrefs.getString("prefmobile1", "NULL");
		String mobile2=sharedPrefs.getString("prefmobile2", "NULL");
		locationB.setLatitude(Double.valueOf(lati1));		
		locationB.setLongitude(Double.valueOf(longi1));
		
		

		float distance = loc.distanceTo(locationB);
		
          try{
			
			textLong.setText(" "+" Longitude= "+ loc.getLongitude());
			if(distance>10.00)
			{
				if(mobile1!="")
			   sendSMS(mobile1,"Message from Location Tracker");
				else
					  sendSMS(mobile2,"Message from Location Tracker");
					
				
			}
			}
			catch(Exception e){
				
				Toast.makeText(MainActivity.this, "Exception " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				
			}
		
		
		
		
		//sendSMS("+919729745889","hello nv....");
		}
		
		
		
		private void sendSMS(String phoneNumber, String message) {
			SmsManager sms=SmsManager.getDefault();
			sms.sendTextMessage(phoneNumber, null, message, null, null);
				
			}

		/*private void sendSMS(String phoneNumber, String message) {
			//SmsManager sms=SmsManager.getDefault();
			//sms.sendTextMessage(phoneNumber, null, message, null, null);
				
		}*/

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(), "GPS disabled",Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_SHORT).show();
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		//showUserSettings();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_settings:
			Intent i = new Intent(this, UserSettingActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			break;

		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			showUserSettings();
			break;

		}

	}

	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		StringBuilder builder = new StringBuilder();

		builder.append("\n Mobile1: "
				+ sharedPrefs.getString("prefmobile1", "NULL"));

		builder.append("\n Mobile2: "
				+ sharedPrefs.getString("prefmobile2", "NULL"));


		builder.append("\n Range: "
				+ sharedPrefs.getString("prefrange", "NULL"));
		
		builder.append("\n Home Longitude:"
				+ sharedPrefs.getString("preflongi", "NULL"));
				
		builder.append("\n Home Latitude:"
				+ sharedPrefs.getString("preflati", "NULL"));
		
	
		TextView settingsTextView = (TextView) findViewById(R.id.textUserSettings);

		settingsTextView.setText(builder.toString());
	}

}
