package cn.eakay.control.parking.handler;

public class ParkingServer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String ip;
	private String serverURL;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

}
