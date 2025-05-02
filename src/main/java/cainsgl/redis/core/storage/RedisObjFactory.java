package cainsgl.redis.core.storage;


import cainsgl.redis.core.command.CommandManager;
import cainsgl.redis.core.command.CommandProcessor;
import cainsgl.redis.core.storage.share.ExpirableProducer;
import io.netty.channel.EventLoopGroup;

public class RedisObjFactory
{

    public static RedisObj<?> produce(RedisObj<?> obj, long expire,ExpirableProducer willDell, Object... params)
    {
        CommandProcessor proc = (CommandProcessor) willDell;
        CommandManager manager = proc.getManager();
        EventLoopGroup workGroup = manager.getWorkGroup();
        return new RedisProxyObj<>(obj, expire, () -> {
            workGroup.execute(() -> {
                willDell.del(obj, params);
            });
        });
    }

    public static RedisObj<?> produce(String obj, long expire)
    {
        return new RedisProxyObj<>(produceReallyObj(obj), expire);
    }

    public static RedisObj<?> produce(String obj, long expire, ExpirableProducer willDell, Object... params)
    {
        //去拿到processor里的del方法并代理
        CommandProcessor proc = (CommandProcessor) willDell;
        CommandManager manager = proc.getManager();
        EventLoopGroup workGroup = manager.getWorkGroup();
        RedisObj<?> redisObj = produceReallyObj(obj);
        return new RedisProxyObj<>(redisObj, expire, () -> {
            workGroup.execute(() -> {
                willDell.del(redisObj, params);
            });
        });
    }

    private static RedisObj<?> produceReallyObj(String obj)
    {
        try
        {
            int v = Integer.parseInt(obj);
            return new RedisNumberObj(v);
        } catch (Exception e)
        {
            return new RedisStringObj(obj);
        }
    }
}
