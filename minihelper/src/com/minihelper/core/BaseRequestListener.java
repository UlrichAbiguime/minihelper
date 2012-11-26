package com.minihelper.core;

import org.json.JSONException;

import com.minihelper.core.AsyncRunner.RequestListener;

public class BaseRequestListener implements RequestListener{

	@Override
	public void onReading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRequesting() throws HttpRequstError, JSONException {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDone(){
		// TODO Auto-generated method stub
	}

	@Override
	public void onHttpRequstError(HttpRequstError e) {
		// TODO Auto-generated method stub
		
	}
	
}