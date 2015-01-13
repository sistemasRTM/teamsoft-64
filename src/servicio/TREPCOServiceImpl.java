package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TREPCODAO;
import bean.TREPCO;

public class TREPCOServiceImpl implements TREPCOService{
	TREPCODAO trepcDAO = new TREPCODAO();
	@Override
	public List<TREPCO> listar() throws SQLException {
		return trepcDAO.listar();
	}

	@Override
	public List<TREPCO> buscarPorCodigo(String codigo, String situacion)
			throws SQLException {
		return trepcDAO.buscarPorCodigo(codigo, situacion);
	}

	@Override
	public List<TREPCO> buscarPorCodigo(String codigo) throws SQLException {
		return trepcDAO.buscarPorCodigo(codigo);
	}

	@Override
	public int insertar(TREPCO obj) throws SQLException {
		return trepcDAO.insertar(obj);
	}

	@Override
	public int modificar(TREPCO obj) throws SQLException {
		return trepcDAO.modificar(obj);
	}

	@Override
	public TREPCO obtenerEquivalencia(String val1, String val2, String val3)
			throws SQLException {
		return trepcDAO.obtenerEquivalencia(val1, val2, val3);
	}

}
