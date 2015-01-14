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

import javax.swing.JFileChooser;

import bean.Auditoria;
import bean.RegistroCompras;
import bean.TALIAS;
import bean.TREGC;
import bean.TTIDO;
import recursos.Sesion;
import servicio.AuditoriaService;
import servicio.ComprasService;
import ventanas.FIRegistroCompras;
import delegate.GestionCompras;
import delegate.GestionSeguridad;

public class ComprasController implements ActionListener {
	// w
	FIRegistroCompras registroCompras;
	ComprasService servicio = GestionCompras.getComprasService();
	TREGC tregc = new TREGC();
	//TTABD ttabd = null;
	//TPROV tprov = null;
	TALIAS talias = null;
	// *********************************************************************
	// datos que sirven para realizar el quiebre
	String tipoDocumento = "";
	String descDocumento = "";
	// *********************************************************************
	// datos para el reporte
	RegistroCompras reporte = null;
	// lo que se visualiza en el txt
	List<RegistroCompras> reporteTxt = new ArrayList<RegistroCompras>();
	// lo que se visualiza en el txt de errores
	List<RegistroCompras> reporteTxtError = new ArrayList<RegistroCompras>();
	// lo que se visualiza en la tabla detalle de errores
	List<RegistroCompras> detalleErrores = new ArrayList<RegistroCompras>();
	// datos para el detalle de errores
	RegistroCompras reporteError = null;
	AuditoriaService auditoriaService = GestionSeguridad.getAuditoriaService();
	Auditoria auditoria;
	JFileChooser fileChooser;
	String ruta = "";// ruta de la carpeta donde se ubicara el archivo
	String archivo = "";// nombre del archivo txt
	String extension = ".txt";// extension del archivo

	// *********************************************************************
	int periodoInformado = 0;
	int totalRegistros = 0;
	int wSecuTabla = 0;
	int wSecuTXT = 0;
	int xValor = 0;
	double xLrito = 0;
	double xCret1 = 0;
	// acumuladores por tipo de documento
	double TDBI1 = 0;
	double TDIG1 = 0;
	double TDBI2 = 0;
	double TDIG2 = 0;
	double TDBI3 = 0;
	double TDIG3 = 0;
	double TDVAG = 0;
	double TDISC = 0;
	double TDOTC = 0;
	double TDRET = 0;
	double TDITO = 0;
	// acumuladores totales
	double TGBI1 = 0;
	double TGIG1 = 0;
	double TGBI2 = 0;
	double TGIG2 = 0;
	double TGBI3 = 0;
	double TGIG3 = 0;
	double TGVAG = 0;
	double TGISC = 0;
	double TGOTC = 0;
	double TGRET = 0;
	double TGITO = 0;
	// *********************************************************************
	NumberFormat redondea = NumberFormat.getInstance();
	DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
	DecimalFormat formateadorTC;
	DecimalFormat formateador;

