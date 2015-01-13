package servicio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import persistencia.EmpresaDAO;
import bean.Empresa;

public class EmpresaServiceImpl implements EmpresaService{
	
	EmpresaDAO empresaDAO = new EmpresaDAO();
	
	@Override
	public List<Empresa> listar() throws SQLException {
		
		return empresaDAO.listar();
	}

	@Override
	public Empresa buscraByPk(String codigo) throws SQLException {

		return empresaDAO.buscarByPK(codigo);
	}

	@Override
	public Connection verifica(String cia) {

		return empresaDAO.verifica(cia);
	}

}
