package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.ModuloDAO;

import bean.Modulo;

public class ModuloServiceImpl implements ModuloService{
	ModuloDAO moduloDAO = new ModuloDAO();
	 
	@Override
	public int insertar(Modulo modulo) throws SQLException {
		
		return moduloDAO.insertar(modulo);
	}

	@Override
	public int modificar(Modulo modulo) throws SQLException {
	
		return moduloDAO.modificar(modulo);
	}

	@Override
	public int eliminar(int codigo) throws SQLException {
		
		return moduloDAO.eliminar(codigo);
	}

	@Override
	public List<Modulo> buscar(Modulo modulo) throws SQLException {

		return moduloDAO.buscar(modulo);
	}

	@Override
	public List<Modulo> listar() throws SQLException {
		
		return moduloDAO.listar();
	}

}
