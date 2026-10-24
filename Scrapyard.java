public class Scrapyard extends Converter{
    public Scrapyard(String name, ResourceType input, int inputAmount, ResourceType output, int outputAmount){
        super(name, input, inputAmount, output, outputAmount);
    }

    public void convert(Context ctx){
        if(ctx.state().removeResource(input, inputAmount)){
            ctx.state().addResource(this.output, this.outputAmount);
        }
    }

    public void tick(Context ctx){
        this.convert(ctx);
    }

    public String toCSV(){
        StringBuilder CSV = new StringBuilder();
        CSV.append(this.name).append(",").append(input).append(",").append(inputAmount).append(",").append(output).append(",").append(outputAmount);
        return CSV.toString();
    }
}