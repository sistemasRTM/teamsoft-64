package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TCCMDAO;
import recursos.MaestroBean;
import bean.TCCM;

public class MantenimientoCriteriosComisionServiceImpl implements MantenimientoCriteriosComisionService{
	TCCMDAO tccmDAO = new TCCMDAO();
	
	@Override
	public List<TCCM> listar() throws SQLException {
		return tccmDAO.listar();
	}

	@Override
	public List<TCCM> buscarPorFamilia(TCCM tccm) throws SQLException {
		return tccmDAO.buscarPorFamilia(tccm);
	}

	@Override
	public List<TCCM> buscarPorFamYSubFam(TCCM tccm) throws SQLException {
		return tccmDAO.buscarPorFamYSubFam(tccm);
	}
	
	@Override
	public List<TCCM> buscarPorFamYSubFamyOri(TCCM tccm) throws SQLException {
		return tccmDAO.buscarPorFamYSubFamyOri(tccm);
	}

	@Override
	public int insertar(TCCM tccm) throws SQLException {
		return tccmDAO.insertar(tccm);
	}

	@Override
	public int modificar(TCCM tccm) throws SQLException {
		return tccmDAO.modificar(tccm);
	}
	
	@Override
	public TCCM verificar(TCCM tccm) throws SQLException {
		return tccmDAO.verificar(tccm);
	}
	
	public List<MaestroBean> listarSubFamilias(TCCM tccm) throws SQLException {
		return tccmDAO.listarSubFamilias(tccm);
	}

	@Override
	public List<TCCM> listarCriteriosParaExcepcion() throws SQLException {
		return tccmDAO.listarCriteriosParaExcepcion();
	}

	@Override
	public int eliminar(String codigo) throws SQLException {
		return tccmDAO.eliminar(codigo);
	}

	@Override
	public List<TCCM> buscarCriteriosParaExcepcionPorFamilia(TCCM tccm)
			throws SQLException {
		
		return tccmDAO.buscarCriteriosParaExcepcionPorFamilia(tccm);
	}

	@Override
	public List<MaestroBean> listarOrigenes(TCCM tccm) throws SQLException {
	
		return tccmDAO.listarOrigenes(tccm);
	}

}
