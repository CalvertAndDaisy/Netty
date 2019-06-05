/**
 * 
 */
package config;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description:初始化器，channel注册后，会执行里面的相应的初始化方法
 * @author Calvert
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		//1. 通过SocketChannel去获得对应的管道
		ChannelPipeline pipeline = channel.pipeline();
		//2. 通过管道，添加handler
		// HttpServerCodec 是由netty提供的助手类，可以理解为拦截器
		// 当请求到服务端我们需要做解码，响应到客户端我们需要做编码
		pipeline.addLast("HttpServerCodec", new HttpServerCodec());
		
		//3. 添加自定义的助手类，返回 hello netty
		pipeline.addLast("customerHandler",new CustomerHandler());
		
	}

}
