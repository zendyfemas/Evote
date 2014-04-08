package com.evote.adapter;

import java.util.ArrayList;

import com.evote.main.ProfileActivity;
import com.evote.main.R;
import com.evote.model.Calon;
import com.evote.parser.Constant;
import com.evote.parser.DrawableManager;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CalonAdapter extends ArrayAdapter<Calon>{
	Context mCon;
	ArrayList<Calon> mList;
	int resource;
	DrawableManager manager;
	Activity act;
	public CalonAdapter(Context context, int textViewResourceId, ArrayList<Calon> objects,Activity act) {
		super(context, textViewResourceId);
		mList=objects;
		resource=textViewResourceId;
		mCon=context;
		manager=new DrawableManager();
		this.act=act;
		// TODO Auto-generated constructor stub
	}
	public int getCount() {
        return mList.size();
    }

    public Calon getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
    	ContactsViewHolder viewHolder;
    	 
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(resource, parent, false);
 
            viewHolder = new ContactsViewHolder();
            viewHolder.txName = (TextView) convertView.findViewById(R.id.nama);
            viewHolder.txNo = (TextView) convertView.findViewById(R.id.no);
            viewHolder.txHasil = (TextView) convertView.findViewById(R.id.hasilvote);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.photo);
            viewHolder.profile=(Button)convertView.findViewById(R.id.profile);
            viewHolder.vote=(Button)convertView.findViewById(R.id.vote);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ContactsViewHolder) convertView.getTag();
        }
 
        Calon calon= mList.get(position);
        if (calon != null) {
            viewHolder.txName.setText(calon.getNama());
            viewHolder.txNo.setText(calon.getNo_urut());
            viewHolder.txHasil.setText("Vote : "+calon.getHasil());
            viewHolder.profile.setTag(calon.getId_calon());
            viewHolder.vote.setTag(calon.getId_calon());
            if(Constant.pilih){
            	viewHolder.vote.setEnabled(false);
            }
            manager.fetchDrawableOnThread(Constant.urlpict+calon.getPhoto(), viewHolder.image);
            viewHolder.profile.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Constant.selectedCalon=getItem(position);
					Intent i=new Intent(getContext(), ProfileActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mCon.startActivity(i);
					
				}
			});
            viewHolder.vote.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    
					act.showDialog(position);
				}
			});
        }
        return convertView;
    }
    static class ContactsViewHolder {
        TextView txName;
        TextView txNo;
        TextView txHasil;
        ImageView image;
        Button vote;
        Button profile;
    }
}
