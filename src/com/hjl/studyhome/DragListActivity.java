package com.hjl.studyhome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hjl.studyhome.utils.DBManager;
import com.hjl.studyhome.utils.DragListView;
import com.hjl.studyhome.utils.Lanmu;


public class DragListActivity extends Activity {
    
    private static List<String> list = new ArrayList<String>();
    private DragListAdapter adapter = null;
    private DBManager dbmanager;
//    private ServiceInterface si;
//    private Handler  handler = CreateHandler();
    
    private List<Lanmu> list_lanmu;
    private List<Map<String,String>> list_data = new ArrayList<Map<String,String>>();
    private Button back;
    private TextView title;
    private  DragListView dragListView;
    private ProgressDialog mpDialog;
    
    private String backdata;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_title_edit_activity);
        dbmanager = new DBManager(this);
//        si = new ServiceInterface();
        init();
//        getdata();
        dragListView = (DragListView)findViewById(R.id.drag_list);
       
    }
//    @Override
//	public void finish() {
//		// TODO Auto-generated method stub
//    	/*list_lanmu = getChangeA(list);
//    	dbmanager.deleteDataTable("lanmu"); 
//    	//将变更后的A列表存放到lanmu表中
//    	if(list_lanmu.size()>0 && list_lanmu != null){
//    		for(Lanmu lanmu : list_lanmu){
//    			dbmanager.addProduct(lanmu);
//    		}
//    	}
//    	//将整体变化的栏目列表更新到数据库中
//    	if(list != null && list.size() > 0){
//    		dbmanager.deleteDataTable("totle_lanmu");
//        	for(String a :list){
//        		dbmanager.addTotle_lanmu(a);
//        	}
//    	}
//    	System.out.println("do finish!");*/
//		super.finish();
//	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
        list =dbmanager.getAllData_lanmu();
        for(String lan : list){
        	System.out.println("--------------"+lan);
        }
		adapter = new DragListAdapter(this, list);
	    dragListView.setAdapter(adapter);
		super.onResume();
	}
	private void init(){
    	back = (Button) findViewById(R.id.back);
    	title = (TextView) findViewById(R.id.title_name);
    	
    	/*
		 * dialog 
		 * */
		mpDialog = new ProgressDialog(DragListActivity.this);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
		mpDialog.setIcon(R.drawable.ic_launcher);//设置图标
		mpDialog.setMessage("保存中...");
		mpDialog.setIndeterminate(false);//设置进度条是否为不明确
		mpDialog.setCancelable(true);//设置进度条是否可以按退回键取消
    	
    	title.setText("编辑栏目");
    	back.setOnClickListener(back_click);
    }
    OnClickListener back_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			Intent intent = new Intent();
			setResult(0);
			DragListActivity.this.finish();
		}
	};
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		list_lanmu = getChangeA(list);
    	dbmanager.deleteDataTable("lanmu"); 
    	//将变更后的A列表存放到lanmu表中
    	if(list_lanmu.size()>0 && list_lanmu != null){
    		for(Lanmu lanmu : list_lanmu){
    			dbmanager.addProduct(lanmu);
    		}
    	}
    	//将整体变化的栏目列表更新到数据库中
    	if(list != null && list.size() > 0){
    		dbmanager.deleteDataTable("totle_lanmu");
        	for(String a :list){
        		dbmanager.addTotle_lanmu(a);
        	}
    	}
    	System.out.println("do finish!");
		super.onPause();
	}
	
	/*private Handler CreateHandler(){
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				switch(msg.what){
				case 1:
					mpDialog.dismiss();
					mpDialog.cancel();
					try {
						String data = new String(backdata.getBytes("unicode"),"unicode");
						try {
							JSONArray array = new JSONArray(data);
							for(int i = 0;i<array.length();i++){
								JSONObject o=array.getJSONObject(i);
								String key1=o.getString("key");
								String name1=o.getString("name");
								System.out.println("key1:"+key1+";"+"name1:"+name1);
								JSONArray a2=o.getJSONArray("sub_cate");
								for(int j=0;j<a2.length();j++){
									JSONObject o2=a2.getJSONObject(j);
									String key2=o2.getString("key");
									String name2=o2.getString("name");
									System.out.println("key2:"+key2+";"+"name2:"+name2);
									JSONObject o3=o2.getJSONObject("sub_cate");
									String key=key2+"1";
									String sub1=o3.getString(key);
									String sub2=o3.getString(key2+"2");
									System.out.println("sub1:"+sub1+";"+"sub2:"+sub2);
								}
								System.out.println("*******************************************************");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 2:
					mpDialog.dismiss();
					mpDialog.cancel();
					String error = si.getError(backdata);
					Toast.makeText(DragListActivity.this, error, Toast.LENGTH_SHORT).show();
					break;
				case 3:
					mpDialog.dismiss();
					mpDialog.cancel();
					Toast.makeText(DragListActivity.this, "请求服务器失败！", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		
		return handler;
	}*/
	
	/*private void getdata(){
		new Thread(){
			@Override
			public void run(){
				Message msg=new Message();
				backdata = si.getzixun();
				System.out.println("backdata:"+backdata);
				if(backdata.length() > 0 && !backdata.equals("")){
					msg.what = 1;
					handler.sendMessage(msg);
				}else{
					msg.what=3;
				    handler.sendMessage(msg);
				}
			}
		}.start();
	}*/

	/**
	 * 获取变更后的A栏目列表
	 * @author Administrator
	 *
	 */
	private List<Lanmu> getChangeA(List<String> list){
		List<String> list_get = new ArrayList<String>();
		//存取变更后的A栏目列表
		list_lanmu = new ArrayList<Lanmu>();
		for(String a : list){
			list_get.add(a);
			if(a.equals("金融")){
				break;
			}
		}
		//去除list_get的A标题栏及B标题栏
		list_get.remove(0);
		list_get.remove(list_get.size()-1);
		//存放变更后的A列表
		for(String b : list_get){
			Lanmu lanmu = new Lanmu();
			lanmu.setName(b);
			lanmu.setOrder(null);
			list_lanmu.add(lanmu);
		}
		return list_lanmu;
	}
	
	/**
	 * 解析JSON数据
	 * @author Administrator
	 *
	 */
	
