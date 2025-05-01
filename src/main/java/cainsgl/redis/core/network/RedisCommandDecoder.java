package cainsgl.redis.core.network;


import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.config.RedisConfig;
import cainsgl.redis.core.network.command.CommandAdapter;
import cainsgl.redis.core.network.decoder.AuthDecoder;
import cainsgl.redis.core.network.decoder.Decoder;
import cainsgl.redis.core.network.decoder.DefalutDcoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RedisCommandDecoder extends ByteToMessageDecoder
{
    private static final Logger log = LoggerFactory.getLogger(RedisCommandDecoder.class);
    Decoder decoder;

    public RedisCommandDecoder(RedisConfig config)
    {
        if (config.auth != null && !config.auth.isEmpty())
        {
            decoder = new AuthDecoder(config.auth,this,config.userName);
        } else
        {
            decoder = new DefalutDcoder();
        }
    }

    public void setDecoder(Decoder decoder)
    {
        this.decoder = decoder;
    }


    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
    {
        decoder.decode(ctx, in, out);
    }

    public void init()
    {
        decoder.reset();
    }
}
