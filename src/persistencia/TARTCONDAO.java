package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.TARTCON;
import recursos.Sesion;
import util.Conexion;

public class TARTCONDAO {

	public List<TARTCON> listar() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,ref4,ref1,ref2,ref3,codart,tipo,situacion from "+Sesion.bdProd+"TARTCON");
		ResultSet rs = pstm.executeQuery();
		List<TARTCON> listado = new ArrayList<TARTCON>();
		while(rs.next()){
			TARTCON tartcon = new TARTCON();
			tartcon.setId(rs.getInt("id"));
			tartcon.setCodigo(rs.getString("ref4"));
			tartcon.setValor1(rs.getString("ref1"));
			tartcon.setValor2(rs.getString("ref2"));
			tartcon.setValor3(rs.getString("ref3"));
			tartcon.setTipo(rs.getString("tipo"));
			tartcon.setArticulo(rs.getString("codart"));
			tartcon.setSituacion(rs.getString("situacion"));
			listado.add(tartcon);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TARTCON> buscarPorCodigo(String codigo,String situacion) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,ref4,ref1,ref2,ref3,codart,tipo,situacion from "+Sesion.bdProd+"TARTCON where ref4 like ? and situacion in ("+situacion+")");
		pstm.setString(1, codigo + "%");
		ResultSet rs = pstm.executeQuery();
		List<TARTCON> listado = new ArrayList<TARTCON>();
		while(rs.next()){
			TARTCON tartcon = new TARTCON();
			tartcon.setId(rs.getInt("id"));
			tartcon.setCodigo(rs.getString("ref4"));
			tartcon.setValor1(rs.getString("ref1"));
			tartcon.setValor2(rs.getString("ref2"));
			tartcon.setValor3(rs.getString("ref3"));
			tartcon.setTipo(rs.getString("tipo"));
			tartcon.setArticulo(rs.getString("codart"));
			tartcon.setSituacion(rs.getString("situacion"));
			listado.add(tartcon);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TARTCON> buscarPorCodigo(String codigo,String articulo,String vacio) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,ref4,ref1,ref2,ref3,codart,tipo,situacion from "+Sesion.bdProd+"TARTCON where ref4 = ? and codart=?");
		pstm.setString(1, codigo);
		pstm.setString(2, articulo);
		ResultSet rs = pstm.executeQuery();
		List<TARTCON> listado = new ArrayList<TARTCON>();
		while(rs.next()){
			TARTCON tartcon = new TARTCON();
			tartcon.setId(rs.getInt("id"));
			tartcon.setCodigo(rs.getString("ref4"));
			tartcon.setValor1(rs.getString("ref1"));
			tartcon.setValor2(rs.getString("ref2"));
			tartcon.setValor3(rs.getString("ref3"));
			tartcon.setTipo(rs.getString("tipo"));
			tartcon.setArticulo(rs.getString("codart"));
			tartcon.setSituacion(rs.getString("situacion"));
			listado.add(tartcon);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public int insertar(TARTCON obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into "+Sesion.bdProd+"TARTCON (id,ref4,ref1,ref2,ref3,codart,tipo,situacion,usucre,feccre,usumod,fecmod) values (NEXT VALUE FOR " + Sesion.bdProd+"CODTARTCON,?,?,?,?,?,?,?,?,?,?,?)");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getValor1());
		pstm.setString(3, obj.getValor2());
		pstm.setString(4, obj.getValor3());
		pstm.setString(5, obj.getArticulo());
		pstm.setString(6, obj.getTipo());
		pstm.setString(7, obj.getSituacion());
		pstm.setString(8, obj.getUsu_crea());
		pstm.setInt(9, obj.getFecha_crea());
		pstm.setString(10, obj.getUsu_mod());
		pstm.setInt(11, obj.getFecha_mod());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int modificar(TARTCON obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"update "+Sesion.bdProd+"TARTCON set ref4=?,ref1=?,ref2=?,ref3=?,codart=?,tipo=?,situacion=?,usumod=?,fecmod=? where id=? ");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getValor1());
		pstm.setString(3, obj.getValor2());
		pstm.setString(4, obj.getValor3());
		pstm.setString(5, obj.getArticulo());
		pstm.setString(6, obj.getTipo());
		pstm.setString(7, obj.getSituacion());
		pstm.setString(8, obj.getUsu_mod());
		pstm.setInt(9, obj.getFecha_mod());
		pstm.setInt(10, obj.getId());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
}
