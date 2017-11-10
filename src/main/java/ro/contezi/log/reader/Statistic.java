package ro.contezi.log.reader;

public class Statistic {
    private final String ip;
    private final String request;
    private final String bytesSent;
    
    public Statistic(String logLine) {
        String[] bySpace = logLine.split(" ");
        ip = bySpace[0];
        request = bySpace[6];
        bytesSent = bySpace[8];
    }
    
    @Override
    public String toString() {
        return "{\"ip\":\""+ip+"\",\"request\":\""+request+"\",\"bytesSent\":"+bytesSent+"}";
    }

    public String getRequest() {
        return request;
    }
}
