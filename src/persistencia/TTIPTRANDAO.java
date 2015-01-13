package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.TTIPTRAN;
import recursos.Sesion;
import util.Conexion;

public class TTIPTRANDAO {

	public List<TTIPTRAN> listar() throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,operacion,movimiento,transaccion,tipomovimiento,situacion from "
						+ Sesion.bdProd + "TTIPTRAN");
		ResultSet rs = pstm.executeQuery();
		List<TTIPTRAN> listado = new ArrayList<TTIPTRAN>();
		while (rs.next()) {
			TTIPTRAN ttiptran = new TTIPTRAN();
			ttiptran.setId(rs.getInt("id"));
			ttiptran.setCodigo(rs.getString("codigo"));
			ttiptran.setOperacion(rs.getString("operacion"));
			ttiptran.setTransaccion(rs.getString("transaccion"));
			ttiptran.setMovimiento(rs.getString("movimiento"));
			ttiptran.setTipomovimiento(rs.getString("tipomovimiento"));
			ttiptran.setSituacion(rs.getString("situacion"));
			listado.add(ttiptran);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TTIPTRAN> buscarPorCodigo(String codigo, String situacion) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,operacion,movimiento,transaccion,tipomovimiento,situacion from "
						+ Sesion.bdProd + "TTIPTRAN where codigo like ? and situacion in ("
						+ situacion + ")");
		pstm.setString(1, codigo + "%");
		ResultSet rs = pstm.executeQuery();
		List<TTIPTRAN> listado = new ArrayList<TTIPTRAN>();
		while (rs.next()) {
			TTIPTRAN ttiptran = new TTIPTRAN();
			ttiptran.setId(rs.getInt("id"));
			ttiptran.setCodigo(rs.getString("codigo"));
			ttiptran.setOperacion(rs.getString("operacion"));
			ttiptran.setTransaccion(rs.getString("transaccion"));
			ttiptran.setMovimiento(rs.getString("movimiento"));
			ttiptran.setTipomovimiento(rs.getString("tipomovimiento"));
			ttiptran.setSituacion(rs.getString("situacion"));
			listado.add(ttiptran);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TTIPTRAN> buscarPorCodigo(String codigo,String movimiento,String tipomovimiento) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,operacion,movimiento,transaccion,tipomovimiento,situacion from "
						+ Sesion.bdProd + "TTIPTRAN where codigo = ? and movimiento=? and tipomovimiento=? ");
		pstm.setString(1, codigo);
		pstm.setString(2, movimiento);
		pstm.setString(3, tipomovimiento);
		ResultSet rs = pstm.executeQuery();
		List<TTIPTRAN> listado = new ArrayList<TTIPTRAN>();
		while (rs.next()) {
			TTIPTRAN ttiptran = new TTIPTRAN();
			ttiptran.setId(rs.getInt("id"));
			ttiptran.setCodigo(rs.getString("codigo"));
			ttiptran.setOperacion(rs.getString("operacion"));
			ttiptran.setTransaccion(rs.getString("transaccion"));
			ttiptran.setMovimiento(rs.getString("movimiento"));
			ttiptran.setTipomovimiento(rs.getString("tipomovimiento"));
			ttiptran.setSituacion(rs.getString("situacion"));
			listado.add(ttiptran);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public int insertar(TTIPTRAN obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into "+Sesion.bdProd+"TTIPTRAN (id,codigo,operacion,transaccion,movimiento,tipomovimiento,situacion,usu_crea,fech_crea) " +
						"values (NEXT VALUE FOR " + Sesion.bdProd+"CODTTIPTRAN,?,?,?,?,?,?,?,?)");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getOperacion());
		pstm.setString(3, obj.getTransaccion());
		pstm.setString(4, obj.getMovimiento());
		pstm.setString(5, obj.getTipomovimiento());
		pstm.setString(6, obj.getSituacion());
		pstm.setString(7, obj.getUsu_crea());
		pstm.setInt(8, obj.getFecha_crea());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int modificar(TTIPTRAN obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"update "+Sesion.bdProd+"TTIPTRAN set codigo=?,operacion=?,transaccion=?,movimiento=?,tipomovimiento=?,situacion=?,usu_mod=?,fech_mod=? where id=? ");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getOperacion());
		pstm.setString(3, obj.getTransaccion());
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
