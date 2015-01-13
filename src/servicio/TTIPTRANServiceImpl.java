package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TTIPTRANDAO;
import bean.TTIPTRAN;

public class TTIPTRANServiceImpl implements TTIPTRANService{
	TTIPTRANDAO ttiptranDAO = new TTIPTRANDAO();
	@Override
	public List<TTIPTRAN> listar() throws SQLException {
		return ttiptranDAO.listar();
	}

	@Override
	public List<TTIPTRAN> buscarPorCodigo(String codigo, String situacion)
			throws SQLException {
		return ttiptranDAO.buscarPorCodigo(codigo, situacion);
	}

	@Override
	public List<TTIPTRAN> buscarPorCodigo(String codigo,String movimiento,String tipomovimiento) throws SQLException {
		return ttiptranDAO.buscarPorCodigo(codigo,movimiento,tipomovimiento);
	}

	@Override
	public int insertar(TTIPTRAN obj) throws SQLException {
		return ttiptranDAO.insertar(obj);
	}

	@Override
	public int modificar(TTIPTRAN obj) throws SQLException {
		return ttiptranDAO.modificar(obj);
	}

}
