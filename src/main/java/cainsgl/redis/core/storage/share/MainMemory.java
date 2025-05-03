package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.config.Stopable;
import cainsgl.redis.core.storage.RedisProxyObj;
import cainsgl.redis.core.storage.redisObj.RedisObj;
import cainsgl.redis.core.utils.EventWorkGroups;
import cainsgl.redis.core.utils.ref.KeyWeakReference;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MainMemory implements Stopable
{
    public static final Map<String, KeyWeakReference> DICT = new WeakHashMap<>();
    private static final ReferenceQueue<RedisObj<?>> referenceQueue = new ReferenceQueue<>();
    static {
       EventLoop MAINLOOP = EventWorkGroups.ExpireThread.next();
        MAINLOOP.scheduleAtFixedRate(()->{
            while(true)
            {
                KeyWeakReference poll = (KeyWeakReference)referenceQueue.poll();
                if(poll != null) {
                    //说明被回收了
                    DICT.remove(poll.key);
                }else
                {
                    break;
                }
            }
        },0, 100, TimeUnit.MILLISECONDS);
    }
    public static void put(String key, RedisObj<?> obj)
    {
        EventWorkGroups.MainThread.execute(() -> DICT.put(key, new KeyWeakReference(obj,referenceQueue,key) ));
    }

    public static void del(String key)
    {
        EventWorkGroups.MainThread.execute(() -> {
            RedisObj<?> remove = DICT.remove(key).get();
            if(remove instanceof RedisProxyObj<?> r)
            {
                ExpireManager.del(r);
            }
        });
    }

    public static Future<RedisObj<?>> get(String key)
    {
        Promise<RedisObj<?>> promise = EventWorkGroups.MainThread.next().newPromise();
        EventWorkGroups.MainThread.execute(() -> {
            RedisObj<?> r = DICT.get(key).get();
            if (r == null)
            {
                DICT.remove(key);
            }
            promise.setSuccess(r);
            // 将结果设置到 Promise（确保在 EventLoop 线程）
            //    group.execute(() -> promise.setSuccess(r));
        });
        return promise;
    }


    @Override
    public void stop() throws InterruptedException, ExecutionException
    {
        EventWorkGroups.MainThread.shutdownGracefully();
        DICT.clear();
    }
}
