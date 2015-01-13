package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import bean.RegistroVentas;

import controlador.VentasController;

import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;

public class FIRegistroVentas extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FIRegistroVentas gui = null;
	private JTextField txtEjercicio;
	private JTextField txtPeriodo;
	private JButton btnProcesar;
	private JScrollPane scpReporte;
	private JLabel lblEjercicio;
	private JLabel lblPeriodo;
	private TextFilterDocument tfdEjercicio = new TextFilterDocument(
			TextFilterDocument.DIGITS, 4);
	private TextFilterDocument tfdPeriodo = new TextFilterDocument(
			TextFilterDocument.DIGITS, 2);
	private String[][] datos;
	private String cabecera[] = { "WLRSEQ", "WLREPE_1", "WLRCOD_2", "WLRFCP_3",
			"WLRFVE_4", "WLRTDO_5", "WLRSCP_6", "WLRNCP_7", "WLRIOD_8",
			"WLRTDI_9", "WLRNDI_10", "WLRNCL_11", "WLRVFE_12", "WLRBI1_13",
			"WLRITE_14", "WLRITI_15", "WLRISC_16", "WLRIG1_17", "WLRBI2_18",
			"WLRIVE_19", "WLROTC_20", "WLRITO_21", "WLRTCA_22", "WLRFER_23",
			"WLRTDR_24", "WLRSRR_25", "WLRNRR_26", "WLREST_27", "ERRORES" };
	private DefaultTableModel dtm;
	private JXTable tbReporte;
	private JProgressBar pgbProgreso;
	private JLabel lblProgreso;
	private JLabel lblErrores;
	private JScrollPane scpErrores;
	private DefaultTableModel dtmErrores;
	private JXTable tbErrores;
	private String[][] datosErrores;
	private String cabeceraErrores[] = { "Fila", "Columna", "Descripción" };
	private JLabel lblCantidadDeFilas;
	private JLabel lblCantidadDeErrores;
	private JLabel lblCantErr;
	private JLabel lblCantFil;
	private JXTable tbTotales;
	private DefaultTableModel dtmTotales;
	private String[][] datosTotales;
	private String cabeceraTotales[] = { "Tipo Documento", "WLRVFE_12",
			"WLRBI1_13", "WLRITE_14", "WLRITI_15", "WLRISC_16", "WLRIG1_17",
			"WLRBI2_18", "WLRIVE_19", "WLROTC_20", "WLRITO_21" };
	private JLabel lblSubTotalesPor;
	private JScrollPane scpTotales;

	public static FIRegistroVentas createInstance() {
		if (gui == null) {
			gui = new FIRegistroVentas();
		}
		return gui;
	}

	public static FIRegistroVentas getInstance() {
		return gui;
	}

	public FIRegistroVentas() {
		// initialize();
	}

	public void initialize() {
		setSize(1355, 674);
		setTitle(Sesion.titulo + "-" + Sesion.tifRegistroVentas);
		//
		toolBar.setVisible(true);
		//
		lblEjercicio = new JLabel("Ejercicio:");
		lblEjercicio.setBounds(26, 13, 73, 14);
		contenedorCenter.add(lblEjercicio);

		lblPeriodo = new JLabel("Periodo:");
		lblPeriodo.setBounds(26, 38, 73, 14);
		contenedorCenter.add(lblPeriodo);

		txtEjercicio = new JTextField();
		txtEjercicio.setBounds(109, 11, 46, 20);
		txtEjercicio.setDocument(tfdEjercicio);
		txtEjercicio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtPeriodo.requestFocus();
			}
		});
		contenedorCenter.add(txtEjercicio);

		txtPeriodo = new JTextField();
		txtPeriodo.setBounds(109, 35, 46, 20);
		txtPeriodo.setDocument(tfdPeriodo);
		contenedorCenter.add(txtPeriodo);

		btnProcesar = new JButton("Procesar");
		btnProcesar.setEnabled(false);
		btnProcesar.setBounds(180, 11, 89, 23);
		contenedorCenter.add(btnProcesar);

		dtm = new DefaultTableModel(datos, cabecera);

		tbReporte = new JXTable(dtm);
		tbReporte.getTableHeader().setReorderingAllowed(false);
		// tbReporte.getTableHeader().setResizingAllowed(false);
		tbReporte.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbReporte.setEditable(false);
		tbReporte.setSortable(false);

		scpReporte = new JScrollPane(tbReporte);
		scpReporte.setAutoscrolls(true);
		scpReporte.setBounds(26, 64, 748, 543);
		contenedorCenter.add(scpReporte);

		pgbProgreso = new JProgressBar(0, 100);
		pgbProgreso.setBounds(279, 12, 369, 20);
		pgbProgreso.setStringPainted(true);
		pgbProgreso.setVisible(false);
		contenedorCenter.add(pgbProgreso);

		lblProgreso = new JLabel("");
		lblProgreso.setBounds(279, 39, 369, 14);
		lblProgreso.setVisible(false);
		contenedorCenter.add(lblProgreso);

		lblErrores = new JLabel("Detalle de errores detectados");
		lblErrores.setVisible(false);
		lblErrores.setBounds(781, 239, 200, 14);
		contenedorCenter.add(lblErrores);

		dtmErrores = new DefaultTableModel(datosErrores, cabeceraErrores);

		tbErrores = new JXTable(dtmErrores);
		tbErrores.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tbErrores.getSelectedRow() > -1) {
					seleccionaFila();
				}
			}
		});
		tbErrores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tbErrores.getSelectedRow() > -1) {
					seleccionaFila();
				}
			}
		});

		tbErrores.getTableHeader().setReorderingAllowed(false);
		tbErrores.getTableHeader().setResizingAllowed(false);
		tbErrores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbErrores.setEditable(false);
		tbErrores.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);

		scpErrores = new JScrollPane(tbErrores);
		scpErrores.setVisible(false);
		scpErrores.setAutoscrolls(true);
		scpErrores.setBounds(781, 314, 546, 293);
		contenedorCenter.add(scpErrores);

		lblCantidadDeFilas = new JLabel("Cantidad de filas validadas:");
		lblCantidadDeFilas.setVisible(false);
		lblCantidadDeFilas.setBounds(781, 264, 178, 14);
		contenedorCenter.add(lblCantidadDeFilas);

		lblCantidadDeErrores = new JLabel("Cantidad de errores:");
		lblCantidadDeErrores.setVisible(false);
		lblCantidadDeErrores.setBounds(781, 289, 137, 14);
		contenedorCenter.add(lblCantidadDeErrores);

		lblCantErr = new JLabel("");
		lblCantErr.setVisible(false);
		lblCantErr.setBounds(979, 289, 46, 14);
		contenedorCenter.add(lblCantErr);

		lblCantFil = new JLabel("");
		lblCantFil.setVisible(false);
		lblCantFil.setBounds(979, 264, 46, 14);
		contenedorCenter.add(lblCantFil);

		lblSubTotalesPor = new JLabel(
				"Sub Totales por tipo de documento y Total General:");
		lblSubTotalesPor.setBounds(781, 66, 383, 14);
		contenedorCenter.add(lblSubTotalesPor);

		dtmTotales = new DefaultTableModel(datosTotales, cabeceraTotales);
		tbTotales = new JXTable(dtmTotales);
		tbTotales.getTableHeader().setReorderingAllowed(false);
		// tbTotales.getTableHeader().setResizingAllowed(false);
		tbTotales.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbTotales.setEditable(false);
		tbTotales.setSortable(false);

		scpTotales = new JScrollPane(tbTotales);
		scpTotales.setBounds(781, 91, 546, 140);
		contenedorCenter.add(scpTotales);

		txtEjercicio.requestFocus();
		limpiarTablaTotales();
		limpiarTabla();
	}

	public String getEjercicio() {
		return txtEjercicio.getText().trim();
	}

	public String getPeriodo() {
		return txtPeriodo.getText().trim();
	}

	public DefaultTableModel getDTM() {
		return dtm;
	}

	public JTextField getTxtEjercicio() {
		return txtEjercicio;
	}

	public JTextField getTxtPeriodo() {
		return txtPeriodo;
	}

	public JButton getProcesar() {
		return btnProcesar;
	}

	public JButton getSalir() {
		return btnSalir;
	}

	public JButton getExportarTXT() {
		return btnExportarTxt;
	}

	public JProgressBar getProgreso() {
		return pgbProgreso;
	}

	public JLabel getTextoProgreso() {
		return lblProgreso;
	}

	public void limpiarTabla() {
		try {
			lblErrores.setVisible(false);
			lblCantidadDeErrores.setVisible(false);
			lblCantidadDeFilas.setVisible(false);
			lblCantErr.setVisible(false);
			lblCantFil.setVisible(false);
			scpErrores.setVisible(false);
			tbErrores.setVisible(false);
			repaint();

			tbReporte.setModel(dtm = new DefaultTableModel(datos, cabecera));

			TableColumn colWLRSEQ = tbReporte.getColumn(0);
			TableColumn colWLREPE = tbReporte.getColumn(1);
			TableColumn colWLRCOD = tbReporte.getColumn(2);
			TableColumn colWLRFCP = tbReporte.getColumn(3);
			TableColumn colWLRFVE = tbReporte.getColumn(4);
			TableColumn colWLRTDO = tbReporte.getColumn(5);
			TableColumn colWLRSCP = tbReporte.getColumn(6);
			TableColumn colWLRNCP = tbReporte.getColumn(7);
			TableColumn colWLRIOD = tbReporte.getColumn(8);
			TableColumn colWLRTDI = tbReporte.getColumn(9);
			TableColumn colWLRNDI = tbReporte.getColumn(10);
			TableColumn colWLRNCL = tbReporte.getColumn(11);
			TableColumn colWLRVFE = tbReporte.getColumn(12);
			TableColumn colWLRBI1 = tbReporte.getColumn(13);
			TableColumn colWLRITE = tbReporte.getColumn(14);
			TableColumn colWLRITI = tbReporte.getColumn(15);
			TableColumn colWLRISC = tbReporte.getColumn(16);
			TableColumn colWLRIG1 = tbReporte.getColumn(17);
			TableColumn colWLRBI2 = tbReporte.getColumn(18);
			TableColumn colWLRIVE = tbReporte.getColumn(19);
			TableColumn colWLROTC = tbReporte.getColumn(20);
			TableColumn colWLRITO = tbReporte.getColumn(21);
			TableColumn colWLRTCA = tbReporte.getColumn(22);
			TableColumn colWLRFER = tbReporte.getColumn(23);
			TableColumn colWLRTDR = tbReporte.getColumn(24);
			TableColumn colWLRSRR = tbReporte.getColumn(25);
			TableColumn colWLRNRR = tbReporte.getColumn(26);
			TableColumn colWLREST = tbReporte.getColumn(27);
			TableColumn colERRORES = tbReporte.getColumn(28);

			colWLRSEQ.setPreferredWidth(60);
			colWLREPE.setPreferredWidth(80);
			colWLRCOD.setPreferredWidth(100);
			colWLRFCP.setPreferredWidth(80);
			colWLRFVE.setPreferredWidth(80);
			colWLRTDO.setPreferredWidth(80);
			colWLRSCP.setPreferredWidth(80);
			colWLRNCP.setPreferredWidth(80);
			colWLRIOD.setPreferredWidth(80);
			colWLRTDI.setPreferredWidth(80);
			colWLRNDI.setPreferredWidth(80);
			colWLRNCL.setPreferredWidth(350);
			colWLRVFE.setPreferredWidth(90);
			colWLRBI1.setPreferredWidth(90);
			colWLRITE.setPreferredWidth(90);
			colWLRITI.setPreferredWidth(90);
			colWLRISC.setPreferredWidth(90);
			colWLRIG1.setPreferredWidth(90);
			colWLRBI2.setPreferredWidth(90);
			colWLRIVE.setPreferredWidth(90);
			colWLROTC.setPreferredWidth(90);
			colWLRITO.setPreferredWidth(90);
			colWLRTCA.setPreferredWidth(90);
			colWLRFER.setPreferredWidth(90);
			colWLRTDR.setPreferredWidth(90);
			colWLRSRR.setPreferredWidth(90);
			colWLRNRR.setPreferredWidth(90);
			colWLREST.setPreferredWidth(90);
			colERRORES.setPreferredWidth(90);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarTabla(RegistroVentas reporte) {
		try {
			Object datos[] = { reporte.getWLRSEQ_0(), reporte.getWLREPE_1(),
					reporte.getWLRCOD_2(), reporte.getWLRFCP_3(),
					reporte.getWLRFVE_4(), reporte.getWLRTDO_5(),
					reporte.getWLRSCP_6(), reporte.getWLRNCP_7(),
					reporte.getWLRIOD_8(), reporte.getWLRTDI_9(),
					reporte.getWLRNDI_10(), reporte.getWLRNCL_11(),
					reporte.getWLRVFE_12(), reporte.getWLRBI1_13(),
					reporte.getWLRITE_14(), reporte.getWLRITI_15(),
					reporte.getWLRISC_16(), reporte.getWLRIG1_17(),
					reporte.getWLRBI2_18(), reporte.getWLRIVE_19(),
					reporte.getWLROTC_20(), reporte.getWLRITO_21(),
					reporte.getWLRTCA_22(), reporte.getWLRFER_23(),
					reporte.getWLRTDR_24(), reporte.getWLRSRR_25(),
					reporte.getWLRNRR_26(), reporte.getWLREST_27(),
					reporte.getError() };
			dtm.addRow(datos);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarTabla(List<RegistroVentas> reportes) {
		limpiarTabla();
		try {
			for (RegistroVentas reporte : reportes) {
				Object datos[] = { reporte.getWLRSEQ_0(),
						reporte.getWLREPE_1(), reporte.getWLRCOD_2(),
						reporte.getWLRFCP_3(), reporte.getWLRFVE_4(),
						reporte.getWLRTDO_5(), reporte.getWLRSCP_6(),
						reporte.getWLRNCP_7(), reporte.getWLRIOD_8(),
						reporte.getWLRTDI_9(), reporte.getWLRNDI_10(),
						reporte.getWLRNCL_11(), reporte.getWLRVFE_12(),
						reporte.getWLRBI1_13(), reporte.getWLRITE_14(),
						reporte.getWLRITI_15(), reporte.getWLRISC_16(),
						reporte.getWLRIG1_17(), reporte.getWLRBI2_18(),
						reporte.getWLRIVE_19(), reporte.getWLROTC_20(),
						reporte.getWLRITO_21(), reporte.getWLRTCA_22(),
						reporte.getWLRFER_23(), reporte.getWLRTDR_24(),
						reporte.getWLRSRR_25(), reporte.getWLRNRR_26(),
						reporte.getWLREST_27(), reporte.getError() };
				dtm.addRow(datos);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void limpiarTablaTotales() {
		tbTotales.setModel(dtmTotales = new DefaultTableModel(datosTotales,
				cabeceraTotales));

		TableColumn colTipo = tbTotales.getColumn(0);
		TableColumn colWLRVFE = tbTotales.getColumn(1);
		TableColumn colWLRBI1 = tbTotales.getColumn(2);
		TableColumn colWLRITE = tbTotales.getColumn(3);
		TableColumn colWLRITI = tbTotales.getColumn(4);
		TableColumn colWLRISC = tbTotales.getColumn(5);
		TableColumn colWLRIG1 = tbTotales.getColumn(6);
		TableColumn colWLRBI2 = tbTotales.getColumn(7);
		TableColumn colWLRIVE = tbTotales.getColumn(8);
		TableColumn colWLROTC = tbTotales.getColumn(9);
		TableColumn colWLRITO = tbTotales.getColumn(10);

		colTipo.setPreferredWidth(160);
		colWLRVFE.setPreferredWidth(90);
		colWLRBI1.setPreferredWidth(90);
		colWLRITE.setPreferredWidth(90);
		colWLRITI.setPreferredWidth(90);
		colWLRISC.setPreferredWidth(90);
		colWLRIG1.setPreferredWidth(90);
		colWLRBI2.setPreferredWidth(90);
		colWLRIVE.setPreferredWidth(90);
		colWLROTC.setPreferredWidth(90);
		colWLRITO.setPreferredWidth(90);
	}

	public void cargarTablaTotales(List<RegistroVentas> reportes) {
		limpiarTablaTotales();
		try {
			for (RegistroVentas reporte : reportes) {
				Object datos[] = { reporte.getWLRNCL_11(),
						reporte.getWLRVFE_12(), reporte.getWLRBI1_13(),
						reporte.getWLRITE_14(), reporte.getWLRITI_15(),
						reporte.getWLRISC_16(), reporte.getWLRIG1_17(),
						reporte.getWLRBI2_18(), reporte.getWLRIVE_19(),
						reporte.getWLROTC_20(), reporte.getWLRITO_21() };
				dtmTotales.addRow(datos);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarTablaTotales(RegistroVentas reporte) {
		try {
			Object datos[] = { reporte.getWLRNCL_11(), reporte.getWLRVFE_12(),
					reporte.getWLRBI1_13(), reporte.getWLRITE_14(),
					reporte.getWLRITI_15(), reporte.getWLRISC_16(),
					reporte.getWLRIG1_17(), reporte.getWLRBI2_18(),
					reporte.getWLRIVE_19(), reporte.getWLROTC_20(),
					reporte.getWLRITO_21() };
			dtmTotales.addRow(datos);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void limpiarTablaErrores() {
		try {
			tbErrores.setModel(dtmErrores = new DefaultTableModel(datosErrores,
					cabeceraErrores));
			TableColumn colFila = tbErrores.getColumn(0);
			TableColumn colColumna = tbErrores.getColumn(1);
			TableColumn colDescripcion = tbErrores.getColumn(2);

			colFila.setPreferredWidth(100);
			colColumna.setPreferredWidth(100);
			colDescripcion.setPreferredWidth(500);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarTablaErrores(List<RegistroVentas> reportes, int totalFilas) {
		try {
			limpiarTablaErrores();
			lblErrores.setVisible(true);
			lblCantidadDeErrores.setVisible(true);
			lblCantidadDeFilas.setVisible(true);
			lblCantErr.setVisible(true);
			lblCantFil.setVisible(true);
			scpErrores.setVisible(true);
			tbErrores.setVisible(true);
			lblCantFil.setText(totalFilas + "");
			lblCantErr.setText(reportes.size() + "");

			for (RegistroVentas reporte : reportes) {
				Object datos[] = { reporte.getFila(), reporte.getColumna(),
						reporte.getDescripcion() };
				dtmErrores.addRow(datos);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void setControlador(VentasController controlador) {
		btnProcesar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnExportarTxt.addActionListener(controlador);
		txtPeriodo.addActionListener(controlador);
	}

	private void seleccionaFila() {
		int fila = Integer.parseInt(tbErrores.getValueAt(
				tbErrores.getSelectedRow(), 0).toString());
		int columna = Integer.parseInt(tbErrores.getValueAt(
				tbErrores.getSelectedRow(), 1).toString());
		tbReporte.changeSelection(fila - 1, columna, false, false);
	}

	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
}
