package cn.eakay.control.parking.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HTTPServer {
	private static final String HOST = "192.168.1.24";
	private static final int PORT = 8088;
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

	public static void run(String host, int port) {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(new HTTPServerInitializer());
		try {
			serverBootstrap.bind(host, port).sync();
			System.err.println("HTTPServer启动成功.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	public static void main(String[] args) {
		System.err.println("开始启动HTTPServer服务器...");
		HTTPServer.run(HOST, PORT);
	}
}
