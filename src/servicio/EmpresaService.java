package servicio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import bean.Empresa;

public interface EmpresaService {

	public List<Empresa> listar() throws SQLException;
	public Empresa buscraByPk(String codigo) throws SQLException;
	public Connection verifica(String cia);
}
