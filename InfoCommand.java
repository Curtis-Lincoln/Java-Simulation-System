import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class InfoCommand extends Command{
    private String object;

    public InfoCommand(String object){
        this.object = object;
    }

    @Override
    public String execute(Context ctx){
        SimulationState state = ctx.state();
        switch(object){
            case "":
                return "You need to specify whether you want info on resources or entities.";
            case "resources":
                StringBuilder output = new StringBuilder();
                Map<ResourceType, Integer> resources = state.getInventory();
                resources.forEach((key, value) -> output.append(key).append(" : ").append(value).append("\n"));
                return output.deleteCharAt(output.length() - 1).toString();
            case "entities": 
                List<Producer> producers = state.getProducers();
                List<String> producerInfo = new ArrayList<>();
                for(Producer producer: producers){
                    if(producer.getName().contains("mine")){
                        producerInfo.add(producer.getName() + " [ORE x10 & COPPER x10]");
                    }
                    else if(producer.getName().contains("house")){
                        producerInfo.add(producer.getName() + " [PEOPLE x2]");
                    }
                    else if(producer.getName().contains("oilrig")){
                        producerInfo.add(producer.getName() + " [OIL x10]");
                    }
                    else if(producer.getName().contains("reservoir")){
                        producerInfo.add(producer.getName() + " [WATER x10]");
                    }
                } 
                List<Converter> converters = state.getConverters();
                List<String> converterInfo = new ArrayList<>();
                for(Converter converter: converters){
                    if(converter.getName().contains("refinery")){
                        converterInfo.add(converter.getName() + " [OIL x5 & WATER x5 --> FUEL x5]");
                    }
                    else if(converter.getName().contains("scrapyard")){
                        converterInfo.add(converter.getName() + " [ORE x5 --> METAL x5]");
                    }
                    else if(converter.getName().contains("engineer")){
                        converterInfo.add(converter.getName() + " [PEOPLE x2 & COPPER x5 --> WIRES x5]");
                    }
                }
                List<Consumer> consumers = state.getConsumers();
                List<String> consumerInfo = new ArrayList<>();
                for(Consumer consumer: consumers){
                    consumerInfo.add(consumer.getName() + " [PEOPLE x2 & WIRES x5 & FUEL x10 & METAL x5 to level up]" + " -- Current Level: " + ((Garage) consumer).getLevel() + " --");
                }
                List<String> entities = new ArrayList<>();
                entities.addAll(producerInfo);
                entities.addAll(converterInfo);
                entities.addAll(consumerInfo);

                StringBuilder entitiesInfo = new StringBuilder();
                for(String entityInfo: entities){
                    entitiesInfo.append(entityInfo).append("\n");
                }
                return entitiesInfo.deleteCharAt(entitiesInfo.length() - 1).toString();
            default:
                return new InvalidCommand().execute(ctx);
        }
    }
}