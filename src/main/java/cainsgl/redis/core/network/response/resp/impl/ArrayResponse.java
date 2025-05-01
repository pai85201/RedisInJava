package cainsgl.redis.core.network.response.resp.impl;


import cainsgl.redis.core.network.response.resp.AbstractRESP2Response;
import cainsgl.redis.core.network.response.resp.RESP2Response;

import java.util.List;

public class ArrayResponse implements RESP2Response
{

    public RESP2Response[] responses;
    public boolean isFuture=false;
    public ArrayResponse(RESP2Response... responses)
    {
        this.responses = responses;
    }
    public ArrayResponse(FutureResponse<?>... responses)
    {
        isFuture=true;
        this.responses = responses;
    }
    public ArrayResponse(List<RESP2Response> responses)
    {
     this.responses = new RESP2Response[responses.size()];
     responses.toArray(this.responses);
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
