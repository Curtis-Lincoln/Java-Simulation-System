import java.util.Map;

public class TickCommand extends Command{

    public TickCommand(){}

    @Override
    public String execute(Context ctx){
        System.out.println(ctx.engine().tick(ctx));
        StringBuilder output = new StringBuilder();
        Map<ResourceType, Integer> resources = ctx.state().getInventory();
        resources.forEach((key, value) -> output.append(key).append(" : ").append(value).append(" "));
        return output.toString();
    }
}