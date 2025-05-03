package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.processor.common.DebugProcessor;
import cainsgl.redis.core.command.processor.common.PingProcessor;
import cainsgl.redis.core.command.processor.common.TTLProcessor;

//所有简单的命令，都让这个manager来管理
public class SimpleManager extends AbstractCommandManager
{

    public SimpleManager()
    {
        super(true, new PingProcessor(),new DebugProcessor(),new TTLProcessor());
    }
}
