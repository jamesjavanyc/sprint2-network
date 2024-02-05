import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Server {

    private static final int PORT = 12000;
    private static final MessageBuffer messageBuffer = new MessageBuffer();
    private static final Map<String, Socket> sockets = new HashMap<>();
    private static ServerPortListener requestHolder;

    public static void main(String[] args) throws IOException {
        new Thread(new MessageWorker(1, messageBuffer)).start();
        new Thread(new MessageWorker(2, messageBuffer)).start();
        new Thread(new MessageWorker(3, messageBuffer)).start();
        new Thread(new MessageWorker(4, messageBuffer)).start();

        requestHolder = new ServerPortListener(PORT);
        Thread serverThread = new Thread(requestHolder);
        serverThread.start();
        while (serverThread.isAlive()){
            if(requestHolder.hasNextRequest()){
                Socket request = requestHolder.getNextRequest();
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));

                String clientMessage = in.readLine();
                System.out.println("Received from client: " + request.getInetAddress() + ":" + clientMessage);

                String requestId = UUID.randomUUID().toString();
                sockets.put(requestId, request);
                messageBuffer.dispatchRequest(requestId, clientMessage);
            }
        }
    }

    public static void messageResolved(String requestId, String response) throws IOException {
        Socket socket = sockets.get(requestId);
        requestHolder.response(socket, response);
    }
}
