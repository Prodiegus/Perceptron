/**
 * Perceptron
 */
public class Perceptron {
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
        int epochs = 100;
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
            System.exit(0);
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

    }
}