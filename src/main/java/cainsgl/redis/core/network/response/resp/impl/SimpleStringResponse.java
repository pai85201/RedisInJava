package cainsgl.redis.core.network.response.resp.impl;

import cainsgl.redis.core.network.response.resp.AbstractRESP2Response;

public class SimpleStringResponse extends AbstractRESP2Response
{
    private static final char ProtocolCharacter = '+';
    private final String data;
    public SimpleStringResponse(String data)
    {
        super(ProtocolCharacter);
        this.data = data;
    }

    @Override
    protected String toData()
    {
        return data;
    }
}
