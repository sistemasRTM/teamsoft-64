package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos.Sesion;
import util.Conexion;
import bean.TECC;

public class TECCDAO {

	public List<TECC> listar() throws SQLException {
		List<TECC> listado = new ArrayList<TECC>();
		TECC objTECC = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select criterio,substring(excepcion,length(excepcion)-8,length(excepcion)) excepc from " + Sesion.bdProd + "tecc order by criterio");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTECC = new TECC();
			objTECC.setCriterio(rs.getString("criterio"));
			objTECC.setExcepcion(rs.getString("excepc"));
			listado.add(objTECC);
		}
		return listado;
	}
	
	public List<TECC> buscarPorCriterio(TECC tecc) throws SQLException {
		List<TECC> listado = new ArrayList<TECC>();
		TECC objTECC = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select * from " + Sesion.bdProd + "tecc where criterio=?");
		pstm.setString(1, tecc.getCriterio());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objTECC = new TECC();
			objTECC.setCriterio(rs.getString("criterio"));
			objTECC.setExcepcion(rs.getString("excepcion"));
			listado.add(objTECC);
		}
		return listado;
	}
	
	public int eliminar(String criterio)throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"delete from " + Sesion.bdProd + "tecc where criterio=?");
		pstm.setString(1,criterio);
		return pstm.executeUpdate();
	}
	
	public int insertar(TECC tecc)throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into " + Sesion.bdProd + "tecc(criterio,excepcion) values(?,?)");
		pstm.setString(1, tecc.getCriterio());
		pstm.setString(2, tecc.getExcepcion());
		
		return pstm.executeUpdate();
	}
	
}
