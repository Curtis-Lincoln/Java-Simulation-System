import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Map;

public class SimulationState {

    private List<Producer> producers = new ArrayList<>();
    private List<Converter> converters = new ArrayList<>();
    private List<Consumer> consumers = new ArrayList<>();
    private Map<ResourceType, Integer> inventory = new EnumMap<>(ResourceType.class);
    private List<Map<ResourceType, Integer>> resourceHistory = new ArrayList<>();

    public void addProducer(Producer producer){
        producers.add(producer);
    }

    public void addConverter(Converter converter){
        converters.add(converter);
    }

    public void addConsumer(Consumer consumer){
        consumers.add(consumer);
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public List<Converter> getConverters() {
        return converters;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void resetProducers(){
        producers.clear();
    }

    public void resetConverters(){
        converters.clear();
    }
    
    public void resetConsumers(){
        consumers.clear();
    }

    public void addResource(ResourceType resource, int amount){
        int currentAmount = this.getResourceAmount(resource);
        int newAmount = currentAmount + amount;
        inventory.put(resource, newAmount);
    }

    public void updateResource(ResourceType resource, int amount){
        inventory.put(resource, amount);
    }

    public int getResourceAmount(ResourceType resource){
        return (inventory.get(resource) != null) ? inventory.get(resource) : 0;
    }

    public boolean removeResource(ResourceType resource, int amount){
        int currentAmount = this.getResourceAmount(resource);
        if(currentAmount >= amount){
            inventory.put(resource, (currentAmount-amount));
            return true;
        }
        else{
            return false;
        }
    }

    public void updateHistory(){
        Map<ResourceType, Integer> currentResources = new HashMap<>();
        for(ResourceType resource : inventory.keySet()){
            currentResources.put(resource, inventory.get(resource));
        }
        resourceHistory.add(currentResources);
    }

    public Map<ResourceType, Integer> getInventory(){
        return inventory;
    }

    public List<Map<ResourceType, Integer>> getHistory(){
        return resourceHistory;
    }
}