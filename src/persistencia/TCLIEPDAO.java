package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Conexion;
import bean.TDFCEPE;
import bean.TCLIEP;

public class TCLIEPDAO {
	
	public TCLIEP isCPerceptor(TDFCEPE tcepe) throws SQLException{
		TCLIEP tcliep = null;
		//clnrf2 = PERCEPCION (00 NO AFECTO,01 2%,02 0.05%)
		//clnrf2 = 01 POR DEFECTO EN VIAJEROS
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select clnrf2 from TCLIC where CLNCVE=?");
		pstm.setString(1, tcepe.getPhclie());
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			tcliep = new TCLIEP();
			tcliep.setClnrf2(rs.getString("clnrf2"));
		}
		rs.close();
		pstm.close();
		return tcliep;
	}

}
