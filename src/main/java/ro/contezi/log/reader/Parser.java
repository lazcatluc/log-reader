package ro.contezi.log.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private final Statistic[] statistics;
    private final Map<String, Integer> requestCount;
    
    public Parser(String file) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(new File(file).toPath(), Charset.forName("UTF-8"))) {
            statistics = reader.lines().map(Statistic::new).toArray(i -> new Statistic[i]);
        }
        requestCount = new HashMap<>();
        for (Statistic statistic : statistics) {
            requestCount.compute(statistic.getRequest(), (s, i) -> i == null ? 1 : i + 1);
        }
    }

    public Map<String, Integer> getRequestCount() {
        return requestCount;
    }
}
