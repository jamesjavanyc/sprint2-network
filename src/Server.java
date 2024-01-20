import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            // 创建 ServerSocket 对象并绑定到指定端口
            ServerSocket serverSocket = new ServerSocket(12000);

            System.out.println("Server is running and waiting for connections...");

            // 监听客户端连接
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established with " + clientSocket.getInetAddress());

            // 获取输入流和输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // 从客户端读取数据并回送响应
            String clientMessage = in.readLine();
            System.out.println("Received from client: " + clientMessage);

            // 向客户端发送响应
            out.println("Hello, Client! I received your message: " + clientMessage);

            // 关闭连接
            clientSocket.close();
            serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
