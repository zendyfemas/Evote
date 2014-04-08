package com.evote.main;

import com.evote.chart.ProjectStatusChart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class UserActivity extends Activity{
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.menu);
		LinearLayout login=(LinearLayout)findViewById(R.id.menuvote);
		login.setOnClickListener(listener);
		LinearLayout lapor=(LinearLayout)findViewById(R.id.menulapor);
		lapor.setOnClickListener(listener);
		LinearLayout statistik=(LinearLayout)findViewById(R.id.menustatistik);
		statistik.setOnClickListener(listener);
		LinearLayout logout=(LinearLayout)findViewById(R.id.menulogout);
		logout.setOnClickListener(listener);
	}
	public OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.menuvote:
				Intent login=new Intent(UserActivity.this, VoteActivity.class);
				startActivity(login);
				finish();
				break;
			case R.id.menulapor:
				Intent lapor=new Intent(UserActivity.this, LaporActivity.class);
				startActivity(lapor);
				finish();
				break;
			case R.id.menustatistik:
				//Intent lapor=new Intent(UserActivity.this, LaporActivity.class);
				startActivity(new ProjectStatusChart().execute(getApplicationContext()));
				
				break;
			case R.id.menulogout:
				//Intent lapor=new Intent(UserActivity.this, LaporActivity.class);
				Intent menu=new Intent(UserActivity.this, MenuActivity.class);
				startActivity(menu);
				finish();
				break;

			default:
				break;
			}
		}
	};
	public void onBackPressed() {};
}
