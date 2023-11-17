import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Inputs{
    int i1;
    int i2;

    public Inputs(int i1, int i2){
        this.i1 = i1;
        this.i2 = i2;
    }

    public int geti1(){
        return i1;
    }

    public int geti2(){
        return i2;
    }
}

class Weights {
    double w1;
    double w2;
    double w3;
    
    Weights(double w1, double w2, double w3){
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
    }

    public void setW1(double w1) {
        this.w1 = w1;
    }

    public void setW2(double w2) {
        this.w2 = w2;
    }

    public void setW3(double w3) {
        this.w3 = w3;
    }


    public double getw1(){
        return w1;
    }

    public double getw2(){
        return w2;
    }

    public double getw3(){
        return w3;
    }
    
}
/**
 * Perceptron
 */
public class Perceptron {
    private Weights[] weights;
    private double learning_rate;
    private int epochs;
    private double threshold;
    private Inputs[] inputs;
    private double[] spectedOutputs;
    private int bias = 1;
    String title = "\n"+
            "┌───┐              ┌┐\n"+
            "│┌─┐│             ┌┘└┐\n"+
            "│└─┘├──┬─┬──┬──┬──┼┐┌┼─┬──┬─┐\n"+
            "│┌──┤│─┤┌┤┌─┤│─┤┌┐││││┌┤┌┐│┌┐┐\n"+
            "││  ││─┤││└─┤│─┤└┘││└┤││└┘││││\n"+
            "└┘  └──┴┘└──┴──┤┌─┘└─┴┘└──┴┘└┘\n"+
            "               ││\n"+
            "               └┘\n";
    Perceptron (double learning_rate, int epochs, double threshold, Inputs[] inputs, double[] spectedOutputs, int bias){
        this.learning_rate = learning_rate;
        this.epochs = epochs;
        this.threshold = threshold;
        this.inputs = inputs;
        this.spectedOutputs = spectedOutputs;
        this.bias = bias;
        this.weights = new Weights[inputs.length];
        double w1 = 0;
        double w2 = 0;
        double w3 = 0;
        for (int i = 0; i < inputs.length; i++) {
            // a random number between 0 and 100
            w1 = (double)(Math.random() * 100)/100;
            w2 = (double)(Math.random() * 100)/100;
            w3 = (double)(Math.random() * 100)/100;
            this.weights[i] = new Weights(w1, w2, w3);
        }
    }

