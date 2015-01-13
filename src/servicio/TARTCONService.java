package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TARTCON;

public interface TARTCONService {
	public List<TARTCON> listar() throws SQLException;
	public List<TARTCON> buscarPorCodigo(String codigo,String situacion) throws SQLException;
	public List<TARTCON> buscarPorCodigo(String codigo,String articulo,String vacio) throws SQLException;
	public int insertar(TARTCON obj) throws SQLException;
	public int modificar(TARTCON obj) throws SQLException;
}
