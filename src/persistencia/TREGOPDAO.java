package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.TREGOP;
import recursos.Sesion;
import util.Conexion;

public class TREGOPDAO {

	public List<TREGOP> listar() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,tipo,movimiento,situacion from "+Sesion.bdProd+"TREGOP");
		ResultSet rs = pstm.executeQuery();
		List<TREGOP> listado = new ArrayList<TREGOP>();
		while(rs.next()){
			TREGOP tregop = new TREGOP();
			tregop.setId(rs.getInt("id"));
			tregop.setCodigo(rs.getString("codigo"));
			tregop.setTipo(rs.getString("tipo"));
			tregop.setMovimiento(rs.getString("movimiento"));
			tregop.setSituacion(rs.getString("situacion"));
			listado.add(tregop);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TREGOP> buscarPorCodigo(String codigo,String situacion) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,tipo,movimiento,situacion from "+Sesion.bdProd+"TREGOP where codigo like ? and situacion in ("+situacion+")");
		pstm.setString(1, codigo + "%");
		ResultSet rs = pstm.executeQuery();
		List<TREGOP> listado = new ArrayList<TREGOP>();
		while(rs.next()){
			TREGOP tregop = new TREGOP();
			tregop.setId(rs.getInt("id"));
			tregop.setCodigo(rs.getString("codigo"));
			tregop.setTipo(rs.getString("tipo"));
			tregop.setMovimiento(rs.getString("movimiento"));
			tregop.setSituacion(rs.getString("situacion"));
			listado.add(tregop);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TREGOP> buscarPorCodigo(String codigo) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,tipo,movimiento,situacion from "+Sesion.bdProd+"TREGOP where codigo = ?");
		pstm.setString(1, codigo);
		ResultSet rs = pstm.executeQuery();
		List<TREGOP> listado = new ArrayList<TREGOP>();
		while(rs.next()){
			TREGOP tregop = new TREGOP();
			tregop.setId(rs.getInt("id"));
			tregop.setCodigo(rs.getString("codigo"));
			tregop.setTipo(rs.getString("tipo"));
			tregop.setMovimiento(rs.getString("movimiento"));
			tregop.setSituacion(rs.getString("situacion"));
			listado.add(tregop);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public int insertar(TREGOP obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into "+Sesion.bdProd+"TREGOP (id,codigo,tipo,movimiento,situacion,usu_crea,fech_crea) values (NEXT VALUE FOR " + Sesion.bdProd+"CODTREGOP,?,?,?,?,?,?)");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getTipo());
		pstm.setString(3, obj.getMovimiento());
		pstm.setString(4, obj.getSituacion());
		pstm.setString(5, obj.getUsu_crea());
		pstm.setInt(6, obj.getFecha_crea());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int modificar(TREGOP obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"update "+Sesion.bdProd+"TREGOP set codigo=?,tipo=?,movimiento=?,situacion=?,usu_mod=?,fech_mod=? where id=? ");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getTipo());
		pstm.setString(3, obj.getMovimiento());
		pstm.setString(4, obj.getSituacion());
		pstm.setString(5, obj.getUsu_mod());
		pstm.setInt(6, obj.getFecha_mod());
		pstm.setInt(7, obj.getId());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
}