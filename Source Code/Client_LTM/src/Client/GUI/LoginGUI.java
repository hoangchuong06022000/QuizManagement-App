package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import Client.BUS.*;
import models.*;


public class LoginGUI extends JFrame {
	private static String host = "localhost";
    private static int port = 2000;
    public static String current_session = "";
    public static String error_mess = "";
    public static String otp;
    ConnectServer conn;
    public static Socket socket;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
    private JPanel contentPane;
    public static JPanel pnDangNhap, pnDangKy, pnForm, pnNorth;
    public static JLabel lbDangNhap, lbDangKy, lbUser, lbPass, lbXacNhan, lbHoTen, lbGioiTinh, lbNgaySinh, lbPhut, lbGiay;
    public static JPasswordField txtPass, txtXacNhan;
    public static JTextField txtUser, txtHoTen, txtOTP;
    public static JXDatePicker txtNgaySinh;
    public static ButtonGroup GroupGioiTinh;
    public static JRadioButton rdNam, rdNu;
    public static JButton btnDangNhap, btnDangKy, btnXacNhan;
    public static Color BGChinh = new Color(45, 59, 85);
    public static Color BGPhu = new Color(232, 233, 236);
    public int MM, ss;
	int interval;
	public Timer timer;
	public static JDialog frame;
	public static boolean checkOTP;
	
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
                try {
					conn = new ConnectServer(socket, "readUser", out, in);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
    public LoginGUI(String i) {
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
        rdNam.setSelected(true);
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
    	String passwordHashed = new ExecuteED().hashMD5(password);
    	String re_password = txtXacNhan.getText();
    	String hoTen = txtHoTen.getText();
    	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    	String ngSinh = String.valueOf(formater.format(txtNgaySinh.getDate()));
    	int gioiTinh = 0;
    	if (rdNam.isSelected()){
            gioiTinh = 1;
    	}
    	if (rdNu.isSelected()){
    		gioiTinh = 0;
    	}
    	if(checkInputDangKy(userName, password, hoTen, re_password)) {
    		JOptionPane.showMessageDialog(null, "Đang gửi mã OTP đến email của bạn!!\n Vui lòng chờ đến khi có thông báo!!");
    		otp = new SendOTP().OTP();
    		new SendOTP(userName, otp);
    		JOptionPane.showMessageDialog(null, "Vui lòng nhập mã OTP gửi đến email của bạn!!");
    		LoginGUI parrent = new LoginGUI("");
    		frame = new JDialog(parrent, true);
            frame.setLayout(null);
    		frame.pack();
    		frame.setSize(300, 210);
    		frame.setLocationRelativeTo(null);
    		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    		frame.add(JPanelXacNhanOTP());
    		frame.setVisible(true);
    		if(checkOTP == true) {
    			UserDTO user = new UserDTO(userName, passwordHashed, hoTen, gioiTinh, ngSinh, 1);
    			boolean checkAddUser;
    			try {
					if(checkAddUser = new ConnectServer(socket, out, in).addOrModUser(user, "addUser") == true) {
						JOptionPane.showMessageDialog(null, "Đăng ký thành công!!");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
    			
    		}
    	}else {
    		JOptionPane.showMessageDialog(null, error_mess);
    	}
    }
    
    public void DemNguocThoiGian(int SoPhut)
	{
		 int delay = 1000;
		 int period = 1000;
		 timer = new Timer();
		 MM = SoPhut;
		 ss = 59;
		 timer = new Timer();
		 timer.scheduleAtFixedRate(new TimerTask() {
			 public void run() {	
				 setInterval();
				 
			 }
		 }, delay, period);
	}
	
	private final void setInterval() {
		if(MM < 10)
			 lbPhut.setText("0" + MM);
		 else
			 lbPhut.setText("" + MM);
		 if(ss < 10)
			 lbGiay.setText("0" + ss);
		 else
			 lbGiay.setText("" + ss);
		if (MM == 0 && ss <= 0) {
			timer.cancel();
			JOptionPane.showMessageDialog(null, "Mã OTP đã hết hạn!!");
		    frame.dispose();
		} 
		 if(ss <= 0)
		 {
		    MM--;
		    ss = 59;
		    return;
		 }
	    --ss;
	}
    
    public JPanel JPanelXacNhanOTP() {
    	JPanel p = new JPanel();
    	p.setBackground(Color.WHITE);
        p.setBounds(0, 0, 300, 210);
        p.setLayout(null);
        JLabel lbText = new JLabel();
        lbText.setText("Nhập mã OTP:");
        lbText.setBounds(p.getWidth()/2 - 60, 10, 120, 30);
        lbText.setForeground(BGChinh);
        lbText.setFont(new Font("Arial", Font.BOLD, 15));
		p.add(lbText);
       
		txtOTP = new JTextField();
		txtOTP.setBounds(lbText.getX()+12, lbText.getY() + lbText.getHeight() + 10, 80, 30);
		txtOTP.setFont(new Font("Arial", Font.BOLD, 15));
		p.add(txtOTP);
		
		int ThoiGian = 2;
		String s_ThoiGian = "";
		if(ThoiGian < 10)
			s_ThoiGian = "0" + ThoiGian;
		else
			s_ThoiGian = "" + ThoiGian;	
        
		lbPhut = new JLabel();
		lbPhut.setText(s_ThoiGian);
		lbPhut.setBounds(txtOTP.getX()+12, txtOTP.getY() + txtOTP.getHeight() + 10, 25, 30);
		lbPhut.setForeground(BGChinh);
		lbPhut.setFont(new Font("Arial", Font.BOLD, 15));
		p.add(lbPhut);
		
		JLabel lbtem = new JLabel();
		lbtem.setText(":");
		lbtem.setBounds(lbPhut.getX() + lbPhut.getWidth(), lbPhut.getY(), 10, 30);
		lbtem.setForeground(BGChinh);
		lbtem.setFont(new Font("Arial", Font.BOLD, 15));
		p.add(lbtem);
		
		lbGiay = new JLabel();
		lbGiay.setText("59");
		lbGiay.setBounds(lbtem.getX() + lbtem.getWidth(), lbtem.getY(), 30, 30);
		lbGiay.setForeground(BGChinh);
		lbGiay.setFont(new Font("Arial", Font.BOLD, 15));
		p.add(lbGiay);
		
		btnXacNhan = new JButton("Xác nhận");
		btnXacNhan.setBounds(lbText.getX()+2, lbGiay.getY() + lbGiay.getHeight() + 5, 100, 30);
        btnXacNhan.setBackground(BGChinh);
        btnXacNhan.setForeground(Color.WHITE);
        p.add(btnXacNhan);
        
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btnXacNhanActionPerformed(evt);
            }
        });
		
		DemNguocThoiGian(ThoiGian);
		
    	return p;
    }
    
    public void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {
    	String input = txtOTP.getText();
    	if(input.equals(otp)) {
    		checkOTP = true;
    		JOptionPane.showMessageDialog(null, "Xác nhận OTP thành công!!");
    		timer.cancel();
    		frame.dispose();
    	}else {
    		checkOTP = false;
    		JOptionPane.showMessageDialog(null, "Mã OTP không đúng!!");
    	}
    }
}
