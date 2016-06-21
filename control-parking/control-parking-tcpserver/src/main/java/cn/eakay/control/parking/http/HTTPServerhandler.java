package cn.eakay.control.parking.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.eakay.control.parking.handler.ParkingDevice;
import cn.eakay.control.parking.handler.PrakingDevicePort;
import cn.eakay.control.parking.tcpserver.help.ParkingDeviceControlHelp;
import cn.eakay.control.parking.tcpserver.util.StringUtils;
import cn.eakay.control.parking.tools.DeviceCmdType;
import cn.eakay.control.parking.tools.MSG;

public class HTTPServerhandler extends
		SimpleChannelInboundHandler<FullHttpRequest> {

	/**
	 * http参数说明：type=1&deviceKey=QXDS0003&port=2
	 * 
	 * @param type
	 *            :请求类型
	 * @param deviceKey
	 *            :地锁设备编号
	 * @param port
	 *            :地锁设备端口
	 * 
	 */
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request)
			throws Exception {
		String uri = request.getUri().substring(2);
		System.err.println(uri);
		String[] str = uri.split("&");
		Map<String, String> params = StringUtils.splitParam(str);
		DeviceCmdType type = DeviceCmdType.valueOf(Integer.parseInt(params
				.get("type")));
		System.err.println("type:" + type);
		if (type != null) {
			switch (type) {
			case UP:
				// 升锁
				up(ctx, params);
				return;
			case DOWN:
				// 降锁
				down(ctx, params);
				return;
			case QUERY:
				// 查询地锁状态
				query(ctx, params);
				return;
			case QUERY_DEVICES:
				// 查询地锁当前地锁设备的状态情况
				queryParkingDevices(ctx, params);
				return;
			case QUERY_PORTS:
				// 查询地锁设备端口的状态情况
				queryParkingDevicePorts(ctx, params);
				return;

			default:
				break;
			}

		} else {
			MSG msg = MSG.createErrorMSG(0, "参数错误");
			writeObject(ctx, msg);
		}

	}

	/**
	 * 查询地锁当前地锁设备的状态情况
	 * 
	 * @param ctx
	 * @param params
	 */
	private void queryParkingDevices(ChannelHandlerContext ctx,
			Map<String, String> params) {
		List<ParkingDevice> devices = ParkingDeviceControlHelp
				.getParkingDevices();
		writeArray(ctx, devices);
	}

	/**
	 * 查询地锁当前地锁设备的状态情况
	 * 
	 * @param ctx
	 * @param params
	 */
	private void queryParkingDevicePorts(ChannelHandlerContext ctx,
			Map<String, String> params) {
		String deviceKey = params.get("deviceKey");
		List<PrakingDevicePort> devicePorts = ParkingDeviceControlHelp
				.getDervicePorts(deviceKey);
		writeArray(ctx, devicePorts);
	}

	/**
	 * 查询地锁状态
	 * 
	 * @param ctx
	 * @param params
	 */
	private void query(ChannelHandlerContext ctx, Map<String, String> params) {
		String deviceKey = params.get("deviceKey");
		int port = Integer.parseInt(params.get("port"));
		MSG result = ParkingDeviceControlHelp.up(deviceKey, port,
				DeviceCmdType.QUERY);
		writeObject(ctx, result);
	}

	/**
	 * 降锁
	 * 
	 * @param ctx
	 * @param params
	 */
	private void down(ChannelHandlerContext ctx, Map<String, String> params) {
		String deviceKey = params.get("deviceKey");
		int port = Integer.parseInt(params.get("port"));
		MSG result = ParkingDeviceControlHelp.up(deviceKey, port,
				DeviceCmdType.DOWN);
		writeObject(ctx, result);
	}

	/**
	 * 升锁操作
	 * 
	 * @param ctx
	 * @param params
	 */
	private void up(ChannelHandlerContext ctx, Map<String, String> params) {
		String deviceKey = params.get("deviceKey");
		int port = Integer.parseInt(params.get("port"));
		MSG result = ParkingDeviceControlHelp.up(deviceKey, port,
				DeviceCmdType.UP);
		writeObject(ctx, result);
	}

	private void writeObject(ChannelHandlerContext ctx, Object obj) {
		String result = "{'msg':'参数错误'}";
		try {
			result = JSONObject.fromObject(obj).toString();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
		response.headers().set("content_type", "text/html;charset=UTF-8");
		response.content().writeBytes(result.getBytes());
		ctx.channel().writeAndFlush(response)
				.addListener(ChannelFutureListener.CLOSE);
	}

	private void writeArray(ChannelHandlerContext ctx, Object obj) {
		String result = "{'msg':'参数错误'}";
		try {
			result = JSONArray.fromObject(obj).toString();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
		response.headers().set("content_type", "text/html;charset=UTF-8");
		response.content().writeBytes(result.getBytes());
		ctx.channel().writeAndFlush(response)
				.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
		System.err.println("exception");
		System.err.println(cause);
	}

}
