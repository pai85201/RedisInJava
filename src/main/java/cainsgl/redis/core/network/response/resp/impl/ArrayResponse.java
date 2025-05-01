package cainsgl.redis.core.network.response.resp.impl;


import cainsgl.redis.core.network.response.resp.AbstractRESP2Response;
import cainsgl.redis.core.network.response.resp.RESP2Response;

public class ArrayResponse implements RESP2Response
{

    RESP2Response[] responses;
    public ArrayResponse(RESP2Response... responses)
    {
        this.responses = responses;
    }

    @Override
    public String serialization()
    {
        if(responses.length == 0)
        {
            return "*0\r\n";
        }
        if(responses.length == 1)
        {
            return "*1\r\n"+responses[0].serialization();
        }
        StringBuilder sb = new StringBuilder(responses.length+1);
        sb.append("*");
        sb.append(responses.length);
        sb.append("\r\n");
        for (RESP2Response response : responses)
        {
            sb.append(response.serialization());
        }
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return serialization();
    }
}
