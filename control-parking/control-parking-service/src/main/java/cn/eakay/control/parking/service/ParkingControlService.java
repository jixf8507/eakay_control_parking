package cn.eakay.control.parking.service;

import java.util.List;

import cn.eakay.control.parking.handler.ParkingDevice;
import cn.eakay.control.parking.handler.ParkingServer;
import cn.eakay.control.parking.handler.PrakingDevicePort;
import cn.eakay.control.parking.tools.ControlResult;

public interface ParkingControlService {

	/**
	 * 升锁
	 * 
	 * @param deviceKey
	 * @param port
	 * @return
	 */
	public ControlResult up(String deviceKey, int port);

	/**
	 * 降锁
	 * 
	 * @param deviceKey
	 * @param port
	 * @return
	 */
	public ControlResult down(String deviceKey, int port);

	/**
	 * 查询当前地锁端口的状态
	 * 
	 * @param deviceKey
	 * @param port
	 * @return
	 */
	public ControlResult query(String deviceKey, int port);

	/**
	 * 查询地锁设备的服务
	 * 
	 * @return
	 */
	public List<ParkingServer> queryParkingServers();

	/**
	 * 查找制定地锁服务上连接的地锁设备
	 * 
	 * @param serverURL
	 *            指定的TCPserver
	 * @return
	 */
	public List<ParkingDevice> getParkingDevices(int serverId);

	/**
	 * 查看当前地锁端口的状态
	 * 
	 * @param serverURL
	 *            指定的TCPserver
	 * @param deviceKey
	 *            地锁设备编号
	 * @return
	 */
	public List<PrakingDevicePort> getDervicePorts(int serverId,
			String deviceKey);

}
