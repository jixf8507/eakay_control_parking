package cn.eakay.control.parking.service;

import cn.eakay.control.parking.tools.ControlResult;

public interface CustomerParkingControlService {

	/**
	 * 用户开始停车操作
	 * 
	 * @param orderId
	 *            订单编号
	 * @param customerId
	 *            用户ID
	 * @return
	 */
	public ControlResult parkingStart(int orderId, int customerId);

	/**
	 * 用户结束停车操作
	 * 
	 * @param orderId
	 *            订单编号
	 * @param customerId
	 *            用户ID
	 * @return
	 */
	public ControlResult parkingEnd(int orderId, int customerId);

	/**
	 * 用户升锁操作
	 * 
	 * @param orderId
	 *            订单编号
	 * @param customerId
	 *            用户ID
	 * @return
	 */
	public ControlResult up(int orderId, int customerId);

	/**
	 * 用户降锁操作
	 * 
	 * @param orderId
	 *            订单编号
	 * @param customerId
	 *            用户ID
	 * @return
	 */
	public ControlResult down(int orderId, int customerId);

	/**
	 * 用户查询订单状态
	 * 
	 * @param orderId
	 *            订单编号
	 * @param customerId
	 *            用户ID
	 * @return
	 */
	public ControlResult query(int orderId, int customerId);

}
