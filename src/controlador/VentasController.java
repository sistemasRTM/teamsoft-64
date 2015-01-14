package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

import delegate.GestionSeguridad;
import delegate.GestionVentas;
import bean.Auditoria;
import bean.RegistroVentas;
import bean.TREGV;
import bean.TTIVC;
import recursos.Sesion;
import servicio.AuditoriaService;
import servicio.CorrelativoService;
import servicio.VentasService;
import ventanas.FIRegistroVentas;

public class VentasController implements ActionListener {

	FIRegistroVentas mVentas = new FIRegistroVentas();
	VentasService servicio = GestionVentas.getVentasService();
	CorrelativoService correlativoService = GestionVentas
			.getCorrelativoService();
	// ComprasService service = GestionCompras.getComprasService();
	TTIVC correlativo;
	TREGV tregv = new TREGV();
	RegistroVentas reporte = null;
	// datos para el detalle de errores
	RegistroVentas reporteError = null;
	// lo que se visualiza en la primera tabla y se exportara
	List<RegistroVentas> reporteTable = new ArrayList<RegistroVentas>();
	// lo que se visualiza en el txt de errores
	List<RegistroVentas> reporteTxtError = new ArrayList<RegistroVentas>();
	// lo que se visualiza en la tabla detalle de errores
	List<RegistroVentas> detalleErrores = new ArrayList<RegistroVentas>();
	// lo que se visualiza en la tabla de totales
	List<RegistroVentas> reporteTotales = new ArrayList<RegistroVentas>();
	AuditoriaService auditoriaService = GestionSeguridad.getAuditoriaService();
	Auditoria auditoria;
	
	int periodoInformado = 0;
	int totalRegistros = 0;
	int wSecuTabla = 0;
	int wSecuTXT = 0;
	String fechaDefault = "01/01/0001";
	String alfaDefault = "-";
	String montoDefault = "0.00";
	String cantDefault = "0";
	// datos para el quiebre
	String tipoDocumento = "";
	String descDocumento = "";
	//
	JFileChooser fileChooser;
	String ruta = "";// ruta de la carpeta donde se ubicara el archivo
	String archivo = "";// nombre del archivo txt
	String extension = ".txt";// extension del archivo
	// acumuladores por tipo de documento
	double TDVFE = 0;
	double TDBI1 = 0;
	double TDITE = 0;
	double TDITI = 0;
	double TDISC = 0;
	double TDIG1 = 0;
	double TDBI2 = 0;
	double TDIVE = 0;
	double TDOTC = 0;
	double TDITO = 0;
	// acumuladores totales
	double TGVFE = 0;
	double TGBI1 = 0;
	double TGITE = 0;
	double TGITI = 0;
	double TGISC = 0;
	double TGIG1 = 0;
	double TGBI2 = 0;
	double TGIVE = 0;
	double TGOTC = 0;
	double TGITO = 0;
	//
	NumberFormat redondea = NumberFormat.getInstance();
	DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
	DecimalFormat formateador;

	public void setVista(FIRegistroVentas mVentas) {
		this.mVentas = mVentas;
		simbolos.setDecimalSeparator('.');
		formateador = new DecimalFormat("####.##", simbolos);
		formateador.setMaximumFractionDigits(2);
		formateador.setMinimumFractionDigits(2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mVentas.getProcesar()
				|| e.getSource() == mVentas.getTxtPeriodo()) {
			String msjError = "";
			if (mVentas.getEjercicio().equals(""))
				msjError += "-Ingrese Ejercicio \n";
			if (mVentas.getPeriodo().trim().length() == 1)
				msjError += "-Ingrese formato correcto para el periodo.\n Por ejemplo: 01,02,03...12\n";
			if (msjError.equals("")) {
				mVentas.getProcesar().setEnabled(false);
				mVentas.getTxtEjercicio().setEnabled(false);
				mVentas.getTxtPeriodo().setEnabled(false);
				Thread hilo = new Thread() {
					public void run() {
						try {
							procesar();
						} catch (Exception e) {
							Sesion.mensajeError(mVentas, e.getMessage() + " ");
							mVentas.getProcesar().setEnabled(true);
							mVentas.getTxtEjercicio().setEnabled(true);
							mVentas.getTxtPeriodo().setEnabled(true);
						}

					}
				};
				hilo.start();
			} else {
				Sesion.mensajeError(mVentas, msjError);
			}

		} else if (e.getSource() == mVentas.getExportarTXT()) {
			if (reporteTotales.size() > 0) {
				elegirDestino();
			} else {
				Sesion.mensajeError(mVentas,
						"No hay registros en la tabla que exportar");
			}
		} else if (e.getSource() == mVentas.getSalir()) {
			FIRegistroVentas.close();
			mVentas.salir();
		}
	}

