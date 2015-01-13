package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import org.jdesktop.swingx.JXTable;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import bean.Auditoria;
import bean.CalculoComision;
import bean.Comision;
import bean.TCCM;
import bean.TECC;
import delegate.GestionComision;
import delegate.GestionSeguridad;
import recursos.MaestroBean;
import recursos.Sesion;
import servicio.AuditoriaService;
import servicio.ComisionService;
import servicio.MantenimientoCriteriosComisionService;
import servicio.MantenimientoExcepcionCriterioComisionService;
import ventanas.FIAbrirPeriodo;
import ventanas.FICalculoComisiones;
import ventanas.FIDetalleCalculos;
import ventanas.FIMantenimientoCriteriosComision;
import ventanas.FIMantenimientoExcepcionCriterio;

public class RRHHController implements ActionListener {
	DefaultListModel modelo = new DefaultListModel();
	FICalculoComisiones mCalculoComisiones = new FICalculoComisiones();
	FIDetalleCalculos mDetalleCalculos = new FIDetalleCalculos();
	FIAbrirPeriodo mAbrirPeriodo = new FIAbrirPeriodo();
	FIMantenimientoCriteriosComision mCriteriosComision = new FIMantenimientoCriteriosComision();
	FIMantenimientoExcepcionCriterio mExcepcionCriterio = new FIMantenimientoExcepcionCriterio();
	ComisionService comisionService = GestionComision.getComisionService();
	MantenimientoCriteriosComisionService criteriosComisionService = GestionComision
			.getMantenimientoCriteriosComisionService();
	MantenimientoExcepcionCriterioComisionService excepcionCriterioComisionService = GestionComision
			.getMantenimientoExcepcionCriterioComisionService();
	SimpleDateFormat formateaMes = new SimpleDateFormat("MM");
	AuditoriaService auditoriaService = GestionSeguridad.getAuditoriaService();
	Auditoria auditoria;

	double comisionXMayor = 0;
	double comisionXMenor = 0;
	//
	double totalVentaXMayor = 0;
	double totalVentaXMenor = 0;
	double totalComisionXMayor = 0;
	double totalComisionXMenor = 0;
	//
	double totalVentasGeneral = 0;
	double totalComisionXMayorGeneral = 0;
	double totalComisionXMenorGeneral = 0;
	//
	double acumuladoTotalXMayor = 0;
	double acumuladoTotalXMenor = 0;
	//
	List<CalculoComision> pedidosCalculados = new ArrayList<CalculoComision>();
	List<CalculoComision> comisionError = new ArrayList<CalculoComision>();
	List<Comision> comisiones = new ArrayList<Comision>();
	CalculoComision comisionCalculada = null;
	List<CalculoComision> pedidosXCalcular = new ArrayList<CalculoComision>();;
	List<CalculoComision> notaCreditosXCalcular;
	//
	JFileChooser fileChooser;
	String ruta = "";// ruta de la carpeta donde se ubicara el archivo
	String archivo = "";// nombre del archivo txt
	String extension = ".xls";// extension del archivo
	//
	int totalRegistros = 0;
	int situacionPeriodo = -1;
	Thread hilo;
	boolean procesando = false;

	List<TCCM> criterios = null;
	List<TECC> excepciones = null;

	public RRHHController() {

	}

	public void setVista(FICalculoComisiones mCalculoComisiones) {
		this.mCalculoComisiones = mCalculoComisiones;
	}

	public void setVista(FIDetalleCalculos mDetalleCalculos) {
		this.mDetalleCalculos = mDetalleCalculos;
	}

	public void setVista(FIAbrirPeriodo mAbrirPeriodo) {
		this.mAbrirPeriodo = mAbrirPeriodo;
	}

	public void setVista(FIMantenimientoCriteriosComision mCriteriosComision) {
		this.mCriteriosComision = mCriteriosComision;
	}

