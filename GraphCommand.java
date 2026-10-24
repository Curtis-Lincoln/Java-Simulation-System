import java.util.List;
import java.util.Map;

public class GraphCommand extends Command{
    private String resourceName;

    public GraphCommand(String resourceName){
        this.resourceName = resourceName;
    }

    @Override
    public String execute(Context ctx){
        if(resourceName.equals("")){
            return "You need to specify what resource you would like to look at";
        }
        
        ResourceType resource; 
        try{
            resource = ResourceType.valueOf(resourceName.trim().toUpperCase());
        }
        catch (IllegalArgumentException e){
            return new InvalidCommand().execute(ctx);
        }

        SimulationState state = ctx.state();
        List<Map<ResourceType, Integer>> history = state.getHistory();

        String graph = String.valueOf(resource) + ":\n";
        graph += "Scale: # = 10\n\n";
        int[] resourceAmounts = new int[ctx.engine().getCurrentTick() + 1]; 
        int resourceIndex = 0;
        boolean exist;
        for(Map<ResourceType, Integer> tick: history){
            exist = false;
            for(ResourceType resourceType: tick.keySet()){
                if(resourceType == resource){
                    resourceAmounts[resourceIndex] = tick.get(resourceType);
                    resourceIndex++;
                    exist = true;
                }
            }
            if(!exist){
                resourceAmounts[resourceIndex] = 0;
                resourceIndex++;
            }
        }
    
        for(int turn = 0; turn < resourceAmounts.length ; turn++){
            if(turn < 10){
                graph += "Tick " + String.valueOf(turn) + "   |";
            }
            else if(turn < 100){
                graph += "Tick " + String.valueOf(turn) + "  |";
            }
            else{
                graph += "Tick " + String.valueOf(turn) + "|";
            }
            int scaled = resourceAmounts[turn]/10;
            for(int i = 0; i < scaled; i++){
                graph += "#";
            }
            graph += "\n";
        } 
        return graph;
    }
}