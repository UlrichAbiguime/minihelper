/**
 * INetSolution.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-11-5
 */
package com.minihelper.core;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author comger
 * 网络接口解决方案
 * 功能列表
 * 	1. 获取可选网络方案 (支持主从方案),设置应用网络方案为主方案
 * 	2. 在方案不可用的时候，设置应用网络为备选方案，并启动主方案检查服务，如果检查到主方案恢复正常,设置应用网络方案为主方案
 * 	3. 在备选方案通信时，提前检查主方案是否可用,如可用，设置应用网络方案为主方案
 */ 
public interface INetSolution {
	
	//获取可用网络方案接口Host
	public String getApiHost();
	
	//更换网络方案
	public void changeSolution();
	
	//是否为默认方案
	public boolean isDefault();
	
	//获取检测间隔时间 单位为分钟
	public int getSpanTime();
	
	//加载解决方案
	public void loadSolution() throws JSONException, HttpRequstError; 
	
	//保存解决方案
	public void saveSolution(JSONArray array);
}
