public class InvalidCommand extends Command{


    public InvalidCommand(){}

    @Override
    public String execute(Context ctx){
        return "Invalid Command";
    }
}