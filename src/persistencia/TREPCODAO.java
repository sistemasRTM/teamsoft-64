package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.TREPCO;
import recursos.Sesion;
import util.Conexion;

public class TREPCODAO {

	public List<TREPCO> listar() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,val1,val2,val3,situacion from "+Sesion.bdProd+"TREPCO");
		ResultSet rs = pstm.executeQuery();
		List<TREPCO> listado = new ArrayList<TREPCO>();
		while(rs.next()){
			TREPCO trepco = new TREPCO();
			trepco.setId(rs.getInt("id"));
			trepco.setCodigo(rs.getString("codigo"));
			trepco.setValor1(rs.getString("val1"));
			trepco.setValor2(rs.getString("val2"));
			trepco.setValor3(rs.getString("val3"));
			trepco.setSituacion(rs.getString("situacion"));
			listado.add(trepco);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TREPCO> buscarPorCodigo(String codigo,String situacion) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,val1,val2,val3,situacion from "+Sesion.bdProd+"TREPCO where codigo like ? and situacion in ("+situacion+")");
		pstm.setString(1, codigo + "%");
		ResultSet rs = pstm.executeQuery();
		List<TREPCO> listado = new ArrayList<TREPCO>();
		while(rs.next()){
			TREPCO trepco = new TREPCO();
			trepco.setId(rs.getInt("id"));
			trepco.setCodigo(rs.getString("codigo"));
			trepco.setValor1(rs.getString("val1"));
			trepco.setValor2(rs.getString("val2"));
			trepco.setValor3(rs.getString("val3"));
			trepco.setSituacion(rs.getString("situacion"));
			listado.add(trepco);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TREPCO> buscarPorCodigo(String codigo) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,val1,val2,val3,situacion from "+Sesion.bdProd+"TREPCO where codigo = ? ");
		pstm.setString(1, codigo);
		ResultSet rs = pstm.executeQuery();
		List<TREPCO> listado = new ArrayList<TREPCO>();
		while(rs.next()){
			TREPCO trepco = new TREPCO();
			trepco.setId(rs.getInt("id"));
			trepco.setCodigo(rs.getString("codigo"));
			trepco.setValor1(rs.getString("val1"));
			trepco.setValor2(rs.getString("val2"));
			trepco.setValor3(rs.getString("val3"));
			trepco.setSituacion(rs.getString("situacion"));
			listado.add(trepco);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public TREPCO obtenerEquivalencia(String val1,String val2,String val3) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,val1,val2,val3,situacion from "+Sesion.bdProd+"TREPCO where val1=? and val2=? and val3=?");
		pstm.setString(1, val1);
		pstm.setString(2, val2);
		pstm.setString(3, val3);
		ResultSet rs = pstm.executeQuery();
		TREPCO trepco = null;
		while(rs.next()){
			trepco = new TREPCO();
			trepco.setId(rs.getInt("id"));
			trepco.setCodigo(rs.getString("codigo"));
			trepco.setValor1(rs.getString("val1"));
			trepco.setValor2(rs.getString("val2"));
			trepco.setValor3(rs.getString("val3"));
			trepco.setSituacion(rs.getString("situacion"));
		}
		rs.close();
		pstm.close();
		return trepco;
	}
	
	public int insertar(TREPCO obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into "+Sesion.bdProd+"TREPCO (id,codigo,val1,val2,val3,situacion,usu_crea,fech_crea) values (NEXT VALUE FOR " + Sesion.bdProd+"CODTREPCO,?,?,?,?,?,?,?)");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getValor1());
		pstm.setString(3, obj.getValor2());
		pstm.setString(4, obj.getValor3());
		pstm.setString(5, obj.getSituacion());
		pstm.setString(6, obj.getUsu_crea());
		pstm.setInt(7, obj.getFecha_crea());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int modificar(TREPCO obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"update "+Sesion.bdProd+"TREPCO set codigo=?,val1=?,val2=?,val3=?,situacion=?,usu_mod=?,fech_mod=? where id=? ");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getValor1());
		pstm.setString(3, obj.getValor2());
		pstm.setString(4, obj.getValor3());
		pstm.setString(5, obj.getSituacion());
		pstm.setString(6, obj.getUsu_mod());
		pstm.setInt(7, obj.getFecha_mod());
		pstm.setInt(8, obj.getId());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
}
