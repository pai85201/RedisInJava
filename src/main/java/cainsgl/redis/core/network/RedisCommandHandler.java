package cainsgl.redis.core.network;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.network.response.Response;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static cainsgl.redis.core.network.response.ResponseEnum.*;

public class RedisCommandHandler  extends ChannelInboundHandlerAdapter
{
    private static final Logger log = LoggerFactory.getLogger(RedisCommandHandler.class);
    private static final String END="\r\n";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if(msg instanceof AbstractCommandProcessor abstractCommandProcessor){
//            command.submit(()->{
//                Response execute = processor.execute();
//                writeData(ctx,execute);
//            });
            abstractCommandProcessor.getWorkerGroup().submit(()->{
                Response res = abstractCommandProcessor.execute();
                writeData(ctx,res,true);
                ctx.flush();
            });
        }
    }

    private void writeData(ChannelHandlerContext ctx, Response execute,boolean isLog)
    {
        try{
            if(isLog)
                log.debug("\nres=> {}",execute.toString());
            Object value = execute.getValue();
            if(value == null)
            {
                ctx.write(Unpooled.copiedBuffer("$-1"+END, CharsetUtil.UTF_8));
                return;
            }
            byte type=execute.getType();
            switch (type)
            {
                case STRING:
                    String s=value.toString();
                    ctx.write(Unpooled.copiedBuffer("$"+s.length()+END+s+ END, CharsetUtil.UTF_8));
                    break;
                case SIMPLE_STRING:
                    String s2=value.toString();
                    ctx.write(Unpooled.copiedBuffer("+"+s2+ END, CharsetUtil.UTF_8));
                    break;
                case NUMBER:
                    ctx.write(Unpooled.copiedBuffer(":"+value+ END, CharsetUtil.UTF_8));
                    break;
                case ARRAY:
                    Response[] arr=(Response[])value;
                    ctx.write(Unpooled.copiedBuffer("*"+arr.length+ END, CharsetUtil.UTF_8));
                    for (Response response : arr)
                    {
                        writeData(ctx, response,false);
                    }

                    break;
                case ERROR:
                    ctx.write(Unpooled.copiedBuffer("-ERR "+value+ END, CharsetUtil.UTF_8));
                    break;
            }
        }catch (Exception e){
            log.error("序列化失败，请检查",e);
        }

    }

}
