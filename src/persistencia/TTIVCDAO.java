package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Conexion;
import bean.TTIVC;

public class TTIVCDAO {

	public TTIVC buscar(TTIVC bean) throws SQLException {
		TTIVC nuevo = null;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"Select tvcore from ttivc  where tvejer=? and tvperi=? and tvtipo=?");
		pstm.setInt(1, bean.getTvejer());
		pstm.setInt(2, bean.getTvperi());
		pstm.setString(3, bean.getTvtipo());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			nuevo = new TTIVC();
			nuevo.setTvcore(rs.getInt("tvcore"));
		};
		rs.close();
		pstm.close();
		return nuevo;
	}
}
