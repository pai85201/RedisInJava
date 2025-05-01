package cainsgl.redis.core.network.decoder;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.processor.AuthProcessor;
import cainsgl.redis.core.network.RedisCommandDecoder;
import cainsgl.redis.core.network.command.CommandAdapter;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.network.response.resp.impl.ErrorResponse;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class AuthDecoder implements Decoder
{
    String auth;
    RedisCommandDecoder redisDecoder;
    String userName;
    public AuthDecoder(String auth, RedisCommandDecoder redisDecoder,String userName)
    {
        this.auth = auth;
        this.redisDecoder = redisDecoder;
        this.userName = userName;
    }

    CommandAdapter cmdAdapter;

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
    {
        while (in.readableBytes() > 0)
        {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            if (cmdAdapter != null)
            {
                //应该处理数据
                if (cmdAdapter.pullData(bytes))
                {
                    AbstractCommandProcessor.Command command = cmdAdapter.getCommand();
                    if (command.cmd.equalsIgnoreCase("auth"))
                    {
                        AuthProcessor processor = (AuthProcessor) command.processor;
                        processor.setAuth(auth);
                        processor.setUserName(userName);
                        RESP2Response execute = command.processor.execute();
                        out.add(execute);
                        if (execute == EnumResponse.ok)
                        {
                            //密码正确，替换为默认Decoder
                            redisDecoder.setDecoder(new DefalutDcoder());
                        }
                    } else
                    {
                        out.add(new ErrorResponse("NOAUTH Authentication required."));
                    }
                    cmdAdapter = null;
                }
                return;
            }
            if (bytes[0] == '*')
            {
                //获取参数个数
                int dataNum = 0;
                for (int i = 1; i < bytes.length; i++)
                {
                    dataNum = bytes[i] - '0' + dataNum * 10;
                }
                cmdAdapter = new CommandAdapter(dataNum);
            }
        }
    }

    @Override
    public void reset()
    {
        cmdAdapter=null;
    }
}
