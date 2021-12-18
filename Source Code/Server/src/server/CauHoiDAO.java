
package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.CauHoiDTO;


public class CauHoiDAO {
    DBConnection db;
    
    public ArrayList<CauHoiDTO> readCauHoi(){
        db = new DBConnection();
        ArrayList<CauHoiDTO> arrCauHoi = new ArrayList<>();
        
        try{
            String query = "SELECT * FROM CauHoi";
            ResultSet rs = db.SQLQuery(query);
            if (rs != null){
                while (rs.next()){
                    int stt = rs.getInt("stt");
                    String maDeThi = rs.getString("maDeThi");
                    String tenCauHoi = rs.getString("tenCauHoi");
                    String cauA = rs.getString("cauA");
                    String cauB = rs.getString("cauB");
                    String cauC = rs.getString("cauC");
                    String cauD = rs.getString("cauD");
                    String dapAn = rs.getString("dapAn");
                    arrCauHoi.add(new CauHoiDTO(stt, maDeThi, tenCauHoi, cauA, cauB, cauC, cauD, dapAn));
                }
            }
        }
        catch (SQLException ex){
        	JOptionPane.showMessageDialog(null, "Lỗi!!! Lỗi đọc dữ liệu bảng CauHoi");
        } 
        finally{
            db.closeConnection();
        }
        
        return arrCauHoi;
    }
    
    public Boolean add(CauHoiDTO cauHoi){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("INSERT INTO CauHoi(stt, maDeThi, tenCauHoi, cauA, cauB, cauC, cauD, dapAn) "
                + "VALUES ("
                + cauHoi.getStt()+ ",'"
                + cauHoi.getMaDeThi()+ "','"
                + cauHoi.getTenCauHoi()+ "','"
                + cauHoi.getCauA()+ "','"
                + cauHoi.getCauB()+ "','"
                + cauHoi.getCauC()+ "','"
                + cauHoi.getCauD()+ "','"
                + cauHoi.getDapAn()+ "');");
        db.closeConnection();
        return check;
    }

    public Boolean del(int stt, String maDeThi){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("DELETE FROM CauHoi WHERE CauHoi.stt = " + stt + " AND CauHoi.maDeThi = '" + maDeThi + "'");
        db.closeConnection();
        return check;
    }

    public Boolean mod(CauHoiDTO cauHoi){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("Update CauHoi Set"
                + " tenCauHoi = '" + cauHoi.getTenCauHoi()
                + "', cauA = '" + cauHoi.getCauA()
                + "', cauB = '" + cauHoi.getCauB()
                + "', cauC = '" + cauHoi.getCauC()
                + "', cauD = '" + cauHoi.getCauD()
                + "', dapAn = '" + cauHoi.getDapAn()
                + "' where stt = " + cauHoi.getStt()
                + " AND maDeThi = '" + cauHoi.getMaDeThi() + "'");
        db.closeConnection();
        return check;
    }     
}
