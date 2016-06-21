package cn.eakay.control.parking.handler;

import java.sql.Timestamp;
import java.util.Date;

public class PrakingDevicePort implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private static final long OUT_LINE_TIME = 60 * 1000;

	private int port;
	// 当前状态
	private String currStatus;

	// 当前是否正在有发送控制指令
	private String control = "N";
	// 上一次发送控制命令时间
	private Timestamp lastControlTime;
	private int lastControlCmd = 0;
	// 上一次控制命令执行完成时间
	private Timestamp lastControlFinishTime;
	private int lastControlResult;

	private PrakingDevicePort(int port) {
		this.port = port;
	}

	public static PrakingDevicePort create(int port) {
		return new PrakingDevicePort(port);
	}

	public boolean isCurrControl() {
		if ("Y".equals(control) && lastControlFinishTime != null) {
			long nowTime = new Date().getTime();
			return lastControlFinishTime.getTime() < lastControlTime.getTime()
					&& (nowTime < lastControlTime.getTime() + OUT_LINE_TIME);
		}
		return false;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getControl() {
		return control;
	}

	public Timestamp getLastControlTime() {
		return lastControlTime;
	}

	public void setLastControlTime(Timestamp lastControlTime) {
		this.lastControlTime = lastControlTime;
	}

	public Timestamp getLastControlFinishTime() {
		return lastControlFinishTime;
	}

	public void setLastControlFinishTime(Timestamp lastControlFinishTime) {
		this.lastControlFinishTime = lastControlFinishTime;
	}

	public int getLastControlResult() {
		if (lastControlFinishTime == null || lastControlTime == null) {
			return -1;
		}
		if (lastControlFinishTime.getTime() > lastControlTime.getTime()) {
			return lastControlResult;
		}
		return -1;
	}

	public void setLastControlResult(int lastControlResult) {
		this.lastControlResult = lastControlResult;
	}

	public String getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public int getLastControlCmd() {
		return lastControlCmd;
	}

	public void setLastControlCmd(int lastControlCmd) {
		this.lastControlCmd = lastControlCmd;
	}

}
