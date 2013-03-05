package com.barfoo.flip.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;
import com.barfoo.formatstyle.BFormateStyle;

public class TestDemo extends Activity {

	Resources res;
	private JSONArray array;
	boolean isScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtil.screenInfo(this);
		res=getResources();
		getData();
		//setContentView(new AFormateStyle(TestDemo.this,null,array));
		setContentView(new BFormateStyle(TestDemo.this,null,array));
	}
	
	public void getData() {
		//title、sourceimage、source、content、titleimage
		
		array = new JSONArray();
		try {
			JSONObject obj0 = new JSONObject();
			obj0.put("title", res.getString(R.string.Adatatitle));
			obj0.put("sourceimage", "sourceimage");
			obj0.put("source",res.getString(R.string.Adatasource));
			obj0.put("content", res.getString(R.string.Adatacontent));
			obj0.put("titleimage","image");
			//obj0.put("titleimage",null);

			JSONObject obj1 = new JSONObject();
			obj1.put("title", res.getString(R.string.Bdatatitle));
			obj1.put("sourceimage", "sourceimage");
			obj1.put("source",res.getString(R.string.Bdatasource));
			obj1.put("content", res.getString(R.string.Bdatacontent));
			obj1.put("titleimage","");

			JSONObject obj2 = new JSONObject();
			obj2.put("title", res.getString(R.string.Cdatatitle));
			obj2.put("sourceimage", "sourceimage");
			obj2.put("source",res.getString(R.string.Cdatasource));
			obj2.put("content", res.getString(R.string.Cdatacontent));
			obj2.put("titleimage","");
			
			
			JSONObject obj3 = new JSONObject();
			obj3.put("title", res.getString(R.string.Ddatatitle));
			obj3.put("sourceimage", "sourceimage");
			obj3.put("source",res.getString(R.string.Ddatasource));
			obj3.put("content", res.getString(R.string.Ddatacontent));
			obj3.put("titleimage","");
			
			
			JSONObject obj4 = new JSONObject();
			obj4.put("title", res.getString(R.string.Edatatitle));
			obj4.put("sourceimage", "sourceimage");
			obj4.put("source",res.getString(R.string.Edatasource));
			obj4.put("content", res.getString(R.string.Edatacontent));
			obj4.put("titleimage","");
			
			
			JSONObject obj5 = new JSONObject();
			obj5.put("title", res.getString(R.string.Fdatatitle));
			obj5.put("sourceimage", "sourceimage");
			obj5.put("source",res.getString(R.string.Fdatasource));
			obj5.put("content", res.getString(R.string.Fdatacontent));
			obj5.put("titleimage","");

			array.put(obj0);
			array.put(obj1);
			array.put(obj2);
			array.put(obj3);
			array.put(obj4);
			array.put(obj5);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
