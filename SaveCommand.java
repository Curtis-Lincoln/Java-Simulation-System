public class SaveCommand extends Command{
    private String filename;

    public SaveCommand(String filename){
        this.filename = filename;
    }

    @Override
    public String execute(Context ctx){
        return ctx.engine().save(filename);
    }
}