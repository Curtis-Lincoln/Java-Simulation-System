public class Engineer extends Converter{
    protected ResourceType input2;
    protected int input2Amount;
    
    public Engineer(String name, ResourceType input, int inputAmount, ResourceType input2, int input2Amount, ResourceType output, int outputAmount){
        super(name, input, inputAmount, output, outputAmount);
        this.input2 = input2;
        this.input2Amount = input2Amount;
    }

    public void convert(Context ctx){
        if(ctx.state().removeResource(input, inputAmount) && ctx.state().removeResource(input2, input2Amount)){
            ctx.state().addResource(this.output, this.outputAmount);
        }
    }
    
    public void tick(Context ctx){
        this.convert(ctx);
    }

    public String toCSV(){
        StringBuilder CSV = new StringBuilder();
        CSV.append(name).append(",").append(input).append(",").append(inputAmount).append(",").append(input2).append(",").append(input2Amount).append(",").append(output).append(",").append(outputAmount);
        return CSV.toString();
    }
    
    
}