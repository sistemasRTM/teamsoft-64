package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.SubOpcionDAO;
import bean.Operacion;
import bean.SubOpcion;

public class SubOpcionServiceImpl implements SubOpcionService{

	SubOpcionDAO subOpcionDAO = new SubOpcionDAO();
	
	@Override
	public int insertar(SubOpcion subOpcion) throws SQLException {
		
		return subOpcionDAO.insertar(subOpcion);
	}

	@Override
	public int modificar(SubOpcion subOpcion) throws SQLException {

		return subOpcionDAO.modificar(subOpcion);
	}

	@Override
	public int eliminar(int codigo) throws SQLException {

		return subOpcionDAO.eliminar(codigo);
	}

	@Override
	public List<SubOpcion> buscar(SubOpcion subOpcion) throws SQLException {

		return subOpcionDAO.buscar(subOpcion);
	}

	@Override
	public List<SubOpcion> listar() throws SQLException {

		return subOpcionDAO.listar();
	}

	@Override
	public int guardarDetalleSubOpcionOperacion(SubOpcion subOpcion,
			List<Operacion> operaciones) throws SQLException {
		return subOpcionDAO.guardarDetalleSubOpcionOperacion(subOpcion, operaciones);
	}

}
