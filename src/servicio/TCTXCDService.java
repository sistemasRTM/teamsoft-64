package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.TCLIE;
import bean.TCTXCD;

public interface TCTXCDService {

	public List<TCTXCD> buscarTCTXCD(String ruc,int fDesde,int fHasta) throws SQLException;
	public double getSaldoSoles(String ruc,int fHasta) throws SQLException;
	public double getSaldoDolares(String ruc,int fHasta) throws SQLException;
	public List<TCLIE> getClientes() throws SQLException;
}
