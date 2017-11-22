package ro.contezi.log.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Parser {
    private final Map<String, Integer> requestCount;
    
    public Parser(String file) throws IOException {
        requestCount = new ConcurrentHashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(new File(file).toPath(), Charset.forName("UTF-8"))) {
        	reader.lines().parallel().map(Statistic::new).forEach(statistic -> 
        		requestCount.compute(statistic.getRequest(), (s, i) -> i == null ? 1 : i + 1));
        }
    }

    public Map<String, Integer> getRequestCount() {
        return requestCount;
    }
}
