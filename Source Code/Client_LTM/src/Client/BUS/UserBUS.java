
package Client.BUS;

import java.io.IOException;
import models.UserDTO;
import java.util.ArrayList;

public class UserBUS {
    public static ArrayList<UserDTO> arrUser = new ArrayList<UserDTO>();
    public static ConnectServer conn;
    
    public UserBUS() {
    } 
    
    public ArrayList<UserDTO> getArrUser() {
        return arrUser;
    }
    
    public int getSoLuongUser(){
        return arrUser.size();
    }
    
    public Boolean add(UserDTO user, String current_session) throws IOException{
        Boolean check = conn.addOrModUser(user, current_session);
        if (check){
            arrUser.add(user);
        }
        return check;
    }
    
    public Boolean add(String userName, String password, String hoTen, int gioiTinh, int trangThai, String ngSinh, String current_session) throws IOException{
        UserDTO user = new UserDTO(userName, password, hoTen, gioiTinh, ngSinh, trangThai);
        return this.add(user, current_session);
    }
    
    public Boolean del(String userName, String current_session) throws IOException{
        Boolean check = conn.delUser(userName, current_session);
        if (check){
            for (UserDTO user : arrUser){
                if (user.getUserName().equals(userName)){
                    arrUser.remove(user);
                    break;
                }
            }
        } 
        return check;
    }
    
    public Boolean mod(UserDTO user, String current_session) throws IOException{
        Boolean check = conn.addOrModUser(user, current_session);  
        if (check){
            for (UserDTO s : arrUser){
                if (s.getUserName().equals(user.getUserName())){
                    arrUser.set(arrUser.indexOf(s), user);
                    break;
                }
            }
        } 
        return check;
    }
     
    public Boolean mod(String password, String hoTen, int gioiTinh, int trangThai, String ngSinh, String current_session) throws IOException{
        UserDTO user = new UserDTO(password, hoTen, gioiTinh, ngSinh, trangThai);
        return this.mod(user, current_session);
    }   
    
    public String checkTK(String userName, String passHashed) {
    	for(UserDTO u : arrUser) {
    		if(u.getUserName().equals(userName) && u.getPassword().equals(passHashed)) {
    			return u.getUserName();
    		}
    	}
    	return "";
    }
}
