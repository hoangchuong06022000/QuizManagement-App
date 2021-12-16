
package models;

import java.io.Serializable;

public class DiemDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7953837297898294457L;
	String userName, maDeThi;
    int diem, thuHang;
    
    public DiemDTO(int diem, int thuHang) {
        this.diem = diem;
        this.thuHang = thuHang;
    }    

    public DiemDTO(String maDeThi, String userName, int diem, int thuHang) {
        this.userName = userName;
        this.maDeThi = maDeThi;
        this.diem = diem;
        this.thuHang = thuHang;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMaDeThi() {
        return maDeThi;
    }

    public void setMaDeThi(String maDeThi) {
        this.maDeThi = maDeThi;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public int getThuHang() {
        return thuHang;
    }

    public void setThuHang(int thuHang) {
        this.thuHang = thuHang;
    }
    
}
