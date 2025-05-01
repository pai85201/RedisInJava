package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.storage.RedisObj;

import java.util.HashMap;
import java.util.Map;

public class MainMemory
{
    public static final Map<String, RedisObj<?>> DICT=new HashMap<>();
}
