package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import bean.TARTIAP;
import bean.TDFCEPE;
import util.Conexion;

public class TARTIAPDAO {

	public TARTIAP isAPerceptor(TDFCEPE tcepe) throws SQLException{
		TARTIAP tartiap= null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from tartc where trim(artcod)=? and arobse like '%*%'");
		pstm.setString(1, tcepe.getPdarti().trim());
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			tartiap = new TARTIAP();
		}
		rs.close();
		pstm.close();
		return tartiap;
	}
}
