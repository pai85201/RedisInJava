package cainsgl.redis.core.config;

public class RedisConfig implements Config
{
    public RedisConfig()
    {

    }

    @Override
    public void autoConfig()
    {

    }
    public String auth;
    public int port;
    public String userName;
    public RedisConfig setAuth(String auth)
    {
        this.auth = auth;
        return this;
    }
    public RedisConfig setPassWord(String auth)
    {
        this.auth = auth;
        return this;
    }
    public RedisConfig setPort(int port)
    {
        this.port = port;
        return this;
    }
    public RedisConfig setUserName(String userName)
    {
        this.userName = userName;
        return this;
    }
}
