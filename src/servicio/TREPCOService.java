package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TREPCO;

public interface TREPCOService {

	public List<TREPCO> listar() throws SQLException;
	public List<TREPCO> buscarPorCodigo(String codigo,String situacion) throws SQLException;
	public List<TREPCO> buscarPorCodigo(String codigo) throws SQLException;
	public int insertar(TREPCO obj) throws SQLException;
	public int modificar(TREPCO obj) throws SQLException;
	public TREPCO obtenerEquivalencia(String val1,String val2,String val3) throws SQLException;
}
