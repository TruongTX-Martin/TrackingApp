package com.ks.trackingapp.client.sqlite;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.ks.trackingapp.shared.interfacemodel.IUserInfo;

interface MyFactory extends AutoBeanFactory {
	AutoBean<IUserInfo> toObject(IUserInfo obj);
}
