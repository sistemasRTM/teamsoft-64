package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.VendedorDAO;

import bean.Vendedor;

public class VendedorServiceImpl implements VendedorService {
	VendedorDAO vendedorDAO = new VendedorDAO();
	
	@Override
	public List<Vendedor> listar() throws SQLException {
		return vendedorDAO.listar();
	}

}
