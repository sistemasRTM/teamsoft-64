package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.Operacion;
import bean.SubOpcion;
public interface SubOpcionService {
	
	public int insertar(SubOpcion subOpcion) throws SQLException;
	public int modificar(SubOpcion subOpcion)throws SQLException;
	public int eliminar(int codigo)throws SQLException;
	public List<SubOpcion> buscar(SubOpcion subOpcion)throws SQLException;
	public List<SubOpcion> listar()throws SQLException;
	
	public int guardarDetalleSubOpcionOperacion(SubOpcion subOpcion,List<Operacion> operaciones)throws SQLException;
}
