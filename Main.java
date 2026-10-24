import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        SimulationState state = new SimulationState();
        Engine engine = new Engine(state);
        Context ctx = new Context(engine, state);
        Parser parser = new Parser();

        boolean quitting = false;
        String userinput;
        String result;
        Command command;

        System.out.println("***************************************************");
        System.out.println("Welcome to the car simulation game where you can build producers, converters and consumers to create garages that sell cars earning you credits!");
        System.out.println("Valid Commands: <build|b> <entity>, <info|i> <resources|entities>, <graph|g> <resource>, <save|s> <filename>, <load|l> <filename>, <tick|t>, <help> <topic>, <sell>,\n<cheat>, <quit> ");
        System.out.println("***************************************************");

        while(!quitting){
            System.out.printf("[%d] Enter Command >> ", ctx.engine().getCurrentTick());
            userinput = scanner.nextLine();
            command = parser.parse(userinput);
            result = command.execute(ctx);
            System.out.println(result);
            
            if(result.equals("Quitting Game...")){
                quitting = true;
            }  
        } 
        scanner.close();
    }
} 