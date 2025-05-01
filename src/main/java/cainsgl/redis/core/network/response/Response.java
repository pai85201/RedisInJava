package cainsgl.redis.core.network.response;


import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.Getter;

import static cainsgl.redis.core.network.response.ResponseEnum.*;


public class Response
{
    @Getter
    Object value;
    @Getter
    byte type;
    public Response(Object value,byte type)
    {
        this.value = value;
        this.type = type;
    }
    public static final Response OK=new Response("ok",ResponseEnum.SIMPLE_STRING);
    public static final Response nil=new Response(null,ResponseEnum.STRING);

    @Override
    public String toString()
    {
        String typeName;
        switch(type)
        {
            case STRING:
               typeName = "string";
                break;
            case SIMPLE_STRING:
               typeName = "SIMPLE_STRING";
                break;
            case NUMBER:
              typeName = "number";
                break;
            case ARRAY:
               typeName = "array";
                break;
            case ERROR:
                typeName = "error";
                break;
            default:
                typeName="unknown";
                break;
        }
        if(type==ARRAY)
        {
            //说明是数组
            StringBuilder builder = new StringBuilder("[\n");
            Response[] res=(Response[])value;
            for(Response r:res)
            {
               builder.append(r.toString()).append(",\n");
            }
            return builder.append(" ]").toString();
        }
        if(value!=null)
        {
            StringBuilder builder = new StringBuilder();
           return  builder.append("( ").append(value.toString()).append(" : ").append(typeName).append(" )").toString();
        }else
        {
            return "(nil)";
        }

    }
}
