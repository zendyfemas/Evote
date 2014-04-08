package com.evote.main;

import com.evote.main.VoteActivity.GetData;
import com.evote.parser.Constant;
import com.evote.parser.ExecuteStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AktivasiActivity extends Activity{
	public void onCreate(Bundle Save){
		super.onCreate(Save);
		setContentView(R.layout.aktivasi);
		Button send=(Button)findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView email=(TextView)findViewById(R.id.email);
				String[] params=new String[1];
				params[0]=email.getText().toString();
				new Aktivasi().execute(params);
			}
		});
		Button cancel=(Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), MenuActivity.class));
				finish();
			}
		});
	}
	public class Aktivasi extends AsyncTask<String, Void, Void>{
		  ProgressDialog dialog;
		  String hasil="";
		  String email;
			@Override
			protected void onPreExecute() {
				Toast.makeText(getApplicationContext(), "Sending...", Toast.LENGTH_LONG).show();
			}
			
			

			@Override
			protected void onPostExecute(Void unused) {
				//setContentView(R.layout.adapter);
			   if(hasil.equals("")){
				   Toast.makeText(getApplicationContext(), "Email tidak terdaftar", Toast.LENGTH_LONG).show();
			   }else{
				   Toast.makeText(getApplicationContext(), "Mohon tunggu sms dari server", Toast.LENGTH_LONG).show();
			       SMS sms=new SMS(getApplicationContext());
			       sms.sendSMS(hasil.split("-")[0], "email : "+email+" password : "+hasil.split("-")[1]);
			       startActivity(new Intent(getApplicationContext(), MenuActivity.class));
			       finish();
			   }
		       
			}

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				email=params[0];
				hasil=ExecuteStream.execute(Constant.url+"aktivasi.php?email="+params[0]);
				return null;
			}

		  
	  }
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(), MenuActivity.class));
		finish();
	}
}
