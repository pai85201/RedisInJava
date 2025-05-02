package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.network.RedisServer;
import cainsgl.redis.core.storage.ExpireObj;
import cainsgl.redis.core.storage.RedisProxyObj;
import cainsgl.redis.core.utils.EventWorkGroups;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ExpireManager
{
    private static final Logger log = LoggerFactory.getLogger(ExpireManager.class);
    private static final int BUCKET_SIZE = 16;
    private static final List<List<RedisProxyObj<?>>> buckets = new ArrayList<>(BUCKET_SIZE);
  //  public static final long SystemStartTime = System.currentTimeMillis();
    public static volatile long updateTime = 0L;
    public static final EventLoop ADDTHREAD = EventWorkGroups.ExpireThread.next();
    public static final EventLoop GCASSIST= EventWorkGroups.ExpireThread.next();

    public static void register(RedisProxyObj<?> obj)
    {
        ADDTHREAD.execute(() -> {
            int hash=(int) obj.expire & 0x000F;
            log.debug("add expire obj {} in {} solt", obj,hash);
            List<RedisProxyObj<?>> expireObjs = buckets.get(hash);
            int index = 0;
            // 找到合适的插入位置
            while (index < expireObjs.size() && expireObjs.get(index).expire < obj.expire) {
                index++;
            }
            // 插入元素
            expireObjs.add( obj);
        });
    }

    static
    {
        for (int i = 0; i < BUCKET_SIZE; i++)
        {
            buckets.add(new ArrayList<>());
        }
        executeGc(0, 6, ADDTHREAD,100);
        executeGc(6, 16, GCASSIST,50);
    }

    private static void executeGc(int from, int to, EventLoop eventLoop,int gcNum)
    {
        eventLoop.scheduleAtFixedRate(() -> {
            long now= System.currentTimeMillis();
            updateTime = now;
            for (int i = from; i < to; i++)
            {
                List<RedisProxyObj<?>> expireObjs = buckets.get(i);
                Iterator<RedisProxyObj<?>> iterator = expireObjs.iterator();
                while (iterator.hasNext())
                {
                    RedisProxyObj<?> next = iterator.next();
                    if (next.expire < now)
                    {
                        //说明已经存活时间已经过了，现在应该清除他
                        if(eventLoop==ADDTHREAD)
                        {
                            //直接删除
                            log.debug("gc remove this obj {}",next.getRes());
                            next.del();
                            iterator.remove();
                        }else
                        {
                            next.del();
                            ADDTHREAD.execute(iterator::remove);
                        }
                    } else
                    {
                        //由于这个是有序的，我们直接给跳过，不再遍历
                        return;
                    }
                }
            }
        }, 0, gcNum, TimeUnit.MILLISECONDS);

    }
}
