import java.util.List;
import java.util.Map;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;



public class Engine {
    private int currentTick;
    private SimulationState state;
    
    // USED FOR TESTING
    public void initialiseDefaults() {
        Mine mine = new Mine("mine_1", ResourceType.ORE, 10, ResourceType.COPPER, 10);
        House house = new House("house_1", ResourceType.PEOPLE, 2);
        Oilrig oilrig = new Oilrig("oilrig_1", ResourceType.OIL, 10);
        Reservoir reservoir = new Reservoir("reservoir_1", ResourceType.WATER, 10);
        Refinery refinery = new Refinery("refinery_1", ResourceType.OIL, 5, ResourceType.WATER, 5, ResourceType.FUEL, 5);
        Scrapyard scrapyard = new Scrapyard("scrapyard_1", ResourceType.ORE, 5, ResourceType.METAL, 5);
        Engineer engineer = new Engineer("engineer_1", ResourceType.PEOPLE, 2, ResourceType.COPPER, 5, ResourceType.WIRES, 5);
        Garage garage = new Garage("garage_1", ResourceType.PEOPLE, 2, ResourceType.WIRES, 5, ResourceType.FUEL, 10, ResourceType.METAL, 5);

        state.addProducer(mine);
        state.addProducer(house);
        state.addProducer(oilrig);
        state.addProducer(reservoir);
        state.addConverter(refinery);
        state.addConverter(scrapyard);
        state.addConverter(engineer);
        state.addConsumer(garage);
        
        //ADD ALL THE ENTITIES YOU CREATE TO THE PRODUCER, CONVERTER AND CONSUMER LISTS IN SIMULATIONSTATE HERE.
        
    }

    public Engine(SimulationState state){
        this.state = state;
        this.currentTick = 0;
        state.addResource(ResourceType.CREDITS, 1000);
        state.updateHistory();
    }

    public String tick(Context ctx){
        List<Producer> producers = state.getProducers();
        if(producers != null){
            for(Producer producer: producers){
                producer.tick(ctx);
            }
        }
        List<Converter> converters = state.getConverters();
        if(converters != null){
            for(Converter converter: converters){
                converter.tick(ctx);
            }
        }
        List<Consumer> consumers = state.getConsumers();
        if(consumers != null){
            for(Consumer consumer: consumers){
                consumer.tick(ctx);
            }
        }
        return this.nextTick();
    }

    public int getCurrentTick(){
        return currentTick;
    } 

    public String nextTick(){
        currentTick++;
        state.updateHistory();
        return "Game has ticked by 1";
    }

