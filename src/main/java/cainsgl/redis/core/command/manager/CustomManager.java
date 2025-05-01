package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.processor.CommandProcessor;
import cainsgl.redis.core.command.processor.CustomProcessor;

public class CustomManager extends AbstractCommandManager
{
    public CustomManager()
    {
        super(true, new CustomProcessor());
    }
}
