package cn.eakay.control.parking.util;

public class Code
{
	// 公共错误代码.
	public final static int PARAM_MISSING = 10000;
	public final static String PARAM_MISSING_STR = "应用参数缺失";
	public final static int DB_ACCESS_ERROR = 10001;
	public final static String DB_ACCESS_ERROR_STR = "数据访问异常";

	// 账户业务.
	public final static int LOGIN_TIMEOUT = 11000;
	public final static String LOGIN_TIMEOUT_STR = "请重新登录";
	public final static int ACCOUNT_VERIFY = 11001;
	public final static String ACCOUNT_VERIFY_Str = "账户尚未通过审核";

	// 充电业务.
	public final static int NO_STAKE = 12000;
	public final static String NO_STAKE_STR = "该时间段已被别人预约";
	public final static int NO_PORT = 12001;
	public final static String NO_PORT_STR = "该充电口不能被预约";
	public final static int OUT_OF_MAX_EARLY = 12002;
	public final static String OUT_OF_MAX_EARLY_STR = "超出了最大提前预约时间";
	public final static int OUT_OF_MAX_ORDER = 12003;
	public final static String OUT_OF_MAX_ORDER_STR = "超出预约的最长时间";
	public final static int CANCEL_ORDER_FAIL = 12004;
	public final static String CANCEL_ORDER_FAIL_STR = "取消订单失败,请重试";
	
	

}
