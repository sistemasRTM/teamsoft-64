package delegate;

import servicio.CorrelativoService;
import servicio.CorrelativoServiceImpl;
import servicio.TPEDHService;
import servicio.TPEDHServiceImpl;
import servicio.VentasService;
import servicio.VentasServiceImpl;

public class GestionVentas {

	public static VentasService getVentasService(){
		return new VentasServiceImpl();
	}
	
	public static CorrelativoService getCorrelativoService(){
		return new CorrelativoServiceImpl();
	}
	
	public static TPEDHService getTPEDHService(){
		return new TPEDHServiceImpl();
	}
	
}
