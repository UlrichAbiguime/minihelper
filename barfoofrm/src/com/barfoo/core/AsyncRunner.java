/**
 * AsyncRunner.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-5-9
 */
package com.barfoo.core;
import org.json.JSONException;

import android.os.Looper;

import com.barfoo.app.BarfooError;

/**
 * @author comger
 * 异常接口方案,提供开始状态、运行完成及异常处理
 */
public class AsyncRunner {
	
	//请求的监听接口
    public interface RequestListener {
    	
    	//请求之前
    	public void onReading();
    	
    	//请求完成处理数据
    	public void onRequesting() throws BarfooError, JSONException;
    	
    	//处理完成
    	public void onDone();
        
    	//请求处理时的异常
        public void onBarfooError(BarfooError e);

    }
 
    
    //快速入口
    public static void HttpGet(final RequestListener listener){
    	listener.onReading(); //准备请求
    	new Thread() {
            @Override 
            public void run() {
            	Looper.prepare();
            	try {
            		listener.onRequesting();
				} catch (BarfooError e) {
					listener.onBarfooError(e);
				} catch (JSONException e) {
					listener.onBarfooError(new BarfooError("请求解析异常:"+e.getMessage()));
				}
                Looper.loop();
            }
        }.start();
        listener.onDone();

    }
}
