package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.Sesion;
import util.Conexion;
import bean.Menu;
import bean.Opcion;

public class MenuDAO {

	public int insertar(Menu menu) throws SQLException {
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"SELECT NEXTVAL FOR "+Sesion.bdProd+"CODMENU FROM SYSIBM.SYSDUMMY1");
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int codigo = rs.getInt(1);
		pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"insert into "+Sesion.bdProd+"TMENU(cod_menu,desc_menu) values(?,?)");
		pstm.setInt(1, codigo);
		pstm.setString(2, menu.getDescripcion());
		pstm.executeUpdate();
		
		rs.close();
		pstm.close();

		return codigo;
	}
	
	public int modificar(Menu menu) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"update "+Sesion.bdProd+"TMENU set desc_menu=? where cod_menu=?");
		pstm.setString(1, menu.getDescripcion());
		pstm.setInt(2,menu.getCodigo() );
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public int eliminar(int codigo) throws SQLException {
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement(
						"delete from "+Sesion.bdProd+"TMENU where cod_menu=?");
		pstm.setInt(1,codigo);
		int cantidad = pstm.executeUpdate();
		pstm.close();
		return cantidad;
	}
	
	public List<Menu> buscar(Menu menu) throws SQLException {
		Menu objMenu = null;
		List<Menu> menus = new ArrayList<Menu>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TMENU where desc_menu like ?");
		pstm.setString(1, menu.getDescripcion()+"%");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			objMenu = new Menu();
			objMenu.setCodigo(rs.getInt("cod_menu"));
			objMenu.setDescripcion(rs.getString("desc_menu"));
			menus.add(objMenu);
		}
		rs.close();
		pstm.close();
		return menus;
	}
	
	public List<Menu> listar() throws SQLException {
		Menu menu = null;
		List<Menu> menus = new ArrayList<Menu>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select * from "+Sesion.bdProd+"TMENU");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			menu = new Menu();
			menu.setCodigo(rs.getInt("cod_menu"));
			menu.setDescripcion(rs.getString("desc_menu"));
			menus.add(menu);
		}
		rs.close();
		pstm.close();
		return menus;
	}

	public int guardarDetalleMenuOpcion(Menu menu, List<Opcion> opciones) throws SQLException {
		int resultado = 0;
		//Conexion.obtenerConexion().setAutoCommit(false);
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("delete from "+Sesion.bdProd+"TDETA_MENU_OPC where cod_menu=?");
		pstm.setInt(1, menu.getCodigo());
		pstm.executeUpdate();
		
		pstm = Conexion
				.obtenerConexion()
				.prepareStatement("insert into "+Sesion.bdProd+"TDETA_MENU_OPC(COD_MENU,COD_OPC)values(?,?)");
		pstm.setInt(1, menu.getCodigo());
		for (Opcion opcion : opciones) {
			pstm.setInt(2, opcion.getCodigo());
			pstm.executeUpdate();
			resultado++;
		}
		//Conexion.obtenerConexion().setAutoCommit(true);
		pstm.close();
		return resultado;
	}

}