    public String save(String filename){ 
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("data/" + filename))) {
            String curTick = "CurrentTick," + currentTick;
            bos.write((curTick + "\n").getBytes());
            
            
            Map<ResourceType, Integer> inventory = state.getInventory();
            inventory.forEach((resource, amount) -> {
                try{
                    bos.write((resource + "," + amount + "\n").getBytes());
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            });
            List<Producer> producers = state.getProducers();
            producers.forEach(producer -> {
                try{
                    bos.write(producer.toCSV().getBytes());
                    bos.write("\n".getBytes());
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            });
            List<Converter> converters = state.getConverters();
            converters.forEach(converter -> {
                try{
                    bos.write(converter.toCSV().getBytes());
                    bos.write("\n".getBytes());
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            });
            List<Consumer> consumers = state.getConsumers();
            consumers.forEach(consumer -> {
                try{
                    bos.write(consumer.toCSV().getBytes());
                    bos.write("\n".getBytes());
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            });
            bos.flush();
            return "Saving to... " + filename;
        }
        //Change what exceptions are caught
        catch (IOException e) {
            e.printStackTrace();
            return "Save failed";
        }
    }

    public String load(String filename){
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("data/" + filename))){
            state.resetProducers();
            state.resetConverters();
            state.resetConsumers();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] fields = line.split(",");
                if(fields[0].equals("CurrentTick")){
                    currentTick = Integer.valueOf(fields[1]);
                }
                else if(fields[0].equals("ORE") || fields[0].equals("METAL") || fields[0].equals("CREDITS") || fields[0].equals("WIRES") || fields[0].equals("COPPER") || fields[0].equals("PEOPLE") || fields[0].equals("OIL") || fields[0].equals("WATER") || fields[0].equals("FUEL")){
                    state.updateResource(ResourceType.valueOf(fields[0]),Integer.valueOf(fields[1]));
                }
                else if(fields[0].contains("mine")){
                    state.addProducer(new Mine(fields[0],ResourceType.valueOf(fields[1]),Integer.valueOf(fields[2]),ResourceType.valueOf(fields[3]),Integer.valueOf(fields[4])));
                }
                else if(fields[0].contains("house")){
                    state.addProducer(new House(fields[0],ResourceType.valueOf(fields[1]),Integer.valueOf(fields[2])));
                }
                else if(fields[0].contains("oilrig")){
                    state.addProducer(new Oilrig(fields[0],ResourceType.valueOf(fields[1]),Integer.valueOf(fields[2])));
                }
                else if(fields[0].contains("reservoir")){
                    state.addProducer(new Reservoir(fields[0],ResourceType.valueOf(fields[1]),Integer.valueOf(fields[2])));
                }
                else if(fields[0].contains("refinery")){
                    state.addConverter(new Refinery(fields[0],ResourceType.valueOf(fields[1]),Integer.valueOf(fields[2]),ResourceType.valueOf(fields[3]),Integer.valueOf(fields[4]),ResourceType.valueOf(fields[5]),Integer.valueOf(fields[6])));
                }
                else if(fields[0].contains("scrapyard")){
                    state.addConverter(new Scrapyard(fields[0],ResourceType.valueOf(fields[1]),Integer.valueOf(fields[2]),ResourceType.valueOf(fields[3]),Integer.valueOf(fields[4])));
                }
                else if(fields[0].contains("engineer")){
                    state.addConverter(new Engineer(fields[0],ResourceType.valueOf(fields[1]),Integer.valueOf(fields[2]),ResourceType.valueOf(fields[3]),Integer.valueOf(fields[4]),ResourceType.valueOf(fields[5]),Integer.valueOf(fields[6])));
                }
                else if(fields[0].contains("garage")){
                    Garage garage = new Garage(fields[0],ResourceType.valueOf(fields[1]),Integer.valueOf(fields[2]),ResourceType.valueOf(fields[3]),Integer.valueOf(fields[4]),ResourceType.valueOf(fields[5]),Integer.valueOf(fields[6]),ResourceType.valueOf(fields[7]),Integer.valueOf(fields[8]));
                    state.addConsumer(garage);
                    garage.setLevel(Integer.valueOf(fields[9]));
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        return "Loaded... " + filename;
    }

    public String build(String buildName){
        int count = 0;
        List<Producer> producers = state.getProducers();
        List<Converter> converters = state.getConverters();
        List<Consumer> consumers = state.getConsumers();
        String name;
        switch(buildName){
            case "mine":
                {
                if(state.removeResource(ResourceType.CREDITS, 100)){
                    if(producers != null){
                        for(Producer producer: producers){
                            if(producer instanceof Mine){
                                count++;
                            } 
                        }
                    }
                    String numOf = String.valueOf(count+1);
                    name = "mine_" + numOf;
                    Mine mine = new Mine(name, ResourceType.ORE, 10, ResourceType.COPPER, 10);
                    state.addProducer(mine);
                    return "You built a mine";
                }
                else{
                    return "You do not have enough resources to build a mine";
                }
            }
            case "house":
                {
                if(state.removeResource(ResourceType.CREDITS, 100)){
                    if(producers != null){
                        for(Producer producer: producers){
                            if(producer instanceof House){
                                count++;
                            } 
                        }
                    }
                    String numOf = String.valueOf(count+1);
                    name = "house_" + numOf;
                    House house = new House(name, ResourceType.PEOPLE, 2);
                    state.addProducer(house);
                    return "You built a house";
                }
                else{
                    return "You do not have enough resources to build a house";
                }
            }
            case "oilrig":
                {
                if(state.removeResource(ResourceType.CREDITS, 100)){
                    if(producers != null){
                        for(Producer producer: producers){
                            if(producer instanceof Oilrig){
                                count++;
                            } 
                        }
                    }
                    String numOf = String.valueOf(count+1);
                    name = "oilrig_" + numOf;
                    Oilrig oilrig = new Oilrig(name, ResourceType.OIL, 10);
                    state.addProducer(oilrig);
                    return "You built a oilrig";
                }
                else{
                    return "You do not have enough resources to build a oilrig";
                }
            }
            case "reservoir":
                {
                if(state.removeResource(ResourceType.CREDITS, 100)){
                    if(producers != null){
                        for(Producer producer: producers){
                            if(producer instanceof Reservoir){
                                count++;
                            } 
                        }
                    }
                    String numOf = String.valueOf(count+1);
                    name = "reservoir_" + numOf;
                    Reservoir reservoir = new Reservoir(name, ResourceType.WATER, 10);
                    state.addProducer(reservoir);
                    return "You built a reservoir";
                }
                else{
                    return "You do not have enough resources to build a reservoir";
                }
            }
            case "refinery":
                {
                if(state.removeResource(ResourceType.CREDITS, 100)){
                    if(converters != null){
                        for(Converter converter: converters){
                            if(converter instanceof Refinery){
                                count++;
                            } 
                        }
                    }
                    String numOf = String.valueOf(count+1);
                    name = "refinery_" + numOf;
                    Refinery refinery = new Refinery(name, ResourceType.OIL, 5, ResourceType.WATER, 5, ResourceType.FUEL, 5);
                    state.addConverter(refinery);
                    return "You built a refinery";
                }
                else{
                    return "You do not have enough resources to build a refinery";
                }
            }
            case "scrapyard":
                {
                if(state.removeResource(ResourceType.CREDITS, 100)){
                    if(converters != null){
                        for(Converter converter: converters){
                            if(converter instanceof Scrapyard){
                                count++;
                            } 
                        }
                    }
                    String numOf = String.valueOf(count+1);
                    name = "scrapyard_" + numOf;
                    Scrapyard scrapyard = new Scrapyard(name, ResourceType.ORE, 5, ResourceType.METAL, 5);
                    state.addConverter(scrapyard);
                    return "You built a scrapyard";
                }
                else{
                    return "You do not have enough resources to build a scrapyard";
                }
            }
            case "engineer":
                {
                if(state.removeResource(ResourceType.CREDITS, 100)){
                    if(converters != null){
                        for(Converter converter: converters){
                            if(converter instanceof Engineer){
                                count++;
                            } 
                        }
                    }
                    String numOf = String.valueOf(count+1);
                    name = "engineer_" + numOf;
                    Engineer engineer = new Engineer(name, ResourceType.PEOPLE, 2, ResourceType.COPPER, 5, ResourceType.WIRES, 5);
                    state.addConverter(engineer);
                    return "You built a engineer";
                }
                else{
                    return "You do not have enough resources to build a engineer";
                }
            }
            case "garage":
                {
                if(state.removeResource(ResourceType.CREDITS, 100)){
                    if(consumers != null){
                        for(Consumer consumer: consumers){
                            if(consumer instanceof Garage){
                                count++;
                            } 
                        }
                    }
                    String numOf = String.valueOf(count+1);
                    name = "garage_" + numOf;
                    Garage garage = new Garage(name, ResourceType.PEOPLE, 2, ResourceType.WIRES, 5, ResourceType.FUEL, 10, ResourceType.METAL, 5);
                    state.addConsumer(garage);
                    return "You built a garage";
                }
                else{
                    return "You do not have enough resources to build a garage";
                }
            }
            default:
                return "This is not a valid entity"; 
        }
    }

    public String cheat(){
        state.addResource(ResourceType.ORE, 10000);
        state.addResource(ResourceType.METAL, 10000);
        state.addResource(ResourceType.CREDITS, 10000);
        state.addResource(ResourceType.WIRES, 10000);
        state.addResource(ResourceType.COPPER, 10000);
        state.addResource(ResourceType.PEOPLE, 10000);
        state.addResource(ResourceType.OIL, 10000);
        state.addResource(ResourceType.WATER, 10000);
        state.addResource(ResourceType.FUEL, 10000);
        return "CHEAT ACTIVATED";
    }

    

}