package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos.Sesion;
import util.Conexion;
import bean.TASDT;

public class TASDTDAO {

	public List<TASDT> listar() throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,documento,abr,movimiento,tipomovimiento,situacion from "
						+ Sesion.bdProd + "TASDT");
		ResultSet rs = pstm.executeQuery();
		List<TASDT> listado = new ArrayList<TASDT>();
		while (rs.next()) {
			TASDT tasdt = new TASDT();
			tasdt.setId(rs.getInt("id"));
			tasdt.setCodigo(rs.getString("codigo"));
			tasdt.setDocumento(rs.getString("documento"));
			tasdt.setAbreviado(rs.getString("abr"));
			tasdt.setMovimiento(rs.getString("movimiento"));
			tasdt.setTipomovimiento(rs.getString("tipomovimiento"));
			tasdt.setSituacion(rs.getString("situacion"));
			listado.add(tasdt);
		}
		rs.close();
		pstm.close();
		return listado;
	}

	public List<TASDT> buscarPorCodigo(String codigo, String situacion)
			throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,documento,abr,movimiento,tipomovimiento,situacion from "
						+ Sesion.bdProd
						+ "TASDT where codigo like ? and situacion in ("
						+ situacion + ")");
		pstm.setString(1, codigo + "%");
		ResultSet rs = pstm.executeQuery();
		List<TASDT> listado = new ArrayList<TASDT>();
		while (rs.next()) {
			TASDT tasdt = new TASDT();
			tasdt.setId(rs.getInt("id"));
			tasdt.setCodigo(rs.getString("codigo"));
			tasdt.setDocumento(rs.getString("documento"));
			tasdt.setAbreviado(rs.getString("abr"));
			tasdt.setMovimiento(rs.getString("movimiento"));
			tasdt.setTipomovimiento(rs.getString("tipomovimiento"));
			tasdt.setSituacion(rs.getString("situacion"));
			listado.add(tasdt);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TASDT> buscarPorCodigo(String codigo,String abreviado,String movimiento,String tipomov)
			throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,documento,abr,movimiento,tipomovimiento,situacion from "
						+ Sesion.bdProd
						+ "TASDT where codigo=? and abr=? and movimiento=? and tipomovimiento=? ");
		pstm.setString(1, codigo);
		pstm.setString(2, abreviado);
		pstm.setString(3, movimiento);
		pstm.setString(4, tipomov);
		ResultSet rs = pstm.executeQuery();
		List<TASDT> listado = new ArrayList<TASDT>();
		while (rs.next()) {
			TASDT tasdt = new TASDT();
			tasdt.setId(rs.getInt("id"));
			tasdt.setCodigo(rs.getString("codigo"));
			tasdt.setDocumento(rs.getString("documento"));
			tasdt.setAbreviado(rs.getString("abr"));
			tasdt.setMovimiento(rs.getString("movimiento"));
			tasdt.setTipomovimiento(rs.getString("tipomovimiento"));
			tasdt.setSituacion(rs.getString("situacion"));
			listado.add(tasdt);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public int insertar(TASDT obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into "+Sesion.bdProd+"TASDT (id,codigo,documento,abr,movimiento,tipomovimiento,situacion,usu_crea,fech_crea) " +
						"values (NEXT VALUE FOR " + Sesion.bdProd+"CODTASDT,?,?,?,?,?,?,?,?)");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getDocumento());
		pstm.setString(3, obj.getAbreviado());
		pstm.setString(4, obj.getMovimiento());
		pstm.setString(5, obj.getTipomovimiento());
		pstm.setString(6, obj.getSituacion());
		pstm.setString(7, obj.getUsu_crea());
		pstm.setInt(8, obj.getFecha_crea());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int modificar(TASDT obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"update "+Sesion.bdProd+"TASDT set codigo=?,documento=?,abr=?,movimiento=?,tipomovimiento=?,situacion=?,usu_mod=?,fech_mod=? where id=? ");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getDocumento());
		pstm.setString(3, obj.getAbreviado());
		pstm.setString(4, obj.getMovimiento());
		pstm.setString(5, obj.getTipomovimiento());
		pstm.setString(6, obj.getSituacion());
		pstm.setString(7, obj.getUsu_mod());
		pstm.setInt(8, obj.getFecha_mod());
		pstm.setInt(9, obj.getId());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
}