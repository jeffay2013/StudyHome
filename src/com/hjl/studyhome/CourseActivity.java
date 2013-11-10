package com.hjl.studyhome;

import java.util.ArrayList;
import java.util.List;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hjl.studyhome.utils.DBManager;
import com.hjl.studyhome.utils.Lanmu;
import com.hjl.studyhome.utils.MyListView;

public class CourseActivity extends Activity {
	
	private LinearLayout text_layout;
	private List<ToggleButton> textview_list;
	private Button tianjia;
	private ToggleButton first_one;
	private ListView otherlistview;
	private ScrollView scrollview ;
	private MyListView mylistview;
	private ViewFlow viewFlow; // 进行图片轮播的viewFlow
    private Button back;
    private TextView title;
    
    private SharedPreferences sp;
//	View header;
	
	private List<String> start_list = null;
    public static List<String> groupKey= new ArrayList<String>();
    private List<String> navList = new ArrayList<String>();
    private List<String> moreList = new ArrayList<String>();
	private DBManager dbmanager;
	private List<Lanmu> list_lanmu = new ArrayList<Lanmu>();
    private String kind;
    
    private CourseAdapter otheradapter;
    
//    private ServiceInterface si;
    private int count;
    
    private String programePoint = "An Intent is an object that provides runtime binding between separate components (such as two activities). ";
    private String financialPoint = "金融学（Finance）是研究价值判断和价值规律的学科。";
    private String englishPoint = "BEIJING - The Communist Party of China's top discipline watchdog is collecting anti-corruption cartoons nationwide and plans an exhibition .";
    private String datas[] = {programePoint,financialPoint,englishPoint};
//    public int imgs[] = {R.drawable.img1,R.drawable.img2,R.drawable.img3};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
		sp = this.getSharedPreferences("location", Context.MODE_PRIVATE);
		first_one = (ToggleButton) View.inflate(CourseActivity.this, R.layout.first_radiobutton, null);
		dbmanager = new DBManager(this);
//		si = new ServiceInterface();
		initData();
		init();
	}
	
	private void init(){
		back = (Button) findViewById(R.id.back);
    	title = (TextView) findViewById(R.id.title_name);
		text_layout = (LinearLayout) findViewById(R.id.ht_first_title_bar_text_layout);
		tianjia = (Button) findViewById(R.id.first_title_edit_btn);
		otherlistview = (ListView) findViewById(R.id.first_other_lv);
		scrollview = (ScrollView) findViewById(R.id.scrollview);
		mylistview = (MyListView) findViewById(R.id.first_lv);
		
		count = sp.getInt("count", 0);
        
		title.setText("课程");
    	back.setOnClickListener(listener);
    	if(count == 0){
    		Lanmu lanmu;
    		lanmu = new Lanmu();
    		lanmu.setName("程序");
    		lanmu.setOrder("0");
    		list_lanmu.add(lanmu);
    		
    		lanmu = new Lanmu();
    		lanmu.setName("金融");
    		list_lanmu.add(lanmu);
    		
    		lanmu = new Lanmu();
    		lanmu.setName("英语");
    		list_lanmu.add(lanmu);

    	}else{
    		list_lanmu = dbmanager.getAllData();
    	}
		Editor editor = sp.edit();
		editor.putInt("count", 1);
		editor.commit();
		tianjia.setOnClickListener(listener);
		if(list_lanmu.size()>0 && list_lanmu!=null){
			ChushihuaView();
		}
		
	}
	
	
	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent;
			switch (v.getId()) {
			case R.id.first_title_edit_btn:
				intent = new Intent(CourseActivity.this,DragListActivity.class);
				startActivityForResult(intent,1003);
				break;
			case R.id.back:
				CourseActivity.this.finish();
				break;
			default:
				break;
			}
		}};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
