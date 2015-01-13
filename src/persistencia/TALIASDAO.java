package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Conexion;
import bean.TALIAS;

public class TALIASDAO {

	public TALIAS listarTALIAS() throws SQLException {
		TALIAS nuevo = null;
		PreparedStatement s = Conexion.obtenerConexion().prepareStatement("Select ALI014,ALI027,ALI063,ALI008 from TALIAS");
		ResultSet rs = s.executeQuery();
		while(rs.next()){
		nuevo = new TALIAS();
		nuevo.setAli014(rs.getString(1));
		nuevo.setAli027(rs.getString(2));
		nuevo.setAli063(rs.getString(3));	
		nuevo.setAli008(rs.getString("ALI008"));
		}
		rs.close();
		s.close();
		return nuevo;
	}	
}
