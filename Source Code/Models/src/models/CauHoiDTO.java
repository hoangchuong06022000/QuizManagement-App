
package models;

import java.io.Serializable;

public class CauHoiDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6211856959310776023L;
	int stt;
    String maDeThi, tenCauHoi, cauA, cauB, cauC, cauD, dapAn;
    
    public CauHoiDTO(String tenCauHoi, String cauA, String cauB, String cauC, String cauD, String dapAn) {
        this.tenCauHoi = tenCauHoi;
        this.cauA = cauA;
        this.cauB = cauB;
        this.cauC = cauC;
        this.cauD = cauD;
        this.dapAn = dapAn;
    }

    public CauHoiDTO(int stt, String maDeThi, String tenCauHoi, String cauA, String cauB, String cauC, String cauD, String dapAn) {
        this.stt = stt;
        this.maDeThi = maDeThi;
        this.tenCauHoi = tenCauHoi;
        this.cauA = cauA;
        this.cauB = cauB;
        this.cauC = cauC;
        this.cauD = cauD;
        this.dapAn = dapAn;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMaDeThi() {
        return maDeThi;
    }

    public void setMaDeThi(String maDeThi) {
        this.maDeThi = maDeThi;
    }

    public String getTenCauHoi() {
        return tenCauHoi;
    }

    public void setTenCauHoi(String tenCauHoi) {
        this.tenCauHoi = tenCauHoi;
    }

    public String getCauA() {
        return cauA;
    }

    public void setCauA(String cauA) {
        this.cauA = cauA;
    }

    public String getCauB() {
        return cauB;
    }

    public void setCauB(String cauB) {
        this.cauB = cauB;
    }

    public String getCauC() {
        return cauC;
    }

    public void setCauC(String cauC) {
        this.cauC = cauC;
    }

    public String getCauD() {
        return cauD;
    }

    public void setCauD(String cauD) {
        this.cauD = cauD;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }
}
