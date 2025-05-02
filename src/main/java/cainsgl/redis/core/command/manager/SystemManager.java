package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.processor.common.CommandProcessor;
import cainsgl.redis.core.command.processor.common.DebugProcessor;
import cainsgl.redis.core.command.processor.safe.AuthProcessor;
import cainsgl.redis.core.command.processor.system.GcProcessor;

public class SystemManager extends AbstractCommandManager
{
    public SystemManager()
    {
        super(true, new GcProcessor(),new CommandProcessor(),new AuthProcessor(),new DebugProcessor());
    }
}
