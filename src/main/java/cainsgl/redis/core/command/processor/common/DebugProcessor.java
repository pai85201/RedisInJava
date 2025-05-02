package cainsgl.redis.core.command.processor.common;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.SimpleManager;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.*;
import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.storage.share.MainMemory;
import io.netty.util.concurrent.Future;

import java.util.*;

public class DebugProcessor extends AbstractCommandProcessor<SimpleManager>
{
    public DebugProcessor()
    {
        super("debug", 1, 1);
    }

    @Override
    public RESP2Response execute() throws RedisException
    {
        Iterator<Map.Entry<String, RedisObj<?>>> iterator = MainMemory.DICT.entrySet().iterator();
        List<Future<RedisObj<?>>> redisObjs = new ArrayList<>(MainMemory.DICT.size());
        while (iterator.hasNext())
        {
            Map.Entry<String, RedisObj<?>> next = iterator.next();
            Future<RedisObj<?>> redisObjFuture = MainMemory.get(next.getKey(), this);
            redisObjs.add(redisObjFuture);
        }
        FutureResponse[] resp2Responses = new FutureResponse[redisObjs.size()];

        for (int i = 0; i < resp2Responses.length; i++)
        {
            resp2Responses[i] = new FutureResponse<>(redisObjs.get(i)).addListener((RedisObj<?> robj) -> {
                //转换器，把转成对于的协议
                Object r = robj.value;
                if (r instanceof Integer integer)
                {
                    return new NumberResponse(integer);
                } else
                {
                    return new StringResponse(r.toString());
                }
            });
        }
        return new ArrayResponse(resp2Responses);
    }

    @Override
    public void processArgs(List<String> args) throws RedisException
    {

    }
}
