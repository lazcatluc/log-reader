package ro.contezi.log.reader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {
    
    private String file = "target/generated.log";
    private int lines = 300000;
    
    @Before
    public void setUp() throws IOException {
        new Generator.Builder().lines(lines).uri("/a").uri("/b").uri("/a/c").uri("/a/c/d").file(file).build().generate();
    }
    
    @After
    public void tearDown() {
        new File(file).delete();
    }
    
    @Test
    public void parsesFile() throws Exception {
        assertEquals(Optional.of(lines), new Parser(file).getRequestCount().values().stream().reduce(Integer::sum));
    }
}
