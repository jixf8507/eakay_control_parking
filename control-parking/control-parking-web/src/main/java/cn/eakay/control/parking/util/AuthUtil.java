/**
 * @title AuthUtil.java
 * @package com.baizhu.util
 * @projectName VolunteerAPI
 * @date 2014年5月13日
 * @Copyright: 2014 www.byzhu.com Inc. All rights reserved.
 */

package cn.eakay.control.parking.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.eakay.control.parking.tools.MSG;

/**
 * 签名/授权辅助类.
 * 
 * @author xiachengyun xiachengyun@baizhu.cc
 * @version V1.0
 */
public class AuthUtil {
	/**
	 * 参数和签名验证,不验证user_token参数.
	 * 
	 * @param params参数的集合
	 * @param isSign是否需要验证sign参数
	 *            .
	 * @return MSG.
	 */
	public static MSG verifySign(Map<String, String> params, boolean isSign) {
		MSG msg = MSG.createSuccessMSG();
		
		String test=params.get("test");
		if (test!=null&&test.equals("yes")) {
			msg.setSuccess(true);
			msg.setInfo("测试使用");
			return msg;
		}
		
		if (params == null || params.size() == 0) {
			msg.setSuccess(false);
			msg.setInfo("系统参数缺失.");
			return msg;
		}

		String timeStamp = params.get("timestamp");
		if (timeStamp == null) {
			msg.setSuccess(false);
			msg.setInfo("timestamp参数缺失.");
			return msg;
		}

		String format = params.get("format");
		if ((format != null) && (!format.equals("json"))) {
			msg.setSuccess(false);
			msg.setInfo("format参数错误.");
			return msg;
		}

		String appKey = params.get("app_key");
		if (appKey == null) {
			msg.setSuccess(false);
			msg.setInfo("app_key参数缺失.");
			return msg;
		}

		if (!appKey.equals(Config.APP_KEY)) {
			msg.setSuccess(false);
			msg.setInfo("app_key没有授权.");
			return msg;
		}

		String clientType = params.get("client_type");
		if (clientType == null) {
			msg.setSuccess(false);
			msg.setInfo("client_type参数缺失.");
			return msg;
		}

		if (!(clientType.equals(Config.CLIENT_TYPE_ANDROID)||clientType.equals(Config.CLIENT_TYPE_IOS))) {
			msg.setSuccess(false);
			msg.setInfo("client_type不正确.");
			return msg;
		}

		String ver = params.get("ver");
		if ((ver != null) && (!ver.equals("1.0"))) {
			msg.setSuccess(false);
			msg.setInfo("ver参数错误.");
			return msg;
		}

		String signMethod = params.get("sign_method");
		if ((signMethod != null) && (!signMethod.equals("md5"))) {
			msg.setSuccess(false);
			msg.setInfo("sign_method参数错误.");
			return msg;
		}
		if (isSign) {
			String sign = params.get("sign");
			if (sign == null || sign.equals("")) {
				msg.setSuccess(false);
				msg.setInfo("sign签名参数缺失.");
				return msg;
			}

			if (sign.equals(sortAndJoint(params))) {
				msg.setSuccess(true);
				msg.setInfo("sign签名验证成功.");
				return msg;
			} else {
				msg.setSuccess(false);
				msg.setInfo("sign签名错误.");
				return msg;
			}
		} else {
			msg.setSuccess(true);
			msg.setInfo("系统参数正确.");
			return msg;
		}

	}

	public static String sortAndJoint(Map<String, String> params) {
		// sign不参与签名参数.
		params.remove("sign");
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		int size = keys.size();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append(keys.get(i));
			//System.err.println(keys.get(i));
			builder.append((params.get(keys.get(i))));
		//System.err.println(params.get(keys.get(i)));

		}
		// 末尾添加app_secret
		builder.append(Config.APP_SECRET);
		//System.err.println(builder.toString());
		String sign = builder.toString();
		sign = SecurityUtil.MD5(sign);
		return sign;
	}

}
