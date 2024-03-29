import java.io.IOException;
import java.util.Map;

public class MessageWorker implements Runnable{
    private final int workerId;
    private final MessageBuffer sourceMessageBuffer;

    public MessageWorker(int id, MessageBuffer messageBuffer) {
        this.workerId = id;
        this.sourceMessageBuffer = messageBuffer;
    }

    private String resolveRequestMessage(String message){
        try {
            Thread.sleep(1000);
            return "Worker " + this.workerId + " received your message:" + message;
        }catch (Exception e){
            return "Exception when resolve message";
        }
    }

    @Override
    public void run() {
        while(true){
            Map.Entry<String, String> messageEntry = this.sourceMessageBuffer.getRequestMessage();
            if(messageEntry != null){
                String response = resolveRequestMessage(messageEntry.getValue());
                try {
                    Server.messageResolved(messageEntry.getKey(), response);
                } catch (IOException e) {
                    System.out.println("IO exception when sending message to client.");
                }
            }
        }
    }
}
