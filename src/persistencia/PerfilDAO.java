package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.Sesion;
import bean.Modulo;
import bean.Perfil;
import util.Conexion;

public class PerfilDAO {

	public int insertar(Perfil perfil) throws SQLException {
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"SELECT NEXTVAL FOR "+Sesion.bdProd+"CODPERFIL FROM SYSIBM.SYSDUMMY1");
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int codigo = rs.getInt(1);
		pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"insert into "+Sesion.bdProd+"TPERFIL(cod_perfil,desc_perfil) values(?,?)");
		pstm.setInt(1, codigo);
		pstm.setString(2, perfil.getDescripcion());
		pstm.executeUpdate();

		rs.close();
		pstm.close();
		return codigo;
	}
	
	public int modificar(Perfil perfil) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"update "+Sesion.bdProd+"TPERFIL set desc_perfil=? where cod_perfil=?");
		pstm.setString(1, perfil.getDescripcion());
		pstm.setInt(2,perfil.getCodigo() );
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public int eliminar(int codigo) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"delete from "+Sesion.bdProd+"TPERFIL where cod_perfil=?");
		pstm.setInt(1,codigo);
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public List<Perfil> buscar(Perfil perfil) throws SQLException {
		Perfil objPerfil = null;
		List<Perfil> perfiles = new ArrayList<Perfil>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TPERFIL where desc_perfil like ?");
		pstm.setString(1, perfil.getDescripcion()+"%");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objPerfil = new Perfil();
			objPerfil.setCodigo(rs.getInt("cod_perfil"));
			objPerfil.setDescripcion(rs.getString("desc_perfil"));
			perfiles.add(objPerfil);
		}
		rs.close();
		pstm.close();
		return perfiles;
	}
	
	public List<Perfil> listar() throws SQLException {
		Perfil perfil = null;
		List<Perfil> perfiles = new ArrayList<Perfil>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TPERFIL");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			perfil = new Perfil();
			perfil.setCodigo(rs.getInt("cod_perfil"));
			perfil.setDescripcion(rs.getString("desc_perfil"));
			perfiles.add(perfil);
		}
		rs.close();
		pstm.close();
		return perfiles;
	}

	public int guardarDetallePerfilModulo(Perfil perfil, List<Modulo> modulos) throws SQLException{
		int resultado = 0;
		//Conexion.obtenerConexion().setAutoCommit(false);
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("delete from "+Sesion.bdProd+"TDETA_PER_MOD where cod_perfil=?");
		pstm.setInt(1, perfil.getCodigo());
		pstm.executeUpdate();
		
		pstm = Conexion
				.obtenerConexion()
				.prepareStatement("insert into "+Sesion.bdProd+"TDETA_PER_MOD(COD_PERFIL,COD_MOD)values(?,?)");
		pstm.setInt(1, perfil.getCodigo());
		for (Modulo modulo : modulos) {
			pstm.setInt(2, modulo.getCodigo());
			pstm.executeUpdate();
			resultado++;
		}
		//Conexion.obtenerConexion().setAutoCommit(true);
		pstm.close();
		return resultado;
	}
	
}
