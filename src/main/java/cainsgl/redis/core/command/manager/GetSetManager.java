package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.processor.GetProcessor;
import cainsgl.redis.core.command.processor.SetProcessor;

import java.util.HashMap;

public class GetSetManager extends AbstractCommandManager
{
    public HashMap<String,String> test=new HashMap<>();
    public GetSetManager()
    {
        SetProcessor  set =new SetProcessor();
        GetProcessor get =new GetProcessor();
        super(false, set, get);
    }

}
