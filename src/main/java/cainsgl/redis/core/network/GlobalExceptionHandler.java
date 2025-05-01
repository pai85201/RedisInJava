package cainsgl.redis.core.network;

import cainsgl.redis.core.exception.RedisException;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;



public class GlobalExceptionHandler  extends ChannelDuplexHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    RedisCommandDecoder redisCommandDecode;
    public GlobalExceptionHandler(RedisCommandDecoder redisCommandDecoder)
    {
        this.redisCommandDecode = redisCommandDecoder;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //初始化网络哪里
        redisCommandDecode.init();
       if(cause instanceof DecoderException DecoderEx)
       {
           Throwable cause1 = DecoderEx.getCause();
           String errorMsg;
           if(cause1 instanceof RedisException redisException)
           {
               errorMsg = "-ERR " +redisException.getMessage() ;
           }else
           {
               errorMsg="-ERR "+cause.getMessage() ;
           }
           log.warn(errorMsg);
           ctx.writeAndFlush(Unpooled.copiedBuffer(errorMsg+ "\r\n", CharsetUtil.UTF_8));
       }else
       {
           if(cause instanceof SocketException)
           {
               return;
           }
           log.error("出乎意料的异常",cause);
       }
    }
}
