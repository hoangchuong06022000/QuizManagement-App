package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import models.CauHoiDTO;
import models.DeThiDTO;
import models.ExecuteED;
import models.UserDTO;
import Client.BUS.*;

public class UpdateExamGUI extends JFrame
{
	public static DeThiDTO DeThi;
	public static String current_session = "";
	public static String userName = "";
	public static String maDeThi = "";
	public int count;
	public static Socket socket;
	public static ObjectInputStream in;
    public static ObjectOutputStream out;
    ConnectServer conn;
	public static String error_mess = "";
	public JPanel btn, btnDelete, btnContinue;
	public Color BGChinh = new Color(45, 59, 85);
	public Color BGPhu = new Color(232, 233, 236);
	public Color BGCam = new Color(255, 165, 0);
	public static JPanel pnChung;
	public JComboBox<String> comboSoCau, comboThoiGian;
	public JTextArea txtTenDeThi, txtCauHoi, txtA, txtB, txtC, txtD, txtDapAn;
	public JLabel lbSTT, lbSCH, lbTGT;
	public ButtonGroup groupDapAn;
	public JRadioButton rdA, rdB, rdC, rdD;
	private JDialog frame;
	
	public MouseAdapter MouseBtnContinue = new MouseAdapter() 
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
           	CreateCauHoi(src); 	
        }
    };
    public MouseAdapter MouseBtnDelete = new MouseAdapter() 
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
           	XoaDeThi(src); 	
        }
    };
	
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
        	int soCauHoi = Integer.parseInt(lbSCH.getText());
        	int thoiGianThi = Integer.parseInt(lbTGT.getText());
        	SaveCauHoi(src, soCauHoi, thoiGianThi);
        }
    };
	
    public UpdateExamGUI(String userName) {
    	UpdateExamGUI.userName = userName;
    }
    
	public UpdateExamGUI(Socket socket, ObjectOutputStream out, ObjectInputStream in)
    {
		UpdateExamGUI.socket = socket;
		UpdateExamGUI.out = out;
		UpdateExamGUI.in = in;
        init();
    }
	
	public void init()
    { 
        setSize(650, 500);
        JPanel p = new JPanel();
        p = JPanelChung();
        add(p);
        
        for(DeThiDTO De : DeThiBUS.arrDeThi)
        {
        	if(De.getMaDeThi().equals(maDeThi))
        	{
        		DeThi = De;
        	}
        	
        }
        
        txtTenDeThi.setText(DeThi.getTenDeThi());
        comboSoCau.setSelectedItem(DeThi.getSoCauHoi());
        comboThoiGian.setSelectedItem(DeThi.getThoiGianThi());
    }
	
	public JPanel JPanelChung() {
		pnChung = new JPanel();    
        pnChung.setLayout(null);
        pnChung.setBackground(Color.WHITE);
        pnChung.setBounds(0, 0, 650, 500);
        pnChung.add(JPanelCreateExam());
        return pnChung;
	}
	
	public void CreateCauHoi(JPanel p) {
   		int soCauHoi = Integer.parseInt((String) comboSoCau.getSelectedItem());
		int thoiGianThi = Integer.parseInt((String) comboThoiGian.getSelectedItem());
		if(p.getName().equals("Continue")) {
			String maDeThi = DeThi.getMaDeThi();
			CreateExamGUI.maDeThi = maDeThi;
			if(txtTenDeThi.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Tên đề thi trống!!");
			}else {
				if (txtTenDeThi.getText().contains("'") || txtTenDeThi.getText().contains("\"") || txtTenDeThi.getText().contains("#") 
					|| txtTenDeThi.getText().contains("%") || txtTenDeThi.getText().contains("`") || txtTenDeThi.getText().contains("$")){
					JOptionPane.showMessageDialog(null, "Tên đề thi không hợp lệ!!");
				}else {
					String tenDeThi = txtTenDeThi.getText();
					for(DeThiDTO DeThi : DeThiBUS.arrDeThi){
						if(DeThi.getTenDeThi().equals(tenDeThi)){
							JOptionPane.showMessageDialog(null, "Tên đề thi này đã tồn tại !!!");
							return;
						}
					}
					DeThiDTO deThi = new DeThiDTO(maDeThi, tenDeThi, soCauHoi, thoiGianThi, 0, CreateExamGUI.userName);
					try {
						boolean check;
						if(check = new ConnectServer(socket, out, in).addOrModDeThi(deThi, "addDeThi") == true) {
							JOptionPane.showMessageDialog(null, "Sửa Đề Thi thành công!!");
							CreateExamGUI parrentFrame = new CreateExamGUI(userName);
							frame = new JDialog(parrentFrame, true);
							frame.setUndecorated(true);
							count = 1;
							frame.getContentPane().add(JPanelCauHoi(soCauHoi, thoiGianThi, count));
							frame.pack();
							frame.setSize(650, 500);
							frame.setLocationRelativeTo(null);
							frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							frame.setVisible(true);
						}
					}catch (StreamCorruptedException ex) {
		                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
		        	}catch (EOFException ex) {
		                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
		        	}catch (SocketException ex) {
		                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
		        	}catch (NullPointerException ex) {
		                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex); 
		            } catch (Exception ex) {
		                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
		            }
				}
	        } 	
    	}	 		
	}
	public void XoaDeThi(JPanel p) {
		if(p.getName().equals("Delete")) {
			int select = JOptionPane.showConfirmDialog(null, "Thao tác này sẽ xoá đề thi bạn vừa tạo!!", "Lựa chọn của bạn", JOptionPane.YES_NO_OPTION);
    		if (select == JOptionPane.YES_OPTION) {
    			boolean checkDelCauHoi;
    			boolean checkDelDeThi;
				try {
					new ConnectServer(socket, out, in).readCauHoiByMaDeThi(CreateExamGUI.maDeThi, "readCauHoi");
					System.out.println(CauHoiBUS.arrCauHoi);
					Thread.sleep(1000);
					ArrayList<Integer> stt = new CauHoiBUS().getSTTByMaDeThi(CreateExamGUI.maDeThi);
					System.out.println(stt);
					for(int i = 0; i < stt.size(); i++) {
						checkDelCauHoi = new ConnectServer(socket, out, in).delCauHoi(stt.get(i), CreateExamGUI.maDeThi, "delCauHoi");
					}
					if(checkDelDeThi = new ConnectServer(socket, out, in).delDeThi(CreateExamGUI.maDeThi, "delDeThi") == true) {
						frame.dispose();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
		}
	}
	
	public void SaveCauHoi(JPanel p, int soCauHoi, int thoiGianThi) {
		String tenCauHoi = txtCauHoi.getText();
		
		String cauA = txtA.getText();
		String cauB = txtB.getText();
		String cauC = txtC.getText();
		String cauD = txtD.getText();
		String dapAn = txtDapAn.getText();
		
		
		if(p.getName().equals("OK")) {
			if(count == soCauHoi) {
				JOptionPane.showMessageDialog(null, "Sửa Câu Hỏi cho đề thi thành công!!");
				frame.dispose();
			}
			if(checkDapAn(tenCauHoi, cauA, cauB, cauC, cauD, dapAn)) {	
				if(cauA.equals(cauB) || cauA.equals(cauC) || cauA.equals(cauD) || cauB.equals(cauC) || cauB.equals(cauD) || cauC.equals(cauD) )
				{
					JOptionPane.showMessageDialog(null, "Đáp án của bạn không được trùng !!!");
					return;
				}
				try {
					CauHoiDTO cauHoi = new CauHoiDTO(count, CreateExamGUI.maDeThi, tenCauHoi, cauA, cauB, cauC, cauD, dapAn);
					System.out.println(cauHoi.getMaDeThi());
					boolean check;
					if(check = new ConnectServer(socket, out, in).addOrModCauHoi(cauHoi, "modCauHoi") == true) {
						if(count < soCauHoi) {
							JOptionPane.showMessageDialog(null, "Sửa thành công!!");
							CauHoiBUS.arrCauHoi.add(cauHoi);
							count ++;
							frame.dispose();
							CreateExamGUI parrentFrame = new CreateExamGUI(userName);
							frame = new JDialog(parrentFrame, true);
							frame.setUndecorated(true);
							frame.getContentPane().add(JPanelCauHoi(soCauHoi, thoiGianThi, count));
							frame.pack();
							frame.setSize(650, 500);
							frame.setLocationRelativeTo(null);
							frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							frame.setVisible(true); 	
						}		
					}
				}catch (StreamCorruptedException ex) {
	                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (EOFException ex) {
	                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (SocketException ex) {
	                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
	        	}catch (NullPointerException ex) {
	                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex); 
	            } catch (Exception ex) {
	                Logger.getLogger(CreateExamGUI.class.getName()).log(Level.SEVERE, null, ex);
	            }
			}
		}
	}
	public boolean checkInput(String tenCauHoi, String cauA, String cauB, String cauC, String cauD, String dapAn) {
		if (tenCauHoi.equals("")){
            error_mess = "Tên Câu Hỏi trống!!!";
            return false;
        }
		else if (tenCauHoi.contains("'") || tenCauHoi.contains("\"") || tenCauHoi.contains("#") 
				|| tenCauHoi.contains("%") || tenCauHoi.contains("`") || tenCauHoi.contains("$")){
            error_mess = "Tên Câu Hỏi không hợp lệ!!!";
            return false;
        } 
		else if (cauA.equals("")){
            error_mess = "Câu A trống!!!";
            return false;
        }
		else if (cauA.contains("'") || cauA.contains("\"") || cauA.contains("#") 
				|| cauA.contains("%") || cauA.contains("`") || cauA.contains("$")){
            error_mess = "Câu A không hợp lệ!!!";
            return false;
        } 
        else if (cauB.equals("")){
            error_mess = "Câu B trống!!!";
            return false;
        }  
        else if (cauB.contains("'") || cauB.contains("\"") || cauB.contains("#") 
				|| cauB.contains("%") || cauB.contains("`") || cauB.contains("$")){
            error_mess = "Câu B không hợp lệ!!!";
            return false;
        } 
        else if (cauC.equals("")){
            error_mess = "Câu C trống!!!";
            return false;
        } 
        else if (cauC.contains("'") || cauC.contains("\"") || cauC.contains("#") 
				|| cauC.contains("%") || cauC.contains("`") || cauC.contains("$")){
            error_mess = "Câu C không hợp lệ!!!";
            return false;
        } 
        else if (cauD.equals("")){
            error_mess = "Câu D trống!!!";
            return false;
        } 
        else if (cauD.contains("'") || cauD.contains("\"") || cauD.contains("#") 
				|| cauD.contains("%") || cauD.contains("`") || cauD.contains("$")){
            error_mess = "Câu D không hợp lệ!!!";
            return false;
        } 
        else if (dapAn.equals("")){
            error_mess = "Đáp Án trống!!!";
            return false;
        } 
        else if (dapAn.contains("'") || dapAn.contains("\"") || dapAn.contains("#") 
				|| dapAn.contains("%") || dapAn.contains("`") || dapAn.contains("$")){
            error_mess = "Đáp Án không hợp lệ!!!";
            return false;
        } 
        return true;
	}
	public boolean checkDapAn(String tenCauHoi,String cauA, String cauB, String cauC, String cauD, String dapAn) {		
		if(checkInput(tenCauHoi,cauA, cauB, cauC, cauD, dapAn)) {
			System.out.println(checkInput(tenCauHoi,cauA, cauB, cauC, cauD, dapAn));
			if(dapAn.equals(cauA)) {
				return true;
			}else {
				if(dapAn.equals(cauB)) {
					return true;
				}else {
					if(dapAn.equals(cauC)) {
						return true;
					}else {
						if(dapAn.equals(cauD)) {
							return true;
						}else {
							JOptionPane.showMessageDialog(null, "Đáp án phải giống với 1 trong 4 câu trả lời!!");
						}
					}
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, error_mess);
		}
		return false;
	}
	
	public JPanel JPanelCreateExam() {
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setBackground(Color.WHITE);
		p.setBounds(0, 0, 650, 500);
		
		JLabel lbSoCau = new JLabel();
		lbSoCau.setText("Số câu hỏi : ");
		lbSoCau.setBounds(20, 10, 100, 30);
		lbSoCau.setFont(new Font("Arial", Font.BOLD, 14));
		p.add(lbSoCau);
		
		String[] SoCau = {"10", "15", "20", "30", "40"};
		comboSoCau = new JComboBox<>(SoCau);
		comboSoCau.setEnabled(false);
		comboSoCau.setBounds(lbSoCau.getX() + lbSoCau.getWidth(), lbSoCau.getY(), 100, 30);
		p.add(comboSoCau);
		
		JLabel lbThoiGian = new JLabel();
		lbThoiGian.setText("Thời gian thi (phút) : ");
		lbThoiGian.setBounds(comboSoCau.getX() + comboSoCau.getWidth() + 60, comboSoCau.getY(), 150, 30);
		lbThoiGian.setFont(new Font("Arial", Font.BOLD, 14));
		p.add(lbThoiGian);
		
		String[] ThoiGian = {"10", "20", "30", "60", "90"};
		comboThoiGian = new JComboBox<>(ThoiGian);
		comboThoiGian.setBounds(lbThoiGian.getX() + lbThoiGian.getWidth(), lbThoiGian.getY(), 100, 30);
		p.add(comboThoiGian);
		
		JLabel lbTenDeThi = new JLabel();
		lbTenDeThi.setText("Tên đề thi : ");
		lbTenDeThi.setBounds(lbSoCau.getX(), comboSoCau.getWidth() + 60, 100, 30);
		lbTenDeThi.setFont(new Font("Arial", Font.BOLD, 14));
		p.add(lbTenDeThi);
		
		txtTenDeThi = new JTextArea();
		txtTenDeThi.setBounds(lbTenDeThi.getX() + lbTenDeThi.getWidth() + 10, lbTenDeThi.getY(), 300, 80);
		txtTenDeThi.setFont(new Font("Arial", 0, 16));
		txtTenDeThi.setBorder(BorderFactory.createLineBorder(Color.black));
		txtTenDeThi.setLineWrap(true);
		txtTenDeThi.setWrapStyleWord(true);
		p.add(txtTenDeThi);
		
		btnContinue = new JPanel();
		btnContinue.setName("Continue");
		btnContinue.setBackground(BGChinh);
		btnContinue.setBounds((p.getWidth() / 2) - 40, txtTenDeThi.getWidth() + 50, 80, 30);
		btnContinue.addMouseListener(MouseBtnContinue);
		JLabel lb = new JLabel("Continue");
		lb.setFont(new Font("Arial", 1, 16));
		lb.setForeground(Color.white);
		btnContinue.add(lb);
		p.add(btnContinue);
		
		return p;
	}
	
	public JPanel JPanelCauHoi(int soCauHoi, int thoiGianThi, int stt) {
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setBackground(Color.WHITE);
		p.setBounds(0, 0, 650, 500);
		
		JLabel lbSoCau = new JLabel();
		lbSoCau.setText("Số câu hỏi : ");
		lbSoCau.setBounds(20, 10, 150, 30);
		lbSoCau.setFont(new Font("Arial", Font.BOLD, 14));
		p.add(lbSoCau);
		
		lbSCH = new JLabel();
		lbSCH.setText(String.valueOf(soCauHoi));
		lbSCH.setBounds(200, 10, 30, 30);
		lbSCH.setFont(new Font("Arial", Font.BOLD, 14));
		p.add(lbSCH);
		
		JLabel lbThoiGian = new JLabel();
		lbThoiGian.setText("Thời gian thi (phút) : ");
		lbThoiGian.setBounds(comboSoCau.getX() + comboSoCau.getWidth() + 60, comboSoCau.getY(), 200, 30);
		lbThoiGian.setFont(new Font("Arial", Font.BOLD, 14));
		p.add(lbThoiGian);
		
		lbTGT = new JLabel();
		lbTGT.setText(String.valueOf(soCauHoi));
		lbTGT.setBounds(comboSoCau.getX() + comboSoCau.getWidth() + 300, comboSoCau.getY(), 200, 30);
		lbTGT.setFont(new Font("Arial", Font.BOLD, 14));
		p.add(lbTGT);
		
		JLabel lbCauHoi = new JLabel();
		lbCauHoi.setText("Câu hỏi số ");
		lbCauHoi.setBounds(lbSoCau.getX(), comboThoiGian.getY() + comboThoiGian.getHeight() + 20, 90, 30);
		lbCauHoi.setFont(new Font("Arial", Font.BOLD, 14));
		p.add(lbCauHoi);
		
		lbSTT = new JLabel();
		lbSTT.setBounds(lbCauHoi.getX() + lbCauHoi.getWidth(), lbCauHoi.getY(), 50, 30);
		lbSTT.setText(String.valueOf(stt));
		p.add(lbSTT);
		
		txtCauHoi= new JTextArea();
		txtCauHoi.setBounds(lbCauHoi.getX(), lbSTT.getY() + lbSTT.getHeight() + 20, 590, 80);
		txtCauHoi.setFont(new Font("Arial", 0, 16));
		txtCauHoi.setBorder(BorderFactory.createLineBorder(Color.black));
		txtTenDeThi.setLineWrap(true);
		txtTenDeThi.setWrapStyleWord(true);
		p.add(txtCauHoi);
		
		groupDapAn = new ButtonGroup();
		rdA = new JRadioButton("A/ ");
		rdA.setBounds(txtCauHoi.getX(), txtCauHoi.getY() + txtCauHoi.getHeight() + 20, 50, 30);
		rdA.setFont(new Font("Arial", 0, 16));
		rdA.setBackground(Color.WHITE);
		groupDapAn.add(rdA);
		p.add(rdA);
		
		txtA= new JTextArea();
		txtA.setBounds(rdA.getX() + rdA.getWidth(), rdA.getY(), 230, 40);
		txtA.setFont(new Font("Arial", 0, 16));
		txtA.setBorder(BorderFactory.createLineBorder(Color.black));
		txtA.setLineWrap(true);
		txtA.setWrapStyleWord(true);
		p.add(txtA);
		
		rdB = new JRadioButton("B/ ");
		rdB.setBounds(txtA.getX() + txtA.getWidth() + 28, txtA.getY(), 50, 30);
		rdB.setFont(new Font("Arial", 0, 16));
		rdB.setBackground(Color.WHITE);
		groupDapAn.add(rdB);
		p.add(rdB);
		
		txtB= new JTextArea();
		txtB.setBounds(rdB.getX() + rdB.getWidth(), rdB.getY(), 230, 40);
		txtB.setFont(new Font("Arial", 0, 16));
		txtB.setBorder(BorderFactory.createLineBorder(Color.black));
		txtB.setLineWrap(true);
		txtB.setWrapStyleWord(true);
		p.add(txtB);
		
		rdC = new JRadioButton("C/ ");
		rdC.setBounds(rdA.getX(), txtA.getY() + txtA.getHeight() + 20, 50, 30);
		rdC.setFont(new Font("Arial", 0, 16));
		rdC.setBackground(Color.WHITE);
		groupDapAn.add(rdC);
		p.add(rdC);
		
		txtC= new JTextArea();
		txtC.setBounds(rdC.getX() + rdC.getWidth(), rdC.getY(), 230, 40);
		txtC.setFont(new Font("Arial", 0, 16));
		txtC.setBorder(BorderFactory.createLineBorder(Color.black));
		txtC.setLineWrap(true);
		txtC.setWrapStyleWord(true);
		p.add(txtC);
		
		rdD = new JRadioButton("D/ ");
		rdD.setBounds(txtC.getX() + txtC.getWidth() + 28, txtC.getY(), 50, 30);
		rdD.setFont(new Font("Arial", 0, 16));
		rdD.setBackground(Color.WHITE);
		groupDapAn.add(rdD);
		p.add(rdD);
		
		txtD= new JTextArea();
		txtD.setBounds(rdD.getX() + rdD.getWidth(), rdD.getY(), 230, 40);
		txtD.setFont(new Font("Arial", 0, 16));
		txtD.setBorder(BorderFactory.createLineBorder(Color.black));
		txtD.setLineWrap(true);
		txtD.setWrapStyleWord(true);
		p.add(txtD);
		
		JLabel lbDapAn = new JLabel("Đáp Án: ");
		lbDapAn.setBounds(rdC.getX(), txtC.getY() + txtC.getHeight() + 20, 70, 30);
		lbDapAn.setFont(new Font("Arial", 0, 16));
		p.add(lbDapAn);
		
		txtDapAn= new JTextArea();
		txtDapAn.setBounds(lbDapAn.getX() + lbDapAn.getWidth(), lbDapAn.getY(), 230, 40);
		txtDapAn.setFont(new Font("Arial", 0, 16));
		txtDapAn.setBorder(BorderFactory.createLineBorder(Color.black));
		txtDapAn.setLineWrap(true);
		txtDapAn.setWrapStyleWord(true);
		p.add(txtDapAn);
		
		btnDelete = new JPanel();
		btnDelete.setBackground(BGChinh);
		btnDelete.setName("Delete");
		btnDelete.setBounds((p.getWidth() / 2), txtDapAn.getY() + txtDapAn.getHeight() + 50, 80, 30);
		btnDelete.addMouseListener(MouseBtnDelete);
		JLabel lb1 = new JLabel("Delete");
		lb1.setFont(new Font("Arial", 1, 16));
		lb1.setForeground(Color.white);
		btnDelete.add(lb1);
		p.add(btnDelete);
		
		btn = new JPanel();
		btn.setBackground(BGChinh);
		btn.setName("OK");
		btn.setBounds((p.getWidth() / 2) - 100, txtDapAn.getY() + txtDapAn.getHeight() + 50, 80, 30);
		btn.addMouseListener(MouseButton);
		JLabel lb2 = new JLabel("OK");
		lb2.setFont(new Font("Arial", 1, 16));
		lb2.setForeground(Color.white);
		btn.add(lb2);
		p.add(btn);
		
		txtCauHoi.setText(CauHoiBUS.arrCauHoi.get(stt).getTenCauHoi());
		txtA.setText(CauHoiBUS.arrCauHoi.get(stt).getCauA());
		txtB.setText(CauHoiBUS.arrCauHoi.get(stt).getCauB());
		txtC.setText(CauHoiBUS.arrCauHoi.get(stt).getCauC());
		txtD.setText(CauHoiBUS.arrCauHoi.get(stt).getCauD());
		txtDapAn.setText(CauHoiBUS.arrCauHoi.get(stt).getDapAn());
		
		return p;
	}
}
