package ro.contezi.log.reader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws IOException {
        int linesToGenerate = args.length > 0 ? Integer.parseInt(args[0]) : 6000000;
        
        LOGGER.log(Level.INFO, "Generating " + linesToGenerate + " lines");
        String file = "target/generated.log";
        new Generator.Builder().lines(linesToGenerate).uri("/a").uri("/b").uri("/a/c").uri("/a/c/d").file(file).build().generate();
        LOGGER.log(Level.INFO, "Parsing file");
        LOGGER.log(Level.INFO, new Parser(file).getRequestCount().toString());
    }
}
