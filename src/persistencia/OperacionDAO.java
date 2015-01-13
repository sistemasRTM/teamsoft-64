package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.Sesion;
import util.Conexion;
import bean.Operacion;

public class OperacionDAO {

	public int insertar(Operacion operacion) throws SQLException {
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"SELECT NEXTVAL FOR "+Sesion.bdProd+"CODOPERACION FROM SYSIBM.SYSDUMMY1");
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int codigo = rs.getInt(1);
		pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"insert into "+Sesion.bdProd+"TOPERACION(cod_opr,desc_opr) values(?,?)");
		pstm.setInt(1, codigo);
		pstm.setString(2, operacion.getDescripcion());
		pstm.executeUpdate();
		
		rs.close();
		pstm.close();
		return codigo;
	}
	
	public int modificar(Operacion operacion) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"update "+Sesion.bdProd+"TOPERACION set desc_opr=? where cod_opr=?");
		pstm.setString(1, operacion.getDescripcion());
		pstm.setInt(2,operacion.getCodigo() );
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public int eliminar(int codigo) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"delete from "+Sesion.bdProd+"TOPERACION where cod_opr=?");
		pstm.setInt(1,codigo);
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public List<Operacion> buscar(Operacion operacion) throws SQLException {
		Operacion objOperacion = null;
		List<Operacion> operaciones = new ArrayList<Operacion>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TOPERACION where desc_opr like ?");
		pstm.setString(1, operacion.getDescripcion()+"%");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objOperacion = new Operacion();
			objOperacion.setCodigo(rs.getInt("cod_opr"));
			objOperacion.setDescripcion(rs.getString("desc_opr"));
			operaciones.add(objOperacion);
		}
		rs.close();
		pstm.close();
		return operaciones;
	}
	
	public List<Operacion> listar() throws SQLException {
		Operacion operacion = null;
		List<Operacion> operaciones = new ArrayList<Operacion>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TOPERACION");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			operacion = new Operacion();
			operacion.setCodigo(rs.getInt("cod_opr"));
			operacion.setDescripcion(rs.getString("desc_opr"));
			operaciones.add(operacion);
		}
		rs.close();
		pstm.close();
		return operaciones;
	}
	
}
