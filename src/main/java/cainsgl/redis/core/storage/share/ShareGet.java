package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.storage.RedisObj;

public interface ShareGet
{
    RedisObj<?> get(String key);
}
