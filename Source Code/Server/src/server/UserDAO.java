
package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.UserDTO;

public class UserDAO {
    DBConnection db;
    
    public ArrayList<UserDTO> readUser(){
        db = new DBConnection();
        ArrayList<UserDTO> arrUser = new ArrayList<>();
        
        try{
            String query = "SELECT * FROM user";
            ResultSet rs = db.SQLQuery(query);
            if (rs != null){
                while (rs.next()){
                    String userName = rs.getString("userName");
                    String password = rs.getString("password");
                    String hoTen = rs.getString("hoTen");
                    int gioiTinh = rs.getInt("gioiTinh");
                    String ngSinh = rs.getString("ngSinh");
                    int trangThai = rs.getInt("trangThai");
                    arrUser.add(new UserDTO(userName, password, hoTen, gioiTinh, ngSinh, trangThai));
                }
            }
        }
        catch (SQLException ex){
        	JOptionPane.showMessageDialog(null, "Lỗi!!! Lỗi đọc dữ liệu bảng User");
        } 
        finally{
            db.closeConnection();
        }
        
        return arrUser;
    }
    
    public Boolean add(UserDTO user){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("INSERT INTO User(userName, password, hoTen, gioiTinh, ngSinh, trangThai) "
                + "VALUES ('"
                + user.getUserName()+ "','"
                + user.getPassword()+ "','"
                + user.getHoTen()+ "',"                  
                + user.getGioiTinh()+ ",'" 
                + user.getNgSinh()+ "','"         
                + user.isTrangThai() + "');");
        db.closeConnection();
        return check;
    }

    public Boolean del(String userName){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("DELETE FROM User WHERE User.userName = '" + userName + "'");
        db.closeConnection();
        return check;
    }

    public Boolean mod(UserDTO user){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("Update User Set"
                + " password = '" + user.getPassword()
                + "', hoTen = '" + user.getHoTen()
                + "', gioiTinh = " + user.getGioiTinh()
                + ", ngSinh = '" + user.getNgSinh()
                + "', trangThai = '" + user.isTrangThai()
                + "' where userName = '" + user.getUserName()+ "'");
        db.closeConnection();
        return check;
    }     
    public static void main(String[] args){
        System.out.println(new UserDAO().readUser());
        
    }  
}
