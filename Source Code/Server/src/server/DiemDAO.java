
package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.DiemDTO;
import models.ThongKeDiemDTO;

public class DiemDAO {
    DBConnection db;
    
    public ArrayList<DiemDTO> readDiem(){
        db = new DBConnection();
        ArrayList<DiemDTO> arrDiem = new ArrayList<>();
        
        try{
            String query = "SELECT * FROM ORDER BY Diem";
            ResultSet rs = db.SQLQuery(query);
            if (rs != null){
                while (rs.next()){
                    String maDeThi = rs.getString("maDeThi");
                    String userName = rs.getString("userName");
                    int diem = rs.getInt("diem");
                    int thuHang = rs.getInt("thuHang");
                    arrDiem.add(new DiemDTO(maDeThi, userName, diem, thuHang));
                }
            }
        }
        catch (SQLException ex){
        	JOptionPane.showMessageDialog(null, "Lỗi!!! Lỗi đọc dữ liệu bảng Diem");
        } 
        finally{
            db.closeConnection();
        }
        
        return arrDiem;
    }
    public ArrayList<ThongKeDiemDTO> ThongKeDiem(){
        db = new DBConnection();
        ArrayList<ThongKeDiemDTO> arrThongKe= new ArrayList<>();
        
        try{
            String query = "SELECT maDeThi, MAX(Diem) as maxDiem, MIN(Diem) as minDiem FROM Diem GROUP BY maDeThi";
            ResultSet rs = db.SQLQuery(query);
            if (rs != null){
                while (rs.next()){
                    String maDeThi = rs.getString("maDeThi");
                    String diemMax = rs.getString("maxDiem");
                    String diemMin = rs.getString("minDiem");
                    arrThongKe.add(new ThongKeDiemDTO(maDeThi, Float.parseFloat(diemMax), Float.parseFloat(diemMin)));
                }
            }
        }
        catch (SQLException ex){
        	JOptionPane.showMessageDialog(null, "Lỗi!!! Lỗi đọc dữ liệu bảng Diem");
        } 
        finally{
            db.closeConnection();
        }
        
        return arrThongKe;
    }
    public ArrayList<DiemDTO> readDiemByMaDeThi(String maDe){
        db = new DBConnection();
        ArrayList<DiemDTO> arrDiem = new ArrayList<>();
        
        try{
            String query = "SELECT * FROM Diem Where maDeThi='" + maDe + "'";
            ResultSet rs = db.SQLQuery(query);
            if (rs != null){
                while (rs.next()){
                    String maDeThi = rs.getString("maDeThi");
                    String userName = rs.getString("userName");
                    int diem = rs.getInt("diem");
                    int thuHang = rs.getInt("thuHang");
                    arrDiem.add(new DiemDTO(maDeThi, userName, diem, thuHang));
                }
            }
        }
        catch (SQLException ex){
        	JOptionPane.showMessageDialog(null, "Lỗi!!! Lỗi đọc dữ liệu bảng Diem");
        } 
        finally{
            db.closeConnection();
        }
        
        return arrDiem;
    }
    
    public Boolean add(DiemDTO diem){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("INSERT INTO Diem(maDeThi, userName, diem, thuHang) "
                + "VALUES ('"
                + diem.getMaDeThi()+ "','"
                + diem.getUserName()+ "',"
                + diem.getDiem()+ ","                    
                + diem.getThuHang()+ ");");
        db.closeConnection();
        return check;
    }

    public Boolean del(String maDeThi, String userName){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("DELETE FROM Diem WHERE Diem.maDeThi = '" + maDeThi + "' AND Diem.userName = '" + userName + "'");
        db.closeConnection();
        return check;
    }

    public Boolean mod(DiemDTO diem){
        db = new DBConnection();
        Boolean check = db.SQLUpdate("Update Diem Set"
                + " diem = " + diem.getDiem()
                + ", thuHang = " + diem.getThuHang()
                + " where maDeThi = '" + diem.getMaDeThi()
                + "' AND userName = '" + diem.getUserName()+ "'");
        db.closeConnection();
        return check;
    }     
}
