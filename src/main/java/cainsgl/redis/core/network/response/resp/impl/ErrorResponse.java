package cainsgl.redis.core.network.response.resp.impl;

import cainsgl.redis.core.network.response.Response;
import cainsgl.redis.core.network.response.resp.AbstractRESP2Response;

public class ErrorResponse extends AbstractRESP2Response
{
    public static final char ERROR = '-';
    String msg;
    public ErrorResponse(String msg)
    {
        super(ERROR);
        this.msg = msg;
    }

    @Override
    protected String toData()
    {
        return msg;
    }
}
