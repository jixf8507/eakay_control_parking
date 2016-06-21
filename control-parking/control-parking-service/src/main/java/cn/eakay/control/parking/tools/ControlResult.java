package cn.eakay.control.parking.tools;

public class ControlResult implements java.io.Serializable {

	private static final long serialVersionUID = -8162338848158348791L;
	private MSG msg;
	private Object data;

	private ControlResult() {

	}

	public static ControlResult create(MSG msg, Object data) {
		ControlResult controlResult = new ControlResult();
		controlResult.msg = msg;
		controlResult.data = data;
		return controlResult;
	}

	public MSG getMsg() {
		return msg;
	}

	public void setMsg(MSG msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
