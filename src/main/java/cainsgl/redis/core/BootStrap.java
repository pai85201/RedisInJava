package cainsgl.redis.core;

import cainsgl.redis.core.config.RedisConfig;
import cainsgl.redis.core.network.RedisServer;

public class BootStrap
{
    public static void main(String[] args) throws Exception
    {
        new RedisServer(new RedisConfig()).start(6379);
    }
}
