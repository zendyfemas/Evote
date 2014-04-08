package com.evote.main;

import java.util.ArrayList;

import com.evote.adapter.CalonAdapter;
import com.evote.adapter.HasilAdapter;
import com.evote.main.VoteActivity.GetData;
import com.evote.model.Calon;
import com.evote.parser.Constant;
import com.evote.parser.ExecuteStream;
import com.evote.parser.ParsedCalonDataSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class HasilActivity extends Activity{
	GridView list;
	ArrayList<Calon> listitem;
	
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.hasil);
		list=(GridView)findViewById(R.id.gridView1);
		
		new GetData().execute();
	}
	public class GetData extends AsyncTask<Void, Void, Void>{
		  ProgressDialog dialog;
		  String hasil="";
		  String lastvote;
			
			@Override
			protected void onPreExecute() {
				dialog=ProgressDialog.show(HasilActivity.this, "Vote", "Loading data...");
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
					final AlertDialog alertDialog = new AlertDialog.Builder(HasilActivity.this).create();
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
				HasilAdapter adapter=new HasilAdapter(getApplicationContext(), R.layout.hasil_item, listitem);
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(), MenuActivity.class));
		finish();
	}
}
