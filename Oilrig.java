public class Oilrig extends Producer{
    public Oilrig(String name, ResourceType product, int amount){
        super(name, product, amount);
    }

    public void produce(Context ctx){
        ctx.state().addResource(this.product, this.amount);
    }

    public String toCSV(){
        StringBuilder CSV = new StringBuilder();
        CSV.append(this.name).append(",").append(product).append(",").append(amount);
        return CSV.toString();
    }

    public void tick(Context ctx){
        this.produce(ctx);
    }
}