package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.TCLIEDAO;
import persistencia.TCTXCDDAO;
import bean.TCLIE;
import bean.TCTXCD;

public class TCTXCDServiceImpl implements TCTXCDService{

	TCTXCDDAO tctxcdDAO = new TCTXCDDAO(); 
	TCLIEDAO tclieDAO = new TCLIEDAO();
	@Override
	public List<TCTXCD> buscarTCTXCD(String ruc, int fDesde, int fHasta)
			throws SQLException {
		return tctxcdDAO.buscarTCTXCD(ruc, fDesde, fHasta);
	}
	@Override
	public double getSaldoSoles(String ruc, int fHasta) throws SQLException {
		return tctxcdDAO.getSaldoSoles(ruc, fHasta);
	}
	@Override
	public double getSaldoDolares(String ruc, int fHasta) throws SQLException {
		return tctxcdDAO.getSaldoDolares(ruc, fHasta);
	}
	
	@Override
	public List<TCLIE> getClientes() throws SQLException {
		return tclieDAO.getClientes();
	}

}
