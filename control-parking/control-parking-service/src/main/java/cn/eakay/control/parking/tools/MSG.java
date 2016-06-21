package cn.eakay.control.parking.tools;

public class MSG implements java.io.Serializable {

	private static final long serialVersionUID = -8162338848158348791L;
	private boolean success = true;
	private int code = 0;
	private String info = "";

	private MSG() {

	}

	public static MSG createSuccessMSG() {
		return new MSG();
	}

	public static MSG createErrorMSG(int code, String info) {
		MSG msg = new MSG();
		msg.code = code;
		msg.info = info;
		return msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
