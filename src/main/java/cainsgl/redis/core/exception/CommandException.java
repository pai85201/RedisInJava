package cainsgl.redis.core.exception;

public class CommandException extends RedisException
{
    public CommandException(String commandName,String message)
    {
        super("ERR unknown command '"+commandName+"' "+message);

    }
}
