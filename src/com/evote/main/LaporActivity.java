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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class LaporActivity extends Activity{
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.lapor);
		Button send=(Button)findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView lapor=(TextView)findViewById(R.id.laporan);
				String[] params=new String[2];
				params[0]=lapor.getText().toString();
				params[1]=Constant.idUser;
				if(!lapor.getText().toString().equals("")){
					new Lapor().execute(params);
				}else{
					Toast.makeText(getApplicationContext(), "Mohon isi form..", Toast.LENGTH_LONG).show();
				}
			}
		});
		Button cancel=(Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LaporActivity.this, UserActivity.class));
				finish();
			}
		});
	}
	public class Lapor extends AsyncTask<String, Void, Void>{
		  ProgressDialog dialog;
		  String hasil="";
		  private String unescape(String description) {
		        return description.replaceAll("\n", "<br>");
			}
			@Override
			protected void onPreExecute() {
				Toast.makeText(getApplicationContext(), "Mengirim Laporan...", Toast.LENGTH_LONG).show();
			}
			
			

			@Override
			protected void onPostExecute(Void unused) {
				//setContentView(R.layout.adapter);
				
		       Toast.makeText(getApplicationContext(), "Laporan Anda sudah terkirim", Toast.LENGTH_LONG).show();
		       startActivity(new Intent(LaporActivity.this, UserActivity.class));
		       finish();
			}

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				ExecuteStream.execute(Constant.url+"lapor.php?laporan="+unescape(params[0])+"&idUser="+params[1]);
				return null;
			}

		  
	  }
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(), UserActivity.class));
		finish();
	}
}
