package servicio;

import java.sql.SQLException;
import java.util.List;

import bean.TFVAD;
import bean.TNCDD;
import bean.TPEDH;
import bean.TPEDL;
import bean.TREGV;

public interface ReImpresionService {

	public List<TREGV> obtenerCorrelativos(String tipo,String desde,String hasta) throws SQLException;
	public  List<TPEDH> listarDetallePedidoTPEDD(int phpvta,String pdtdoc,int pdfabo) throws SQLException,Exception;
	public TPEDL getDetalleMotorOMoto(int serie,int numero,String articulo) throws SQLException;
	public List<TFVAD> listarDetallePedidoTFVAD(int fvpvta,int fvnume,String fvtdoc) throws SQLException;
	public List<TFVAD> listarDetallePedidoTFVADND(int fvpvta,int fvnume,String fvtdoc) throws SQLException;
	public List<TFVAD> listarDetallePedidoTFVADNC(int fvpvta,int fvnume,String fvtdoc) throws SQLException;
	public List<TNCDD> listarNotasDeCredito(int nhpvta,int nhnume) throws SQLException;
	
}
