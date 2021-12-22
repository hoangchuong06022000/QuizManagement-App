package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.BUS.CauHoiBUS;
import Client.BUS.ConnectServer;
import Client.BUS.DeThiBUS;
import Client.BUS.DiemBUS;
import Client.BUS.UserBUS;
import models.CauHoiDTO;
import models.DeThiDTO;
import models.DiemDTO;


public class MainGUI extends JFrame
{
	public static String current_session = "";
	public static Socket socket;
	public static String userName = "";
	public static ObjectInputStream in;
    public static ObjectOutputStream out;
    ConnectServer conn;
	public JPanel pLeft, pCenter, pBoDeThi;
	public static JPanel pDown;
	private JPanel[] pnDanhMuc, pnPhanTrang; 
    private JLabel[] lbDanhMuc;
	private String[] DanhMuc = {"Tham gia thi", "Tạo đề thi", "Thông tin tài khoản", "Đăng xuất"};
	public Color BGChinh = new Color(45, 59, 85);
	public Color BGPhu = new Color(232, 233, 236);
	public Color BGCam = new Color(255, 165, 0);
	public String LuaChon, TrangHienTai;
	public int SoDeToiDa = 2;
	public static int SoTrang = 0;
		
	public MouseAdapter MouseEV = new MouseAdapter() 
    {
        public void mouseEntered(MouseEvent me) 
        {
            JPanel src =(JPanel) me.getSource();
            for(int i = 0; i < DanhMuc.length; i++)
            {
                if(src.getName().equals(DanhMuc[i]) && LuaChon.equals(DanhMuc[i]) == false)
                {
                    pnDanhMuc[i].setBackground(BGCam);
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent me) 
        {
            JPanel src =(JPanel) me.getSource();
            for(int i = 0; i < DanhMuc.length; i++)
            {
                if(src.getName().equals(DanhMuc[i]) && LuaChon.equals(DanhMuc[i]) == false)
                {
                    pnDanhMuc[i].setBackground(BGChinh);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent me)
        {
        	JPanel src =(JPanel) me.getSource();
            switch (src.getName()) {
				case "Tham gia thi":{
					try {
					    conn = new ConnectServer(socket, "readDeThi", out, in);
						SetBGDanhMuc();
						pnDanhMuc[0].setBackground(BGCam);
	        			LuaChon = src.getName().toString();
	                    XoaPhanNoiDung(src); 
	                    Thread.sleep(500);
						break;
					} catch (IOException e) {	
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
							| InvalidAlgorithmParameterException | BadPaddingException
							| IllegalBlockSizeException e) {
						e.printStackTrace();
					}
				}
				case "Tạo đề thi":{
					SetBGDanhMuc();
					pnDanhMuc[1].setBackground(BGCam);
                    LuaChon = src.getName().toString();
                    XoaPhanNoiDung(src);
					break;
				}	
				case "Thông tin tài khoản":{
					SetBGDanhMuc();
					pnDanhMuc[2].setBackground(BGCam);
                    LuaChon = src.getName().toString();
                    XoaPhanNoiDung(src);
					break;
				}		
				case "Đăng xuất":{
					SetBGDanhMuc();
					pnDanhMuc[3].setBackground(BGCam);
	                LuaChon = src.getName().toString();
	                XoaPhanNoiDung(src);
	        		int select = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất không?", "Lựa chọn của bạn", JOptionPane.YES_NO_OPTION);
	        		if (select == JOptionPane.YES_OPTION) {
	        			dispose();
	                    new LoginGUI(0).setVisible(true);
	        		}
					break;			
				}
					
			}        	                                
        }
    };
    
    public MouseAdapter MouseEVPhanTrang = new MouseAdapter() 
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
            if(TrangHienTai.equals(src.getName()) == true)
            {
                	return;
            }
            src.setBackground(BGChinh);
        }

        @Override
        public void mouseClicked(MouseEvent me)
        {
            JPanel src =(JPanel) me.getSource();
            for(int i = 0; i < SoTrang; i++)
            {
            	pnPhanTrang[i].setBackground(BGChinh);
            }
            src.setBackground(BGCam);
            TrangHienTai = src.getName();
            ChuyenTrang();
        }
    };
	public MainGUI(String userName) {
		MainGUI.userName = userName;
		new CreateExamGUI(userName);
	}
	public MainGUI(Socket socket, ObjectOutputStream out, ObjectInputStream in)
    {
		MainGUI.socket = socket;
		MainGUI.out = out;
		MainGUI.in = in;
        init();
    }
	
	public void SetBGDanhMuc() {
		for(int i = 0; i < DanhMuc.length; i++) {
			pnDanhMuc[i].setBackground(BGChinh);
		}
	}
	
	public void LoadBoDeThi()
	{
		SoTrang = (DeThiBUS.arrDeThi.size()) / SoDeToiDa;
		if(DeThiBUS.arrDeThi.size() % SoDeToiDa != 0)
		{
			SoTrang ++;
		}
	}
    
    public void init()
    {
    	LuaChon = "Tham gia thi";
    	TrangHienTai = "1"; 
        setTitle("Chương trình thi trắc nghiệm");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(2,1));
        
        pLeft = JPanelMenuLeft();
        pLeft.setBackground(BGChinh);
        pLeft.setPreferredSize(new Dimension(250, 0));
        
        LoadBoDeThi();
        
        pCenter = JPanelThamGiaThi();
        pCenter.setBackground(Color.white);
        pCenter.setPreferredSize(new Dimension(0, 0));
                
        add(pLeft, BorderLayout.WEST);
        add(pCenter, BorderLayout.CENTER);
    }
    
    public JPanel JPanelMenuLeft()
    {
        int y = 0;
        JPanel p =new JPanel();
        p.setLayout(null);
        p.setBounds(0, 0, 250, 300);
        p.setBackground(Color.BLACK);
        pnDanhMuc = new JPanel[DanhMuc.length];
        lbDanhMuc = new JLabel[DanhMuc.length];
        for(int i = 0; i < DanhMuc.length; i++)
        {
            pnDanhMuc[i] = new JPanel();
            pnDanhMuc[i].setName(DanhMuc[i]);
            pnDanhMuc[i].setBounds(0, y, p.getWidth(), 60);
            pnDanhMuc[i].setBackground(BGChinh);
            pnDanhMuc[i].setLayout(null);
            pnDanhMuc[i].addMouseListener(MouseEV);
            lbDanhMuc[i] = new JLabel(DanhMuc[i]);
            lbDanhMuc[i].setBounds(30, 0, p.getWidth() - 80, pnDanhMuc[i].getHeight());
            lbDanhMuc[i].setForeground(Color.WHITE);
            lbDanhMuc[i].setFont(new Font("Arial", Font.BOLD, 16));
            pnDanhMuc[i].add(lbDanhMuc[i]);
            p.add(pnDanhMuc[i]);
            y += 60;
        }
        pnDanhMuc[0].setBackground(BGCam);
        pnDanhMuc[0].setForeground(Color.white);
        return p;
    }
    
    public JPanel JPanelThamGiaThi() 
	{
    	JPanel p = new JPanel();
    	p.setLayout(new BorderLayout(2,1));
    	
    	pBoDeThi = JPanelBoDeThi();
    	pBoDeThi.setPreferredSize(new Dimension(0, 0));
    	pBoDeThi.setBackground(Color.white);
    	
    	pDown = new JPanel();
    	pDown.setPreferredSize(new Dimension(0, 50));
    	pDown.setBackground(Color.white);
    	
    	LoadBoDeThi();

		pnPhanTrang = new JPanel[SoTrang];
		for(int i = 0; i < SoTrang; i++)
		{
			pnPhanTrang[i] = new JPanel();
			pnPhanTrang[i].setName((i + 1) + "");
			pnPhanTrang[i].addMouseListener(MouseEVPhanTrang);
			pnPhanTrang[i].setBackground(BGChinh);
			JLabel lb = new JLabel((i + 1) + "");
			lb.setForeground(Color.white);
			pnPhanTrang[i].add(lb);
			if(i == (Integer.parseInt(TrangHienTai) - 1))
			{
				pnPhanTrang[i].setBackground(BGCam);
			}
			pDown.add(pnPhanTrang[i]);
		}
    	
    	p.add(pBoDeThi, BorderLayout.CENTER);
    	p.add(pDown, BorderLayout.SOUTH);
    	return p;
	}
	
	public JPanel JPanelBoDeThi() 
	{
		int i = (Integer.parseInt(TrangHienTai) - 1) * SoDeToiDa;
		int dem = i;
		JPanel p = new JPanel();
        
        for(; dem < (SoDeToiDa * Integer.parseInt(TrangHienTai)) && dem < DeThiBUS.arrDeThi.size(); dem++)
        {
        	JPanel pn = JPanelDeThi(DeThiBUS.arrDeThi.get(i));
        	p.add(pn);
        	i++;
        }
        
        p.setLayout(new GridLayout(2, 0, 5, 5));
        return p;
	}
	
	public JPanel JPanelDeThi(DeThiDTO De)
	{
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.black));
    	p.setLayout(new BorderLayout(3, 1));
    	
