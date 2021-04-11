import java.net.ServerSocket;
import java.net.Socket;

public class MessageOutput
{
   private boolean updated;
   private boolean moreThread;
   private String message;
   private String username;
   private String recipient;
   private Socket socket;
   private ServerSocket ss;

   MessageOutput(ServerSocket ss){
      updated = false;
      this.ss = ss;
   }

   public String getRecipient()
   {
      return recipient;
   }

   public void setRecipient(String recipient)
   {
      this.recipient = recipient;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String username)
   {
      this.username = username;
   }


   public ServerSocket getServerSocket(){
      return ss;
   }

   public String getMessage()
   {
      return message;
   }

   public void setMessage(String message)
   {
      this.message = message;
   }

   public boolean isUpdated()
   {
      return updated;
   }

   public void setUpdated(boolean updated)
   {
      this.updated = updated;
   }

   public void setSocket(Socket socket)
   {
      this.socket = socket;
   }

   public Socket getSocket()
   {
      return socket;
   }

   public boolean moreThread()
   {
      return moreThread;
   }

   public void setMoreThread(boolean moreThread)
   {
      this.moreThread = moreThread;
   }
}
