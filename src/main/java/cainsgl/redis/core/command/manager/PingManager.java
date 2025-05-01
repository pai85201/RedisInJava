package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.processor.PingProcessor;

public class PingManager extends AbstractCommandManager
{
    public PingManager()
    {
        super(true, Thread.MIN_PRIORITY, new PingProcessor());
    }
}
