package cainsgl.redis.core.network.response.resp.impl;

import cainsgl.redis.core.network.response.resp.AbstractRESP2Response;

public class NumberResponse extends AbstractRESP2Response
{
    private static final char ProtocolCharacter = ':';
    Number n;
    public NumberResponse(Number number)
    {
        super(ProtocolCharacter);
        n=number;
    }

    @Override
    protected String toData()
    {
        return n.toString();

    }
}
