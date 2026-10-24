import java.util.List;

public abstract class Command {

    protected List<String> words;
   

    public Command (List<String> words) {
       
        this.words = words;
    }

    public Command () {}
    
    public abstract String execute(Context ctx);
}