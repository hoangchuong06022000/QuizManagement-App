
package Client.BUS;

import models.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;


public class ConnectServer {
    public static String current_session = "";
    public static Socket socket;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
    public static boolean check = false;
    public static String publicKey;
    public ExecuteED exec;
    public static SecretKey key;
    public static boolean getPublicKey = true;

    public ConnectServer(SecretKey key) {
    	ConnectServer.key = key;
    }
    public ConnectServer(Socket socket, String current_session, ObjectOutputStream out, ObjectInputStream in) throws IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException{
        ConnectServer.socket = socket;  
        ConnectServer.current_session = current_session;  
        ConnectServer.out = out; 
        ConnectServer.in = in;
        if(getPublicKey == true) { //Chỉ nhận public key lần đầu cho từng client
        	getPublicKeyFromServer();
        	getPublicKey = false;
        }
        send(ConnectServer.current_session);
        receive();
        
    }
    public ConnectServer(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
    	ConnectServer.socket = socket;  
        ConnectServer.out = out; 
        ConnectServer.in = in;
    }
    public void getPublicKeyFromServer() {
    	try {
    		String tmp = in.readUTF();
    		ConnectServer.publicKey = tmp;
    		//System.out.println(key);
    		
    		String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
			//System.out.println(encodedKey);
			String key = new RSA(0).encryptRSA(encodedKey, publicKey);
			out.writeUTF(key);
            out.flush();
            //Thread.sleep(1000);
		} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException
				| NoSuchAlgorithmException e) {
			e.printStackTrace();
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (SocketException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (IOException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex); 
//		} catch (InterruptedException e) {
//			e.printStackTrace();
		} 
    }
    
    public void receive(){
        Thread thread = new Thread(){
            public void run(){       
            	String line = null;
            	String msg = null;
				try {
					System.err.println("wait receive!!");
					line = in.readUTF();
					msg = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", line, key);
					System.out.println("receive = "+msg);
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
                    switch(msg){
                        case "readUser": {
                        	try {
                        		SealedObject tmp = (SealedObject) in.readObject();                        		
                                UserBUS.arrUser = (ArrayList<UserDTO>) exec.convertObjectToList(new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", tmp, key));
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
                            	SealedObject tmp = (SealedObject) in.readObject();
                                DeThiBUS.arrDeThi = (ArrayList<DeThiDTO>) exec.convertObjectToList(new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", tmp, key));                          
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
                        		SealedObject tmp = (SealedObject) in.readObject(); 
                                DiemBUS.arrDiem = (ArrayList<DiemDTO>) exec.convertObjectToList(new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", tmp, key));
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
                        		SealedObject tmp = (SealedObject) in.readObject(); 
                                DiemBUS.arrDiemByMaDe = (ArrayList<DiemDTO>) exec.convertObjectToList(new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", tmp, key));
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
                        		SealedObject tmp = (SealedObject) in.readObject(); 
                                CauHoiBUS.arrCauHoi = (ArrayList<CauHoiDTO>) exec.convertObjectToList(new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", tmp, key));        
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
    
    public void send(String current_session) throws IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException{
    	String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
    	out.writeUTF(cipherText);
        System.out.println(cipherText);
        out.flush();
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
        	String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
        	out.writeUTF(cipherText);
        	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", user, key);
            out.writeObject(sealedObject);
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
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
    		out.writeUTF(cipherText);
    		String cipherTextUserName = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", userName, key);
            out.writeUTF(cipherTextUserName);
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
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
        	out.writeUTF(cipherText);
        	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", dethi, key);
            out.writeObject(sealedObject);
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
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
    		out.writeUTF(cipherText);
    		String cipherTextMaDeThi = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", maDeThi, key);
            out.writeUTF(cipherTextMaDeThi);
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
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
        	out.writeUTF(cipherText);
        	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", diem, key);
            out.writeObject(sealedObject);
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
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
        	out.writeUTF(cipherText);
    		String cipherTextMaDeThi = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", maDeThi, key);
            out.writeUTF(cipherTextMaDeThi);
    		String cipherTextUserName = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", userName, key);
            out.writeUTF(cipherTextUserName);
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
    		System.out.println(current_session);
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
    		out.writeUTF(cipherText);
    		String cipherTextMaDeThi = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", maDeThi, key);
            out.writeUTF(cipherTextMaDeThi);
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
    		System.out.println(current_session);
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
    		out.writeUTF(cipherText);
    		String cipherTextMaDeThi = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", maDeThi, key);
            out.writeUTF(cipherTextMaDeThi);
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
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
        	out.writeUTF(cipherText);
        	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", cauHoi, key);
            out.writeObject(sealedObject);
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
    		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, key);
    		out.writeUTF(cipherText);
    		String cipherTextSTT = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", String.valueOf(stt), key);
            out.writeUTF(cipherTextSTT);
    		String cipherTextMaDeThi = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", maDeThi, key);
            out.writeUTF(cipherTextMaDeThi);
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
