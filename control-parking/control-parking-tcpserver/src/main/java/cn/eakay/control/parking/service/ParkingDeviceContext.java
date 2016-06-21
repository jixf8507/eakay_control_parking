package cn.eakay.control.parking.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

import cn.eakay.control.parking.handler.ParkingDevice;
import cn.eakay.control.parking.handler.PrakingDevicePort;

public class ParkingDeviceContext {

	private ParkingDevice device;
	private ChannelHandlerContext context;
	private Map<String, PrakingDevicePort> dervicePorts;

	public static ParkingDeviceContext create(ParkingDevice device) {
		ParkingDeviceContext deviceContext = new ParkingDeviceContext();
		deviceContext.device = device;
		deviceContext.dervicePorts = new HashMap<>();
		return deviceContext;

	}

	public ParkingDevice getDevice() {
		return device;
	}

	public void setDevice(ParkingDevice device) {
		this.device = device;
	}

	public ChannelHandlerContext getContext() {
		return context;
	}

	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}

	public Map<String, PrakingDevicePort> getDervicePorts() {
		return dervicePorts;
	}

	public void setDervicePorts(Map<String, PrakingDevicePort> dervicePorts) {
		this.dervicePorts = dervicePorts;
	}

}
