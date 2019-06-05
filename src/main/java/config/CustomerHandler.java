package config;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * @Description:创建自定义助手类
 * @author Calvert
 * SimpleChannelInboundHandler:对于请求来讲，其实相当于[入站，入境]
 */
public class CustomerHandler extends SimpleChannelInboundHandler<HttpObject>{

	// ChannelHandlerContext: 为管道处理器上下文对象
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

		//1. 获得channel
		Channel channel = ctx.channel();
		if(msg instanceof HttpRequest){  // 只要是HttpRequest请求，都会执行，需要优化！！！
			//2. 显示客户端远程地址
			System.out.println("客户端远程地址: "+channel.remoteAddress());
			//3. 定义发送的数据消息
			//Unpooled 是一个深克隆对象
			ByteBuf content = Unpooled.copiedBuffer("hello netty ~ ", CharsetUtil.UTF_8);
			//4. 构建一个 http response
			DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1,  // HTTP_1_1 默认开启的是一个长连接
					HttpResponseStatus.OK, // 还有很多状态
					content);
			//5. 为响应增加数据类型和长度
			httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
			httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
			
			//6. 把响应刷到客户端
			ctx.writeAndFlush(httpResponse);
		}
		
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel...注册");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel...移除");
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel...活跃");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel...不活跃");
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel...读取完毕");
		super.channelReadComplete(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println("用户事件触发");
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel...可写更改");
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("捕获到异常");
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel...助手类添加");
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel...助手类移除");
		super.handlerRemoved(ctx);
	}

}
