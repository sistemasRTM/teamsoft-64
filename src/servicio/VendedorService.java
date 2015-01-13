package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.Vendedor;

public interface VendedorService {
	
	public List<Vendedor> listar() throws SQLException;

}
