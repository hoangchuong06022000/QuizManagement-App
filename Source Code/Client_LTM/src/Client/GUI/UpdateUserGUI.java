package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import Client.BUS.ConnectServer;
import Client.BUS.UserBUS;
import models.ExecuteED;
import models.UserDTO;

public class UpdateUserGUI extends JFrame 
{
    public static String error_mess = "";
	public JPanel pLeft, pRight;
	public JPasswordField txtMatKhauCu, txtMatKhauMoi, txtXacNhan;
	public JTextField txtUser, txtHoten;
	public JXDatePicker txtNgaySinh;
	public ButtonGroup groupGioiTinh;
	public JRadioButton rdNam, rdNu;
	public Color BGChinh = new Color(45, 59, 85);
	public Color BGPhu = new Color(232, 233, 236);
	public Color BGCam = new Color(255, 165, 0);
	
	public MouseAdapter MouseButton = new MouseAdapter() 
    {
        public void mouseEntered(MouseEvent me) 
        {
            JPanel src =(JPanel) me.getSource();
            src.setBackground(BGCam);
        }

        @Override
        public void mouseExited(MouseEvent me) 
        {
        	JPanel src =(JPanel) me.getSource();
            src.setBackground(BGChinh);
        }

        @Override
        public void mouseClicked(MouseEvent me)
        {
           JPanel src =(JPanel) me.getSource();
           if(src.getName().equals("Cập nhật"))
           {
        	   UserDTO user = new UserDTO();
        	   user.setUserName(txtUser.getText());
        	   user.setHoTen(txtHoten.getText());
        	   user.setNgSinh(GetNgay(txtNgaySinh));
        	   user.setPassword(LoginGUI.current_user.getPassword());
        	   user.setTrangThai(LoginGUI.current_user.isTrangThai());
        	   if(rdNam.isSelected())
        		   user.setGioiTinh(0);
        	   else
        		   user.setGioiTinh(1);
        	   if(checkInputCapNhatTT(txtUser.getText(), GetNgay(txtNgaySinh)) == false) {
        		   JOptionPane.showMessageDialog(null, error_mess);
        		   return;
        	   }
        	   try {
        		   ConnectServer conn = new ConnectServer(MainGUI.socket, MainGUI.out, MainGUI.in);
        		   conn.addOrModUser(user, "modUser");
        		   LoginGUI.current_user = user;
        		   JOptionPane.showMessageDialog(null, "Cập nhật thành công!!!");
			   } catch (Exception e) {
					System.out.println("Lỗi");
			   }
           }
           else
           {
        	   UserDTO user = new UserDTO();
        	   user.setUserName(LoginGUI.current_user.getUserName());
        	   user.setHoTen(LoginGUI.current_user.getHoTen());
        	   user.setNgSinh(LoginGUI.current_user.getNgSinh());
        	   user.setGioiTinh(LoginGUI.current_user.getGioiTinh());
        	   user.setTrangThai(LoginGUI.current_user.isTrangThai());
        	   String passOld = txtMatKhauCu.getText();
        	   String passOldHashed = new ExecuteED().hashMD5(passOld);
        	   String passNew = txtMatKhauMoi.getText();
        	   String passConfirm = txtXacNhan.getText();
        	   if(passOld.equals("") || passNew.equals("") || passConfirm.equals(""))
        	   {
        		   JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
        		   return;
        	   }
        	   if(passOldHashed.equals(LoginGUI.current_user.getPassword()) == false)
        	   {
        		   JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mật khẩu hiện tại");
        		   return;
        	   }
        	   if(passConfirm.equals(passNew) == false)
        	   {
        		   JOptionPane.showMessageDialog(null, "Xác nhận mật khẩu chưa chính xác !!!");
        		   return;
        	   }
        	   if(checkInputCapNhatMK(passOld, passNew, passConfirm) == false) {
        		   JOptionPane.showMessageDialog(null, error_mess);
        		   return;
        	   }
        	   String passHashed = new ExecuteED().hashMD5(passNew);
        	   user.setPassword(passHashed);
        	   
        	   try {
        		   ConnectServer conn = new ConnectServer(MainGUI.socket, MainGUI.out, MainGUI.in);
        		   conn.addOrModUser(user, "modUser");
        		   LoginGUI.current_user.setPassword(passHashed);
        		   LoginGUI.current_user = user;
        		   JOptionPane.showMessageDialog(null, "Cập nhật thành công !!!");
			   } catch (Exception e) {
					System.out.println("Lỗi");
			   }
           }
        }
    };
	