	// **********************************************************************
	public ComprasController(FIRegistroCompras registroCompras) {
		this.registroCompras = registroCompras;
		simbolos.setDecimalSeparator('.');

		formateadorTC = new DecimalFormat("####.###", simbolos);
		formateadorTC.setMaximumFractionDigits(3);
		formateadorTC.setMinimumFractionDigits(3);

		formateador = new DecimalFormat("####.##", simbolos);
		formateador.setMaximumFractionDigits(2);
		formateador.setMinimumFractionDigits(2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registroCompras.getTxtEjercicio()) {
			registroCompras.getTxtPeriodo().requestFocus();
		} else if (e.getSource() == registroCompras.getProcesar()
				|| e.getSource() == registroCompras.getTxtPeriodo()) {
			String msjError = "";
			if (registroCompras.getEjercicio().equals(""))
				msjError += "Ingrese Ejercicio \n";
			if (registroCompras.getPeriodo().trim().length() == 1)
				msjError += "-Ingrese formato correcto para el periodo.\n Por ejemplo: 01,02,03...12\n";
			if (msjError.equals("")) {
				registroCompras.getProcesar().setEnabled(false);
				registroCompras.getTxtEjercicio().setEnabled(false);
				registroCompras.getTxtPeriodo().setEnabled(false);
				Thread hilo = new Thread() {
					public void run() {
						try {
							procesar();
						} catch (Exception e) {
							Sesion.mensajeError(registroCompras, e.getMessage());
							registroCompras.getProcesar().setEnabled(true);
							registroCompras.getTxtEjercicio().setEnabled(true);
							registroCompras.getTxtPeriodo().setEnabled(true);
						}

					}
				};
				try {
					hilo.start();
				} catch (Exception ex) {
					Sesion.mensajeError(registroCompras, ex.getMessage());
					registroCompras.getProcesar().setEnabled(true);
					registroCompras.getTxtEjercicio().setEnabled(true);
					registroCompras.getTxtPeriodo().setEnabled(true);
				}

			} else{
				Sesion.mensajeError(registroCompras, msjError);
			}
		}

		else if (e.getSource() == registroCompras.getSalir()) {
			FIRegistroCompras.close();
			registroCompras.salir();
		} else if (e.getSource() == registroCompras.getExportarTXT()) {
			if (reporteTxt.size() > 0) {
				elegirDestino();
			} else{
				Sesion.mensajeError(registroCompras,
						"No hay registros en la tabla que exportar");
				}
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
				archivo = "/LE" + Sesion.ruc.trim()
						+ periodoInformado + "00" + "080100" + "00"
						+ "1111";
				String resultado = exportarTxt(ruta + archivo + extension);
				if (reporteTxtError.size() > 0) {
					resultado += exportarTxtError(ruta + archivo + "-Error"
							+ extension);
				}
				Sesion.mensajeInformacion(registroCompras, resultado);
			} else {
				Sesion.mensajeError(registroCompras, "No esta permitido darle nombre al archivo a exportar,\nsolo debe seleccionar una carpeta de destino.");
				elegirDestino();
			}
		}
	}

	public void procesar() {
		registroCompras.getTextoProgreso().setVisible(true);
		registroCompras.getTextoProgreso().setText("Procesando, por favor espere.");
		
		tregc.setRcejer(Integer.parseInt(registroCompras.getEjercicio()));
		tregc.setRcperi(Integer.parseInt(registroCompras.getPeriodo().equals("")?"01":registroCompras.getPeriodo()));
		try {
			List<TREGC> listaTREGC = registroCompras.getPeriodo().equals("")?servicio.listarTREGCByEjercicio(tregc):servicio.listarTREGC(tregc);
			if (listaTREGC.size() > 0) {
				reporteTxt.clear();
				reporteTxtError.clear();
				detalleErrores.clear();
				
				talias = servicio.listarTALIAS();
				tipoDocumento = listaTREGC.get(0).getTtido().getTdsuna().trim();
				descDocumento = listaTREGC.get(0).getTtido().getTddesc().trim();
				registroCompras.limpiarTabla();
				registroCompras.getProgreso().setVisible(true);
				for (TREGC tregc : listaTREGC) {
					totalRegistros++;
					registroCompras.getProgreso().setValue(
							(totalRegistros * 100) / listaTREGC.size());
					registroCompras.getTextoProgreso().setText(
							"Procesando registro nº: " + totalRegistros
									+ " de: " + listaTREGC.size());
					// si es el mismo tipo de documento
					if (tregc.getTtido().getTdsuna().trim()
							.equalsIgnoreCase(tipoDocumento)) {
						agregarFila(tregc.getTtido(), tregc);
						agregarReporteTXT();
						// registro contra partida si
						// setRegistroContraPartida();
					}
					// sino has la sumarizacion por quiebre
					else {
						// registro el subtotal acumulado por tipo de documento
						agregarSubTotal();
						// sumariza para total general
						sumarizaTotalGeneral();
						// reinicializar las variables para realizar la nueva
						// acumulacion por el cambio tipo de documento
						reinicializarSubTotal();
						// agrego la primera fila del siguiente tipo de
						// documento
						agregarFila(tregc.getTtido(), tregc);
						agregarReporteTXT();
						// registro contra partida si
						// setRegistroContraPartida();
						// seteo el nuevo tipo de documento para la siguiente
						// evaluacion
						tipoDocumento = tregc.getTtido().getTdsuna().trim();
						descDocumento = tregc.getTtido().getTddesc().trim();
					}

				}
			
				agregarSubTotal();
				sumarizaTotalGeneral();
				reinicializarSubTotal();
				agregarTotal();
				if (detalleErrores.size() > 0) {
					registroCompras.cargarTablaErrores(detalleErrores,
							totalRegistros);
				}
				reinicializarTotalgeneral();
				registroCompras.getProgreso().setVisible(false);
			} else {
				Sesion.mensajeInformacion(registroCompras,
						"No existen resultados para el Ejercicio y Periodo ingresado");
				registroCompras.limpiarTabla();
			}
			auditoria = new Auditoria();
			auditoria.setUsu(Sesion.usuario);
			auditoria.setCIA(Sesion.cia);
			auditoria.setFecha(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			auditoria.setModulo("Contabilidad");
			auditoria.setMenu("Reportes");
			auditoria.setOpcion("Regristo de compras");
			auditoria.setTipo_ope("Consulta");
			auditoria.setCant_reg(reporteTxt.size());
			auditoria.setRef1((periodoInformado+"").substring(0, 4));
			auditoria.setRef2((periodoInformado+"").substring(4, 6));
			auditoriaService.insertarAuditoria(auditoria);
			
			registroCompras.getProcesar().setEnabled(true);
			registroCompras.getTxtEjercicio().setEnabled(true);
			registroCompras.getTxtPeriodo().setEnabled(true);
			registroCompras.getTextoProgreso().setVisible(false);
		} catch (SQLException ex) {
			Sesion.mensajeError(registroCompras, ex.getMessage());
			registroCompras.getProcesar().setEnabled(true);
			registroCompras.getTxtEjercicio().setEnabled(true);
			registroCompras.getTxtPeriodo().setEnabled(true);
			registroCompras.getTextoProgreso().setVisible(false);
		}

	}

	private void agregarFila(TTIDO ttido, TREGC tregc) throws SQLException {
		// falta validar logintud de todos los campos segun los permitidos por
		// sunat
		xValor = 0;
		xCret1 = 0;

		//ttabd = servicio.buscarTTABD("TDOCR", tregc.getRctdoc());
		//tprov = servicio.buscarTPROV(tregc.getRccpro());
		reporte = new RegistroCompras();
		String peri="";
		if(tregc.getRcperi()<10){
			peri="0"+tregc.getRcperi();
		}else{
			peri=""+tregc.getRcperi();
		}
		periodoInformado = Integer.parseInt(tregc.getRcejer()+peri);
		// ejercicio periodo
		reporte.setWLREPE_1(periodoInformado + "00");
		// numero unico de registro
		setWLRCOD_2(tregc);
		setCampo33_34_35();
		// fecha de documento
		setWLRFDO_3(tregc);
		// tipo de documento
		reporte.setWLRTDO_5(ttido.getTdsuna());
		// fecha de vencimiento
		setWLRFVE_04(reporte.getWLRTDO_5(), tregc.getRcfeve());
		// codigo de aduana // dua comp. de pago // cred. fiscal
		setWLRCADandWLRDUAandWLRCFI(reporte.getWLRTDO_5().trim(),
				tregc.getRcndoc(),// ok
				tregc.getRcref6(), tregc.getRcndua());
		// importe total operaciones diarias que no otorguen derecho a crédito
		// fiscal en forma consolidada
		reporte.setWLRIOD_9("0");
		// tip. doc. ident. proveedor // dni o ruc ident. proveedor
		setWLRTDIandWLRNRI(tregc);
		// descripcion del proveedor (verificar si es rccpro o rcprov)
		reporte.setWLRNCL_12(tregc.getRcprov());
		// tipo de cambio
		setWLRTCA(tregc.getRcmone(), tregc.getRctcam());
		//
		setWLRFERandWLRTDRandWLRSRRandWLRNRR(reporte.getWLRTDO_5(), tregc);
		// N° comp. pago emitido
		setWLRNDN(reporte.getWLRCFI_8(), reporte.getWLRTDO_5());
		// num detraccion // fecha detraccion
		setWLRDNRandWLRDFE(tregc.getRcref1(), tregc.getRcref2());
		// marca de comp. sujeto a retencion
		setWLRMPR(tregc);// ok
		// estado que identifica la oportunidad de la anotacion
		// si fecha de doc es = a la fecha del proceso
		// System.out.println("setWLREST" + totalRegistros);
		setWLREST(tregc.getRcfech());// ok
		//
		setValorVenta(tregc);
		//
		setAbonos(tregc, reporte.getWLRTDO_5());
		//
		setImportes(tregc);
		// sumariza para el importe total
		sumarizaImporteTotal(tregc);
		// sumariza total por documento
		sumarizaTotalXTipoDocumento();
		xLrito += Double.parseDouble(reporte.getWLRITO_22());
		agregarReporteTabla();
	}

	private void setCampo33_34_35() {
		if(!reporte.getWLRCOD_2().equals("")){
			reporte.setCintdiamay(reporte.getWLRCOD_2().trim());
			reporte.setCintkardex(reporte.getWLRCOD_2().trim());
			reporte.setCintreg(reporte.getWLRCOD_2().trim());
		}else{
			reporte.setCintdiamay("");
			reporte.setCintkardex("");
			reporte.setCintreg("");
			agregarError(33, wSecuTabla + 1, "Campo es obligatorio");
			agregarError(34, wSecuTabla + 1, "Campo es obligatorio");
			agregarError(35, wSecuTabla + 1, "Campo es obligatorio");
		}
	}

	private void setWLRCOD_2(TREGC tregc) {
		if (tregc.getRcrcxp() != null) {
			if (!tregc.getRcrcxp().trim().equals("")) {
				reporte.setWLRCOD_2(tregc.getRcrcxp());
			} else {
				reporte.setWLRCOD_2("");
				agregarError(2, wSecuTabla + 1, "Campo es obligatorio");
			}
		} else {
			reporte.setWLRCOD_2("");
			agregarError(2, wSecuTabla + 1, "Campo es obligatorio");
		}
	}

	private void setWLRFDO_3(TREGC tregc) {
		String wlrfdo = tregc.getRcfech() + "";
		try {
			int fechaWlrfdo = Integer.parseInt(wlrfdo);
			int fdoEjePer = fechaWlrfdo / 100;
			if (periodoInformado >= fdoEjePer) {
				reporte.setWLRFDO_3(wlrfdo.substring(6, 8) + "/"
						+ wlrfdo.substring(4, 6) + "/" + wlrfdo.substring(0, 4));
			} else {
				reporte.setWLRFDO_3(wlrfdo.substring(6, 8) + "/"
						+ wlrfdo.substring(4, 6) + "/" + wlrfdo.substring(0, 4));
				agregarError(3, wSecuTabla + 1,
						"La fecha debe ser menor o igual al periodo señalado en el campo 1");
			}
		} catch (Exception e) {
			reporte.setWLRFDO_3(tregc.getRcfech() + "");
			agregarError(3, wSecuTabla + 1, "Formato incorrecto");
		}
	}

	private void setWLRFVE_04(String wlrtdo, int rcfeve) {
		// 14 = recibo por servicios publicos
		if (wlrtdo.trim().equals("14")) {
			String wlrfve = rcfeve + "";
			try {
				int fechaWlrfve = Integer.parseInt(wlrfve);
				int fveEjerPer = fechaWlrfve / 100;

				if (periodoInformado >= fveEjerPer) {
					reporte.setWLRFVE_4(wlrfve.substring(6, 8) + "/"
							+ wlrfve.substring(4, 6) + "/"
							+ wlrfve.substring(0, 4));
				} else {
					reporte.setWLRFVE_4(wlrfve.substring(6, 8) + "/"
							+ wlrfve.substring(4, 6) + "/"
							+ wlrfve.substring(0, 4));
					agregarError(4, wSecuTabla + 1,
							"La fecha debe ser menor o igual al periodo señalado en el campo 1");
				}
			} catch (Exception e) {
				reporte.setWLRFVE_4(rcfeve + "");
				agregarError(4, wSecuTabla + 1, "Formato incorrecto");
			}

		} else {
			reporte.setWLRFVE_4("01/01/0001");
		}

	}

	private void setWLRCADandWLRDUAandWLRCFI(String wlrtdo, String rcndoc,
			String rcref6, String rcndua) throws SQLException {
		// wlrtdo = si es boleta, factura de venta, notas de credito o notas de
		// debito en soles o dolares
		// rcndoc = codigo de aduana de comprobante de pago
		int cdua = -1;
		if (wlrtdo.equals("01") || wlrtdo.equals("03") || wlrtdo.equals("04")
				|| wlrtdo.equals("07") || wlrtdo.equals("08")) {
			if (rcndoc != null) {
				if (!rcndoc.trim().equals("")) {
					reporte.setWLRCAD_6(rcndoc.trim().substring(1, 5));
				} else {
					reporte.setWLRCAD_6("");
					agregarError(6, wSecuTabla + 1, "Campo es obligatorio");
				}
			} else {
				reporte.setWLRCAD_6("");
				agregarError(6, wSecuTabla + 1, "Campo es obligatorio");
			}
		} else {
			if (rcndoc != null) {
				if (!rcndoc.trim().equals("")) {
					reporte.setWLRCAD_6(rcndoc);
				} else {
					reporte.setWLRCAD_6("-");
				}
			} else {
				reporte.setWLRCAD_6("-");
			}
		}

		// wlrtdo = si es poliza
		if (wlrtdo.equals("50") || wlrtdo.equals("52")) {
			if (rcref6 != null) {
				if (!rcref6.trim().equals("")) {
					reporte.setWLRCAD_6(rcref6.trim().substring(0, 3));
					try {
						cdua = Integer.parseInt(rcref6.substring(3,
								rcref6.length()).trim());
						if (cdua > 1981 && cdua <= this.tregc.getRcejer()) {
							reporte.setWLRDUA_7(cdua + "");
						} else {
							reporte.setWLRDUA_7(cdua + "");
							agregarError(7, wSecuTabla + 1,
									"El año DUA debe ser menor o igual al periodo señalado en el campo 1");
						}
					} catch (Exception e) {
						reporte.setWLRDUA_7(rcref6
								.substring(3, rcref6.length()).trim());
						agregarError(7, wSecuTabla + 1, "Formato incorrecto");
					}
				} else {
					reporte.setWLRCAD_6("");
					agregarError(6, wSecuTabla + 1, "Campo es obligatorio");
					reporte.setWLRDUA_7("");
					agregarError(7, wSecuTabla + 1, "Campo es obligatorio");
				}
			} else {
				reporte.setWLRCAD_6("");
				agregarError(6, wSecuTabla + 1, "Campo es obligatorio");
				reporte.setWLRDUA_7("");
				agregarError(7, wSecuTabla + 1, "Campo es obligatorio");
			}

			if (rcndua != null) {
				if (!rcndua.equals("")) {
					reporte.setWLRCFI_8(rcndua.substring(0, 15));
				} else {
					reporte.setWLRCFI_8("");
					agregarError(8, wSecuTabla + 1, "Campo es obligatorio");
				}
			} else {
				reporte.setWLRCFI_8("");
				agregarError(8, wSecuTabla + 1, "Campo es obligatorio");
			}

		} else {
			reporte.setWLRDUA_7("0");
			if (rcndua != null) {
				if (!rcndua.equals("")) {
					int longitudCampo = Integer.parseInt(talias.getAli014()
							.trim());
					reporte.setWLRCFI_8(rcndoc.substring(longitudCampo, rcndoc
							.trim().length()));
				} else {
					reporte.setWLRCFI_8("");
					agregarError(8, wSecuTabla + 1, "Campo es obligatorio");
				}
			} else {
				reporte.setWLRCFI_8("");
				agregarError(8, wSecuTabla + 1, "Campo es obligatorio");
			}

		}
	}

	private void setWLRTDIandWLRNRI(TREGC tregc)
			throws SQLException {
		if (tregc.getTprov().getProtip() != null) {
			if (tregc.getTprov().getProtip().equalsIgnoreCase("PJ")
					|| tregc.getTprov().getProtip().equalsIgnoreCase("NI")) {
				reporte.setWLRTDI_10("6");
				reporte.setWLRNRI_11(tregc.getRcruc());
			}
			else if (tregc.getTprov().getProtip().equalsIgnoreCase("PN")) {
				//TPROA tproa = servicio.buscarTPROA(tregc.getRccpro());
				if (tregc.getTproa().getPatide() != null) {
					if (tregc.getTproa().getPatide().equalsIgnoreCase("RU")) {
						reporte.setWLRTDI_10("6");
					}
					if (tregc.getTproa().getPatide().equalsIgnoreCase("DN")) {
						reporte.setWLRTDI_10("1");
					}
					reporte.setWLRNRI_11(tregc.getTproa().getPanide());
				}
			}

			else {
				if (tregc.getTprde().getPrdrf1() != null) {
					if (tregc.getTtabdz().getTbalf1() != null) {
						reporte.setWLRTDI_10(tregc.getTtabdz().getTbalf1());
					} else {
						reporte.setWLRTDI_10("0");
					}
					reporte.setWLRNRI_11(tregc.getTprde().getPrdrf1().substring(2,
							tregc.getTprde().getPrdrf1().length()));
				}
			}
		}
		// **********************************************************************************
		if (reporte.getWLRTDI_10() == null)
			reporte.setWLRTDI_10("0");
		if (reporte.getWLRNRI_11() == null)
			reporte.setWLRNRI_11("-");
		
		if(reporte.getWLRTDI_10().trim().equals("")){
			agregarError(10, wSecuTabla + 1,
					"Campo obligatorio");
		}
		if(reporte.getWLRNRI_11().trim().equals("")){
			agregarError(11, wSecuTabla + 1,
					"Campo obligatorio");
		}
	}

	private void setWLRTCA(int rcmone, double rctcam) {
		/*
		if (rcmone != 0) {
			reporte.setWLRTCA_23(redondearTC(rctcam + ""));
		} else
			reporte.setWLRTCA_23("0.000");
			*/
		reporte.setWLRTCA_23(redondearTC(rctcam + ""));
	}

	private void setWLRFERandWLRTDRandWLRSRRandWLRNRR(String wlrtdo, TREGC tregc) {
		if (wlrtdo.equals("07") || wlrtdo.equals("08") || wlrtdo.equals("87")
				|| wlrtdo.equals("88") || wlrtdo.equals("97")
				|| wlrtdo.equals("98")) {
			String wlrfer = tregc.getRcfref() + "";
			try {
				int fechaWlrfer = Integer.parseInt(wlrfer);
				int ferEjerPer = fechaWlrfer / 100;

				if (periodoInformado >= ferEjerPer) {
					reporte.setWLRFER_24(wlrfer.substring(6, 8) + "/"
							+ wlrfer.substring(4, 6) + "/"
							+ wlrfer.substring(0, 4));
				} else {
					reporte.setWLRFER_24(wlrfer.substring(6, 8) + "/"
							+ wlrfer.substring(4, 6) + "/"
							+ wlrfer.substring(0, 4));
					agregarError(24, wSecuTabla + 1,
							"La fecha debe ser menor o igual al periodo señalado en el campo 1");
				}
			} catch (Exception e) {
				reporte.setWLRFER_24(tregc.getRcfref() + "");
				agregarError(24, wSecuTabla + 1, "Formato incorrecto");
			}

			if (tregc.getRctref() != null) {
				if (!(tregc.getRctref().trim().equals(""))) {
					if (tregc.getTtidox().getTdsuna() != null) {
						reporte.setWLRTDR_25(tregc.getTtidox().getTdsuna());
					} else {
						reporte.setWLRTDR_25(tregc.getRctref());
						agregarError(25, wSecuTabla + 1,
								"No se encontro referencia para el tipo de documento.");
					}

				} else {
					reporte.setWLRTDR_25("");
					agregarError(25, wSecuTabla + 1, "Campo es obligatorio.");
				}
			} else {
				reporte.setWLRTDR_25("");
				agregarError(25, wSecuTabla + 1, "Campo es obligatorio.");
			}

			if (tregc.getRcnref() != null) {
				if (!(tregc.getRcnref().trim().equals(""))
						&& tregc.getRcnref().trim().length() > 5) {
					int longitudCampo = Integer.parseInt(talias.getAli014()
							.trim());
					reporte.setWLRSRR_26(tregc.getRcnref().substring(0,
							longitudCampo));
					reporte.setWLRNRR_27(tregc.getRcnref().substring(5,
							tregc.getRcnref().trim().length()));
				} else {
					reporte.setWLRSRR_26("");
					agregarError(26, wSecuTabla + 1, "Campo es obligatorio.");

					reporte.setWLRNRR_27("");
					agregarError(27, wSecuTabla + 1, "Campo es obligatorio.");
				}
			} else {
				reporte.setWLRSRR_26("");
				agregarError(26, wSecuTabla + 1, "Campo es obligatorio.");

				reporte.setWLRNRR_27("");
				agregarError(27, wSecuTabla + 1, "Campo es obligatorio.");
			}
		} else {
			reporte.setWLRFER_24("01/01/0001");
			reporte.setWLRTDR_25("00");
			reporte.setWLRSRR_26("-");
			reporte.setWLRNRR_27("-");
		}
	}

	private void setWLRNDN(String wlrcfi, String wlrtdo) {
		if (wlrtdo.equals("91") || wlrtdo.equals("97") || wlrtdo.equals("98")) {
			if (!wlrcfi.trim().equals("")) {
				reporte.setWLRNDN_28(wlrcfi);
			} else {
				reporte.setWLRNDN_28("");
				agregarError(28, wSecuTabla + 1, "Campo es obligatorio.");
			}
		} else
			reporte.setWLRNDN_28("-");
	}

	private void setWLRDNRandWLRDFE(String rcref1, String rcref2) {
		if (rcref1 != null || rcref2 != null) {
			if (!(rcref1.trim().trim().equals("") || rcref2.trim().equals(""))) {
				int meses = Integer.parseInt(rcref2.substring(3, 5));
				int anos = Integer.parseInt(rcref2.substring(6, 10));

				int dfe = Sesion.convertirFecha(anos, meses);
				int ejePer = Sesion.convertirFecha(periodoInformado/100, periodoInformado%100) + 1;

				if (ejePer >= dfe) {
					reporte.setWLRDFE_29(rcref2);
				} else {
					reporte.setWLRDFE_29(rcref2);
					agregarError(
							29,
							wSecuTabla + 1,
							"La fecha debe ser menor o igual al mes siguiente del periodo señalado en el campo 1");
				}
				try {
					int dnr = Integer.parseInt(rcref1.trim());
					reporte.setWLRDNR_30(dnr + "");
				} catch (Exception e) {
					reporte.setWLRDNR_30(rcref1);
					//agregarError(30, wSecuTabla + 1, "Formato incorrecto");
				}

			} else {
				reporte.setWLRDNR_30("0");
				reporte.setWLRDFE_29("01/01/0001");
			}
		} else {
			reporte.setWLRDNR_30("0");
			reporte.setWLRDFE_29("01/01/0001");
		}
	}

	private void setWLRMPR(TREGC tregc) throws SQLException {
		double rcpvtai = Double.parseDouble(tregc.getRcpvta());
		double ireten = Double.parseDouble(talias.getAli027());

		if (tregc.getTprov().getProrf1().trim().equals("S")) {
			if (rcpvtai > ireten) {
				reporte.setWLRMPR_31("1");
			} else
				reporte.setWLRMPR_31("0");
		} else {
			reporte.setWLRMPR_31("0");
		}

	}

	private void setWLREST(int rcfech) {
		int rcfechEjerPEr = rcfech/100;
		if (rcfechEjerPEr == periodoInformado) {
			reporte.setWLREST_32("1");
		} else {
			int fda = Sesion.convertirFecha(rcfechEjerPEr/100,rcfechEjerPEr%100);
			int xejer = Sesion.convertirFecha(periodoInformado%100,periodoInformado%100);
			xValor = xejer - fda;
			if (xValor <= 12) {
				reporte.setWLREST_32("6");
			} else {
				reporte.setWLREST_32("7");
			}

		}
	}

	private void setValorVenta(TREGC tregc) {

		if (tregc.getRcvalv() > 0) {
			if (tregc.getRcmdsc().equalsIgnoreCase(("C"))) {
				tregc.setRcvalv(tregc.getRcvalv() + tregc.getRcdsct());
			} else
				tregc.setRcvalv(tregc.getRcvalv() - tregc.getRcdsct());
		} else {
			if (tregc.getRcmdsc().equalsIgnoreCase(("C"))) {
				tregc.setRcvali(tregc.getRcvali() + tregc.getRcdsct());
			} else
				tregc.setRcvali(tregc.getRcvali() - tregc.getRcdsct());
		}
	}

	private void setAbonos(TREGC tregc, String wlrtdo) {
		if (tregc.getRcmval().equalsIgnoreCase("A"))
			tregc.setRcvalv(tregc.getRcvalv() * -1);
		if (tregc.getRcmim1().equalsIgnoreCase("A"))
			tregc.setRcimp1(tregc.getRcimp1() * -1);
		if (tregc.getRcmvai().equalsIgnoreCase("A")){
			tregc.setRcvali(tregc.getRcvali() * -1);
		}
		if (tregc.getRcmim2().equalsIgnoreCase("A"))
			tregc.setRcimp2(tregc.getRcimp2() * -1);
		if (tregc.getRcmvvg().equalsIgnoreCase("A"))
			tregc.setRcvavg(tregc.getRcvavg() * -1);
		if (tregc.getRcmim3().equalsIgnoreCase("A"))
			tregc.setRcimp3(tregc.getRcimp3() * -1);
		if (tregc.getRcmvvn().equalsIgnoreCase("A"))
			tregc.setRcvavn(tregc.getRcvavn() * -1);
		if (tregc.getRcmim4().equalsIgnoreCase("A"))
			tregc.setRcimp4(tregc.getRcimp4() * -1);
		if (tregc.getRcmigr().equalsIgnoreCase("A"))
			tregc.setRcvigr(tregc.getRcvigr() * -1);
		if (tregc.getRcmre1().equalsIgnoreCase("A"))
			tregc.setRcret1(tregc.getRcret1() * -1);
		if (tregc.getRcmre2().equalsIgnoreCase("A"))
			tregc.setRcret2(tregc.getRcret2() * -1);
		if (tregc.getRcmre3().equalsIgnoreCase("A"))
			tregc.setRcret3(tregc.getRcret3() * -1);

	}

	private void setImportes(TREGC tregc) throws SQLException {
		if (tregc.getRcmone() == 0) {
			reporte.setWLRBI1_13(tregc.getRcvalv() + "");
			reporte.setWLRIG1_14(tregc.getRcimp1() + "");
			reporte.setWLRBI2_15(tregc.getRcvali() + "");
			reporte.setWLRIG2_16(tregc.getRcimp2() + "");
			reporte.setWLRBI3_17(tregc.getRcvavg() + "");
			reporte.setWLRIG3_18(tregc.getRcimp3() + "");
			reporte.setWLRVAG_19(tregc.getRcvavn() + "");
			reporte.setWLRISC_20(tregc.getRcimp4() + "");
			reporte.setWLROTC_21(tregc.getRcvigr() + "");
			reporte.setWLRRET(tregc.getRcret2() + "");
			// percepcion en soles
			if (tregc.getRcret3() != 0) {
				reporte.setWLROTC_21(tregc.getRcret3() + "");
			}
			// detraccion en soles
			if (tregc.getTtabdx().getTbalf1() != null) {
				xCret1 = tregc.getRcret1();
			}

		} else {
			reporte.setWLRBI1_13(tregc.getRcvalv() * tregc.getRctcam() + "");
			reporte.setWLRIG1_14(tregc.getRcimp1() * tregc.getRctcam() + "");
			reporte.setWLRBI2_15(tregc.getRcvali() * tregc.getRctcam() + "");
			reporte.setWLRIG2_16(tregc.getRcimp2() * tregc.getRctcam() + "");
			reporte.setWLRBI3_17(tregc.getRcvavg() * tregc.getRctcam() + "");
			reporte.setWLRIG3_18(tregc.getRcimp3() * tregc.getRctcam() + "");
			reporte.setWLRVAG_19(tregc.getRcvavn() * tregc.getRctcam() + "");
			reporte.setWLRISC_20(tregc.getRcimp4() * tregc.getRctcam() + "");
			reporte.setWLROTC_21(tregc.getRcvigr() * tregc.getRctcam() + "");
			reporte.setWLRRET(tregc.getRcret2() * tregc.getRctcam() + "");
			// percepcion en dolares
			if (tregc.getRcret3() != 0) {
				reporte.setWLROTC_21(tregc.getRcret3() * tregc.getRctcam() + "");
			}
			// detraccion en dolares
			if (tregc.getTtabdx().getTbalf1() != null) {
				xCret1 = tregc.getRcret1() * tregc.getRctcam();
			}

		}
	}

	private void sumarizaImporteTotal(TREGC tregc) {
		double importeTotalDocumento = Double.parseDouble(reporte
				.getWLRBI1_13())
				+ Double.parseDouble(reporte.getWLRIG1_14())
				+ Double.parseDouble(reporte.getWLRBI2_15())
				+ Double.parseDouble(reporte.getWLRIG2_16())
				+ Double.parseDouble(reporte.getWLRBI3_17())
				+ Double.parseDouble(reporte.getWLRIG3_18())
				+ Double.parseDouble(reporte.getWLRVAG_19())
				+ Double.parseDouble(reporte.getWLRISC_20())
				+ Double.parseDouble(reporte.getWLROTC_21())
				+ Double.parseDouble(reporte.getWLRRET()) + xCret1+tregc.getRcving();
		//RCPVTA
		reporte.setWLRITO_22(tregc.getRcpvta());
	}

	private void sumarizaTotalXTipoDocumento() {
		TDBI1 += Double.parseDouble(reporte.getWLRBI1_13());
		TDIG1 += Double.parseDouble(reporte.getWLRIG1_14());
		TDBI2 += Double.parseDouble(reporte.getWLRBI2_15());
		TDIG2 += Double.parseDouble(reporte.getWLRIG2_16());
		TDBI3 += Double.parseDouble(reporte.getWLRBI3_17());
		TDIG3 += Double.parseDouble(reporte.getWLRIG3_18());
		TDVAG += Double.parseDouble(reporte.getWLRVAG_19());
		TDISC += Double.parseDouble(reporte.getWLRISC_20());
		TDOTC += Double.parseDouble(reporte.getWLROTC_21());
		TDRET += Double.parseDouble(reporte.getWLRRET());
		TDITO += Double.parseDouble(reporte.getWLRITO_22());
		
	}

	/*
	 * private void setRegistroContraPartida() { 
	 * stub if (talias.getAli063().trim().equalsIgnoreCase("S")) { if
	 * (reporte.getWLRTDO_5().equals("91") ||
	 * reporte.getWLRTDO_5().equals("97")) {
	 * reporte.setWLRBI1_13(Double.parseDouble(reporte.getWLRBI1_13()) -1 + "");
	 * reporte.setWLRIG1_14(Double.parseDouble(reporte.getWLRIG1_14()) -1 + "");
	 * reporte.setWLRBI2_15(Double.parseDouble(reporte.getWLRBI2_15()) -1 + "");
	 * reporte.setWLRIG2_16(Double.parseDouble(reporte.getWLRIG2_16()) -1 + "");
	 * reporte.setWLRBI3_17(Double.parseDouble(reporte.getWLRBI3_17()) -1 + "");
	 * reporte.setWLRIG3_18(Double.parseDouble(reporte.getWLRIG3_18()) -1 + "");
	 * reporte.setWLRVAG_19(Double.parseDouble(reporte.getWLRVAG_19()) -1 + "");
	 * reporte.setWLRISC_20(Double.parseDouble(reporte.getWLRISC_20()) -1 + "");
	 * reporte.setWLROTC_21(Double.parseDouble(reporte.getWLROTC_21()) -1 + "");
	 * reporte.setWLRRET(Double.parseDouble(reporte.getWLRRET()) * -1 + "");
	 * reporte.setWLRITO_22(Double.parseDouble(reporte.getWLRITO_22()) -1 + "");
	 * agregarReporteTabla(); sumarizaTotalXTipoDocumento(); xCret1 = 0; } } }
	 */

	private void agregarSubTotal() {
		reporte = new RegistroCompras();
		if (xLrito != 0) {
			reporte.setWLRBI1_13(TDBI1 + "");
			reporte.setWLRIG1_14(TDIG1 + "");
			reporte.setWLRBI2_15(TDBI2 + "");
			reporte.setWLRIG2_16(TDIG2 + "");
			reporte.setWLRBI3_17(TDBI3 + "");
			reporte.setWLRIG3_18(TDIG3 + "");
			reporte.setWLRVAG_19(TDVAG + "");
			reporte.setWLRISC_20(TDISC + "");
			reporte.setWLROTC_21(TDOTC + "");
			reporte.setWLRRET(TDRET + "");
			reporte.setWLRITO_22(TDITO + "");
			reporte.setWLRNCL_12("Total " + descDocumento + " --------->");
			agregarReporteTabla();
			xLrito = 0;
		}
	}

	private void agregarTotal() {
		reporte = new RegistroCompras();
		reporte.setWLRBI1_13(TGBI1 + "");
		reporte.setWLRIG1_14(TGIG1 + "");
		reporte.setWLRBI2_15(TGBI2 + "");
		reporte.setWLRIG2_16(TGIG2 + "");
		reporte.setWLRBI3_17(TGBI3 + "");
		reporte.setWLRIG3_18(TGIG3 + "");
		reporte.setWLRVAG_19(TGVAG + "");
		reporte.setWLRISC_20(TGISC + "");
		reporte.setWLROTC_21(TGOTC + "");
		reporte.setWLRRET(TGRET + "");
		reporte.setWLRITO_22(TGITO + "");
		reporte.setWLRNCL_12("Total General: --------->");
		agregarReporteTabla();
	}

	private void sumarizaTotalGeneral() {
		TGBI1 += TDBI1;
		TGIG1 += TDIG1;
		TGBI2 += TDBI2;
		TGIG2 += TDIG2;
		TGBI3 += TDBI3;
		TGIG3 += TDIG3;
		TGVAG += TDVAG;
		TGISC += TDISC;
		TGOTC += TDOTC;
		TGRET += TDRET;
		TGITO += TDITO;
	}

	private void reinicializarSubTotal() {
		TDBI1 = 0;
		TDIG1 = 0;
		TDBI2 = 0;
		TDIG2 = 0;
		TDBI3 = 0;
		TDIG3 = 0;
		TDVAG = 0;
		TDISC = 0;
		TDOTC = 0;
		TDRET = 0;
		TDITO = 0;
	}

	private void reinicializarTotalgeneral() {
		TGBI1 = 0;
		TGIG1 = 0;
		TGBI2 = 0;
		TGIG2 = 0;
		TGBI3 = 0;
		TGIG3 = 0;
		TGVAG = 0;
		TGISC = 0;
		TGOTC = 0;
		TGRET = 0;
		TGITO = 0;
		totalRegistros = 0;
		wSecuTabla = 0;
		wSecuTXT = 0;
		xValor = 0;
		xLrito = 0;
		xCret1 = 0;
	}

	private void agregarReporteTabla() {
		wSecuTabla += 1;// secuencia
		reporte.setWLRSEQ_0(wSecuTabla + "");
		reporte.setWLRBI1_13(redondear((reporte.getWLRBI1_13())));
		reporte.setWLRIG1_14(redondear((reporte.getWLRIG1_14())));
		reporte.setWLRBI2_15(redondear((reporte.getWLRBI2_15())));
		reporte.setWLRIG2_16(redondear((reporte.getWLRIG2_16())));
		reporte.setWLRBI3_17(redondear((reporte.getWLRBI3_17())));
		reporte.setWLRIG3_18(redondear((reporte.getWLRIG3_18())));
		reporte.setWLRVAG_19(redondear((reporte.getWLRVAG_19())));
		reporte.setWLRISC_20(redondear((reporte.getWLRISC_20())));
		reporte.setWLROTC_21(redondear((reporte.getWLROTC_21())));
		reporte.setWLRRET(redondear((reporte.getWLRRET())));
		reporte.setWLRITO_22(redondear((reporte.getWLRITO_22())));
		if (reporte.getWLRTDO_5() != null) {
			if (!(reporte.getWLRTDO_5().trim().equalsIgnoreCase("07")
					|| reporte.getWLRTDO_5().trim().equalsIgnoreCase("87") || reporte
					.getWLRTDO_5().trim().equalsIgnoreCase("97"))) {
				if (Double.parseDouble(reporte.getWLRBI1_13()) < 0) {
					agregarError(13, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLRIG1_14()) < 0) {
					agregarError(14, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLRBI2_15()) < 0) {
					agregarError(15, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLRIG2_16()) < 0) {
					agregarError(16, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLRBI3_17()) < 0) {
					agregarError(17, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLRIG3_18()) < 0) {
					agregarError(18, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLRVAG_19()) < 0) {
					agregarError(19, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLRISC_20()) < 0) {
					agregarError(20, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLROTC_21()) < 0) {
					agregarError(21, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
				if (Double.parseDouble(reporte.getWLRITO_22()) < 0) {
					agregarError(22, wSecuTabla,
							"Solo se acepta positivos o 0.00");
				}
			}
		}
		registroCompras.cargarTabla(reporte);
	}

	private void agregarReporteTXT() {
		wSecuTXT += 1;
		reporte.setWLRSEQ_0(wSecuTXT + "");

		switch (reporte.getError_0()) {
		case 0:
			reporteTxt.add(reporte);
			break;
		default:
			reporteTxtError.add(reporte);
			reporteTxt.add(reporte);
		}

	}

	private String exportarTxt(String ruta) {
		int totalReg = 0;
		String resultado = "";
		String separador = "|";
		PrintWriter pw = null;
		try {
			RegistroCompras reporte;
			pw = new PrintWriter(new FileWriter(ruta));
			pw.println("CPERIODO|CNUMREGOPE|CFECCOM|CFECVENPAG|CTIPDOCCOM|CNUMSER|CEMIDUADSI|CNUMDCOFV|COSDCREFIS|CTIPDIDPRO|CNUMDIDPRO|CNOMRSOPRO|CBASIMPGRA|CIGVGRA|CBASIMPGNG|CIGVGRANGV|CBASIMPSCF|CIGVSCF|CIMPTOTNGV|CISC|COTRTRIOGO|CIMPTOTCOM|CTIPCAM|CFECCOMMOD|CTIPCOMMOD|CNUNSERMOD|CNUNCOMMOD|CCOMNODOMI|CEMIDEPDET|CNUMDEPDET|CCOMPGRET|CESTOPE|CINTDIAMAY|CINTKARDEX|CINTREG|");
			for (int i = 0; i < reporteTxt.size(); i++) {
				totalReg++;
				reporte = reporteTxt.get(i);
				pw.println(reporte.getWLREPE_1().trim() + separador
						+ reporte.getWLRCOD_2().trim() + separador
						+ reporte.getWLRFDO_3().trim() + separador
						+ reporte.getWLRFVE_4().trim() + separador
						+ reporte.getWLRTDO_5().trim() + separador
						+ reporte.getWLRCAD_6().trim() + separador
						+ reporte.getWLRDUA_7().trim() + separador
						+ reporte.getWLRCFI_8().trim() + separador
						+ reporte.getWLRIOD_9().trim() + separador
						+ reporte.getWLRTDI_10().trim() + separador
						+ reporte.getWLRNRI_11().trim() + separador
						+ reporte.getWLRNCL_12().trim() + separador
						+ reporte.getWLRBI1_13().trim() + separador
						+ reporte.getWLRIG1_14().trim() + separador
						+ reporte.getWLRBI2_15().trim() + separador
						+ reporte.getWLRIG2_16().trim() + separador
						+ reporte.getWLRBI3_17().trim() + separador
						+ reporte.getWLRIG3_18().trim() + separador
						+ reporte.getWLRVAG_19().trim() + separador
						+ reporte.getWLRISC_20().trim() + separador
						+ reporte.getWLROTC_21().trim() + separador
						+ reporte.getWLRITO_22().trim() + separador
						+ reporte.getWLRTCA_23().trim() + separador
						+ reporte.getWLRFER_24().trim() + separador
						+ reporte.getWLRTDR_25().trim() + separador
						+ reporte.getWLRSRR_26().trim() + separador
						+ reporte.getWLRNRR_27().trim() + separador
						+ reporte.getWLRNDN_28().trim() + separador
						+ reporte.getWLRDFE_29().trim() + separador
						+ reporte.getWLRDNR_30().trim() + separador
						+ reporte.getWLRMPR_31().trim() + separador
						+ reporte.getWLREST_32().trim() + separador
						+ reporte.getCintdiamay().trim() + separador
						+ reporte.getCintkardex().trim() + separador
						+ reporte.getCintreg().trim() + separador);
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
			RegistroCompras reporte;
			pw = new PrintWriter(new FileWriter(ruta));
			for (int i = 0; i < reporteTxtError.size(); i++) {
				totalReg++;
				reporte = reporteTxtError.get(i);
				canError += reporte.getError_0();
				pw.println(reporte.getWLREPE_1().trim() + separador
						+ reporte.getWLRCOD_2().trim() + separador
						+ reporte.getWLRFDO_3().trim() + separador
						+ reporte.getWLRFVE_4().trim() + separador
						+ reporte.getWLRTDO_5().trim() + separador
						+ reporte.getWLRCAD_6().trim() + separador
						+ reporte.getWLRDUA_7().trim() + separador
						+ reporte.getWLRCFI_8().trim() + separador
						+ reporte.getWLRIOD_9().trim() + separador
						+ reporte.getWLRTDI_10().trim() + separador
						+ reporte.getWLRNRI_11().trim() + separador
						+ reporte.getWLRNCL_12().trim() + separador
						+ reporte.getWLRBI1_13().trim() + separador
						+ reporte.getWLRIG1_14().trim() + separador
						+ reporte.getWLRBI2_15().trim() + separador
						+ reporte.getWLRIG2_16().trim() + separador
						+ reporte.getWLRBI3_17().trim() + separador
						+ reporte.getWLRIG3_18().trim() + separador
						+ reporte.getWLRVAG_19().trim() + separador
						+ reporte.getWLRISC_20().trim() + separador
						+ reporte.getWLROTC_21().trim() + separador
						+ reporte.getWLRITO_22().trim() + separador
						+ reporte.getWLRTCA_23().trim() + separador
						+ reporte.getWLRFER_24().trim() + separador
						+ reporte.getWLRTDR_25().trim() + separador
						+ reporte.getWLRSRR_26().trim() + separador
						+ reporte.getWLRNRR_27().trim() + separador
						+ reporte.getWLRNDN_28().trim() + separador
						+ reporte.getWLRDFE_29().trim() + separador
						+ reporte.getWLRDNR_30().trim() + separador
						+ reporte.getWLRMPR_31().trim() + separador
						+ reporte.getWLREST_32().trim() + separador
						+ reporte.getCintdiamay().trim() + separador
						+ reporte.getCintkardex().trim() + separador
						+ reporte.getCintreg().trim() + separador);
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

	public String redondearTC(String numero) {
		double num = 0.00;
		try {
			num = Double.parseDouble(numero);
			return formateadorTC.format(num);
		} catch (Exception e) {
	
		}
		return num + "";
	}

	private void agregarError(int col, int fila, String msj) {
		reporte.setError_0(reporte.getError_0() + 1);
		reporteError = new RegistroCompras();
		reporteError.setFila(fila);
		reporteError.setColumna(col);
		reporteError.setDescripcion(msj);
		detalleErrores.add(reporteError);
	}

}
