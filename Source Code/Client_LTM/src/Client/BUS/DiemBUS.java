
package Client.BUS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import models.DiemDTO;


public class DiemBUS {
    public static ArrayList<DiemDTO> arrDiem = new ArrayList<>();
    public static ArrayList<DiemDTO> arrDiemByMaDe = new ArrayList<>();
    public static ConnectServer conn;
    
    public DiemBUS() {
    } 
    
    public int XepHangTheoMaDeThi(float diem) {
    	HashMap<Integer, Float> arrXepHang = new HashMap<>();
    	for (DiemDTO e : arrDiemByMaDe){
            if(e.getDiem() < diem) {
            	arrXepHang.put(e.getThuHang(), e.getDiem());
            }
        }
    	for (float i : arrXepHang.keySet()) {
    	      System.out.println(i);
    	}
    	return 0;
    }
    
    public ArrayList<DiemDTO> getArrDiem() {
        return arrDiem;
    }
    
    public int getSoLuongDiem(){
        return arrDiem.size();
    }
    
    public Boolean add(DiemDTO diem, String current_session) throws IOException{
        Boolean check = conn.addOrModDiem(diem, current_session);
        if (check){
            arrDiem.add(diem);
        }
        return check;
    }
    
    public Boolean add(String userName, String maDeThi, int dieM, int thuHang, String current_session) throws IOException{
        DiemDTO diem = new DiemDTO(userName, maDeThi, dieM, thuHang);
        return this.add(diem, current_session);
    }
    
    public Boolean del(String userName, String maDeThi,String current_session) throws IOException{
        Boolean check = conn.delDiem(userName, maDeThi, current_session);
        if (check){
            for (DiemDTO diem : arrDiem){
                if ((diem.getUserName().equals(userName)) && (diem.getMaDeThi().equals(maDeThi))){
                    arrDiem.remove(diem);
                    break;
                }
            }
        } 
        return check;
    }
    
    public Boolean mod(DiemDTO diem, String current_session) throws IOException{
        Boolean check = conn.addOrModDiem(diem, current_session);  
        if (check){
            for (DiemDTO s : arrDiem){
                if ((s.getUserName().equals(diem.getUserName())) && (s.getMaDeThi().equals(diem.getMaDeThi()))){
                    arrDiem.set(arrDiem.indexOf(s), diem);
                    break;
                }
            }
        } 
        return check;
    }
     
    public Boolean mod(int dieM, int thuHang, String current_session) throws IOException{
        DiemDTO diem = new DiemDTO(dieM, thuHang);
        return this.mod(diem, current_session);
    }   
}
