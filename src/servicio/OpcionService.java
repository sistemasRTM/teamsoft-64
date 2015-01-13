package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.Opcion;
import bean.SubOpcion;

public interface OpcionService {
		
	public int insertar(Opcion opcion) throws SQLException;
	public int modificar(Opcion opcion)throws SQLException;
	public int eliminar(int codigo)throws SQLException;
	public List<Opcion> buscar(Opcion opcion)throws SQLException;
	public List<Opcion> listar()throws SQLException;
	
	public int guardarDetalleOpcionSubOpcion(Opcion opcion,List<SubOpcion> subOpciones)throws SQLException;
}
