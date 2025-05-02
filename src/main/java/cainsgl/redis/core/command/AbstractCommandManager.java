package cainsgl.redis.core.command;


import cainsgl.redis.core.utils.EventWorkGroups;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;


public abstract class AbstractCommandManager {

    private final EventLoopGroup MyWorkGroup;

   // private final static Map<AbstractCommandProcessor<?>, ShareGet> ;
    public  AbstractCommandManager(boolean sharedThread, int priority, AbstractCommandProcessor<?>... processors) {
        if(sharedThread) {
            MyWorkGroup= EventWorkGroups.unpopularWorkGroup;
        }else {
            MyWorkGroup=new NioEventLoopGroup(1, new DefaultThreadFactory("CommandManager", priority));
        }
        for(AbstractCommandProcessor<?> processor : processors) {
            processor.setManager(this);
            //去检查类型，看看是否继承了share的几种，后面添加到回调方法里去
        }
    }

    public final EventLoopGroup getWorkGroup()
    {
        return MyWorkGroup;
    }
    public  AbstractCommandManager(boolean sharedThread, AbstractCommandProcessor<?>... abstractCommandProcessor)
    {
        this(sharedThread,Thread.MAX_PRIORITY, abstractCommandProcessor);
    }
    public final void submit(Runnable task)
    {
        MyWorkGroup.submit(task);
    }
}
