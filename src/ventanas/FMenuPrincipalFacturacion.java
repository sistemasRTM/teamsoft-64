package ventanas;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Calendar;

import recursos.MaestroFrame;
import recursos.Sesion;
import servicio.LoginService;
import servicio.TPEDHService;
import bean.Operacion;
import controlador.ClienteController;
import controlador.FacturacionController;
import controlador.PreciosController;
import delegate.GestionSeguridad;
import delegate.GestionVentas;

public class FMenuPrincipalFacturacion extends MaestroFrame {

	private static final long serialVersionUID = 12L;
	private static FMenuPrincipalFacturacion gui = null;
	private JMenuItem mntmCliente;
	private JMenuItem mntmListaDePrecios;
	private JButton btnSalir;
	private JMenu mnMantenimiento;
	private LoginService LoginServicio = GestionSeguridad.getLoginService();
	TPEDHService servicioVentas = GestionVentas.getTPEDHService();
	private JMenu mnFacturacin;
	private JMenuItem mntmGenerarCertificado;
	private JMenuItem mntmVerificarPercepcion;
	private JMenuItem mntmImprimirPedidos;
	private JMenu mnConsultas;
	private JMenuItem mntmNotaDeCreditos;
	private JMenuItem mntmDetalleDePedidos;

	public static FMenuPrincipalFacturacion createInstance() {
		if (gui == null) {
			gui = new FMenuPrincipalFacturacion();
		}
		return gui;
	}

	public static FMenuPrincipalFacturacion getInstance() {
		return gui;
	}

