package delegate;

import servicio.MenuService;
import servicio.MenuServiceImpl;
import servicio.ModuloService;
import servicio.ModuloServiceImpl;
import servicio.OpcionService;
import servicio.OpcionServiceImpl;
import servicio.OperacionService;
import servicio.OperacionServiceImpl;
import servicio.PerfilService;
import servicio.PerfilServiceImpl;
import servicio.SubOpcionService;
import servicio.SubOpcionServiceImpl;

public class GestionPermisos {
	
	public static OperacionService getOperacionService(){
		return new OperacionServiceImpl();
	}
	
	public static SubOpcionService getSubOpcionService(){
		return new SubOpcionServiceImpl();
	}
	
	public static OpcionService getOpcionService(){
		return new OpcionServiceImpl();
	}
	
	public static MenuService getMenuService(){
		return new MenuServiceImpl();
	}
	
	public static ModuloService getModuloService(){
		return new ModuloServiceImpl();
	}
	
	public static PerfilService getPerfilService(){
		return new PerfilServiceImpl();
	}
	
}
