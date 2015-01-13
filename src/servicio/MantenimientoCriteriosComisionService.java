package servicio;

import java.sql.SQLException;
import java.util.List;

import recursos.MaestroBean;

import bean.TCCM;

public interface MantenimientoCriteriosComisionService {

	public List<TCCM> listar() throws SQLException;
	public List<TCCM> buscarPorFamilia(TCCM tccm) throws SQLException;
	public List<TCCM> buscarPorFamYSubFam(TCCM tccm) throws SQLException;
	public List<TCCM> buscarPorFamYSubFamyOri(TCCM tccm) throws SQLException;
	public int insertar(TCCM tccm) throws SQLException;
	public int modificar(TCCM tccm) throws SQLException;
	public int eliminar(String codigo) throws SQLException;
	public TCCM verificar(TCCM tccm) throws SQLException;
	public List<MaestroBean> listarSubFamilias(TCCM tccm) throws SQLException;
	public List<MaestroBean> listarOrigenes(TCCM tccm) throws SQLException;
	public List<TCCM> listarCriteriosParaExcepcion() throws SQLException;
	public List<TCCM> buscarCriteriosParaExcepcionPorFamilia(TCCM tccm) throws SQLException;
}
