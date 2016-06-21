package cn.eakay.control.parking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.eakay.control.parking.handler.ParkingServer;

@Repository
public class ParkingControlDao extends BaseDao {

	/**
	 * 查询地锁设备的服务
	 * 
	 * @return
	 */
	public List<ParkingServer> queryParkingServers() {
		String sql = "SELECT id,`Name` as name,ip,spaceControllerSystemServer as serverURL from stake_sitegroup";
		return this.getJdbcTemplate().query(sql, new Object[] {},
				new RowMapper<ParkingServer>() {
					@Override
					public ParkingServer mapRow(ResultSet rs, int num)
							throws SQLException {
						ParkingServer server = new ParkingServer();
						server.setId(rs.getInt("id"));
						server.setName(rs.getString("name"));
						server.setIp(rs.getString("ip"));
						server.setServerURL(rs.getString("serverURL"));
						return server;
					}
				});
	}

	/**
	 * 根据地锁设备查找服务
	 * 
	 * @param deviceKey
	 * @return
	 */
	public String queryServerURL(String deviceKey) {
		String sql = "SELECT d.pFactoryNo,d.deviceType,ss.chargeControllerSystemServer as serverURL"
				+ " from parking_space_device d,stake_sitegroup_relation sr,stake_sitegroup ss"
				+ " where d.siteId=sr.siteCode and d.deviceType=sr.deviceType and sr.groupNo=ss.id and d.pFactoryNo=?";
		List<Map<String, Object>> list = this.findListBySQL(sql,
				new Object[] { deviceKey });
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0).get("serverURL") + "";
	}

	/**
	 * 根据地锁设备查找服务
	 * 
	 * @param deviceKey
	 * @return
	 */
	public String getParkingServers(int id) {
		String sql = "SELECT ss.chargeControllerSystemServer as serverURL from stake_sitegroup ss where ss.id=?";
		List<Map<String, Object>> list = this.findListBySQL(sql,
				new Object[] { id });
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0).get("serverURL") + "";
	}

	/**
	 * 根据订单ID查询用户停车订单
	 * 
	 * @param orderId
	 * @return
	 */
	public Map<String, Object> queryParkingOrder(int orderId) {
		String sql = "SELECT pr.id,pr.customerId,pr.`status`,pr.spaceId,ps.pFactoryNo as deviceKey,ps.pGrooveNo as port from parking_record pr,parking_space ps where pr.spaceId=ps.id and pr.id=?";
		List<Map<String, Object>> list = this.findListBySQL(sql,
				new Object[] { orderId });
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 取车更改订单状态
	 * 
	 * @param orderId
	 * @return
	 */
	public boolean updateParkingOrderForGet(int orderId) {
		String sql = "update parking_record set `status`='已停车',reachTime=NOW() where id=?";
		return this.getJdbcTemplate().update(sql, new Object[] { orderId }) > 0;
	}

	/**
	 * 取车更改订单状态
	 * 
	 * @param orderId
	 * @return
	 */
	public boolean updateParkingOrderForReturn(int orderId) {
		String sql = "update parking_record set `status`='待付费',leaveTime=NOW() where id=?";
		return this.getJdbcTemplate().update(sql, new Object[] { orderId }) > 0;
	}

	public boolean updateParkingSpaceStatus(String pStatus, String orderStatus,
			int parkingSpaceId) {
		String sql = "  update parking_space set PStatus=?,orderStatus=? WHERE id=?";
		return this.getJdbcTemplate().update(sql,
				new Object[] { pStatus, orderStatus, parkingSpaceId }) > 0;
	}

}
