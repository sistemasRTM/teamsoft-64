package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TASDTDAO;
import bean.TASDT;

public class TASDTServiceImpl implements TASDTService{
	TASDTDAO tasdtDAO = new TASDTDAO();
	@Override
	public List<TASDT> listar() throws SQLException {
		return tasdtDAO.listar();
	}

	@Override
	public List<TASDT> buscarPorCodigo(String codigo, String situacion)
			throws SQLException {
		return tasdtDAO.buscarPorCodigo(codigo, situacion);
	}

	@Override
	public List<TASDT> buscarPorCodigo(String codigo, String abreviado,
			String movimiento, String tipomov) throws SQLException {
		return tasdtDAO.buscarPorCodigo(codigo, abreviado, movimiento, tipomov);
	}

	@Override
	public int insertar(TASDT obj) throws SQLException {
		return tasdtDAO.insertar(obj);
	}

	@Override
	public int modificar(TASDT obj) throws SQLException {
		return tasdtDAO.modificar(obj);
	}

}
