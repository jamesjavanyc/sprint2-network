import java.io.IOException;
import java.net.Socket;

public interface RequestHolder {
    Socket getNextRequest();
    boolean hasNextRequest();
    void response(Socket request, String response) throws IOException;
}
