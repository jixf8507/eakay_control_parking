package cn.eakay.control.parking.tools;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class WebUtil {

	private static Logger logger = Logger.getLogger(WebUtil.class);

	private static HttpClient httpClient;

	private static HttpClient initHttpClient() {
		if (httpClient == null) {
			HttpConnectionManagerParams params = new HttpConnectionManagerParams();
			// 指定向每个host发起的最大连接数，默认是2，太少了
			params.setDefaultMaxConnectionsPerHost(10);
			// 指定总共发起的最大连接数，默认是20，太少了
			params.setMaxTotalConnections(10);
			// 连接超时时间-10s
			params.setConnectionTimeout(30 * 1000);
			// 读取数据超时时间-60s
			params.setSoTimeout(30 * 1000);
			// params.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
			// "UTF-8");
			MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
			manager.setParams(params);
			return new HttpClient(manager);
		}
		return httpClient;
	}

	/**
	 * 得到页面返回结果
	 * 
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	public static String pageString(String urlString,
			Map<String, String> paramMap) throws Exception {
		logger.debug("pageString begin:" + urlString);

		PostMethod httpget = null;
		String result = "";
		try {
			httpget = new PostMethod(urlString);

			httpget.setRequestHeader("Connection", "close");
			httpget.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			if (paramMap != null) {
				for (String key : paramMap.keySet()) {
					httpget.setParameter(key, paramMap.get(key));
				}
			}

			initHttpClient().executeMethod(httpget);
			result = httpget.getResponseBodyAsString();
		} finally {
			httpget.releaseConnection();
			httpget.abort();
			initHttpClient().getHttpConnectionManager().closeIdleConnections(0);
		}
		return result.trim();
	}

}
