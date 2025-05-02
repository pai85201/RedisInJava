package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.storage.RedisObj;

public interface ExpirableProducer
{
    void del(RedisObj<?> r,Object ...param);
}
