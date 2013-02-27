package com.minihelper;

import com.minihelper.core.DataBase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DataActivity extends Activity implements OnClickListener {
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private DataBase dataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);

		dataBase = new DataBase(this);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			dataBase.CreateTable();
			break;
		case R.id.button2:
			dataBase.dropTable();
			break;
		case R.id.button3:
			dataBase.insertItem();
			break;
		case R.id.button4:
			dataBase.deleteItem();
			break;
		case R.id.button5:
			dataBase.showItems();
			break;
		default:
			break;
		}
	}
}
