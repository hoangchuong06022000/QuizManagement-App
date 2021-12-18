
package Client.BUS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import models.CauHoiDTO;


public class CauHoiBUS {
    public static ArrayList<CauHoiDTO> arrCauHoi = new ArrayList<>();
    public static ConnectServer conn;
    
    public CauHoiBUS() {
    } 
    
    public ArrayList<CauHoiDTO> getArrCauHoi() {
        return arrCauHoi;
    }
    
    public ArrayList<CauHoiDTO> SortRandomCauHoi() {
    	Random rand = new Random();
    	ArrayList<CauHoiDTO> arrOut = new ArrayList<CauHoiDTO>();

    	int len = arrCauHoi.size();
    	for(int i = 0; i < len;) {
    		int randomIndex = rand.nextInt(arrCauHoi.size());
	        CauHoiDTO randomItem = arrCauHoi.get(randomIndex);
	        if(!arrOut.contains(randomItem)) {
	        	arrOut.add(randomItem);
		        len--;
	        }		        
    	}
    	return arrOut;
    }
    
    public ArrayList<Integer> getSTTByMaDeThi(String maDeThi) {
    	ArrayList<Integer> stt = new ArrayList<>();
    	for (CauHoiDTO e : arrCauHoi){
            if(e.getMaDeThi().equals(maDeThi)) {
            	stt.add(e.getStt());
            }
        }
    	return stt;
    }
    
    public int getSoLuongCauHoi(){
        return arrCauHoi.size();
    }
    
    public Boolean add(CauHoiDTO cauHoi, String current_session) throws IOException{
        Boolean check = conn.addOrModCauHoi(cauHoi, current_session);
        if (check){
            arrCauHoi.add(cauHoi);
        }
        return check;
    }
    
    public Boolean add(int stt, String maDeThi, String tenCauHoi, String cauA, String cauB, String cauC, String cauD, String dapAn, String current_session) throws IOException{
        CauHoiDTO cauHoi = new CauHoiDTO(stt, maDeThi, tenCauHoi, cauA, cauB, cauC, cauD, dapAn);
        return this.add(cauHoi, current_session);
    }
    
    public Boolean del(int stt, String maDeThi,String current_session) throws IOException{
        Boolean check = conn.delCauHoi(stt, maDeThi, current_session);
        if (check){
            for (CauHoiDTO cauHoi : arrCauHoi){
                if ((cauHoi.getStt() == stt) && (cauHoi.getMaDeThi().equals(maDeThi))){
                    arrCauHoi.remove(cauHoi);
                    break;
                }
            }
        } 
        return check;
    }
    
    public Boolean mod(CauHoiDTO cauHoi, String current_session) throws IOException{
        Boolean check = conn.addOrModCauHoi(cauHoi, current_session);  
        if (check){
            for (CauHoiDTO s : arrCauHoi){
                if ((s.getStt() == cauHoi.getStt()) && (s.getMaDeThi().equals(cauHoi.getMaDeThi()))){
                    arrCauHoi.set(arrCauHoi.indexOf(s), cauHoi);
                    break;
                }
            }
        } 
        return check;
    }
     
    public Boolean mod(String tenCauHoi, String cauA, String cauB, String cauC, String cauD, String dapAn, String current_session) throws IOException{
        CauHoiDTO cauHoi = new CauHoiDTO(tenCauHoi, cauA, cauB, cauC, cauD, dapAn);
        return this.mod(cauHoi, current_session);
    }   
}