/*	private List<Map<String,String>> get_jsondata(String data){
		try {
			String dat =  new String(data.getBytes("unicode"),"unicode");
			try {
				JSONArray array = new JSONArray(dat);
				for(int i = 0;i<array.length();i++){
					JSONObject o=array.getJSONObject(i);
					String key1=o.getString("key");
					String name1=o.getString("name");
					System.out.println("key1:"+key1+";"+"name1:"+name1);
					JSONArray a2=o.getJSONArray("sub_cate");
					for(int j=0;j<a2.length();j++){
						JSONObject o2=a2.getJSONObject(j);
						String key2=o2.getString("key");
						String name2=o2.getString("name");
						System.out.println("key2:"+key2+";"+"name2:"+name2);
						JSONObject o3=o2.getJSONObject("sub_cate");
						String key=key2+"1";
						String sub1=o3.getString(key);
						String sub2=o3.getString(key2+"2");
						System.out.println("sub1:"+sub1+";"+"sub2:"+sub2);
					}
					System.out.println("*******************************************************");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		
		
		return list;
	}*/
	
    
    public static class DragListAdapter extends ArrayAdapter<String>{

        public DragListAdapter(Context context, List<String> objects) {
            super(context, 0, objects);
        }
        
        public List<String> getList(){
            return list;
        }
        
        @Override
        public boolean isEnabled(int position) {
            if(CourseActivity.groupKey.contains(getItem(position))){
                //如果是分组标签，返回false，不能选中，不能点击
                return false;
            }
            return super.isEnabled(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            
            View view = convertView;
            if(CourseActivity.groupKey.contains(getItem(position))){
                //如果是分组标签，就加载分组标签的布局文件，两个布局文件显示效果不同
                view = LayoutInflater.from(getContext()).inflate(R.layout.drag_list_item_tag, null);
            }else{
                //如果是正常数据项标签，就加在正常数据项的布局文件
                view = LayoutInflater.from(getContext()).inflate(R.layout.first_title_drag_list_item, null);
            }
            
            TextView textView = (TextView)view.findViewById(R.id.drag_list_item_text);
            textView.setText(getItem(position));
            
            return view;
        }
    }
}