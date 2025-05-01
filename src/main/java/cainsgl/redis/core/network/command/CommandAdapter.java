package cainsgl.redis.core.network.command;



import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.exception.CommandException;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.NetworkConfig;
import cainsgl.redis.core.network.RedisCommandDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class CommandAdapter
{
    private static final Logger log = LoggerFactory.getLogger(CommandAdapter.class);
    int dataNumber;
    boolean argsProcessing =false;

    public CommandAdapter(int num)
    {
        dataNumber=num;
    }
    //需要查看究竟是什么data
    AbstractCommandProcessor<?> abstractCommandProcessor = null;
    List<String> args=new ArrayList<String>();
    public boolean pullData(byte[] bytes)
    {
       if(bytes[0]=='$')
       {
         return false;
       }
       if(!argsProcessing)
       {
           String commandName = new String(bytes, StandardCharsets.UTF_8);
           abstractCommandProcessor = NetworkConfig.get(commandName);
           if(abstractCommandProcessor ==null)
           {
               abstractCommandProcessor = NetworkConfig.get(commandName.toLowerCase());
               if(abstractCommandProcessor ==null)
               {
                   if(args.isEmpty())
                   {
                       throw new CommandException(commandName,", with args beginning with:");
                   }
                   throw new CommandException(commandName,", with args beginning with:"+args.getLast());
               }
           }
           if(dataNumber> abstractCommandProcessor.maxArgsCount())
           {
               throw new RedisException("wrong number of arguments for '"+commandName+"' command");
           }
           dataNumber--;
           argsProcessing=true;
       }else
       {
           args.add(new String(bytes, StandardCharsets.UTF_8));
           dataNumber--;
       }
        return dataNumber == 0;
    }
    public AbstractCommandProcessor getCommand()
    {
        abstractCommandProcessor.processArgs(args);
        log.debug("execute command:{} {}" ,abstractCommandProcessor.getCommand(),args);
        return abstractCommandProcessor;
    }
}
