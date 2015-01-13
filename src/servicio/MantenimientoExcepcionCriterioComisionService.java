package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.TECC;

public interface MantenimientoExcepcionCriterioComisionService {

	public List<TECC> listar() throws SQLException;
	public List<TECC> buscarPorCriterio(TECC tecc) throws SQLException;
	public int eliminar(String criterio)throws SQLException;
	public int insertar(TECC tecc)throws SQLException;
}
