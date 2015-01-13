package persistencia;

import util.Conexion;
import bean.Empleado;

public class LoginDAO {
	
	public String verifica(Empleado objEmpleado) {
		return  Conexion.obtenerConexion(objEmpleado);
	}

}
