package cn.eakay.control.parking.tcpserver.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.sql.Timestamp;

import cn.eakay.control.parking.handler.PrakingDevicePort;
import cn.eakay.control.parking.service.ParkingDeviceContext;
import cn.eakay.control.parking.tcpserver.help.ParkingDeviceControlHelp;
import cn.eakay.control.parking.tools.DeviceCmdType;

public class TcpServerHandler extends SimpleChannelInboundHandler<byte[]> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] bytes)
			throws Exception {
		System.err.println("tcpServerhandler" + bytes.length);
		if (bytes.length < 8) {
			return;
		}
		int msgCode = toInt(bytes[7]);
		switch (msgCode) {
		case 1:
			// 解析上线报文
			handleOnLineMsg(ctx, bytes);
			return;
		case 2:
			// 解析上线报文
			handleHeartMsg(ctx, bytes);
			return;
		case 3:
			// 解析控制命令返回报文
			handleControlReturnMsg(ctx, bytes);
			return;
		default:
			break;
		}

	}

	/**
	 * 解析上线报文
	 * 
	 * @param ctx
	 * @param bytes
	 */
	private void handleOnLineMsg(ChannelHandlerContext ctx, byte[] bytes) {
		String deviceKey = this.getDeviceNO(bytes);
		ParkingDeviceContext context = ParkingDeviceControlHelp.getContext(
				deviceKey, ctx);
		context.getDevice().setPortCount(toInt(bytes[8]));
		// 回复收到报文
		ParkingDeviceControlHelp.reOnlineCmd(deviceKey, ctx);
	}

	/**
	 * 解析上线报文
	 * 
	 * @param ctx
	 * @param bytes
	 */
	private void handleHeartMsg(ChannelHandlerContext ctx, byte[] bytes) {
		String deviceKey = this.getDeviceNO(bytes);
		ParkingDeviceContext context = ParkingDeviceControlHelp.getContext(
				deviceKey, ctx);
		for (int i = 0; i < bytes.length - 10; i++) {
			PrakingDevicePort devicePort = context.getDervicePorts()
					.get(i + "");
			if (devicePort == null) {
				devicePort = PrakingDevicePort.create(i);
				context.getDervicePorts().put(i + "", devicePort);
			}
			devicePort.setCurrStatus(toInt(bytes[8 + i]) + "");
		}
	}

	/**
	 * 解析控制返回报文
	 * 
	 * @param ctx
	 * @param bytes
	 */
	private void handleControlReturnMsg(ChannelHandlerContext ctx, byte[] bytes) {
		String deviceKey = this.getDeviceNO(bytes);
		ParkingDeviceContext context = ParkingDeviceControlHelp.getContext(
				deviceKey, ctx);
		String port = toInt(bytes[8]) + "";
		DeviceCmdType cmd = DeviceCmdType.valueOf(toInt(bytes[9]));
		int result = toInt(bytes[10]);
		PrakingDevicePort devicePort = context.getDervicePorts().get(port);
		if (devicePort == null) {
			return;
		}
		devicePort.setControl("N");
		devicePort.setLastControlFinishTime(new Timestamp(System
				.currentTimeMillis()));
		switch (cmd) {
		case QUERY:
			devicePort.setCurrStatus(result + "");
			break;
		default:
			devicePort.setLastControlResult(result);
			break;
		}

	}

	private String getDeviceNO(byte[] bytes) {
		String header = new String(new byte[] { bytes[0], bytes[1], bytes[2],
				bytes[3] }).toUpperCase();
		int aa = toInt(bytes[4]) * 16 * 16 + toInt(bytes[5]);
		String NO = aa + "";
		for (int i = NO.length(); i < 5; i++) {
			header = header + "0";
		}
		return header + NO;
	}

	public static int toInt(byte b) {
		return (b & 0xFF);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);

	}
}