import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Chat_with_Server{
   public Socket socket;
   public BufferedReader from_server;
   public PrintWriter to_server;

   public Chat_with_Server(String host_name,int port) throws IOException {
       socket = new Socket(host_name,port);
       from_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       to_server = new PrintWriter(socket.getOutputStream(),true);
   }
   // reading from the server which is myself cause we are using loopback ip address
   public String read() throws IOException {
       return from_server.readLine();
   }
// writing to the server
   public void write_to_server(String msg){
       if(msg.length()<=0) msg = ".";
       else{
           to_server.println("Client: " + msg);
       }
   }

   public void close() throws IOException {
       socket.close();
       from_server.close();
       to_server.close();
   }
}


public class client {

public static void main(String[] argv){
    final Scanner sc = new Scanner(System.in);
    String msg;
    try {
        Chat_with_Server chat = new Chat_with_Server("127.0.0.1",6013);
        msg = sc.nextLine();
       while(true){ if(msg.equals("bye")) break;
           chat.write_to_server(msg);
          System.out.println(chat.read());
           System.out.println(": ");
           msg = sc.nextLine();
       }
       chat.close();
    }
    catch (IOException ioe) {
        System.err.println(ioe);
    }
}
}
