package cn.eakay.control.parking.service;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eakay.control.parking.dao.ParkingControlDao;
import cn.eakay.control.parking.handler.ParkingDevice;
import cn.eakay.control.parking.handler.ParkingServer;
import cn.eakay.control.parking.handler.PrakingDevicePort;
import cn.eakay.control.parking.tools.ControlResult;
import cn.eakay.control.parking.tools.DeviceCmdType;
import cn.eakay.control.parking.tools.MSG;
import cn.eakay.control.parking.tools.WebUtil;

import com.alibaba.dubbo.common.json.JSON;

@Service("parkingControlService")
public class ParkingControlServiceImpl implements ParkingControlService {

	// private static final String URL = "HTTP://192.168.1.24:8088/?";

	@Autowired
	private ParkingControlDao controlDao;

	public ControlResult up(String deviceKey, int port) {
		String serverURL = controlDao.queryServerURL(deviceKey);
		if (serverURL == null) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该地锁设备没有配置控制服务"), "");
		}
		String queryString = "deviceKey=" + deviceKey + "&port=" + port
				+ "&type=" + DeviceCmdType.UP.getValue();
		String result;
		try {
			result = WebUtil.pageString(serverURL + queryString, null);
			MSG msg = (MSG) JSONObject.toBean(JSONObject.fromObject(result),
					MSG.class);
			return ControlResult.create(msg, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ControlResult down(String deviceKey, int port) {
		String serverURL = controlDao.queryServerURL(deviceKey);
		if (serverURL == null) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该地锁设备没有配置控制服务"), "");
		}
		String queryString = "deviceKey=" + deviceKey + "&port=" + port
				+ "&type=" + DeviceCmdType.DOWN.getValue();
		String result;
		try {
			result = WebUtil.pageString(serverURL + queryString, null);
			MSG msg = (MSG) JSONObject.toBean(JSONObject.fromObject(result),
					MSG.class);
			return ControlResult.create(msg, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ControlResult query(String deviceKey, int port) {
		String serverURL = controlDao.queryServerURL(deviceKey);
		if (serverURL == null) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该地锁设备没有配置控制服务"), "");
		}
		String queryString = "deviceKey=" + deviceKey + "&port=" + port
				+ "&type=" + DeviceCmdType.QUERY.getValue();
		String result;
		try {
			result = WebUtil.pageString(serverURL + queryString, null);
			MSG msg = (MSG) JSONObject.toBean(JSONObject.fromObject(result),
					MSG.class);
			return ControlResult.create(msg, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ParkingServer> queryParkingServers() {
		return controlDao.queryParkingServers();
	}

	public List<ParkingDevice> getParkingDevices(int serverId) {
		String serverURL = controlDao.getParkingServers(serverId);
		if (serverURL == null) {
			return null;
		}
		String queryString = "type=" + DeviceCmdType.QUERY_DEVICES.getValue();
		System.out.println(queryString);
		String result;
		try {
			result = WebUtil.pageString(serverURL + queryString, null);
			return JSON.parse(result, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<PrakingDevicePort> getDervicePorts(int serverId,
			String deviceKey) {
		String serverURL = controlDao.getParkingServers(serverId);
		if (serverURL == null) {
			return null;
		}
		String queryString = "deviceKey=" + deviceKey + "&type="
				+ DeviceCmdType.QUERY_PORTS.getValue();
		String result;
		try {
			result = WebUtil.pageString(serverURL + queryString, null);
			return JSON.parse(result, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
