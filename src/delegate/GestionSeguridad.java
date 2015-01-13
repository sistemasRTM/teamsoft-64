package delegate;

import servicio.AuditoriaService;
import servicio.AuditoriaServiceImpl;
import servicio.EmpresaService;
import servicio.EmpresaServiceImpl;
import servicio.LoginService;
import servicio.LoginServiceImpl;
import servicio.ParametrosService;
import servicio.ParametrosServiceImpl;
/*
 * ESTA CLASE
 * REPRESENTA EL PAQUETE DE ANALISIS
 * GESTION DE SEGURIDAD
 */
public class GestionSeguridad {
	
	//obtiene implementacion del CU LOGIN
	public static LoginService getLoginService(){
		return new LoginServiceImpl();
	}
	
	public static EmpresaService getEmpresaService(){
		return new EmpresaServiceImpl();
	}
	
	public static AuditoriaService getAuditoriaService(){
		return new AuditoriaServiceImpl();
	}
	
	public static ParametrosService getParametrosService(){
		return new ParametrosServiceImpl();
	}
	
}
