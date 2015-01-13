package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import recursos.Sesion;

import bean.TPARAM;

import util.Conexion;

public class TPARAMDAO {
	
	public TPARAM listarParametros(String id,String serie) throws SQLException{
		TPARAM tparam = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select * from "+ Sesion.bdProd+"parametrospercepcion where idparametro=? and serieparametro=?");
		pstm.setString(1, id);
		pstm.setString(2, serie);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			tparam = new TPARAM();
			tparam.setCampo1(rs.getString("campo1"));
			tparam.setCampo2(rs.getString("campo2"));
			tparam.setCampo3(rs.getString("campo3"));
		}
		rs.close();
		pstm.close();
		return tparam;
	}
		
	public int bloquearCorrelativo(String id,String serie) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("update "+ Sesion.bdProd+"parametrospercepcion set campo2='ocupado' where idparametro=? and serieparametro=? and campo2=?");
		pstm.setString(1, id);
		pstm.setString(2, serie);
		pstm.setString(3, "libre");
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public TPARAM obtenerCorrelativo(String id,String serie) throws SQLException{
		TPARAM tparam = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select campo1 from "+ Sesion.bdProd+"parametrospercepcion where idparametro=? and serieparametro=?");
		pstm.setString(1, id);
		pstm.setString(2, serie);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			tparam = new TPARAM();
			tparam.setCampo1(rs.getString("campo1"));
		}
		rs.close();		
		pstm.close();
		return tparam;
	}
	
	public int desbloquearCorrelativo(String numero,String id,String serie) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("update "+ Sesion.bdProd+"parametrospercepcion set campo1=?,campo2='libre' where idparametro=? and serieparametro=? and campo2=?");
		pstm.setString(1, numero);
		pstm.setString(2, id);
		pstm.setString(3, serie);
		pstm.setString(4, "ocupado");
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}

}
