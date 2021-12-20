package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import models.CauHoiDTO;

import Client.BUS.*;

public class TakeAnExamGUI extends JDialog 
{
	public static ArrayList<CauHoiDTO> arrCauHoi;
	public static String userName;
	public static Socket socket;
	public static ObjectInputStream in;
    public static ObjectOutputStream out;
	public Color BGChinh = new Color(45, 59, 85);
	public Color BGPhu = new Color(232, 233, 236);
	public Color BGCam = new Color(255, 165, 0);
	public JPanel btn, pnNorth, pnCenter;
	public static JDialog frame;
	public JTextArea txtCauHoi, txtA, txtB, txtC, txtD;
	public ButtonGroup groupDapAn;
	public JRadioButton rdA, rdB, rdC, rdD;
	public JLabel lbCauHoi, lbPhut, lbGiay, lbDapAn, lbDapAnDung;
	public int MM, ss;
	int interval;
	public int soCauDung, count;
	public static float diem = 0;
	public Timer timer;
	
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
        	try {
				ChonDapAn(src);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    };
	public TakeAnExamGUI(String userName, Socket socket, ObjectOutputStream out, ObjectInputStream in) {
		TakeAnExamGUI.userName = userName;
		TakeAnExamGUI.socket = socket;
		TakeAnExamGUI.out = out;
		TakeAnExamGUI.in = in;
	}
	public TakeAnExamGUI(JFrame parrent, ArrayList<CauHoiDTO> arrCauHoi) 
	{
		TakeAnExamGUI.arrCauHoi = arrCauHoi;
		init(parrent, arrCauHoi);
	}
	
