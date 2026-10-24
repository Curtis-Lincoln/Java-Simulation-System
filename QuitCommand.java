public class QuitCommand extends Command{

    public QuitCommand(){}

    @Override
    public String execute(Context ctx){
        return "Quitting Game...";
    }
}