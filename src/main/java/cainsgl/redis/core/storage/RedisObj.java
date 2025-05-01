package cainsgl.redis.core.storage;


import cainsgl.redis.core.storage.share.MainMemory;
import cainsgl.redis.core.storage.share.ShareDel;

import cainsgl.redis.core.storage.share.ShareSet;

public  class RedisObj<T>
{
    String key;
    long expire;
    public T value;

    ShareSet set;
    ShareDel del;
    public final void set(String key,T value)
    {
        this.value=value;
        if(set != null)
        {
            set.set(key,this);
        }
    }
    public final void del(String key)
    {
        MainMemory.del(key);
        if(del != null)
        {
            del.del(key);
        }
    }
    public RedisObj(String key,long expire,T value)
    {
        this.key = key;
        this.expire = expire;
        this.value = value;
        MainMemory.put(key,this);
    }
}
