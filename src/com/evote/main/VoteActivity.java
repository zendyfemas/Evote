package com.evote.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.evote.adapter.CalonAdapter;
import com.evote.main.LoginActivity.GetData;
import com.evote.model.Calon;
import com.evote.parser.Constant;
import com.evote.parser.ExecuteStream;
import com.evote.parser.ParsedCalonDataSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class VoteActivity extends Activity{
	GridView list;
	ArrayList<Calon> listitem;
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.vote);
		list=(GridView)findViewById(R.id.gridView1);
		new GetData().execute();
	}
	public class GetData extends AsyncTask<Void, Void, Void>{
		  ProgressDialog dialog;
		  String hasil="";
		  String lastvote;
			@Override
			protected void onPreExecute() {
				dialog=ProgressDialog.show(VoteActivity.this, "Vote", "Loading data...");
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
				try {
					listitem=new ParsedCalonDataSet().parse();
					lastvote=ExecuteStream.execute(Constant.url+"lastvote.php");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					dialog.dismiss();
					final AlertDialog alertDialog = new AlertDialog.Builder(getParent()).create();
					alertDialog.setTitle("Eror");
					alertDialog.setMessage("No Conections!");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int which) {
					      // here you can add functions
						   alertDialog.dismiss();
					   }
					});
					alertDialog.show();
					e.printStackTrace();
				}
				
			      
				return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				//setContentView(R.layout.adapter);
				dialog.dismiss();
				CalonAdapter adapter=new CalonAdapter(getApplicationContext(), R.layout.calon_item, listitem,VoteActivity.this);
				list.setAdapter(adapter);
				int jum=0;
				for(Calon c:listitem){
					jum+=Integer.parseInt(c.getHasil());
				}
				TextView jumlah=(TextView)findViewById(R.id.jumlah);
				jumlah.setText("Jumlah Suara Masuk : "+jum+"");
				TextView last=(TextView)findViewById(R.id.lastvote);
				last.setText(lastvote);
		       
			}

		  
	  }
	@Override
	protected Dialog onCreateDialog(final int id) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Vote");
		alertDialog.setMessage("Are You Sure to Vote "+listitem.get(id).getNama()+" ?");
		alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		      // here you can add functions
			   alertDialog.dismiss();
			   String[] params=new String[2];
			   params[0]=listitem.get(id).getId_calon();
			   params[1]=Constant.idUser;
			   new Vote().execute(params);
			   
		   }
		});
		alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				      // here you can add functions
					   alertDialog.dismiss();
					   
				   }
				});
		
		alertDialog.show();
		return super.onCreateDialog(id);
	}
	
	public class Vote extends AsyncTask<String, Void, Void>{
		  ProgressDialog dialog;
		  String hasil="";
			@Override
			protected void onPreExecute() {
				Toast.makeText(getApplicationContext(), "Saving vote...", Toast.LENGTH_LONG).show();
			}
			
			

			@Override
			protected void onPostExecute(Void unused) {
				//setContentView(R.layout.adapter);
				
		       Toast.makeText(getApplicationContext(), "Your Vote Has been Saved", Toast.LENGTH_LONG).show();
		       Constant.pilih=true;
		       new GetData().execute();
			}

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				ExecuteStream.execute(Constant.url+"vote.php?idCalon="+params[0]+"&idUser="+params[1]);
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
