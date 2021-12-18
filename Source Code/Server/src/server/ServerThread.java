package server;

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

import models.*;

public class ServerThread implements Runnable{
    private Socket socket;
    public static String current_session = "";
    public static Boolean check;
    public static ArrayList<UserDTO> arrUser;
    public static ArrayList<DiemDTO> arrDiem;
    public static ArrayList<DeThiDTO> arrDeThi;
    public static ArrayList<CauHoiDTO> arrCauHoi;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ServerThread() {
	}
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        System.out.println("Client " + socket.toString() +" connected!!");
        while(true) {
        	try {
        		out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                String current = null;
                while(true) {       
            		System.err.println("wait receive!!");
            		Thread.sleep(1000);
                    current = in.readUTF();
                    System.out.println("receive = "+current);
                    ServerThread.current_session = current;
                    receive(ServerThread.current_session);
                }     
        	}catch (StreamCorruptedException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (EOFException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                break;
        	}catch (NullPointerException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (SocketException ex) {
        		break;
        	}catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (Exception ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }     
    	close();
    	System.out.println("Client " + socket.toString() + " closed!!");
    }
    public void receive(String current_session) throws IOException{
        Thread thread = new Thread(){
            public void run(){
                try {
                    switch(current_session){
                        case "readUser": {
                        	try {
                        		arrUser = new UserDAO().readUser();
                                send(current_session);
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
                        		UserDTO user = (UserDTO) in.readObject();
                                check = new UserDAO().add(user);
                                send(current_session);            				
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
                        		UserDTO user = (UserDTO) in.readObject();
                                check = new UserDAO().mod(user);
                                send(current_session);            				
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
                        		String userName = in.readUTF();
                                check = new UserDAO().del(userName);
                                send(current_session);            				
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
                                send(current_session);            				
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
                        		DeThiDTO deThi = (DeThiDTO) in.readObject();
                                check = new DeThiDAO().add(deThi);
                                send(current_session);            				
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
                        		DeThiDTO deThi = (DeThiDTO) in.readObject();
                                check = new DeThiDAO().mod(deThi);
                                send(current_session);            				
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
                        		String maDeThi = in.readUTF();
                                check = new DeThiDAO().del(maDeThi);
                                send(current_session);            				
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
                                send(current_session);            				
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
                        		DiemDTO diem = (DiemDTO) in.readObject();
                                check = new DiemDAO().add(diem);
                                send(current_session);            				
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
                        		DiemDTO diem = (DiemDTO) in.readObject();
                                check = new DiemDAO().mod(diem);
                                send(current_session);            				
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
                        		 String maDeThi = in.readUTF();
                                 String userName = in.readUTF();
                                 check = new DiemDAO().del(maDeThi, userName);
                                 send(current_session);            				
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
                        		String maDeThi = in.readUTF();
                        		arrCauHoi = new CauHoiDAO().readCauHoiByMaDeThi(maDeThi);
                                send(current_session);            				
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
                        		 CauHoiDTO cauHoi = (CauHoiDTO) in.readObject();
                                 check = new CauHoiDAO().add(cauHoi);
                                 send(current_session);            				
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
                        		CauHoiDTO cauHoi = (CauHoiDTO) in.readObject();
                                check = new CauHoiDAO().mod(cauHoi);
                                send(current_session);            				
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
                        		String stt = in.readUTF();
                                String maDeThi = in.readUTF();
                                check = new CauHoiDAO().del(Integer.parseInt(stt), maDeThi);
                                send(current_session);            				
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
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                }                       
            }
        };
        thread.start();
    }
    
    public void send(String current_session) throws IOException{
        switch(current_session){
            case "readUser": {
            	try {
	            	out.writeUTF(current_session);
	                out.writeObject(arrUser);
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
            		out.writeUTF(current_session);
                    out.writeObject(arrDeThi);
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
            		out.writeUTF(current_session);
                    out.writeObject(arrDiem);
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
            		out.writeUTF(current_session);
                    out.writeObject(arrCauHoi);
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
            		out.writeUTF(String.valueOf(check));
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
            out.close();
            in.close();
            socket.close();
        } catch (Exception ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
