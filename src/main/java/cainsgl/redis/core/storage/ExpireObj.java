package cainsgl.redis.core.storage;

import cainsgl.redis.core.storage.share.ExpireManager;
@Deprecated
public  class ExpireObj<T>
{
    public long expire;
    public RedisProxyObj<T> obj;

    public ExpireObj(long expire, RedisProxyObj<T> obj)
    {
     //   ExpireManager.register(this);
        this.expire = expire+System.currentTimeMillis();
        this.obj = obj;
    }

    @Override
    public int hashCode()
    {
        return (int) expire;
    }
    public void del()
    {
        obj.isDel=true;
    }
}
