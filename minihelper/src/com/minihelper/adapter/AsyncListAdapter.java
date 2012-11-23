package com.minihelper.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minihelper.R;

public class AsyncListAdapter extends BaseAdapter {

	Context mContext;
	JSONArray mJsonArray;
	public int mCount = 10;
	private HashMap<Integer, Bitmap> cacheMap = new HashMap<Integer, Bitmap>();
	private ArrayList<AlbumInfo> albums = new ArrayList<AlbumInfo>();

	public AsyncListAdapter(Context context, JSONArray jsonArray) {
		this.mContext = context;
		this.mJsonArray = jsonArray;
		ListGetJson();
	}

	private void ListGetJson() {
		try {
			int length = mJsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject jsonObj = (JSONObject) mJsonArray.get(i);
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

	class MyAsyncTask extends AsyncTask<URL, Void, Bitmap> {
		private ImageView mImageView;
		private int mPosition;

		public MyAsyncTask(int position, ImageView imageView) {
			this.mImageView = imageView;
			this.mPosition = position;
		}

		@Override
		protected Bitmap doInBackground(URL... params) {
			Bitmap bitmap = downloadImg(params[0]);
			cacheMap.put(mPosition, bitmap);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			mImageView.setImageBitmap(result);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 缓存
		LinearLayout layout;
		ViewHolder holder = null;
		if (null == convertView) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			layout = (LinearLayout) inflater.inflate(R.layout.listview, null);
			ImageView imageView = (ImageView) layout.findViewById(R.id.imageView1);
			TextView textView = (TextView) layout.findViewById(R.id.textView1);
			holder = new ViewHolder();
			holder.mImageView = imageView;
			holder.mTextView = textView;
			layout.setTag(holder);
		} else {
			layout = (LinearLayout) convertView;
			holder = (ViewHolder) layout.getTag();
		}

		holder.mImageView.setImageResource(R.drawable.load);
		// 设置服务端下载的图片
		if (albums.size() > position) {
			AlbumInfo albumInfo = albums.get(position);
			holder.mTextView.setText(albumInfo.mName);
		} else {
			holder.mTextView.setText("default");
		}

		if (cacheMap.containsKey(position)) {
			Bitmap bitmap = cacheMap.get(position);
			Log.d("listview", "user cache: " + position);
			holder.mImageView.setImageBitmap(bitmap);
		} else {
			Log.d("listview", "no cache: " + position);
			try {
				MyAsyncTask myAsyncTask = new MyAsyncTask(position, holder.mImageView);
				if (albums.size() > position) {
					AlbumInfo albumInfo = albums.get(position);
					URL url = new URL(albumInfo.mImage);
					myAsyncTask.execute(url);
				}
			} catch (MalformedURLException e) {
				Log.i("error", e.getMessage());
			}
		}
		return layout;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return mCount;
	}

	class ViewHolder {
		ImageView mImageView;
		TextView mTextView;
	}

	/**
	 * 下载指定URL地址的图片
	 * 如果url者错误，返回空
	 * @param strUrl
	 * @return
	 */
	protected Bitmap downloadImg(URL url) {
		try {
			URLConnection openConnection = url.openConnection();
			InputStream is = openConnection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			return bitmap;
		} catch (MalformedURLException e) {
			Log.i("error", e.getMessage());
		} catch (IOException e) {
			Log.i("error", e.getMessage());
		}
		return null;
	}

}
