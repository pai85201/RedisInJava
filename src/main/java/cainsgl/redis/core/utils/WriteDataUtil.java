package cainsgl.redis.core.utils;

import cainsgl.redis.core.network.response.resp.RESP2Response;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class WriteDataUtil
{
    public static void writeData(ChannelHandlerContext ctx, RESP2Response response)
    {
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.serialization(), CharsetUtil.UTF_8));
    }
}
