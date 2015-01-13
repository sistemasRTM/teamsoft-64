package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TECCDAO;
import bean.TECC;

public class MantenimientoExcepcionCriterioComisionServiceImpl implements MantenimientoExcepcionCriterioComisionService{
	
	TECCDAO teccDAO = new TECCDAO();
	
	@Override
	public List<TECC> listar() throws SQLException {
		return teccDAO.listar();
	}

	@Override
	public List<TECC> buscarPorCriterio(TECC tecc) throws SQLException {
		
		return teccDAO.buscarPorCriterio(tecc);
	}

	@Override
	public int eliminar(String criterio) throws SQLException {

		return teccDAO.eliminar(criterio);
	}

	@Override
	public int insertar(TECC tecc) throws SQLException {

		return teccDAO.insertar(tecc);
	}

	
}
