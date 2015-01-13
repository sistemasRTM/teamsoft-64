package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.Modulo;
import bean.Perfil;

public interface PerfilService {
	public int insertar(Perfil perfil) throws SQLException;
	public int modificar(Perfil perfil)throws SQLException;
	public int eliminar(int codigo)throws SQLException;
	public List<Perfil> buscar(Perfil perfil)throws SQLException;
	public List<Perfil> listar()throws SQLException;
	
	public int guardarDetallePerfilModulo(Perfil perfil,List<Modulo> modulos)throws SQLException;
}
