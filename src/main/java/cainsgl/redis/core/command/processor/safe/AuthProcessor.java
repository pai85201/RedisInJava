package cainsgl.redis.core.command.processor.safe;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.SimpleManager;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.network.response.resp.impl.ErrorResponse;

import java.util.List;

public class AuthProcessor extends AbstractCommandProcessor<SimpleManager>
{
    public AuthProcessor()
    {
        super("auth", 3, 2);
    }
    String passWord;
    String auth;
    String authUserName;
    String userName;
    @Override
    public RESP2Response execute() throws RedisException
    {
        if(authUserName!= null)
        {
            if(auth.equals(passWord)&&authUserName.equals(userName))
            {
                return EnumResponse.ok;
            }else
            {
                return new ErrorResponse("WRONGPASS invalid username-password pair");
            }
        }

        if(auth.equals(passWord))
        {
            return EnumResponse.ok;
        }
        return new ErrorResponse("WRONGPASS invalid username-password pair");
    }
    public void setAuth(String auth)
    {
        this.auth = auth;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    @Override
    public void processArgs(List<String> args) throws RedisException
    {
        if(args.isEmpty())
        {
            passWord = "";
            return;
        }
        if(args.size() > 1)
        {
            userName=args.get(0);
            passWord=args.get(1);
        }else
        {
            passWord=args.getFirst();
        }

    }
}
