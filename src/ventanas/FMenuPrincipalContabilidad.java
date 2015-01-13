package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import bean.Operacion;
import controlador.ComprasController;
import controlador.ContabilidadController;
import controlador.FacturacionController;
import controlador.VentasController;
import delegate.GestionSeguridad;
import recursos.MaestroFrame;
import recursos.Sesion;
import servicio.LoginService;

public class FMenuPrincipalContabilidad extends MaestroFrame {
	
	private static final long serialVersionUID = 1L;
	private static FMenuPrincipalContabilidad gui = null;
	public JMenuItem mntmCompras;
	public JMenuItem mntmRegistroDeVentas;
	private JButton btnSalir;
	public JMenu mnReporte;
	private LoginService LoginServicio = GestionSeguridad.getLoginService();
	private JMenuItem mntmCertificadoDePercepcion;
	private JMenuItem mntmRegistroOperacionesDiarias;
	private JMenu mnMantenimiento;
	private JMenuItem mntmRegistroDeOperaciones;
	private JMenuItem mntmRegistroPresentacionC;
	private JMenuItem mntmTipoDeTransaccin;
	private JMenuItem mntmEstablecimientoComercial;
	private JMenuItem mntmAsociacinPresentacionC;
	private JMenuItem mntmAsociacinDeDocumento;
	private JMenuItem mntmAsociacionDocumentoT;
	
	private FIMantenimientoTASDC fiMADC= null;
	private FIMantenimientoTASDT fiMADT= null;
	private FIMantenimientoTARTCON fiMAPC= null;
	private FIMantenimientoTESCOM fiMEC= null;
	private FIMantenimientoTREPCO fiMRPC= null;
	private FIMantenimientoTTIPTRAN fiMTT= null;
	private FIMantenimientoTREGOP fiMRO= null;
	private FIRegistrarMovimiento fiRegistrarMovimiento=null;
	private JMenuItem mntmImprimirDocumentos;
	
	private JMenu mnProceso;
	private JMenuItem mntmRegistrarMovimiento;
	private JMenuItem mntmLibroKardex;
	private JMenuItem mntmLibroDiario;
	private JMenuItem mntmLibroMayor;
	
	
	public static FMenuPrincipalContabilidad createInstance() {
		if (gui == null) {
			gui = new FMenuPrincipalContabilidad();
		}
		return gui;

	}

	public static FMenuPrincipalContabilidad getInstance() {
		return gui;
	}
	
