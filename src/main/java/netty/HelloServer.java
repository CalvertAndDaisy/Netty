package netty;

import config.HelloServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description:实现客户端发送一个请求，服务器返回hello netty
 * @author Calvert
 */
public class HelloServer {
	public static void main(String[] args) throws Exception {
		//1. 定义一对线程组：主从线程组
		//主线程组，用于接受客户端的连接，但是不做任何处理，和老板一样不做，
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		//从线程组，处理IO, 主线程组把任务丢给从线程组，让从线程组去做任务
		EventLoopGroup childGroup = new NioEventLoopGroup();	
		try {
			//2. netty 服务器的创建， ServerBootstrap 是一个启动类
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(parentGroup, childGroup) 				 // 设置主从线程组
						   .channel(NioServerSocketChannel.class)  		 // 设置nio双向通道
						   .childHandler(new HelloServerInitializer());  // 子处理器，用于处理childGroup
			
			//3. 启动server，并且设置 8088为启动端口号，同时启动的方式为同步
			ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
			
			//4. 监听关闭的channel,设置为同步的方式
			channelFuture.channel().closeFuture().sync();
		} finally {
			//5. 优雅的关闭线程组
			childGroup.shutdownGracefully();
			parentGroup.shutdownGracefully();
		}
		
	}
}
