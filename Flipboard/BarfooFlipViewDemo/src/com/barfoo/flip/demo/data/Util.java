package com.barfoo.flip.demo.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {

	/**
	 * 判断当前的关键字是否为空
	 * @param obj
	 * @param str(关键字)
	 * @return
	 * @throws JSONException
	 */
	public static  boolean isJsonNull(JSONObject obj,String str) throws JSONException{
		if(obj.getString(str).length()>0 && obj.getString(str)!=null){
			return true;
		}else{
			return false;
		}
	}
}
