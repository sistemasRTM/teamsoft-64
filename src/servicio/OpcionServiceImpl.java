package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.OpcionDAO;
import bean.Opcion;
import bean.SubOpcion;

public class OpcionServiceImpl implements OpcionService{
	OpcionDAO opcionDAO = new OpcionDAO();

	@Override
	public int insertar(Opcion opcion) throws SQLException {
		
		return opcionDAO.insertar(opcion);
	}

	@Override
	public int modificar(Opcion opcion) throws SQLException {

		return opcionDAO.modificar(opcion);
	}

	@Override
	public int eliminar(int codigo) throws SQLException {

		return opcionDAO.eliminar(codigo);
	}

	@Override
	public List<Opcion> buscar(Opcion opcion) throws SQLException {

		return opcionDAO.buscar(opcion);
	}

	@Override
	public List<Opcion> listar() throws SQLException {

		return opcionDAO.listar();
	}

	@Override
	public int guardarDetalleOpcionSubOpcion(Opcion opcion,
			List<SubOpcion> subOpciones) throws SQLException {

		return opcionDAO.guardarDetalleOpcionSubOpcion(opcion,subOpciones);
	}
	

}
