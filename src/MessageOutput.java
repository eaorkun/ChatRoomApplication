/* EE422C Assignment #4 submission by
 * Eralp Orkun
 * eao789
 * Lab Section: Unique #17110, (5-6:30pm Thursday)
 */

import java.net.ServerSocket;
import java.net.Socket;

/**
 * MessageOutput class which is used between SeverMain and thread to communicate
 *
 * @author Eralp Orkun
 */
public class MessageOutput
{
   private boolean updated;
   private boolean moreThread;
   private String message;
   private String username;
   private String recipient;
   private Socket socket;
   private final ServerSocket ss;
   private int numSockets;

   MessageOutput(ServerSocket ss)
   {
      updated = false;
      this.ss = ss;
   }

   public int getNumSockets()
   {
      return numSockets;
   }

   public void setNumSockets(int numSockets)
   {
      this.numSockets = numSockets;
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


   public ServerSocket getServerSocket()
   {
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

   public Socket getSocket()
   {
      return socket;
   }

   public void setSocket(Socket socket)
   {
      this.socket = socket;
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
