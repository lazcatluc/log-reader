package ro.contezi.log.reader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws IOException {
    	String file;
    	int linesParam;
    	try {
    		linesParam = Integer.parseInt(args[0]);
    	}
    	catch (RuntimeException re) {
    		linesParam = -1;
    	}
    	if (args.length == 0 || linesParam > -1) {
    		int linesToGenerate = linesParam > -1 ? linesParam : 6000000;
    		file = args.length == 1 ? "target/generated.log" : args[1];
    		LOGGER.log(Level.INFO, "Generating " + linesToGenerate + " lines");
    		new Generator.Builder().lines(linesToGenerate).uri("/a").uri("/b")
    				.uri("/a/c").uri("/a/c/d").file(file).build().generate();
        }
        else {
        	file = args[0];
    	}
        LOGGER.log(Level.INFO, "Parsing file " + file);
        LOGGER.log(Level.INFO, new Parser(file).getRequestCount().toString());
    }
}
