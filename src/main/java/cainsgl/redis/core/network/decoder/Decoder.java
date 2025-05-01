package cainsgl.redis.core.network.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface Decoder
{
     void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out);
     void reset();
}
