package cn.eakay.control.parking.tcpserver.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import cn.eakay.control.parking.tcpserver.netty.handler.TCPServerInitializer;

public class TcpServer {
	private static final String HOST = "localhost";
	private static final int PORT = 9999;
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

	public static void run(String host, int port) {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(new TCPServerInitializer());
		try {
			serverBootstrap.bind(host, port).sync();
			System.err.println("TCPServer启动成功.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	public static void main(String[] args) throws Exception {
		TcpServer.run(HOST, PORT);
		// TcpServer.shutdown();
	}

}
