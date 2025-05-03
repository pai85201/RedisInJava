package cainsgl.redis.core.utils.ref;

import cainsgl.redis.core.storage.redisObj.RedisObj;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class KeyWeakReference extends WeakReference<RedisObj<?>>
{
    public String key;
    public KeyWeakReference(RedisObj<?> referent, ReferenceQueue<RedisObj<?>> q,String key)
    {
        super(referent, q);
        this.key = key;
    }
}
