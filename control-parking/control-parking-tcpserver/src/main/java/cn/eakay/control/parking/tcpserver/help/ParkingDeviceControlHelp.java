package cn.eakay.control.parking.tcpserver.help;

import io.netty.channel.ChannelHandlerContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.eakay.control.parking.handler.ParkingDevice;
import cn.eakay.control.parking.handler.PrakingDevicePort;
import cn.eakay.control.parking.service.ParkingDeviceContext;
import cn.eakay.control.parking.tcpserver.util.NumberUtils;
import cn.eakay.control.parking.tools.DeviceCmdType;
import cn.eakay.control.parking.tools.MSG;

public class ParkingDeviceControlHelp {

	// 线程等待时间
	private static final long SLEEP_TIME = 2 * 1000;
	// 检查次数
	private static final int CHECK_ACCOUNT = 13;

	private static HashMap<String, ParkingDeviceContext> parkingDevices = new HashMap<>();

	public static ParkingDeviceContext getContext(String key,
			ChannelHandlerContext ctx) {
		ParkingDeviceContext context = parkingDevices.get(key);
		if (context == null) {
			context = ParkingDeviceContext.create(ParkingDevice.create(key));
			parkingDevices.put(key, context);
		}
		context.setContext(ctx);
		context.getDevice().setLastReciveTime(
				new Timestamp(System.currentTimeMillis()));
		return context;
	}

	/**
	 * 升起地锁
	 * 
	 * @param deviceKey
	 *            地锁设备编号
	 * @return
	 */
	public static MSG up(String deviceKey, int port, DeviceCmdType controlType) {
		ParkingDeviceContext parkingDevice = parkingDevices.get(deviceKey);
		if (parkingDevice == null) {
			return MSG.createErrorMSG(1, "地锁设备(" + deviceKey + ")没有上线，操作失败！");
		}
		if (!parkingDevice.getDevice().isOnline()) {
			return MSG.createErrorMSG(2, "地锁设备(" + deviceKey + ")离线，操作失败！");
		}
		PrakingDevicePort devicePort = parkingDevice.getDervicePorts().get(
				port + "");
		if (devicePort == null) {
			return MSG.createErrorMSG(3, "地锁编号(" + deviceKey + "-" + port
					+ ")不存在，操作失败！");
		}
		if (devicePort.isCurrControl()) {
			return MSG.createErrorMSG(4, "地锁编号(" + deviceKey + "-" + port
					+ ")正在执行控制指令，请稍后在操作！");
		}
		ChannelHandlerContext tcpChannel = parkingDevice.getContext();
		if (tcpChannel != null) {
			devicePort.setControl("Y");
			devicePort.setLastControlTime(new Timestamp(System
					.currentTimeMillis()));
			devicePort.setLastControlCmd(controlType.getValue());
			// 发送控制命令
			sendControlCmd(deviceKey, port, controlType, tcpChannel);

			switch (controlType) {
			case QUERY:
				// 查看询问命令执行的返回的结果
				return checkQueryResult(devicePort, deviceKey, port);
			default:
				// 查看控制命令执行的返回的结果
				return checkControlResult(devicePort, deviceKey, port);
			}

		}
		return MSG.createErrorMSG(7, "地锁控制通道(" + deviceKey + ")不存在，无法发送控制指令！");
	}

	/**
	 * 检测控制命令执行的结果
	 * 
	 * @param devicePort
	 *            控制的口号
	 * @return
	 */
	private static MSG checkControlResult(PrakingDevicePort devicePort,
			String deviceKey, int port) {
		for (int i = 0; i < CHECK_ACCOUNT; i++) {
			try {
				Thread.sleep(SLEEP_TIME);
				int result = devicePort.getLastControlResult();
				switch (result) {
				case 90:
					return MSG.createSuccessMSG();
				case 91:
					return MSG.createErrorMSG(5, "地锁编号(" + deviceKey + "-"
							+ port + ")控制失败，请稍后在操作！");
				default:
					break;
				}
			} catch (InterruptedException e) {

			}
		}
		return MSG.createErrorMSG(6, "地锁编号(" + deviceKey + "-" + port
				+ ")执行请求超时，请稍后在操作！");
	}

