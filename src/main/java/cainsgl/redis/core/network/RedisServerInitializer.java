package cainsgl.redis.core.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

public class RedisServerInitializer extends ChannelInitializer<SocketChannel>
{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()));
        RedisCommandDecoder redisCommandDecoder = new RedisCommandDecoder();
        pipeline.addLast(redisCommandDecoder);
        pipeline.addLast(new RedisCommandHandler());
        pipeline.addLast(new GlobalExceptionHandler(redisCommandDecoder));
    }
}
