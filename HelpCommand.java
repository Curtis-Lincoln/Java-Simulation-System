public class HelpCommand extends Command{
    private String topic;

    public HelpCommand(String topic){
        this.topic = topic;
    }

    @Override
    public String execute(Context ctx){
        switch(topic){
            case "":
                return "Valid Commands: <build|b> <entity>, <info|i> <resources|entities>, <graph|g> <resource>, <save|s> <filename>, <load|l> <filename>, <tick|t>, <help> <topic>, \n<sell>, <cheat>, <quit>";
            case "build":
                return "Command: build|b <entity>\nBuilds the entity given if the player has the required materials";
            case "info":
                return "Command: info|i <resources|entities>\nProvides infomation on the resources you have, or the entities you have built.";
            case "graph":
                return "Command: graph|g <resource>\nDisplays a graph showing how much of the resource you had each tick.";
            case "save":
                return "Command: save|s <filename>\nSaves the current state of the game.";
            case "load|l": 
                return "Command: load|l <filename>\nLoads a previously saved game.";
            case "tick":
                return "Command: tick|t\nAdvances the game by 1 tick.";
            case "sell":
                return "Command: sell\nSells the garage and earns you credits.";
            case "cheat":
                return "Command: cheat\nAdds each resource to your inventory.";
            case "quit":
                return "Command: quit\nQuits the game.";
            default:
                return new InvalidCommand().execute(ctx);
        }       
    }
}