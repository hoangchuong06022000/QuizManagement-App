
package Client.BUS;

import models.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


public class ConnectServer {
    public static String current_session = "";
    public static Socket socket;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
    public static boolean check = false;
    private ExecuteED exec;
    //private static SecretKey key;
    //private static IvParameterSpec iv;

    public ConnectServer(Socket socket, String current_session, ObjectOutputStream out, ObjectInputStream in) throws IOException{
        ConnectServer.socket = socket;  
        ConnectServer.current_session = current_session;  
        ConnectServer.out = out; 
        ConnectServer.in = in;
        send(ConnectServer.current_session);
        receive();
        
    }
    public ConnectServer(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
    	ConnectServer.socket = socket;  
        ConnectServer.out = out; 
        ConnectServer.in = in;
    }
    
    public void receive(){
        Thread thread = new Thread(){
            public void run(){       
            	String line = null;
				try {
					System.err.println("wait receive!!");
					line = in.readUTF();
					System.out.println("receive = "+line);
				}catch (StreamCorruptedException ex) {
	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (EOFException ex) {
	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (SocketException ex) {
	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (NullPointerException ex) {
	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
	        	}catch (IOException ex) {
	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
	        	} catch (Exception e) {
					e.printStackTrace();
				}
                try {
                    switch(line){
                        case "readUser": {
                        	try {
                        		Object tmp = in.readObject();
                                UserBUS.arrUser = (ArrayList<UserDTO>) exec.convertObjectToList(tmp);
                                break;
                        	}catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}    
                        }
                        case "readDeThi": { 
                            try {
                            	Object tmp = in.readObject();
                                DeThiBUS.arrDeThi = (ArrayList<DeThiDTO>) exec.convertObjectToList(tmp);                           
                                break;
                        	}catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}    
                        }
                        case "readDiem": {
                        	try {
                        		Object tmp = in.readObject();
                                DiemBUS.arrDiem = (ArrayList<DiemDTO>) exec.convertObjectToList(tmp);
                                break;
                        	}catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}    
                            
                        }
                        case "readDiemByMaDe": {
                        	try {
                        		Object tmp = in.readObject();
                                DiemBUS.arrDiemByMaDe = (ArrayList<DiemDTO>) exec.convertObjectToList(tmp);
                                break;
                        	}catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}    
                            
                        }
                        case "readCauHoi": {
                        	try {
                        		Object tmp = in.readObject();
                                CauHoiBUS.arrCauHoi = (ArrayList<CauHoiDTO>) exec.convertObjectToList(tmp);        
                                break;
                        	}catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (SocketException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
            				} 
                        }
                        case "true": {
                        	boolean check = true;
                        	ConnectServer.check = check;
                        	break;
                        }
                        case "false": {
                        	boolean check = false;
                        	ConnectServer.check = check;
                        	break;
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
	                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
	            }
            }                            
        };
        thread.start();
    }
    
    public void send(String current_session) throws IOException{
        System.out.println("send :"+current_session);
        switch(current_session){
            case "readUser": {
                out.writeUTF(current_session);
                out.flush();
                break;
            }
            case "readDeThi": {
                out.writeUTF(current_session);
                out.flush();        
                break;
            }
            case "readDiem": {
                out.writeUTF(current_session);
                out.flush();
                break;
            }
        }
    }
    
    public void close(){
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean addOrModUser(UserDTO user, String current_session) throws IOException{   
        try {
        	out.writeUTF(current_session);
            out.writeObject(user);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
        
        return ConnectServer.check;
    }
    
    public boolean delUser(String userName, String current_session) throws IOException{
    	try {
    		out.writeUTF(current_session);
            out.writeUTF(userName);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		}    
        
        return ConnectServer.check;
    }
    
    public boolean addOrModDeThi(DeThiDTO dethi, String current_session) throws IOException{
    	try {
    		out.writeUTF(current_session);
            out.writeObject(dethi);
            out.flush();   
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		}    
        return ConnectServer.check;
    }
    
    public boolean delDeThi(String maDeThi, String current_session) throws IOException{
    	try {
    		out.writeUTF(current_session);
            out.writeUTF(maDeThi);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		}    
        
        return ConnectServer.check;
    }
    
    public boolean addOrModDiem(DiemDTO diem, String current_session) throws IOException{
    	try {
    		out.writeUTF(current_session);
            out.writeObject(diem);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		}    
        
        return ConnectServer.check;
    }
    
    public boolean delDiem(String maDeThi, String userName, String current_session) throws IOException{
    	try {
    		out.writeUTF(current_session);
            out.writeUTF(maDeThi);
            out.writeUTF(userName);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		}    
        
        return ConnectServer.check;
    }
    
    public void readDiemByMaDeThi(String maDeThi, String current_session ){
    	try {
    		out.writeUTF(current_session);
            out.writeUTF(maDeThi);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    public void readCauHoiByMaDeThi(String maDeThi, String current_session ){
    	try {
    		out.writeUTF(current_session);
            out.writeUTF(maDeThi);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    public boolean addOrModCauHoi(CauHoiDTO cauHoi, String current_session) throws IOException{
    	try {
    		out.writeUTF(current_session);
            out.writeObject(cauHoi);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		}    
        
        return ConnectServer.check;
    }
    
    public boolean delCauHoi(int stt, String maDeThi, String current_session) throws IOException{
    	try {
    		out.writeUTF(current_session);
            out.writeUTF(String.valueOf(stt));
            out.writeUTF(maDeThi);
            out.flush();
            receive();
            Thread.sleep(1000);
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
		} catch (Exception e) {
			e.printStackTrace();
		}    
        
        return ConnectServer.check;
    }
}