	/**
	 * 查看询问命令的执行结果
	 * 
	 * @param devicePort
	 * @param deviceKey
	 * @param port
	 * @return
	 */
	private static MSG checkQueryResult(PrakingDevicePort devicePort,
			String deviceKey, int port) {
		for (int i = 0; i < CHECK_ACCOUNT; i++) {
			try {
				Thread.sleep(SLEEP_TIME);
				if (devicePort.isCurrControl()
						&& devicePort.getLastControlCmd() == 3) {
					continue;
				}
				// Map<String, Object> data = new HashMap<String, Object>();
				// data.put("deviceKey", deviceKey);
				// data.put("port", port);
				// data.put("status", devicePort.getCurrStatus());
				MSG msg = MSG.createSuccessMSG();
				msg.setInfo(devicePort.getCurrStatus());
				return msg;
			} catch (InterruptedException e) {

			}
		}
		return MSG.createErrorMSG(6, "地锁编号(" + deviceKey + "-" + port
				+ ")执行请求超时，请稍后在操作！");
	}

	/**
	 * 发送控制命令
	 * 
	 * @param deviceKey
	 *            地锁设备编号
	 * @param port
	 *            地锁口
	 * @param controlType
	 *            控制命令类型
	 * @param tcpChannel
	 *            通信口
	 */
	private static void sendControlCmd(String deviceKey, int port,
			DeviceCmdType controlType, ChannelHandlerContext tcpChannel) {
		byte[] deviceBytes = getDeviceKeyBytes(deviceKey);
		byte[] cmd = new byte[9];
		for (int i = 0; i < 6; i++) {
			cmd[i] = deviceBytes[i];
		}
		cmd[6] = NumberUtils.int2Byte(port)[3];
		cmd[7] = NumberUtils.int2Byte(controlType.getValue())[3];
		cmd[8] = (byte) (cmd[4] ^ cmd[5] ^ cmd[6] ^ cmd[7]);
		tcpChannel.writeAndFlush(cmd);
	}

	/**
	 * 发送控制命令
	 * 
	 * @param deviceKey
	 *            地锁设备编号
	 * @param tcpChannel
	 *            通信口
	 */
	public static void reOnlineCmd(String deviceKey,
			ChannelHandlerContext tcpChannel) {
		byte[] deviceBytes = getDeviceKeyBytes(deviceKey);
		byte[] cmd = new byte[9];
		for (int i = 0; i < 6; i++) {
			cmd[i] = deviceBytes[i];
		}
		cmd[6] = (byte) 0xFF;
		cmd[7] = (byte) 0xFF;
		cmd[8] = (byte) (cmd[4] ^ cmd[5] ^ cmd[6] ^ cmd[7]);
		System.out.println(cmd[8]);
		tcpChannel.writeAndFlush(cmd);
	}

	private static byte[] getDeviceKeyBytes(String deviceKey) {
		deviceKey = deviceKey.toLowerCase();
		char[] no = new char[4];
		no[0] = deviceKey.charAt(0);
		no[1] = deviceKey.charAt(1);
		no[2] = deviceKey.charAt(2);
		no[3] = deviceKey.charAt(3);
		String number = deviceKey.substring(4);
		Integer noo = Integer.valueOf(number);
		System.err.println("number:" + number);
		byte[] cmd = new byte[6];
		cmd[0] = (byte) no[0];
		cmd[1] = (byte) no[1];
		cmd[2] = (byte) no[2];
		cmd[3] = (byte) no[3];

		cmd[4] = NumberUtils.int2Byte(noo)[2];
		cmd[5] = NumberUtils.int2Byte(noo)[3];
		return cmd;
	}

	public static List<ParkingDevice> getParkingDevices() {
		List<ParkingDevice> list = new ArrayList<>();
		for (String key : parkingDevices.keySet()) {
			list.add(parkingDevices.get(key).getDevice());
		}
		return list;
	}

	public static List<PrakingDevicePort> getDervicePorts(String deviceKey) {
		Map<String, PrakingDevicePort> devicePorts = parkingDevices.get(
				deviceKey).getDervicePorts();
		List<PrakingDevicePort> list = new ArrayList<>();
		for (String key : devicePorts.keySet()) {
			list.add(devicePorts.get(key));
		}
		return list;

	}

}
