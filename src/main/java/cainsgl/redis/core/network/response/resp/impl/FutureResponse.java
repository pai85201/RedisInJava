package cainsgl.redis.core.network.response.resp.impl;


import cainsgl.redis.core.network.response.resp.RESP2Response;
import io.netty.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class FutureResponse<T> implements RESP2Response
{
    private FutureListener<T> listener;
    public Future<T> future;

    public FutureResponse(Future<T> future)
    {
        this.future = future;
    }

    public FutureResponse<T> addListener(FutureListener<T> futureListener)
    {
        listener = futureListener;
        return this;
    }

    public RESP2Response callback(Object callRes) throws ExecutionException, InterruptedException
    {
        return listener.process((T)callRes);
    }

    @Override
    public String serialization()
    {
        throw new UnsupportedOperationException("Future RESPONSE serialization is not implemented yet");
    }
}
