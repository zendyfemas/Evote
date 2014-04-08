package com.evote.main;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		LinearLayout login=(LinearLayout)findViewById(R.id.menulogin);
		login.setOnClickListener(listener);
		LinearLayout hasil=(LinearLayout)findViewById(R.id.menuhasil);
		hasil.setOnClickListener(listener);
		LinearLayout aktivasi=(LinearLayout)findViewById(R.id.menuaktivasi);
		aktivasi.setOnClickListener(listener);
		LinearLayout about=(LinearLayout)findViewById(R.id.menuabout);
		about.setOnClickListener(listener);
	}
	public OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.menulogin:
				Intent login=new Intent(MenuActivity.this, LoginActivity.class);
				startActivity(login);
				finish();
				break;
			case R.id.menuhasil:
				Intent hasil=new Intent(MenuActivity.this, HasilActivity.class);
				startActivity(hasil);
				finish();
				break;
			case R.id.menuaktivasi:
				Intent akt=new Intent(MenuActivity.this, AktivasiActivity.class);
				startActivity(akt);
				finish();
				break;
			case R.id.menuabout:
				Intent abt=new Intent(MenuActivity.this, AboutActivity.class);
				startActivity(abt);
				finish();
				break;

			default:
				break;
			}
		}
	};
	

}
