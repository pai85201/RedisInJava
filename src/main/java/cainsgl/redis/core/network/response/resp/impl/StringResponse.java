package cainsgl.redis.core.network.response.resp.impl;


import cainsgl.redis.core.network.response.resp.AbstractRESP2Response;

import java.nio.charset.StandardCharsets;

public class StringResponse extends AbstractRESP2Response {
    private static final char ProtocolCharacter = '$';
    public final String data;

    public StringResponse(String data) {
        super(ProtocolCharacter);
        this.data = data;
    }

    @Override
    protected String toData() {
        if(data == null) {
            return "-1";
        }
        return data.getBytes(StandardCharsets.UTF_8).length + END + data;
    }
}
