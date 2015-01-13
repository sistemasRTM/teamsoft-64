package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXTable;
import controlador.FacturacionController;
import bean.CertificadoPercepcion;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;

public class FICertificadoPercepcion extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FICertificadoPercepcion gui = null;
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
	private String cabecera[] = { "Item", "col_1", "col_2", "col_3",
			"col_4", "col_5", "col_6", "col_7", "col_8",
			"col_9", "col_10", "col_11", "col_12", "col_13",
			"col_14", "col_15", "col_16", "col_17", "col_18","ERRORES" };
	private DefaultTableModel dtm;
	private JXTable tbReporte;
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

	public static FICertificadoPercepcion createInstance() {
		if (gui == null) {
			gui = new FICertificadoPercepcion();
		}
		return gui;
	}

	public static FICertificadoPercepcion getInstance() {
		return gui;
	}

	public FICertificadoPercepcion() {
		// initialize();
	}

	public void initialize() {
		setSize(1355, 674);
		setTitle(Sesion.titulo + "-" + Sesion.tifCertificadoPercepcion);
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
		scpReporte.setBounds(26, 64, 774, 543);
		contenedorCenter.add(scpReporte);

		lblErrores = new JLabel("Detalle de errores detectados");
		lblErrores.setVisible(false);
		lblErrores.setBounds(810, 64, 200, 14);
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
		scpErrores.setBounds(810, 127, 517, 293);
		contenedorCenter.add(scpErrores);

		lblCantidadDeFilas = new JLabel("Cantidad de filas validadas:");
		lblCantidadDeFilas.setVisible(false);
		lblCantidadDeFilas.setBounds(810, 81, 178, 14);
		contenedorCenter.add(lblCantidadDeFilas);

		lblCantidadDeErrores = new JLabel("Cantidad de errores:");
		lblCantidadDeErrores.setVisible(false);
		lblCantidadDeErrores.setBounds(810, 100, 137, 14);
		contenedorCenter.add(lblCantidadDeErrores);

		lblCantErr = new JLabel("");
		lblCantErr.setVisible(false);
		lblCantErr.setBounds(993, 100, 46, 14);
		contenedorCenter.add(lblCantErr);

		lblCantFil = new JLabel("");
		lblCantFil.setVisible(false);
		lblCantFil.setBounds(993, 81, 46, 14);
		contenedorCenter.add(lblCantFil);

		txtEjercicio.requestFocus();
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

			TableColumn colSecuencia = tbReporte.getColumn(0);
			TableColumn col1 = tbReporte.getColumn(1);
			TableColumn col2 = tbReporte.getColumn(2);
			TableColumn col3 = tbReporte.getColumn(3);
			TableColumn col4 = tbReporte.getColumn(4);
			TableColumn col5 = tbReporte.getColumn(5);
			TableColumn col6 = tbReporte.getColumn(6);
			TableColumn col7 = tbReporte.getColumn(7);
			TableColumn col8 = tbReporte.getColumn(8);
			TableColumn col9 = tbReporte.getColumn(9);
			TableColumn col10 = tbReporte.getColumn(10);
			TableColumn col11 = tbReporte.getColumn(11);
			TableColumn col12 = tbReporte.getColumn(12);
			TableColumn col13 = tbReporte.getColumn(13);
			TableColumn col14 = tbReporte.getColumn(14);
			TableColumn col15 = tbReporte.getColumn(15);
			TableColumn col16 = tbReporte.getColumn(16);
			TableColumn col17 = tbReporte.getColumn(17);
			TableColumn col18 = tbReporte.getColumn(18);
			TableColumn colERRORES = tbReporte.getColumn(19);

			colSecuencia.setPreferredWidth(45);
			col1.setPreferredWidth(45);
			col2.setPreferredWidth(100);
			col3.setPreferredWidth(350);
			col4.setPreferredWidth(90);
			col5.setPreferredWidth(80);
			col6.setPreferredWidth(80);
			col7.setPreferredWidth(45);
			col8.setPreferredWidth(45);
			col9.setPreferredWidth(80);
			col10.setPreferredWidth(50);
			col11.setPreferredWidth(50);
			col12.setPreferredWidth(50);
			col13.setPreferredWidth(55);
			col14.setPreferredWidth(50);
			col15.setPreferredWidth(50);
			col16.setPreferredWidth(60);
			col17.setPreferredWidth(90);
			col18.setPreferredWidth(90);
			colERRORES.setPreferredWidth(90);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarTabla(CertificadoPercepcion reporte) {
		try {
			Object datos[] = { reporte.getSecuencia(),reporte.getCol1(),
					reporte.getCol2(), reporte.getCol3(),
					reporte.getCol4(), reporte.getCol5(),
					reporte.getCol6(), reporte.getCol7(),
					reporte.getCol8(), reporte.getCol9(),
					reporte.getCol10(), reporte.getCol11(),
					reporte.getCol12(), reporte.getCol13(),
					reporte.getCol14(), reporte.getCol15(),
					reporte.getCol16(), reporte.getCol17(),
					reporte.getCol18(), reporte.getError()};
			dtm.addRow(datos);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarTabla(List<CertificadoPercepcion> reportes) {
		limpiarTabla();
		try {
			for (CertificadoPercepcion reporte : reportes) {
				Object datos[] = { reporte.getSecuencia(),reporte.getCol1(),
						reporte.getCol2(), reporte.getCol3(),
						reporte.getCol4(), reporte.getCol5(),
						reporte.getCol6(), reporte.getCol7(),
						reporte.getCol8(), reporte.getCol9(),
						reporte.getCol10(), reporte.getCol11(),
						reporte.getCol12(), reporte.getCol13(),
						reporte.getCol14(), reporte.getCol15(),
						reporte.getCol16(), reporte.getCol17(),
						reporte.getCol18(),reporte.getError()};
				dtm.addRow(datos);
			}
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

			colFila.setPreferredWidth(45);
			colColumna.setPreferredWidth(70);
			colDescripcion.setPreferredWidth(350);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarTablaErrores(List<CertificadoPercepcion> reportes, int totalFilas) {
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
			
			for (CertificadoPercepcion reporte : reportes) {
				Object datos[] = { reporte.getFila(), reporte.getColumna(),
						reporte.getDescripcion() };
				dtmErrores.addRow(datos);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void setControlador(FacturacionController controlador) {
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
