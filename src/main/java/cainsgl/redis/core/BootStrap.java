package cainsgl.redis.core;

import cainsgl.redis.core.config.RedisConfig;
import cainsgl.redis.core.network.RedisServer;

import java.util.Scanner;

public class BootStrap
{
    public static void main(String[] args) throws Exception
    {
        new RedisServer().start(new RedisConfig()
//                .setUserName("cainsgl")
//                .setAuth("123456")
                .setPort(6379));
    }
}