//		list_lanmu = dbmanager.getAllData();
		//添加重点项
		/*if(list_lanmu.size()>0 && list_lanmu!=null){
			textview_list = getDataView();
			text_layout.removeAllViews();
			first_one.setText("重点");
			text_layout.addView(first_one);
			otherlistview.setVisibility(View.GONE);
			scrollview.setVisibility(View.VISIBLE);
			
			viewFlow = (ViewFlow) findViewById(R.id.viewflow);// 获得viewFlow对象
			viewFlow.setAdapter(new ViewFlowAdapter(getApplicationContext())); // 对viewFlow添加图片
			viewFlow.setmSideBuffer(3);
			CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic); // viewFlow下的indic
			viewFlow.setFlowIndicator(indic);
			viewFlow.setTimeSpan(4500);
			viewFlow.setSelection(3 * 1000); // 设置初始位置
			viewFlow.startAutoFlowTimer(); // 启动自动播放
			
			first_one.setChecked(true);
			otheradapter = new Zixun_Other_Adapter(datas, Zixun_Activity.this, "重点");
			mylistview.setAdapter(otheradapter);
			first_one.setOnClickListener(first_listener);
			for(ToggleButton text : textview_list){
				text_layout.addView(text);
			}
		}*/
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1003:
			list_lanmu = dbmanager.getAllData();
			//添加重点项
			if(list_lanmu.size()>0 && list_lanmu!=null){
				ChushihuaView();
			}
			break;

		default:
			break;
		}
	}
	/**
	 * 初始化View
	 */
	private void ChushihuaView(){
		textview_list = getDataView();
		text_layout.removeAllViews();
		first_one.setText("重点");
		text_layout.addView(first_one);
		otherlistview.setVisibility(View.GONE);
		scrollview.setVisibility(View.VISIBLE);
		
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);// 获得viewFlow对象
		viewFlow.setAdapter(new ViewFlowAdapter(getApplicationContext())); // 对viewFlow添加图片
		viewFlow.setmSideBuffer(3);
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic); // viewFlow下的indic
		viewFlow.setFlowIndicator(indic);
		viewFlow.setTimeSpan(4500);
		viewFlow.setSelection(3 * 1000); // 设置初始位置
		viewFlow.startAutoFlowTimer(); // 启动自动播放
		
		first_one.setChecked(true);
		otheradapter = new CourseAdapter(datas, CourseActivity.this, "重点");
		mylistview.setAdapter(otheradapter);
		first_one.setOnClickListener(first_listener);
		for(ToggleButton text : textview_list){
			text_layout.addView(text);
		}
	}
	
	private List<ToggleButton> getDataView(){
		List<ToggleButton> list_view = new ArrayList<ToggleButton>();
		for(Lanmu lanmu :list_lanmu){
			ToggleButton textview = (ToggleButton) View.inflate(CourseActivity.this, R.layout.first_radiobutton, null);
			textview.setTextSize(16);
			textview.setTextColor(CourseActivity.this.getResources().getColor(R.color.black));
			textview.setGravity(Gravity.CENTER);
			textview.setText(lanmu.getName());
			textview.setOnClickListener(btn_listener);
			list_view.add(textview);
		}
		return list_view;
	}
	
	//ToggleButton点击事件
	OnClickListener btn_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			first_one.setChecked(true);
			otherlistview.setVisibility(View.VISIBLE);
			scrollview.setVisibility(View.GONE);
			for(ToggleButton chose : textview_list){
				if(v==chose){
				kind = String.valueOf(textview_list.indexOf(chose));
				Lanmu data = list_lanmu.get(Integer.valueOf(kind));
				otheradapter = new CourseAdapter(datas, CourseActivity.this, data.getName());
				otherlistview.setAdapter(otheradapter);
				System.out.println("kind:"+kind);
					chose.setChecked(true);
					first_one.setChecked(false);
				}else{
					chose.setChecked(false);
					first_one.setChecked(false);
				}
			}
		}
	};
	
	OnClickListener first_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			first_one.setChecked(true);
			otherlistview.setVisibility(View.GONE);
			scrollview.setVisibility(View.VISIBLE);
			otheradapter = new CourseAdapter(datas, CourseActivity.this, "重点");
			mylistview.setAdapter(otheradapter);
			
			for(ToggleButton chose : textview_list){
					chose.setChecked(false);
			}
		}
	};
	
	
	/**
	 * 初始化数据
	 */
	 public void initData(){
	        //数据结果
		 start_list = new ArrayList<String>();
	        
	        //groupKey存放的是分组标签
	        groupKey.add("程序");
	        groupKey.add("金融");
	        groupKey.add("英语");

	        navList.add("java开发");
	        navList.add("andriod开发");
	        navList.add("perl开发");
	        
	        start_list.add("程序");
	        start_list.addAll(navList);
	        
	        moreList.add("金融市场");
	        moreList.add("金融机构");
	        moreList.add("中央银行与货币政策操作");
	        moreList.add("国际金融与货币政策");
	        moreList.add("货币政策");
	        
	        start_list.add("金融");
	        start_list.addAll(moreList);
	        
	        if(dbmanager.getAllData_lanmu() != null && dbmanager.getAllData_lanmu().size() > 0 ){
	        	Toast.makeText(CourseActivity.this, "数据库里面有数据", 3).show();
	        }else{
	        	Toast.makeText(CourseActivity.this, "数据库中没有数据", 1).show();
	        	for(String str : start_list){
		        	dbmanager.addTotle_lanmu(str);
		        }
	        }
}
}