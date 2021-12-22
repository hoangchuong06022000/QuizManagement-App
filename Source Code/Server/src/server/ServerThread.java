package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import models.*;

public class ServerThread implements Runnable{
    private Socket socket;
    private int index;
    public static String current_session = "";
    public static Boolean check;
    public static ArrayList<UserDTO> arrUser;
    public static ArrayList<DiemDTO> arrDiem;
    public static ArrayList<DiemDTO> arrDiemByMaDe;
    public static ArrayList<DeThiDTO> arrDeThi;
    public static ArrayList<CauHoiDTO> arrCauHoi;
    public static ArrayList<SecretKey> arrKey = new ArrayList<>();
    ExecuteED exec;
    
    public ServerThread(Socket socket, int index) {
    	this.index = index;
        this.socket = socket;
    }
    public void getSecretKeyFromServer(ObjectOutputStream out, ObjectInputStream in) {
    	try {
        	out.writeUTF(MainServer.publicKey);	
            out.flush();
            String line = in.readUTF();
            String decrypt = new RSA(0).decryptRSA(line, MainServer.privateKey);
            SecretKey key = new ExecuteED().convertStringToSecretKey(decrypt);
            arrKey.add(key);
            //System.out.println(ServerThread.key);
            //Thread.sleep(1000);
		} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException
				| NoSuchAlgorithmException e) {
			e.printStackTrace();
    	}catch (StreamCorruptedException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (SocketException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
//		} catch (InterruptedException e) {
//			e.printStackTrace();
		} 
    }
    @Override
    public void run() {
        System.out.println("Client " + socket.toString() +" connected!!");
        boolean getSecretKey = true;
//        while(true) {
        	try {
        		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                if(getSecretKey == true) {
        			getSecretKeyFromServer(out, in);
        			getSecretKey = false;
        		}
                String current = null;
                while(true) {       
            	    System.err.println("wait receive!!");
            	    Thread.sleep(1000);
                    current = in.readUTF();
                    System.out.println("receive = "+current);
                    String msg = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", current, arrKey.get(index));
                    receive(msg, out, in);
                } 
        	}catch (StreamCorruptedException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (EOFException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
               // break;
        	}catch (NullPointerException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (SocketException ex) {
        		//break;
        	}catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (Exception ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }  
        //}     
    	close();
    	MainServer.SoLuongOnline--;
    	System.out.println("Client " + socket.toString() + " closed!!");
    }
    public void receive(String current_session, ObjectOutputStream out, ObjectInputStream in) throws IOException{
        Thread thread = new Thread(){
            public void run(){
                try {
                    switch(current_session){
                        case "readUser": {
                        	try {
                        		arrUser = new UserDAO().readUser();
                                send(current_session, out);
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "addUser": {
                        	try {
                        		SealedObject sealedObject = (SealedObject) in.readObject();
                        		UserDTO user = (UserDTO) new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", sealedObject, arrKey.get(index));
                                check = new UserDAO().add(user);
                                send(current_session, out);  				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "modUser": {
                        	try {
                        		SealedObject sealedObject = (SealedObject) in.readObject();
                        		UserDTO user = (UserDTO) new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", sealedObject, arrKey.get(index));
                                check = new UserDAO().mod(user);
                                send(current_session, out);           				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "delUser": {
                        	try {
                        		String cipherText = in.readUTF();
                        		String userName = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", cipherText, arrKey.get(index));
                                check = new UserDAO().del(userName);
                                send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "readDeThi": {
                        	try {
                        		arrDeThi = new DeThiDAO().readDeThi();
                        		send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "addDeThi": {
                        	try {
                        		SealedObject sealedObject = (SealedObject) in.readObject();
                        		DeThiDTO deThi = (DeThiDTO) new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", sealedObject, arrKey.get(index));
                                check = new DeThiDAO().add(deThi);
                                send(current_session, out);            				
                        	}catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);            	                     	        	
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                        	break;
                            
                        }
                        case "modDeThi": {
                        	try {
                        		SealedObject sealedObject = (SealedObject) in.readObject();
                        		DeThiDTO deThi = (DeThiDTO) new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", sealedObject, arrKey.get(index));
                                check = new DeThiDAO().mod(deThi);
                                send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "delDeThi": {
                        	try {
                        		String cipherText = in.readUTF();
                        		String maDeThi = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", cipherText, arrKey.get(index));
                                check = new DeThiDAO().del(maDeThi);
                                send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "readDiem": {
                        	try {
                        		arrDiem = new DiemDAO().readDiem();
                        		send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "readDiemByMaDe": {
                        	try {
                        		String cipherText = in.readUTF();
                        		String maDeThi = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", cipherText, arrKey.get(index));
                        		arrDiemByMaDe = new DiemDAO().readDiemByMaDeThi(maDeThi);
                        		send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "addDiem": {
                        	try {
                        		SealedObject sealedObject = (SealedObject) in.readObject();
                        		DiemDTO diem = (DiemDTO) new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", sealedObject, arrKey.get(index));
                                check = new DiemDAO().add(diem);
                                send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "modDiem": {
                        	try {
                        		SealedObject sealedObject = (SealedObject) in.readObject();
                        		DiemDTO diem = (DiemDTO) new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", sealedObject, arrKey.get(index));
                                check = new DiemDAO().mod(diem);
                                send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "delDiem": {
                        	try {
                        		String cipherText = in.readUTF();
                        		String maDeThi = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", cipherText, arrKey.get(index));
                        		String cipherText2 = in.readUTF();
                        		String userName = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", cipherText2, arrKey.get(index));
	                            check = new DiemDAO().del(maDeThi, userName);
	                            send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                           
                            break;
                        }
                        case "readCauHoi": {
                        	try {
                        		String cipherText = in.readUTF();
                        		String maDeThi = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", cipherText, arrKey.get(index));
                        		arrCauHoi = new CauHoiDAO().readCauHoiByMaDeThi(maDeThi);
                        		send(current_session, out);           				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "addCauHoi": {
                        	try {
                        		SealedObject sealedObject = (SealedObject) in.readObject();
                        		CauHoiDTO cauHoi = (CauHoiDTO) new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", sealedObject, arrKey.get(index));
                                check = new CauHoiDAO().add(cauHoi);
                                send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                           
                            break;
                        }
                        case "modCauHoi": {
                        	try {
                        		SealedObject sealedObject = (SealedObject) in.readObject();
                        		CauHoiDTO cauHoi = (CauHoiDTO) new ExecuteED().decryptObjectAES("AES/ECB/PKCS5Padding", sealedObject, arrKey.get(index));
                                check = new CauHoiDAO().mod(cauHoi);
                                send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "delCauHoi": {
                        	try {
                        		String cipherText = in.readUTF();
                        		String stt = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", cipherText, arrKey.get(index));
                        		String cipherText2 = in.readUTF();
                        		String maDeThi = new ExecuteED().decryptAES("AES/ECB/PKCS5PADDING", cipherText2, arrKey.get(index));
                                check = new CauHoiDAO().del(Integer.parseInt(stt), maDeThi);
                                send(current_session, out);            				
                            }catch (StreamCorruptedException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (EOFException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	}catch (NullPointerException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex); 
            	        	}catch (IOException ex) {
            	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            	        	} catch (Exception e) {
            					e.printStackTrace();
            				}
                            
                            break;
                        }
                        case "LoginSuccess": {
                        	MainServer.SoLuongOnline++;
                        	check = true;
                        	send(current_session, out);
                            break;
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                }                       
            }
        };
        thread.start();
    }
    
    public void send(String current_session, ObjectOutputStream out) throws IOException{
        switch(current_session){ 
            case "readUser": {
            	try {
            		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, arrKey.get(index));
	            	out.writeUTF(cipherText);
	            	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", arrUser, arrKey.get(index));
	                out.writeObject(sealedObject);
	                out.flush();
	            }catch (StreamCorruptedException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (EOFException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (NullPointerException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	            }catch (IOException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	            }catch (Exception ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}
                break;
            }
            case "readDeThi": {
            	try {
            		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, arrKey.get(index));
	            	out.writeUTF(cipherText);
	            	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", arrDeThi, arrKey.get(index));
                    out.writeObject(sealedObject);
                    out.flush();
	            }catch (StreamCorruptedException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (EOFException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (NullPointerException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (IOException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	            }catch (Exception ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}
            	
                break;
            }
            case "readDiem": {
            	try {
            		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, arrKey.get(index));
	            	out.writeUTF(cipherText);
	            	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", arrDiem, arrKey.get(index));
                    out.writeObject(sealedObject);
                    out.flush();
	            }catch (StreamCorruptedException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (EOFException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (NullPointerException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (IOException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	            }catch (Exception ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}
            	
                break;
            }
            case "readDiemByMaDe": {
            	try {
            		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, arrKey.get(index));
	            	out.writeUTF(cipherText);
	            	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", arrDiemByMaDe, arrKey.get(index));
                    out.writeObject(sealedObject);
                    out.flush();
	            }catch (StreamCorruptedException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (EOFException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (NullPointerException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (IOException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	            }catch (Exception ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}
            	
                break;
            }
            case "readCauHoi": {
            	try {
            		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", current_session, arrKey.get(index));
	            	out.writeUTF(cipherText);
	            	SealedObject sealedObject = new ExecuteED().encryptObjectAES("AES/ECB/PKCS5PADDING", arrCauHoi, arrKey.get(index));
                    out.writeObject(sealedObject);
                    out.flush();
	            }catch (StreamCorruptedException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (EOFException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (NullPointerException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (IOException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	            }catch (Exception ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}
            	
                break;
            }
            default:{
            	try {
            		String cipherText = new ExecuteED().encryptAES("AES/ECB/PKCS5PADDING", String.valueOf(check), arrKey.get(index));
            		out.writeUTF(cipherText);
                    out.flush();
	            }catch (StreamCorruptedException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (EOFException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (NullPointerException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (IOException ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	            }catch (Exception ex) {
	                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	        	}
                
                break;
            }
        }
    }
    
    public void close(){
        try {
            socket.close();
        } catch (Exception ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