	public void setVista(FIMantenimientoExcepcionCriterio mExcepcionCriterio) {
		this.mExcepcionCriterio = mExcepcionCriterio;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == mCalculoComisiones.getBtnCalcular()) {

			hilo = new Thread() {
				public void run() {
					try {
						procesar();
					} catch (OutOfMemoryError e) {
						cancelar();
						System.out.println(e.getMessage());
						Sesion.mensajeError(mCalculoComisiones,
								"menoria insuficiente para esta consulta \n");
					} catch (Exception ex) {
						Sesion.mensajeError(mCalculoComisiones, ex.getMessage()
								+ " hilo");
					}
				}
			};
			try {
				hilo.start();
			} catch (OutOfMemoryError ex) {
				cancelar();
				System.out.println(ex.getMessage());
				Sesion.mensajeError(mCalculoComisiones,
						"menoria insuficiente para esta consulta \n");
			} catch (Exception ex) {
				Sesion.mensajeError(mCalculoComisiones, ex.getMessage() + " 2");
			}

		} else if (e.getSource() == mCalculoComisiones.getBtnExcel()) {
			exportarExcelCalculoComisiones();
		} else if (e.getSource() == mCalculoComisiones.getBtnCerrarPeriodo()) {
			cerrarPeriodo(pedidosCalculados);
		} else if (e.getSource() == mCalculoComisiones.getBtnCancelar()) {
			cancelar();
		} else if (e.getSource() == mCalculoComisiones.getBtnSalir()) {
			FICalculoComisiones.close();
			mCalculoComisiones.salir();
		} else if (e.getSource() == mDetalleCalculos.getBtnExcel()) {
			exportarExcelDetalleCalculoComisiones(mDetalleCalculos.getTabla());
		} else if (e.getSource() == mDetalleCalculos.getBtnSalir()) {
			FIDetalleCalculos.close();
			mDetalleCalculos.salir();
		} else if (e.getSource() == mAbrirPeriodo.getBtnAbrirPeriodo()) {
			try {
				int situacion = comisionService.situacionPeriodo(
						mAbrirPeriodo.getFechaInicial(),
						mAbrirPeriodo.getFechaFin());
				if (situacion == 1) {
					comisionService.actualizarPeriodo(
							mAbrirPeriodo.getFechaInicial(),
							mAbrirPeriodo.getFechaFin(), 0);
					mAbrirPeriodo.cargarTabla(comisionService.listarPeriodos());
					Sesion.mensajeInformacion(mAbrirPeriodo,
							"Periodo abierto con exito");
				} else {
					Sesion.mensajeInformacion(mAbrirPeriodo,
							"El periodo ya esta abierto");
				}

			} catch (SQLException e1) {
				Sesion.mensajeError(mAbrirPeriodo,
						"Error al tratar de abrir periodo " + e1.getMessage());
			}
		} else if (e.getSource() == mAbrirPeriodo.getBtnSalir()) {
			FIAbrirPeriodo.close();
			mAbrirPeriodo.salir();
		} else if (e.getSource() == mCriteriosComision.getBtnGuardar()) {
			if (!mCriteriosComision.getFamilia().getDescripcion()
					.equalsIgnoreCase("Seleccione")) {
				if (mCriteriosComision.getFamilia() != null) {
					if (mCriteriosComision.getSubFamilia() != null) {
						if (mCriteriosComision.getOrigen() != null) {
							guardar();
						} else {
							Sesion.mensajeError(mCriteriosComision,
									"Seleccione Origen");
						}
					} else {
						Sesion.mensajeError(mCriteriosComision,
								"Seleccione Sub Familia");
					}
				} else {
					Sesion.mensajeError(mCriteriosComision,
							"Seleccione Familia");
				}
			} else {
				Sesion.mensajeError(mCriteriosComision, "Seleccione Familia");
			}

		} else if (e.getSource() == mCriteriosComision.getBtnCancelar()) {
			try {
				List<TCCM> tccms = criteriosComisionService.listar();
				mCriteriosComision.limpiar();
				mCriteriosComision.cargarTablaProceso(tccms);
			} catch (SQLException ex) {
				Sesion.mensajeError(mCriteriosComision, "Error al listar");
			}
		} else if (e.getSource() == mCriteriosComision.getBtnEliminar()) {
			if (!mCriteriosComision.getCodigo().equals("")) {
				try {
					if (Sesion.mensajeConfirmacion(mCriteriosComision,
							"¿Esta seguro que desea eliminar el registro?") == 0) {
						int resultado = criteriosComisionService
								.eliminar(mCriteriosComision.getCodigo());
						if (resultado > 0) {
							//
							auditoria = new Auditoria();
							auditoria.setUsu(Sesion.usuario);
							auditoria.setCIA(Sesion.cia);
							auditoria.setFecha(new Timestamp(Calendar
									.getInstance().getTimeInMillis()));
							auditoria.setModulo("RR.HH");
							auditoria.setMenu("Mantenimientos");
							auditoria.setOpcion("Criterios de clasificación");
							auditoria.setTipo_ope("Eliminar");
							auditoria.setCant_reg(resultado);
							auditoria.setRef1(mCriteriosComision.getCodigo());
							auditoriaService.insertarAuditoria(auditoria);
							//
							Sesion.mensajeInformacion(mCriteriosComision,
									"Eliminación exitosa");
							mCriteriosComision.limpiar();
						}
					}
				} catch (SQLException e1) {
					Sesion.mensajeError(mCriteriosComision,
							"Error inesperado de BD, " + e1.getMessage() + " "
									+ e1.getErrorCode() + " " + e1.getCause());
				}
			} else {
				Sesion.mensajeError(mCriteriosComision,
						"Seleccione el criterio a eliminar");
			}

		} else if (e.getSource() == mCriteriosComision.getBtnSalir()) {
			FIMantenimientoCriteriosComision.close();
			mCriteriosComision.salir();
		} else if (e.getSource() == mExcepcionCriterio.getBtnMantener()) {
			if (mExcepcionCriterio.getTCCM() != null) {
				mExcepcionCriterio.getTbpExcepciones().setEnabledAt(0, false);
				mExcepcionCriterio.getTbpExcepciones().setEnabledAt(1, true);
				mExcepcionCriterio.getTbpExcepciones().setSelectedIndex(1);
				mExcepcionCriterio.getBtnGuardar().setEnabled(true);
				String familia = mExcepcionCriterio.getTCCM().getCodigo()
						.substring(0, 3);
				String subFam = mExcepcionCriterio.getTCCM().getCodigo()
						.substring(3, 6);
				String origen = mExcepcionCriterio.getTCCM().getCodigo()
						.substring(6, 9);
				String codigo = mExcepcionCriterio.getTCCM().getCodigo().trim();

				TECC tecc = new TECC();
				tecc.setCriterio(codigo);
				try {
					List<TECC> excepciones = excepcionCriterioComisionService
							.buscarPorCriterio(tecc);
					if (!origen.equals("000") && subFam.equals("000")) {
						TCCM tccm = new TCCM();
						tccm.setCodfam(familia);
						try {
							List<MaestroBean> listado = criteriosComisionService
									.listarSubFamilias(tccm);
							mExcepcionCriterio.procesoSubFam(listado, codigo,
									excepciones);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else if (origen.equals("000") && !subFam.equals("000")) {
						TCCM tccm = new TCCM();
						tccm.setCodfam(familia);
						tccm.setCodsfa(subFam);
						try {
							List<MaestroBean> listado = criteriosComisionService
									.listarOrigenes(tccm);
							mExcepcionCriterio.procesoOrigen(listado, codigo,
									excepciones);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else if (origen.equals("000") && subFam.equals("000")) {
						TCCM tccm = new TCCM();
						tccm.setCodfam(familia);
						try {
							List<MaestroBean> listado = criteriosComisionService
									.listarSubFamilias(tccm);
							mExcepcionCriterio.procesoSubFamOri(listado,
									codigo, excepciones);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}

			} else {
				Sesion.mensajeError(mCriteriosComision, "Seleccione una fila");
			}
		} else if (e.getSource() == mExcepcionCriterio.getBtnCancelar()) {
			mExcepcionCriterio.getTbpExcepciones().setEnabledAt(1, false);
			mExcepcionCriterio.getTbpExcepciones().setEnabledAt(0, true);
			mExcepcionCriterio.getTbpExcepciones().setSelectedIndex(0);
			mExcepcionCriterio.getBtnGuardar().setEnabled(false);
			mExcepcionCriterio.limpiar();
		} else if (e.getSource() == mExcepcionCriterio.getBtnGuardar()) {
			try {
				excepcionCriterioComisionService.eliminar(mExcepcionCriterio
						.getCodigo());
				
				TECC tecc = null;
				for (int i = 0; i < mExcepcionCriterio.getTbExcepciones()
						.getRowCount(); i++) {
					tecc = new TECC();
					tecc.setCriterio(mExcepcionCriterio.getTbExcepciones()
							.getValueAt(i, 0).toString());
					tecc.setExcepcion(mExcepcionCriterio.getTbExcepciones()
							.getValueAt(i, 1).toString());
					excepcionCriterioComisionService
							.insertar(tecc);
				}

				List<TECC> excepcionesActuales = mExcepcionCriterio
						.getExcepcionesActuales();
				for (TECC tecc2 : excepcionesActuales) {
					boolean flag = false;
					for (int i = 0; i < mExcepcionCriterio.getTbExcepciones()
							.getRowCount(); i++) {
						if (tecc2.getExcepcion().equalsIgnoreCase(
								mExcepcionCriterio.getTbExcepciones()
										.getValueAt(i, 1).toString())) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						auditoria = new Auditoria();
						auditoria.setUsu(Sesion.usuario);
						auditoria.setCIA(Sesion.cia);
						auditoria.setFecha(new Timestamp(Calendar.getInstance()
								.getTimeInMillis()));
						auditoria.setModulo("RR.HH");
						auditoria.setMenu("Mantenimientos");
						auditoria.setOpcion("Excepción de criterios");
						auditoria.setTipo_ope("Eliminar");
						auditoria.setCant_reg(1);
						auditoria.setRef1(tecc2.getCriterio());
						auditoria.setRef2(tecc2.getExcepcion());
						auditoriaService.insertarAuditoria(auditoria);
					}
				}

				for (int i = 0; i < mExcepcionCriterio.getTbExcepciones()
						.getRowCount(); i++) {
					boolean flag = false;
					for (TECC tecc2 : excepcionesActuales) {
						if (tecc2.getExcepcion().equalsIgnoreCase(
								mExcepcionCriterio.getTbExcepciones()
										.getValueAt(i, 1).toString())) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						auditoria = new Auditoria();
						auditoria.setUsu(Sesion.usuario);
						auditoria.setCIA(Sesion.cia);
						auditoria.setFecha(new Timestamp(Calendar.getInstance()
								.getTimeInMillis()));
						auditoria.setModulo("RR.HH");
						auditoria.setMenu("Mantenimientos");
						auditoria.setOpcion("Excepción de criterios");
						auditoria.setTipo_ope("Insertar");
						auditoria.setCant_reg(1);
						auditoria.setRef1(mExcepcionCriterio.getTbExcepciones()
								.getValueAt(i, 0).toString());
						auditoria.setRef2(mExcepcionCriterio.getTbExcepciones()
								.getValueAt(i, 1).toString());
						auditoriaService.insertarAuditoria(auditoria);
					}
				}

				Sesion.mensajeInformacion(mExcepcionCriterio,
						"Se guardaron los cambios satisfactoriamente");
				mExcepcionCriterio.getTbpExcepciones().setEnabledAt(1, false);
				mExcepcionCriterio.getTbpExcepciones().setEnabledAt(0, true);
				mExcepcionCriterio.getTbpExcepciones().setSelectedIndex(0);
				mExcepcionCriterio.limpiar();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == mExcepcionCriterio.getBtnSalir()) {
			FIMantenimientoExcepcionCriterio.close();
			mExcepcionCriterio.salir();
		}

	}

	private void guardar() {
		TCCM tccm = new TCCM();
		tccm.setCodfam(mCriteriosComision.getFamilia().getCodigo().trim());
		tccm.setDesfam(mCriteriosComision.getFamilia().getDescripcion().trim());
		tccm.setCodsfa(mCriteriosComision.getSubFamilia().getCodigo().trim());
		tccm.setDessfa(mCriteriosComision.getSubFamilia().getDescripcion()
				.trim());
		tccm.setCodori(mCriteriosComision.getOrigen().getCodigo().trim());
		tccm.setDesori(mCriteriosComision.getOrigen().getDescripcion().trim());
		tccm.setValor(mCriteriosComision.getValor());
		tccm.setCodigo(tccm.getCodfam() + tccm.getCodsfa() + tccm.getCodori());

		//
		auditoria = new Auditoria();
		auditoria.setUsu(Sesion.usuario);
		auditoria.setCIA(Sesion.cia);
		auditoria.setFecha(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		auditoria.setModulo("RR.HH");
		auditoria.setMenu("Mantenimientos");
		auditoria.setOpcion("Criterios de clasificación");
		auditoria.setRef1(tccm.getCodigo());
		auditoria.setRef2(tccm.getDesfam());
		auditoria.setRef3(tccm.getDessfa());
		auditoria.setRef4(tccm.getDesori());
		auditoria.setRef5(tccm.getValor() + "");
		//

		try {
			if (criteriosComisionService.verificar(tccm) == null) {
				int resultado = criteriosComisionService.insertar(tccm);
				if (resultado > 0) {
					auditoria.setTipo_ope("Insertar");
					auditoria.setCant_reg(resultado);
					Sesion.mensajeInformacion(mCriteriosComision,
							"CRITERIO INGRESADO EXITOSAMENTE");
					List<TCCM> listado = criteriosComisionService.listar();
					mCriteriosComision.cargarTablaProceso(listado);
				} else {
					Sesion.mensajeError(mCriteriosComision,
							"Error al registrar criterio. Contactar al area de sistemas");
				}
			} else {
				int resultado = criteriosComisionService.modificar(tccm);
				if (resultado > 0) {
					auditoria.setTipo_ope("Modificar");
					auditoria.setCant_reg(resultado);
					Sesion.mensajeInformacion(mCriteriosComision,
							"CRITERIO MODIFICADO EXITOSAMENTE");
					List<TCCM> listado = criteriosComisionService.listar();
					mCriteriosComision.cargarTablaProceso(listado);
				} else {
					Sesion.mensajeError(mCriteriosComision,
							"Error al modificar criterio. Contactar al area de sistemas");
				}
			}
			auditoriaService.insertarAuditoria(auditoria);
		} catch (SQLException e1) {
			Sesion.mensajeError(mCriteriosComision, e1.getMessage()
					+ ". Error interno");
		}
	}

	private void exportarExcelDetalleCalculoComisiones(JXTable tabla) {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fil = fileChooser.getSelectedFile();
			String ruta = fil.toString();
			ruta = ruta + "/detalleCalculos.xls";
			File file = new File(ruta);
			try {
				DataOutputStream out = new DataOutputStream(
						new FileOutputStream(file));
				WritableWorkbook w = Workbook.createWorkbook(out);

				WritableSheet s = w.createSheet(
						"Detalle de cálculos de comisión", 0);

				// tamaño de celdas
				CellView cv0 = new CellView();
				CellView cv1 = new CellView();
				CellView cv2 = new CellView();
				CellView cv3 = new CellView();

				cv0.setSize(2500);
				cv1.setSize(3500);
				cv2.setSize(8000);
				cv3.setAutosize(true);

				s.setColumnView(0, cv0);
				s.setColumnView(1, cv2);
				s.setColumnView(2, cv0);
				s.setColumnView(3, cv0);
				s.setColumnView(4, cv0);
				s.setColumnView(5, cv1);
				s.setColumnView(6, cv3);
				s.setColumnView(7, cv1);
				s.setColumnView(8, cv1);
				s.setColumnView(9, cv1);
				s.setColumnView(10, cv1);
				s.setColumnView(11, cv1);
				// cabecera
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setAlignment(Alignment.CENTRE);

				s.addCell(new Label(0, 6, "Código", wcf));
				s.addCell(new Label(1, 6, "Nombre", wcf));
				s.addCell(new Label(2, 6, "P.V.", wcf));
				s.addCell(new Label(3, 6, "# Doc.", wcf));
				s.addCell(new Label(4, 6, "T.Doc", wcf));
				s.addCell(new Label(5, 6, "# Doc. Ori.", wcf));
				s.addCell(new Label(6, 6, "Categoria", wcf));
				s.addCell(new Label(7, 6, "Articulo", wcf));
				s.addCell(new Label(8, 6, "V.Venta", wcf));
				s.addCell(new Label(9, 6, "Descuento", wcf));
				s.addCell(new Label(10, 6, "% Descuento", wcf));
				s.addCell(new Label(11, 6, "Tipo Venta", wcf));
				// cargando data a excel
				for (int i = 0; i < tabla.getColumnCount(); i++) {
					for (int j = 0; j < tabla.getRowCount(); j++) {
						Object objeto = tabla.getValueAt(j, i);
						try {
							s.addCell(new jxl.write.Number(i, j + 7, Double
									.parseDouble(String.valueOf(objeto))));
						} catch (Exception e) {
							s.addCell(new Label(i, j + 7, String
									.valueOf(objeto)));
						}
					}
				}

				w.write();
				w.close();
				out.close();

				Sesion.mensajeInformacion(mCalculoComisiones,
						"Se a exportado con exito en la siguiente ruta: "
								+ ruta);
			} catch (IOException ex) {
				Sesion.mensajeError(mCalculoComisiones, ex.getMessage());
			} catch (WriteException ex) {
				Sesion.mensajeError(mCalculoComisiones, ex.getMessage());
			}
		}

	}

	@SuppressWarnings("deprecation")
	private void cancelar() {
		hilo.stop();
		
		mCalculoComisiones.getProgreso().setVisible(false);
		mCalculoComisiones.getTextoProgreso().setVisible(false);
		mCalculoComisiones.getBtnCalcular().setEnabled(true);
		//***************************************************************************************************************************
		mCalculoComisiones.getBtnCerrarPeriodo().setEnabled(true);
		reinicializarGenerales();
		reinicializarMontos();
		pedidosCalculados.clear();
		comisionError.clear();
	}

	private void cerrarPeriodo(List<CalculoComision> pedidosCalculados) {
		if (pedidosCalculados.size() > 0) {
			String resultadoOperacion = "";
			try {
				int resultadoCierre = 0;
				int resultadoGrabacion = 0;
				Date desde = mCalculoComisiones.getDtpFechaDesde().getDate();
				Date hasta = mCalculoComisiones.getDtpFechaHasta().getDate();
				situacionPeriodo = comisionService.situacionPeriodo(desde,
						hasta);
				if (situacionPeriodo == -1) {
					System.out.println("insertar");
					resultadoCierre = comisionService.cerrarPeriodo(
							mCalculoComisiones.getDtpFechaDesde().getDate(),
							mCalculoComisiones.getDtpFechaHasta().getDate(),"Calculo Cominiones");
					resultadoGrabacion = comisionService
							.grabarHistorico(pedidosCalculados);
				} else if (situacionPeriodo == 0) {
					System.out.println("modificar");
					resultadoCierre = comisionService.actualizarPeriodo(
							mCalculoComisiones.getDtpFechaDesde().getDate(),
							mCalculoComisiones.getDtpFechaHasta().getDate(), 1);
					resultadoGrabacion = comisionService.actualizarHistorico(
							mCalculoComisiones.getDtpFechaDesde().getDate(),
							mCalculoComisiones.getDtpFechaHasta().getDate());
					resultadoGrabacion = comisionService
							.grabarHistorico(pedidosCalculados);
				} else if (situacionPeriodo == 1) {
					Sesion.mensajeError(mCalculoComisiones,
							"El periodo ya esta cerrado");
				}

				if (situacionPeriodo <= 0) {
					if (resultadoGrabacion > 0 && resultadoCierre > 0) {
						resultadoOperacion = "Periodo cerrado con exito";
						Sesion.mensajeInformacion(mCalculoComisiones,
								resultadoOperacion);
						mCalculoComisiones.limpiarTabla();
						mCalculoComisiones.getScpList().setVisible(false);
						mCalculoComisiones.getVenNCom().setVisible(false);
						pedidosCalculados.clear();
					} else {
						resultadoOperacion = "Problemas al intentar cerrar periodo, llamar al area de Sistemas";
						Sesion.mensajeError(mCalculoComisiones,
								resultadoOperacion);
					}
				}
			} catch (SQLException e) {
				resultadoOperacion = e.getMessage();
				Sesion.mensajeError(mCalculoComisiones, resultadoOperacion);
			}

			if (situacionPeriodo <= 0) {
				auditoria = new Auditoria();
				auditoria.setUsu(Sesion.usuario);
				auditoria.setCIA(Sesion.cia);
				auditoria.setFecha(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				auditoria.setMenu("Planillas");
				auditoria.setModulo("RR.HH");
				auditoria.setOpcion("Cálculo Comisiones");
				auditoria.setTipo_ope("Cerrar Periodo");
				auditoria.setCant_reg(pedidosXCalcular.size());
				auditoria.setRef1(resultadoOperacion);
				auditoria.setRef2(Sesion.formateaFechaVista(mCalculoComisiones
						.getDtpFechaDesde().getDate()));
				auditoria.setRef3(Sesion.formateaFechaVista(mCalculoComisiones
						.getDtpFechaHasta().getDate()));
				try {
					auditoriaService.insertarAuditoria(auditoria);
				} catch (SQLException e) {
				}
			}
		} else {
			Sesion.mensajeError(mCalculoComisiones,
					"No hay datos en la tabla para cerrar el periodo");
		}

	}

	private void exportarExcelCalculoComisiones() {
		String resultadoOperacion = "";
		if (pedidosCalculados.size() > 0) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnValue = fileChooser.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File fil = fileChooser.getSelectedFile();
				if (fil.isDirectory()) {
					String ruta = fil.toString();
					ruta = ruta + "/calculos.xls";
					File file = new File(ruta);

					try {
						DataOutputStream out = new DataOutputStream(
								new FileOutputStream(file));
						WritableWorkbook w = Workbook.createWorkbook(out);

						WritableSheet s = w.createSheet("Cálculos de comisión", 0);
						// cabecera
						WritableCellFormat wcf = new WritableCellFormat();
						wcf.setAlignment(Alignment.CENTRE);
						wcf.setBorder(Border.ALL, BorderLineStyle.THIN);

						WritableCellFormat wcfDatos = new WritableCellFormat();
						wcfDatos.setBorder(Border.ALL, BorderLineStyle.THIN);

						if (pedidosCalculados.size() > 0) {
							CellView cv0 = new CellView();
							CellView cv1 = new CellView();
							CellView cv2 = new CellView();
							CellView cv3 = new CellView();
							CellView cv4 = new CellView();
							CellView cv5 = new CellView();
							CellView cv6 = new CellView();

							cv0.setSize(2500);
							cv1.setSize(10000);// 18.86 * 2
							cv2.setSize(4200);
							cv3.setSize(4200);
							cv4.setSize(4600);
							cv5.setSize(4600);
							cv6.setSize(4600);

							s.setColumnView(0, cv0);
							s.setColumnView(1, cv1);
							s.setColumnView(2, cv2);
							s.setColumnView(3, cv3);
							s.setColumnView(4, cv4);
							s.setColumnView(5, cv5);
							s.setColumnView(6, cv6);

							// fecha de creacion
							Date fecha = Calendar.getInstance().getTime();
							String fechaFormateada = Sesion
									.formateaFechaVista(fecha);
							s.addCell(new Label(0, 1, fechaFormateada));
							// titulo
							s.addCell(new Label(2, 1,
									"Analisis y Diseño de Comisiones Mixtas"));
							// fecha de la operacion
							String fDesde = mCalculoComisiones.getFechaDesde();
							String fHasta = mCalculoComisiones.getFechaHasta();
							s.addCell(new Label(2, 3, "del: " + fDesde
									+ " al: " + fHasta));

							s.addCell(new Label(0, 5, "Vendedor", wcf));
							s.addCell(new Label(2, 5, "Ventas", wcf));
							s.addCell(new Label(4, 5, "Comisión", wcf));

							s.addCell(new Label(0, 6, "Código.", wcf));
							s.addCell(new Label(1, 6, "Nombre", wcf));
							s.addCell(new Label(2, 6, "Por Mayor", wcf));
							s.addCell(new Label(3, 6, "Por Menor", wcf));
							s.addCell(new Label(4, 6, "Por Mayor", wcf));
							s.addCell(new Label(5, 6, "Por Menor", wcf));
							s.addCell(new Label(6, 6, "Total", wcf));
							// merge
							// ds - hs
							// c,r,c,r
							s.mergeCells(0, 5, 1, 5);
							s.mergeCells(2, 5, 3, 5);
							s.mergeCells(4, 5, 6, 5);

						}

						for (int i = 0; i < pedidosCalculados.size(); i++) {
							s.addCell(new Label(0, i + 7, pedidosCalculados
									.get(i).getPhusap(), wcfDatos));
							s.addCell(new Label(1, i + 7, pedidosCalculados
									.get(i).getAgenom().trim(), wcfDatos));
							s.addCell(new jxl.write.Number(2, i + 7,
									pedidosCalculados.get(i).getVentaMayor(),
									wcfDatos));
							s.addCell(new jxl.write.Number(3, i + 7,
									pedidosCalculados.get(i).getVentaMenor(),
									wcfDatos));
							s.addCell(new jxl.write.Number(
									4,
									i + 7,
									pedidosCalculados.get(i).getComisionMayor(),
									wcfDatos));
							s.addCell(new jxl.write.Number(
									5,
									i + 7,
									pedidosCalculados.get(i).getComisionMenor(),
									wcfDatos));
							s.addCell(new jxl.write.Number(
									6,
									i + 7,
									pedidosCalculados.get(i).getTotalComision(),
									wcfDatos));

						}
						/*
						s.addCell(new Label(1, pedidosCalculados.size() + 9, "Total Ventas X Mayor y Mennor", wcfDatos));
						s.addCell(new Label(
									2,
									pedidosCalculados.size() + 9,
									mCalculoComisiones.getVentaGeneral(),
									wcfDatos));
						s.addCell(new Label(1, pedidosCalculados.size() + 10, "Total Comisión X Mayor y Mennor", wcfDatos));
						s.addCell(new Label(
								2,
								pedidosCalculados.size() + 10,
								mCalculoComisiones.getComisionGral(),
								wcfDatos));
						
						s.addCell(new Label(1, pedidosCalculados.size() + 11, "Total Comisión X Mayor", wcfDatos));
						s.addCell(new Label(
								2,
								pedidosCalculados.size() + 11,
								mCalculoComisiones.getComisionXMayorGral(),
								wcfDatos));
						s.addCell(new Label(1, pedidosCalculados.size() + 12, "Total Comisión X Mennor", wcfDatos));
						s.addCell(new Label(
								2,
								pedidosCalculados.size() + 12,
								mCalculoComisiones.getComisionXMenorGral(),
								wcfDatos));
						*/
						
						WritableSheet s2 = w.createSheet("Artículos No Comisionados", 1);

						int fila = 2;
						// no comisionados
						CellView cv2 = new CellView();
						CellView cv3 = new CellView();
						CellView cv4 = new CellView();
						CellView cv5 = new CellView();
						CellView cv6 = new CellView();
						CellView cv7 = new CellView();
					
			
						cv2.setSize(4000);
						cv3.setSize(9500);
						cv4.setSize(2000);
						cv5.setSize(3000);
						cv6.setSize(7000);
						cv7.setSize(27500);

						
						s2.setColumnView(0, cv3);
						s2.setColumnView(1, cv4);
						s2.setColumnView(2, cv5);
						s2.setColumnView(3, cv4);
						s2.setColumnView(4, cv5);
						s2.setColumnView(5, cv5);
						s2.setColumnView(6, cv5);
						s2.setColumnView(7, cv5);
						s2.setColumnView(8, cv3);
						s2.setColumnView(9, cv6);
						s2.setColumnView(10, cv2);
						s2.setColumnView(11, cv6);
						s2.setColumnView(12, cv6);
						s2.setColumnView(13, cv5);
						s2.setColumnView(14, cv7);

						s2.addCell(new Label(0, 1, "Vendedor", wcf));
						s2.addCell(new Label(1, 1, "P.V", wcf));
						s2.addCell(new Label(2, 1, "Nº Doc.", wcf));
						s2.addCell(new Label(3, 1, "T.D", wcf));
						s2.addCell(new Label(4, 1, "Fecha", wcf));
						s2.addCell(new Label(5, 1, "V. Venta", wcf));
						s2.addCell(new Label(6, 1, "Dsct.", wcf));
						s2.addCell(new Label(7, 1, "% Dsct.", wcf));
						s2.addCell(new Label(8, 1, "Familia", wcf));
						s2.addCell(new Label(9, 1, "Sub Familia", wcf));
						s2.addCell(new Label(10, 1, "Cod.Art.", wcf));
						s2.addCell(new Label(11, 1, "Articulo", wcf));
						s2.addCell(new Label(12, 1, "Cod. Equivalente", wcf));
						s2.addCell(new Label(13, 1, "Motivo", wcf));
						s2.addCell(new Label(14, 1, "Clasificación", wcf));
						
						for (int i = 0; i < pedidosXCalcular.size(); i++) {
							if (pedidosXCalcular.get(i).isFlag() == false) {
								s2.addCell(new Label(0, fila, pedidosXCalcular
										.get(i).getAgenom().trim(), wcfDatos));
								s2.addCell(new jxl.write.Number(1, fila,
										pedidosXCalcular.get(i).getPhpvta(),
										wcfDatos));
								s2.addCell(new jxl.write.Number(2, fila,
										pedidosXCalcular.get(i).getPdfabo(),
										wcfDatos));
								s2.addCell(new Label(3, fila, pedidosXCalcular
										.get(i).getPdtdoc().trim(), wcfDatos));
								String fecha = pedidosXCalcular
										.get(i).getPdfecf() + "";
								s2.addCell(new Label(4, fila, fecha.substring(6, 8) + "/"
										+ fecha.substring(4, 6) + "/"
										+ fecha.substring(0, 4), wcfDatos));
								s2.addCell(new jxl.write.Number(5, fila,
										pedidosXCalcular.get(i).getNvva(),
										wcfDatos));
								s2.addCell(new jxl.write.Number(6, fila,
										pedidosXCalcular.get(i).getNds2(),
										wcfDatos));
								s2.addCell(new jxl.write.Number(7, fila,Integer.parseInt(
										pedidosXCalcular.get(i).getRef1()),
										wcfDatos));
								s2.addCell(new Label(8, fila, pedidosXCalcular
										.get(i).getDescArtFam().trim()+"("+pedidosXCalcular.get(i).getArtfam().trim()+")", wcfDatos));
								s2.addCell(new Label(9, fila, pedidosXCalcular
										.get(i).getDescArtSFam().trim()+"("+pedidosXCalcular.get(i).getArtsfa().trim()+")", wcfDatos));
								s2.addCell(new Label(10, fila, pedidosXCalcular
										.get(i).getArti().trim(), wcfDatos));
								s2.addCell(new Label(11, fila, pedidosXCalcular
										.get(i).getArtdes().trim(), wcfDatos));
								s2.addCell(new Label(12, fila, pedidosXCalcular
										.get(i).getArtequ().trim(), wcfDatos));
								s2.addCell(new Label(13, fila, pedidosXCalcular
										.get(i).getTipoVenta().trim(), wcfDatos));
								s2.addCell(new Label(14, fila, pedidosXCalcular
										.get(i).getClasificacion().trim(), wcfDatos));
								fila++;
								}
						}
						
						for (int i = 0; i < notaCreditosXCalcular.size(); i++) {
							if (notaCreditosXCalcular.get(i).isFlag() == false) {
							s2.addCell(new Label(0, fila, notaCreditosXCalcular
										.get(i).getAgenom().trim(), wcfDatos));
							s2.addCell(new jxl.write.Number(1, fila,
									notaCreditosXCalcular.get(i).getPhpvta(),
										wcfDatos));
							s2.addCell(new jxl.write.Number(2, fila,
									notaCreditosXCalcular.get(i).getPdfabo(),
										wcfDatos));
							s2.addCell(new Label(3, fila, notaCreditosXCalcular
										.get(i).getPdtdoc().trim(), wcfDatos));
							String fecha = notaCreditosXCalcular
										.get(i).getPdfecf() + "";
							s2.addCell(new Label(4, fila, fecha.substring(6, 8) + "/"
										+ fecha.substring(4, 6) + "/"
										+ fecha.substring(0, 4), wcfDatos));
							s2.addCell(new jxl.write.Number(5, fila,
									notaCreditosXCalcular.get(i).getNvva(),
										wcfDatos));
							s2.addCell(new jxl.write.Number(6, fila,
									notaCreditosXCalcular.get(i).getNds2(),
										wcfDatos));
							s2.addCell(new jxl.write.Number(7, fila,Integer.parseInt(
									notaCreditosXCalcular.get(i).getRef1()),
										wcfDatos));
							s2.addCell(new Label(8, fila, notaCreditosXCalcular
										.get(i).getDescArtFam().trim()+"("+notaCreditosXCalcular.get(i).getArtfam().trim()+")", wcfDatos));
							s2.addCell(new Label(9, fila, notaCreditosXCalcular
										.get(i).getDescArtSFam().trim()+"("+notaCreditosXCalcular.get(i).getArtsfa().trim()+")", wcfDatos));
							s2.addCell(new Label(10, fila, notaCreditosXCalcular
										.get(i).getArti().trim(), wcfDatos));
							s2.addCell(new Label(11, fila, notaCreditosXCalcular
										.get(i).getArtdes().trim(), wcfDatos));
							s2.addCell(new Label(12, fila, notaCreditosXCalcular
										.get(i).getArtequ().trim(), wcfDatos));
							s2.addCell(new Label(13, fila, notaCreditosXCalcular
										.get(i).getTipoVenta().trim(), wcfDatos));
							s2.addCell(new Label(14, fila, notaCreditosXCalcular
										.get(i).getClasificacion().trim(), wcfDatos));
								fila++;
							}
						}
						/*
						if (fila == 4) {
							w.removeSheet(1);
						}*/
						/*WritableSheet s3 = w.createSheet("Vendedores No Comisionados", 2);
						CellView cv11 = new CellView();
						cv11.setSize(10000);
						s3.setColumnView(0, cv11);
						s3.addCell(new Label(0, 1, "Vendedores No Comisionados"));
						s3.addCell(new Label(0, 3, "Vendedor", wcf));
						*/
						fila = 2;
						WritableSheet s3 = w.createSheet(
								"Detalle de cálculos de comisión", 2);

						// tamaño de celdas
						CellView ccv2 = new CellView();
						CellView ccv3 = new CellView();
						CellView ccv4 = new CellView();
						CellView ccv5 = new CellView();
						CellView ccv6 = new CellView();
						CellView ccv7 = new CellView();
					
			
						ccv2.setSize(4000);
						ccv3.setSize(9500);
						ccv4.setSize(2000);
						ccv5.setSize(3000);
						ccv6.setSize(7000);
						ccv7.setSize(27500);
						
						s3.setColumnView(0, ccv3);
						s3.setColumnView(1, ccv4);
						s3.setColumnView(2, ccv5);
						s3.setColumnView(3, ccv4);
						s3.setColumnView(4, ccv5);
						s3.setColumnView(5, ccv5);
						s3.setColumnView(6, ccv5);
						s3.setColumnView(7, ccv5);
						s3.setColumnView(8, ccv3);
						s3.setColumnView(9, ccv6);
						s3.setColumnView(10, ccv2);
						s3.setColumnView(11, ccv6);
						s3.setColumnView(12, ccv6);
						s3.setColumnView(13, ccv5);
						s3.setColumnView(14, ccv7);
						// cabecera
						s3.addCell(new Label(0, 1, "Vendedor", wcf));
						s3.addCell(new Label(1, 1, "P.V", wcf));
						s3.addCell(new Label(2, 1, "Nº Doc.", wcf));
						s3.addCell(new Label(3, 1, "T.D", wcf));
						s3.addCell(new Label(4, 1, "Fecha", wcf));
						s3.addCell(new Label(5, 1, "V. Venta", wcf));
						s3.addCell(new Label(6, 1, "Dsct.", wcf));
						s3.addCell(new Label(7, 1, "% Dsct.", wcf));
						s3.addCell(new Label(8, 1, "Familia", wcf));
						s3.addCell(new Label(9, 1, "Sub Familia", wcf));
						s3.addCell(new Label(10, 1, "Cod.Art.", wcf));
						s3.addCell(new Label(11, 1, "Articulo", wcf));
						s3.addCell(new Label(12, 1, "Cod. Equivalente", wcf));
						s3.addCell(new Label(13, 1, "Motivo", wcf));
						s3.addCell(new Label(14, 1, "Clasificación", wcf));
						
						System.out.println(pedidosXCalcular.size());
						for (int i = 0; i < pedidosXCalcular.size(); i++) {
							s3.addCell(new Label(0, fila, pedidosXCalcular
										.get(i).getAgenom().trim(), wcfDatos));
							s3.addCell(new jxl.write.Number(1, fila,
										pedidosXCalcular.get(i).getPhpvta(),
										wcfDatos));
							s3.addCell(new jxl.write.Number(2, fila,
										pedidosXCalcular.get(i).getPdfabo(),
										wcfDatos));
							s3.addCell(new Label(3, fila, pedidosXCalcular
										.get(i).getPdtdoc().trim(), wcfDatos));
							String fecha = pedidosXCalcular
										.get(i).getPdfecf() + "";
							s3.addCell(new Label(4, fila, fecha.substring(6, 8) + "/"
										+ fecha.substring(4, 6) + "/"
										+ fecha.substring(0, 4), wcfDatos));
							s3.addCell(new jxl.write.Number(5, fila,
										pedidosXCalcular.get(i).getNvva(),
										wcfDatos));
							s3.addCell(new jxl.write.Number(6, fila,
										pedidosXCalcular.get(i).getNds2(),
										wcfDatos));
							s3.addCell(new jxl.write.Number(7, fila,Integer.parseInt(
										pedidosXCalcular.get(i).getRef1()),
										wcfDatos));
							s3.addCell(new Label(8, fila, pedidosXCalcular
										.get(i).getDescArtFam().trim()+"("+pedidosXCalcular.get(i).getArtfam().trim()+")", wcfDatos));
							s3.addCell(new Label(9, fila, pedidosXCalcular
										.get(i).getDescArtSFam().trim()+"("+pedidosXCalcular.get(i).getArtsfa().trim()+")", wcfDatos));
							s3.addCell(new Label(10, fila, pedidosXCalcular
										.get(i).getArti().trim(), wcfDatos));
							s3.addCell(new Label(11, fila, pedidosXCalcular
										.get(i).getArtdes().trim(), wcfDatos));
							s3.addCell(new Label(12, fila, pedidosXCalcular
										.get(i).getArtequ().trim(), wcfDatos));
							s3.addCell(new Label(13, fila, pedidosXCalcular
										.get(i).getTipoVenta().trim(), wcfDatos));
							s3.addCell(new Label(14, fila, pedidosXCalcular
										.get(i).getClasificacion().trim(), wcfDatos));
								fila++;
						}
						System.out.println(notaCreditosXCalcular.size());
						for (int i = 0; i < notaCreditosXCalcular.size(); i++) {
							s3.addCell(new Label(0, fila, notaCreditosXCalcular
										.get(i).getAgenom().trim(), wcfDatos));
							s3.addCell(new jxl.write.Number(1, fila,
									notaCreditosXCalcular.get(i).getPhpvta(),
										wcfDatos));
							s3.addCell(new jxl.write.Number(2, fila,
									notaCreditosXCalcular.get(i).getPdfabo(),
										wcfDatos));
							s3.addCell(new Label(3, fila, notaCreditosXCalcular
										.get(i).getPdtdoc().trim(), wcfDatos));
							String fecha = notaCreditosXCalcular
										.get(i).getPdfecf() + "";
							s3.addCell(new Label(4, fila, fecha.substring(6, 8) + "/"
										+ fecha.substring(4, 6) + "/"
										+ fecha.substring(0, 4), wcfDatos));
							s3.addCell(new jxl.write.Number(5, fila,
									notaCreditosXCalcular.get(i).getNvva(),
										wcfDatos));
							s3.addCell(new jxl.write.Number(6, fila,
									notaCreditosXCalcular.get(i).getNds2(),
										wcfDatos));
							s3.addCell(new jxl.write.Number(7, fila,Integer.parseInt(
									notaCreditosXCalcular.get(i).getRef1()),
										wcfDatos));
							s3.addCell(new Label(8, fila, notaCreditosXCalcular
										.get(i).getDescArtFam().trim()+"("+notaCreditosXCalcular.get(i).getArtfam().trim()+")", wcfDatos));
							s3.addCell(new Label(9, fila, notaCreditosXCalcular
										.get(i).getDescArtSFam().trim()+"("+notaCreditosXCalcular.get(i).getArtsfa().trim()+")", wcfDatos));
							s3.addCell(new Label(10, fila, notaCreditosXCalcular
										.get(i).getArti().trim(), wcfDatos));
							s3.addCell(new Label(11, fila, notaCreditosXCalcular
										.get(i).getArtdes().trim(), wcfDatos));
							s3.addCell(new Label(12, fila, notaCreditosXCalcular
										.get(i).getArtequ().trim(), wcfDatos));
							s3.addCell(new Label(13, fila, notaCreditosXCalcular
										.get(i).getTipoVenta().trim(), wcfDatos));
							s3.addCell(new Label(14, fila, notaCreditosXCalcular
										.get(i).getClasificacion().trim(), wcfDatos));
								fila++;
						}
						
						w.write();
						w.close();
						out.close();
						resultadoOperacion = "Se a exportado con exito en la siguiente ruta: "
								+ ruta;
						Sesion.mensajeInformacion(mCalculoComisiones,
								resultadoOperacion);
					} catch (IOException ex) {
						resultadoOperacion = ex.getMessage();
						Sesion.mensajeError(mCalculoComisiones,
								resultadoOperacion);
					} catch (WriteException ex) {
						resultadoOperacion = ex.getMessage();
						Sesion.mensajeError(mCalculoComisiones,
								resultadoOperacion);
					}

					auditoria = new Auditoria();
					auditoria.setUsu(Sesion.usuario);
					auditoria.setCIA(Sesion.cia);
					auditoria.setFecha(new Timestamp(Calendar.getInstance()
							.getTimeInMillis()));
					auditoria.setMenu("Planillas");
					auditoria.setModulo("RR.HH");
					auditoria.setOpcion("Cálculo Comisiones");
					auditoria.setTipo_ope("Exportar Excel");
					auditoria.setCant_reg(pedidosCalculados.size());

					auditoria.setRef2(mCalculoComisiones.getDtpFechaDesde()
							.getEditor().getText());
					auditoria.setRef3(mCalculoComisiones.getDtpFechaHasta()
							.getEditor().getText());
					try {
						auditoriaService.insertarAuditoria(auditoria);
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				} else {
					Sesion.mensajeError(
							mCalculoComisiones,
							"No esta permitido darle nombre al archivo a exportar,\nsolo debe seleccionar una carpeta de destino.");
					exportarExcelCalculoComisiones();
				}
			}

		} else
			Sesion.mensajeError(mCalculoComisiones,
					"No hay registros en la tabla que exportar");

	}

	void procesar() {
		String resultadoOperacion = "";
		mCalculoComisiones.getProgreso().setIndeterminate(true);

		String fDesde = mCalculoComisiones.getFechaDesde();
		String fHasta = mCalculoComisiones.getFechaHasta();
		fDesde = fDesde.substring(6, 10) + fDesde.substring(3, 5)
				+ fDesde.substring(0, 2);
		fHasta = fHasta.substring(6, 10) + fHasta.substring(3, 5)
				+ fHasta.substring(0, 2);

		final int fechaDesde = Integer.parseInt(fDesde);
		final int fechaHasta = Integer.parseInt(fHasta);
		Date desde = mCalculoComisiones.getDtpFechaDesde().getDate();
		Date hasta = mCalculoComisiones.getDtpFechaHasta().getDate();

		if (fechaDesde <= fechaHasta) {
			procesando = true;
			pedidosCalculados.clear();
			comisionError.clear();
			reinicializarGenerales();
			mCalculoComisiones.getBtnCalcular().setEnabled(false);
			mCalculoComisiones.getBtnCerrarPeriodo().setEnabled(false);
			try {
				situacionPeriodo = comisionService.situacionPeriodo(desde,
						hasta);

				if (situacionPeriodo <= 0) {

					mCalculoComisiones.getTextoProgreso().setVisible(true);
					mCalculoComisiones.getProgreso().setVisible(true);

					mCalculoComisiones.getTextoProgreso().setText(
							"Cargando comisiones...");
					comisiones = comisionService.listarActivos(
							mCalculoComisiones.getDtpFechaDesde().getDate(),
							mCalculoComisiones.getDtpFechaHasta().getDate());
					mCalculoComisiones.limpiarTabla();
					mCalculoComisiones.limpiarTablaErrores();
					mCalculoComisiones.limpiarTablaDetalle();
					//mCalculoComisiones.getScpList().setVisible(false);
					//mCalculoComisiones.getVenNCom().setVisible(false);
					modelo.removeAllElements();
					mCalculoComisiones.getListVenNoCom().setModel(modelo);

					mCalculoComisiones.getTextoProgreso().setText(
							"Cargando pedidos...");
					System.out.println(Calendar.getInstance().getTime());
					pedidosXCalcular = comisionService.listarPedidos(
							fechaDesde, fechaHasta);
					criterios = criteriosComisionService.listar();
					excepciones = excepcionCriterioComisionService.listar();
					if (pedidosXCalcular.size() > 0) {
						resultadoOperacion = "exito";
						mCalculoComisiones.getTextoProgreso().setText(
								"Calculando pedidos...");
						calcular(pedidosXCalcular, pedidosCalculados);
					}
					reinicializarMontos();
					//******************************************************************
					mCalculoComisiones.getTextoProgreso().setText(
							"Cargando notas de credito...");
					notaCreditosXCalcular = comisionService.listarNotaCreditos(
							fechaDesde, fechaHasta);
					// si hay notas de credito
					
					if (notaCreditosXCalcular.size() > 0) {
						mCalculoComisiones.getTextoProgreso().setText(
								"Calculando notas de credito...");
						calcular(notaCreditosXCalcular, pedidosCalculados);
					}
					System.out.println(Calendar.getInstance().getTime());
					// **********************************************************************
					Comision maestro = comisionService.buscarMaestro();
					if (maestro != null) {
						for (CalculoComision calculoComision : pedidosCalculados) {
							if (calculoComision.getPhusap().trim()
									.equals(maestro.getVendedor().getCodigo().trim())) {
								calculoComision
										.setComisionMayor(calculoComision
												.getComisionMayor()
												+ (acumuladoTotalXMayor * (maestro.getComision_mayor_m()/100)));
								calculoComision
										.setComisionMenor(calculoComision
												.getComisionMenor()
												+ (acumuladoTotalXMenor * (maestro.getComision_menor_m()/100)));

								calculoComision
										.setComisionMayor(Double.parseDouble(Sesion
												.formateaDecimal_2(calculoComision
														.getComisionMayor())));
								calculoComision
										.setComisionMenor(Double.parseDouble(Sesion
												.formateaDecimal_2(calculoComision
														.getComisionMenor())));

								calculoComision
										.setTotalComision(calculoComision
												.getComisionMayor()
												+ calculoComision
														.getComisionMenor());
							}
						}
					}
					// **********************************************************************

					for (CalculoComision pedidos : pedidosCalculados) {
						pedidos.setVentaMayor(Double.parseDouble(Sesion
								.formateaDecimal_3(pedidos.getVentaMayor())));
						pedidos.setVentaMenor(Double.parseDouble(Sesion
								.formateaDecimal_3(pedidos.getVentaMenor())));
						pedidos.setComisionMayor(Double.parseDouble(Sesion
								.formateaDecimal_3(pedidos.getComisionMayor())));
						pedidos.setComisionMenor(Double.parseDouble(Sesion
								.formateaDecimal_3(pedidos.getComisionMenor())));
						pedidos.setTotalComision(pedidos.getComisionMayor()
								+ pedidos.getComisionMenor());
						pedidos.setFechaInicial(desde);
						pedidos.setFechaFinal(hasta);
						pedidos.setPeriodo(formateaMes
								.format(mCalculoComisiones.getDtpFechaHasta()
										.getDate()));
						mCalculoComisiones.cargaTabla(pedidos);
					}

					mCalculoComisiones.setVentaGeneral(Sesion
							.formatoDecimalVista(totalVentasGeneral));
					mCalculoComisiones
							.setComisionXMayorGral(Sesion
									.formatoDecimalVista(totalComisionXMayorGeneral
											+ (acumuladoTotalXMayor * (maestro.getComision_mayor_m()/100))));
					mCalculoComisiones
							.setComisionXMenorGral(Sesion
									.formatoDecimalVista(totalComisionXMenorGeneral
											+ (acumuladoTotalXMenor * (maestro.getComision_menor_m()/100))));
					mCalculoComisiones
							.setComisionGral(Sesion
									.formatoDecimalVista((totalComisionXMayorGeneral
											+ (acumuladoTotalXMayor * (maestro.getComision_mayor_m()/100)) + totalComisionXMenorGeneral)
											+ (acumuladoTotalXMenor * (maestro.getComision_menor_m()/100))));

					mCalculoComisiones.limpiarTablaErrores();
					for (CalculoComision calculoComision : pedidosXCalcular) {
						if (calculoComision.isFlag() == false) {
							mCalculoComisiones
									.cargarTablaErrores(calculoComision);
						}
						mCalculoComisiones.cargarTablaDetalle(calculoComision);
					}
					
					for (CalculoComision calculoComision : notaCreditosXCalcular) {
						if (calculoComision.isFlag() == false) {
							mCalculoComisiones
									.cargarTablaErrores(calculoComision);
						}
						mCalculoComisiones.cargarTablaDetalle(calculoComision);
					}

					/*
					if (mCalculoComisiones.getListVenNoCom().getModel()
							.getSize() > 0) {
						mCalculoComisiones.getScpList().setVisible(true);
						mCalculoComisiones.getVenNCom().setVisible(true);
					}*/

					if (notaCreditosXCalcular.size() == 0
							&& pedidosXCalcular.size() == 0) {
						mCalculoComisiones.setVentaGeneral("");
						mCalculoComisiones.setComisionXMayorGral("");
						mCalculoComisiones.setComisionXMenorGral("");
						mCalculoComisiones.setComisionGral("");
						resultadoOperacion = "No existen resultados para el rango de fechas ingresado";
						Sesion.mensajeInformacion(mCalculoComisiones,
								resultadoOperacion);
					}
					mCalculoComisiones.getTextoProgreso().setVisible(false);
					mCalculoComisiones.getProgreso().setVisible(false);
					
					auditoria = new Auditoria();
					auditoria.setUsu(Sesion.usuario);
					auditoria.setCIA(Sesion.cia);
					auditoria.setFecha(new Timestamp(Calendar.getInstance()
							.getTimeInMillis()));
					auditoria.setModulo("RR.HH");
					auditoria.setMenu("Planillas");
					auditoria.setOpcion("Cálculo Comisiones");
					auditoria.setTipo_ope("Consulta");
					auditoria.setCant_reg(pedidosXCalcular.size()+notaCreditosXCalcular.size());
					auditoria.setRef1(resultadoOperacion);
					auditoria.setRef2(fDesde);
					auditoria.setRef3(fHasta);
					try {
						auditoriaService.insertarAuditoria(auditoria);
					} catch (SQLException e) {
					}
				} else {
					resultadoOperacion = "El periodo ha sido cerrado";
					Sesion.mensajeInformacion(mCalculoComisiones,
							resultadoOperacion);
				}
				
			} catch (SQLException e) {
				Sesion.mensajeError(mCalculoComisiones, e.getMessage()
						+ "codigo 3");
				mCalculoComisiones.getBtnCalcular().setEnabled(true);
			} catch (OutOfMemoryError e) {
				cancelar();
				System.out.println(e.getMessage());
				Sesion.mensajeError(mCalculoComisiones,
						"menoria insuficiente para esta consulta \n");
			} catch (Exception e) {
				Sesion.mensajeError(mCalculoComisiones, e.getMessage()
						+ "codigo 4");
				mCalculoComisiones.getBtnCalcular().setEnabled(true);
				mCalculoComisiones.getBtnCerrarPeriodo().setEnabled(true);
			}

			mCalculoComisiones.getBtnCalcular().setEnabled(true);
			mCalculoComisiones.getBtnCerrarPeriodo().setEnabled(true);
			mCalculoComisiones.getProgreso().setIndeterminate(false);
			mCalculoComisiones.getTextoProgreso().setText("");
			procesando = false;
		} else
			Sesion.mensajeError(mCalculoComisiones,
					"Fecha desde debe ser mayor a fecha hasta");
	}

	private void calcular(List<CalculoComision> xCalcular,
			List<CalculoComision> calculados) {
		String codVend = xCalcular.get(0).getPhusap();
		String nomVend = xCalcular.get(0).getAgenom();
		Comision comision = actualizaComision(codVend, nomVend);

		for (CalculoComision calcular : xCalcular) {
			if (codVend.equals(calcular.getPhusap())) {
				calculaComision(calcular);
		} 
			else {

				if (comision.getAcumula().trim().equals("T")) {
					acumuladoTotalXMayor += totalVentaXMayor;
					acumuladoTotalXMenor += totalVentaXMenor;
				}
				grabar(codVend, nomVend);
				codVend = calcular.getPhusap();
				nomVend = calcular.getAgenom();
				comision = actualizaComision(codVend, nomVend);
				calculaComision(calcular);
			}
		}
		if (comision.getAcumula().trim().equals("T")) {
			acumuladoTotalXMayor += totalVentaXMayor;
			acumuladoTotalXMenor += totalVentaXMenor;
		}
		grabar(codVend, nomVend);
	}

	void grabar(String codVend, String nomVend) {
		CalculoComision calculado = buscarPedidoCalculados(codVend);
		if (calculado == null) {
			comisionCalculada = new CalculoComision();
			comisionCalculada.setPhusap(codVend.trim());
			comisionCalculada.setAgenom(nomVend);
			comisionCalculada.setVentaMayor(totalVentaXMayor);
			comisionCalculada.setVentaMenor(totalVentaXMenor);
			comisionCalculada.setComisionMayor(totalComisionXMayor);
			comisionCalculada.setComisionMenor(totalComisionXMenor);
			pedidosCalculados.add(comisionCalculada);
		} else {
			calculado.setVentaMayor(calculado.getVentaMayor()
					+ totalVentaXMayor);
			calculado.setVentaMenor(calculado.getVentaMenor()
					+ totalVentaXMenor);
			calculado.setComisionMayor(calculado.getComisionMayor()
					+ totalComisionXMayor);
			calculado.setComisionMenor(calculado.getComisionMenor()
					+ totalComisionXMenor);
		}
		totalVentasGeneral += totalVentaXMayor + totalVentaXMenor;
		totalComisionXMayorGeneral += totalComisionXMayor;
		totalComisionXMenorGeneral += totalComisionXMenor;
		reinicializarMontos();
	}

	CalculoComision buscarPedidoCalculados(String codVend) {
		CalculoComision calculo = null;
		if (pedidosCalculados.size() > 0) {
			for (CalculoComision calculoComision : pedidosCalculados) {
				if (calculoComision.getPhusap().trim().equals(codVend.trim())) {
					return calculoComision;
				}
			}
		}
		return calculo;
	}

	Comision actualizaComision(String cod, String nombre) {
		for (int i = 0; i < comisiones.size(); i++) {
			if (comisiones.get(i).getVendedor().getCodigo().trim()
					.equals(cod.trim())) {
				comisionXMayor = (comisiones.get(i).getComision_mayor() / 100);
				comisionXMenor = (comisiones.get(i).getComision_menor() / 100);
				return comisiones.get(i);
			}
		}

		Comision comision = new Comision();
		comisionXMayor = 0.0;
		comisionXMenor = 0.0;
		comision.setAcumula("F");
		modelo.addElement(cod + "-" + nombre);
		mCalculoComisiones.getListVenNoCom().setModel(modelo);
		return comision;
	}

	void calculaComision(CalculoComision calculoComision) {
		boolean flag = false;
		for (int i = 0; i < criterios.size(); i++) {
			//******************************* familia subfamilia origen
			if (criterios.get(i).getCodfam().trim()
					.equalsIgnoreCase(calculoComision.getArtfam().trim())
					&& criterios
							.get(i)
							.getCodsfa()
							.trim()
							.equalsIgnoreCase(
									calculoComision.getArtsfa().trim())
					&& criterios
							.get(i)
							.getCodori()
							.trim()
							.equalsIgnoreCase(
									calculoComision.getOrigen().trim())) {
				formula(calculoComision,
						criterios.get(i).getValor(),criterios.get(i));
				flag = true;
				break;
			}
		}
		
		if (flag == false) {
			for (int i = 0; i < criterios.size(); i++) {
				//******************************* familia subfamilia todos
				if (criterios.get(i).getCodfam().trim()
						.equalsIgnoreCase(calculoComision.getArtfam().trim())
						&& criterios
								.get(i)
								.getCodsfa()
								.trim()
								.equalsIgnoreCase(
										calculoComision.getArtsfa().trim())
						&& criterios.get(i).getCodori().equalsIgnoreCase("000")) {
					boolean excepcionExiste = false;

					String excepcion = calculoComision.getArtfam().trim()
							+ calculoComision.getArtsfa().trim()
							+ calculoComision.getOrigen();

					String criterio = calculoComision.getArtfam().trim()
							+ calculoComision.getArtsfa().trim() + "000";

					for (int x = 0; x < excepciones.size(); x++) {
						if (excepciones.get(x).getCriterio().trim()
								.equals(criterio)
								&& excepciones.get(x).getExcepcion().trim()
										.equals(excepcion)) {
							excepcionExiste = true;
							break;
						}
					}
					if (excepcionExiste == false) {
						formula(calculoComision,
								criterios.get(i).getValor(),criterios.get(i));
						flag = true;
						break;
					}
				}
			}
			if (flag == false) {
				for (int i = 0; i < criterios.size(); i++) {
					//***************** familia todos origen
					if (criterios
							.get(i)
							.getCodfam()
							.trim()
							.equalsIgnoreCase(
									calculoComision.getArtfam().trim())
							&& criterios.get(i).getCodsfa()
									.equalsIgnoreCase("000")
							&& criterios
									.get(i)
									.getCodori()
									.trim()
									.equalsIgnoreCase(
											calculoComision.getOrigen().trim())) {
						boolean excepcionExiste = false;
						String excepcion = calculoComision.getArtfam().trim()
								+ calculoComision.getArtsfa().trim()
								+ calculoComision.getOrigen();
						String criterio = calculoComision.getArtfam().trim()
								+ "000" + calculoComision.getOrigen();

						for (int x = 0; x < excepciones.size(); x++) {
							if (excepciones.get(x).getCriterio()
									.equals(criterio)
									&& excepciones.get(x).getExcepcion()
											.equals(excepcion)) {
								excepcionExiste = true;
								break;
							}
						}
						if (excepcionExiste == false) {
							formula(calculoComision, criterios.get(i)
									.getValor(),criterios.get(i));
							flag = true;
							break;
						}
					}
				}
				if (flag == false) {
					boolean flagTodos = false;
					for (int i = 0; i < criterios.size(); i++) {
						//***************** familia todos todos
						if (criterios
								.get(i)
								.getCodfam()
								.trim()
								.equalsIgnoreCase(
										calculoComision.getArtfam().trim())
								&& criterios.get(i).getCodsfa()
										.equalsIgnoreCase("000")
								&& criterios.get(i).getCodori()
										.equalsIgnoreCase("000")) {
							flagTodos = true;
							break;
						}
					}

					if (flagTodos == true) {
						for (int i = 0; i < criterios.size(); i++) {
							if (criterios
									.get(i)
									.getCodfam()
									.trim()
									.equalsIgnoreCase(
											calculoComision.getArtfam().trim())
									&& criterios.get(i).getCodsfa()
											.equalsIgnoreCase("000")
									&& criterios.get(i).getCodori()
											.equalsIgnoreCase("000")) {
								boolean excepcionExiste = false;
								String excepcion = calculoComision.getArtfam()
										.trim()
										+ calculoComision.getArtsfa().trim()
										+ calculoComision.getOrigen();
								String criterio = calculoComision.getArtfam()
										.trim() + "000" + "000";
								for (int x = 0; x < excepciones.size(); x++) {
									if (excepciones.get(x).getCriterio()
											.equals(criterio)
											&& excepciones.get(x)
													.getExcepcion()
													.equals(excepcion)) {
										excepcionExiste = true;
										break;
									}
								}
								if (excepcionExiste == false) {
									formula(calculoComision, criterios.get(i)
											.getValor(),criterios.get(i));
									flag = true;
									break;
								}

							}
						}
					}
				}
			}
			
		}
		
		if(flag==false){
			calculoComision.setTipoVenta("Clasificación");
			calculoComision.setFlag(false);
			calculoComision.setClasificacion("Ninguna");
		}

	}

	void formula(CalculoComision calculoComision, double xMayor,TCCM criterio) {
		double porcentajeDescuento = Double.parseDouble(calculoComision
				.getRef1()) / 100;
		// x Mayor
		if (porcentajeDescuento >= xMayor) {
			totalVentaXMayor += (calculoComision.getNvva() - calculoComision
					.getNds2());
			totalComisionXMayor += ((calculoComision.getNvva() - calculoComision
					.getNds2()) * comisionXMayor);
			calculoComision.setTipoVenta("Por Mayor");
			calculoComision.setFlag(true);
			calculoComision.setClasificacion("Familia: " + criterio.getDesfam() + " y Sub Familia: " + criterio.getDessfa() + " y Origen: " + criterio.getDesori() + " y %dsct>=" + criterio.getValor());
		}
		// x Menor
		else {
			boolean flag = true;
			if(calculoComision.getArtfam().equalsIgnoreCase("008") && calculoComision.getArtsfa().equalsIgnoreCase("009") && porcentajeDescuento>40.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Motores y %dsct > 40");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("001") && porcentajeDescuento>20.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Motocicletas y %dsct > 20");
			}  
			else if(calculoComision.getArtfam().equalsIgnoreCase("012") && (calculoComision.getOrigen().equalsIgnoreCase("TWN") || calculoComision.getOrigen().equalsIgnoreCase("TWM")) && porcentajeDescuento>35.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Repuestos de Motocicletas o Bicicletas y Origen: TWN o TWM y %dsct > 35");
			}  
			else if(calculoComision.getArtfam().equalsIgnoreCase("013") && calculoComision.getOrigen().equalsIgnoreCase("TWN")  && porcentajeDescuento>35.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Repuestos de Motocicletas o Bicicletas y Origen: TWN o TWM y %dsct > 35");
			} 
			else if((calculoComision.getArtfam().equalsIgnoreCase("013") || calculoComision.getArtfam().equalsIgnoreCase("012") || calculoComision.getArtfam().equalsIgnoreCase("011") || calculoComision.getArtfam().equalsIgnoreCase("005")) && calculoComision.getOrigen().equalsIgnoreCase("JPN")  && porcentajeDescuento>25.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Repuestos de Bicicletas o Motocicletas o Aros de Motocicletas o Cadenas de Motocicletas y Origen: JPN y %dsct > 25");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("002") && calculoComision.getArtsfa().equalsIgnoreCase("001") && (calculoComision.getOrigen().equalsIgnoreCase("CHN")|| calculoComision.getOrigen().equalsIgnoreCase("TWN") || calculoComision.getOrigen().equalsIgnoreCase("VTM") ) && porcentajeDescuento>25.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Llantas o Camaras y Sub Familia:Kenda y Origen: CHN o TWN o VTM y %dsct > 25");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("003") && calculoComision.getArtsfa().equalsIgnoreCase("001") && (calculoComision.getOrigen().equalsIgnoreCase("CHN")|| calculoComision.getOrigen().equalsIgnoreCase("TWN") || calculoComision.getOrigen().equalsIgnoreCase("VTM")) && porcentajeDescuento>25.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Llantas o Camaras y Sub Familia:Kenda y Origen: CHN o TWN o VTM y %dsct > 25");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("006") && calculoComision.getArtsfa().equalsIgnoreCase("002") && (calculoComision.getOrigen().equalsIgnoreCase("CHN")|| calculoComision.getOrigen().equalsIgnoreCase("TWN") || calculoComision.getOrigen().equalsIgnoreCase("VTM")) && porcentajeDescuento>25.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Llantas o Camaras y Sub Familia:Kenda y Origen: CHN o TWN o VTM y %dsct > 25");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("007") && calculoComision.getArtsfa().equalsIgnoreCase("002") && (calculoComision.getOrigen().equalsIgnoreCase("CHN")|| calculoComision.getOrigen().equalsIgnoreCase("TWN") || calculoComision.getOrigen().equalsIgnoreCase("VTM")) && porcentajeDescuento>25.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Llantas o Camaras y Sub Familia:Kenda y Origen: CHN o TWN o VTM y %dsct > 25");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("002") && calculoComision.getArtsfa().equalsIgnoreCase("002") && porcentajeDescuento>15.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Llantas o Camaras y Sub Familia: Cheng Shin y %dsct > 15");
			} 			
			else if(calculoComision.getArtfam().equalsIgnoreCase("003") && calculoComision.getArtsfa().equalsIgnoreCase("002") && porcentajeDescuento>15.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Llantas o Camaras y Sub Familia: Cheng Shin y %dsct > 15");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("006") && calculoComision.getArtsfa().equalsIgnoreCase("001") && porcentajeDescuento>15.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Llantas o Camaras y Sub Familia: Cheng Shin y %dsct > 15");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("007") && calculoComision.getArtsfa().equalsIgnoreCase("001") && porcentajeDescuento>15.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Llantas o Camaras y Sub Familia: Cheng Shin y %dsct > 15");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("004") && calculoComision.getArtsfa().equalsIgnoreCase("001") && porcentajeDescuento>35.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Cadenas de Bicicletas o Motocicletas y Sub Familia: KMC y %dsct > 35");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("005") && calculoComision.getArtsfa().equalsIgnoreCase("001") && porcentajeDescuento>35.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Cadenas de Bicicletas o Motocicletas y Sub Familia: KMC y %dsct > 35");
			} 
			//*************************************
			else if(calculoComision.getArtfam().equalsIgnoreCase("012") && calculoComision.getArtsfa().equalsIgnoreCase("015") && calculoComision.getOrigen().equalsIgnoreCase("VTM") && porcentajeDescuento>35.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Repuestos de Motocicletas y Sub Familia: Baterías y Origen: VTM y %dsct > 35");
			} 
			else if(calculoComision.getArtfam().equalsIgnoreCase("005") && calculoComision.getArtsfa().equalsIgnoreCase("003") && (calculoComision.getOrigen().equalsIgnoreCase("BRS")||calculoComision.getOrigen().equalsIgnoreCase("TAI") ) && porcentajeDescuento>25.000){
				flag = false;
				calculoComision.setClasificacion("Familia: Cadenas de Motocicletas y Sub Familia: DID y Origen: BRS o TAI y %dsct > 25");
			} 
			//*************************************			
			else if(calculoComision.getArtequ()!=null){
				if((calculoComision.getArtfam().equalsIgnoreCase("007") || calculoComision.getArtfam().equalsIgnoreCase("006") || calculoComision.getArtfam().equalsIgnoreCase("003") || calculoComision.getArtfam().equalsIgnoreCase("002")) && (calculoComision.getArtequ().contains("SW") || calculoComision.getArtequ().contains("sw") ) && porcentajeDescuento>25.000){
					flag = false;
					calculoComision.setClasificacion("Familia: Llantas o Camaras y Cod. Equivalente Contiene: SW y %dsct > 25");
				} 
				
				else if((calculoComision.getArtfam().equalsIgnoreCase("005") || calculoComision.getArtfam().equalsIgnoreCase("004")) && (calculoComision.getArtequ().contains("ACC")||calculoComision.getArtequ().contains("acc")) && porcentajeDescuento>35.000){
					flag = false;
					calculoComision.setClasificacion("Familia: Cadenas de Bicicletas o Motocicletas y Cod. Equivalente Contiene: ACC y %dsct > 35");
				}
				
				else if(calculoComision.getArtdes()!=null){
					if(calculoComision.getArtdes().trim().length()>=4){
						if(calculoComision.getArtdes().trim().substring(0, 4).equalsIgnoreCase("CATA") && (calculoComision.getArtequ().contains("TEC") || calculoComision.getArtequ().contains("MIB") || calculoComision.getArtequ().contains("TSK") || calculoComision.getArtequ().contains("tec") || calculoComision.getArtequ().contains("mib") || calculoComision.getArtequ().contains("tsk")) && porcentajeDescuento>35.000){
							flag = false;
							calculoComision.setClasificacion("Artículo Empieza Con: CATA y Cod. Equivalente Contiene: TEC o MIB o TSK y %dsct > 35");
						}
					}
				}
			} 
			if(flag==true){
			totalVentaXMenor += (calculoComision.getNvva() - calculoComision
					.getNds2());
			totalComisionXMenor += ((calculoComision.getNvva() - calculoComision
					.getNds2()) * comisionXMenor);
			calculoComision.setTipoVenta("Por Menor");
			calculoComision.setFlag(true);
			calculoComision.setClasificacion("Familia: " + criterio.getDesfam() + " y Sub Familia: " + criterio.getDessfa() + " y Origen: " + criterio.getDesori() + " y %dsct<" + criterio.getValor());
			}else{
				calculoComision.setTipoVenta("Intervalo");
				calculoComision.setFlag(false);
			}
		}
	}

	void reinicializarMontos() {
		totalVentaXMayor = 0;
		totalVentaXMenor = 0;
		totalComisionXMayor = 0;
		totalComisionXMenor = 0;
	}

	void reinicializarGenerales() {
		totalVentasGeneral = 0;
		totalComisionXMayorGeneral = 0;
		totalComisionXMenorGeneral = 0;
		acumuladoTotalXMayor = 0;
		acumuladoTotalXMenor = 0;
	}
}
