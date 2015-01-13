package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.PerfilDAO;

import bean.Modulo;
import bean.Perfil;

public class PerfilServiceImpl implements PerfilService{

	PerfilDAO perfilDAO = new PerfilDAO();
	
	@Override
	public int insertar(Perfil perfil) throws SQLException {
		
		return perfilDAO.insertar(perfil);
	}

	@Override
	public int modificar(Perfil perfil) throws SQLException {
	
		return perfilDAO.modificar(perfil);
	}

	@Override
	public int eliminar(int codigo) throws SQLException {
	
		return perfilDAO.eliminar(codigo);
	}

	@Override
	public List<Perfil> buscar(Perfil perfil) throws SQLException {

		return perfilDAO.buscar(perfil);
	}

	@Override
	public List<Perfil> listar() throws SQLException {
		
		return perfilDAO.listar();
	}

	@Override
	public int guardarDetallePerfilModulo(Perfil perfil, List<Modulo> modulos)
			throws SQLException {
		
		return perfilDAO.guardarDetallePerfilModulo(perfil,modulos);
	}

}
