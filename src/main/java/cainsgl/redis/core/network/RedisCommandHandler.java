package cainsgl.redis.core.network;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.ArrayResponse;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.network.response.resp.impl.ErrorResponse;
import cainsgl.redis.core.network.response.resp.impl.FutureResponse;
import cainsgl.redis.core.utils.EventWorkGroups;
import cainsgl.redis.core.utils.WriteDataUtil;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
public class RedisCommandHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger log = LoggerFactory.getLogger(RedisCommandHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if (msg instanceof AbstractCommandProcessor.Command command)
        {
            command.processor.getManager().submit(() -> {
                RESP2Response res = command.processor.execute();
                if (res instanceof FutureResponse<?> f)
                {
                    asynProcessor(f, ctx, command.cmd);
                } else
                {
                    if (res instanceof ArrayResponse f)
                    {
                        if (f.isFuture)
                        {
                            asynArrayProcessor(f, ctx, command.cmd);
                            return;
                        }
                    }
                    log.debug("execute {} and Response=> {}", command.cmd, res.toString());
                    WriteDataUtil.writeData(ctx, res);
                }
            });
            return;
        }
        //处理直接是请求的
        if (msg instanceof RESP2Response res)
        {
            WriteDataUtil.writeData(ctx, res);

        }
    }

    private void asynProcessor(FutureResponse<?> f, ChannelHandlerContext ctx, String cmd)
    {
        EventWorkGroups.ASYNWorkGroup.submit(() -> {
            try
            {
                Object o = f.future.get();
                if (o == null)
                {
                    WriteDataUtil.writeData(ctx, EnumResponse.nil);
                }
                RESP2Response callback = f.callback(o);
                WriteDataUtil.writeData(ctx, callback);
                log.debug("execute {} and Response=> {}", cmd, callback);
            } catch (Exception e)
            {
                log.error("error processing future", e);
            }
        });
    }

    private void asynArrayProcessor(ArrayResponse arrayResponse, ChannelHandlerContext ctx, String cmd)
    {
        EventWorkGroups.ASYNWorkGroup.submit(() -> {
            FutureResponse<?>[] responses = (FutureResponse<?>[]) arrayResponse.responses;
            try
            {
                List<RESP2Response> res = new ArrayList<>();
                for (FutureResponse<?> r : responses)
                {
                    if (r == null)
                    {
                        continue;
                    }

                    Object o = r.future.get();
                    if (o == null)
                    {
                        continue;
                    }
                    RESP2Response callback = r.callback(o);
                    res.add(callback);

                }
                WriteDataUtil.writeData(ctx, new ArrayResponse(res));
            } catch (Exception e)
            {
                log.error("error processing array future", e);
            }
        });


    }

}

