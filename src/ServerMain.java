import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerMain
{
   public static void main(String[] args)
   {
      Scanner messageScanner;
      Map<String, Socket> activeUsers = new HashMap<>();

      try
      {

         ServerSocket ss = new ServerSocket(6666);
         MessageOutput messageOutput = new MessageOutput(ss);
         messageOutput.setMoreThread(true);

         while(true){
            if(messageOutput.moreThread()){
               messageOutput.setMoreThread(false);
               Runnable r = new ServerListener(messageOutput);
               Thread t = new Thread(r);
               t.start();
            }
            Thread.sleep(100);
            if(messageOutput.isUpdated()){
               System.out.println("New Message");
               messageOutput.setUpdated(false);
               if(messageOutput.getUsername().equals(messageOutput.getRecipient())){
                  activeUsers.put(messageOutput.getUsername(), messageOutput.getSocket());
               }

               messageOutput.setSocket(activeUsers.get(messageOutput.getRecipient()));

               Runnable r = new ServerOutputThread(messageOutput);
               Thread t = new Thread(r);
               t.start();

            }

         }



      }
      catch (Exception e){
         System.out.println(e);
      }
   }
}
