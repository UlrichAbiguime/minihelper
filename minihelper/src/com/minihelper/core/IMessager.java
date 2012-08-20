/**
 * IMessager.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-8-20
 */
package com.minihelper.core;

import android.app.NotificationManager;
/**
 * @author comger
 *
 */
public interface IMessager {
	
	public int countNewMessage();
	
	public void showMessage(NotificationManager notificationManager, int count);
}
