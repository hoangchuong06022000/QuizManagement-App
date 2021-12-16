
package models;

import java.io.Serializable;

public class UserDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2242214958172178976L;
	String userName, password, hoTen, ngSinh;
    int gioiTinh;
    int trangThai;

    public UserDTO(String password, String hoTen, int gioiTinh, String ngSinh, int trangThai) {
        this.password = password;
        this.hoTen = hoTen;
        this.ngSinh = ngSinh;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
    }

    public UserDTO(String userName, String password, String hoTen, int gioiTinh, String ngSinh, int trangThai) {
        this.userName = userName;
        this.password = password;
        this.hoTen = hoTen;
        this.ngSinh = ngSinh;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgSinh() {
        return ngSinh;
    }

    public void setNgSinh(String ngSinh) {
        this.ngSinh = ngSinh;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
}
