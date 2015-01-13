package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.Cliente;

public interface ClienteService {

	public List<Cliente> buscraByPk(String codigo) throws SQLException;

	public void registrarCliente(Cliente cliente) throws SQLException;
	public void modificarCliente(Cliente cliente) throws SQLException;
	public Cliente verificaCliente(Cliente listacliente) throws SQLException;
	
}
