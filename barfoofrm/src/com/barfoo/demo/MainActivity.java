/**
 *
 *@Class MainActivity.java
 *@author zxy
 *@date 2012-2012-9-13-下午5:07:25
 *@Description 说明
 */
package com.barfoo.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.barfoo.R;
import com.barfoo.core.ImageLoad;

public class MainActivity extends Activity{
	private ImageView iv_pic1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		iv_pic1=(ImageView)findViewById(R.id.iv_pic1);
		ImageLoad load=new ImageLoad();
		//http://s1.it.itc.cn/z/forum_attachment/day_110130/110130003081a87a9881d094f9.jpg
		//http://picm.bbzhi.com/fengjingbizhi/meilitianyuanfengjingsheying/nature_sky-farm_19478_m.jpg", iv_pic1
		load.loadBitmap("http://s1.it.itc.cn/z/forum_attachment/day_110130/110130003081a87a9881d094f9.jpg", iv_pic1, 100);
	}
}
