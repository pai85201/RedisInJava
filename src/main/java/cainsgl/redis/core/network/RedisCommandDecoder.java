package cainsgl.redis.core.network;


import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.network.command.CommandAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class RedisCommandDecoder extends ByteToMessageDecoder
{

    CommandAdapter cmdAdapter;
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        while(in.readableBytes() >0)
        {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            if(cmdAdapter!=null)
            {
                //应该处理数据
                if(cmdAdapter.pullData(bytes))
                {
                    AbstractCommandProcessor<?>.Command command = cmdAdapter.getCommand();
                    out.add(command);
                    cmdAdapter=null;
                }
                return;
            }
            if(bytes[0]=='*')
            {
                //获取参数个数
                int dataNum=0;
                for(int i=1;i<bytes.length;i++)
                {
                    dataNum=bytes[i]-'0'+dataNum*10;
                }
                cmdAdapter=new CommandAdapter(dataNum);
            }
        }
    }

    public void init()
    {
        cmdAdapter=null;
    }
}
