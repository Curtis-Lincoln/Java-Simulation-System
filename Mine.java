public class Mine extends Producer{
    protected ResourceType product2;
    protected int amount2;
    
    public Mine(String name, ResourceType product, int amount, ResourceType product2, int amount2){
        super(name, product, amount);
        this.product2 = product2;
        this.amount2 = amount2;
    }

    public void produce(Context ctx){
        ctx.state().addResource(this.product, this.amount);
        ctx.state().addResource(this.product2, this.amount2);
    }

    public String toCSV(){
        StringBuilder CSV = new StringBuilder();
        CSV.append(this.name).append(",").append(product).append(",").append(amount).append(",").append(product2).append(",").append(amount2);
        return CSV.toString();
    }

    public void tick(Context ctx){
        this.produce(ctx);        
    }
}