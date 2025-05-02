package cainsgl.redis.core.storage;

import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;

import cainsgl.redis.core.storage.share.ExpirableProducer;
import cainsgl.redis.core.storage.share.ExpireManager;

public class RedisProxyObj<T> implements RedisObj<T>
{
    RedisObj<T> proxy;
    public long expire;
    //  public long reallyExpire;
    Runnable call;
    public RedisProxyObj(RedisObj<T> proxy, long expire)
    {
        this.proxy = proxy;
        //  this.now = ;//每次都切换到内核态会在高并发条件下造成不好的影响，我们去gc线程拿即可
        if (expire > 0)
        {
            // new ExpireObj<>(expire,this);
            ExpireManager.register(this);
            this.expire = expire + ExpireManager.updateTime;
        }
        // reallyExpire = this.expire;
    }
    public RedisProxyObj(RedisObj<T> proxy, long expire,Runnable call)
    {
        this(proxy, expire);
        this.call = call;

    }
    // volatile ?
    boolean isDel;


    @Override
    public T getData()
    {
        if (isDel)
        {
            return null;
        }
        return proxy.getData();
    }

    @Override
    public RESP2Response getRes()
    {
        if (isDel)
        {
            return EnumResponse.nil;
        }
        return proxy.getRes();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj==this)
        {
            return true;
        }else
        {
            if(obj instanceof RedisProxyObj<?> proxyObj)
            {
                return proxyObj.expire==this.expire;
            }else
            {
                return false;
            }
        }
    }

    @Override
    public int hashCode()
    {
        return (int) expire;
    }

    public void del()
    {
        isDel = true;
        this.proxy=null;
        if(call!=null)
        {

            call.run();
        }

        //回调manager
    }
}
