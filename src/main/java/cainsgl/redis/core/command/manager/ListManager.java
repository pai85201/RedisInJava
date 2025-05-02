package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.processor.list.LPushProcessor;
import cainsgl.redis.core.command.processor.list.RangeProcessor;
import cainsgl.redis.core.storage.RedisObj;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ListManager extends AbstractCommandManager
{
    public Map<String, RedisObj<LinkedList<String>>> redisObjGroup = new HashMap<>();
    public ListManager()
    {
        super(false,new LPushProcessor(),new RangeProcessor());
    }
}