	public FMenuPrincipalContabilidad() {
		setTitle(getTitle() + "-" + Sesion.tfContabilidad);
		
		mnReporte = new JMenu("Reporte");
		mnReporte.setVisible(false);
		mnMenuBar.add(mnReporte);
		
		mntmCompras = new JMenuItem("Registro de Compras");
		mntmCompras.setVisible(false);
		mntmCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIRegistroCompras.getInstance() == null){
				FIRegistroCompras registroCompras = FIRegistroCompras.createInstance();
				registroCompras.initialize();
				try {
					Sesion.operaciones = LoginServicio.listarOperaciones(4,
							1, 2);
					for (Operacion operacion : Sesion.operaciones) {
						switch (operacion.getCodigo()) {
						case 7:
							registroCompras.getExportarTXT().setVisible(true);
							break;
						case 11:
							registroCompras.getSalir().setVisible(true);
							break;
						case 16:
							registroCompras.getProcesar().setEnabled(true);
							break;
						}
					}
				} catch (SQLException e1) {
				}
				ComprasController controlador = new ComprasController(registroCompras);
				registroCompras.setControlador(controlador);
				dpEscritorio.add(registroCompras,Sesion.show);
				}else{
					FIRegistroCompras.getInstance().toFront();
				}
			}
		});
		mnReporte.add(mntmCompras);
		
	    mntmRegistroDeVentas = new JMenuItem("Registro de Ventas");
		mntmRegistroDeVentas.setVisible(false);
		mntmRegistroDeVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIRegistroVentas.getInstance() == null){
					FIRegistroVentas mVentas = FIRegistroVentas.createInstance();
					mVentas.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								1, 20);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 7:
								mVentas.getExportarTXT().setVisible(true);
								break;
							case 11:
								mVentas.getSalir().setVisible(true);
								break;
							case 16:
								mVentas.getProcesar().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					VentasController controlador = new VentasController();
					mVentas.setControlador(controlador);
					controlador.setVista(mVentas);
					dpEscritorio.add(mVentas,Sesion.show);
				}else{
					FIRegistroVentas.getInstance().toFront();
				}
			}
		});
		mnReporte.add(mntmRegistroDeVentas);
		
		mntmCertificadoDePercepcion = new JMenuItem("Certificado de Percepci\u00F3n");
		mntmCertificadoDePercepcion.setVisible(false);
		mntmCertificadoDePercepcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FICertificadoPercepcion.getInstance() == null){
					FICertificadoPercepcion mPercepcion = FICertificadoPercepcion.createInstance();
					mPercepcion.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								1, 25);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 7:
								mPercepcion.getExportarTXT().setVisible(true);
								break;
							case 11:
								mPercepcion.getSalir().setVisible(true);
								break;
							case 16:
								mPercepcion.getProcesar().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					FacturacionController controlador = new FacturacionController(mPercepcion);
					mPercepcion.setControlador(controlador);
					dpEscritorio.add(mPercepcion,Sesion.show);
				}else{
					FICertificadoPercepcion.getInstance().toFront();
				}
			}
		});
		mnReporte.add(mntmCertificadoDePercepcion);
		
		mntmRegistroOperacionesDiarias = new JMenuItem("Registro de Operaciones Diarias");
		mntmRegistroOperacionesDiarias.setVisible(false);
		mntmRegistroOperacionesDiarias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIRegistroOperacionDiaria.getInstance() == null){
					FIRegistroOperacionDiaria mROD = FIRegistroOperacionDiaria.createInstance();
					mROD.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								1, 26);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 5:
								mROD.getCancelar().setVisible(true);
								break;
							case 7:
								mROD.getExportarTXT().setVisible(true);
								break;
							case 11:
								mROD.getSalir().setVisible(true);
								break;
							case 13:
								mROD.getCerrarPeriodo().setEnabled(true);
								break;
							case 16:
								mROD.getProcesar().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					ContabilidadController controlador = new ContabilidadController(mROD);
					mROD.setControlador(controlador);
					dpEscritorio.add(mROD,Sesion.show);
				}else{
					FIRegistroOperacionDiaria.getInstance().toFront();
				}
			}
		});
		mnReporte.add(mntmRegistroOperacionesDiarias);
		
		mntmImprimirDocumentos = new JMenuItem("Imprimir Documentos");
		mntmImprimirDocumentos.setVisible(false);
		mntmImprimirDocumentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIReImpresionDocumentos.getInstance() == null){
					FIReImpresionDocumentos mImpr = FIReImpresionDocumentos.createInstance();
					mImpr.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								1, 38);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 11:
								mImpr.getBtnSalir().setVisible(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					ContabilidadController controlador = new ContabilidadController(mImpr);
					mImpr.setControlador(controlador);
					dpEscritorio.add(mImpr,Sesion.show);
				}else{
					FIRegistroOperacionDiaria.getInstance().toFront();
				}
			}
		});
		
		mntmLibroKardex = new JMenuItem("Libro Kardex");
		mntmLibroKardex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIReporteLibroKardex.getInstance() == null){
					FIReporteLibroKardex mRLK = FIReporteLibroKardex.createInstance();
					mRLK.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								1, 41);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 11:
								mRLK.getSalir().setVisible(true);
								break;
							case 16:
								mRLK.getProcesar().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					ContabilidadController controlador = new ContabilidadController(mRLK);
					mRLK.setControlador(controlador);
					dpEscritorio.add(mRLK,Sesion.show);
				}else{
					FIReporteLibroKardex.getInstance().toFront();
				}
			}
		});
		mntmLibroKardex.setVisible(false);
		mnReporte.add(mntmLibroKardex);
		
		mntmLibroDiario = new JMenuItem("Libro Diario");
		mntmLibroDiario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIReporteLibroKardex.getInstance() == null){
					FIReporteLibroDiario mRLK = FIReporteLibroDiario.createInstance();
					mRLK.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								1, 48);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 11:
								mRLK.getSalir().setVisible(true);
								break;
							case 16:
								mRLK.getProcesar().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					ContabilidadController controlador = new ContabilidadController(mRLK);
					mRLK.setControlador(controlador);
					dpEscritorio.add(mRLK,Sesion.show);
				}else{
					FIReporteLibroKardex.getInstance().toFront();
				}
			}
		});
		mntmLibroDiario.setVisible(false);
		mnReporte.add(mntmLibroDiario);
		
		mntmLibroMayor = new JMenuItem("Libro Mayor");
		mntmLibroMayor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIReporteLibroKardex.getInstance() == null){
					FIReporteLibroMayor mRLK = FIReporteLibroMayor.createInstance();
					mRLK.initialize();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								1, 49);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 11:
								mRLK.getSalir().setVisible(true);
								break;
							case 16:
								mRLK.getProcesar().setEnabled(true);
								break;
							}
						}
					} catch (SQLException e1) {
					}
					ContabilidadController controlador = new ContabilidadController(mRLK);
					mRLK.setControlador(controlador);
					dpEscritorio.add(mRLK,Sesion.show);
				}else{
					FIReporteLibroKardex.getInstance().toFront();
				}
			}
		});
		mntmLibroMayor.setVisible(false);
		mnReporte.add(mntmLibroMayor);
		
		mnReporte.add(mntmImprimirDocumentos);
		
		
		btnSalir = new JButton("",Sesion.cargarIcono(Sesion.imgSalir));
		btnSalir.setToolTipText("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIRegistroVentas.close();
				FIRegistroCompras.close();
				FICertificadoPercepcion.close();
				FIRegistroOperacionDiaria.close();
				FIReImpresionDocumentos.close();
				FIRegistrarMovimiento.close();
				FIReporteLibroKardex.close();
				gui = null;
				dispose();
			}
		});
		
		mnMantenimiento = new JMenu("Mantenimiento");
		mnMantenimiento.setVisible(false);
		mnMenuBar.add(mnMantenimiento);
		
		mntmRegistroPresentacionC = new JMenuItem("Registro de Presentaci\u00F3n Comercial");
		mntmRegistroPresentacionC.setVisible(false);
		mntmRegistroPresentacionC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOpen(fiMRPC) == false) {
					fiMRPC = new FIMantenimientoTREPCO();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								8, 28);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 1:
								fiMRPC.getBtnNuevo().setVisible(true);
								break;
							case 2:
								fiMRPC.getBtnModificar().setVisible(true);
								break;
							case 4:
								fiMRPC.getBtnGuardar().setVisible(true);
								break;
							case 5:
								fiMRPC.getBtnCancelar().setVisible(true);
								break;
							case 11:
								fiMRPC.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {	}
					ContabilidadController controlador = new ContabilidadController(fiMRPC);
					fiMRPC.setControlador(controlador);
					dpEscritorio.add(fiMRPC, Sesion.show);
				} else {
					fiMRPC.toFront();
				}
				try {
					fiMRPC.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnMantenimiento.add(mntmRegistroPresentacionC);
		
		mntmRegistroDeOperaciones = new JMenuItem("Registro de Operaciones");
		mntmRegistroDeOperaciones.setVisible(false);
		mntmRegistroDeOperaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOpen(fiMRO) == false) {
					fiMRO = new FIMantenimientoTREGOP();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								8, 27);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 1:
								fiMRO.getBtnNuevo().setVisible(true);
								break;
							case 2:
								fiMRO.getBtnModificar().setVisible(true);
								break;
							case 4:
								fiMRO.getBtnGuardar().setVisible(true);
								break;
							case 5:
								fiMRO.getBtnCancelar().setVisible(true);
								break;
							case 11:
								fiMRO.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {	}
					ContabilidadController controlador = new ContabilidadController(fiMRO);
					fiMRO.setControlador(controlador);
					dpEscritorio.add(fiMRO, Sesion.show);
				} else {
					fiMRO.toFront();
				}
				try {
					fiMRO.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnMantenimiento.add(mntmRegistroDeOperaciones);
		
		mntmEstablecimientoComercial = new JMenuItem("Establecimiento Comercial");
		mntmEstablecimientoComercial.setVisible(false);
		mntmEstablecimientoComercial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOpen(fiMEC) == false) {
					fiMEC = new FIMantenimientoTESCOM();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								8, 29);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 1:
								fiMEC.getBtnNuevo().setVisible(true);
								break;
							case 2:
								fiMEC.getBtnModificar().setVisible(true);
								break;
							case 4:
								fiMEC.getBtnGuardar().setVisible(true);
								break;
							case 5:
								fiMEC.getBtnCancelar().setVisible(true);
								break;
							case 11:
								fiMEC.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {	}
					ContabilidadController controlador = new ContabilidadController(fiMEC);
					fiMEC.setControlador(controlador);
					dpEscritorio.add(fiMEC, Sesion.show);
				} else {
					fiMEC.toFront();
				}
				try {
					fiMEC.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnMantenimiento.add(mntmEstablecimientoComercial);
		
		mntmTipoDeTransaccin = new JMenuItem("Tipo de Transacci\u00F3n");
		mntmTipoDeTransaccin.setVisible(false);
		mntmTipoDeTransaccin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOpen(fiMTT) == false) {
					fiMTT = new FIMantenimientoTTIPTRAN();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								8, 30);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 1:
								fiMTT.getBtnNuevo().setVisible(true);
								break;
							case 2:
								fiMTT.getBtnModificar().setVisible(true);
								break;
							case 4:
								fiMTT.getBtnGuardar().setVisible(true);
								break;
							case 5:
								fiMTT.getBtnCancelar().setVisible(true);
								break;
							case 11:
								fiMTT.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {	}
					ContabilidadController controlador = new ContabilidadController(fiMTT);
					fiMTT.setControlador(controlador);
					dpEscritorio.add(fiMTT, Sesion.show);
				} else {
					fiMTT.toFront();
				}
				try {
					fiMTT.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnMantenimiento.add(mntmTipoDeTransaccin);
		
		mntmAsociacinPresentacionC = new JMenuItem("Asociaci\u00F3n de Presentaci\u00F3n Comercial");
		mntmAsociacinPresentacionC.setVisible(false);
		mntmAsociacinPresentacionC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOpen(fiMAPC) == false) {
					fiMAPC = new FIMantenimientoTARTCON();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								8, 31);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 1:
								fiMAPC.getBtnNuevo().setVisible(true);
								break;
							case 2:
								fiMAPC.getBtnModificar().setVisible(true);
								break;
							case 4:
								fiMAPC.getBtnGuardar().setVisible(true);
								break;
							case 5:
								fiMAPC.getBtnCancelar().setVisible(true);
								break;
							case 11:
								fiMAPC.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {	}
					ContabilidadController controlador = new ContabilidadController(fiMAPC);
					fiMAPC.setControlador(controlador);
					dpEscritorio.add(fiMAPC, Sesion.show);
				} else {
					fiMAPC.toFront();
				}
				try {
					fiMAPC.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnMantenimiento.add(mntmAsociacinPresentacionC);
		
		mntmAsociacionDocumentoT = new JMenuItem("Asociaci\u00F3n de Documento de Transacci\u00F3n");
		mntmAsociacionDocumentoT.setVisible(false);
		mntmAsociacionDocumentoT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOpen(fiMADT) == false) {
					fiMADT = new FIMantenimientoTASDT();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								8, 32);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 1:
								fiMADT.getBtnNuevo().setVisible(true);
								break;
							case 2:
								fiMADT.getBtnModificar().setVisible(true);
								break;
							case 4:
								fiMADT.getBtnGuardar().setVisible(true);
								break;
							case 5:
								fiMADT.getBtnCancelar().setVisible(true);
								break;
							case 11:
								fiMADT.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {	}
					ContabilidadController controlador = new ContabilidadController(fiMADT);
					fiMADT.setControlador(controlador);
					dpEscritorio.add(fiMADT, Sesion.show);
				} else {
					fiMADT.toFront();
				}
				try {
					fiMADT.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnMantenimiento.add(mntmAsociacionDocumentoT);
		
		mntmAsociacinDeDocumento = new JMenuItem("Asociaci\u00F3n de Documento de Cliente");
		mntmAsociacinDeDocumento.setVisible(false);
		mntmAsociacinDeDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOpen(fiMADC) == false) {
					fiMADC = new FIMantenimientoTASDC();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								8, 33);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 1:
								fiMADC.getBtnNuevo().setVisible(true);
								break;
							case 2:
								fiMADC.getBtnModificar().setVisible(true);
								break;
							case 4:
								fiMADC.getBtnGuardar().setVisible(true);
								break;
							case 5:
								fiMADC.getBtnCancelar().setVisible(true);
								break;
							case 11:
								fiMADC.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {	}
					ContabilidadController controlador = new ContabilidadController(fiMADC);
					fiMADC.setControlador(controlador);
					dpEscritorio.add(fiMADC, Sesion.show);
				} else {
					fiMADC.toFront();
				}
				try {
					fiMADC.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnMantenimiento.add(mntmAsociacinDeDocumento);
		
		mnProceso = new JMenu("Proceso");
		mnProceso.setVisible(false);
		mnMenuBar.add(mnProceso);
		
		mntmRegistrarMovimiento = new JMenuItem("Registrar Movimiento");
		mntmRegistrarMovimiento.setVisible(false);
		mntmRegistrarMovimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isOpen(fiRegistrarMovimiento) == false) {
					fiRegistrarMovimiento = new FIRegistrarMovimiento();
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(4,
								12, 40);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 11:
								fiRegistrarMovimiento.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {	}
					ContabilidadController controlador = new ContabilidadController(fiRegistrarMovimiento);
					fiRegistrarMovimiento.setControlador(controlador);
					dpEscritorio.add(fiRegistrarMovimiento, Sesion.show);
				} else {
					fiRegistrarMovimiento.toFront();
				}
				try {
					fiRegistrarMovimiento.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnProceso.add(mntmRegistrarMovimiento);
		
		mnMenuBar.add(btnSalir);
	}
	public JMenu getOpcReporte() {
		return mnReporte;
	}
	
	public JMenu getOpcMantenimiento() {
		return mnMantenimiento;
	}
	
	public JMenu getOpcProceso() {
		return mnProceso;
	}
	
	public JMenuItem getSubOpcRegistroCompras() {
		return mntmCompras;
	}
	
	public JMenuItem getSubOpcRegistroVentas() {
		return mntmRegistroDeVentas;
	}
	
	public JMenuItem getSubOpcCertificadoPercepcion() {
		return mntmCertificadoDePercepcion;
	}
	
	public JMenuItem getSubOpcRegistroOperacionDiaria() {
		return mntmRegistroOperacionesDiarias;
	}
	
	public JMenuItem getSubOpcRegistroOperacion() {
		return mntmRegistroDeOperaciones;
	}
	
	public JMenuItem getSubOpcRegistroPresentacionComercial() {
		return mntmRegistroPresentacionC;
	}
	
	public JMenuItem getSubOpcEstablecimientoComercial() {
		return mntmEstablecimientoComercial;
	}
	
	public JMenuItem getSubOpcTipoTransanccion() {
		return mntmTipoDeTransaccin;
	}
	
	public JMenuItem getSubOpcAsociacionPresentacionComercial() {
		return mntmAsociacinPresentacionC;
	}
	
	public JMenuItem getSubOpcAsociacionDocumentoTransaccion() {
		return mntmAsociacionDocumentoT;
	}
	
	public JMenuItem getSubOpcAsociacionDocumentoCliente() {
		return mntmAsociacinDeDocumento;
	}
	
	public JMenuItem getSubOpcReImprimirDocuemntos() {
		return mntmImprimirDocumentos;
	}
	
	public JMenuItem getSubOpcRegistrarMovimientos() {
		return mntmRegistrarMovimiento;
	}
	
	public JMenuItem getSubOpcReporteLibroKardex() {
		return mntmLibroKardex;
	}
	
	public JMenuItem getSubOpcReporteLibroDiario() {
		return mntmLibroDiario;
	}
	
	public JMenuItem getSubOpcReporteLibroMayor() {
		return mntmLibroMayor;
	}
	
}
