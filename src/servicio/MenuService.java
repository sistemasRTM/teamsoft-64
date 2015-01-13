package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.Menu;
import bean.Opcion;

public interface MenuService {

	public int insertar(Menu menu) throws SQLException;
	public int modificar(Menu menu)throws SQLException;
	public int eliminar(int codigo)throws SQLException;
	public List<Menu> buscar(Menu menu)throws SQLException;
	public List<Menu> listar()throws SQLException;
	
	public int guardarDetalleMenuOpcion(Menu menu,List<Opcion> opciones) throws SQLException;
}
