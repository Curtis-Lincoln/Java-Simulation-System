import java.util.List;

public class SellCar extends Command{
    private  String garageName;
    
    public SellCar(String garageName){
        this.garageName = garageName;
    }

    @Override
    public String execute(Context ctx){
        List<Consumer> consumers = ctx.state().getConsumers();
        if(consumers != null){
            for(Consumer consumer : consumers){
                if(consumer.getName().equals(garageName)){
                    int level = ((Garage) consumer).getLevel();
                    ctx.state().addResource(ResourceType.CREDITS, 200*level);
                    ((Garage) consumer).setLevel(0);
                    ctx.state().updateHistory();
                    return "The cars in the " + ((Garage) consumer).getName() + " have been sold. Level reset back to 0";
                }
            }
        }
        return "That garage does not exist.";
    }
}