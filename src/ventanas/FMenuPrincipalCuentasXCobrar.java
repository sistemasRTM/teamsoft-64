package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import bean.Operacion;
import controlador.CuentasXCobrarController;
import delegate.GestionSeguridad;
import recursos.MaestroFrame;
import recursos.Sesion;
import servicio.LoginService;

public class FMenuPrincipalCuentasXCobrar extends MaestroFrame{

	private static final long serialVersionUID = 1L;
	private static FMenuPrincipalCuentasXCobrar gui = null;
	private JMenu mnProcesos;
	private JMenuItem mntmLetras;
	private JMenuItem mntmEstadoCuenta;	
	private JMenuItem mntmHistorialComprasCliente;	
	private JButton btnSalir;
	private CuentasXCobrarController controlador = new CuentasXCobrarController();
	private LoginService LoginServicio = GestionSeguridad.getLoginService();

	
	public static FMenuPrincipalCuentasXCobrar createInstance() {
		if (gui == null) {
			gui = new FMenuPrincipalCuentasXCobrar();
			
		}
		return gui;

	}

	public static FMenuPrincipalCuentasXCobrar getInstance() {
		return gui;
	}
	
	public FMenuPrincipalCuentasXCobrar() {		
		setTitle(getTitle() + "-" + Sesion.tfCuentasXCobrar);

		mnProcesos = new JMenu("Procesos");
		mnProcesos.setVisible(false);
		mnMenuBar.add(mnProcesos);

	
		mntmLetras = new JMenuItem("Letras");
		mntmLetras.setVisible(false);
		mntmLetras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FILetras.getInstance() == null){
					FILetras letras = FILetras.createInstance();
					letras.initialize();
					letras.setVisible(true);
					controlador.setVista(letras);
					letras.setControlador(controlador);
					dpEscritorio.add(letras,Sesion.show);
					
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(5, 10,
								36);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 9:
								letras.getBtnImprimir().setVisible(true);
								break;
							case 11:
								letras.getBtnSalir().setVisible(true);
								break;
							case 16:
								letras.getBtnProcesar().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {
					}
					
				}else{
					FILetras.getInstance().toFront();
				}
			}
		});
		mnProcesos.add(mntmLetras);
		
		mntmEstadoCuenta = new JMenuItem("Estado de Cuentas");
		mntmEstadoCuenta.setVisible(false);
		mntmEstadoCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIEstadoCuenta.getInstance() == null){
					FIEstadoCuenta estado = FIEstadoCuenta.createInstance();
					estado.initialize();
					estado.setVisible(true);
					controlador.setVista(estado);
					estado.setControlador(controlador);
					dpEscritorio.add(estado,Sesion.show);
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(5, 10,
								37);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							/*
							case 6:
								estado.getBtnExcel().setVisible(true);
								break;
								
							case 9:
								estado.getBtnImprimir().setVisible(true);
								break;*/
							case 11:
								estado.getBtnSalir().setVisible(true);
								break;
							case 16:
								estado.getBtnProcesar().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {
					}
				}else{
					FIEstadoCuenta.getInstance().toFront();
				}
			}
		});
		mnProcesos.add(mntmEstadoCuenta);
		
		mntmHistorialComprasCliente = new JMenuItem("Historial de Compras de Clientes");
		mntmHistorialComprasCliente.setVisible(false);
		mntmHistorialComprasCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIHistorialComprasCliente.getInstance() == null){
					FIHistorialComprasCliente historial = FIHistorialComprasCliente.createInstance();
					historial.initialize();
					historial.setVisible(true);
					controlador.setVista(historial);
					historial.setControlador(controlador);
					dpEscritorio.add(historial,Sesion.show);
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(5, 10,
								39);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							/*
							case 6:
								historial.getBtnExcel().setVisible(true);
								break;
								*/
							case 11:
								historial.getBtnSalir().setVisible(true);
								break;
							case 16:
								historial.getBtnProcesar().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {
					}
				}else{
					FIHistorialComprasCliente.getInstance().toFront();
				}
			}
		});
		mnProcesos.add(mntmHistorialComprasCliente);
		
		btnSalir = new JButton("",Sesion.cargarIcono(Sesion.imgSalir));
		btnSalir.setToolTipText("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FILetras.close();
				FIEstadoCuenta.close();
				FIHistorialComprasCliente.close();
				gui = null;
				dispose();
			}
		});
		mnMenuBar.add(btnSalir);
	}
	
	public JMenu getOpcProcesos() {
		return mnProcesos;
	}
	
	public JMenuItem getSubOpcLetras() {
		return mntmLetras;
	}
	
	public JMenuItem getSubOpcEstadoCuentas() {
		return mntmEstadoCuenta;
	}
	
	public JMenuItem getSubOpcHistorialComprasClientes() {
		return mntmHistorialComprasCliente;
	}

}
