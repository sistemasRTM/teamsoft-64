package servicio;
import java.sql.SQLException;
import java.util.List;
import bean.Empleado;
import bean.Modulo;
import bean.Opcion;
import bean.Operacion;
import bean.SubOpcion;

public interface LoginService {

	public String verifica(Empleado objEmpleado);
	public List<Modulo> listarModulos() throws SQLException;
	public List<Opcion> listarOpciones(int modulo) throws SQLException;
	public List<SubOpcion> listarSubOpciones(int modulo) throws SQLException;
	public List<Operacion> listarOperaciones(int modulo,int opcion,int subopcion) throws SQLException;
}
