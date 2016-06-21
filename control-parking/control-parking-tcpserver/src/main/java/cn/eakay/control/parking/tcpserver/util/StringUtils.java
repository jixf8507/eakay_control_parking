package cn.eakay.control.parking.tcpserver.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
	public static Map<String, String> splitParam(String[] params) {
		Map<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < params.length; i++) {
			map.put(params[i].split("=")[0], params[i].split("=")[1]);
		}
		return map;
	}
}
