public class Parser {

    public Command parse(String command){
        if(command == null || command.trim().isEmpty()){
            return new TickCommand();
        }

        String formatCommand = command.trim().toLowerCase();
        String[] words = formatCommand.split("\\s+");

        switch(words[0]){
            case "build", "b":
                {
                if(words.length != 2){
                    return new InvalidCommand();
                }
                return new BuildCommand(words[1]);
            }
            case "info", "i":
                {
                if(words.length != 2){
                    return new InfoCommand("");
                }
                return new InfoCommand(words[1]);
            }
            case "graph", "g":
                {
                if(words.length != 2){
                    return new GraphCommand("");
                }
                return new GraphCommand(words[1]);
            }
            case "save", "s":
                {
                if(words.length != 2){
                    return new InvalidCommand();
                }
                return new SaveCommand(words[1]);
            }
            case "load", "l":
                {
                if(words.length != 2){
                    return new InvalidCommand();
                }
                return new LoadCommand(words[1]);
            }                
            case "tick", "t":
                return new TickCommand();
            case "help":
                {
                if(words.length != 2){
                    return new HelpCommand("");
                }
                return new HelpCommand(words[1]);
            }
            case "sell":
                {
                if(words.length != 2){
                    return new InvalidCommand();
                }                
                return new SellCar(words[1]);
            }
            case "cheat":
                return new CheatCommand();
            case "quit":
                return new QuitCommand();
            default:
                return new InvalidCommand();
        }
    }
}