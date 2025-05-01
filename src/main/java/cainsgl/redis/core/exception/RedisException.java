package cainsgl.redis.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


public class RedisException extends RuntimeException
{
    public RedisException(String message)
    {
        super(message);
    }
}
