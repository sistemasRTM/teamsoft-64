package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.Operacion;;

public interface OperacionService {

	public int insertar(Operacion operacion) throws SQLException;
	public int modificar(Operacion operacion)throws SQLException;
	public int eliminar(int codigo)throws SQLException;
	public List<Operacion> buscar(Operacion operacion)throws SQLException;
	public List<Operacion> listar()throws SQLException;
}
