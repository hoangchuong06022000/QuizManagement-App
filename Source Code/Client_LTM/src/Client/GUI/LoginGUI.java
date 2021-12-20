package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import Client.BUS.ConnectServer;
import Client.BUS.DeThiBUS;
import Client.BUS.UserBUS;
import models.ExecuteED;


public class LoginGUI extends JFrame {
	private static String host = "localhost";
    private static int port = 2000;
    public static String current_session = "";
    public static String error_mess = "";
    ConnectServer conn;
    public static Socket socket;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
    private JPanel contentPane;
    public static JPanel pnDangNhap, pnDangKy, pnForm, pnNorth;
    public static JLabel lbDangNhap, lbDangKy, lbUser, lbPass, lbXacNhan, lbHoTen, lbGioiTinh, lbNgaySinh;
    public static JPasswordField txtPass, txtXacNhan;
    public static JTextField txtUser, txtHoTen;
    public static JXDatePicker txtNgaySinh;
    public static ButtonGroup GroupGioiTinh;
    public static JRadioButton rdNam, rdNu;
    public static JButton btnDangNhap, btnDangKy;
    public static Color BGChinh = new Color(45, 59, 85);
    public static Color BGPhu = new Color(232, 233, 236);
    public MouseAdapter MouseEV = new MouseAdapter() {
        public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

        }
        public void mousePressed(MouseEvent e) {

        }
        public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

        }
        public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

        }
        public void mouseClicked(MouseEvent e) {
            JPanel src =(JPanel) e.getSource();
            if(src.getName().equals("Đăng nhập")) {
                pnDangNhap.setBackground(BGChinh);
                lbDangNhap.setForeground(Color.WHITE);
                pnDangKy.setBackground(BGPhu);
                lbDangKy.setForeground(Color.GRAY);
                CapNhatNoiDung(src);
            }else {
        		pnDangKy.setBackground(BGChinh);
                lbDangKy.setForeground(Color.white);
                pnDangNhap.setBackground(BGPhu);
                lbDangNhap.setForeground(Color.GRAY);
                CapNhatNoiDung(src);
            }
        }
    };
 
    public void init(){
    	setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 150, 450, 300);
        setSize(700, 440);
        setLayout(new BorderLayout(1,2));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        pnDangNhap = new JPanel();
        pnDangNhap.setName("Đăng nhập");
        pnDangNhap.setBounds(0, 0, getWidth() / 2, 50);
        pnDangNhap.setBackground(BGChinh);
        lbDangNhap = new JLabel("Đăng nhập");
        lbDangNhap.setFont(new Font("Arial", Font.BOLD, 20));
        lbDangNhap.setForeground(Color.WHITE);
        lbDangNhap.setBounds(pnDangNhap.getWidth()/2, pnDangNhap.getHeight(), 100, 50);
        pnDangNhap.add(lbDangNhap);
        pnDangNhap.addMouseListener(MouseEV);       

        pnDangKy = new JPanel();
        pnDangKy.setName("Đăng ký");
        pnDangKy.setBounds(pnDangNhap.getWidth(), 0, getWidth() / 2, 50);
        pnDangKy.setBackground(BGPhu);
        lbDangKy = new JLabel("Đăng ký");
        lbDangKy.setFont(new Font("Arial", Font.BOLD, 20));
        lbDangKy.setForeground(Color.GRAY);
        lbDangKy.setBounds(pnDangKy.getWidth()/2, pnDangKy.getHeight(), 100, 50);
        pnDangKy.add(lbDangKy);
        pnDangKy.addMouseListener(MouseEV);
        
        pnNorth = new JPanel();
        pnNorth.setPreferredSize(new Dimension(700, 50));
        pnNorth.setLayout(null);
        pnNorth.add(pnDangNhap);
        pnNorth.add(pnDangKy);
        add(pnNorth, BorderLayout.NORTH);

        pnForm = new JPanel();
        pnForm.setLayout(null);
        pnForm.setBounds(0, 0, pnDangKy.getWidth() * 2, 350);
        pnForm.setBackground(Color.white);
        pnForm.add(JPanelDangNhap());
        add(pnForm, BorderLayout.CENTER);
    }
    public LoginGUI(int i) {
    	init();
    }
    public LoginGUI() {
        init();
        current_session = "readUser";
		try {
        	socket = new Socket(host, port);
        	out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        	conn = new ConnectServer(socket, current_session, out, in);
		}catch (StreamCorruptedException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (EOFException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (SocketException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
    	}catch (NullPointerException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex); 
        } catch (Exception ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    public JPanel JPanelDangNhap() {
        JPanel p = new JPanel();
        p.setName("Đăng nhập");
        p.setLayout(null);
        p.setBackground(Color.white);
        p.setBounds(0,pnDangNhap.getY() + pnDangNhap.getHeight(), pnDangKy.getWidth() * 2, 350);
        
        
        JLabel lbDangNhap = new JLabel("Đăng nhập");
        lbDangNhap.setBounds(p.getWidth()/2 - 50 , 0, 200, 50);
        lbDangNhap.setFont(new Font("Arial", Font.BOLD, 20));
        lbDangNhap.setForeground(Color.BLACK);
        
        lbUser = new JLabel("Email");
        lbUser.setBounds(p.getWidth()/2 - 170, lbDangNhap.getHeight() + 30, 100, 30);
        lbUser.setFont(new Font("Arial", 1, 16));
        lbUser.setForeground(Color.BLACK);
        txtUser = new JTextField();
        txtUser.setBounds(lbUser.getX() + lbUser.getWidth(), lbUser.getY(), 200, 30);
        
        lbPass = new JLabel("Mật khẩu");
        lbPass.setBounds(lbUser.getX(), lbUser.getY() + lbUser.getHeight() + 20, 100, 30);
        lbPass.setFont(new Font("Arial", 1, 16));
        lbPass.setForeground(Color.BLACK);
        txtPass = new JPasswordField();
        txtPass.setBounds(lbPass.getX() + lbPass.getWidth(), lbPass.getY(), 200, 30);
        
        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setBounds(p.getWidth() / 2 - 65, lbPass.getY() + lbPass.getHeight() + 50, 150, 35);
        btnDangNhap.setBackground(BGChinh);
        btnDangNhap.setForeground(Color.WHITE);
        
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	try {
					btnDangNhapActionPerformed(evt);
            	} catch (NullPointerException e) {
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
        
        p.add(lbDangNhap);
        p.add(lbUser);
        p.add(txtUser);
        p.add(lbPass);
        p.add(txtPass);
        p.add(btnDangNhap);
        
        return p;
    }
	
    public JPanel JPanelDangKy() {
        JPanel p = new JPanel();
        p.setName("Đăng ký");
        p.setLayout(null);
        p.setBackground(Color.white);
        p.setBounds(0,pnDangNhap.getY() + pnDangNhap.getHeight(), pnDangKy.getWidth() * 2, 350);
        
        
        JLabel lbDangKy = new JLabel("Đăng ký");
        lbDangKy.setBounds(p.getWidth()/2 - 40 , 0, 200, 50);
        lbDangKy.setFont(new Font("Arial", Font.BOLD, 20));
        lbDangKy.setForeground(Color.BLACK);
        
        lbUser = new JLabel("Email");
        lbUser.setBounds(20, lbDangNhap.getHeight() + 30, 100, 30);
        lbUser.setFont(new Font("Arial", 1, 16));
        lbUser.setForeground(Color.BLACK);
        txtUser = new JTextField();
        txtUser.setBounds(lbUser.getX() + lbUser.getWidth(), lbUser.getY(), 200, 30);
        
        lbPass = new JLabel("Mật khẩu");
        lbPass.setBounds(lbUser.getX(), lbUser.getY() + lbUser.getHeight() + 20, 100, 30);
        lbPass.setFont(new Font("Arial", 1, 16));
        lbPass.setForeground(Color.BLACK);
        txtPass = new JPasswordField();
        txtPass.setBounds(lbPass.getX() + lbPass.getWidth(), lbPass.getY(), 200, 30);
        
        lbXacNhan = new JLabel("Nhập lại");
        lbXacNhan.setBounds(lbPass.getX(), lbPass.getY() + lbPass.getHeight() + 20, 100, 30);
        lbXacNhan.setFont(new Font("Arial", 1, 16));
        lbXacNhan.setForeground(Color.BLACK);
        txtXacNhan = new JPasswordField();
        txtXacNhan.setBounds(lbXacNhan.getX() + lbXacNhan.getWidth(), lbXacNhan.getY(), 200, 30);
        
        
        lbHoTen = new JLabel("Họ tên");
        lbHoTen.setBounds(txtUser.getX() + txtUser.getWidth() + 40, lbUser.getY(), 100, 30);
        lbHoTen.setFont(new Font("Arial", 1, 16));
        lbHoTen.setForeground(Color.BLACK);
        txtHoTen = new JTextField();
        txtHoTen.setBounds(lbHoTen.getX() + lbHoTen.getWidth(), lbHoTen.getY(), 200, 30);
        
        lbGioiTinh = new JLabel("Giới tính");
        lbGioiTinh.setBounds(lbHoTen.getX(), lbHoTen.getY() + lbHoTen.getHeight() + 20, 100, 30);
        lbGioiTinh.setFont(new Font("Arial", 1, 16));
        lbGioiTinh.setForeground(Color.BLACK);
        GroupGioiTinh = new ButtonGroup();
        rdNam = new JRadioButton();
        rdNam.setText("Nam");
        rdNam.setBackground(Color.white);
        rdNam.setBounds(lbGioiTinh.getX() + lbGioiTinh.getWidth(), lbGioiTinh.getY(), 100, 30);
        rdNu = new JRadioButton();
        rdNu.setText("Nữ");
        rdNu.setBackground(Color.white);
        rdNu.setBounds(rdNam.getX() + rdNam.getWidth(), lbGioiTinh.getY(), 100, 30);
        GroupGioiTinh.add(rdNam);
        GroupGioiTinh.add(rdNu);
        
        lbNgaySinh = new JLabel("Ngày sinh");
        lbNgaySinh.setBounds(lbGioiTinh.getX(), lbGioiTinh.getY() + lbGioiTinh.getHeight() + 20, 100, 30);
        lbNgaySinh.setFont(new Font("Arial", 1, 16));
        lbXacNhan.setForeground(Color.BLACK);
        txtNgaySinh = new JXDatePicker();
        txtNgaySinh.setBounds(lbNgaySinh.getX() + lbNgaySinh.getWidth(), lbNgaySinh.getY(), 200, 30);
        txtNgaySinh.setDate(Calendar.getInstance().getTime());
        txtNgaySinh.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        
        btnDangKy = new JButton("Đăng ký");
        btnDangKy.setBounds(p.getWidth() / 2 - 65, lbXacNhan.getY() + lbXacNhan.getHeight() + 45, 150, 35);
        btnDangKy.setBackground(BGChinh);
        btnDangKy.setForeground(Color.WHITE);
        
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnDangKyActionPerformed(evt);
            }
        });
        
        p.add(lbDangKy);
        p.add(lbUser);
        p.add(txtUser);
        p.add(lbPass);
        p.add(txtPass);
        p.add(lbXacNhan);
        p.add(txtXacNhan);
        p.add(lbHoTen);
        p.add(txtHoTen);
        p.add(lbGioiTinh);
        p.add(rdNam);  p.add(rdNu);
        p.add(lbNgaySinh);
        p.add(txtNgaySinh);
        p.add(btnDangKy);
        
        return p;
    }
	
    public void CapNhatNoiDung(JPanel p) {
        pnForm.removeAll();
        pnForm.validate();
        pnForm.repaint();
        pnForm.setLayout(null);
        if(p.getName().equals("Đăng nhập")) {
            pnForm.add(JPanelDangNhap());
        }
        else {
            pnForm.add(JPanelDangKy());
        }
        pnForm.validate();
        pnForm.repaint();
    }
    
    private static boolean check_input(String userName, String pass){
        if (userName.equals("")){
            error_mess = "UserName trống!!!";
            return false;
        }
        else if (pass.equals("")){
            error_mess = "Password trống!!!";
            return false;
        }       
        return true;
    }
    
    public Boolean checkLogin() {
    	String userName = txtUser.getText();
    	String pass = txtPass.getText();
    	String passHashed = new ExecuteED().hashMD5(pass);
    	String user = new UserBUS().checkTK(userName, passHashed);
    	if(check_input(userName, pass) == true) {
    		if(!user.equals("")) {
    			new MainGUI(user);
    			return true;
    		}else {
                JOptionPane.showMessageDialog(null, "UserName hoặc Password không chính xác!!");
        	} 
    	}else{
    		JOptionPane.showMessageDialog(null, error_mess);
        }   
    	return false;
    }
    
    public void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
    	
		if(checkLogin() == true) {
			try {
	        	conn = new ConnectServer(socket, "readDeThi", out, in);  				
	        	JOptionPane.showMessageDialog(null, "Đăng nhập thành công!!");					
				this.dispose(); 
				Thread.sleep(1000);
				new MainGUI(socket , out, in).setVisible(true);
			}catch (StreamCorruptedException ex) {
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (EOFException ex) {
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (SocketException ex) {
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        	}catch (NullPointerException ex) {
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex); 
            } catch (Exception ex) {
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
		}    		
    }
    
    public boolean checkInputDangKy(String userName, String password, String hoTen, String re_password) {
		if (userName.equals("")){
            error_mess = "User Name trống!!!";
            return false;
        }
		else if (!userName.endsWith("@gmail.com")){
            error_mess = "User Name phải là email!!!";
            return false;
        } 
		else if (password.equals("")){
            error_mess = "Password trống!!!";
            return false;
        } 
        else if (re_password.equals("")){
            error_mess = "Bạn chưa nhập lại password!!!";
            return false;
        }else if(!password.equals(re_password)) {
        	error_mess = "Password nhập lại không trùng khớp!!!";
            return false;
        }
        else if (hoTen.equals("")){
            error_mess = "Họ Tên trống!!!";
            return false;
        } 
        
        return true;
	}
    
    public void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {
    	String userName = txtUser.getText();
    	String password = txtPass.getText();
    	String re_password = txtXacNhan.getText();
    	String hoTen = txtHoTen.getText();
    	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    	String ngSinh = String.valueOf(formater.format(txtNgaySinh.getDate()));
    	int gioiTinh;
    	if (!rdNam.isSelected()){
            gioiTinh = 1;
        }else {
        	if (!rdNu.isSelected()){
        		gioiTinh = 0;
        	}else {
        		JOptionPane.showMessageDialog(null, "Bạn chưa chọn giới tính!!");
        	}
        }
    	if(checkInputDangKy(userName, password, hoTen, re_password)) {
    		
    	}else {
    		JOptionPane.showMessageDialog(null, error_mess);
    	}
    }
}
