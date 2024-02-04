import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12000);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String messageToSend = "Hello, Server!";
            out.println(messageToSend);
            System.out.println("Sent to server: " + messageToSend);

            // read response
            String serverResponse = in.readLine();
            System.out.println("Received from server: " + serverResponse);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
