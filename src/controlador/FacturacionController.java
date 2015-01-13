package controlador;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.jdesktop.swingx.JXTable;
import bean.CertificadoPercepcion;
import bean.TARTIAP;
import bean.TCP;
import bean.TDFCEPE;
import bean.TCLIEP;
import bean.TDFCPE;
import bean.TDHCPE;
import bean.THCEPE;
import bean.TPARAM;
import bean.TPEDH;
import delegate.GestionComision;
import delegate.GestionSeguridad;
import delegate.GestionVentas;
import recursos.Numero_a_Letra;
import recursos.Sesion;
import servicio.ComisionService;
import servicio.ParametrosService;
import servicio.TPEDHService;
import ventanas.FICertificadoPercepcion;
import ventanas.FIGenerarCertificado;
import ventanas.FIImprimirPedidos;
import ventanas.FIVerificarPercepcion;
import ventanas.FMenuPrincipalFacturacion;

public class FacturacionController implements ActionListener {
	JDialog modal = null;
	JDialog mVerListadoPedidos = null;
	FIGenerarCertificado mGenerarCertificado = new FIGenerarCertificado();
	FIVerificarPercepcion mVerificarPercepcion = new FIVerificarPercepcion();
	FICertificadoPercepcion mCertificadoPercepcion = new FICertificadoPercepcion();
	FIImprimirPedidos mImprimirPedidos = new FIImprimirPedidos();
	ComisionService servicio = GestionComision.getComisionService();
	ParametrosService servicioParametros = GestionSeguridad
			.getParametrosService();
	TPEDHService servicioVentas = GestionVentas.getTPEDHService();
	List<TDFCEPE> listado = new ArrayList<TDFCEPE>();
	List<TDHCPE> listadoImpresion = new ArrayList<TDHCPE>();
	List<TDHCPE> listadoDocumento = new ArrayList<TDHCPE>();
	List<CertificadoPercepcion> reporteCertificadosTable = new ArrayList<CertificadoPercepcion>();
	List<CertificadoPercepcion> detalleCertificadoErrores = new ArrayList<CertificadoPercepcion>();
	List<CertificadoPercepcion> reporteCertificadoTxtError = new ArrayList<CertificadoPercepcion>();
	CertificadoPercepcion reporteCertificadoError = null;
	int wSecuTabla = 0;
	int periodoInformado = 0;
	CertificadoPercepcion reporteCertificado = null;
	THCEPE thcepe = null;
	double itperc = 0.0;
	JasperPrint jpCertificadoPercepcion = null;

	public FacturacionController(FIGenerarCertificado mGenerarCertificado) {
		this.mGenerarCertificado = mGenerarCertificado;
	}

	public FacturacionController(FIVerificarPercepcion mVerificarPercepcion) {
		this.mVerificarPercepcion = mVerificarPercepcion;
	}

	public FacturacionController(FICertificadoPercepcion mCertificadoPercepcion) {
		this.mCertificadoPercepcion = mCertificadoPercepcion;
	}
	
	public FacturacionController(FIImprimirPedidos mImprimirPedidos) {
		this.mImprimirPedidos = mImprimirPedidos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (mGenerarCertificado.getBtnProcesar() == e.getSource()) {
			procesar();
		}

		else if (mGenerarCertificado.getBtnAgregar() == e.getSource()) {
			agregar();
		}

		else if (mGenerarCertificado.getBtnQuitar() == e.getSource()) {
			quitar();
		}

		else if (mGenerarCertificado.getBtnSalir() == e.getSource()) {
			FIGenerarCertificado.close();
			mGenerarCertificado.salir();
		}

		else if (mVerificarPercepcion.getBtnProcesar() == e.getSource()) {
			verificarPercepcion();
		}

		else if (mVerificarPercepcion.getBtnAgregar() == e.getSource()
				|| mVerificarPercepcion.getTxtFactura() == e.getSource()) {
			agregar2();
		}

		else if (mVerificarPercepcion.getBtnQuitar() == e.getSource()) {
			quitar2();
		}

		else if (mVerificarPercepcion.getBtnSalir() == e.getSource()) {
			FIVerificarPercepcion.close();
			mVerificarPercepcion.salir();
		}

		else if (mCertificadoPercepcion.getProcesar() == e.getSource() || mCertificadoPercepcion.getTxtPeriodo() == e.getSource()) {
			String msjError = "";
			if (mCertificadoPercepcion.getEjercicio().equals(""))
				msjError += "-Ingrese Ejercicio \n";
			if (mCertificadoPercepcion.getPeriodo().equals(""))
				msjError += "-Ingrese Periodo \n";
			if (mCertificadoPercepcion.getPeriodo().trim().length() == 1)
				msjError += "-Ingrese formato correcto para el periodo.\n Por ejemplo: 01,02,03...12\n";
			if (msjError.equals("")) {
				procesarCertificadoPercepcion();
			}else {
				Sesion.mensajeError(mCertificadoPercepcion, msjError);
			}
			
		}

		else if (mCertificadoPercepcion.getExportarTXT() == e.getSource()) {
			if (reporteCertificadosTable.size() > 0) {
				elegirDestino();
			} else {
				Sesion.mensajeError(mCertificadoPercepcion,
						"No hay registros en la tabla que exportar");
			}
		}

		else if (mCertificadoPercepcion.getSalir() == e.getSource()) {
			FICertificadoPercepcion.close();
			mCertificadoPercepcion.salir();
		}
		
		else if (mImprimirPedidos.getBtnProcesar() == e.getSource()) {
			procesarImprimirPedidos();
		}
		
		else if (mImprimirPedidos.getBtnImprimir() == e.getSource()) {
			for (int i = 0; i < mImprimirPedidos.getTable().getRowCount(); i++) {
				String valor = mImprimirPedidos.getTable().getValueAt(i,0).toString().trim();
				if(valor.equals("true")){
					String phpvta = mImprimirPedidos.getTable().getValueAt(i,1).toString().trim();
					String phnume = mImprimirPedidos.getTable().getValueAt(i,2).toString().trim();
					imprimirPedidos(phpvta,phnume);
				}
			}			
		}
		
		else if (mImprimirPedidos.getBtnSalir() == e.getSource()) {
			FIImprimirPedidos.close();
			mImprimirPedidos.salir();
		}
	}

