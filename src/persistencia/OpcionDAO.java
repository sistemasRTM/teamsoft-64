package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.Sesion;
import util.Conexion;
import bean.Opcion;
import bean.SubOpcion;

public class OpcionDAO {

	public int insertar(Opcion opcion) throws SQLException {
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"SELECT NEXTVAL FOR "+Sesion.bdProd+"CODOPCION FROM SYSIBM.SYSDUMMY1");
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int codigo = rs.getInt(1);
		pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"insert into "+Sesion.bdProd+"TOPCION(cod_opc,desc_opc) values(?,?)");
		pstm.setInt(1, codigo);
		pstm.setString(2, opcion.getDescripcion());
		pstm.executeUpdate();

		rs.close();
		pstm.close();
		return codigo;
	}
	
	public int modificar(Opcion opcion) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"update "+Sesion.bdProd+"TOPCION set desc_opc=? where cod_opc=?");
		pstm.setString(1, opcion.getDescripcion());
		pstm.setInt(2,opcion.getCodigo() );
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public int eliminar(int codigo) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"delete from "+Sesion.bdProd+"TOPCION where cod_opc=?");
		pstm.setInt(1,codigo);
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public List<Opcion> buscar(Opcion opcion) throws SQLException {
		Opcion objOpcion = null;
		List<Opcion> opciones = new ArrayList<Opcion>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TOPCION where desc_opc like ?");
		pstm.setString(1, opcion.getDescripcion()+"%");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objOpcion = new Opcion();
			objOpcion.setCodigo(rs.getInt("cod_opc"));
			objOpcion.setDescripcion(rs.getString("desc_opc"));
			opciones.add(objOpcion);
		}
		rs.close();
		pstm.close();
		return opciones;
	}
	
	public List<Opcion> listar() throws SQLException {
		Opcion opcion = null;
		List<Opcion> opciones = new ArrayList<Opcion>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TOPCION");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			opcion = new Opcion();
			opcion.setCodigo(rs.getInt("cod_opc"));
			opcion.setDescripcion(rs.getString("desc_opc"));
			opciones.add(opcion);
		}
		rs.close();
		pstm.close();
		return opciones;
	}

	public int guardarDetalleOpcionSubOpcion(Opcion opcion,
			List<SubOpcion> subOpciones) throws SQLException {
		int resultado = 0;
		//Conexion.obtenerConexion().setAutoCommit(false);
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("delete from "+Sesion.bdProd+"TDETA_OPC_SUBOPC where cod_opc=?");
		pstm.setInt(1, opcion.getCodigo());
		pstm.executeUpdate();
		
		pstm = Conexion
				.obtenerConexion()
				.prepareStatement("insert into "+Sesion.bdProd+"TDETA_OPC_SUBOPC(COD_OPC,COD_SUBOPC)values(?,?)");
		pstm.setInt(1, opcion.getCodigo());
		for (SubOpcion subOpcion : subOpciones) {
			pstm.setInt(2, subOpcion.getCodigo());
			pstm.executeUpdate();
			resultado++;
		}
		//Conexion.obtenerConexion().setAutoCommit(true);
		pstm.close();
		return resultado;
	}
}