    public void train(){
        double error = 0;
        Weights[] newWeights = new Weights[inputs.length];
        // we add the current weights to the new weights
        for(int i = 0; i<weights.length;i++){
            newWeights[i] = new Weights(weights[i].getw1(), weights[i].getw2(), weights[i].getw3());
        }
        double output = 0;
        for(int i = 0; i<epochs;i++){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("\033[0;33m" + title + "\033[0m");
            System.out.println("\033[0;34mEpoch: \033[0m"+(i+1));
            for(int j = 0; j<inputs.length;j++){
                output = activationFunction(inputs[j].geti1(), inputs[j].geti2(), newWeights[j].getw1(), newWeights[j].getw2(), newWeights[j].getw3());
                error = spectedOutputs[j] - output;
                System.out.println("\033[0;34mInput: \033[0m"+
                inputs[j].geti1()+" "+inputs[j].geti2()+
                " \033[0;34mSpected output: \033[0m"+spectedOutputs[j]+
                " \033[0;34mOutput: \033[0m"+output+
                " \033[0;34mError: \033[0m"+error);
                // we show the weights
                System.out.println("\n\033[0;34mActual Weights: \033[0m"+newWeights[j].getw1()+" "+newWeights[j].getw2());
                // we calculate the new weights
                newWeights[j].setW1(getNewWeight(inputs[j].geti1(), newWeights[j].getw1(), error));
                newWeights[j].setW2(getNewWeight(inputs[j].geti2(), newWeights[j].getw2(), error));
                newWeights[j].setW3(getNewWeight(bias, newWeights[j].getw3(), error));
                System.out.println("\033[0;34mNew weights: \033[0m"+newWeights[j].getw1()+" "+newWeights[j].getw2());
            }
            try {
                Thread.sleep(500);// default 500
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\033[0;33m" + title + "\033[0m");
        System.out.println("\033[0;34mEpoch: \033[0m"+epochs);
        System.out.println("\033[0;34mLearning rate: \033[0m"+learning_rate);
        System.out.println("\033[0;34mThreshold: \033[0m"+threshold);
        System.out.println("\033[0;34mBias: \033[0m"+bias);
        // we show the final outputs
        for(int i = 0; i<inputs.length;i++){
            output = activationFunction(inputs[i].geti1(), inputs[i].geti2(), newWeights[i].getw1(), newWeights[i].getw2(), newWeights[i].getw3());
            System.out.println("\033[0;34mInput: \033[0m"+
            inputs[i].geti1()+" "+inputs[i].geti2()+
            " \033[0;34mSpected output: \033[0m"+spectedOutputs[i]+
            " \033[0;34mOutput: \033[0m"+output);
        }
        // we show the final weights
        for(int i = 0; i<weights.length;i++){
            System.out.println("\033[0;34mWeight "+(i+1)+": \033[0m"+newWeights[i].getw1()+" "+newWeights[i].getw2());
        }
    }
    
    public double getNewWeight(int input, double w, double error){
        return w + learning_rate * error * input;
    }
    public double activationFunction(int i1, int i2, double w1, double w2, double w3){
        return (i1 * w1 + i2 * w2 + bias * w3 ) > threshold ? 1 : 0;
    }

    
    /**
     * We will use the comand line to pass the arguments
     * -file <file_name> 
     * -lr <learning_rate> 
     * -e <epochs> 
     * -t <threshold>
     * -h or -help to show the help
     * @param args
     * 
     */
    public static void main(String[] args) {
        /**
         * Color codes
         * \033[0;30m BLACK
         * \033[0;31m RED
         * \033[0;32m GREEN
         * \033[0;33m YELLOW
         * \033[0;34m BLUE
         * \033[0;35m PURPLE
         * \033[0;36m CYAN
         * \033[0;37m WHITE
         * \033[0m RESET
         */
        // default values
        String path = "FileFolder/";
        String file_name = "and.txt";
        double learning_rate = 0.1;
        int bias = 1;
        int epochs = 10;
        double threshold = 0.5;
        String title = "\n"+
            "┌───┐              ┌┐\n"+
            "│┌─┐│             ┌┘└┐\n"+
            "│└─┘├──┬─┬──┬──┬──┼┐┌┼─┬──┬─┐\n"+
            "│┌──┤│─┤┌┤┌─┤│─┤┌┐││││┌┤┌┐│┌┐┐\n"+
            "││  ││─┤││└─┤│─┤└┘││└┤││└┘││││\n"+
            "└┘  └──┴┘└──┴──┤┌─┘└─┴┘└──┴┘└┘\n"+
            "               ││\n"+
            "               └┘\n";
        String help = "\033[0;34mUsage: \033[0mjava Perceptron \n -file <file_name> \n -lr <learning_rate> \n -e <epochs> \n -t <threshold>\n"+
                      "\n\033[0;34mDefault values: \033[0m\n-file \033[0;32m"+file_name+
                      "\033[0m\n-lr \033[0;32m"+learning_rate+
                      "\033[0m\n-e \033[0;32m"+epochs+
                      "\033[0m\n-t \033[0;32m"+threshold+
                      "\033[0m\n";

        System.out.print("\033[H\033[2J");
        System.out.flush();
        // if the command line is empty or the first argument is -h or -help
        if (args.length == 0 || args[0].equals("-h") || args[0].equals("-help")) {
            // the title on purple
            // we clean the terminal
            System.out.println("\033[0;35m" + title + "\033[0m");
            // we show the help
            System.out.println(help);
            // in red press enter to exit
            System.out.println("\033[0;31mPress enter to exit...\033[0m");
            // we wait for the user to press enter
            System.console().readLine();
            // we clean the terminal
            System.out.print("\033[H\033[2J");
            System.out.flush();
            //System.exit(0);
        } else {
            // if the command line is not empty
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-file")) {
                    file_name = args[i + 1];
                } else if (args[i].equals("-lr")) {
                    // we check if the learning rate is a number between 0 and 1 
                    if (Double.parseDouble(args[i + 1]) > 1 || Double.parseDouble(args[i + 1]) < 0) {
                        // title in red
                        System.out.println("\033[0;31m" + title + "\033[0m");
                        // if not we show an error message
                        System.out.println("\033[0;31mError: \033[0mLearning rate must be a number between 0 and 1");
                        // in red press enter to exit
                        System.out.println("\033[0;31mPress enter to exit...\033[0m");
                        // we wait for the user to press enter
                        System.console().readLine();
                        // we clean the terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.exit(0);
                    }
                    learning_rate = Double.parseDouble(args[i + 1]);
                } else if (args[i].equals("-e")) {
                    // we check if the epochs is a number greater than 0
                    if (Integer.parseInt(args[i + 1]) <= 0) {
                        // title in red
                        System.out.println("\033[0;31m" + title + "\033[0m");
                        // if not we show an error message
                        System.out.println("\033[0;31mError: \033[0mEpochs must be a number greater than 0");
                        // in red press enter to exit
                        System.out.println("\033[0;31mPress enter to exit...\033[0m");
                        // we wait for the user to press enter
                        System.console().readLine();
                        // we clean the terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.exit(0);
                    }
                    epochs = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-t")) {
                    // we check if the threshold is a number between 0 and 1
                    if (Double.parseDouble(args[i + 1]) > 1 || Double.parseDouble(args[i + 1]) < 0) {
                        // title in red
                        System.out.println("\033[0;31m" + title + "\033[0m");
                        // if not we show an error message
                        System.out.println("\033[0;31mError: \033[0mThreshold must be a number between 0 and 1");
                        // in red press enter to exit
                        System.out.println("\033[0;31mPress enter to exit...\033[0m");
                        // we wait for the user to press enter
                        System.console().readLine();
                        // we clean the terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.exit(0);
                    }
                    threshold = Double.parseDouble(args[i + 1]);
                }
            }
        }
        // we read de file and create the inputs and spected outputs
        ArrayList<Inputs> inputs = new ArrayList<Inputs>();
        ArrayList<Double> spectedOutputs = new ArrayList<Double>();

        File file = new File(path + file_name);
        // we check if the file exists
        if (!file.exists()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // title in red
            System.out.println("\033[0;31m" + title + "\033[0m");
            // if not we show an error message
            System.out.println("\033[0;31mError: \033[0mFile not found");
            // in red press enter to exit
            System.out.println("\033[0;31mPress enter to exit...\033[0m");
            // we wait for the user to press enter
            System.console().readLine();
            // we clean the terminal
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.exit(0);
        } else {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                // we read the file line by line and we create the inputs and spected outputs
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] lineArray = line.split("\s+");
                    inputs.add(new Inputs(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1])));
                    spectedOutputs.add(Double.parseDouble(lineArray[2]));
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                // title in red
                System.out.println("\033[0;31m" + title + "\033[0m");
                // if not we show an error message
                System.out.println("\033[0;31mError: \033[0m"+ e.getMessage());
                // in red press enter to exit
                System.out.println("\033[0;31mPress enter to exit...\033[0m");
                // we wait for the user to press enter
                System.console().readLine();
                // we clean the terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.exit(0);
            } 
        }
        Inputs[] inputsArray = new Inputs[inputs.size()];
        double[] spectedOutputsArray = new double[spectedOutputs.size()];
        for (int i = 0; i < inputs.size(); i++) {
            inputsArray[i] = inputs.get(i);
            spectedOutputsArray[i] = spectedOutputs.get(i);
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // we create the perceptron
        Perceptron perceptron = new Perceptron(learning_rate, epochs, threshold, inputsArray, spectedOutputsArray, bias);
        // we show the title
        System.out.println("\033[0;33m" + title + "\033[0m");
        // we train the perceptron
        perceptron.train();

        // in red press enter to exit
        System.out.println("\033[0;31mPress enter to exit...\033[0m");
        // we wait for the user to press enter
        System.console().readLine();
        // we clean the terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.exit(0);
    }
}