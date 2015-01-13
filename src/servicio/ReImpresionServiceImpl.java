package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.TNCDDDAO;
import persistencia.TPEDHDAO;
import persistencia.TREGVDAO;
import bean.TFVAD;
import bean.TNCDD;
import bean.TPEDH;
import bean.TPEDL;
import bean.TREGV;

public class ReImpresionServiceImpl implements ReImpresionService{

	TREGVDAO tregvDAO = new TREGVDAO();
	TPEDHDAO tpedhDAO = new TPEDHDAO();
	TNCDDDAO tncdddao = new TNCDDDAO();
	
	@Override
	public List<TREGV> obtenerCorrelativos(String tipo, String desde,
			String hasta) throws SQLException {
		return tregvDAO.obtenerCorrelativos(tipo,desde,hasta);
	}

	@Override
	public List<TPEDH> listarDetallePedidoTPEDD(int phpvta,
			String pdtdoc, int pdfabo) throws SQLException, Exception {
		return tpedhDAO.listarDetallePedidoTPEDD(phpvta, pdtdoc, pdfabo);
	}

	@Override
	public TPEDL getDetalleMotorOMoto(int serie, int numero, String articulo)
			throws SQLException {
		return tpedhDAO.getDetalleMotorOMoto(serie, numero, articulo);
	}

	@Override
	public List<TFVAD> listarDetallePedidoTFVAD(int fvpvta, int fvnume,
			String fvtdoc) throws SQLException {
		return tpedhDAO.listarDetallePedidoTFVAD(fvpvta, fvnume, fvtdoc);
	}

	@Override
	public List<TFVAD> listarDetallePedidoTFVADND(int fvpvta, int fvnume,
			String fvtdoc) throws SQLException {
		return tpedhDAO.listarDetallePedidoTFVADND(fvpvta, fvnume, fvtdoc);
	}

	@Override
	public List<TFVAD> listarDetallePedidoTFVADNC(int fvpvta, int fvnume,
			String fvtdoc) throws SQLException {
		return tpedhDAO.listarDetallePedidoTFVADNC(fvpvta, fvnume, fvtdoc);
	}

	@Override
	public List<TNCDD> listarNotasDeCredito(int nhpvta, int nhnume)
			throws SQLException {
		return tncdddao.listarNotasDeCredito(nhpvta, nhnume);
	}

}
