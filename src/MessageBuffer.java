import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBuffer {
    private final Map<String, String> requestMessageBuffer = new ConcurrentHashMap<>();

    public void dispatchRequest(String requestId, String message){
        requestMessageBuffer.put(requestId, message);
    }

    public synchronized Map.Entry<String, String> getRequestMessage(){
        Map.Entry<String, String> entry = requestMessageBuffer.entrySet().stream().findFirst().orElse(null);
        if(entry != null) requestMessageBuffer.remove(entry.getKey());
        return entry;
    }
}
