package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TASDCDAO;
import bean.TASDC;

public class TASDCServiceImpl implements TASDCService{
	TASDCDAO tasdcDAO = new TASDCDAO();
	@Override
	public List<TASDC> listar() throws SQLException {
		return tasdcDAO.listar();
	}

	@Override
	public List<TASDC> buscarPorSituacion(String codigo, String situacion)
			throws SQLException {
		return tasdcDAO.buscarPorSituacion(codigo, situacion);
	}

	@Override
	public int insertar(TASDC obj) throws SQLException {
		return tasdcDAO.insertar(obj);
	}

	@Override
	public int modificar(TASDC obj) throws SQLException {
		return tasdcDAO.modificar(obj);
	}

	@Override
	public List<TASDC> buscarPorCodigo(String codigo,String abreviado) throws SQLException {
		return tasdcDAO.buscarPorCodigo(codigo,abreviado);
	}

}
