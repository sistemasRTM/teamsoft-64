package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.TESCOM;
import recursos.Sesion;
import util.Conexion;

public class TESCOMDAO {

	public List<TESCOM> listar() throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,establecimiento,almacen,situacion from "+Sesion.bdProd+"TESCOM");
		ResultSet rs = pstm.executeQuery();
		List<TESCOM> listado = new ArrayList<TESCOM>();
		while(rs.next()){
			TESCOM tescom = new TESCOM();
			tescom.setId(rs.getInt("id"));
			tescom.setCodigo(rs.getString("codigo"));
			tescom.setEstablecimiento(rs.getString("establecimiento"));
			tescom.setAlmacen(rs.getString("almacen"));
			tescom.setSituacion(rs.getString("situacion"));
			listado.add(tescom);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TESCOM> buscarPorCodigo(String codigo,String situacion) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,establecimiento,almacen,situacion from "+Sesion.bdProd+"TESCOM where codigo like ? and situacion in ("+situacion+")");
		pstm.setString(1, codigo + "%");
		ResultSet rs = pstm.executeQuery();
		List<TESCOM> listado = new ArrayList<TESCOM>();
		while(rs.next()){
			TESCOM tescom = new TESCOM();
			tescom.setId(rs.getInt("id"));
			tescom.setCodigo(rs.getString("codigo"));
			tescom.setEstablecimiento(rs.getString("establecimiento"));
			tescom.setAlmacen(rs.getString("almacen"));
			tescom.setSituacion(rs.getString("situacion"));
			listado.add(tescom);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TESCOM> buscarPorCodigo(String codigo,String almacen,String vacio) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select id,codigo,establecimiento,almacen,situacion from "+Sesion.bdProd+"TESCOM where codigo = ? and almacen=?");
		pstm.setString(1, codigo);
		pstm.setString(2, almacen);
		ResultSet rs = pstm.executeQuery();
		List<TESCOM> listado = new ArrayList<TESCOM>();
		while(rs.next()){
			TESCOM tescom = new TESCOM();
			tescom.setId(rs.getInt("id"));
			tescom.setCodigo(rs.getString("codigo"));
			tescom.setEstablecimiento(rs.getString("establecimiento"));
			tescom.setAlmacen(rs.getString("almacen"));
			tescom.setSituacion(rs.getString("situacion"));
			listado.add(tescom);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public int insertar(TESCOM obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"insert into "+Sesion.bdProd+"TESCOM (id,codigo,establecimiento,almacen,situacion,usu_crea,fech_crea) values (NEXT VALUE FOR " + Sesion.bdProd+"CODTESCOM,?,?,?,?,?,?)");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getEstablecimiento());
		pstm.setString(3, obj.getAlmacen());
		pstm.setString(4, obj.getSituacion());
		pstm.setString(5, obj.getUsu_crea());
		pstm.setInt(6, obj.getFecha_crea());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
	public int modificar(TESCOM obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"update "+Sesion.bdProd+"TESCOM set codigo=?,establecimiento=?,almacen=?,situacion=?,usu_mod=?,fech_mod=? where id=? ");
		pstm.setString(1, obj.getCodigo());
		pstm.setString(2, obj.getEstablecimiento());
		pstm.setString(3, obj.getAlmacen());
		pstm.setString(4, obj.getSituacion());
		pstm.setString(5, obj.getUsu_mod());
		pstm.setInt(6, obj.getFecha_mod());
		pstm.setInt(7, obj.getId());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
	
}
