/**
 * BarfooError.java
 * barfoo 接口请求异常类描述
 * @user comger
 * @mail comger@gmail.com
 * 2012-3-26
 */
package com.barfoo.app;

import android.util.Log;

public class BarfooError extends Throwable {
	
	private static final long serialVersionUID = 1L;
	
    private String mErrorType;
    private String mFailingUrl;

    /**
     * 简单异常信息提示
     * @param message
     */
    public BarfooError(String message){
    	super(message);
    }
    
    /**
     * Json数据解析出错
     * @param message
     * @param cls 解析目标模型
     */
    public BarfooError(String message, @SuppressWarnings("rawtypes") Class cls) {
        super(message);
        mErrorType = cls.toString();
        Log.i("BarfooError", "when parse json to "+mErrorType + " error is:"+ message);
    }
    
    /**
     * 请求服务出错
     * @param message
     * @param failingUrl 接口地址
     */
    public BarfooError(String message, String failingUrl) {
        super(message);
        mFailingUrl = failingUrl;
        Log.i("BarfooError", "when open url "+failingUrl + " error is:"+ message);
    }
    

    public String getErrorType() {
        return mErrorType;
    }
    
    public String getFailingUrl(){
    	return mFailingUrl;
    }
}
