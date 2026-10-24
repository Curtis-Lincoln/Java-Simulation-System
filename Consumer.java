public abstract class Consumer extends Entity implements Tickable {

    protected ResourceType consumedResource;
    protected int amount;


     public Consumer(String name, ResourceType resource, int amount) {
        super(name);
        this.consumedResource = resource;
        this.amount = amount;
    }

    public ResourceType getProduct() {
        return consumedResource;
    }
    
    public int getAmount() {
        return amount;
    }

    public abstract void consume(Context ctx);
}