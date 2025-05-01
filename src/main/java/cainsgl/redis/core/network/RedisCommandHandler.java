package cainsgl.redis.core.network;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class RedisCommandHandler  extends ChannelInboundHandlerAdapter
{
    private static final Logger log = LoggerFactory.getLogger(RedisCommandHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if(msg instanceof AbstractCommandProcessor.Command command){
//            command.submit(()->{
//                Response execute = processor.execute();
//                writeData(ctx,execute);
//            });
            command.processor.getManager().submit(()->{
                RESP2Response res = command.processor.execute();
                log.debug("execute {} and Response=> {}",command.cmd,res.toString());
                ctx.writeAndFlush(Unpooled.copiedBuffer(res.serialization(), CharsetUtil.UTF_8));

            });
        }
        //处理直接是请求的
        if(msg instanceof RESP2Response res){
            ctx.writeAndFlush(Unpooled.copiedBuffer(res.serialization(), CharsetUtil.UTF_8));
        }
    }

}
