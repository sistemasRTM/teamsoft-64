package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TREGOPDAO;
import bean.TREGOP;

public class TREGOPServiceImpl implements TREGOPService{
	TREGOPDAO tregopDAO = new TREGOPDAO();
	@Override
	public List<TREGOP> listar() throws SQLException {
		return tregopDAO.listar();
	}

	@Override
	public List<TREGOP> buscarPorCodigo(String codigo, String situacion)
			throws SQLException {
		return tregopDAO.buscarPorCodigo(codigo, situacion);
	}

	@Override
	public List<TREGOP> buscarPorCodigo(String codigo) throws SQLException {
		return tregopDAO.buscarPorCodigo(codigo);
	}

	@Override
	public int insertar(TREGOP obj) throws SQLException {
		return tregopDAO.insertar(obj);
	}

	@Override
	public int modificar(TREGOP obj) throws SQLException {
		return tregopDAO.modificar(obj);
	}

}
