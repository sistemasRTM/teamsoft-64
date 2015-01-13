package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TREGOP;

public interface TREGOPService {
	public List<TREGOP> listar() throws SQLException;
	public List<TREGOP> buscarPorCodigo(String codigo,String situacion) throws SQLException;
	public List<TREGOP> buscarPorCodigo(String codigo) throws SQLException;
	public int insertar(TREGOP obj) throws SQLException;
	public int modificar(TREGOP obj) throws SQLException;
}
