package com.hjl.studyhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hjl.studyhome.R;

public class CourseAdapter  extends BaseAdapter {
	
	private String[] data;
	private Context context;
	private String d1;
	public CourseAdapter(String[] data,Context context,String d1){
		this.data = data;
		this.context = context;
		this.d1 = d1;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context).inflate(R.layout.layout_item, null);
		TextView txt = (TextView) convertView.findViewById(R.id.txt);
		txt.setText(data[position]+d1);
		return convertView;
	}

}