package cainsgl.redis.core.command;

import io.netty.channel.EventLoopGroup;

public interface CommandManager
{
    void submit(Runnable task);

    EventLoopGroup getWorkGroup();
}
