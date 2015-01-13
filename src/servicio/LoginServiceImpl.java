package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.Empleado;
import bean.Modulo;
import bean.Opcion;
import bean.Operacion;
import bean.SubOpcion;
import persistencia.AuditoriaDAO;
import persistencia.LoginDAO;
import persistencia.PermisosDAO;

public class LoginServiceImpl implements LoginService{
	
	LoginDAO loginDAO = new LoginDAO(); 
	AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
	PermisosDAO permisoDAO = new PermisosDAO();
	
	@Override
	public String verifica(Empleado objEmpleado) {
	
		return loginDAO.verifica(objEmpleado);
	}

	@Override
	public List<Modulo> listarModulos() throws SQLException {

		return permisoDAO.listarModulos();
	}

	@Override
	public List<Opcion> listarOpciones(int modulo) throws SQLException {
	
		return permisoDAO.listarOpciones(modulo);
	}

	@Override
	public List<SubOpcion> listarSubOpciones(int modulo) throws SQLException {

		return permisoDAO.listarSubOpciones(modulo);
	}

	@Override
	public List<Operacion> listarOperaciones(int modulo,int opcion,int subopcion) throws SQLException {
		
		return permisoDAO.listarOperaciones(modulo,opcion,subopcion);
	}

	
}
