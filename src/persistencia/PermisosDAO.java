package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos.Sesion;
import util.Conexion;
import bean.Modulo;
import bean.Opcion;
import bean.Operacion;
import bean.SubOpcion;

public class PermisosDAO {

	public List<Modulo> listarModulos() throws SQLException {
		List<Modulo> modulos = new ArrayList<Modulo>();
		Modulo modulo = null;
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("select distinct COD_MOD from "+Sesion.bdProd+"TPERMISOS_EMPLEADO where upper(USU_EMP)=? and upper(ciacve)=? and estado_mod='true'");
		pstm.setString(1, Sesion.usuario.toUpperCase().trim());
		pstm.setString(2, Sesion.cia.toUpperCase().trim());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			modulo = new Modulo();
			modulo.setCodigo(rs.getInt("COD_MOD"));
			modulos.add(modulo);
		}
		rs.close();
		pstm.close();
		return modulos;
	}

	public List<Opcion> listarOpciones(int modulo) throws SQLException {
		List<Opcion> opciones = new ArrayList<Opcion>();
		Opcion opcion = null;
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("select distinct COD_OPC from "+Sesion.bdProd+"TPERMISOS_EMPLEADO where upper(USU_EMP)=? and upper(ciacve)=? and COD_MOD=? and ESTADO_OPC='true'");
		pstm.setString(1, Sesion.usuario.toUpperCase());
		pstm.setString(2, Sesion.cia.toUpperCase().trim());
		pstm.setInt(3, modulo);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			opcion = new Opcion();
			opcion.setCodigo(rs.getInt("COD_OPC"));
			opciones.add(opcion);
		}
		rs.close();
		pstm.close();
		return opciones;
	}

	public List<SubOpcion> listarSubOpciones(int modulo) throws SQLException {
		List<SubOpcion> subOpciones = new ArrayList<SubOpcion>();
		SubOpcion subOpcion = null;
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("select distinct COD_SUBOPC from "+Sesion.bdProd+"TPERMISOS_EMPLEADO where upper(USU_EMP)=? and upper(ciacve)=? and COD_MOD=? and ESTADO_SUBOPC='true'");
		pstm.setString(1, Sesion.usuario.toUpperCase());
		pstm.setString(2, Sesion.cia.toUpperCase().trim());
		pstm.setInt(3, modulo);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			subOpcion = new SubOpcion();
			subOpcion.setCodigo(rs.getInt("COD_SUBOPC"));
			subOpciones.add(subOpcion);
		}
		rs.close();
		pstm.close();
		return subOpciones;
	}

	public List<Operacion> listarOperaciones(int modulo,int opcion,int subopcion) throws SQLException {
		List<Operacion> operaciones = new ArrayList<Operacion>();
		Operacion operacion = null;
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("select distinct COD_OPR from "+Sesion.bdProd+"TPERMISOS_EMPLEADO where upper(USU_EMP)=? and ESTADO_OPR='true'");
		pstm.setString(1, Sesion.usuario.toUpperCase());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			operacion = new Operacion();
			operacion.setCodigo(rs.getInt("COD_OPR"));
			operaciones.add(operacion);
		}
		rs.close();
		pstm.close();
		return operaciones;
	}
	
	
}
