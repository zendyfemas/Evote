package com.evote.main;

import com.evote.parser.Constant;
import com.evote.parser.ExecuteStream;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
public class LoginActivity extends Activity {
	String npm;
	TextView gagal;
	String urlLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		gagal=(TextView)findViewById(R.id.gagal);
		Button b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText id=(EditText)findViewById(R.id.userid);
				EditText pass=(EditText)findViewById(R.id.password);
				urlLogin=Constant.url+"login.php?username="+id.getText().toString()+"&password="+pass.getText().toString();
				npm=id.getText().toString();
				new GetData().execute();
			}
		});
	}
	public void onBackPressed(){
		startActivity(new Intent(getApplicationContext(), MenuActivity.class));
		finish();
	}
	
	public class GetData extends AsyncTask<Void, Void, Void>{
		  ProgressDialog dialog;
		  String hasil="";
			@Override
			protected void onPreExecute() {
				dialog=ProgressDialog.show(LoginActivity.this, "Login", "Loging In...");
				dialog.setCancelable(true);
				dialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface arg0) {
						GetData.this.cancel(true);
					}
				});
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				hasil=ExecuteStream.execute(urlLogin);
				Log.d("hasillogin",hasil);
				
			      
				return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				//setContentView(R.layout.adapter);
				dialog.dismiss();
				if(hasil.contains("sukses")){
					Constant.idUser=hasil.split("-")[1];
					if(hasil.split("-")[2].equals("0")){
						Constant.pilih=false;
					}else{
						Constant.pilih=true;
					}
					startActivity(new Intent(LoginActivity.this, UserActivity.class));
		        	finish();
				}else{
					gagal.setVisibility(View.VISIBLE);
				}
		       
		       
			}

		  
	  }
	  
}
