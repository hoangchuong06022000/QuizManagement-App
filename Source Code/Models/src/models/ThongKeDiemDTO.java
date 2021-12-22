
package models;

import java.io.Serializable;

public class ThongKeDiemDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6211856959310776023L;
    String maDeThi;
    float diemMax, diemMin;
    
    public ThongKeDiemDTO(String maDeThi, float DiemMax, float DiemMin) {
        this.maDeThi = maDeThi;
        this.diemMax = DiemMax;
        this.diemMin = DiemMin;
    }

    public String getmaDethi() {
        return maDeThi;
    }

    public void setmaDeThi(String maDethi) {
        this.maDeThi = maDethi;
    }

    public float getdiemMax() {
        return diemMax;
    }

    public void setdiemMax(float diemMax) {
        this.diemMax = diemMax;
    }

    public float getdiemMin() {
        return diemMin;
    }

    public void setdiemMin(float diemMin) {
        this.diemMin = diemMin;
    }
}
