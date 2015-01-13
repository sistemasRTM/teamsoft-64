package delegate;

import servicio.TCTXCDService;
import servicio.TCTXCDServiceImpl;
import servicio.TCTXCService;
import servicio.TCTXCServiceImpl;

public class GestionCuentasXCobrar {

	public static TCTXCService getTCTXCService(){
		return new TCTXCServiceImpl();
	}
	
	public static TCTXCDService getTCTXCDService(){
		return new TCTXCDServiceImpl();
	}
}
