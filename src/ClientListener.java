import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener implements Runnable
{
   private Socket socket;
   String clientUsername;
   private boolean loop;

   public ClientListener(Socket s, String username)
   {
      socket = s;
      clientUsername = "[" + username + "]";
      loop = true;
   }

   @Override
   public void run()
   {

      while (loop){
         try
         {
            DataInputStream din = new DataInputStream(socket.getInputStream());
            Scanner threadScanner = new Scanner(din.readUTF());
            String recipient = threadScanner.next();
            if(recipient.equals(clientUsername)){
               System.out.println(threadScanner.nextLine());
            }
         }
         catch (IOException e)
         {
            loop = false;
         }
      }
   }
}
