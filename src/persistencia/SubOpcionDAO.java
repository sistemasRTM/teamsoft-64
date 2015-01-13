package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.Sesion;
import util.Conexion;
import bean.Operacion;
import bean.SubOpcion;

public class SubOpcionDAO {

	public int insertar(SubOpcion subOpcion) throws SQLException {
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"SELECT NEXTVAL FOR "+Sesion.bdProd+"CODSUBOPC FROM SYSIBM.SYSDUMMY1");
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int codigo = rs.getInt(1);
		pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"insert into "+Sesion.bdProd+"TSUBOPC(cod_subopc,desc_subopc) values(?,?)");
		pstm.setInt(1, codigo);
		pstm.setString(2, subOpcion.getDescripcion());
		pstm.executeUpdate();
		
		rs.close();
		pstm.close();
		return codigo;
	}
	
	public int modificar(SubOpcion subOpcion) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"update "+Sesion.bdProd+"TSUBOPC set desc_subopc=? where cod_subopc=?");
		pstm.setString(1, subOpcion.getDescripcion());
		pstm.setInt(2,subOpcion.getCodigo() );
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public int eliminar(int codigo) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"delete from "+Sesion.bdProd+"TSUBOPC where cod_subopc=?");
		pstm.setInt(1,codigo);
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public List<SubOpcion> buscar(SubOpcion subOpcion) throws SQLException {
		SubOpcion objSubOpcion = null;
		List<SubOpcion> subOpciones = new ArrayList<SubOpcion>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TSUBOPC where desc_subopc like ?");
		pstm.setString(1, subOpcion.getDescripcion()+"%");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objSubOpcion = new SubOpcion();
			objSubOpcion.setCodigo(rs.getInt("cod_subopc"));
			objSubOpcion.setDescripcion(rs.getString("desc_subopc"));
			subOpciones.add(objSubOpcion);
		}
		rs.close();
		pstm.close();
		return subOpciones;
	}
	
	public List<SubOpcion> listar() throws SQLException {
		SubOpcion subOpcion = null;
		List<SubOpcion> subOpciones = new ArrayList<SubOpcion>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TSUBOPC");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			subOpcion = new SubOpcion();
			subOpcion.setCodigo(rs.getInt("cod_subopc"));
			subOpcion.setDescripcion(rs.getString("desc_subopc"));
			subOpciones.add(subOpcion);
		}
		rs.close();
		pstm.close();
		return subOpciones;
	}
	
	public int guardarDetalleSubOpcionOperacion(SubOpcion subOpcion,List<Operacion> operaciones) throws SQLException{
		int resultado = 0;
		//Conexion.obtenerConexion().setAutoCommit(false);
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("delete from "+Sesion.bdProd+"TDETA_OPR_SUBOPC where cod_subopc=?");
		pstm.setInt(1, subOpcion.getCodigo());
		pstm.executeUpdate();
		
		pstm = Conexion
				.obtenerConexion()
				.prepareStatement("insert into "+Sesion.bdProd+"TDETA_OPR_SUBOPC(COD_SUBOPC,COD_OPR)values(?,?)");
		pstm.setInt(1, subOpcion.getCodigo());
		for (Operacion operacion : operaciones) {
			pstm.setInt(2, operacion.getCodigo());
			pstm.executeUpdate();
			resultado++;
		}
		//Conexion.obtenerConexion().setAutoCommit(true);
		pstm.close();
		return resultado;
	}
}