    	JPanel pUp = new JPanel();
    	pUp.setPreferredSize(new Dimension(0, 50));
    	pUp.setBackground(Color.white);
    	JLabel lbTenDe = new JLabel(De.getTenDeThi());
    	lbTenDe.setHorizontalAlignment(JLabel.CENTER);
		lbTenDe.setForeground(BGCam);
		lbTenDe.setFont(new Font("Arial", Font.BOLD, 14));
		pUp.add(lbTenDe);
 	
    	JPanel pCenter = new JPanel();
    	pCenter.setPreferredSize(new Dimension(0, 0));
    	pCenter.setBackground(Color.white);
    	
    	JPanel pn = new JPanel();
    	pn.setBackground(Color.white);
    	pn.setLayout(new BoxLayout(pn, BoxLayout.Y_AXIS));
    	JLabel lbSoCau = new JLabel("Số câu : " + De.getSoCauHoi());
    	pn.add(lbSoCau);
		
		JLabel lbLuotThi = new JLabel("Lượt thi : " + De.getSoLuotThi());
		pn.add(lbLuotThi);
		
		JLabel lbThoiGian = new JLabel("Thời gian : " + De.getThoiGianThi() + " phút");
		pn.add(lbThoiGian);
		
		JLabel lbNguoiLam = new JLabel("Người tạo : " + De.getUserName());
		pn.add(lbNguoiLam);
				
