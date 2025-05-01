package cainsgl.redis.core.command.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RedisParameter
{
    Class<?> type;
    List<String> only;

}
