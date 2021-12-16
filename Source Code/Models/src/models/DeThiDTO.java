
package models;

import java.io.Serializable;

public class DeThiDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8152060404187806101L;
	String maDeThi, tenDeThi, userName;
    int soCauHoi, soLuotThi, thoiGianThi;

    public DeThiDTO(String tenDeThi, int soCauHoi, int thoiGianThi, int soLuotThi, String userName) {
        this.tenDeThi = tenDeThi;
        this.userName = userName;
        this.soCauHoi = soCauHoi;
        this.soLuotThi = soLuotThi;
        this.thoiGianThi = thoiGianThi;
    }  

    public DeThiDTO(String maDeThi, String tenDeThi, int soCauHoi, int thoiGianThi, int soLuotThi, String userName) {
        this.maDeThi = maDeThi;
        this.tenDeThi = tenDeThi;
        this.userName = userName;
        this.soCauHoi = soCauHoi;
        this.soLuotThi = soLuotThi;
        this.thoiGianThi = thoiGianThi;
    }
    
    public String getMaDeThi() {
        return maDeThi;
    }

    public void setMaDeThi(String maDeThi) {
        this.maDeThi = maDeThi;
    }

    public String getTenDeThi() {
        return tenDeThi;
    }

    public void setTenDeThi(String tenDeThi) {
        this.tenDeThi = tenDeThi;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSoCauHoi() {
        return soCauHoi;
    }

    public void setSoCauHoi(int soCauHoi) {
        this.soCauHoi = soCauHoi;
    }

    public int getSoLuotThi() {
        return soLuotThi;
    }

    public void setSoLuotThi(int soLuotThi) {
        this.soLuotThi = soLuotThi;
    }

    public int getThoiGianThi() {
        return thoiGianThi;
    }

    public void setThoiGianThi(int thoiGianThi) {
        this.thoiGianThi = thoiGianThi;
    }
    
}