	public void ChonDapAn(JPanel p) throws InterruptedException {
		
		String dapAn = "";
		if(rdA.isSelected()) {
			dapAn = txtA.getText();
			if(p.getName().equals("OK")) {				
				if(count < TakeAnExamGUI.arrCauHoi.size()) {
					if(TakeAnExamGUI.arrCauHoi.get(count).getDapAn().equals(dapAn)) {
						
						//new ConnectServer(socket, out, in).addOrModDiem(diemUser, "addDiem");
						soCauDung++;
						JOptionPane.showMessageDialog(null, "Bạn trả lời đúng!! Đáp án là:\n" + TakeAnExamGUI.arrCauHoi.get(count).getDapAn());
					}else {
						JOptionPane.showMessageDialog(null, "Bạn trả lời sai!! Đáp án là:\n" + TakeAnExamGUI.arrCauHoi.get(count).getDapAn());
					}
					count++;
					if(count == TakeAnExamGUI.arrCauHoi.size()) {
						diem = (float) 10/TakeAnExamGUI.arrCauHoi.size()*soCauDung;
						JOptionPane.showMessageDialog(null, "Bạn đã trả lời đúng " + soCauDung + "/" + count + "câu \n Số điểm của bạn là: " + diem);
						frame.dispose();
					}else {
						pnCenter.removeAll();
						pnCenter.validate();
						pnCenter.repaint();
						pnCenter.add(JPanelTakeAnExam(TakeAnExamGUI.arrCauHoi));
						pnCenter.validate();
						pnCenter.repaint();
					}
					
				}	
			}
		}
		else if(rdB.isSelected()){
			dapAn = txtB.getText();

			if(p.getName().equals("OK")) {
				if(count < TakeAnExamGUI.arrCauHoi.size()) {
					if(TakeAnExamGUI.arrCauHoi.get(count).getDapAn().equals(dapAn)) {
						soCauDung++;
						JOptionPane.showMessageDialog(null, "Bạn trả lời đúng!! Đáp án là:\n" + TakeAnExamGUI.arrCauHoi.get(count).getDapAn());
					}else {
						JOptionPane.showMessageDialog(null, "Bạn trả lời sai!! Đáp án là:\n" + TakeAnExamGUI.arrCauHoi.get(count).getDapAn());
					}
					count++;
					if(count == TakeAnExamGUI.arrCauHoi.size()) {
						diem = (float) 10/TakeAnExamGUI.arrCauHoi.size()*soCauDung;
						JOptionPane.showMessageDialog(null, "Bạn đã trả lời đúng " + soCauDung + "/" + count + "câu \n Số điểm của bạn là: " + diem);
						frame.dispose();
					}else {
						pnCenter.removeAll();
						pnCenter.validate();
						pnCenter.repaint();
						pnCenter.add(JPanelTakeAnExam(TakeAnExamGUI.arrCauHoi));
						pnCenter.validate();
						pnCenter.repaint();
					}
				}	
			}
		}
		else if(rdC.isSelected()){
			dapAn = txtC.getText();

			if(p.getName().equals("OK")) {
				if(count < TakeAnExamGUI.arrCauHoi.size()) {
					if(TakeAnExamGUI.arrCauHoi.get(count).getDapAn().equals(dapAn)) {
						soCauDung++;
						JOptionPane.showMessageDialog(null, "Bạn trả lời đúng!! Đáp án là:\n" + TakeAnExamGUI.arrCauHoi.get(count).getDapAn());
					}else {
						JOptionPane.showMessageDialog(null, "Bạn trả lời sai!! Đáp án là:\n" + TakeAnExamGUI.arrCauHoi.get(count).getDapAn());
					}
					count++;
					if(count == TakeAnExamGUI.arrCauHoi.size()) {
						diem = (float) 10/TakeAnExamGUI.arrCauHoi.size()*soCauDung;
						JOptionPane.showMessageDialog(null, "Bạn đã trả lời đúng " + soCauDung + "/" + count + "câu \n Số điểm của bạn là: " + diem);
						frame.dispose();
					}else {
						pnCenter.removeAll();
						pnCenter.validate();
						pnCenter.repaint();
						pnCenter.add(JPanelTakeAnExam(TakeAnExamGUI.arrCauHoi));
						pnCenter.validate();
						pnCenter.repaint();
					}
				}	
			}
		}
		else if(rdD.isSelected()){
			dapAn = txtD.getText();

			if(p.getName().equals("OK")) {
				if(count < TakeAnExamGUI.arrCauHoi.size()) {
					if(TakeAnExamGUI.arrCauHoi.get(count).getDapAn().equals(dapAn)) {
						soCauDung++;
						JOptionPane.showMessageDialog(null, "Bạn trả lời đúng!! Đáp án là:\n" + TakeAnExamGUI.arrCauHoi.get(count).getDapAn());
					}else {
						JOptionPane.showMessageDialog(null, "Bạn trả lời sai!! Đáp án là:\n" + TakeAnExamGUI.arrCauHoi.get(count).getDapAn());
					}
					count++;
					if(count == TakeAnExamGUI.arrCauHoi.size()) {
						diem = (float) 10/TakeAnExamGUI.arrCauHoi.size()*soCauDung;
						JOptionPane.showMessageDialog(null, "Bạn đã trả lời đúng " + soCauDung + "/" + count + "câu \n Số điểm của bạn là: " + diem);
						frame.dispose();
					}else {
						pnCenter.removeAll();
						pnCenter.validate();
						pnCenter.repaint();
						pnCenter.add(JPanelTakeAnExam(TakeAnExamGUI.arrCauHoi));
						pnCenter.validate();
						pnCenter.repaint();
					}
				}	
			}
		}else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn đáp án!!");
		}
	}
	
	public void init(JFrame parrent, ArrayList<CauHoiDTO> arrCauHoi)
    { 
		count = 0; soCauDung = 0;
        setSize(650, 500);
        setLocationRelativeTo(null);
		setTitle("Thực hiện thi");
        pnNorth = JPanelThoiGianThi(arrCauHoi);
        pnNorth.setPreferredSize(new Dimension(650, 40));
        pnCenter = new JPanel();
        pnCenter.setBounds(0, 0, 650, 460);
        pnCenter.setLayout(null);
        pnCenter.add(JPanelTakeAnExam(arrCauHoi));
        frame = new JDialog(parrent, true);
        frame.setLayout(new BorderLayout(1,2));
		frame.pack();
		frame.setSize(650, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(pnNorth, BorderLayout.NORTH);
        frame.add(pnCenter, BorderLayout.CENTER);
		frame.setVisible(true);
    }
	
	public JPanel JPanelThoiGianThi(ArrayList<CauHoiDTO> arrCauHoi) {
		int ThoiGian = new DeThiBUS().getThoiGianThi(arrCauHoi.get(count).getMaDeThi()) - 1;
		String s_ThoiGian = "";
		if(ThoiGian < 10)
			s_ThoiGian = "0" + ThoiGian;
		else
			s_ThoiGian = "" + ThoiGian;
		JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(0, 0, 650, 40);
        
		lbPhut = new JLabel();
		lbPhut.setText(s_ThoiGian);
		lbPhut.setBounds(470, 10, 25, 30);
		lbPhut.setForeground(BGChinh);
		lbPhut.setFont(new Font("Arial", Font.BOLD, 20));
		p.add(lbPhut);
		
		JLabel lbtem = new JLabel();
		lbtem.setText(":");
		lbtem.setBounds(lbPhut.getX() + lbPhut.getWidth(), lbPhut.getY(), 10, 30);
		lbtem.setForeground(BGChinh);
		lbtem.setFont(new Font("Arial", Font.BOLD, 20));
		p.add(lbtem);
		
		lbGiay = new JLabel();
		lbGiay.setText("00");
		lbGiay.setBounds(lbtem.getX() + lbtem.getWidth(), lbtem.getY(), 30, 30);
		lbGiay.setForeground(BGChinh);
		lbGiay.setFont(new Font("Arial", Font.BOLD, 20));
		p.add(lbGiay);
		
		DemNguocThoiGian(ThoiGian);
        
        return p;
	}
	
	public JPanel JPanelTakeAnExam(ArrayList<CauHoiDTO> arrCauHoi) {

		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(2,1));
		
		p.setLayout(null);
		p.setBackground(Color.WHITE);
		p.setBounds(0, 0, 650, 460);
		
		JLabel lbSoCau = new JLabel();
		lbSoCau.setText("Câu hỏi : ");
		lbSoCau.setBounds(20, 10, 90, 30);
		lbSoCau.setFont(new Font("Arial", Font.BOLD, 16));
		p.add(lbSoCau);
		
		lbCauHoi = new JLabel();
		lbCauHoi.setText(String.valueOf(arrCauHoi.get(count).getStt()));
		lbCauHoi.setBounds(lbSoCau.getX() + lbSoCau.getWidth(), lbSoCau.getY(), 50, 30);
		lbCauHoi.setFont(new Font("Arial", Font.BOLD, 16));
		p.add(lbCauHoi);
		
		txtCauHoi= new JTextArea();
		txtCauHoi.setText(arrCauHoi.get(count).getTenCauHoi());
		txtCauHoi.setBounds(lbSoCau.getX(), lbSoCau.getY() + lbSoCau.getHeight() + 10, 600, 120);
		txtCauHoi.setFont(new Font("Arial", Font.ITALIC, 16));
		txtCauHoi.setForeground(Color.black);
		txtCauHoi.setEditable(false);
		//txtCauHoi.setEnabled(false);
		//txtCauHoi.setBorder(BorderFactory.createLineBorder(Color.black));
		txtCauHoi.setLineWrap(true);
		txtCauHoi.setWrapStyleWord(true);
		p.add(txtCauHoi);
		
		groupDapAn = new ButtonGroup();
		rdA = new JRadioButton("A/ ");
		rdA.setBounds(txtCauHoi.getX(), txtCauHoi.getY() + txtCauHoi.getHeight() + 30, 50, 30);
		rdA.setFont(new Font("Arial", 0, 16));
		rdA.setBackground(Color.WHITE);
		groupDapAn.add(rdA);
		p.add(rdA);
		
		txtA= new JTextArea();
		txtA.setText(arrCauHoi.get(count).getCauA());
		txtA.setBounds(rdA.getX() + rdA.getWidth(), rdA.getY()+5, 230, 60);
		txtA.setFont(new Font("Arial", 0, 16));
		txtA.setForeground(Color.black);
		//txtA.setBorder(BorderFactory.createLineBorder(Color.black));
		txtA.setLineWrap(true);
		txtA.setWrapStyleWord(true);
		txtA.setEditable(false);
		//txtA.setEnabled(false);
		p.add(txtA);
		
		rdB = new JRadioButton("B/ ");
		rdB.setBounds(txtA.getX() + txtA.getWidth() + 28, txtA.getY(), 50, 30);
		rdB.setFont(new Font("Arial", 0, 16));
		rdB.setBackground(Color.WHITE);
		groupDapAn.add(rdB);
		p.add(rdB);
		
		txtB= new JTextArea();
		txtB.setText(arrCauHoi.get(count).getCauB());
		txtB.setBounds(rdB.getX() + rdB.getWidth(), rdB.getY()+5, 230, 60);
		txtB.setFont(new Font("Arial", 0, 16));
		txtB.setForeground(Color.black);
		//txtB.setBorder(BorderFactory.createLineBorder(Color.black));
		txtB.setLineWrap(true);
		txtB.setWrapStyleWord(true);
		txtB.setEditable(false);
		//txtB.setEnabled(false);
		p.add(txtB);
		
		rdC = new JRadioButton("C/ ");
		rdC.setBounds(rdA.getX(), txtA.getY() + txtA.getHeight() + 30, 50, 30);
		rdC.setFont(new Font("Arial", 0, 16));
		rdC.setBackground(Color.WHITE);
		groupDapAn.add(rdC);
		p.add(rdC);
		
		txtC= new JTextArea();
		txtC.setText(arrCauHoi.get(count).getCauC());
		txtC.setBounds(rdC.getX() + rdC.getWidth(), rdC.getY()+5, 230, 60);
		txtC.setFont(new Font("Arial", 0, 16));
		txtC.setForeground(Color.black);
		//txtC.setBorder(BorderFactory.createLineBorder(Color.black));
		txtC.setLineWrap(true);
		txtC.setWrapStyleWord(true);
		txtC.setEditable(false);
		//xtC.setEnabled(false);
		p.add(txtC);
		
		rdD = new JRadioButton("D/ ");
		rdD.setBounds(txtC.getX() + txtC.getWidth() + 28, txtC.getY(), 50, 30);
		rdD.setFont(new Font("Arial", 0, 16));
		rdD.setBackground(Color.WHITE);
		groupDapAn.add(rdD);
		p.add(rdD);
		
		txtD= new JTextArea();
		txtD.setText(arrCauHoi.get(count).getCauD());
		txtD.setBounds(rdD.getX() + rdD.getWidth(), rdD.getY()+5, 230, 60);
		txtD.setFont(new Font("Arial", 0, 16));
		txtD.setForeground(Color.black);
		//txtD.setBorder(BorderFactory.createLineBorder(Color.black));
		txtD.setLineWrap(true);
		txtD.setWrapStyleWord(true);
		txtD.setEditable(false);
		//txtD.setEnabled(false);
		p.add(txtD);
		
//		lbDapAn = new JLabel();
//		lbDapAn.setText("Đáp án là:");
//		lbDapAn.setBounds(rdA.getX(), txtC.getY() + txtC.getHeight() + 10, 100, 30);
//		lbDapAn.setForeground(BGChinh);
//		lbDapAn.setFont(new Font("Arial", Font.BOLD, 15));
//		p.add(lbDapAn);
//		
//		lbDapAnDung = new JLabel();
//		lbDapAnDung.setBounds(lbDapAn.getX() + lbDapAn.getWidth() + 5,txtC.getY() + txtC.getHeight() + 10 , 450, 30);
//		lbDapAnDung.setForeground(BGChinh);
//		lbDapAnDung.setFont(new Font("Arial", Font.BOLD, 15));	
//		p.add(lbDapAnDung);
				
		btn = new JPanel();
		btn.setBackground(BGChinh);
		btn.setName("OK");
		btn.setBounds((p.getWidth() / 2) - 40, txtD.getY() + txtD.getHeight() + 10, 80, 30);
		btn.addMouseListener(MouseButton);
		JLabel lb = new JLabel("OK");
		lb.setFont(new Font("Arial", 1, 16));
		lb.setForeground(Color.white);
		btn.add(lb);
		p.add(btn);
		
		return p;
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
		if (MM == 0 && ss <= 0)
	        timer.cancel();
		 if(ss <= 0)
		 {
		    MM--;
		    ss = 59;
		    return;
		 }
	    --ss;
	}
}
