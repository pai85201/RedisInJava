package cainsgl.redis.core.command;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

public abstract class AbstractCommandManager
{
    private final static EventLoopGroup unpopularWorkGroup=new NioEventLoopGroup(1, new DefaultThreadFactory("unpopularWorkGroup",3));
    public  AbstractCommandManager(boolean sharedThread, int priority, AbstractCommandProcessor... processors)
    {
        for(AbstractCommandProcessor processor : processors)
        {
            processor.setManager(this);
            if(sharedThread)
            {
                processor.setWorkerGroup(unpopularWorkGroup);

            }else
            {
                NioEventLoopGroup commandManager = new NioEventLoopGroup(1, new DefaultThreadFactory("CommandManager", priority));
                processor.setWorkerGroup(commandManager);
            }
        }

    }
    public   AbstractCommandManager(boolean sharedThread, AbstractCommandProcessor... abstractCommandProcessor)
    {
        this(sharedThread,Thread.MAX_PRIORITY, abstractCommandProcessor);
    }

}