    public boolean checkInputCapNhatTT(String hoTen, String ngSinh) {
		if (hoTen.contains("'") || hoTen.contains("\"") || hoTen.contains("#") 
				|| hoTen.contains("%") || hoTen.contains("`") || hoTen.contains("$")){
            error_mess = "Họ Tên không hợp lệ!!!";
            return false;
        } 
		else if (ngSinh.contains("'") || ngSinh.contains("\"") || ngSinh.contains("#") 
				|| ngSinh.contains("%") || ngSinh.contains("`") || ngSinh.contains("$")){
            error_mess = "Ngày sinh không hợp lệ!!!";
            return false;
        } 

        return true;
	}
    
    public boolean checkInputCapNhatMK(String oldPassword, String newPassword, String re_newPassword) {
		if (oldPassword.contains("'") || oldPassword.contains("\"") || oldPassword.contains("#") 
				|| oldPassword.contains("%") || oldPassword.contains("`") || oldPassword.contains("$")){
            error_mess = "Password hiện tại không hợp lệ!!!";
            return false;
        } 
		else if (newPassword.contains("'") || newPassword.contains("\"") || newPassword.contains("#") 
				|| newPassword.contains("%") || newPassword.contains("`") || newPassword.contains("$")){
            error_mess = "Password mới không hợp lệ!!!";
            return false;
        } 
        else if (re_newPassword.contains("'") || re_newPassword.contains("\"") || re_newPassword.contains("#") 
				|| re_newPassword.contains("%") || re_newPassword.contains("`") || re_newPassword.contains("$")){
            error_mess = "Re-Password không hợp lệ!!!";
            return false;
        } 
       
        return true;
	}
    
	public UpdateUserGUI() 
	{
		init();
	}
	
	public void init()
    { 
        setSize(650, 500);
        
        JPanel p = JPanelUpdateUser();
        add(p);
    }
	
	public JPanel JPanelUpdateUser()
	{
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(2,1));
		
		pLeft = new JPanel();
		pLeft.setLayout(null);
	    pLeft.setBackground(Color.white);
	    pLeft.setPreferredSize(new Dimension(330, 0));
	    
	    JPanel pn = new JPanel();
	    pn.setLayout(null);
	    pn.setBounds(0, 0, 300, 500);
	    pn.setBackground(Color.white);
	    
	    JLabel lbUsername = new JLabel("Username : ");
	    lbUsername.setBounds(30, 60, 100, 30);
	    lbUsername.setFont(new Font("Arial", 0, 14));
	    pn.add(lbUsername);
	    txtUser = new JTextField(15);
	    txtUser.setBounds(lbUsername.getX() + lbUsername.getWidth(), lbUsername.getY(), 150, 30);
	    txtUser.setEditable(false);
	    pn.add(txtUser);
	    
	    JLabel lbHoten = new JLabel("Họ tên : ");
	    lbHoten.setBounds(lbUsername.getX(), lbUsername.getY() + lbUsername.getHeight() + 15, 100, 30);
	    lbHoten.setFont(new Font("Arial", 0, 14));
	    pn.add(lbHoten);
	    txtHoten = new JTextField(15);
	    txtHoten.setBounds(lbHoten.getX() + lbHoten.getWidth(), lbHoten.getY(), 150, 30);
	    pn.add(txtHoten);
	    
	    JLabel lbNgaysinh = new JLabel("Ngày sinh : ");
	    lbNgaysinh.setBounds(lbHoten.getX(), lbHoten.getY() + lbHoten.getHeight() + 15, 100, 30);
	    lbNgaysinh.setFont(new Font("Arial", 0, 14));
	    pn.add(lbNgaysinh);
	    txtNgaySinh = new JXDatePicker();
	    txtNgaySinh.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
	    txtNgaySinh.setBounds(lbNgaysinh.getX() + lbNgaysinh.getWidth(), lbNgaysinh.getY(), 150, 30);
	    pn.add(txtNgaySinh);
	    
	    JLabel lbGioiTinh = new JLabel("Giới tính : ");
	    lbGioiTinh.setBounds(lbNgaysinh.getX(), lbNgaysinh.getY() + lbNgaysinh.getHeight() + 15, 100, 30);
	    lbGioiTinh.setFont(new Font("Arial", 0, 14));
	    pn.add(lbGioiTinh);
	    groupGioiTinh = new ButtonGroup();
	    rdNam = new JRadioButton("Nam");
	    rdNam.setBackground(Color.WHITE);
	    rdNam.setFont(new Font("Arial", 0, 14));
	    groupGioiTinh.add(rdNam);
	    rdNam.setBounds(lbGioiTinh.getX() + lbGioiTinh.getWidth(), lbGioiTinh.getY(), 80, 30);
	    pn.add(rdNam);
	    rdNu = new JRadioButton("Nữ");
	    rdNu.setBackground(Color.WHITE);
	    rdNu.setFont(new Font("Arial", 0, 14));
	    groupGioiTinh.add(rdNu);
	    rdNu.setBounds(rdNam.getX() + rdNam.getWidth(), rdNam.getY(), 80, 30);
	    pn.add(rdNu);
	    
