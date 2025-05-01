package cainsgl.redis.core.network.response.resp.impl;

import cainsgl.redis.core.network.response.Response;

public class OkResponse extends SimpleStringResponse
{
    public OkResponse()
    {
        super("OK");
    }
}
