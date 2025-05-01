package cainsgl.redis.core.command;

import cainsgl.redis.core.storage.share.ShareGet;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.Map;

public abstract class AbstractCommandManager
{
    private final static EventLoopGroup unpopularWorkGroup=new NioEventLoopGroup(1, new DefaultThreadFactory("unpopularWorkGroup",3));
    private final EventLoopGroup MyWorkGroup;

   // private final static Map<AbstractCommandProcessor<?>, ShareGet> ;
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
            //去检查类型，看看是否继承了share的几种，后面添加到回调方法里去



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
