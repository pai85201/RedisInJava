package cainsgl.redis.core.command.processor.common;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.SimpleManager;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.*;
import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.storage.share.MainMemory;
import cainsgl.redis.core.utils.ref.KeyWeakReference;
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
        Iterator<Map.Entry<String, KeyWeakReference>> iterator = MainMemory.DICT.entrySet().iterator();
        List<Future<RedisObj<?>>> redisObjs = new ArrayList<>(MainMemory.DICT.size());
        List<String> keys = new ArrayList<>(MainMemory.DICT.size());
        while (iterator.hasNext())
        {
            Map.Entry<String, KeyWeakReference> next = iterator.next();
            Future<RedisObj<?>> redisObjFuture = MainMemory.get(next.getKey());
            keys.add(next.getKey());
            redisObjs.add(redisObjFuture);
        }
        FutureResponse[] resp2Responses = new FutureResponse[redisObjs.size()];
        for (int i = 0; i < resp2Responses.length; i++)
        {
            int finalI = i;
            resp2Responses[i] = new FutureResponse<>(redisObjs.get(i)).addListener((RedisObj<?> robj) -> {
                //转换器，把转成对于的协议
                String s = keys.get(finalI);
                return new ArrayResponse(new StringResponse("key:"+s),robj.getRes());
            });
        }
        return new ArrayResponse(resp2Responses);
    }

    @Override
    public void processArgs(List<String> args) throws RedisException
    {

    }
}
