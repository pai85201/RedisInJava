package cainsgl.redis.core.command.processor.common;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.SimpleManager;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.network.response.resp.impl.FutureResponse;
import cainsgl.redis.core.network.response.resp.impl.NumberResponse;
import cainsgl.redis.core.storage.ExpireObj;
import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.storage.RedisProxyObj;
import cainsgl.redis.core.storage.share.ExpireManager;
import cainsgl.redis.core.storage.share.MainMemory;
import io.netty.util.concurrent.Future;

import java.util.List;

public class TTLProcessor extends AbstractCommandProcessor<SimpleManager>
{
    public TTLProcessor()
    {
        super("ttl", 2, 2);
    }

    String key;

    @Override
    public RESP2Response execute() throws RedisException
    {
        Future<RedisObj<?>> r = MainMemory.get(key);
        if(r==null)
        {
            return EnumResponse.nil;
        }
        return new FutureResponse<>(r).addListener((redisObj) -> {
            if (redisObj instanceof RedisProxyObj<?> obj)
            {
                return new NumberResponse((obj.expire - ExpireManager.updateTime)/1000);
            }
            //这里只要不是自己手动去new的，都不会返回他
            return new NumberResponse(-1);
        });
    }

    @Override
    public void processArgs(List<String> args) throws RedisException
    {
        this.key = args.get(0);
    }
}
