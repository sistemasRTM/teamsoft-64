package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.LibroKardexDAO;
import bean.LibroKardex;

public class LibroKardexServiceImpl implements LibroKardexService{

	LibroKardexDAO libroKardexDAO = new LibroKardexDAO();
	
	@Override
	public List<LibroKardex> getLibroKardex(int ejercicio) throws SQLException {
		return libroKardexDAO.getLibroKardex(ejercicio);
	}

}