		pCenter.add(pn);
    	
    	
    	JPanel pDown = new JPanel();
    	pDown.setPreferredSize(new Dimension(0, 40));
    	pDown.setBackground(Color.white);
    	JButton btnThi = new JButton("Thi");
    	btnThi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ThucHienThi(De.getMaDeThi());
			}
		});
    	btnThi.setName(De.getMaDeThi());
    	pDown.add(btnThi);
    	if((De.getSoLuotThi() == 0) && (De.getUserName().equals(userName)))
    	{
    		JButton btnSua = new JButton("Sửa");
    		btnSua.setName(De.getMaDeThi());
        	pDown.add(btnSua);
        	btnSua.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				SuaDeThi(De.getMaDeThi());
    			}
    		});
    	}
    	
		
    	
    	p.add(pUp, BorderLayout.NORTH);
    	p.add(pCenter, BorderLayout.CENTER);
    	p.add(pDown, BorderLayout.SOUTH);
				
		return p;
	}
	
	public void XoaPhanNoiDung(JPanel p)
    {
        pCenter.removeAll();
        pCenter.validate();
        pCenter.repaint();
        switch(p.getName())
        {
            case "Tham gia thi":
            {
                pCenter.add(JPanelThamGiaThi());
                break;
            }
            case "Tạo đề thi":
            {
                pCenter.add(new CreateExamGUI(socket, out, in).JPanelChung());
                break;
            }
            case "Thông tin tài khoản":
            {
            	pCenter.add(new UpdateUserGUI().JPanelUpdateUser());
                break;
            }
        }
        pCenter.validate();
        pCenter.repaint();
    }
	
	public void ChuyenTrang()
    {
		 pCenter.removeAll();
	     pCenter.validate();
	     pCenter.repaint();
	        
	     pCenter.add(JPanelThamGiaThi());
	     
	     pCenter.validate();
	     pCenter.repaint();
    }
	
	public void ThucHienThi(String maDeThi)
    {		
		new ConnectServer(socket, out, in).readDiemByMaDeThi(maDeThi, "readDiemByMaDe");
		DiemBUS bus = new DiemBUS();
		for(DiemDTO Diem : bus.arrDiemByMaDe)
		{
			if(Diem.getUserName().equals(LoginGUI.current_user.getUserName()))
			{
				JOptionPane.showMessageDialog(null, "Bạn đã làm đề thi này rồi");
				return;
			}
		}
		new ConnectServer(socket, out, in).readCauHoiByMaDeThi(maDeThi, "readCauHoi");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DeThiDTO DeThi = new DeThiDTO("", 0, 0, 0, "");
		for(int i = 0; i < DeThiBUS.arrDeThi.size(); i++){
			if(DeThiBUS.arrDeThi.get(i).getMaDeThi() == maDeThi){
				DeThi = DeThiBUS.arrDeThi.get(i);
			}
		}
		ArrayList<CauHoiDTO> arrRandom = new CauHoiBUS().SortRandomCauHoi();
		MainGUI parrent = new MainGUI(userName);
		new TakeAnExamGUI(userName, socket, out, in);
		TakeAnExamGUI.DeThi = DeThi;
		new TakeAnExamGUI(parrent, arrRandom);	
    }
	
	public void SuaDeThi(String maDeThi)
    {		
		new ConnectServer(socket, out, in).readCauHoiByMaDeThi(maDeThi, "readCauHoi");		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DeThiDTO DeThi = new DeThiDTO("", 0, 0, 0, "");
		for(int i = 0; i < DeThiBUS.arrDeThi.size(); i++)
		{
			if(DeThiBUS.arrDeThi.get(i).getMaDeThi() == maDeThi)
			{
				DeThi = DeThiBUS.arrDeThi.get(i);
			}
		}
		
		UpdateExamGUI.DeThi = DeThi;
		UpdateExamGUI.userName = userName;
		new UpdateExamGUI(socket,out,in).setVisible(true);;
		
    }
}
