package com.example.syed.navdrawerdemo.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syed.navdrawerdemo.R;
import com.example.syed.navdrawerdemo.models.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<NavDrawerItem> mNavDrawerItems;
	
	private static class ViewHolder{
		ImageView ivIcon;
		TextView tvTitle;
	}
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
        this.mContext = context;
        this.mNavDrawerItems = navDrawerItems;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mNavDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mNavDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.drawer_nav_item, parent, false);
			
			viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
	        
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.ivIcon.setImageResource(mNavDrawerItems.get(position).getIcon());        
		viewHolder.tvTitle.setText(mNavDrawerItems.get(position).getTitle());
		
		
		return convertView;
	}

}
