package cn.eakay.control.parking.handler;

import java.sql.Timestamp;
import java.util.Date;

public class ParkingDevice implements java.io.Serializable {

	// 设备离线时间设置
	private static final long OUT_LINE_TIME = 120 * 1000;

	private static final long serialVersionUID = 1L;

	private String deviceKey;
	private int portCount;

	// 上一个心跳消息接受时间
	private Timestamp lastReciveTime;

	private ParkingDevice() {

	}

	public static ParkingDevice create(String deviceKey) {
		ParkingDevice device = new ParkingDevice();
		device.deviceKey = deviceKey;
		return device;
	}

	public Timestamp getLastReciveTime() {
		return lastReciveTime;
	}

	public void setLastReciveTime(Timestamp lastReciveTime) {
		this.lastReciveTime = lastReciveTime;
	}

	public int getPortCount() {
		return portCount;
	}

	public void setPortCount(int portCount) {
		this.portCount = portCount;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public boolean isOnline() {
		long nowTime = new Date().getTime();
		if (nowTime > lastReciveTime.getTime() + OUT_LINE_TIME) {
			return false;
		}
		return true;
	}

}
