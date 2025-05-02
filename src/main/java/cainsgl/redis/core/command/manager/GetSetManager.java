package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.processor.string.GetProcessor;
import cainsgl.redis.core.command.processor.string.SetProcessor;
import cainsgl.redis.core.storage.RedisObj;

import java.util.HashMap;
import java.util.Map;

public class GetSetManager extends AbstractCommandManager
{


    public GetSetManager()
    {
        super(false, new SetProcessor(), new GetProcessor());
    }

    public Map<String, RedisObj<?>> redisObjMap = new HashMap<>();
}
