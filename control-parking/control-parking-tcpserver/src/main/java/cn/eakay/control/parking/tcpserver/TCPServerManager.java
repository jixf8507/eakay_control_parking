package cn.eakay.control.parking.tcpserver;

import cn.eakay.control.parking.http.HTTPServer;
import cn.eakay.control.parking.tcpserver.netty.TcpServer;

public class TCPServerManager {

	private String host;
	private int tcpPort;
	private int httpPort;

	public void init() {
		TcpServer.run(host, tcpPort);
		HTTPServer.run(host, httpPort);
	}

	public void clear() {
		TcpServer.shutdown();
		HTTPServer.shutdown();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

}
