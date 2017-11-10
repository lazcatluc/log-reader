package ro.contezi.log.reader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private final List<String> requestUris;
    private final List<String> browserSignatures;
    private final Random random;
    private final int linesToGenerate;
    private final DateTimeFormatter dateFormat;
    private final String file;
    
    private Generator(Builder builder) {
        requestUris = new ArrayList<>(builder.requestUris);
        browserSignatures = new ArrayList<>(builder.browserSignatures);
        random = builder.random;
        linesToGenerate = builder.linesToGenerate;
        dateFormat = builder.dateFormat;
        file = builder.file;
    }
    
    private String line() {
        StringBuilder sb = new StringBuilder();
        ip(sb);
        
        sb.append(" - - [").append(dateFormat.format(ZonedDateTime.now())).append("] \"GET ");
        sb.append(requestUris.get(random.nextInt(requestUris.size())));
        sb.append(" HTTP/1.1\" ");
        sb.append(random.nextInt(10000)).append(' ').append(random.nextInt(10000)).append(" \"-\" \"");
        sb.append(browserSignatures.get(random.nextInt(browserSignatures.size()))).append("\"\n");
        return sb.toString();
    }
    
    public void generate() throws IOException {
        try(FileOutputStream fos = new FileOutputStream(file)) {
            for (int i = 0; i < linesToGenerate; i++) {
                fos.write(line().getBytes());
            }
        }
    }

    private void ip(StringBuilder sb) {
        sb.append(random.nextInt(256)).append('.').append(random.nextInt(256)).append('.').append(random.nextInt(256)).append('.').append(random.nextInt(256));
    }

    public static class Builder {
        private List<String> requestUris = new ArrayList<>();
        private List<String> browserSignatures = new ArrayList<>();
        private Random random = new Random();
        private int linesToGenerate = 1;
        private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");
        private String file = "generated.log";
        
        public Builder() {
            requestUris.add("/");
            browserSignatures.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.5 Safari/535.19");
        }
        
        public Builder uri(String uri) {
            requestUris.add(uri);
            return this;
        }
        
        public Builder browser(String browserSignature) {
            browserSignatures.add(browserSignature);
            return this;
        }
        
        public Builder lines(int linesToGenerate) {
            this.linesToGenerate = linesToGenerate;
            return this;
        }
        
        public Builder file(String file) {
            this.file = file;
            return this;
        }
        
        public Generator build() {
            return new Generator(this);
        }
    }
}
