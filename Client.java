import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {
        try {
            System.out.println("Enter name");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            int port = 4004;
            InetAddress hostIP = InetAddress.getLocalHost();
            InetSocketAddress myAddress = new InetSocketAddress(hostIP, port);
            SocketChannel myClient = SocketChannel.open(myAddress);
            ByteBuffer myBuffer = ByteBuffer.allocate(102400);
            myBuffer.put(name.getBytes());
            myBuffer.flip();
            while (true) {
                myBuffer = ByteBuffer.allocate(1024);
                myClient.read(myBuffer);
                String data = (new String(myBuffer.array())).trim();
                if (!data.equals("")) {
                    System.out.println(data);
                }
                if(scanner.hasNext()){
                    String message = scanner.nextLine();
                    myBuffer = ByteBuffer.allocate(102400);
                    myBuffer.put(message.getBytes());
                    myBuffer.flip();
                }
            }
        } catch (UnknownHostException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            var8.printStackTrace();
        }
    }
}