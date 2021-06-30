

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// Socket in java // used for communication between host and the client
/*
   Two basic method are used to receive and send data via socket
   getOutputStream() used send data to the socket.
   getInputStream()  used  read data from the socket
   both return an inputStream class type;
 */
class Server {
    public Socket client_socket; // used to listen for client port
    public ServerSocket sockets; // server for connection
    public PrintWriter to_client; // write to a server
    public BufferedReader from_client; // read from server using bufferedReader;
    public Server(int port) throws IOException {
        sockets = new ServerSocket(port); // listen to client port
        client_socket = sockets.accept(); // accept the connection
        from_client = new BufferedReader(new InputStreamReader(client_socket.getInputStream())); // data from client
        to_client = new PrintWriter(client_socket.getOutputStream(),true); // write to client
    }

    public void write_to_client(String message){
        if(message.equals("")) message = ".";
        else{
             to_client.println("Server: " + message);
        }
    }

    public String client_response() throws IOException {
     return from_client.readLine();
    }

    public void close() throws IOException {
        sockets.close();
        from_client.close();
        client_socket.close();
        to_client.close();
    }

}
public class Tuto {

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        try {
            String chat;
            Server client = new Server(6013);
       // chat = sc.nextLine();
            while(true){
                System.out.println(client.client_response());
                System.out.println(": ");
                chat = sc.nextLine();
                if (chat.equals("bye")) break;
                client.write_to_client(chat);
            }
            client.close();
        } catch (IOException ie) {
            System.err.print(ie);
        }
    }
}
