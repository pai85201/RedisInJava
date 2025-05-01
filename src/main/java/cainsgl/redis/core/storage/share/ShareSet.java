package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.storage.RedisObj;

public interface ShareSet
{
    void set(String key, RedisObj<?> value);
}
