package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TESCOM;

public interface TESCOMService {

	public List<TESCOM> listar() throws SQLException;
	public List<TESCOM> buscarPorCodigo(String codigo,String situacion) throws SQLException;
	public List<TESCOM> buscarPorCodigo(String codigo,String almacen,String vacio) throws SQLException;
	public int insertar(TESCOM obj) throws SQLException;
	public int modificar(TESCOM obj) throws SQLException;
}
