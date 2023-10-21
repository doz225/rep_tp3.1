package thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    int ord = 0; //initialiser un compteur a 0
    ServerSocket ss; 

    public static void main(String[] args) throws IOException {
        new Server().start(); // lancer un serveur thread
    }

    public void run() {
        try (ServerSocket ss = new ServerSocket(1234);) {
            while (true) {
                Socket s = ss.accept(); // Accept la connection du client
                new ClientProcess(s, ord++).start(); // creation d'un nouveau thread pour poignér le client and incrementé le compteur
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   
    public class ClientProcess extends Thread {
        Socket s; // client socket.
        int ord;

        public ClientProcess(Socket s, int ord) {
            this.s = s;
            this.ord = ord;
        }

        public void run() {
            System.out.println("Client connected " + s.getRemoteSocketAddress() + " order: " + this.ord);

            
            InputStream input = null;
            try {
                input = s.getInputStream();
                ObjectInputStream is = new ObjectInputStream(input);

              
                Operation op = (Operation) is.readObject();

           
                int nb1 = op.getNb1();
                int nb2 = op.getNb2();
                char ops = op.getOp();

                int res = 0;

             
                switch (ops) {
                    case '+':
                        res = nb1 + nb2;
                        break;
                    case '-':
                        res = nb1 - nb2;
                        break;
                    case '*':
                        res = nb1 * nb2;
                        break;
                    case '/':
                        res = nb1 / nb2;
                        break;
                }

            
                op.setRes(res);

             
                OutputStream output = s.getOutputStream();
                ObjectOutputStream oo = new ObjectOutputStream(output);

               
                oo.writeObject(op);

                s.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}