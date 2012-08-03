/**
 *
 *@Class MainActivity.java
 *@author zxy
 *@date 2012-2012-8-3-下午4:26:51
 *@Description 说明
 */
package com.minihelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		init();
	}
	
	private void init() {
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent TouchAct = new Intent(this, TouchAct.class);
			TouchAct.putExtra("ImageUrl", "http://192.168.1.160:8080/m/getimg?fid=501b30a3194f99763d00007e&token=X3Nlc3Npb25faWQ9Ik1HUTRPVE5oWTJKaVptRmhORGhsTjJFd01EUTVZV1ptWlRneFlUWXpNelE9fDEzNDM5ODQxNjN8MDNlN2UyMGUzNDg5NGE4NzlhMjY3ZTNjYTQ0MDczMjU2MjEyY2VjYyI7IGV4cGlyZXM9U3VuLCAwMiBTZXAgMjAxMiAwODo1NjowMyBHTVQ7IFBhdGg9Lw==&uid=4ff56539b322d01f1b000001&");
			startActivity(TouchAct);
			break;

		default:
			break;
		}
	}
}
