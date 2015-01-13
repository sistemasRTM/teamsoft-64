package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos.Sesion;
import util.Conexion;
import bean.TASDC;

public class TASDCDAO {
	
	public List<TASDC> listar() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,documento,abr,situacion from "+Sesion.bdProd+"TASDC");
		ResultSet rs = pstm.executeQuery();
		List<TASDC> listado = new ArrayList<TASDC>();
		while(rs.next()){
			TASDC tasdc = new TASDC();
			tasdc.setId(rs.getInt("id"));
			tasdc.setCodigo(rs.getString("codigo"));
			tasdc.setDocumento(rs.getString("documento"));
			tasdc.setAbreviado(rs.getString("abr"));
			tasdc.setSituacion(rs.getString("situacion"));
			listado.add(tasdc);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TASDC> buscarPorSituacion(String codigo,String situacion) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,documento,abr,situacion from "+Sesion.bdProd+"TASDC where codigo like ? and situacion in ("+situacion+")");
		pstm.setString(1, codigo + "%");
		ResultSet rs = pstm.executeQuery();
		List<TASDC> listado = new ArrayList<TASDC>();
		while(rs.next()){
			TASDC tasdc = new TASDC();
			tasdc.setId(rs.getInt("id"));
			tasdc.setCodigo(rs.getString("codigo"));
			tasdc.setDocumento(rs.getString("documento"));
			tasdc.setAbreviado(rs.getString("abr"));
			tasdc.setSituacion(rs.getString("situacion"));
			listado.add(tasdc);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TASDC> buscarPorCodigo(String codigo,String abreviado) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,documento,abr,situacion from "+Sesion.bdProd+"TASDC where codigo=? and abr=?");
		pstm.setString(1, codigo);
		pstm.setString(2, abreviado);
		ResultSet rs = pstm.executeQuery();
		List<TASDC> listado = new ArrayList<TASDC>();
		while(rs.next()){
			TASDC tasdc = new TASDC();
			tasdc.setId(rs.getInt("id"));
			tasdc.setCodigo(rs.getString("codigo"));
			tasdc.setDocumento(rs.getString("documento"));
			tasdc.setAbreviado(rs.getString("abr"));
			tasdc.setSituacion(rs.getString("situacion"));
			listado.add(tasdc);
		}
		rs.close();
		pstm.close();
		return listado;
	}

	public int insertar(TASDC obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into "+Sesion.bdProd+"TASDC (id,codigo,documento,abr,situacion,usu_crea,fech_crea) values (NEXT VALUE FOR " + Sesion.bdProd+"CODTASDC,?,?,?,?,?,?)");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getDocumento());
		pstm.setString(3, obj.getAbreviado());
		pstm.setString(4, obj.getSituacion());
		pstm.setString(5, obj.getUsu_crea());
		pstm.setInt(6, obj.getFecha_crea());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int modificar(TASDC obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"update "+Sesion.bdProd+"TASDC set codigo=?,documento=?,abr=?,situacion=?,usu_mod=?,fech_mod=? where id=? ");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getDocumento());
		pstm.setString(3, obj.getAbreviado());
		pstm.setString(4, obj.getSituacion());
		pstm.setString(5, obj.getUsu_mod());
		pstm.setInt(6, obj.getFecha_mod());
		pstm.setInt(7, obj.getId());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
}
