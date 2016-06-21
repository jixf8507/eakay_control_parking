package cn.eakay.control.parking.tools;

public enum DeviceCmdType {

	UP(1), DOWN(2), QUERY(6), QUERY_DEVICES(8), QUERY_PORTS(9);

	private int value;

	public static DeviceCmdType valueOf(int value) { // 手写的从int到enum的转换函数
		switch (value) {
		case 1:
			return UP;
		case 2:
			return DOWN;
		case 6:
			return QUERY;
		case 8:
			return QUERY_DEVICES;
		case 9:
			return QUERY_PORTS;
		default:
			return null;
		}
	}

	private DeviceCmdType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
