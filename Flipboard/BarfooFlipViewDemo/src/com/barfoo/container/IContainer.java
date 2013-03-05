/**
 * 目的:规范 container中的布局
 * buildView()对于container中布局中内部组件的宽高的设置
 * getXmlResource()设置container中所使用的布局
 */
package com.barfoo.container;

import org.json.JSONObject;

public interface IContainer {
	void buildView(JSONObject json);
	int getXmlResource();
}
