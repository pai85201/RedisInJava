package cainsgl.redis.core.network.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCommand
{
    String command;
    String key;
    List<String> args;
}
