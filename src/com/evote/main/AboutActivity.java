package com.evote.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AboutActivity extends Activity{
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.about);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(), MenuActivity.class));
		finish();
	}
}
