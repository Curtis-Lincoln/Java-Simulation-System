public class Garage extends Consumer{
    protected ResourceType consumedResource2;
    protected int amount2;
    protected ResourceType consumedResource3;
    protected int amount3;
    protected ResourceType consumedResource4;
    protected int amount4;
    protected int level = 0;
    
    public Garage(String name, ResourceType resource, int amount, ResourceType resource2, int amount2, ResourceType resource3, int amount3, ResourceType resource4, int amount4){
        super(name, resource, amount);
        this.consumedResource2 = resource2;
        this.amount2 = amount2;
        this.consumedResource3 = resource3;
        this.amount3 = amount3;
        this.consumedResource4 = resource4;
        this.amount4 = amount4;
    }

    public void consume(Context ctx){
        if(ctx.state().removeResource(consumedResource, amount) && ctx.state().removeResource(consumedResource2, amount2) && ctx.state().removeResource(consumedResource3, amount3) && ctx.state().removeResource(consumedResource4, amount4)){
            System.out.printf("%s level is now: %d\n", name, (this.getLevel()+1));
            level++;
        }
        
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public void tick(Context ctx){
        this.consume(ctx);
    } 

    public String toCSV(){
        StringBuilder CSV = new StringBuilder();

CSV.append(name).append(",").append(consumedResource).append(",").append(amount).append(",").append(consumedResource2).append(",").append(amount2).append(",").append(consumedResource3).append(",").append(amount3).append(",").append(consumedResource4).append(",").append(amount4).append(",").append(level);
        return CSV.toString();
    }
    
}