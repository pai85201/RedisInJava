package cainsgl.redis.core.command;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

public abstract class AbstractCommandManager
{
    private final static EventLoopGroup unpopularWorkGroup=new NioEventLoopGroup(1, new DefaultThreadFactory("unpopularWorkGroup",3));

    private final EventLoopGroup MyWorkGroup;
    public  AbstractCommandManager(boolean sharedThread, int priority, AbstractCommandProcessor<?>... processors)
    {
        if(sharedThread)
        {
            MyWorkGroup=unpopularWorkGroup;
        }else
        {
            MyWorkGroup=new NioEventLoopGroup(1, new DefaultThreadFactory("CommandManager", priority));
        }
        for(AbstractCommandProcessor<?> processor : processors)
        {
            processor.setManager(this);
        }

    }
    public   AbstractCommandManager(boolean sharedThread, AbstractCommandProcessor<?>... abstractCommandProcessor)
    {
        this(sharedThread,Thread.MAX_PRIORITY, abstractCommandProcessor);
    }
    public void submit(Runnable task)
    {
        MyWorkGroup.submit(task);
    }
}