	private void elegirDestino() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileGeneral = fileChooser.getSelectedFile();
			if (fileGeneral.isDirectory()) {
				ruta = fileGeneral.toString();
				archivo = "/LE" + Sesion.ruc.trim() + periodoInformado + "00"
						+ "140100" + "00" + "1111";
				String resultado = exportarTxt(ruta + archivo + extension);
				if (reporteTxtError.size() > 0) {
					resultado += exportarTxtError(ruta + archivo + "-Error"
							+ extension);
				}
				Sesion.mensajeInformacion(mVentas, resultado);
			} else {
				Sesion.mensajeError(
						mVentas,
						"No esta permitido darle nombre al archivo a exportar,\nsolo debe seleccionar una carpeta de destino.");
				elegirDestino();
			}
		}
	}

	private void procesar() {

		mVentas.getTextoProgreso().setVisible(true);
		mVentas.getTextoProgreso().setText("Procesando, por favor espere.");

		tregv.setRvejer(Integer.parseInt(mVentas.getEjercicio()));
		tregv.setRvperi(Integer.parseInt(mVentas.getPeriodo().equals("")?"01":mVentas.getPeriodo()));
		//periodoInformado = Integer.parseInt(mVentas.getEjercicio()+ mVentas.getPeriodo());
		TTIVC objTtivc = new TTIVC();
		objTtivc.setTvejer(tregv.getRvejer());
		objTtivc.setTvperi(tregv.getRvperi());
		objTtivc.setTvtipo("RV");
		try {
			List<TREGV> listado = mVentas.getPeriodo().equals("")?servicio.listarTREGCForEjercicio(tregv):servicio.listarTREGC(tregv);
			if (listado.size() > 0) {
				correlativo = correlativoService.buscar(objTtivc);
				if (correlativo != null) {
					reporteTable.clear();
					reporteTxtError.clear();
					detalleErrores.clear();
					reporteTotales.clear();
					reinicializarTotalgeneral();
					tipoDocumento = listado.get(0).getTtido().getTdsuna()
							.trim();
					descDocumento = listado.get(0).getTtido().getTddesc()
							.trim();
					mVentas.limpiarTabla();
					mVentas.limpiarTablaTotales();
					mVentas.getProgreso().setVisible(true);
					for (TREGV tregv : listado) {
						totalRegistros++;
						mVentas.getProgreso().setValue(
								(totalRegistros * 100) / listado.size());
						mVentas.getTextoProgreso().setText(
								"Procesando registro nº: " + totalRegistros
										+ " de: " + listado.size());
						// si es el mismo tipo de documento
						if (tregv.getTtido().getTdsuna().trim()
								.equalsIgnoreCase(tipoDocumento)) {
							agregarFila(tregv);
							agregarReporteTXT();
						}
						// sino has la sumarizacion por quiebre
						else {
							// registro el subtotal acumulado por tipo de
							// documento
							agregarSubTotal();
							// sumariza para total general
							sumarizaTotalGeneral();
							// reinicializar las variables para realizar la
							// nueva
							// acumulacion por el cambio tipo de documento
							reinicializarSubTotal();
							// agrego la primera fila del siguiente tipo de
							// documento
							agregarFila(tregv);
							agregarReporteTXT();
							// seteo el nuevo tipo de documento para la
							// siguiente
							// evaluacion
							tipoDocumento = tregv.getTtido().getTdsuna().trim();
							descDocumento = tregv.getTtido().getTddesc().trim();
						}
					}
					agregarSubTotal();
					sumarizaTotalGeneral();
					reinicializarSubTotal();
					agregarTotal();
					/*
					  TGIG1+= -40;
					  reporteTotales.get(0).setWLRIG1_17(redondear((Double.parseDouble(reporteTotales.get(0).getWLRIG1_17())-40)+""));
					  reporteTotales.get(4).setWLRIG1_17(redondear((Double.parseDouble(reporteTotales.get(4).getWLRIG1_17())-40)+""));
					 */
					//reajuste();
					//reajuste2();
					if (reporteTable.get(0).getReprocesoIGV() > 0
							|| reporteTable.get(0).getReprocesoIT() > 0) {
						mVentas.cargarTabla(reporteTable);
					}
					mVentas.cargarTablaTotales(reporteTotales);
					if (detalleErrores.size() > 0) {
						mVentas.cargarTablaErrores(detalleErrores,
								totalRegistros);
					}
					mVentas.getProgreso().setVisible(false);
				} else {
					Sesion.mensajeInformacion(mVentas,
							"El periodo para el ejercicio ingresado no ha sido contabilizado.");
					mVentas.limpiarTabla();
					mVentas.limpiarTablaTotales();
				}
			} else {
				Sesion.mensajeInformacion(mVentas,
						"No existen resultados para el Ejercicio y Periodo ingresado");
				mVentas.limpiarTabla();
				mVentas.limpiarTablaTotales();
			}
			auditoria = new Auditoria();
			auditoria.setUsu(Sesion.usuario);
			auditoria.setCIA(Sesion.cia);
			auditoria.setFecha(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			auditoria.setModulo("Contabilidad");
			auditoria.setMenu("Reportes");
			auditoria.setOpcion("Regristo de ventas");
			auditoria.setTipo_ope("Consulta");
			auditoria.setCant_reg(reporteTable.size());
			auditoria.setRef1((periodoInformado+"").substring(0, 4));
			auditoria.setRef2((periodoInformado+"").substring(4, 6));
			auditoriaService.insertarAuditoria(auditoria);
			
			mVentas.getProcesar().setEnabled(true);
			mVentas.getTxtEjercicio().setEnabled(true);
			mVentas.getTxtPeriodo().setEnabled(true);
			mVentas.getTextoProgreso().setVisible(false);
		} catch (SQLException e) {
			Sesion.mensajeError(mVentas, e.getMessage());
			mVentas.getProcesar().setEnabled(true);
			mVentas.getTxtEjercicio().setEnabled(true);
			mVentas.getTxtPeriodo().setEnabled(true);
			mVentas.getTextoProgreso().setVisible(false);
		}
	}

	public void agregarFila(TREGV tregv) {
		reporte = new RegistroVentas();
		String peri="";
		if(tregv.getRvperi()<10){
			peri="0"+tregv.getRvperi();
		}else{
			peri=""+tregv.getRvperi();
		}
		periodoInformado = Integer.parseInt(tregv.getRvejer()+peri);
		System.out.println(periodoInformado);
		System.out.println("proc1");
		setWLREPE_1(tregv);
		System.out.println("proc1.1");
		setWLREST_27(tregv);
		System.out.println("proc1.2");
		setWLRCOD_2(tregv);
		System.out.println("proc1.3");
		setWLRFCP_3(tregv);
		System.out.println("proc1.4");
		setWLRTDO_5(tregv);
		System.out.println("proc1.5");
		setWLRFVE_4(tregv);
		System.out.println("proc1.6");
		setWLRSCP_6(tregv);
		System.out.println("proc2");
		setWLRNCP_7(tregv);
		System.out.println("proc2.1");
		setWLRIOD_8(tregv);
		System.out.println("proc2.2");
		setWLRFER_23_WLRTDR_24_WLRSRR_25_WLRNRR_26(tregv);
		System.out.println("proc2.3");
		setWLRTDI_9andWLRNDI_10andWLRNCL_11(tregv);
		System.out.println("proc2.4");
		setWLRVFE_12(tregv);
		System.out.println("proc2.5");
		setAbonos(tregv);
		System.out.println("proc2.6");
		setWLRBI1_13(tregv);
		System.out.println("proc3");
		setWLRITE_14_WLRITI_15(tregv);
		setWLRISC_16(tregv);
		setWLRIG1_17(tregv);
		setWLRBI2_18(tregv);
		setWLRIVE_19(tregv);
		setWLROTC_20(tregv);
		System.out.println("proc4");
		setWLRITO_21(tregv);
		setWLRTCA_22(tregv);
		setRastreo(tregv);
		// sumariza total por documento
		agregarReporteTabla();
		sumarizaTotalXTipoDocumento();
		
	}

	private void setRastreo(TREGV tregv) {
		if(!reporte.getWLRCOD_2().equals("")){
		reporte.setVintdiamay(reporte.getWLRCOD_2());
		reporte.setVintreg(reporte.getWLRCOD_2());
		}else{
			reporte.setVintdiamay("");
			reporte.setVintreg("");
			agregarError(28, wSecuTabla + 1,
					"campo VINTDIAMAY es obligatorio.");
			agregarError(30, wSecuTabla + 1,
					"campo VINTREG es obligatorio.");
		}
		if(tregv.getCampo29()!=null){
			reporte.setVintkardex(tregv.getCampo29());
		}else{
			reporte.setVintkardex("");
		}
		
	}

	private void setWLREPE_1(TREGV tregv) {
		int rvfech = tregv.getRvfech() / 100;
		if (rvfech <= periodoInformado) {
			reporte.setWLREPE_1(rvfech + "00");
		} else {
			reporte.setWLREPE_1(rvfech + "00");
			agregarError(1, wSecuTabla + 1,
					"La fecha debe ser menor o igual al periodo informado");
		}
	}

	private void setWLREST_27(TREGV tregv) {
		int rvfech = tregv.getRvfech() / 100;
		if (tregv.getRvsitu().trim().equals("99") && rvfech == periodoInformado) {
			reporte.setWLREST_27("2");
		} else if (rvfech == periodoInformado) {
			reporte.setWLREST_27("1");
		} else if (rvfech < periodoInformado) {
			reporte.setWLREST_27("8");
		} else if (rvfech < periodoInformado) {
			reporte.setWLREST_27("9");
		}
	}

	private void setWLRCOD_2(TREGV tregv) {
		try {
		if (reporte.getWLREST_27().equals("1")) {
			reporte.setWLRCOD_2(tregv.getRvasto());
		} else if (reporte.getWLREST_27().equals("2")) {
			String tipoDoc = "RV";
			int periodoNum = periodoInformado % 100;
			String periodoAlfa = "";
			String ceros = "";
			correlativo.setTvcore(correlativo.getTvcore() + 1);
			String longitudCorrelativo = tipoDoc + periodoNum
					+ correlativo.getTvcore();

			switch (longitudCorrelativo.length()) {
			case 4:
				ceros = "000000";
				break;
			case 5:
				ceros = "00000";
				break;
			case 6:
				ceros = "0000";
				break;
			case 7:
				ceros = "000";
				break;
			case 8:
				ceros = "00";
				break;
			case 9:
				ceros = "0";
				break;
			case 10:
				ceros = "";
				break;
			}

			if (periodoNum < 10) {
				reporte.setWLRCOD_2(tipoDoc + periodoNum + ceros
						+ correlativo.getTvcore());
			} else {
				switch (periodoNum) {
				case 10:
					periodoAlfa = "A0";
					break;
				case 11:
					periodoAlfa = "B0";
					break;
				case 12:
					periodoAlfa = "C0";
					break;
				}
				reporte.setWLRCOD_2(tipoDoc + periodoAlfa + ceros
						+ correlativo.getTvcore());
			}
		} else if (reporte.getWLREST_27().equals("8")) {
			reporte.setWLRCOD_2(tregv.getRvasto());
		} else if (reporte.getWLREST_27().equals("9")) {
			reporte.setWLRCOD_2(tregv.getRvasto());
		}

		if (reporte.getWLRCOD_2() == null) {
			reporte.setWLRCOD_2("");
			agregarError(2, wSecuTabla + 1, "Campo obligatorio.");
		} else {
			if (reporte.getWLRCOD_2().trim().equals("")) {
				agregarError(2, wSecuTabla + 1, "Campo obligatorio.");
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setWLRFCP_3(TREGV tregv) {
		String rvfech = tregv.getRvfech() + "";
		try {
			int rvfechEjeper = Integer.parseInt(rvfech) / 100;
			if (reporte.getWLREST_27().equals("2") && tregv.getRvfech() == 0) {
				reporte.setWLRFCP_3(fechaDefault);
			} else if (rvfechEjeper <= periodoInformado) {
				reporte.setWLRFCP_3(rvfech.substring(6, 8) + "/"
						+ rvfech.substring(4, 6) + "/" + rvfech.substring(0, 4));
			} else {
				reporte.setWLRFCP_3(rvfech.substring(6, 8) + "/"
						+ rvfech.substring(4, 6) + "/" + rvfech.substring(0, 4));
				agregarError(3, wSecuTabla + 1,
						"Fecha de emisión es mayor a fecha de proceso");
			}
		} catch (Exception e) {
			reporte.setWLRFCP_3(tregv.getRvfech() + "  ");
			agregarError(3, wSecuTabla + 1, "Formato incorrecto");
		}
	}

	private void setWLRTDO_5(TREGV tregv) {
		reporte.setWLRTDO_5(tregv.getTtido().getTdsuna());
	}

	private void setWLRFVE_4(TREGV tregv) {
		if (reporte.getWLRTDO_5().equals("14")
				&& !(reporte.getWLREST_27().equals("2"))) {
			String fechaVenc = tregv.getRvfeve() + "";
			try {
				int fechaVencEjePer = Integer.parseInt(fechaVenc) / 100;
				int periodo = tregv.getRvfech() / 100;
				if (tregv.getRvfeve() == 0) {
					reporte.setWLRFVE_4(tregv.getRvfeve() + "");
					agregarError(4, wSecuTabla + 1,
							"Fecha de vencimiento es obligatoria para recibos de servicio público");
				} else if (fechaVencEjePer <= periodoInformado
						&& fechaVencEjePer <= periodo) {
					reporte.setWLRFVE_4(tregv.getRvfeve() + "");
				} else {
					reporte.setWLRFVE_4(tregv.getRvfeve() + "");
					agregarError(4, wSecuTabla + 1,
							"Fecha de vencimiento es mayor a fecha de proceso");
				}
			} catch (Exception e) {
				reporte.setWLRFVE_4(tregv.getRvfeve() + "");
				agregarError(4, wSecuTabla + 1, "Formato incorrecto");
			}
		} else {
			reporte.setWLRFVE_4(fechaDefault);
		}
	}

	private void setWLRSCP_6(TREGV tregv) {
		if (reporte.getWLRTDO_5().equals("01")
				|| reporte.getWLRTDO_5().equals("03")
				|| reporte.getWLRTDO_5().equals("04")
				|| reporte.getWLRTDO_5().equals("07")
				|| reporte.getWLRTDO_5().equals("08")) {
			if (tregv.getRvndoc() != null) {
				if (!tregv.getRvndoc().trim().equals("")) {
					try {
						reporte.setWLRSCP_6("0"
								+ tregv.getRvndoc().trim().substring(0, 3));
					} catch (Exception e) {
						reporte.setWLRSCP_6(tregv.getRvndoc().trim());
						agregarError(6, wSecuTabla + 1,
								"Longitud del número de documento debe ser mayor a 4");
					}
				} else {
					reporte.setWLRSCP_6("");
					agregarError(6, wSecuTabla + 1, "Campo es obligatorio");
				}
			} else {
				reporte.setWLRSCP_6("");
				agregarError(6, wSecuTabla + 1, "Campo es obligatorio");
			}
		} else if (reporte.getWLRTDO_5().equals("00")) {
			reporte.setWLRSCP_6(alfaDefault);
		} else {
			if (tregv.getRvndoc() != null) {
				if (!tregv.getRvndoc().trim().equals("")) {
					if (tregv.getRvndoc().trim().length() == 20) {
						reporte.setWLRSCP_6(tregv.getRvndoc());
					} else {
						reporte.setWLRSCP_6(tregv.getRvndoc());
						agregarError(6, wSecuTabla + 1,
								"Longitud de número de serie debe ser igual a 20");
					}
				} else {
					reporte.setWLRSCP_6("");
					agregarError(6, wSecuTabla + 1, "Campo es obligatorio");
				}
			} else {
				reporte.setWLRSCP_6("");
				agregarError(6, wSecuTabla + 1, "Campo es obligatorio");
			}
		}
	}

	private void setWLRNCP_7(TREGV tregv) {
		if (tregv.getRvndoc() != null) {
			if (!tregv.getRvndoc().trim().equals("")) {// 1234
				if (tregv.getRvndoc().trim().length() > 3) {
					reporte.setWLRNCP_7(tregv.getRvndoc().trim()
							.substring(3, tregv.getRvndoc().trim().length()));
				} else {
					reporte.setWLRNCP_7(tregv.getRvndoc().trim());
					agregarError(7, wSecuTabla + 1,
							"Longitud del número de documento debe ser mayor a 3");
				}
			} else {
				reporte.setWLRNCP_7("");
				agregarError(7, wSecuTabla + 1, "Campo es obligatorio");
			}
		} else {
			reporte.setWLRNCP_7("");
			agregarError(7, wSecuTabla + 1, "Campo es obligatorio");
		}
	}

	private void setWLRIOD_8(TREGV tregv) {
		reporte.setWLRIOD_8(cantDefault);
	}

	private void setWLRFER_23_WLRTDR_24_WLRSRR_25_WLRNRR_26(TREGV tregv) {
		try {
			
		
		if ((reporte.getWLRTDO_5().equals("07")
				|| reporte.getWLRTDO_5().equals("08")
				|| reporte.getWLRTDO_5().equals("87")
				|| reporte.getWLRTDO_5().equals("88")
				|| reporte.getWLRTDO_5().equals("97") || reporte.getWLRTDO_5()
				.equals("98")) && !reporte.getWLREST_27().equals("2")) {
			
			if (tregv.getTncdh().getNhtdoc() != null
					&& tregv.getRvtdoc().equals("NC")) {
				String rvfech = "";
				try {
					TREGV tregvv = new TREGV();
					tregvv.setRvtdoc(tregv.getTncdh().getNhtdoc().trim());

					String cerosPVTA = "";
					String cerosNUME = "";
					String pvta = tregv.getTncdh().getNhpvtn() + "";
					String fabo = tregv.getTncdh().getNhfabo() + "";
					switch (pvta.trim().length()) {
					case 1:
						cerosPVTA = "00";
						break;
					case 2:
						cerosPVTA = "0";
						break;
					case 3:
						cerosPVTA = "";
						break;
					}
					switch (fabo.trim().length()) {
					case 1:
						cerosNUME = "000000";
						break;
					case 2:
						cerosNUME = "00000";
						break;
					case 3:
						cerosNUME = "0000";
						break;
					case 4:
						cerosNUME = "000";
						break;
					case 5:
						cerosNUME = "00";
						break;
					case 6:
						cerosNUME = "0";
						break;
					case 7:
						cerosNUME = "";
						break;
					}

					tregvv.setRvndoc(cerosPVTA + pvta.trim() + cerosNUME
							+ fabo.trim());
					TREGV tregvvP = servicio.buscarTNCDH(tregvv);
					if(tregvvP==null){
						System.out.println("nulo nota");
						tregvvP = servicio.buscarTNCDHTM(tregvv);
					}
					tregvv=tregvvP;
					rvfech = tregvv.getRvfech() + "";
					int rvfechEjeper = Integer.parseInt(rvfech) / 100;
					if (rvfechEjeper <= periodoInformado) {
						reporte.setWLRFER_23(rvfech.substring(6, 8) + "/"
								+ rvfech.substring(4, 6) + "/"
								+ rvfech.substring(0, 4));
					} else {
						reporte.setWLRFER_23(rvfech.substring(6, 8) + "/"
								+ rvfech.substring(4, 6) + "/"
								+ rvfech.substring(0, 4));
						agregarError(23, wSecuTabla + 1,
								"Fecha de emisión es mayor a fecha de proceso");
					}
				} catch (Exception e) {
					reporte.setWLRFER_23(rvfech + "  ");
					agregarError(23, wSecuTabla + 1, "Formato incorrecto");
					System.out.println(e.getMessage());
				}

				if (tregv.getTtidox().getTdsuna() != null) {
					if (!(tregv.getTtidox().getTdsuna().trim().equals(""))) {
						reporte.setWLRTDR_24(tregv.getTtidox().getTdsuna());
					} else {
						reporte.setWLRTDR_24("");
						agregarError(24, wSecuTabla + 1,
								"No existe tipo de documento que dio origen a Nota de Crédito.");
					}
				} else {
					reporte.setWLRTDR_24("");
					agregarError(24, wSecuTabla + 1,
							"No existe tipo de documento que dio origen a Nota de Crédito.");
				}

				if (!reporte.getWLRTDR_24().equals("12")) {
					String serie = tregv.getTncdh().getNhpvtn() + "";
					String ceros = "";
					switch (serie.length()) {
					case 1:
						ceros = "00";
						break;
					case 2:
						ceros = "0";
						break;
					case 3:
						ceros = "";
						break;
					}
					reporte.setWLRSRR_25("0" + ceros
							+ tregv.getTncdh().getNhpvtn());

					String numero = tregv.getTncdh().getNhfabo() + "";
					switch (numero.length()) {
					case 1:
						ceros = "000000";
						break;
					case 2:
						ceros = "00000";
						break;
					case 3:
						ceros = "0000";
						break;
					case 4:
						ceros = "000";
						break;
					case 5:
						ceros = "00";
						break;
					case 6:
						ceros = "0";
						break;
					case 7:
						ceros = "";
						break;
					}
					reporte.setWLRNRR_26(ceros + tregv.getTncdh().getNhfabo());
				} else {
					reporte.setWLRSRR_25(alfaDefault);
					reporte.setWLRNRR_26(alfaDefault);
				}
			}

			else if (!tregv.getTfvad().getFdglos().equals("")) {
				StringTokenizer stGeneral = new StringTokenizer("relleno " +tregv
						.getTfvad().getFdglos().trim(), "**");
				
				stGeneral.nextToken();// corto la primera parte de la cadena
										// que
										// no me sirve
				String dato = stGeneral.nextToken();// almaceno todo lo que
													// me
													// sirve
				StringTokenizer stEspecifico = new StringTokenizer(dato, "-");
				String tipo = stEspecifico.nextToken().trim();
				String serie = stEspecifico.nextToken().trim();
				String numero = stEspecifico.nextToken().trim();
				
				reporte.setWLRSRR_25("0" + serie);
				reporte.setWLRNRR_26(numero);

				TREGV tregvv = new TREGV();
				tregvv.setRvtdoc(tipo);
				tregvv.setRvndoc(serie + numero);
				try {
					TREGV tregvvP = servicio.buscarTFVAD(tregvv);
					if(tregvvP==null){
						tregvvP = servicio.buscarTFVADTM(tregvv);
					}
					tregvv=tregvvP;
					if (tregvv != null) {
						String rvfech = tregvv.getRvfech() + "";
						try {
							reporte.setWLRTDR_24(tregvv.getTtido().getTdsuna()
									.trim());
							Integer.parseInt(rvfech);
							reporte.setWLRFER_23(rvfech.substring(6, 8) + "/"
									+ rvfech.substring(4, 6) + "/"
									+ rvfech.substring(0, 4));
						} catch (Exception e) {
							reporte.setWLRFER_23(rvfech);
							agregarError(23, wSecuTabla + 1,
									"Formato incorrecto");
						}
					} else {
						if (tipo.equals("FC")) {
							reporte.setWLRTDR_24("01");
						} else if (tipo.equals("BV")) {
							reporte.setWLRTDR_24("03");
						} else {
							reporte.setWLRTDR_24("");
							agregarError(24, wSecuTabla + 1,
									"Campo obligatorio.");
						}

						reporte.setWLRFER_23("");
						agregarError(23, wSecuTabla + 1, "Campo obligatorio.");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				/*
				 * else { reporte.setWLRFER_23(""); reporte.setWLRTDR_24("");
				 * reporte.setWLRSRR_25(""); reporte.setWLRNRR_26("");
				 * agregarError(23, wSecuTabla + 1, "Campo obligatorio.");
				 * agregarError(24, wSecuTabla + 1, "Campo obligatorio.");
				 * agregarError(25, wSecuTabla + 1, "Campo obligatorio.");
				 * agregarError(26, wSecuTabla + 1, "Campo obligatorio."); }
				 */
			} else {
				reporte.setWLRFER_23("");
				reporte.setWLRTDR_24("");
				reporte.setWLRSRR_25("");
				reporte.setWLRNRR_26("");
				agregarError(23, wSecuTabla + 1, "Campo obligatorio.");
				agregarError(24, wSecuTabla + 1, "Campo obligatorio.");
				agregarError(25, wSecuTabla + 1, "Campo obligatorio.");
				agregarError(26, wSecuTabla + 1, "Campo obligatorio.");
			}
		} else {
			reporte.setWLRFER_23(fechaDefault);
			reporte.setWLRTDR_24("00");
			reporte.setWLRSRR_25(alfaDefault);
			reporte.setWLRNRR_26(alfaDefault);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setWLRTDI_9andWLRNDI_10andWLRNCL_11(TREGV tregv) {
		if (reporte.getWLREST_27().equals("2")
				|| (reporte.getWLRTDO_5().equals("07") || reporte.getWLRTDO_5()
						.equals("08") && reporte.getWLRTDR_24().equals("03"))) {
			reporte.setWLRTDI_9(cantDefault);
			reporte.setWLRNDI_10(alfaDefault);
			reporte.setWLRNCL_11(alfaDefault);
		} else {
			if (tregv.getCliente().getTipoIde() != null) {
				if (!tregv.getCliente().getTipoIde().trim().equals("")) {
					if (tregv.getCliente().getTipoIde().trim()
							.equalsIgnoreCase("DN")) {
						reporte.setWLRTDI_9("1");
					} else if (tregv.getCliente().getTipoIde().trim()
							.equalsIgnoreCase("RU")) {
						reporte.setWLRTDI_9("6");
					} else if (tregv.getCliente().getTipoIde().trim()
							.equalsIgnoreCase("CE")) {
						reporte.setWLRTDI_9("4");
					} else if (tregv.getCliente().getTipoIde().trim()
							.equalsIgnoreCase("PS")) {
						reporte.setWLRTDI_9("7");
					} else if (tregv.getCliente().getTipoIde().trim()
							.equalsIgnoreCase("OD")) {
						reporte.setWLRTDI_9("0");
					} else {
						reporte.setWLRTDI_9(tregv.getCliente().getTipoIde());
						agregarError(9, wSecuTabla + 1,
								"Tipo de documento no valido para sunat, verificar en maestra de clientes");
					}
				} else {
					reporte.setWLRTDI_9("");
					agregarError(9, wSecuTabla + 1,
							"Campo es obligatorio, verificar en maestra de clientes");
				}
			} else {
				reporte.setWLRTDI_9("");
				agregarError(9, wSecuTabla + 1,
						"Campo es obligatorio, verificar en maestra de clientes");
			}

			if (tregv.getCliente().getDni() != null) {
				if (!tregv.getCliente().getDni().trim().equals("")) {
					reporte.setWLRNDI_10(tregv.getCliente().getDni().trim());
					// ****************************
					if (tregv.getCliente().getTipoIde().trim()
							.equalsIgnoreCase("DN")) {
						try {
							Integer.parseInt(tregv.getCliente().getDni().trim());
						} catch (Exception e) {
							System.out.println(e.getMessage() + " DN");
							agregarError(10, wSecuTabla + 1,
									"Formato incorrecto. verificar en maestra de clientes");
						}
						if (tregv.getCliente().getDni().trim().length() != 8) {
							agregarError(
									10,
									wSecuTabla + 1,
									"Formato incorrecto,longitud debe ser igual a 8. verificar en maestra de clientes");
						}
					} else if (tregv.getCliente().getTipoIde().trim()
							.equalsIgnoreCase("RU")) {
						try {
							Long.parseLong(tregv.getCliente().getDni().trim());
						} catch (Exception e) {
							System.out.println(e.getMessage() + " RU");
							agregarError(10, wSecuTabla + 1,
									"Formato incorrecto. verificar en maestra de clientes");
						}
						if (tregv.getCliente().getDni().trim().length() != 11) {
							agregarError(
									10,
									wSecuTabla + 1,
									"Formato incorrecto.longitud debe ser igual a 11 verificar en maestra de clientes");
						}
					}
					// ****************************
				} else {
					reporte.setWLRNDI_10("");
					agregarError(10, wSecuTabla + 1,
							"Campo es obligatorio. verificar en maestra de clientes");
				}
			} else {
				reporte.setWLRNDI_10("");
				agregarError(10, wSecuTabla + 1,
						"Campo es obligatorio. verificar en maestra de clientes");
			}

			if (tregv.getRvclie() != null) {
				if (!tregv.getRvclie().trim().equals("")) {
					reporte.setWLRNCL_11(tregv.getRvclie().trim());
				} else {
					reporte.setWLRNCL_11("");
					agregarError(11, wSecuTabla + 1, "Campo es obligatorio.");
				}
			} else {
				reporte.setWLRNCL_11("");
				agregarError(11, wSecuTabla + 1, "Campo es obligatorio.");
			}
		}

	}

	private void setWLRVFE_12(TREGV tregv) {
		reporte.setWLRVFE_12(montoDefault);
	}

	private void setWLRBI1_13(TREGV tregv) {
		double baseImp = tregv.getRvvalv() - tregv.getRvdsct();
		if (tregv.getRvmone() == 1) {
			baseImp = baseImp * tregv.getRvtcam();
		}
		reporte.setWLRBI1_13(baseImp + "");
		if (!reporte.getWLRTDO_5().equals("07") && baseImp < 0) {
			agregarError(13, wSecuTabla + 1,
					"Base imponible gravada no puede ser negativo ya que no es nota de crédito.");
		}
	}

	private void setWLRITE_14_WLRITI_15(TREGV tregv) {
		
		if (tregv.getRvvali() == -0.0) {
			reporte.setWLRITE_14(montoDefault);
			reporte.setWLRITI_15(montoDefault);
		} else {
			if (tregv.getRvmone() == 1) {
				tregv.setRvvali(tregv.getRvvali() * tregv.getRvtcam());
			}
			reporte.setWLRITE_14(tregv.getRvvali() + "");
			reporte.setWLRITI_15(tregv.getRvvali() + "");
		}

	}

	private void setWLRISC_16(TREGV tregv) {
		reporte.setWLRISC_16(montoDefault);
	}

	private void setWLRIG1_17(TREGV tregv) {
		if (tregv.getRvmone() == 1) {
			tregv.setRvigv(tregv.getRvigv() * tregv.getRvtcam());
		}
		reporte.setWLRIG1_17(tregv.getRvigv() + "");
	}

	private void setWLRBI2_18(TREGV tregv) {
		reporte.setWLRBI2_18(montoDefault);
	}

	private void setWLRIVE_19(TREGV tregv) {
		reporte.setWLRIVE_19(montoDefault);
	}

	private void setWLROTC_20(TREGV tregv) {
		reporte.setWLROTC_20(tregv.getRvimp3() + "");
	}

	private void setWLRITO_21(TREGV tregv) {
		if (tregv.getRvmone() == 1) {
			tregv.setRvpvta(tregv.getRvpvta() * tregv.getRvtcam());
		}
		reporte.setWLRITO_21(tregv.getRvpvta() + "");
	}

	private void setWLRTCA_22(TREGV tregv) {
		double rvtcam = tregv.getRvtcam();
		reporte.setWLRTCA_22(Sesion.formateaDecimal_3(rvtcam) + "");
		if (rvtcam < 0) {
			agregarError(22, wSecuTabla + 1,
					"Tipo de cambio no puede ser negativo");
		}
	}

	private void setAbonos(TREGV tregv) {
		if (tregv.getRvmval().equalsIgnoreCase("C"))
			tregv.setRvvalv(tregv.getRvvalv() * -1);
		if (tregv.getRvmdsc().equalsIgnoreCase("A"))
			tregv.setRvdsct(tregv.getRvdsct() * -1);
		if (tregv.getRvmigv().equalsIgnoreCase("C"))
			tregv.setRvigv(tregv.getRvigv() * -1);
		if (tregv.getRvmpvt().equalsIgnoreCase("A"))
			tregv.setRvpvta(tregv.getRvpvta() * -1);
		if (tregv.getRvmvai().equalsIgnoreCase("C"))
			tregv.setRvvali(tregv.getRvvali() * -1);
		if (tregv.getRvmim2().equalsIgnoreCase("C"))
			tregv.setRvimp2(tregv.getRvimp2() * -1);
		if (tregv.getRvmim3().equalsIgnoreCase("C"))
			tregv.setRvimp3(tregv.getRvimp3() * -1);
		if (tregv.getRvmre1().equalsIgnoreCase("C"))
			tregv.setRvret1(tregv.getRvret1() * -1);
		if (tregv.getRvmre2().equalsIgnoreCase("C"))
			tregv.setRvret2(tregv.getRvret2() * -1);
	}

	private void agregarSubTotal() {
		reporte = new RegistroVentas();
		reporte.setWLRVFE_12(TDVFE + "");
		reporte.setWLRBI1_13(TDBI1 + "");
		reporte.setWLRITE_14(TDITE + "");
		reporte.setWLRITI_15(TDITI + "");
		reporte.setWLRISC_16(TDISC + "");
		reporte.setWLRIG1_17(TDIG1 + "");
		reporte.setWLRBI2_18(TDBI2 + "");
		reporte.setWLRIVE_19(TDIVE + "");
		reporte.setWLROTC_20(TDOTC + "");
		reporte.setWLRITO_21(TDITO + "");
		reporte.setWLRNCL_11("Total " + descDocumento);
		agregarTotalizado();
	}

	private void agregarTotal() {
		reporte = new RegistroVentas();
		reporte.setWLRVFE_12(TGVFE + "");
		reporte.setWLRBI1_13(TGBI1 + "");
		reporte.setWLRITE_14(TGITE + "");
		reporte.setWLRITI_15(TGITI + "");
		reporte.setWLRISC_16(TGISC + "");
		reporte.setWLRIG1_17(TGIG1 + "");
		reporte.setWLRBI2_18(TGBI2 + "");
		reporte.setWLRIVE_19(TGIVE + "");
		reporte.setWLROTC_20(TGOTC + "");
		reporte.setWLRITO_21(TGITO + "");
		reporte.setWLRNCL_11("Total General:");
		agregarTotalizado();
	}

	private void sumarizaTotalXTipoDocumento() {
		TDVFE += Double.parseDouble(reporte.getWLRVFE_12());
		TDBI1 += Double.parseDouble(reporte.getWLRBI1_13());
		TDITE += Double.parseDouble(reporte.getWLRITE_14());
		TDITI += Double.parseDouble(reporte.getWLRITI_15());
		TDISC += Double.parseDouble(reporte.getWLRISC_16());
		TDIG1 += Double.parseDouble(reporte.getWLRIG1_17());
		TDBI2 += Double.parseDouble(reporte.getWLRBI2_18());
		TDIVE += Double.parseDouble(reporte.getWLRIVE_19());
		TDOTC += Double.parseDouble(reporte.getWLROTC_20());
		TDITO += Double.parseDouble(reporte.getWLRITO_21());
	}

	private void sumarizaTotalGeneral() {
		TGVFE += TDVFE;
		TGBI1 += TDBI1;
		TGITE += TDITE;
		TGITI += TDITI;
		TGISC += TDISC;
		TGIG1 += TDIG1;
		TGBI2 += TDBI2;
		TGIVE += TDIVE;
		TGOTC += TDOTC;
		TGITO += TDITO;
	}

	private void reinicializarSubTotal() {
		TDVFE = 0;
		TDBI1 = 0;
		TDITE = 0;
		TDITI = 0;
		TDISC = 0;
		TDIG1 = 0;
		TDBI2 = 0;
		TDIVE = 0;
		TDOTC = 0;
		TDITO = 0;
	}

	private void reinicializarTotalgeneral() {
		TGVFE = 0;
		TGBI1 = 0;
		TGITE = 0;
		TGITI = 0;
		TGISC = 0;
		TGIG1 = 0;
		TGBI2 = 0;
		TGIVE = 0;
		TGOTC = 0;
		TGITO = 0;

		totalRegistros = 0;
		wSecuTabla = 0;
		wSecuTXT = 0;
	}

	private void agregarReporteTabla() {
		wSecuTabla += 1;// secuencia
		reporte.setWLRSEQ_0(wSecuTabla + "");
		reporte.setWLRVFE_12(redondear((reporte.getWLRVFE_12())));
		reporte.setWLRBI1_13(redondear((reporte.getWLRBI1_13())));
		reporte.setWLRITE_14(redondear((reporte.getWLRITE_14())));
		reporte.setWLRITI_15(redondear((reporte.getWLRITI_15())));
		reporte.setWLRISC_16(redondear((reporte.getWLRISC_16())));
		reporte.setWLRIG1_17(redondear((reporte.getWLRIG1_17())));
		reporte.setWLRBI2_18(redondear((reporte.getWLRBI2_18())));
		reporte.setWLRIVE_19(redondear((reporte.getWLRIVE_19())));
		reporte.setWLROTC_20(redondear((reporte.getWLROTC_20())));
		reporte.setWLRITO_21(redondear((reporte.getWLRITO_21())));
		reporteTable.add(reporte);
		mVentas.cargarTabla(reporte);
	}

	private void agregarTotalizado() {
		reporte.setWLRVFE_12(redondear((reporte.getWLRVFE_12())));
		reporte.setWLRBI1_13(redondear((reporte.getWLRBI1_13())));
		reporte.setWLRITE_14(redondear((reporte.getWLRITE_14())));
		reporte.setWLRITI_15(redondear((reporte.getWLRITI_15())));
		reporte.setWLRISC_16(redondear((reporte.getWLRISC_16())));
		reporte.setWLRIG1_17(redondear((reporte.getWLRIG1_17())));
		reporte.setWLRBI2_18(redondear((reporte.getWLRBI2_18())));
		reporte.setWLRIVE_19(redondear((reporte.getWLRIVE_19())));
		reporte.setWLROTC_20(redondear((reporte.getWLROTC_20())));
		reporte.setWLRITO_21(redondear((reporte.getWLRITO_21())));
		reporteTotales.add(reporte);
	}

	private void agregarReporteTXT() {
		wSecuTXT += 1;
		reporte.setWLRSEQ_0(wSecuTXT + "");
		switch (reporte.getError()) {
		case 0:
			break;
		default:
			reporteTxtError.add(reporte);
		}

	}

	private void agregarError(int col, int fila, String msj) {
		reporte.setError(reporte.getError() + 1);
		reporteError = new RegistroVentas();
		reporteError.setFila(fila);
		reporteError.setColumna(col);
		reporteError.setDescripcion(msj);
		detalleErrores.add(reporteError);
	}

	private String exportarTxt(String ruta) {
		int totalReg = 0;
		String resultado = "";
		String separador = "|";
		PrintWriter pw = null;
		try {
			RegistroVentas reporte;
			pw = new PrintWriter(new FileWriter(ruta));
			pw.println("VPERIODO|VNUMREGOPE|VFECCOM|VFECVENPAG|VTIPDOCCOM|VNUMSER|VNUMDOCCOI|VNUMDOCCOF|VTIPDIDCLI|VNUMDIDCLI|VAPENOMRSO|VVALFACEXP|VBASIMPGRA|VIMPTOTEXTO|VIMPTOTINA|VISC|VIGVIPM|VBASIMVAP|VIVAP|VOTRTRICGO|VIMPTOTCOM|VTIPCAM|VFECCOMMOD|VTIPCCOMMOD|VNUMSERMOD|VNUMCOMMOD|VESTOPE|VINTDIAMAY|VINTKARDEX|VINTREG|");
			for (int i = 0; i < reporteTable.size(); i++) {
				totalReg++;
				reporte = reporteTable.get(i);
				pw.println(reporte.getWLREPE_1().trim() + separador
						+ reporte.getWLRCOD_2().trim() + separador
						+ reporte.getWLRFCP_3().trim() + separador
						+ reporte.getWLRFVE_4().trim() + separador
						+ reporte.getWLRTDO_5().trim() + separador
						+ reporte.getWLRSCP_6().trim() + separador
						+ reporte.getWLRNCP_7().trim() + separador
						+ reporte.getWLRIOD_8().trim() + separador
						+ reporte.getWLRTDI_9().trim() + separador
						+ reporte.getWLRNDI_10().trim() + separador
						+ reporte.getWLRNCL_11().trim() + separador
						+ reporte.getWLRVFE_12().trim() + separador
						+ reporte.getWLRBI1_13().trim() + separador
						+ reporte.getWLRITE_14().trim() + separador
						+ reporte.getWLRITI_15().trim() + separador
						+ reporte.getWLRISC_16().trim() + separador
						+ reporte.getWLRIG1_17().trim() + separador
						+ reporte.getWLRBI2_18().trim() + separador
						+ reporte.getWLRIVE_19().trim() + separador
						+ reporte.getWLROTC_20().trim() + separador
						+ reporte.getWLRITO_21().trim() + separador
						+ reporte.getWLRTCA_22().trim() + separador
						+ reporte.getWLRFER_23().trim() + separador
						+ reporte.getWLRTDR_24().trim() + separador
						+ reporte.getWLRSRR_25().trim() + separador
						+ reporte.getWLRNRR_26().trim() + separador
						+ reporte.getWLREST_27().trim() + separador
						+ reporte.getVintdiamay().trim() + separador
						+ reporte.getVintkardex().trim() + separador
						+ reporte.getVintreg().trim() + separador);
			}
			pw.flush();
			pw.close();
			resultado = "Se a exportado con exito " + totalReg + " registro(s)"
					+ ", verifique la siguiente ruta: \n -" + ruta;
		} catch (Exception ex) {
			ex.printStackTrace();
			pw.flush();
			pw.close();
		}
		return resultado;
	}

	private String exportarTxtError(String ruta) {
		int totalReg = 0;
		int canError = 0;
		String resultado = "";
		String separador = "|";
		PrintWriter pw = null;
		try {
			RegistroVentas reporte;
			pw = new PrintWriter(new FileWriter(ruta));
			for (int i = 0; i < reporteTxtError.size(); i++) {
				totalReg++;
				reporte = reporteTxtError.get(i);
				canError += reporte.getError();
				pw.println(reporte.getWLREPE_1().trim() + separador
						+ reporte.getWLRCOD_2().trim() + separador
						+ reporte.getWLRFCP_3().trim() + separador
						+ reporte.getWLRFVE_4().trim() + separador
						+ reporte.getWLRTDO_5().trim() + separador
						+ reporte.getWLRSCP_6().trim() + separador
						+ reporte.getWLRNCP_7().trim() + separador
						+ reporte.getWLRIOD_8().trim() + separador
						+ reporte.getWLRTDI_9().trim() + separador
						+ reporte.getWLRNDI_10().trim() + separador
						+ reporte.getWLRNCL_11().trim() + separador
						+ reporte.getWLRVFE_12().trim() + separador
						+ reporte.getWLRBI1_13().trim() + separador
						+ reporte.getWLRITE_14().trim() + separador
						+ reporte.getWLRITI_15().trim() + separador
						+ reporte.getWLRISC_16().trim() + separador
						+ reporte.getWLRIG1_17().trim() + separador
						+ reporte.getWLRBI2_18().trim() + separador
						+ reporte.getWLRIVE_19().trim() + separador
						+ reporte.getWLROTC_20().trim() + separador
						+ reporte.getWLRITO_21().trim() + separador
						+ reporte.getWLRTCA_22().trim() + separador
						+ reporte.getWLRFER_23().trim() + separador
						+ reporte.getWLRTDR_24().trim() + separador
						+ reporte.getWLRSRR_25().trim() + separador
						+ reporte.getWLRNRR_26().trim() + separador
						+ reporte.getWLREST_27().trim() + separador
						+ reporte.getVintdiamay().trim() + separador
						+ reporte.getVintkardex().trim() + separador
						+ reporte.getVintreg().trim() + separador);
			}
			pw.flush();
			pw.close();
			resultado = ".\nAdemás, se han encontrado la siguiente cantidad de errores: "
					+ canError
					+ " en "
					+ totalReg
					+ " registro(s)"
					+ ", verifique la siguiente ruta:\n -" + ruta;
		} catch (Exception ex) {
			ex.printStackTrace();
			pw.flush();
			pw.close();
		}
		return resultado;
	}

	public String redondear(String numero) {
		double num = 0.00;
		try {
			num = Double.parseDouble(numero);
			return formateador.format(num);
		} catch (Exception e) {
		}
		return num + "";
	}

	public void reajuste() {
		//TGITE
		double igvEsperado = TGBI1 * 0.18;
		double igvActual = TGIG1;
		double igvDiferencia = igvActual - igvEsperado;
		double igv = Double.parseDouble(redondear(igvDiferencia + ""));
		// si existe diferencias
		if (igv != 0.00) {
			reporteTable.get(0).setReprocesoIGV(
					reporteTable.get(0).getReprocesoIGV() + 1);
			System.out
					.println("************************************Reajuste 1, Proceso Nº"
							+ reporteTable.get(0).getReprocesoIGV());
			System.out.println("monto a reajustar:" + igv);
			double vueltas = 0.00;
			double reajuste = -0.01;// reduce igv a declarar
			// si igv a declarar es menor al esperado
			if (igv < 0.00) {
				reajuste = 0.01;// aumenta igv a declarar
				vueltas = (igv * -1) / 0.01;// Enero 3740 vueltas
				System.out.println("aumento de igv");
			} else {
				System.out.println("reduccion de igv");
				vueltas = igv / 0.01;// Enero 3740 vueltas
			}
			System.out.println("cantidad de vueltas " + vueltas);
			for (int i = 0; i < reporteTable.size(); i++) {
				if (vueltas > 0) {
					// no es NC
					if (!reporteTable.get(i).getWLRTDO_5().equals("07")) {
						// igv no esta vacio
						if (!reporteTable.get(i).getWLRIG1_17().equals("")) {
							try {
								// trato de obtener los decimales
								int RIG1_17 = Integer.parseInt(reporteTable
										.get(i)
										.getWLRIG1_17()
										.trim()
										.substring(
												reporteTable.get(i)
														.getWLRIG1_17().trim()
														.length() - 2,
												reporteTable.get(i)
														.getWLRIG1_17().trim()
														.length()));
								if (reporteTable.get(0).getReprocesoIGV() == 1) {
									// que los decimales sean > 2 y < 48
									if (RIG1_17 >= 2 && RIG1_17 <= 48) {
										vueltas = vueltas - 1;
										TGIG1 += reajuste;
										reporteTable
												.get(i)
												.setWLRIG1_17(
														redondear((Double
																.parseDouble(reporteTable
																		.get(i)
																		.getWLRIG1_17()) + reajuste)
																+ ""));

										if (reporteTable.get(i).getWLRTDO_5()
												.equals("01")) {
											reporteTotales
													.get(0)
													.setWLRIG1_17(
															redondear((Double
																	.parseDouble(reporteTotales
																			.get(0)
																			.getWLRIG1_17()) + reajuste)
																	+ ""));
										} else if (reporteTable.get(i)
												.getWLRTDO_5().equals("03")) {
											reporteTotales
													.get(1)
													.setWLRIG1_17(
															redondear((Double
																	.parseDouble(reporteTotales
																			.get(1)
																			.getWLRIG1_17()) + reajuste)
																	+ ""));
										} else if (reporteTable.get(i)
												.getWLRTDO_5().equals("08")) {
											reporteTotales
													.get(3)
													.setWLRIG1_17(
															redondear((Double
																	.parseDouble(reporteTotales
																			.get(3)
																			.getWLRIG1_17()) + reajuste)
																	+ ""));
										}

										reporteTotales
												.get(4)
												.setWLRIG1_17(
														redondear((Double
																.parseDouble(reporteTotales
																		.get(4)
																		.getWLRIG1_17()) + reajuste)
																+ ""));
										System.out.println(reporteTable.get(i)
												.getWLRSEQ_0()
												+ " - "
												+ vueltas);
									}
								} else {
									if (RIG1_17 >= 52 && RIG1_17 <= 88) {
										vueltas = vueltas - 1;
										TGIG1 += reajuste;
										reporteTable
												.get(i)
												.setWLRIG1_17(
														redondear((Double
																.parseDouble(reporteTable
																		.get(i)
																		.getWLRIG1_17()) + reajuste)
																+ ""));

										if (reporteTable.get(i).getWLRTDO_5()
												.equals("01")) {
											reporteTotales
													.get(0)
													.setWLRIG1_17(
															redondear((Double
																	.parseDouble(reporteTotales
																			.get(0)
																			.getWLRIG1_17()) + reajuste)
																	+ ""));
										} else if (reporteTable.get(i)
												.getWLRTDO_5().equals("03")) {
											reporteTotales
													.get(1)
													.setWLRIG1_17(
															redondear((Double
																	.parseDouble(reporteTotales
																			.get(1)
																			.getWLRIG1_17()) + reajuste)
																	+ ""));
										} else if (reporteTable.get(i)
												.getWLRTDO_5().equals("08")) {
											reporteTotales
													.get(3)
													.setWLRIG1_17(
															redondear((Double
																	.parseDouble(reporteTotales
																			.get(3)
																			.getWLRIG1_17()) + reajuste)
																	+ ""));
										}

										reporteTotales
												.get(4)
												.setWLRIG1_17(
														redondear((Double
																.parseDouble(reporteTotales
																		.get(4)
																		.getWLRIG1_17()) + reajuste)
																+ ""));
										System.out.println(reporteTable.get(i)
												.getWLRSEQ_0()
												+ " - "
												+ vueltas);
									}
								}
							} catch (Exception e) {

							}
						}
					}
				} else {
					System.out.println("final reajuste igv, Nº proceso "
							+ reporteTable.get(0).getReprocesoIGV());
					break;
				}
			}
			// *************************
			// si aun hay diferencias en el reajuste
			if (TGIG1 != igvEsperado) {
				//reajuste();
			}
			// *************************
		}
	}

	public void reajuste2() {
		double igvEsperado = TGBI1 * 0.18;
		double tEsperado = TGBI1 + igvEsperado + TGITE;
		double tActual = TGITO;
		double tDiferencia = tActual - tEsperado;
		double total = Double.parseDouble(redondear(tDiferencia + ""));
		// si existe diferencias
		if (total != 0.00) {
			reporteTable.get(0).setReprocesoIT(
					reporteTable.get(0).getReprocesoIT() + 1);
			System.out
					.println("************************************Reajuste 2, Proceso Nº"
							+ reporteTable.get(0).getReprocesoIT());
			System.out.println("monto a reajustar: " + total);
			double vueltas = 0.00;
			double reajuste = -0.01;// reduce total a declarar
			// si total a declarar es menor al esperado
			if (total < 0.00) {
				reajuste = 0.01;// aumenta total a declarar
				vueltas = (total * -1) / 0.01;
				System.out.println("aumento de total");
			} else {
				System.out.println("reduccion de total");
				vueltas = total / 0.01;// }
				System.out.println("cantidad de vueltas " + vueltas);
				for (int i = 0; i < reporteTable.size(); i++) {
					if (vueltas > 0) {
						// no es NC
						if (!reporteTable.get(i).getWLRTDO_5().equals("07")) {
							// igv no esta vacio
							if (!reporteTable.get(i).getWLRITO_21().equals("")) {
								try {
									// trato de obtener los decimales
									int RITO_21 = Integer
											.parseInt(reporteTable
													.get(i)
													.getWLRITO_21()
													.trim()
													.substring(
															reporteTable
																	.get(i)
																	.getWLRITO_21()
																	.trim()
																	.length() - 2,
															reporteTable
																	.get(i)
																	.getWLRITO_21()
																	.trim()
																	.length()));
									if (reporteTable.get(0).getReprocesoIT() == 1) {
										// que los decimales sean > 2 y < 48
										if (RITO_21 >= 2 && RITO_21 <= 48) {
											vueltas = vueltas - 1;
											TGITO += reajuste;
											reporteTable
													.get(i)
													.setWLRITO_21(
															redondear((Double
																	.parseDouble(reporteTable
																			.get(i)
																			.getWLRITO_21()) + reajuste)
																	+ ""));

											if (reporteTable.get(i)
													.getWLRTDO_5().equals("01")) {
												reporteTotales
														.get(0)
														.setWLRITO_21(
																redondear((Double
																		.parseDouble(reporteTotales
																				.get(0)
																				.getWLRITO_21()) + reajuste)
																		+ ""));
											} else if (reporteTable.get(i)
													.getWLRTDO_5().equals("03")) {
												reporteTotales
														.get(1)
														.setWLRITO_21(
																redondear((Double
																		.parseDouble(reporteTotales
																				.get(1)
																				.getWLRITO_21()) + reajuste)
																		+ ""));
											} else if (reporteTable.get(i)
													.getWLRTDO_5().equals("08")) {
												reporteTotales
														.get(3)
														.setWLRITO_21(
																redondear((Double
																		.parseDouble(reporteTotales
																				.get(3)
																				.getWLRITO_21()) + reajuste)
																		+ ""));
											}

											reporteTotales
													.get(4)
													.setWLRITO_21(
															redondear((Double
																	.parseDouble(reporteTotales
																			.get(4)
																			.getWLRITO_21()) + reajuste)
																	+ ""));
											System.out.println(reporteTable
													.get(i).getWLRSEQ_0()
													+ " - " + vueltas);
										}
									} else {
										if (RITO_21 >= 52 && RITO_21 <= 88) {
											vueltas = vueltas - 1;
											TGITO += reajuste;
											reporteTable
													.get(i)
													.setWLRITO_21(
															redondear((Double
																	.parseDouble(reporteTable
																			.get(i)
																			.getWLRITO_21()) + reajuste)
																	+ ""));

											if (reporteTable.get(i)
													.getWLRTDO_5().equals("01")) {
												reporteTotales
														.get(0)
														.setWLRITO_21(
																redondear((Double
																		.parseDouble(reporteTotales
																				.get(0)
																				.getWLRITO_21()) + reajuste)
																		+ ""));
											} else if (reporteTable.get(i)
													.getWLRTDO_5().equals("03")) {
												reporteTotales
														.get(1)
														.setWLRITO_21(
																redondear((Double
																		.parseDouble(reporteTotales
																				.get(1)
																				.getWLRITO_21()) + reajuste)
																		+ ""));
											} else if (reporteTable.get(i)
													.getWLRTDO_5().equals("08")) {
												reporteTotales
														.get(3)
														.setWLRITO_21(
																redondear((Double
																		.parseDouble(reporteTotales
																				.get(3)
																				.getWLRITO_21()) + reajuste)
																		+ ""));
											}

											reporteTotales
													.get(4)
													.setWLRITO_21(
															redondear((Double
																	.parseDouble(reporteTotales
																			.get(4)
																			.getWLRITO_21()) + reajuste)
																	+ ""));
											System.out.println(reporteTable
													.get(i).getWLRSEQ_0()
													+ " - " + vueltas);
										}
									}
								} catch (Exception e) {

								}
							}
						}
					} else {
						System.out.println("final reajuste total, Nº proceso "
								+ reporteTable.get(0).getReprocesoIT());
						break;
					}
				}
				// *************************
				// si aun hay diferencias en el reajuste
				if (TGITO != tEsperado) {
					//reajuste2();
				}
				// *************************
			}
		}
	}
}
// col 24,25,26 contiene vacios
