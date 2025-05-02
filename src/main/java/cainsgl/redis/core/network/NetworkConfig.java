package cainsgl.redis.core.network;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.CommandProcessor;

import java.util.HashMap;
import java.util.Map;

public class NetworkConfig
{
    private static final Map<String, CommandProcessor.Command> MANAGERS = new HashMap<>();

    public static Map<String, CommandProcessor.Command> getManagers()
    {
        return MANAGERS;
    }

    public static CommandProcessor.Command get(String commandName)
    {
        return MANAGERS.get(commandName);
    }

    public static void register(CommandProcessor.Command command)
    {
        MANAGERS.put(command.cmd, command);
    }
}
