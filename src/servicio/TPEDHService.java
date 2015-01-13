package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TPEDH;

public interface TPEDHService {
	public  List<TPEDH> listarPedidos(TPEDH tpedh) throws SQLException,Exception;
	public  List<TPEDH> listarPedidos(int phpvta,int fechaDesde, int fechaHasta) throws SQLException,Exception;
	public  List<TPEDH> listarDetallePedido(int phpvta,int phnume) throws SQLException,Exception;
}
