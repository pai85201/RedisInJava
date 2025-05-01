package cainsgl.redis.core.command.parameter;


import java.util.List;


public class RedisParameter
{
   public Class<?> type;
    public List<String> only;
    public RedisParameter(Class<?> type,List<String> only)
    {
        this.type = type;
        this.only = only;
    }
}
