package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import models.CauHoiDTO;

import Client.BUS.*;

public class TakeAnExamGUI extends JDialog 
{
	public static ArrayList<CauHoiDTO> arrCauHoi;
	public Color BGChinh = new Color(45, 59, 85);
	public Color BGPhu = new Color(232, 233, 236);
	public Color BGCam = new Color(255, 165, 0);
	public JPanel btn;
	public static JPanel pnChung;
	public JTextArea txtCauHoi, txtA, txtB, txtC, txtD;
	public ButtonGroup groupDapAn;
	public JRadioButton rdA, rdB, rdC, rdD;
	public JLabel lbCauHoi, lbPhut, lbGiay, lbDapAn, lbDapAnDung;
	public static int MM, ss;
	int interval;
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
           
        }
    };
	
	public TakeAnExamGUI(JFrame parrent, ArrayList<CauHoiDTO> arrCauHoi) 
	{
		TakeAnExamGUI.arrCauHoi = arrCauHoi;
		init(parrent, arrCauHoi);
	}
	
	public void init(JFrame parrent, ArrayList<CauHoiDTO> arrCauHoi)
    { 
        setSize(650, 500);
        setLocationRelativeTo(null);
		setTitle("Thực hiện thi");
        JPanel p = JPanelTakeAnExam(arrCauHoi, 0);
        pnChung = new JPanel();    
        pnChung.setLayout(null);
        pnChung.setBackground(Color.WHITE);
        pnChung.setBounds(0, 0, 650, 500);
        pnChung.add(p);
        JDialog frame = new JDialog(parrent, true);
        frame.getContentPane().add(pnChung);
		frame.pack();
		frame.setSize(650, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setVisible(true);
    }

	public JPanel JPanelTakeAnExam(ArrayList<CauHoiDTO> arrCauHoi, int index) {
		
		int ThoiGian = new DeThiBUS().getThoiGianThi(arrCauHoi.get(index).getMaDeThi()) - 1;
		String s_ThoiGian = "";
		if(ThoiGian < 10)
			s_ThoiGian = "0" + ThoiGian;
		else
			s_ThoiGian = "" + ThoiGian;
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(2,1));
		
		p.setLayout(null);
		p.setBackground(Color.WHITE);
		p.setBounds(0, 0, 650, 500);
		
		JLabel lbSoCau = new JLabel();
		lbSoCau.setText("Câu hỏi : ");
		lbSoCau.setBounds(20, 10, 90, 30);
		lbSoCau.setFont(new Font("Arial", Font.BOLD, 16));
		p.add(lbSoCau);
		
		lbCauHoi = new JLabel();
		lbCauHoi.setText(String.valueOf(arrCauHoi.get(index).getStt()));
		lbCauHoi.setBounds(lbSoCau.getX() + lbSoCau.getWidth(), lbSoCau.getY(), 50, 30);
		lbCauHoi.setFont(new Font("Arial", Font.BOLD, 16));
		p.add(lbCauHoi);
		
		lbPhut = new JLabel();
		lbPhut.setText(s_ThoiGian);
		lbPhut.setBounds(lbCauHoi.getX() + lbCauHoi.getWidth() + 400, lbCauHoi.getY(), 25, 30);
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
		
		txtCauHoi= new JTextArea();
		txtCauHoi.setText(arrCauHoi.get(index).getTenCauHoi());
		txtCauHoi.setBounds(lbSoCau.getX(), lbSoCau.getY() + lbSoCau.getHeight() + 20, 590, 80);
		txtCauHoi.setFont(new Font("Arial", 0, 16));
		txtCauHoi.setEnabled(false);
		txtCauHoi.setBorder(BorderFactory.createLineBorder(Color.black));
		p.add(txtCauHoi);
		
		groupDapAn = new ButtonGroup();
		rdA = new JRadioButton("A/ ");
		rdA.setBounds(txtCauHoi.getX(), txtCauHoi.getY() + txtCauHoi.getHeight() + 30, 50, 30);
		rdA.setFont(new Font("Arial", 0, 16));
		rdA.setBackground(Color.WHITE);
		groupDapAn.add(rdA);
		p.add(rdA);
		
		txtA= new JTextArea();
		txtA.setText(arrCauHoi.get(index).getCauA());
		txtA.setBounds(rdA.getX() + rdA.getWidth(), rdA.getY(), 230, 60);
		txtA.setFont(new Font("Arial", 0, 16));
		//txtA.setBorder(BorderFactory.createLineBorder(Color.black));
		txtA.setEnabled(false);
		p.add(txtA);
		
		rdB = new JRadioButton("B/ ");
		rdB.setBounds(txtA.getX() + txtA.getWidth() + 28, txtA.getY(), 50, 30);
		rdB.setFont(new Font("Arial", 0, 16));
		rdB.setBackground(Color.WHITE);
		groupDapAn.add(rdB);
		p.add(rdB);
		
		txtB= new JTextArea();
		txtB.setText(arrCauHoi.get(index).getCauB());
		txtB.setBounds(rdB.getX() + rdB.getWidth(), rdB.getY(), 230, 60);
		txtB.setFont(new Font("Arial", 0, 16));
		//txtB.setBorder(BorderFactory.createLineBorder(Color.black));
		txtB.setEnabled(false);
		p.add(txtB);
		
		rdC = new JRadioButton("C/ ");
		rdC.setBounds(rdA.getX(), txtA.getY() + txtA.getHeight() + 30, 50, 30);
		rdC.setFont(new Font("Arial", 0, 16));
		rdC.setBackground(Color.WHITE);
		groupDapAn.add(rdC);
		p.add(rdC);
		
		txtC= new JTextArea();
		txtC.setText(arrCauHoi.get(index).getCauC());
		txtC.setBounds(rdC.getX() + rdC.getWidth(), rdC.getY(), 230, 60);
		txtC.setFont(new Font("Arial", 0, 16));
		//txtC.setBorder(BorderFactory.createLineBorder(Color.black));
		txtC.setEnabled(false);
		p.add(txtC);
		
		rdD = new JRadioButton("D/ ");
		rdD.setBounds(txtC.getX() + txtC.getWidth() + 28, txtC.getY(), 50, 30);
		rdD.setFont(new Font("Arial", 0, 16));
		rdD.setBackground(Color.WHITE);
		groupDapAn.add(rdD);
		p.add(rdD);
		
		txtD= new JTextArea();
		txtD.setText(arrCauHoi.get(index).getCauD());
		txtD.setBounds(rdD.getX() + rdD.getWidth(), rdD.getY(), 230, 60);
		txtD.setFont(new Font("Arial", 0, 16));
		//txtD.setBorder(BorderFactory.createLineBorder(Color.black));
		txtD.setEnabled(false);
		p.add(txtD);
		
		lbDapAn = new JLabel();
		lbDapAn.setText("Đáp án là:");
		lbDapAn.setBounds(rdA.getX(), txtC.getY() + txtC.getHeight() + 20, 100, 30);
		lbDapAn.setForeground(BGChinh);
		lbDapAn.setFont(new Font("Arial", Font.BOLD, 15));
		p.add(lbDapAn);
		
		lbDapAnDung = new JLabel();
		lbDapAnDung.setBounds(lbDapAn.getX() + lbDapAn.getWidth() + 10, txtC.getY() + txtC.getHeight() + 20, 20, 30);
		lbDapAnDung.setForeground(BGChinh);
		lbDapAnDung.setFont(new Font("Arial", Font.BOLD, 15));
		p.add(lbDapAnDung);
				
		btn = new JPanel();
		btn.setBackground(BGChinh);
		btn.setName("OK");
		btn.setBounds((p.getWidth() / 2) - 40, lbDapAn.getY() + lbDapAn.getHeight() + 50, 80, 30);
		btn.addMouseListener(MouseButton);
		JLabel lb = new JLabel("OK");
		lb.setFont(new Font("Arial", 1, 16));
		lb.setForeground(Color.white);
		btn.add(lb);
		p.add(btn);
		
		DemNguocThoiGian(ThoiGian);
		
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
