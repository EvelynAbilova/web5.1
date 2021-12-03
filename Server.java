import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Server {
    private static Selector selector;
    private static Map<String, SocketChannel> clients = new HashMap<>();

    public static void main(String[] args) {
        try {
            InetAddress hostIP = InetAddress.getLocalHost();
            int port = 4004;
            selector = Selector.open();
            ServerSocketChannel mySocket = ServerSocketChannel.open();
            ServerSocket serverSocket = mySocket.socket();
            InetSocketAddress address = new InetSocketAddress(hostIP, port);
            serverSocket.bind(address);
            System.out.println("Server is on");
            mySocket.configureBlocking(false);
            int ops = mySocket.validOps();
            mySocket.register(selector, ops, (Object) null);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();

                for (Iterator i = selectedKeys.iterator(); i.hasNext(); i.remove()) {
                    SelectionKey key = (SelectionKey) i.next();
                    if (key.isAcceptable()) {
                        processAcceptEvent(mySocket, key);
                    }

                   /* if (key.isWritable()) {
                        processWriteEvent(mySocket, key);
                    }*/
                }
            }
        } catch (IOException var10) {
            var10.printStackTrace();
        }
    }

    private static void processAcceptEvent(ServerSocketChannel mySocket, SelectionKey key) throws IOException {
        System.out.println("Client connected");
        SocketChannel myClient = mySocket.accept();
        myClient.configureBlocking(false);
        ByteBuffer myBuffer = ByteBuffer.allocate(102400);
        mySocket.accept().read(myBuffer);
        String name=new String(myBuffer.array()).trim();
        clients.put(name, myClient);
/*
        handleMessaging();
*/
        System.out.println(name + "connected");
    }

    private static void handleMessaging() throws IOException {


    }

/*
    private static void processWriteEvent(ServerSocketChannel mySocket, SelectionKey key) throws IOException {
        SocketChannel myClient = (SocketChannel) key.channel();
        ByteBuffer myBuffer = ByteBuffer.allocate(102400);
        myBuffer.put(data.getBytes());
        myBuffer.flip();
        myClient.write(myBuffer);
        myClient.close();
        System.out.println("Message is sent");
    }
*/
}