	    JPanel btnCapNhat = new JPanel();
	    btnCapNhat.setBackground(BGChinh);
	    btnCapNhat.setName("Cập nhật");
	    btnCapNhat.setBounds((pn.getWidth() / 2) - 30, rdNam.getY() + rdNam.getHeight() + 50, 80, 25);
	    pn.add(btnCapNhat);
	    JLabel lbCapNhat = new JLabel("Cập nhật");
	    lbCapNhat.setFont(new Font("Arial", 1, 12));
	    lbCapNhat.setForeground(Color.white);
	    btnCapNhat.add(lbCapNhat);
	    btnCapNhat.setName("Cập nhật");
	    btnCapNhat.addMouseListener(MouseButton);
	    pLeft.add(pn);
	       
	    pRight = new JPanel();
	    pRight.setLayout(null);
	    pRight.setPreferredSize(new Dimension(0, 0));
	    JPanel pn2 = new JPanel();
	    pn2.setLayout(null);
	    pn2.setBounds(0, 0, 300, 500);
	    pn2.setBackground(Color.white);
	    pRight.add(pn2);
	    
	    JLabel lbMatKhauCu = new JLabel("Mật khẩu cũ : ");
	    lbMatKhauCu.setBounds(30, 60, 100, 30);
	    lbMatKhauCu.setFont(new Font("Arial", 0, 14));
	    pn2.add(lbMatKhauCu);
	    txtMatKhauCu = new JPasswordField(15);
	    txtMatKhauCu.setBounds(lbMatKhauCu.getX() + lbMatKhauCu.getWidth(), lbMatKhauCu.getY(), 150, 30);
	    pn2.add(txtMatKhauCu);
	    
	    JLabel lbMatKhauMoi = new JLabel("Mật khẩu mới : ");
	    lbMatKhauMoi.setBounds(30, 60, 100, 30);
	    lbMatKhauMoi.setFont(new Font("Arial", 0, 14));
	    lbMatKhauMoi.setBounds(lbMatKhauCu.getX(), lbMatKhauCu.getY() + lbMatKhauCu.getHeight() + 20, 100, 30);
	    pn2.add(lbMatKhauMoi);
	    txtMatKhauMoi = new JPasswordField(15);
	    txtMatKhauMoi.setBounds(lbMatKhauMoi.getX() + lbMatKhauMoi.getWidth(), lbMatKhauMoi.getY(), 150, 30);
	    pn2.add(txtMatKhauMoi);
	    
	    JLabel lbXacNhan = new JLabel("Xác nhận : ");
	    lbXacNhan.setBounds(lbMatKhauMoi.getX(), lbMatKhauMoi.getY() + lbMatKhauMoi.getHeight() + 20, 100, 30);
	    lbXacNhan.setFont(new Font("Arial", 0, 14));
	    pn2.add(lbXacNhan);
	    txtXacNhan = new JPasswordField(15);
	    txtXacNhan.setBounds(lbXacNhan.getX() + lbXacNhan.getWidth(), lbXacNhan.getY(), 150, 30);
	    pn2.add(txtXacNhan);
	    
	    JPanel btnCapNhat2 = new JPanel();
	    btnCapNhat2.setBackground(BGChinh);
	    btnCapNhat2.setName("Cập nhật 2");
	    btnCapNhat2.setBounds((pn.getWidth() / 2) - 30, btnCapNhat.getY(), 80, 25);
	    pn2.add(btnCapNhat2);
	    JLabel lbCapNhat2 = new JLabel("Cập nhật");
	    lbCapNhat2.setFont(new Font("Arial", 1, 12));
	    lbCapNhat2.setForeground(Color.white);
	    btnCapNhat2.addMouseListener(MouseButton);
	    btnCapNhat2.add(lbCapNhat2);
	    pRight.add(pn2);
		
	    p.add(pLeft, BorderLayout.WEST);
        p.add(pRight, BorderLayout.CENTER);
        
		UserDTO user = LoginGUI.current_user;
		txtUser.setText(user.getUserName());
		txtHoten.setText(user.getHoTen());
		SetNgay(txtNgaySinh, user.getNgSinh());
		if(user.getGioiTinh() == 0)
			rdNam.setSelected(true);
		else
			rdNu.setSelected(true);
		
		return p;
	}
	
	public String GetNgay(JXDatePicker txt)
    {
        String date = "";
        if(txt.getDate() != null)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.format(txt.getDate());
        }
        return date;
    }
	
    public void SetNgay(JXDatePicker txt, String s)
    {
        if(s.equals("") == false)
        {
            try {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
				txt.setDate(date);
			} catch (Exception e) {
				System.out.println("Loi");
			}
        }
        else
        {
            txt.setDate(null);
        }
    }
}
