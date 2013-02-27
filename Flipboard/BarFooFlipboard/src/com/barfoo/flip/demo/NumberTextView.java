/**
 * Copyright 2013 Barfoo
 *
 * All right reserved
 *
 * Created on 2013-2-27 上午9:31:17
 *
 * @author zxy
 */

package com.barfoo.flip.demo;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class NumberTextView extends TextView{
	private int number;

	public NumberTextView(Context context, int number) {
		super(context);
		setNumber(number);
		setTextColor(Color.BLACK);
		setBackgroundColor(Color.YELLOW);
		setGravity(Gravity.CENTER);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		setText(String.valueOf(number));
	}

	@Override
	public String toString() {
		return "NumberTextView: " + number;
	}
}
