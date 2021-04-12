import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerMain
{
   public static void main(String[] args) throws IOException
   {

      Thread latest = null;
      Map<String, Socket> activeUsers = new HashMap<>();
      ServerSocket ss = null;
      int counter = 0;

      try
      {

         ss = new ServerSocket(6666);
         MessageOutput messageOutput = new MessageOutput(ss);
         messageOutput.setMoreThread(true);
         messageOutput.setNumSockets(0);
         while(true){
            if(messageOutput.moreThread()){
               messageOutput.setMoreThread(false);
               Runnable r = new ServerListener(messageOutput);
               Thread t = new Thread(r);
               t.start();
               messageOutput.setNumSockets(messageOutput.getNumSockets()+1);
               counter = 0;
            }
            Thread.sleep(100);
            if(messageOutput.isUpdated()){
               messageOutput.setUpdated(false);
               if(messageOutput.getUsername().equals(messageOutput.getRecipient())){
                  activeUsers.put(messageOutput.getUsername(), messageOutput.getSocket());
               }

               messageOutput.setSocket(activeUsers.get(messageOutput.getRecipient()));

               Runnable r = new ServerOutputThread(messageOutput);
               Thread t = new Thread(r);
               t.start();

            }
            if(messageOutput.getNumSockets() == 1){
               counter = counter+1;
               if(counter>100){
                  latest.interrupt();
               }
            }
            if(messageOutput.getNumSockets() == 0){
               System.out.println("Sever Timeout");
               ss.close();
            }

         }



      }
      catch (Exception e){
      }
      finally{
         ss.close();
         System.out.println("Sever Timeout");
      }
   }
}
