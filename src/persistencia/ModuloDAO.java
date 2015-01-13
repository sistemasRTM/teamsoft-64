package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.Sesion;
import util.Conexion;
import bean.Menu;
import bean.Modulo;

public class ModuloDAO {
	public int insertar(Modulo modulo) throws SQLException{
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"SELECT NEXTVAL FOR "+Sesion.bdProd+"CODMODULO FROM SYSIBM.SYSDUMMY1");
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int codigo = rs.getInt(1);
		pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"insert into "+Sesion.bdProd+"TMODULO(cod_mod,desc_mod,cod_menu) values(?,?,?)");
		pstm.setInt(1, codigo);
		pstm.setString(2, modulo.getDescripcion());
		pstm.setInt(3, modulo.getMenu().getCodigo());
		pstm.executeUpdate();
		
		rs.close();
		pstm.close();
		return codigo;
	}
	public int modificar(Modulo modulo)throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"update "+Sesion.bdProd+"TMODULO set desc_mod=?,cod_menu=? where cod_mod=?");
		pstm.setString(1, modulo.getDescripcion());
		pstm.setInt(2, modulo.getMenu().getCodigo());
		pstm.setInt(3,modulo.getCodigo() );
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	public int eliminar(int codigo)throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"delete from "+Sesion.bdProd+"TMODULO where cod_mod=?");
		pstm.setInt(1,codigo);
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	public List<Modulo> buscar(Modulo modulo)throws SQLException{
		Modulo objModulo = null;
		List<Modulo> modulos = new ArrayList<Modulo>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TMODULO tm inner join "+Sesion.bdProd+"TMENU tmu on tm.cod_menu = tmu.cod_menu  where desc_mod like ?");
		pstm.setString(1, modulo.getDescripcion()+"%");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objModulo = new Modulo();
			objModulo.setCodigo(rs.getInt("cod_mod"));
			objModulo.setDescripcion(rs.getString("desc_mod"));
			Menu menu = new Menu();
			menu.setCodigo(rs.getInt("COD_MENU"));
			menu.setDescripcion(rs.getString("DESC_MENU"));
			objModulo.setMenu(menu);
			modulos.add(objModulo);
		}
		rs.close();
		pstm.close();
		return modulos;

	}
	public List<Modulo> listar()throws SQLException{
		Modulo modulo = null;
		List<Modulo> modulos = new ArrayList<Modulo>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TMODULO tm inner join "+Sesion.bdProd+"TMENU tmu on tm.cod_menu = tmu.cod_menu ");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			modulo = new Modulo();
			modulo.setCodigo(rs.getInt("cod_mod"));
			modulo.setDescripcion(rs.getString("desc_mod"));
			modulos.add(modulo);
			Menu menu = new Menu();
			menu.setCodigo(rs.getInt("COD_MENU"));
			menu.setDescripcion(rs.getString("DESC_MENU"));
			modulo.setMenu(menu);
			modulos.add(modulo);
		}
		rs.close();
		pstm.close();
		return modulos;
	
	}
}
