package com.minihelper.core;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;

public class BaseLocation implements LocationListener {
	private LocationManager lm;
	private Context context;
	double longitude = 0.0;
	double latitude = 0.0;
	Location location = null;

	public BaseLocation(Context context) {
		this.context = context;
		InitLocation();
	}

	public void InitLocation() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		String provider = lm.getBestProvider(criteria, true);
		Location location = lm.getLastKnownLocation(provider);
		updataGpsWidthLocation(location);

		/**
		 * 首次进去获取GSP信息 每隔一定的时间通知server去更新一下GPS信息 这个时候屏幕上面应该会出现一个
		 * */
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this);
	}

	/**
	 * 实时更新地理位置经纬度 
	 * update gps information with location
	 */
	public void updataGpsWidthLocation(Location location) {
		if (location != null) {
			longitude = location.getLongitude();// 进度
			latitude = location.getLatitude();// 维度
		} else {
			longitude = 0.0;
			latitude = 0.0;
		}
	}

	/**
	 * 该函数为系统函数，每隔一定的时间便会自动进行调用
	 * */
	@Override
	public void onLocationChanged(Location location) {
		updataGpsWidthLocation(location);
	}

	/**
	 * 如果GPS没有开启就许找
	 * WIFI Get the Location by GPS or WIFI
	 * */
	public Location getLocation() {
		LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		} else if (location == null) {
			location.setLatitude(39.54);
			location.setLongitude(116.23);
		}
		return location;
	}

	/**
	 * 从地址Geopoint取得Address
	 * */
	public String getAddressbyGeoPoint(GeoPoint gp) {

		String strReturn = "";
		try {
			/**
			 * 创建GeoPoint不等于null
			 * */
			if (gp != null) {
				/**
				 * 创建Geocoder对象，用于获得指定地点的地址
				 * */
				Geocoder gc = new Geocoder(context, Locale.getDefault());
				/**
				 * 取出地理坐标经纬度
				 * */
				double geoLatitude = (int) gp.getLatitudeE6() / 1E6;
				double geoLongitude = (int) gp.getLongitudeE6() / 1E6;

				/**
				 * 自经纬度取得地址（可能有多行）
				 * */
				List<Address> lstAddress = gc.getFromLocation(geoLatitude, geoLongitude, 1);
				StringBuilder sb = new StringBuilder();
				/**
				 * 判断地址是否为多行
				 * */
				if (lstAddress.size() > 0) {
					Address adsLocation = lstAddress.get(0);
					for (int i = 0; i < adsLocation.getMaxAddressLineIndex(); i++) {
						sb.append(adsLocation.getAddressLine(i));
					}
				}
				/* 将取得到的地址组合后放到stringbuilder对象中输出用 */
				if (strReturn.length() > 0) {
					strReturn = sb.toString().substring(2, sb.length());
				}
				// else{
				// strReturn="北京市东城区";
				// }
				/**
				 * sb.append(adsLocation.getLocality());市
				 * sb.append(adsLocation.getPostalCode());省
				 * sb.append(adsLocation.getCountryName());国家
				 * */
			}

		} catch (Exception e) {
			Toast.makeText(context, "获取地址失败！", Toast.LENGTH_LONG).show();
		}
		return strReturn;
	}

	public String getAddressbyGeoPoint(Location l) {
		if (l != null) {
			return getAddressbyGeoPoint(new GeoPoint((int) (l.getLatitude() * 1E6), (int) (l.getLongitude() * 1E6)));
		} else {
			return null;
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
