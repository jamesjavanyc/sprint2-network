import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Server {

    private static final int PORT = 12000;
    private static final MessageBuffer messageBuffer = new MessageBuffer();
    private static final Map<String, Socket> sockets = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            new Thread(new MessageWorker(1, messageBuffer)).start();
            new Thread(new MessageWorker(2, messageBuffer)).start();
            System.out.println("Server start success on port :" + PORT);
            while (true){
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String clientMessage = in.readLine();
                System.out.println("Received from client: " + clientSocket.getInetAddress() + ":" + clientMessage);

                String requestId = UUID.randomUUID().toString();
                sockets.put(requestId, clientSocket);
                messageBuffer.dispatchRequest(requestId, clientMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void messageResolved(String requestId, String response) throws IOException {
        Socket socket = sockets.get(requestId);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(response);
        socket.close();
        System.out.println("Response message to " + socket.getInetAddress() + ":" + response);
    }
}
