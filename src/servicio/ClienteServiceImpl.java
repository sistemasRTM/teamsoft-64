package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.ClienteDAO;

import bean.Cliente;

public class ClienteServiceImpl implements ClienteService{
	ClienteDAO clienteDAO = new ClienteDAO();
	@Override
	public List<Cliente> buscraByPk(String codigo) throws SQLException {
		
		return clienteDAO.buscraByPk(codigo);
	}
	@Override
	public void registrarCliente(Cliente cliente) throws SQLException {

		clienteDAO.registrarCliente(cliente);
	}
	@Override
	public void modificarCliente(Cliente cliente) throws SQLException {
		
		clienteDAO.modificarCliente(cliente);
		
	}
	@Override
	public Cliente verificaCliente(Cliente listacliente) throws SQLException {
		
		return clienteDAO.verificaCliente(listacliente);
		
	}

}
