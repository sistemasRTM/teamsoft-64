package delegate;

import servicio.LibroKardexService;
import servicio.LibroKardexServiceImpl;
import servicio.ReImpresionService;
import servicio.ReImpresionServiceImpl;
import servicio.RegistroOperacionDiariaService;
import servicio.RegistroOperacionDiariaServiceImpl;
import servicio.TARTCONService;
import servicio.TARTCONServiceImpl;
import servicio.TASDCService;
import servicio.TASDCServiceImpl;
import servicio.TASDTService;
import servicio.TASDTServiceImpl;
import servicio.TESCOMService;
import servicio.TESCOMServiceImpl;
import servicio.TREGOPService;
import servicio.TREGOPServiceImpl;
import servicio.TREPCOService;
import servicio.TREPCOServiceImpl;
import servicio.TTIPTRANService;
import servicio.TTIPTRANServiceImpl;

public class GestionContabilidad {

	public static RegistroOperacionDiariaService getRegistroOperacionDiariaService(){
		return new RegistroOperacionDiariaServiceImpl();
	}
	
	public static TARTCONService getTARTCONService(){
		return new TARTCONServiceImpl();
	}
	
	public static TASDCService getTASDCService(){
		return new TASDCServiceImpl();
	}
	
	public static TASDTService getTASDTService(){
		return new TASDTServiceImpl();
	}
	
	public static TESCOMService getTESCOMService(){
		return new TESCOMServiceImpl();
	}
	
	public static TREGOPService getTREGOPService(){
		return new TREGOPServiceImpl();
	}
	
	public static TTIPTRANService getTTIPTRANService(){
		return new TTIPTRANServiceImpl();
	}
	
	public static TREPCOService getTREPCOService(){
		return new TREPCOServiceImpl();
	}
	
	public static ReImpresionService getReImpresionService(){
		return new ReImpresionServiceImpl();
	}
	
	public static LibroKardexService getLibroKardexService(){
		return new LibroKardexServiceImpl();
	}
}
