public class CheatCommand extends Command{

    public CheatCommand(){}

    @Override
    public String execute(Context ctx){
        return ctx.engine().cheat();
    }
}