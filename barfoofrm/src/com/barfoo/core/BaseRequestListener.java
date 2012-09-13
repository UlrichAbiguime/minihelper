package com.barfoo.core;

import org.json.JSONException;

import com.barfoo.app.BarfooError;
import com.barfoo.core.AsyncRunner.RequestListener;

public class BaseRequestListener implements RequestListener{

	@Override
	public void onReading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRequesting() throws BarfooError, JSONException {
		// TODO Auto-generated method stub
	}

	@Override
	public void onBarfooError(BarfooError e) {
	}
	
	@Override
	public void onDone(){
		
	}
	
	
}