package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.processor.AuthProcessor;
import cainsgl.redis.core.command.processor.CommandProcessor;
import cainsgl.redis.core.command.processor.PingProcessor;

//所有简单的命令，都让这个manager来管理
public class SimpleManager extends AbstractCommandManager
{

    public SimpleManager()
    {
        super(true, new CommandProcessor(),new PingProcessor(),new AuthProcessor());
    }
}
