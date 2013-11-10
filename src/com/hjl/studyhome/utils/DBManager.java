package com.hjl.studyhome.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {
	/**
	 * 
	 * jingjia.db
	 * [lanmu] 表
	 * ------------------------------
	 * name          VARCHAR        |
	 * order	     VARCHAR        |
	 * ------------------------------
	 * 
	 * @param context
	 */
	private Context context;
	public DBManager(Context context) {
		this.context=context;
		SQLiteDatabase  db = context.openOrCreateDatabase("jingjia.db", Context.MODE_PRIVATE, null);
		db.execSQL(
				"CREATE TABLE IF NOT EXISTS lanmu" + 
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name VARCHAR, " +
				"num VARCHAR)" 
				);
		db.execSQL(
				"CREATE TABLE IF NOT EXISTS totle_lanmu" + 
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"item VARCHAR)" 
				);
		db.close();
	}
	/**
	 * 检查order是否在数据库
	 * 
	 * @return "true"数据库中有此数据，不用插入数据库
	 *         "false"数据库中没有此数据，可以正常插入数据
	 */
	public boolean checkProductById(String order){
		SQLiteDatabase  db = context.openOrCreateDatabase("jingjia.db", Context.MODE_PRIVATE, null);
		boolean state = false;
		
		Cursor cursor = db.rawQuery("SELECT * FROM lanmu WHERE num = '"+order+"'", null);
		
		if(cursor.getCount() != 0){
			state = true;
		}else{
			state = false;
		}
		cursor.close();
		db.close();
		return state;
	}
	
	/**
	 * 添加一条数据到数据库
	 * @param news
	 */
	public void addProduct(Lanmu lanmu){
		SQLiteDatabase  db = context.openOrCreateDatabase("jingjia.db", Context.MODE_PRIVATE, null);
    	db.execSQL("INSERT INTO lanmu VALUES(null,?, ?)", new Object[]{
    			lanmu.getName(),
    			lanmu.getOrder()
    			});
    	db.close();
	}
	/**
	 * 获取所有的数据
	 */
	public List<Lanmu> getAllData(){
		List<Lanmu> list_lanmu = new ArrayList<Lanmu>();
		SQLiteDatabase  db = context.openOrCreateDatabase("jingjia.db", Context.MODE_PRIVATE, null);     
		String col[] = { 
				"name",
				"num"
				};  
		Cursor cur = db.query("lanmu", col, null, null, null, null, null);    
		cur.moveToFirst();    
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Lanmu lanmu = new Lanmu();
			lanmu.setName(cur.getString(0));
			lanmu.setOrder(cur.getString(1));
			list_lanmu.add(lanmu);
		}
		cur.close();
		db.close();  
		return list_lanmu;
	}
	/**
	 * 根据索引删除表中的数据
	 * @param order
	 */
	public void deleteItemById(String order){
		SQLiteDatabase  db = context.openOrCreateDatabase("jingjia.db", Context.MODE_PRIVATE, null);
    	db.execSQL("DELETE FROM lanmu WHERE num = '"+ order +"'");
    	db.close();
	}
	/**
	 * 根据表名删除表中的数据
	 * @param order
	 */
	public void deleteDataTable(String tablename){
		SQLiteDatabase  db = context.openOrCreateDatabase("jingjia.db", Context.MODE_PRIVATE, null);
    	db.execSQL("DELETE FROM '"+ tablename +"'");                              
    	db.close();
	}
	/**
	 * 向totle_lanmu表中添加数据
	 */
	public void addTotle_lanmu(String str){
		SQLiteDatabase  db = context.openOrCreateDatabase("jingjia.db", Context.MODE_PRIVATE, null);
    	db.execSQL("INSERT INTO totle_lanmu VALUES(null, ?)", new Object[]{
    			str
    			});
    	db.close();
	}
	
	/**
	 * 获取totle_lanmu表中的数据
	 */
	public List<String> getAllData_lanmu(){
		List<String> list_lanmu = new ArrayList<String>();
		SQLiteDatabase  db = context.openOrCreateDatabase("jingjia.db", Context.MODE_PRIVATE, null);     
		String col[] = { 
				"item"
				};  
		Cursor cur = db.query("totle_lanmu", col, null, null, null, null, null);    
		cur.moveToFirst();    
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			list_lanmu.add(cur.getString(0));
		}
		cur.close();
		db.close();  
		return list_lanmu;
	}
	/******************获取全部数据有问题************************/

}
