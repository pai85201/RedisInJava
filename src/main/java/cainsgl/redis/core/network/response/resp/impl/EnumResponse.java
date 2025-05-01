package cainsgl.redis.core.network.response.resp.impl;

import cainsgl.redis.core.network.response.resp.RESP2Response;

public class EnumResponse
{
    public static final RESP2Response ok=new OkResponse();
    public static final RESP2Response nil=new NilResponse();
}
