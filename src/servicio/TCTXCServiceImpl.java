package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.TCTXCDAO;
import bean.TCTXC;

public class TCTXCServiceImpl implements TCTXCService{

	TCTXCDAO tctxcDAO = new TCTXCDAO();
	
	@Override
	public List<TCTXC> buscarPendientes(String ruc) throws SQLException {
		return tctxcDAO.buscarPendientes(ruc);
	}

}
