package cainsgl.redis.core.utils;

import cainsgl.redis.core.config.Stopable;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ExecutionException;

public class EventWorkGroups implements Stopable
{
    public final static EventLoopGroup ASYNWorkGroup = new NioEventLoopGroup(2,new DefaultThreadFactory("ASYNWorkGroup",3));
    public final static EventLoopGroup unpopularWorkGroup=new NioEventLoopGroup(1, new DefaultThreadFactory("unpopularWorkGroup",3));
    public final static EventLoopGroup  MainThread=new NioEventLoopGroup(1,new DefaultThreadFactory("MainThread",true));
    public final static EventLoopGroup  ExpireThread=new NioEventLoopGroup(2,new DefaultThreadFactory("ExpireThread",true));
    public final EventLoopGroup ServerBoosGroup;
    public final EventLoopGroup ServerWorkerGroup;

    public EventWorkGroups(EventLoopGroup ServerBoosGroup, EventLoopGroup ServerWorkerGroup)
    {
        this.ServerBoosGroup = ServerBoosGroup;
        this.ServerWorkerGroup = ServerWorkerGroup;
    }


    @Override
    public void stop() throws InterruptedException, ExecutionException
    {
        ASYNWorkGroup.shutdownGracefully().get();
        unpopularWorkGroup.shutdownGracefully().get();
    }
}
