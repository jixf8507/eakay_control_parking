package cn.eakay.control.parking.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {

	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 条件查询
	 * 
	 * @param sql
	 *            查询SQL
	 * @param args
	 *            查询参数
	 * @return
	 */
	public List<Map<String, Object>> findListBySQL(String sql, Object[] args) {
		logger.info("JDBC sql: " + sql);
		return this.jdbcTemplate.query(sql, args,
				new RowMapper<Map<String, Object>>() {
					@Override
					public Map<String, Object> mapRow(ResultSet rs, int num)
							throws SQLException {
						Map<String, Object> map = new HashMap<String, Object>();
						ResultSetMetaData metaData = rs.getMetaData();
						int columnCount = metaData.getColumnCount();
						for (int i = 1; i <= columnCount; i++) {
							String columnName = metaData.getColumnLabel(i);
							Object value = rs.getObject(i);
							map.put(columnName, value == null ? "" : value + "");
						}
						return map;
					}
				});
	}

	/**
	 * 查询条数
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	protected int findCount(final String sql, Object[] obj) {
		return this.getJdbcTemplate().queryForObject(sql, obj, Integer.class);
	}

}
