public class BuildCommand extends Command{
    private String buildName;

    public BuildCommand(String buildName){
        this.buildName = buildName;
    }

    @Override
    public String execute(Context ctx){
        return ctx.engine().build(buildName);
    }
}