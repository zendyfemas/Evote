package com.evote.main;

import com.evote.model.Calon;
import com.evote.parser.Constant;
import com.evote.parser.DrawableManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ProfileActivity extends Activity{
	Calon calon;
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.profile);
		calon=Constant.selectedCalon;
		DrawableManager manager=new DrawableManager();
		ImageView photo=(ImageView)findViewById(R.id.photo);
		manager.fetchDrawableOnThread(Constant.urlpict+calon.getPhoto(),photo);
		TextView nim=(TextView)findViewById(R.id.nim);
		nim.setText(calon.getNim());
		TextView nama=(TextView)findViewById(R.id.nama);
		nama.setText(calon.getNama());
		TextView angkatan=(TextView)findViewById(R.id.angkatan);
		angkatan.setText(calon.getAngkatan());
		TextView visi=(TextView)findViewById(R.id.visi);
		visi.setText(calon.getVisi());
		String[] misi=calon.getMisi().split("-");
		TableLayout misiTabel=(TableLayout)findViewById(R.id.misi);
		for(String m:misi){
			TableRow row=new TableRow(getApplicationContext());
			TextView isi=new TextView(getApplicationContext());
			isi.setText("- "+m);
			row.addView(isi);
			misiTabel.addView(row);
		}
	}
	
}
