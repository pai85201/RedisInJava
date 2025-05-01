package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.processor.CommandProcessor;

public class CommandManager extends AbstractCommandManager
{
    public CommandManager()
    {
        super(true,Thread.MIN_PRIORITY, new CommandProcessor());
    }
}
