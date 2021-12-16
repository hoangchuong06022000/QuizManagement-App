
package Client.BUS;

import java.io.IOException;
import java.util.ArrayList;
import models.DeThiDTO;


public class DeThiBUS {
    public static ArrayList<DeThiDTO> arrDeThi = new ArrayList<DeThiDTO>();
    public static ConnectServer conn;
    
    public DeThiBUS() {
    } 
    
    public ArrayList <String> getPKey(){
        ArrayList <String> Pkey = new ArrayList<>();
        for (DeThiDTO e : arrDeThi){
            Pkey.add(e.getMaDeThi());
        }
        return Pkey;
    }
    
    public ArrayList<DeThiDTO> getArrDeThi() {
        return arrDeThi;
    }
    
    public int getSoLuongDeThi(){
        return arrDeThi.size();
    }
    
    public Boolean add(DeThiDTO deThi, String current_session) throws IOException{
        Boolean check = conn.addOrModDeThi(deThi, current_session);
        if (check){
            arrDeThi.add(deThi);
        }
        return check;
    }
    
    public Boolean add(String maDeThi, String tenDeThi, int soCauHoi, int thoiGianThi, int soLuotThi, String userName, String current_session) throws IOException{
        DeThiDTO deThi = new DeThiDTO(maDeThi, tenDeThi, soCauHoi, thoiGianThi, soLuotThi, userName);
        return this.add(deThi, current_session);
    }
    
    public Boolean del(String maDeThi, String current_session) throws IOException{
        Boolean check = conn.delDeThi(maDeThi, current_session);
        if (check){
            for (DeThiDTO deThi : arrDeThi){
                if (deThi.getMaDeThi().equals(maDeThi)){
                    arrDeThi.remove(deThi);
                    break;
                }
            }
        } 
        return check;
    }
    
    public Boolean mod(DeThiDTO deThi, String current_session) throws IOException{
        Boolean check = conn.addOrModDeThi(deThi, current_session);  
        if (check){
            for (DeThiDTO s : arrDeThi){
                if (s.getMaDeThi().equals(deThi.getMaDeThi())){
                    arrDeThi.set(arrDeThi.indexOf(s), deThi);
                    break;
                }
            }
        } 
        return check;
    }
     
    public Boolean mod(String tenDeThi, int soCauHoi, int thoiGianThi, int soLuotThi, String userName, String current_session) throws IOException{
        DeThiDTO deThi = new DeThiDTO(tenDeThi, soCauHoi, thoiGianThi, soLuotThi, userName);
        return this.mod(deThi, current_session);
    }   
}
