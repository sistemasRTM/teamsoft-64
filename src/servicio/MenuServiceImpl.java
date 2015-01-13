package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.MenuDAO;

import bean.Menu;
import bean.Opcion;

public class MenuServiceImpl implements MenuService{

	MenuDAO menuDAO = new MenuDAO();

	@Override
	public int insertar(Menu menu) throws SQLException {
		
		return menuDAO.insertar(menu);
	}

	@Override
	public int modificar(Menu menu) throws SQLException {
	
		return menuDAO.modificar(menu);
	}

	@Override
	public int eliminar(int codigo) throws SQLException {
	
		return menuDAO.eliminar(codigo);
	}

	@Override
	public List<Menu> buscar(Menu menu) throws SQLException {
	
		return menuDAO.buscar(menu);
	}

	@Override
	public List<Menu> listar() throws SQLException {
		
		return menuDAO.listar();
	}

	@Override
	public int guardarDetalleMenuOpcion(Menu menu, List<Opcion> opciones)
			throws SQLException {

		return menuDAO.guardarDetalleMenuOpcion(menu,opciones);
	}
	
	
}
