import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            // 创建 Socket 对象并连接到本机的 12000 端口
            Socket socket = new Socket("localhost", 12000);

            // 获取输入流和输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 向服务器发送请求
            String messageToSend = "Hello, Server!";
            out.println(messageToSend);
            System.out.println("Sent to server: " + messageToSend);

            // 从服务器读取响应
            String serverResponse = in.readLine();
            System.out.println("Received from server: " + serverResponse);

            // 关闭连接
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
