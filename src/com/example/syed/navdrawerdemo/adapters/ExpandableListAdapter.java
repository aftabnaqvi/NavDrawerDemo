package com.example.syed.navdrawerdemo.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.syed.navdrawerdemo.R;


public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private List<String> mListDataHeader; // header titles
	// child's data in format of header (title), child title
	private Map<String, List<String>> mMapChildData;

	public ExpandableListAdapter() {
		// TODO Auto-generated constructor stub
	}

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			Map<String, List<String>> mapChildData) {
		this.mContext = context;
		this.mListDataHeader = listDataHeader;
		this.mMapChildData = mapChildData;
	}
	
	@Override
	public int getGroupCount() { // header's
		return mListDataHeader.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		String strHeader = mListDataHeader.get(groupPosition);
		
		List<String> child = mMapChildData.get(strHeader);
		if(child != null)
			return child.size();
		
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) { // get it from header...
		return mListDataHeader.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return  mMapChildData.get(mListDataHeader.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String)getGroup(groupPosition);
		
		int childCount = getChildrenCount(groupPosition);
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(childCount == 0)
				convertView = inflater.inflate(R.layout.list_header_no_child, null);
			else
				convertView = inflater.inflate(R.layout.list_header, null);
		}
		
		TextView lblListHeader = null;
		
		if( childCount == 0){
			lblListHeader = (TextView) convertView
					.findViewById(R.id.listHeaderLabelNoChild);
		} else {
			lblListHeader = (TextView) convertView
				.findViewById(R.id.listHeaderLabel);
		}
		
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}
		
		TextView tvChildItem = (TextView) convertView.findViewById(R.id.listChildItemLabel);
		tvChildItem.setText(childText);

			TextView tvCounter = (TextView)convertView.findViewById(R.id.tvCounter);
			if(tvCounter!=null)
				tvCounter.setText("5");
			
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
