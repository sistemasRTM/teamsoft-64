package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TALIASDAO;
import persistencia.TREGCDAO;
import bean.TREGC;
import bean.TALIAS;


public class ComprasServiceImpl implements ComprasService{
	
	TREGCDAO tregcDAO = new TREGCDAO();//tabla registro de compras listo(lista principal a usar en el proceso del reporte)
	TALIASDAO taliasDAO = new TALIASDAO();//tabla de alias listo(se usa unsa sola vez por proceso)
	
	@Override
	public List<TREGC> listarTREGC(TREGC tregc) throws SQLException {
		return tregcDAO.listarTREGC(tregc);
	}

	@Override
	public TALIAS listarTALIAS() throws SQLException {
		
		return taliasDAO.listarTALIAS();
	}

	@Override
	public List<TREGC> listarTREGCByEjercicio(TREGC tregc) throws SQLException {
		return tregcDAO.listarTREGCByEjercicio(tregc);
	}
}
