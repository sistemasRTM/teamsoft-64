package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.OperacionDAO;
import bean.Operacion;

public class OperacionServiceImpl implements OperacionService{
	
	OperacionDAO operacionDAO = new OperacionDAO();
	
	@Override
	public int insertar(Operacion operacion) throws SQLException {
		return operacionDAO.insertar(operacion);
	}
	
	@Override
	public int modificar(Operacion operacion) throws SQLException {
		return operacionDAO.modificar(operacion);
	}
	
	@Override
	public int eliminar(int codigo) throws SQLException {
		
		return operacionDAO.eliminar(codigo);
	}
	
	@Override
	public List<Operacion> buscar(Operacion operacion) throws SQLException {
		return operacionDAO.buscar(operacion);
	}

	@Override
	public List<Operacion> listar() throws SQLException {

		return operacionDAO.listar();
	}

}
