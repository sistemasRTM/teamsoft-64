package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TASDC;

public interface TASDCService {
	public List<TASDC> listar() throws SQLException;
	public List<TASDC> buscarPorSituacion(String codigo,String situacion) throws SQLException;
	public List<TASDC> buscarPorCodigo(String codigo,String abreviado) throws SQLException;
	public int insertar(TASDC obj) throws SQLException;
	public int modificar(TASDC obj) throws SQLException;
}
