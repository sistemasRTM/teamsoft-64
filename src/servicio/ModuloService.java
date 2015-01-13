package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.Modulo;

public interface ModuloService {
	public int insertar(Modulo modulo) throws SQLException;
	public int modificar(Modulo modulo)throws SQLException;
	public int eliminar(int codigo)throws SQLException;
	public List<Modulo> buscar(Modulo modulo)throws SQLException;
	public List<Modulo> listar()throws SQLException;
}
