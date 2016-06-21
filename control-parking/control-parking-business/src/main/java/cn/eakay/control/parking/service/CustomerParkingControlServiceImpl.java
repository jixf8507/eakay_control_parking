package cn.eakay.control.parking.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.eakay.control.parking.dao.ParkingControlDao;
import cn.eakay.control.parking.tools.ControlResult;
import cn.eakay.control.parking.tools.MSG;

@Service("customerParkingControlService")
public class CustomerParkingControlServiceImpl implements
		CustomerParkingControlService {

	@Autowired
	private ParkingControlDao controlDao;
	@Autowired
	private ParkingControlService controlService;

	/**
	 * 用户开始停车操作
	 */
	@Override
	@Transactional
	public ControlResult parkingStart(int orderId, int customerId) {
		Map<String, Object> map = controlDao.queryParkingOrder(orderId);
		if (map == null) {
			return ControlResult
					.create(MSG.createErrorMSG(-1, "停车订单ID不存在"), "");
		}
		String status = map.get("status") + "";
		if (!"已预约".equals(status)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车订单状态已经改变，不能操作开始停车"), "");
		}
		if (!map.get("customerId").equals(customerId + "")) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "不是停车订单的用户不能操作开始停车"), "");
		}
		String deviceKey = map.get("deviceKey") + "";
		String port = map.get("port") + "";
		if ("".equals(deviceKey) || "".equals(port)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车位没有安装地锁设备，操作失败"), "");
		}
		int spaceId = Integer.parseInt(map.get("spaceId") + "");
		controlDao.updateParkingSpaceStatus("停车", "使用", spaceId);
		controlDao.updateParkingOrderForGet(orderId);
		return controlService.up(deviceKey, Integer.parseInt(port));
	}

	/**
	 * 用户结束停车操作
	 */
	@Override
	public ControlResult parkingEnd(int orderId, int customerId) {
		Map<String, Object> map = controlDao.queryParkingOrder(orderId);
		if (map == null) {
			return ControlResult
					.create(MSG.createErrorMSG(-1, "停车订单ID不存在"), "");
		}
		String status = map.get("status") + "";
		if (!"已停车".equals(status)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车订单状态已经改变，不能操作结束停车"), "");
		}
		if (!map.get("customerId").equals(customerId + "")) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "不是停车订单的用户不能操作结束停车"), "");
		}
		String deviceKey = map.get("deviceKey") + "";
		String port = map.get("port") + "";
		if ("".equals(deviceKey) || "".equals(port)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车位没有安装地锁设备，操作失败"), "");
		}
		int spaceId = Integer.parseInt(map.get("spaceId") + "");
		controlDao.updateParkingSpaceStatus("空闲", "空闲", spaceId);
		controlDao.updateParkingOrderForReturn(orderId);
		return controlService.down(deviceKey, Integer.parseInt(port));
	}

	/**
	 * 用户升锁操作
	 */
	@Override
	public ControlResult up(int orderId, int customerId) {
		Map<String, Object> map = controlDao.queryParkingOrder(orderId);
		if (map == null) {
			return ControlResult
					.create(MSG.createErrorMSG(-1, "停车订单ID不存在"), "");
		}
		if (!map.get("customerId").equals(customerId + "")) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "不是停车订单的用户不能操作升锁操作"), "");
		}
		String status = map.get("status") + "";
		if (!"已停车".equals(status)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车订单状态不能操作升锁操作"), "");
		}
		String deviceKey = map.get("deviceKey") + "";
		String port = map.get("port") + "";
		if ("".equals(deviceKey) || "".equals(port)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车位没有安装地锁设备，操作失败"), "");
		}
		return controlService.up(deviceKey, Integer.parseInt(port));
	}

	/**
	 * 用户降锁操作
	 */
	@Override
	public ControlResult down(int orderId, int customerId) {
		Map<String, Object> map = controlDao.queryParkingOrder(orderId);
		if (map == null) {
			return ControlResult
					.create(MSG.createErrorMSG(-1, "停车订单ID不存在"), "");
		}
		if (!map.get("customerId").equals(customerId + "")) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "不是停车订单的用户不能操作降锁操作"), "");
		}
		String status = map.get("status") + "";
		if (!"已停车".equals(status)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车订单状态不能操作降锁操作"), "");
		}
		String deviceKey = map.get("deviceKey") + "";
		String port = map.get("port") + "";
		if ("".equals(deviceKey) || "".equals(port)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车位没有安装地锁设备，操作失败"), "");
		}
		return controlService.down(deviceKey, Integer.parseInt(port));
	}

	/**
	 * 用户查询订单操作
	 */
	@Override
	public ControlResult query(int orderId, int customerId) {
		Map<String, Object> map = controlDao.queryParkingOrder(orderId);
		if (map == null) {
			return ControlResult
					.create(MSG.createErrorMSG(-1, "停车订单ID不存在"), "");
		}
		if (!map.get("customerId").equals(customerId + "")) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "不是停车订单的用户不能查询停车状态"), "");
		}
		String status = map.get("status") + "";
		if (!"已停车".equals(status)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车订单状态不能查询停车状态"), "");
		}
		String deviceKey = map.get("deviceKey") + "";
		String port = map.get("port") + "";
		if ("".equals(deviceKey) || "".equals(port)) {
			return ControlResult.create(
					MSG.createErrorMSG(-1, "该停车位没有安装地锁设备，操作失败"), "");
		}
		return controlService.query(deviceKey, Integer.parseInt(port));
	}

}
