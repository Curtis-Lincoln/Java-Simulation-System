public class LoadCommand extends Command{
    private String filename;

    public LoadCommand(String filename){
        this.filename = filename;
    }

    @Override
    public String execute(Context ctx){
        return ctx.engine().load(filename);
    }
}