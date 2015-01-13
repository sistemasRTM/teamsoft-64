package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TTIPTRAN;

public interface TTIPTRANService {

	public List<TTIPTRAN> listar() throws SQLException;
	public List<TTIPTRAN> buscarPorCodigo(String codigo,String situacion) throws SQLException;
	public List<TTIPTRAN> buscarPorCodigo(String codigo,String movimiento,String tipomovimiento) throws SQLException;
	public int insertar(TTIPTRAN obj) throws SQLException;
	public int modificar(TTIPTRAN obj) throws SQLException;
	
}
