package cainsgl.redis.core.utils;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.Closeable;
import java.io.IOException;

public class EventWorkGroups implements Closeable
{
    public final static EventLoopGroup ASYNWorkGroup = new NioEventLoopGroup(1,new DefaultThreadFactory("ASYNWorkGroup",3));
    public final static EventLoopGroup unpopularWorkGroup=new NioEventLoopGroup(1, new DefaultThreadFactory("unpopularWorkGroup",3));
    public final static EventLoopGroup  MainThread=new NioEventLoopGroup(1,new DefaultThreadFactory("MainThread",true));

    @Override
    public void close() throws IOException
    {
        ASYNWorkGroup.shutdownGracefully();
        unpopularWorkGroup.shutdownGracefully();
    }
}
