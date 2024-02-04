import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerPortListener implements Runnable, RequestHolder{
    private final int port;
    private final List<Socket> sockets = new LinkedList<>();

    public ServerPortListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server start success on port :" + port);
            while (true){
                Socket clientSocket = serverSocket.accept();
                sockets.add(clientSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasNextRequest(){
        return !this.sockets.isEmpty();
    }

    public Socket getNextRequest(){
        Socket request = this.sockets.get(0);
        this.sockets.remove(0);
        return request;
    }

    public void response(Socket request, String response) throws IOException {
        PrintWriter out = new PrintWriter(request.getOutputStream(), true);
        out.println(response);
        request.close();
        System.out.println("Response message to " + request.getInetAddress() + ":" + response);
    }
}
