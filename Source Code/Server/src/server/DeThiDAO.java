
package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.DeThiDTO;


public class DeThiDAO {
    DBConnection db;
    
    public ArrayList<DeThiDTO> readDeThi(){
        db = new DBConnection();
        ArrayList<DeThiDTO> arrDeThi = new ArrayList<>();
        
        try{
            String query = "SELECT * FROM DeThi";
            ResultSet rs = db.SQLQuery(query);
            if (rs != null){
                while (rs.next()){
                    String maDeThi = rs.getString("maDeThi");
                    String tenDeThi = rs.getString("tenDeThi");
                    int soCauHoi = rs.getInt("soCauHoi");
                    int thoiGianThi = rs.getInt("thoiGianThi");
                    int soLuotThi = rs.getInt("soLuotThi");
                    String userName = rs.getString("userName");
                    arrDeThi.add(new DeThiDTO(maDeThi, tenDeThi, soCauHoi, thoiGianThi, soLuotThi, userName));
                }
            }
        }
        catch (SQLException ex){
        	JOptionPane.showMessageDialog(null, "Lỗi!!! Lỗi đọc dữ liệu bảng DeThi");
        } 
        finally{
            db.closeConnection();
        }
        
        return arrDeThi;
    }
    
    public Boolean add(DeThiDTO deThi){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("INSERT INTO DeThi(maDeThi, tenDeThi, soCauHoi, thoiGianThi, soLuotThi, userName) "
                + "VALUES ('"
                + deThi.getMaDeThi()+ "','"
                + deThi.getTenDeThi()+ "',"
                + deThi.getSoCauHoi()+ ","  
                + deThi.getThoiGianThi()+ ","
                + deThi.getSoLuotThi()+ ",'"
                + deThi.getUserName()+ "');");
        db.closeConnection();
        return check;
    }

    public Boolean del(String maDeThi){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("DELETE FROM DeThi WHERE DeThi.maDeThi = '" + maDeThi + "'");
        db.closeConnection();
        return check;
    }

    public Boolean mod(DeThiDTO deThi){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("Update DeThi Set"
                + " tenDeThi = '" + deThi.getTenDeThi()
                + "', soCauHoi = " + deThi.getSoCauHoi()
                + ", thoiGianThi = " + deThi.getThoiGianThi()
                + ", soLuotThi = " + deThi.getSoLuotThi()
                + ", userName = '" + deThi.getUserName()
                + "' where maDeThi = '" + deThi.getMaDeThi() + "'");
        db.closeConnection();
        return check;
    }     
}
