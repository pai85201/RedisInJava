package cainsgl.redis.core.command.processor.string;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.GetSetManager;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.NumberResponse;
import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.storage.share.MainMemory;

import java.util.List;
import java.util.Map;

public class DelProcessor extends AbstractCommandProcessor<GetSetManager>
{
    public DelProcessor()
    {
        super("del", Integer.MAX_VALUE, 2);
    }

    @Override
    public RESP2Response execute() throws RedisException
    {
        keys.forEach(MainMemory::del);
        Map<String, RedisObj<?>> redisObjMap = getManager().redisObjMap;
        int res=0;
        for(int i=0;i<keys.size();i++)
        {
            RedisObj<?> remove = redisObjMap.remove(keys.get(i));
            if(remove != null)
            {
                res++;
            }
        }
        return new NumberResponse(res);
    }
    List<String> keys;
    @Override
    public void processArgs(List<String> args) throws RedisException
    {
        this.keys = args;
    }
}
