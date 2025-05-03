package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.storage.RedisProxyObj;
import cainsgl.redis.core.utils.EventWorkGroups;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExpireManager
{
    private static final Logger log = LoggerFactory.getLogger(ExpireManager.class);
    private static final int BUCKET_SIZE = 16; // 16个桶
    private static final List<List<RedisProxyObj<?>>> buckets = new ArrayList<>(BUCKET_SIZE);
    //  public static final long SystemStartTime = System.currentTimeMillis();
    public static volatile long updateTime = 0L;
    public static final EventLoop ADDTHREAD = EventWorkGroups.ExpireThread.next(); // 处理添加对象操作
    public static final EventLoop GCASSIST = EventWorkGroups.ExpireThread.next(); // 处理GC流程

    public static void del(RedisProxyObj<?> obj) {
        int hash = (int) obj.expire & 0x000F;
        log.debug("del expire obj {} in {} solt", obj, hash);
        List<RedisProxyObj<?>> expireObjs = buckets.get(hash);
        expireObjs.remove(obj);
    }

    public static void register(RedisProxyObj<?> obj) {
        ADDTHREAD.execute(() -> {
            int hash = (int) obj.expire & 0x000F;
            log.debug("add expire obj {} in {} solt", obj, hash);
            List<RedisProxyObj<?>> expireObjs = buckets.get(hash);
            // 插入元素
            expireObjs.add(obj);
        });
    }

    static {
        for (int i = 0; i < BUCKET_SIZE; i++) {
            buckets.add(new ArrayList<>());
        }
        executeGc(0, 6, ADDTHREAD, 100);
        executeGc(6, 16, GCASSIST, 50);
    }

    private static void executeGc(int from, int to, EventLoop eventLoop, int gcNum) {
        eventLoop.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            updateTime = now;
            // 遍历该线程负责的桶 - 编号 from ~ to
            for (int i = from; i < to; i++) {
                // 拿到某一个桶中 expireObj 集合
                List<RedisProxyObj<?>> expireObjs = buckets.get(i);
                Iterator<RedisProxyObj<?>> iterator = expireObjs.iterator();
                while (iterator.hasNext()) {
                    // 拿到具体的某一个 expireObj，对其过期时间进行判断
                    RedisProxyObj<?> next = iterator.next();
                    if (next.expire < now) {
                        //说明已经存活时间已经过了，现在应该清除他
                        if (eventLoop == ADDTHREAD) {
                            //直接删除
                            next.del();
                            iterator.remove();
                            log.debug("gc remove this obj {}", buckets);
                        } else {
                            next.del();
                            ADDTHREAD.execute(() -> {
                                iterator.remove();
                                log.debug("gc remove this obj {}", buckets);
                            });
                        }
                    } else {
                        //由于这个是有序的，我们直接给跳过，不再遍历
                        return;
                    }
                }
            }
        }, 0, gcNum, TimeUnit.MILLISECONDS);
    }
}
