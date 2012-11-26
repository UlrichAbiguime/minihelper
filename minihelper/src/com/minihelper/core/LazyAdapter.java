package com.minihelper.core;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minihelper.R;

public class LazyAdapter extends BaseAdapter {

	private JSONArray mJsonArray;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private Context mContext;
	private ArrayList<AlbumInfo> albums = new ArrayList<AlbumInfo>();

	public LazyAdapter(Context context, JSONArray jsonArray) {
		mContext = context;
		mJsonArray = jsonArray;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(mContext.getApplicationContext());
		ListGetJson(jsonArray);
	}

	private void ListGetJson(JSONArray jsonArray) {
		try {
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject jsonObj = (JSONObject) jsonArray.get(i);
				AlbumInfo info = new AlbumInfo();
				info.mName = jsonObj.getString("title");
				info.mImage = jsonObj.getString("img");
				albums.add(info);
			}
		} catch (JSONException e) {
			Log.i("jsonerror", e.getMessage());
		}
	}

	class AlbumInfo {
		@Override
		public String toString() {
			return "AlbumInfo [mName=" + mName + ", mImage=" + mImage + "]";
		}

		String mName;
		String mImage;
	}

	public int getCount() {
		return mJsonArray.length();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout;
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			layout = (LinearLayout) inflater.inflate(R.layout.item, null);
			holder.mTextView = (TextView) layout.findViewById(R.id.text);
			holder.mImageView = (ImageView) layout.findViewById(R.id.image);
			layout.setTag(holder);
		} else {
			layout = (LinearLayout) convertView;
			holder = (ViewHolder) layout.getTag();
		}

		AlbumInfo albumInfo = albums.get(position);
		holder.mTextView.setText(albumInfo.mName);
		imageLoader.DisplayImage(albumInfo.mImage, holder.mImageView);

		return layout;
	}

	class ViewHolder {
		ImageView mImageView;
		TextView mTextView;
	}
}