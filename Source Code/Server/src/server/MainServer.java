package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainServer {
    public static int port = 2000;
    public static int numThread = 5;
    public static ServerSocket server;
    
    public MainServer() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try {
        	server = new ServerSocket(port);
            System.out.println("Server is starting.....");
            System.out.println("Waiting for client.....");
            Thread thread = new Thread(){
                public void run(){
                    while(true){
                        try {
                            Socket socket = server.accept();
                            executor.execute(new ServerThread(socket));
                        }catch (StreamCorruptedException ex) {
                            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    	}catch (EOFException ex) {
                            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    	}catch (NullPointerException ex) {
                            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    	}catch (SocketException ex) {
                    		break;
                    	}catch (IOException ex) {
        	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    	} catch (Exception ex) {
                            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            thread.start();   
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			if(server == null) {
				server.close();
			}
		}	
    }   
    public static void main(String[] args) throws IOException {
        new MainServer();
    }   
}
