package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.RSA;
import models.ThongKeDiemDTO;
import models.UserDTO;

public class MainServer {
    public static int SoLuongOnline = 0;
    public static int port = 2000;
    public static int numThread = 5;
    public static ServerSocket server;
    public static String privateKey;
    public static String publicKey;
    public static int index = 0;
    
    public MainServer() throws IOException, NoSuchAlgorithmException {
    	RSA rsa = new RSA();
    	MainServer.privateKey = rsa.privateKey;
    	MainServer.publicKey = rsa.publicKey;
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
                            executor.execute(new ServerThread(socket, index));
                            index++;
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
            while(true){
            	System.out.println("1/ Th???ng k?? s??? ng?????i d??ng ??ang Online");
            	System.out.println("2/ T???ng s??? ????? thi");
            	System.out.println("3/ ??i???m thi cao nh???t v?? th???p nh???t c???a ????? thi");
            	System.out.println("4/ Kh??a t??i kho???n c???a user");
            	System.out.println("Ch???n ");
            	Scanner sc = new Scanner(System.in);
            	String luachon = sc.nextLine();
            	switch (luachon) {
				case "1" :{
					System.out.println("S??? ng?????i Online hi???n t???i l?? : " + SoLuongOnline);
					break;
				}
				case "2" :{
					DeThiDAO DAO = new DeThiDAO();
					System.out.println("T???ng s??? ????? thi : " + DAO.TongSoDeThi());
					break;
				}
				case "3": {
					DiemDAO DAO = new DiemDAO();
					ArrayList<ThongKeDiemDTO> listThongKe = DAO.ThongKeDiem();
					for(ThongKeDiemDTO ThongKe : listThongKe)
					{
						System.out.println("M?? ????? thi : " + ThongKe.getmaDethi() + " - ??i???m cao nh???t : " + ThongKe.getdiemMax() + " - ??i???m th???p nh???t : " + ThongKe.getdiemMin());
					}
					break;
				}
				case "4":{
					UserDAO DAO = new UserDAO();
					ArrayList<UserDTO> listUser = DAO.readUser();
					while(true)
					{
						System.out.println("0. Quay l???i");
						int i = 1;
						for(UserDTO User : listUser)
						{
							System.out.println(i++ + ". " + User.getUserName());
						}
						Scanner sc1 = new Scanner(System.in);
						System.out.println("Ch???n user ????? kh??a : ");
						String chon = sc1.nextLine();
						try
						{
							int VT = Integer.parseInt(chon);
							if(VT == 0) {
								break;
							}
							if(VT >= 1 && VT < listUser.size())
							{
								UserDTO US = listUser.get(VT);
								US.setTrangThai(0);
								DAO.mod(US);
								System.out.println("Kh??a th??nh c??ng !!!");
								break;
							}
							else
							{
								System.out.println("B???n ???? ch???n sai m???i ch???n l???i : ");
							}
						}
						catch (Exception e) {
							System.out.println("B???n ???? ch???n sai m???i ch???n l???i : ");
						}
					}
					break;
				}
				default:
					System.out.println("B???n ???? ch???n sai.");
					break;
				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			if(server == null) {
				server.close();
			}
		}	
    }   
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        new MainServer();
    }   
}
