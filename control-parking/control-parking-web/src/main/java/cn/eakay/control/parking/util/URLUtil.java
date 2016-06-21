/**
 * @title URLUtil.java
 * @package com.baizhu.util
 * @projectName VolunteerAPI
 * @date 2014年5月15日
 * @Copyright: 2014 www.byzhu.com Inc. All rights reserved.
 */

package cn.eakay.control.parking.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * url辅助操作类.
 * 
 * @author xiachengyun xiachengyun@baizhu.cc
 * @version V1.0
 */
public class URLUtil
{
	/**
	 * 解析出请求url中的参数.
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> extractParam(HttpServletRequest request)
	{ 
		Enumeration<String> keys = request.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
        while (keys.hasMoreElements())
		{
			String key = (String) keys.nextElement();
			params.put(key, request.getParameter(key));
		} 
		return params;
	}

}
