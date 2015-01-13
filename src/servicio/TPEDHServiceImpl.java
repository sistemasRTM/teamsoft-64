package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TPEDHDAO;
import bean.TPEDH;

public class TPEDHServiceImpl implements TPEDHService{

	TPEDHDAO tpedhDAO = new TPEDHDAO();
	
	@Override
	public List<TPEDH> listarPedidos(TPEDH tpedh) throws SQLException,
			Exception {
		return tpedhDAO.listarPedidos(tpedh);
	}

	@Override
	public List<TPEDH> listarPedidos(int phpvta,int fechaDesde, int fechaHasta) throws SQLException, Exception {
		return tpedhDAO.listarPedidos(phpvta,fechaDesde,fechaHasta);
	}

	@Override
	public List<TPEDH> listarDetallePedido(int phpvta, int phnume)
			throws SQLException, Exception {
		return tpedhDAO.listarDetallePedido(phpvta, phnume);
	}

}
