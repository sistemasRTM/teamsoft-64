package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TASDT;

public interface TASDTService {

	public List<TASDT> listar() throws SQLException;
	public List<TASDT> buscarPorCodigo(String codigo,String situacion) throws SQLException;
	public List<TASDT> buscarPorCodigo(String codigo,String abreviado,String movimiento,String tipomov) throws SQLException;
	public int insertar(TASDT obj) throws SQLException;
	public int modificar(TASDT obj) throws SQLException;
	
}
