package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.utils.EventWorkGroups;
import io.netty.channel.EventLoopGroup;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainMemory implements Closeable
{
    public static final Map<String, RedisObj<?>> DICT = new HashMap<>();


    public static void put(String key, RedisObj<?> obj)
    {
        EventWorkGroups.MainThread.execute(() -> DICT.put(key, obj));

    }

    public static void del(String key)
    {
        EventWorkGroups.MainThread.execute(() -> DICT.remove(key));
    }

    public static Future<RedisObj<?>> get(String key, EventLoopGroup group)
    {
        Promise<RedisObj<?>> promise = group.next().newPromise();
        EventWorkGroups.MainThread.execute(() -> {
            RedisObj<?> r = DICT.get(key);
            // 将结果设置到 Promise（确保在 EventLoop 线程）
            group.execute(() -> promise.setSuccess(r));
        });
        return promise;
    }

    @Override
    public void close() throws IOException
    {
        EventWorkGroups.MainThread.shutdownGracefully();
        DICT.clear();
    }
}