	public FMenuPrincipalFacturacion() {

		setTitle(Sesion.titulo + "-" + Sesion.tfFacturacion);

		mnMantenimiento = new JMenu("Mantenimiento");
		mnMantenimiento.setVisible(false);
		mnMenuBar.add(mnMantenimiento);

		mntmCliente = new JMenuItem("Cliente");
		mntmCliente.setVisible(false);
		mntmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoCliente mCliente = new FIMantenimientoCliente();
				mCliente.setVisible(true);
				dpEscritorio.add(mCliente,Sesion.show);

				ClienteController controlador = new ClienteController(mCliente);
				mCliente.setControlador(controlador);

				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(1, 5,
							7);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 1:
							mCliente.getBtnNuevo().setVisible(true);
							break;
						case 2:
							mCliente.getBtnModificar().setVisible(true);
							break;
						case 3:
							mCliente.getBtnEliminar().setVisible(true);
							break;
						case 4:
							mCliente.getBtnGuardar().setVisible(true);
							break;
						case 5:
							mCliente.getBtnCancelar().setVisible(true);
							break;
						case 11:
							mCliente.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
			}
		});

		mnMantenimiento.add(mntmCliente);

		mntmListaDePrecios = new JMenuItem("Lista de Precios");
		mntmListaDePrecios.setVisible(false);
		mntmListaDePrecios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMantenimientoListaPrecios mListaPrecios = new FIMantenimientoListaPrecios();
				mListaPrecios.setVisible(true);
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(1, 5,
							1);

					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 11:
							mListaPrecios.getBtnSalir().setVisible(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}

				PreciosController controlador = new PreciosController(
						mListaPrecios);
				mListaPrecios.setControlador(controlador);
				dpEscritorio.add(mListaPrecios,Sesion.show);
			}
		});
		mnMantenimiento.add(mntmListaDePrecios);
	
		btnSalir = new JButton("",Sesion.cargarIcono(Sesion.imgSalir));
		btnSalir.setToolTipText("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIGenerarCertificado.close();
				FIImprimirPedidos.close();
				FIVerificarPercepcion.close();
				FINotaCredito.close();
				gui = null;
				dispose();
			}
		});
		
		mnFacturacin = new JMenu("Facturaci\u00F3n");
		mnFacturacin.setVisible(true);
		mnMenuBar.add(mnFacturacin);
		
		mntmGenerarCertificado = new JMenuItem("Generar Certificado");
		mntmGenerarCertificado.setVisible(false);
		mntmGenerarCertificado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIGenerarCertificado.getInstance() == null){
					FIGenerarCertificado mGenerarCertificado = FIGenerarCertificado.createInstance();
					mGenerarCertificado.initialize();
					mGenerarCertificado.setVisible(true);
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(1, 7,23);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 9:
								mGenerarCertificado.getBtnImprimir().setVisible(true);
								break;
							case 11:
								mGenerarCertificado.getBtnSalir().setVisible(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					FacturacionController controlador = new FacturacionController(mGenerarCertificado);
					mGenerarCertificado.setControlador(controlador);
					dpEscritorio.add(mGenerarCertificado,Sesion.show);
				}else{
					FIRegistroCompras.getInstance().toFront();
				}
			}
		});
		mnFacturacin.add(mntmGenerarCertificado);
		
		mntmVerificarPercepcion = new JMenuItem("Verificar Percepción");
		mntmVerificarPercepcion.setVisible(false);
		mntmVerificarPercepcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIVerificarPercepcion.getInstance() == null){
					FIVerificarPercepcion mVerificarPercepcion = FIVerificarPercepcion.createInstance();
					mVerificarPercepcion.initialize();
					mVerificarPercepcion.setVisible(true);
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(1, 7,24);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 11:
								mVerificarPercepcion.getBtnSalir().setVisible(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					FacturacionController controlador = new FacturacionController(mVerificarPercepcion);
					mVerificarPercepcion.setControlador(controlador);
					dpEscritorio.add(mVerificarPercepcion,Sesion.show);
				}else{
					FIRegistroCompras.getInstance().toFront();
				}
			}
		});
		mnFacturacin.add(mntmVerificarPercepcion);
		
		mntmImprimirPedidos = new JMenuItem("Imprimir Pedidos");
		mntmImprimirPedidos.setVisible(false);
		mntmImprimirPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIImprimirPedidos.getInstance() == null){
					FIImprimirPedidos mImprimirPedidos = FIImprimirPedidos.createInstance();
					mImprimirPedidos.initialize();
					mImprimirPedidos.setVisible(true);
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(1, 7,34);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 9:
								mImprimirPedidos.getBtnImprimir().setVisible(true);
								break;
							case 11:
								mImprimirPedidos.getBtnSalir().setVisible(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					FacturacionController controlador = new FacturacionController(mImprimirPedidos);
					mImprimirPedidos.setControlador(controlador);
					
					String fDesde = Sesion.fechaParametrizada(Calendar.MONTH, -1);
					String fHasta = Sesion.fechaParametrizada(Calendar.MONTH, 0);
					try {
						mImprimirPedidos.cargaTabla(servicioVentas.listarPedidos(9, Integer.parseInt(fDesde.substring(6, 10) + fDesde.substring(3, 5) + fDesde.substring(0, 2)),
								Integer.parseInt(fHasta.substring(6, 10) + fHasta.substring(3, 5) + fHasta.substring(0, 2))));
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					dpEscritorio.add(mImprimirPedidos,Sesion.show);
				}else{
					FIRegistroCompras.getInstance().toFront();
				}
			}
		});
		mnFacturacin.add(mntmImprimirPedidos);
		
		mnConsultas = new JMenu("Consultas");
		mnMenuBar.add(mnConsultas);
		
		mntmNotaDeCreditos = new JMenuItem("Nota de Creditos");
		mntmNotaDeCreditos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FINotaCredito.getInstance() == null){
					FINotaCredito mNotaCredito = FINotaCredito.createInstance();
					mNotaCredito.initialize();
					mNotaCredito.setVisible(true);
					mNotaCredito.getBtnSalir().setVisible(true);
					FacturacionController controlador = new FacturacionController(mNotaCredito);
					mNotaCredito.setControlador(controlador);
					dpEscritorio.add(mNotaCredito,Sesion.show);
				}else{
					FINotaCredito.getInstance().toFront();
				}
			}
		});
		
		mntmDetalleDePedidos = new JMenuItem("Detalle de Pedidos");
		mntmDetalleDePedidos.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(FIDetallePedidos.getInstance() == null){
					FIDetallePedidos mDetallePedidos = FIDetallePedidos.createInstance();
					mDetallePedidos.initialize();
					mDetallePedidos.setVisible(true);
					mDetallePedidos.getBtnSalir().setVisible(true);
					FacturacionController controlador = new FacturacionController(mDetallePedidos);
					mDetallePedidos.setControlador(controlador);
					dpEscritorio.add(mDetallePedidos,Sesion.show);
				}else{
					FIDetallePedidos.getInstance().toFront();
				}
				
			}
		});
		
		mnConsultas.add(mntmDetalleDePedidos);
		mnConsultas.add(mntmNotaDeCreditos);
		mnMenuBar.add(btnSalir);
	}

	public JMenu getOpcMantenimiento() {
		return mnMantenimiento;
	}
	
	public JMenu getOpcFacturacion() {
		return mnFacturacin;
	}
	
	public JMenu getOpcConsulta() {
		return mnConsultas;
	}
	
	

	public JMenuItem getSubOpcCliente() {
		return mntmCliente;
	}

	public JMenuItem getSubOpcListaPrecio() {
		return mntmListaDePrecios;
	}
	
	public JMenuItem getSubOpcGenerarCertificado() {
		return mntmGenerarCertificado;
	}
	
	public JMenuItem getSubOpcVerificarPercepcion() {
		return mntmVerificarPercepcion;
	}
	
	public JMenuItem getSubOpcImprimirPedidos() {
		return mntmImprimirPedidos;
	}
	
	public JMenuItem getSubOpcNotaCredito() {
		return mntmNotaDeCreditos;
	}
	
	public JMenuItem getSubOpcDetallePedidos() {
		return mntmDetalleDePedidos;
	}
}