	private void imprimirPedidos(String phpvta,String phnume) {
		try {
				List<TPEDH> pedidos = servicioVentas.listarDetallePedido(Integer.parseInt(phpvta), Integer.parseInt(phnume));
				
				if(pedidos.size()>0){
				JasperReport reporte = (JasperReport) JRLoader
						.loadObject(ClassLoader
								.getSystemResource("reportes/jpPedido.jasper"));
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.clear();
				parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
				parametros.put("PHNOMC", pedidos.get(0).getPHNOMC());
				parametros.put("CLNIDE", pedidos.get(0).getCLNIDE());
				parametros.put("PHPVTA", pedidos.get(0).getPHPVTA()+"");
				parametros.put("PHNUME", pedidos.get(0).getPHNUME()+"");
				parametros.put("PHDIRC", pedidos.get(0).getPHDIRC().trim()+"-" + pedidos.get(0).getPHDISC().trim() + "-" + pedidos.get(0).getCLIPRO().trim() + "-" + pedidos.get(0).getCLIDPT().trim());
				parametros.put("PHUSAP", pedidos.get(0).getPHUSAP());
				parametros.put("PHFECP", pedidos.get(0).getPHFECP()+"");
				parametros.put("PHTCAM", pedidos.get(0).getPHTCAM()+"");
				parametros.put("AGENOM", pedidos.get(0).getAGENOM());
				parametros.put("CPADES", pedidos.get(0).getCPADES());
				parametros.put("PDTDOC", pedidos.get(0).getPDTDOC());
				parametros.put("PDFABO", pedidos.get(0).getPDFABO());
				for (int i = 0; i < pedidos.size(); i++) {
					pedidos.get(i).setPDREF1C(Double.parseDouble(pedidos.get(i).getPDREF1())/100);
					pedidos.get(i).setPU( Sesion.formateaDecimal_4((pedidos.get(i).getPDUNIT() * (pedidos.get(i).getPDREF1C()/100))));
					if(pedidos.get(i).getPDREFE()>0){
						pedidos.get(i).setFLAG("*");
					}else{
						pedidos.get(i).setFLAG("");
					}
				}
				
				JasperPrint jpLetras = JasperFillManager.fillReport(reporte,
						parametros,new JRBeanCollectionDataSource(pedidos));
				//JasperViewer.viewReport(jpLetras);
				
				AttributeSet attributeSet = new HashAttributeSet();
				attributeSet
						.add(new PrinterName(Sesion.impresoraMatricial, null));
				DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
				PrintService[] impresoras = PrintServiceLookup
						.lookupPrintServices(docFormat, attributeSet);
				JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
				jrprintServiceExporter.setParameter(
						JRExporterParameter.JASPER_PRINT, jpLetras);
				jrprintServiceExporter.setParameter(
						JRPrintServiceExporterParameter.PRINT_SERVICE,
						impresoras[0]);
				jrprintServiceExporter.setParameter(
						JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
						Boolean.FALSE);
				jrprintServiceExporter.exportReport();
				
				}else{
					Sesion.mensajeError(mImprimirPedidos, "El pedido no es valido, porque no tiene detalle. Serie: "+phpvta+" Número: "+phnume);
				}	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void procesarImprimirPedidos() {
		try {
			List<TPEDH> pedidos = null;
			TPEDH tpedh = new TPEDH(); 
			int serie = mImprimirPedidos.getSerie();
			tpedh.setPHPVTA(serie);
			String numero = mImprimirPedidos.getNumero();
			if(!numero.equals("")){
				tpedh.setPHNUME(Integer.parseInt(numero));
				pedidos = servicioVentas.listarPedidos(tpedh);
				if(pedidos.size()<=0){
					Sesion.mensajeError(mImprimirPedidos, "Número de pedido no valido");
				}
			}else{
				String fDesde = Sesion.formateaFechaVista(mImprimirPedidos.getFechaDesde());
				String fHasta = Sesion.formateaFechaVista(mImprimirPedidos.getFechaHasta());
				pedidos = servicioVentas.listarPedidos(serie,Integer.parseInt(fDesde.substring(6, 10) + fDesde.substring(3, 5) + fDesde.substring(0, 2)),
						Integer.parseInt(fHasta.substring(6, 10) + fHasta.substring(3, 5) + fHasta.substring(0, 2)));
				if(pedidos.size()<=0){
					Sesion.mensajeError(mImprimirPedidos, "No existen pedidos para el punto de venta y rango de fechas seleccionado");
				}
			}
			mImprimirPedidos.cargaTabla(pedidos);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void elegirDestino() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileGeneral = fileChooser.getSelectedFile();
			if (fileGeneral.isDirectory()) {
				String ruta = fileGeneral.toString();
				String archivo = "/0697" + Sesion.ruc.trim() + periodoInformado;
				String resultado = exportarTxt(ruta + archivo + ".txt");
				if (reporteCertificadoTxtError.size() > 0) {
					resultado += exportarTxtError(ruta + archivo + "-Error"
							+ ".txt");
				}
				Sesion.mensajeInformacion(mCertificadoPercepcion, resultado);
			} else {
				Sesion.mensajeError(
						mCertificadoPercepcion,
						"No esta permitido darle nombre al archivo a exportar,\nsolo debe seleccionar una carpeta de destino.");
				elegirDestino();
			}
		}
	}

	private String exportarTxt(String ruta) {
		int totalReg = 0;
		String resultado = "";
		String separador = "|";
		PrintWriter pw = null;
		try {
			CertificadoPercepcion reporte;
			pw = new PrintWriter(new FileWriter(ruta));
			for (int i = 0; i < reporteCertificadosTable.size(); i++) {
				totalReg++;
				reporte = reporteCertificadosTable.get(i);
				pw.println(reporte.getCol1().trim() + separador
						+ reporte.getCol2().trim() + separador
						+ reporte.getCol3().trim() + separador
						+ reporte.getCol4().trim() + separador
						+ reporte.getCol5().trim() + separador
						+ reporte.getCol6().trim() + separador
						+ reporte.getCol7().trim() + separador
						+ reporte.getCol8().trim() + separador
						+ reporte.getCol9().trim() + separador
						+ reporte.getCol10().trim() + separador
						+ reporte.getCol11().trim() + separador
						+ reporte.getCol12().trim() + separador
						+ reporte.getCol13().trim() + separador
						+ reporte.getCol14().trim() + separador
						+ reporte.getCol15().trim() + separador
						+ reporte.getCol16().trim() + separador
						+ reporte.getCol17().trim() + separador
						+ reporte.getCol18().trim() + separador);
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
			CertificadoPercepcion reporte;
			pw = new PrintWriter(new FileWriter(ruta));
			for (int i = 0; i < reporteCertificadoTxtError.size(); i++) {
				totalReg++;
				reporte = reporteCertificadoTxtError.get(i);
				canError += reporte.getError();
				pw.println(reporte.getCol1().trim() + separador
						+ reporte.getCol2().trim() + separador
						+ reporte.getCol3().trim() + separador
						+ reporte.getCol4().trim() + separador
						+ reporte.getCol5().trim() + separador
						+ reporte.getCol6().trim() + separador
						+ reporte.getCol7().trim() + separador
						+ reporte.getCol8().trim() + separador
						+ reporte.getCol9().trim() + separador
						+ reporte.getCol10().trim() + separador
						+ reporte.getCol11().trim() + separador
						+ reporte.getCol12().trim() + separador
						+ reporte.getCol13().trim() + separador
						+ reporte.getCol14().trim() + separador
						+ reporte.getCol15().trim() + separador
						+ reporte.getCol16().trim() + separador
						+ reporte.getCol17().trim() + separador
						+ reporte.getCol18().trim() + separador);
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

	private void procesarCertificadoPercepcion() {
		mCertificadoPercepcion.getProcesar().setEnabled(false);
		mCertificadoPercepcion.getTxtEjercicio().setEnabled(false);
		mCertificadoPercepcion.getTxtPeriodo().setEnabled(false);
		String año = mCertificadoPercepcion.getEjercicio();
		String mes = mCertificadoPercepcion.getPeriodo();
		try {
			// **************************************************
			wSecuTabla = 0;
			periodoInformado = Integer.parseInt(año + mes);
			reporteCertificadosTable.clear();
			reporteCertificadoTxtError.clear();
			detalleCertificadoErrores.clear();
			mCertificadoPercepcion.limpiarTabla();
			// **************************************************
			List<TCP> listado = servicio.listarCertificadoPercepcion(
					Integer.parseInt(año), Integer.parseInt(mes));
			if (listado.size() > 0) {
				for (TCP tcp : listado) {
					agregarFila(tcp);
				}
				if (detalleCertificadoErrores.size() > 0) {
					mCertificadoPercepcion.cargarTablaErrores(
							detalleCertificadoErrores, listado.size());
				}
			} else {
				Sesion.mensajeInformacion(mCertificadoPercepcion,
						"No existe resultados para el ejercicio y periodo ingresado");
			}
			mCertificadoPercepcion.getProcesar().setEnabled(true);
			mCertificadoPercepcion.getTxtEjercicio().setEnabled(true);
			mCertificadoPercepcion.getTxtPeriodo().setEnabled(true);
		} catch (SQLException e) {
			e.printStackTrace();
			mCertificadoPercepcion.getProcesar().setEnabled(true);
			mCertificadoPercepcion.getTxtEjercicio().setEnabled(true);
			mCertificadoPercepcion.getTxtPeriodo().setEnabled(true);
		}
	}

	private void agregarFila(TCP tcp) {
		reporteCertificado = new CertificadoPercepcion();
		setColumna1(tcp);
		setColumna2(tcp);
		setColumna3_4_5_6(tcp);
		setColumna7(tcp);
		setColumna8(tcp);
		setColumna9(tcp);
		setColumna14(tcp);
		setColumna10(tcp);
		setColumna11(tcp);
		setColumna12(tcp);
		setColumna13(tcp);
		setColumna15(tcp);
		setColumna16(tcp);
		setColumna17(tcp);
		setColumna18(tcp);
		// ***********************************************************
		wSecuTabla += 1;
		reporteCertificado.setSecuencia(wSecuTabla + "");
		reporteCertificadosTable.add(reporteCertificado);
		mCertificadoPercepcion.cargarTabla(reporteCertificado);
		// ***********************************************************
		switch (reporteCertificado.getError()) {
		case 0:
			break;
		default:
			reporteCertificadoTxtError.add(reporteCertificado);
		}
	}

	private void setColumna18(TCP tcp) {
		reporteCertificado.setCol18(tcp.getTotal() + "");
	}

	private void setColumna17(TCP tcp) {
		reporteCertificado.setCol17(Sesion.formateaFechaVista(tcp.getPdfecf()));
	}

	private void setColumna16(TCP tcp) {
		reporteCertificado.setCol16(tcp.getPdfabo() + "");
	}

	private void setColumna15(TCP tcp) {
		reporteCertificado.setCol15(tcp.getPdpvta() + "");
	}

	private void setColumna14(TCP tcp) {
		if (tcp.getPdtdoc().trim().equalsIgnoreCase("FC")) {
			reporteCertificado.setCol14("01");
		} else if (tcp.getPdtdoc().trim().equalsIgnoreCase("BV")) {
			reporteCertificado.setCol14("03");
		} else {
			reporteCertificado.setCol14("--");
		}
	}

	private void setColumna13(TCP tcp) {
		reporteCertificado.setCol13(tcp.getMtperc() + "");
	}

	private void setColumna12(TCP tcp) {
		if (tcp.getPperc() == 0.0050) {
			reporteCertificado.setCol12("1");
		} else {
			reporteCertificado.setCol12("0");
		}
	}

	private void setColumna11(TCP tcp) {
		reporteCertificado.setCol11("0");
	}

	private void setColumna10(TCP tcp) {
		if (tcp.getPdtdoc().trim().equalsIgnoreCase("FC")) {
			reporteCertificado.setCol10("1");
		} else if (tcp.getPdtdoc().trim().equalsIgnoreCase("BV")) {
			reporteCertificado.setCol10("0");
		} else {
			reporteCertificado.setCol10("--");
		}
	}

	private void setColumna9(TCP tcp) {
		reporteCertificado.setCol9(Sesion.formateaFechaVista(tcp.getFecha()));
	}

	private void setColumna8(TCP tcp) {
		reporteCertificado.setCol8(tcp.getNumero() + "");
	}

	private void setColumna7(TCP tcp) {
		reporteCertificado.setCol7(tcp.getSerie() + "");
	}

	private void setColumna3_4_5_6(TCP tcp) {
		boolean flagNom = false;
		boolean flagTD = false;
		if (tcp.getClnom() != null) {
			if (!tcp.getClnom().trim().equals("")) {
				if (!reporteCertificado.getCol1().trim().equals("")
						&& !reporteCertificado.getCol2().trim().equals("")) {
					try {
						int digitos = Integer.parseInt(reporteCertificado
								.getCol2().substring(0, 2));
						//cambio
						if (reporteCertificado.getCol1().equals("6")
								&& digitos != 10 && digitos != 15 && digitos != 17) {
							reporteCertificado.setCol3(tcp.getClnom().trim());
							reporteCertificado.setCol4("");
							reporteCertificado.setCol5("");
							reporteCertificado.setCol6("");
						} else if (reporteCertificado.getCol1().equals("1")
								|| reporteCertificado.getCol1().equals("4")
								|| reporteCertificado.getCol1().equals("7")
								|| reporteCertificado.getCol1().equals("0")
								|| (reporteCertificado.getCol1().equals("6") && (digitos == 10 || digitos == 15 || digitos == 17 ) ) ) {
							reporteCertificado.setCol3("");
							String[] datos = tcp.getClnom().trim().split(" ");
							for (int i = 0; i < datos.length; i++) {
								if (i == 0) {
									reporteCertificado.setCol4(datos[i]);
								}
								if (i == 1) {
									reporteCertificado.setCol5(datos[i]);
								}
								if (i == 2) {
									reporteCertificado.setCol6(datos[i]);
								}
								if (i >= 3) {
									reporteCertificado
											.setCol6(reporteCertificado
													.getCol6() + " " + datos[i]);
								}
							}
							if (datos.length < 2) {
								reporteCertificado.setCol5("");
							}
							if (datos.length < 3) {
								reporteCertificado.setCol6("");
							}
						} else {
							flagTD = true;
						}
					} catch (Exception e) {
						flagTD = true;
						agregarError(2, wSecuTabla + 1,
								"Formato incorrecto");
					}

				} else {
					flagTD = true;
				}
			} else {
				flagNom = true;
			}
		} else {
			flagNom = true;
		}

		if (flagNom) {
			reporteCertificado.setCol3("");
			reporteCertificado.setCol4("");
			reporteCertificado.setCol5("");
			reporteCertificado.setCol6("");
			agregarError(3, wSecuTabla + 1,
					"Campo es obligatorio, verificar en maestra de clientes");
			agregarError(4, wSecuTabla + 1,
					"Campo es obligatorio, verificar en maestra de clientes");
			agregarError(5, wSecuTabla + 1,
					"Campo es obligatorio, verificar en maestra de clientes");
			agregarError(6, wSecuTabla + 1,
					"Campo es obligatorio, verificar en maestra de clientes");
		}

		if (flagTD) {
			reporteCertificado.setCol3(tcp.getClnom().trim());
			reporteCertificado.setCol4("");
			reporteCertificado.setCol5("");
			reporteCertificado.setCol6("");
		}
	}

	private void setColumna2(TCP tcp) {
		if (tcp.getClnide() != null) {
			if (!tcp.getClnide().trim().equals("")) {
				reporteCertificado.setCol2(tcp.getClnide().trim());
			} else {
				reporteCertificado.setCol2("");
				agregarError(2, wSecuTabla + 1,
						"Campo es obligatorio, verificar en maestra de clientes");
			}
		} else {
			reporteCertificado.setCol2("");
			agregarError(2, wSecuTabla + 1,
					"Campo es obligatorio, verificar en maestra de clientes");
		}
	}

	private void setColumna1(TCP tcp) {
		if (tcp.getCltide() != null) {
			if (!tcp.getCltide().trim().equals("")) {
				if (tcp.getCltide().trim().equalsIgnoreCase("DN")) {
					reporteCertificado.setCol1("1");
				} else if (tcp.getCltide().trim().equalsIgnoreCase("RU")) {
					reporteCertificado.setCol1("6");
				} else if (tcp.getCltide().trim().equalsIgnoreCase("CE")) {
					reporteCertificado.setCol1("4");
				} else if (tcp.getCltide().trim().equalsIgnoreCase("PS")) {
					reporteCertificado.setCol1("7");
				} else if (tcp.getCltide().trim().equalsIgnoreCase("OD")) {
					reporteCertificado.setCol1("0");
				} else {
					reporteCertificado.setCol1(tcp.getCltide());
					agregarError(1, wSecuTabla + 1,
							"Tipo de documento no valido para sunat, verificar en maestra de clientes");
				}
			} else {
				reporteCertificado.setCol1("");
				agregarError(1, wSecuTabla + 1,
						"Campo es obligatorio, verificar en maestra de clientes");
			}
		} else {
			reporteCertificado.setCol1("");
			agregarError(1, wSecuTabla + 1,
					"Campo es obligatorio, verificar en maestra de clientes");
		}
	}

	private void agregarError(int col, int fila, String msj) {
		reporteCertificado.setError(reporteCertificado.getError() + 1);
		reporteCertificadoError = new CertificadoPercepcion();
		reporteCertificadoError.setFila(fila);
		reporteCertificadoError.setColumna(col);
		reporteCertificadoError.setDescripcion(msj);
		detalleCertificadoErrores.add(reporteCertificadoError);
	}

	private void quitar2() {
		JXTable tbDocumentos = mVerificarPercepcion.getTablaDocumentos();
		if (tbDocumentos.getSelectedRow() > -1) {
			int serie = Integer.parseInt(tbDocumentos.getValueAt(
					tbDocumentos.getSelectedRow(), 1).toString());
			int numero = Integer.parseInt(tbDocumentos.getValueAt(
					tbDocumentos.getSelectedRow(), 2).toString());
			mVerificarPercepcion.getDTM().removeRow(
					tbDocumentos.getSelectedRow());
			for (int i = 0; i < listado.size(); i++) {
				if (listado.get(i).getPdpvta() == serie
						&& listado.get(i).getPdfabo() == numero) {
					listado.remove(i);
					i--;
				}
			}
		} else {
			Sesion.mensajeError(mVerificarPercepcion, "Seleccione un pedido");
		}
	}

	private void agregar2() {
		if (!mVerificarPercepcion.getFactura().equals("")) {
			JXTable tbDocumentos = mVerificarPercepcion.getTablaDocumentos();
			boolean repetido = false;
			TDFCEPE tcepe = new TDFCEPE();
			tcepe.setPdpvta(mVerificarPercepcion.getPhpvta());
			tcepe.setPdfabo(mVerificarPercepcion.getPdfabo());
			for (int i = 0; i < tbDocumentos.getRowCount(); i++) {
				if (tcepe.getPdpvta() == Integer.parseInt(tbDocumentos
						.getValueAt(i, 1).toString())
						&& tcepe.getPdfabo() == Integer.parseInt(tbDocumentos
								.getValueAt(i, 2).toString())) {
					repetido = true;
				}
			}

			if (repetido == false) {
				try {
					List<TDFCEPE> pedidos = servicio.listarPedidos(tcepe);
					if (pedidos.size() > 0) {
						// ***************
						if (!pedidos.get(0).getPhsitu().equals("05")
								&& !pedidos.get(0).getPhsitu().equals("99")) {
							if (tbDocumentos.getRowCount() > 0) {
								if (listado.get(0).getPhclie()
										.equals(pedidos.get(0).getPhclie())) {
									if (listado.get(0).getPdpvta() == pedidos
											.get(0).getPdpvta()) {
										for (TDFCEPE tcepef : pedidos) {
											listado.add(tcepef);
										}
										mVerificarPercepcion.cargaTabla(pedidos
												.get(0));
										verListado();
									} else {
										Sesion.mensajeError(
												mVerificarPercepcion,
												"Los comprobantes a procesar deben de ser de la misma serie");
									}

								} else {
									Sesion.mensajeError(mVerificarPercepcion,
											"El pedido pertenece a otro cliente");
								}
							} else {
								for (TDFCEPE tcepef : pedidos) {
									listado.add(tcepef);
								}
								mVerificarPercepcion.cargaTabla(pedidos.get(0));
								verListado();

							}
						} else {
							if (pedidos.get(0).getPhsitu().equals("05")) {
								Sesion.mensajeError(mVerificarPercepcion,
										"El pedido ya ha sido facturado, ingrese otro.");
							} else if (pedidos.get(0).getPhsitu().equals("99")) {
								Sesion.mensajeError(mVerificarPercepcion,
										"El pedido ha sido anulado, ingrese otro");
							}
						}
						// ***************
					} else {
						Sesion.mensajeError(mVerificarPercepcion,
								"El pedido no existe");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				Sesion.mensajeError(mVerificarPercepcion,
						"El pedido ya ha sido agregado, ingrese otro.");
			}
		} else {
			Sesion.mensajeError(mVerificarPercepcion,
					"Ingrese número de pedido");
		}

	}

	private void verificarPercepcion() {
		if (listado.size() > 0) {
				String msj = "";
			try {
				// verifico si cliente se encuentra en tabla de perceptores
				TCLIEP tcliep = servicio.isCPerceptor(listado.get(0));
				if (tcliep != null) {
					if(tcliep.getClnrf2().trim().equals("00")){
						Sesion.pperc = 0;
					}
					else if(tcliep.getClnrf2().trim().equals("01")){
						Sesion.pperc = Sesion.porperCampo1;
					}
					else if(tcliep.getClnrf2().trim().equals("02")){
						Sesion.pperc = Sesion.porperCampo2;
					}
				} else {
					Sesion.pperc = -1;
				}
			} catch (SQLException e) {
				msj = e.getMessage()+"\n"+e.getCause();
				e.printStackTrace();
			}
			if(Sesion.pperc>0){
				clasificar();
				boolean resultado = sumarizarClasificados();
				if (resultado) {
					agruparClasificados2();
					verPedidosAfectos();
				}
			}else if(Sesion.pperc==0){
				Sesion.mensajeError(mVerificarPercepcion, "Cliente no afecto a percepción");
			}else if(Sesion.pperc==-1){
				Sesion.mensajeError(mVerificarPercepcion, "Cliente no se encontro en TCLIC(Datos complementarios del cliente).\nComuniquese con el area de sistemas.");
			}else if(Sesion.pperc==-2){
				Sesion.mensajeError(mVerificarPercepcion, msj);
			}else {
				Sesion.mensajeError(mVerificarPercepcion, msj+"\nCódigo Sistemas Nº:-2");
			}
			itperc = 0.0;
			mVerificarPercepcion.limpiarTabla();
			listado.clear();
			listadoDocumento.clear();
			listadoImpresion.clear();
		}

	}

	private void verPedidosAfectos() {
		double venta = 0.0;
		double vperc = 0.0;
		double total = 0.0;
		mVerListadoPedidos = new JDialog(
				FMenuPrincipalFacturacion.getInstance(),
				ModalityType.DOCUMENT_MODAL);
		mVerListadoPedidos.setLayout(null);
		String[][] datos = null;
		String[] cabecera = { "Tipo", "Serie", "Numero", "Valor Venta",
				"Percepción", "Sub Total" };
		DefaultTableModel dtm = new DefaultTableModel(datos, cabecera);
		JXTable tabla = new JXTable(dtm);
		tabla.setEditable(false);
		JScrollPane scp = new JScrollPane(tabla);
		scp.setBounds(0, 0, 500, 200);
		JLabel lblVenta = new JLabel("Total Venta");
		JLabel lblPercepcion = new JLabel("Total Percepcion");
		JLabel lblTotal = new JLabel("Neto a Pagar");
		JTextField txtVenta = new JTextField();
		JTextField txtPercepcion = new JTextField();
		JTextField txtTotal = new JTextField();
		txtVenta.setEnabled(false);
		txtPercepcion.setEnabled(false);
		txtTotal.setEnabled(false);
		txtVenta.setBounds(0, 220, 150, 20);
		txtPercepcion.setBounds(170, 220, 150, 20);
		txtTotal.setBounds(340, 220, 150, 20);
		lblVenta.setBounds(0, 205, 150, 15);
		lblPercepcion.setBounds(170, 205, 150, 15);
		lblTotal.setBounds(340, 205, 150, 15);
		for (TDHCPE tdhcpe : listadoImpresion) {
			Object[] dato = { tdhcpe.getPdtdoc(), tdhcpe.getPdpvta(),
					tdhcpe.getPdfabo(),
					Sesion.formateaDecimal_2(tdhcpe.getPhnpvt()),
					Sesion.formateaDecimal_2(tdhcpe.getVperc()),
					Sesion.formateaDecimal_2(tdhcpe.getVtfperc()) };
			dtm.addRow(dato);
			venta += tdhcpe.getPhnpvt();
			vperc += tdhcpe.getVperc();
			total += tdhcpe.getVtfperc();
		}
		txtVenta.setText(Sesion.formateaDecimal_2(venta));
		txtPercepcion.setText(Sesion.formateaDecimal_2(vperc));
		txtTotal.setText(Sesion.formateaDecimal_2(total));

		mVerListadoPedidos.add(scp);
		mVerListadoPedidos.add(lblVenta);
		mVerListadoPedidos.add(lblPercepcion);
		mVerListadoPedidos.add(lblTotal);
		mVerListadoPedidos.add(txtVenta);
		mVerListadoPedidos.add(txtPercepcion);
		mVerListadoPedidos.add(txtTotal);
		mVerListadoPedidos.setBounds(0, 0, 510, 280);
		mVerListadoPedidos.setLocationRelativeTo(mVerificarPercepcion);
		mVerListadoPedidos.setVisible(true);
	}

	private void agruparClasificados2() {
		int i = 0;
		boolean flag = true;
		while (i <= (listado.size() - 1) && flag) {
			int j = listado.get(i).getPdfabo();
			double mtperc = 0.0;
			while (j == listado.get(i).getPdfabo() && i <= (listado.size() - 1)
					&& flag) {
				if (listado.get(i).getEstado() == 1) {
					mtperc += listado.get(i).getVperc();
				}
				i++;
				if (i > listado.size() - 1) {
					flag = false;
					break;
				}
			}
			TDHCPE tdhcpe = new TDHCPE();
			tdhcpe.setPdtdoc(listado.get(i - 1).getPdtdoc());
			tdhcpe.setPdpvta(listado.get(i - 1).getPdpvta());
			tdhcpe.setPdfabo(listado.get(i - 1).getPdfabo());

			tdhcpe.setPhnpvt(listado.get(i - 1).getPhnpvt());
			tdhcpe.setPperc(Sesion.pperc);
			tdhcpe.setVperc(mtperc);
			tdhcpe.setVtfperc(tdhcpe.getPhnpvt() + mtperc);
			listadoImpresion.add(tdhcpe);
		}

	}

	private void procesar() {
		boolean flag = false;
		if (listado.size() > 0) {
			try {
				// verifico si cliente se encuentra en tabla de perceptores
				TCLIEP tcliep = servicio.isCPerceptor(listado.get(0));
				// si cliente es perceptor seteo el % por defecto para ellos
				// 0.05
				if (tcliep != null) {
					flag = true;
					Sesion.pperc = Sesion.porperCampo2;
				} else {
					// sino es perceptor seteo el % por defecto para el resto
					// 0.2
					flag = false;
					Sesion.pperc = Sesion.porperCampo1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (flag == false) {
				clasificar();
				boolean resultado = sumarizarClasificados();
				if (resultado) {
					agruparClasificados();
					generarCertificado();
				}
			} else {
				Sesion.mensajeInformacion(mGenerarCertificado,
						"El cliente es un agente perceptor, sus pedidos no estan sujetos a percepción");
			}
			itperc = 0.0;
			mGenerarCertificado.limpiarTabla();
			listado.clear();
			listadoDocumento.clear();
			listadoImpresion.clear();
		}

	}

	private void clasificar() {
		for (TDFCEPE tcepef : listado) {
			try {
				// verifico si articulo se encuentra en tabla de perceptores
				TARTIAP tartiap = servicio.isAPerceptor(tcepef);
				// si articulo es perceptor
				if (tartiap != null) {
					tcepef.setEstado(2);
				} else {
					// sino es perceptor
					tcepef.setEstado(9);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean sumarizarClasificados() {
		double base = 0;
		boolean flag = false;
		String msj = "Los documentos ingresados no requieren certificado de percepción,\nporque no superan el monto limite";
		for (TDFCEPE tcepef : listado) {
			//if (tcepef.getEstado() == 2 || tcepef.getEstado() == 9 ) {
				base += tcepef.getPdnpvt();
			//}
		}
		if (listado.get(0).getPdtdoc().trim().equals("FC")
				|| listado.get(0).getPdtdoc().trim().equals("FG")) {
			if (base > Sesion.monlimCampo1) {
				flag =calcular();//ultima verificacion si monto de afectos es mayor a 0 flag es true
				if (flag == false) {
					msj = "Los documentos ingresados no requieren certificado de percepción,\nporque no tienen artículos afectos";
				}
			}
		} else {
			if (base >= Sesion.monlimCampo2) {
				flag = calcular();
				if (flag == false) {
					msj = "Los documentos ingresados no requieren certificado de percepción,\nporque no tienen artículos afectos";
				}
			}
		}
		//no afecto por no superar el monto limite
		if (flag == false) {
			Sesion.mensajeInformacion(mGenerarCertificado,
					msj);
		}
		return flag;

	}

	private boolean calcular() {
		boolean estado = false;
		for (TDFCEPE tcepef : listado) {
			if (tcepef.getEstado() == 2) {
				estado = true;
				tcepef.setEstado(1);
				// calculo el valor de la percepcion
				tcepef.setVperc(tcepef.getPdnpvt() * Sesion.pperc);
			}
		}
		return estado;
	}

	private void agruparClasificados() {
		int i = 0;
		boolean flag = true;
		while (i <= (listado.size() - 1) && flag) {
			int j = listado.get(i).getPdfabo();
			double mtperc = 0.0;
			double mtfac = 0.0;
			while (j == listado.get(i).getPdfabo() && i <= (listado.size() - 1)
					&& flag) {
				if (listado.get(i).getEstado() == 1) {
					mtperc += listado.get(i).getVperc();
					mtfac += listado.get(i).getPdnpvt();
				}
				i++;
				if (i > listado.size() - 1) {
					flag = false;
					break;
				}
			}

			String fecha = listado.get(i - 1).getPdfecf() + "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			TDHCPE tdhcpe = new TDHCPE();
			tdhcpe.setSerie(Integer.parseInt(Sesion.serie));
			tdhcpe.setPdtdoc(listado.get(i - 1).getPdtdoc());
			tdhcpe.setPdpvta(listado.get(i - 1).getPdpvta());
			tdhcpe.setPdfabo(listado.get(i - 1).getPdfabo());
			try {
				tdhcpe.setPdfecf(sdf.parse(fecha.substring(6, 8) + "/"
						+ fecha.substring(4, 6) + "/" + fecha.substring(0, 4)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tdhcpe.setPhnpvt(mtfac);
			tdhcpe.setPperc(Sesion.pperc);
			tdhcpe.setVperc(mtperc);
			tdhcpe.setVtfperc(mtfac + mtperc);
			listadoImpresion.add(tdhcpe);
		}
	}

	private void agregar() {
		if (!mGenerarCertificado.getFactura().equals("")) {
			JXTable tbDocumentos = mGenerarCertificado.getTablaDocumentos();
			boolean repetido = false;
			TDFCEPE tcepe = new TDFCEPE();
			tcepe.setPdtdoc(mGenerarCertificado.getPdtdoc());
			tcepe.setPdpvta(mGenerarCertificado.getPhpvta());
			tcepe.setPdfabo(mGenerarCertificado.getPdfabo());
			for (int i = 0; i < tbDocumentos.getRowCount(); i++) {
				if (tcepe.getPdtdoc().equalsIgnoreCase(
						tbDocumentos.getValueAt(i, 0).toString())
						&& tcepe.getPdpvta() == Integer.parseInt(tbDocumentos
								.getValueAt(i, 1).toString())
						&& tcepe.getPdfabo() == Integer.parseInt(tbDocumentos
								.getValueAt(i, 2).toString())) {
					repetido = true;
				}
			}

			if (repetido == false) {
				try {
					List<TDFCEPE> pedidos = null;
					pedidos = servicio.listarFacturados(tcepe);
					if (pedidos.size() > 0) {
						if (tbDocumentos.getRowCount() > 0) {
							if (listado.get(0).getPhclie()
									.equals(pedidos.get(0).getPhclie())) {
								if (listado
										.get(0)
										.getPdtdoc()
										.trim()
										.equals(pedidos.get(0).getPdtdoc()
												.trim())
										&& listado.get(0).getPdpvta() == pedidos
												.get(0).getPdpvta()) {
									TDHCPE tdhcpe = null;
									tdhcpe = servicio.certicadoGenerado(tcepe);
									if (tdhcpe == null) {
										for (TDFCEPE tcepef : pedidos) {
											listado.add(tcepef);
										}
										mGenerarCertificado.cargaTabla(pedidos
												.get(0));
										verListado();
									} else {
										Sesion.mensajeInformacion(
												mGenerarCertificado,
												"Ya se genero certificado para el documento, agrege otro");
									}

								} else {
									Sesion.mensajeError(mGenerarCertificado,
											"Los comprobantes a procesar deben de ser del mismo tipo y serie");
								}

							} else {
								Sesion.mensajeError(mGenerarCertificado,
										"El comprobante pertenece a otro cliente");
							}
						} else {
							TDHCPE tdhcpe = servicio.certicadoGenerado(tcepe);
							if (tdhcpe == null) {
								for (TDFCEPE tcepef : pedidos) {
									listado.add(tcepef);
								}
								mGenerarCertificado.cargaTabla(pedidos.get(0));
								verListado();
							} else {
								Sesion.mensajeInformacion(mGenerarCertificado,
										"Ya se genero certificado para el documento, agrege otro");
							}
						}
					} else {
						Sesion.mensajeError(mGenerarCertificado,
								"El comprobante de pago no existe");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				Sesion.mensajeError(mGenerarCertificado,
						"El documento ya ha sido agregado, ingrese otro.");
			}
		} else {
			Sesion.mensajeError(mGenerarCertificado,
					"Ingrese número de factura");
		}
	}

	private void quitar() {
		JXTable tbDocumentos = mGenerarCertificado.getTablaDocumentos();
		if (tbDocumentos.getSelectedRow() > -1) {
			String tipo = tbDocumentos
					.getValueAt(tbDocumentos.getSelectedRow(), 0).toString()
					.trim();
			int serie = Integer.parseInt(tbDocumentos.getValueAt(
					tbDocumentos.getSelectedRow(), 1).toString());
			int numero = Integer.parseInt(tbDocumentos.getValueAt(
					tbDocumentos.getSelectedRow(), 2).toString());
			mGenerarCertificado.getDTM().removeRow(
					tbDocumentos.getSelectedRow());
			for (int i = 0; i < listado.size(); i++) {
				if (listado.get(i).getPdtdoc().trim().equals(tipo)
						&& listado.get(i).getPdpvta() == serie
						&& listado.get(i).getPdfabo() == numero) {
					listado.remove(i);
					i--;
				}
			}
		} else {
			Sesion.mensajeError(mVerificarPercepcion, "Seleccione un documento");
		}
	}

	private void verListado() {
		System.out
				.println("**************************************************");
		double base = 0;
		for (TDFCEPE tcepe : listado) {
			System.out.println(tcepe.getPdtdoc() + " " + tcepe.getPdpvta()
					+ " " + tcepe.getPdfabo() + " " + tcepe.getPdarti().trim()
					+ " " + tcepe.getPdnpvt() + " " + tcepe.getVperc() + " "
					+ tcepe.getEstado());

			base += tcepe.getPdnpvt();

		}
		System.out.println("total: " + listado.size());
		System.out.println("Total monto de articulos perceptores " + base);
		System.out.println("Total monto de percepcion " + itperc);
	}

	private void generarCertificado() {
		if (listadoImpresion.size() > 0) {
			int j = 0;
			int i = 0;
			String estado = "";
			while ((i < listadoImpresion.size() && i > -1)
					|| estado.equals("errorImpresion")) {
				// generar correlativo
				int correlativo = generarCorrelativo();
				while (j < Sesion.impcerCampo1 && i < listadoImpresion.size()) {
					// escribe en arcdhivo
					listadoImpresion.get(i).setCorrelativo(i + 1);
					// grabarDocumento(listadoImpresion.get(i));
					listadoDocumento.add(listadoImpresion.get(i));
					itperc += listadoImpresion.get(i).getVperc();
					i++;
					j++;
				}
				// registrar cabecera
				registrarCabecera(correlativo);
				if (listadoDocumento.size() > 0) {
					grabarDocumento();
					// listadoDocumento.clear();
				}
				//
				j = 0;

				// ver correlativo generado
				verCorrelativo();
				// manda a imprimir
				int resultado = imprimirCertificado();
				System.out.println("resultado impresion " + resultado);
				if (resultado == 1) {
					j = Sesion.impcerCampo1 + 1;
					estado = "errorImpresion";
				} else if (resultado == -1) {
					verListado();
					i = -1;
					estado = "ImpresionCancelada";
				} else if (resultado == 0) {
					estado = "ImpresionCorrecta";
					// grabar certificado cab,detalle cab y detalle fac en
					// estado 01
					grabarBD(01);
					// reinicia importe total de percepcion para el nuevo
					// documento
					itperc = 0.0;
					j = 0;
					listadoDocumento.clear();
				}

			}
		}
	}

	private void registrarCabecera(int correlativo) {
		thcepe = new THCEPE();
		thcepe.setCliente(listado.get(0).getPhclie());
		thcepe.setFecha(Sesion.getFechaActualBD());
		thcepe.setItperc(itperc);
		thcepe.setNumero(correlativo);
		thcepe.setPperc(Sesion.pperc);
		thcepe.setSerie(Integer.parseInt(Sesion.serie));
	}

	private void grabarDocumento() {
		try {
			String cltide = "";
			try {
				if (listado.get(0).getCltide().equalsIgnoreCase("RU")) {
					cltide = listado.get(0).getCltide().trim() + "C";
				} else if (listado.get(0).getCltide().equalsIgnoreCase("DN")) {
					cltide = listado.get(0).getCltide().trim() + "I";
				} else {
					cltide = listado.get(0).getCltide().trim();
				}
			} catch (Exception e) {

			}

			JasperReport reporte = (JasperReport) JRLoader
					.loadObject(ClassLoader
							.getSystemResource("reportes/CertificadoPercepcion.jasper"));
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.clear();
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
			parametros.put("nomCli", listado.get(0).getClinom());
			parametros.put("tipoDoc", cltide);
			parametros.put("numDoc", listado.get(0).getClnide());
			parametros.put("fecha", Sesion.getFechaActualVista());
			parametros.put("mtl", Numero_a_Letra.Convertir(
					Sesion.formateaDecimal_2(itperc), true));
			parametros.put("correlativo", "CP: " + Sesion.serie+"-"+thcepe.getNumero());
			jpCertificadoPercepcion = JasperFillManager.fillReport(reporte,
					parametros,
					new JRBeanCollectionDataSource(listadoDocumento));
			// JasperViewer.viewReport(jpCertificadoPercepcion);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	private int generarCorrelativo() {
		int correlativo = -1;
		try {
			int bloqueo = servicioParametros.bloquearCorrelativo("SERCER",
					Sesion.serie);
			if (bloqueo == 0) {
				Thread.sleep(3000);
				generarCorrelativo();
			} else if (bloqueo == 1) {
				TPARAM tparam = servicioParametros.obtenerCorrelativo("SERCER",
						Sesion.serie);
				correlativo = Integer.parseInt(tparam.getCampo1().trim()) + 1;
				servicioParametros.desbloquearCorrelativo(correlativo + "",
						"SERCER", Sesion.serie);
			} else {
				correlativo = -2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return correlativo;
	}

	private void grabarBD(int estado) {
		try {
			thcepe.setSituacion(estado);
			servicio.insertarHCEPE(thcepe);
			for (TDHCPE tdhcpe : listadoImpresion) {
				tdhcpe.setNumero(thcepe.getNumero());
				tdhcpe.setSituacion(thcepe.getSituacion());
				servicio.insertarDHCPE(tdhcpe);
			}
			if (estado == 01) {
				for (TDFCEPE tcepef : listado) {
					if (tcepef.getEstado() == 1) {
						TDFCPE tdfcpe = new TDFCPE();
						tdfcpe.setPdtdoc(tcepef.getPdtdoc());
						tdfcpe.setPdpvta(tcepef.getPdpvta());
						tdfcpe.setPdfabo(tcepef.getPdfabo());
						tdfcpe.setPdarti(tcepef.getPdarti());
						tdfcpe.setPdnpvt(tcepef.getPdnpvt());
						tdfcpe.setVperc(tcepef.getVperc());
						tdfcpe.setSituacion(thcepe.getSituacion());
						tdfcpe.setVtotartperc(tdfcpe.getPdnpvt()
								+ tdfcpe.getVperc());
						servicio.insertarDFCPE(tdfcpe);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void verCorrelativo() {
		modal = new JDialog(FMenuPrincipalFacturacion.getInstance(),
				ModalityType.DOCUMENT_MODAL);
		modal.setLayout(null);

		JLabel lblSerie = new JLabel("Serie:");
		JLabel lblNumero = new JLabel("Número:");
		JLabel lblFecha = new JLabel("Fecha:");
		JTextField txtSerie = new JTextField(Sesion.serie);
		JTextField txtNumero = new JTextField(thcepe.getNumero() + "");
		JTextField txtFecha = new JTextField(Sesion.getFechaActualVista());
		JButton btnAceptar = new JButton("Aceptar");
		lblSerie.setBounds(10, 10, 45, 22);
		txtSerie.setBounds(50, 10, 35, 22);
		lblNumero.setBounds(90, 10, 60, 22);
		txtNumero.setBounds(150, 10, 65, 22);
		lblFecha.setBounds(220, 10, 45, 22);
		txtFecha.setBounds(265, 10, 80, 22);
		btnAceptar.setBounds(130, 40, 100, 22);
		txtSerie.setEnabled(false);
		txtFecha.setEnabled(false);
		txtNumero.setEnabled(false);
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modal.dispose();
			}
		});
		modal.add(lblSerie);
		modal.add(lblNumero);
		modal.add(lblFecha);
		modal.add(txtSerie);
		modal.add(txtNumero);
		modal.add(txtFecha);
		modal.add(btnAceptar);
		modal.setBounds(500, 300, 370, 100);
		modal.setVisible(true);
	}

	private int imprimirCertificado() {
		try {
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName(Sesion.impresoraMatricial, null));
			DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] impresoras = PrintServiceLookup.lookupPrintServices(
					docFormat, attributeSet);
			// if(impresoras.length>0){
			JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();

			jrprintServiceExporter.setParameter(
					JRExporterParameter.JASPER_PRINT, jpCertificadoPercepcion);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					impresoras[0]);
			jrprintServiceExporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			jrprintServiceExporter.exportReport();
			// }else{
			// Sesion.mensajeError(mGenerarCertificado,
			// "No se ha encontrado la impresora configurada, verifique que este encendida.\n El sistema intentara buscarla nuevamente.");
			// imprimirCertificado();
			// }

			int resul = Sesion.mensajeConfirmacion2(mGenerarCertificado,
					"¿Se imprimio el certificado " + thcepe.getNumero()
							+ " correctamente?");

			System.out.println("resul " + resul);
			// si se imprimio correctamente
			if (resul == 0) {
				return 0;
			}
			// sino se imprimio correctamente
			else if (resul == 1) {
				grabarBD(98);
				return 1;
			}
			/*
			 * //si cancelo impresion else if(resul == 2){ return -1; } //si
			 * cerro la ventana else if(resul == -1){ return -1; }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}
}