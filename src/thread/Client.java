package thread;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            
            InetAddress serverAddress = InetAddress.getByName("192.168.1.2");
            InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, 1234);

           
            Socket clientSocket = new Socket();

           
            clientSocket.connect(serverSocketAddress);

           
            OutputStream output = clientSocket.getOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(output);

         
            Operation op = new Operation(40, 20, '+');

           
            os.writeObject(op);

            
            InputStream input = clientSocket.getInputStream();
            ObjectInputStream is = new ObjectInputStream(input);

            
            op = (Operation) is.readObject();

        
            System.out.println("Result received from the server: " + op.getRes());
        } catch (Exception e) {
            System.out.println("Client: An error occurred - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
