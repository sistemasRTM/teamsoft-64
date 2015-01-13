package ventanas;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXTable;
import bean.RegistroOperacionDiaria;
import controlador.ContabilidadController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FIRegistroOperacionDiaria extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FIRegistroOperacionDiaria gui=null;
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
	private String cabecera[] = { 
			"Item","Columna 1","Columna 2","Columna 3","Columna 4","Columna 5",
			"Columna 6","Columna 7","Columna 8","Columna 9","Columna 10","ERRORES",
			"Nº Vale","Correlativo","Almacen","Movimiento","Tipo Movimiento","Fecha","Artículo","Cantidad",
			"Cod. Cliente","T. Doc. Cliente","N. Doc. Cliente","Nº Documento","Destino","Lic. Conducir","Placa Veh."};
	private DefaultTableModel dtm;
	private JXTable tbReporte;
	private JProgressBar pgbProgreso;
	private JLabel lblProgreso;
	private JLabel lblErrores;
	private JScrollPane scpErrores;
	private DefaultTableModel dtmErrores;
	private JXTable tbErrores;
	private String[][] datosErrores;
	private String cabeceraErrores[]= {"Fila","Columna","Descripción"};
	private JLabel lblCantidadDeFilas;
	private JLabel lblCantidadDeErrores;
	private JLabel lblCantErr;
	private JLabel lblCantFil;
	private JButton btnCerrarPeriodo;
	
	public static FIRegistroOperacionDiaria createInstance() {
		if (gui == null) {
			gui = new FIRegistroOperacionDiaria();
		}
		return gui;
	}

	public static FIRegistroOperacionDiaria getInstance() {
		return gui;
	}
	
	public FIRegistroOperacionDiaria() {
		//initialize();
	}
	
	public void initialize() {
		setSize(1198, 560);
		setTitle(Sesion.titulo+"-"+Sesion.tifRegistroOperacionDiaria);
		//
		toolBar.setVisible(true);
		//
		lblEjercicio = new JLabel("Ejercicio:");
		lblEjercicio.setBounds(26, 13, 73, 14);
		contenedorCenter.add(lblEjercicio);

		lblPeriodo = new JLabel("Periodo:");
		lblPeriodo.setBounds(26, 44, 73, 14);
		contenedorCenter.add(lblPeriodo);

		txtEjercicio = new JTextField();
		txtEjercicio.setBounds(109, 11, 46, 20);
			
		txtEjercicio.setDocument(tfdEjercicio);
		contenedorCenter.add(txtEjercicio);

		txtPeriodo = new JTextField();
		txtPeriodo.setBounds(109, 41, 46, 20);
		txtPeriodo.setDocument(tfdPeriodo);
		contenedorCenter.add(txtPeriodo);

		btnProcesar = new JButton("Procesar");
		btnProcesar.setEnabled(false);
		btnProcesar.setBounds(165, 11, 104, 23);
		contenedorCenter.add(btnProcesar);

		dtm = new DefaultTableModel(datos, cabecera);

		tbReporte = new JXTable(dtm);
		//tbReporte.getTableHeader().setReorderingAllowed(false);
		tbReporte.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//tbReporte.setSortable(false);
		
		scpReporte = new JScrollPane(tbReporte);
		scpReporte.setAutoscrolls(true);
		scpReporte.setBounds(26, 69, 748, 418);
		contenedorCenter.add(scpReporte);
		
		pgbProgreso = new JProgressBar(0,100);
		pgbProgreso.setBounds(279, 12, 369, 20);
		pgbProgreso.setStringPainted(true);
		pgbProgreso.setVisible(false);
		contenedorCenter.add(pgbProgreso);
		
		lblProgreso = new JLabel("");
		lblProgreso.setBounds(279, 44, 369, 14);
		lblProgreso.setVisible(false);
		contenedorCenter.add(lblProgreso);
												
		lblErrores = new JLabel("Detalle de errores detectados");
		lblErrores.setVisible(false);
		lblErrores.setBounds(784, 71, 200, 14);
		contenedorCenter.add(lblErrores);
		
		dtmErrores = new DefaultTableModel(datosErrores, cabeceraErrores);

		tbErrores = new JXTable(dtmErrores);
		tbErrores.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(tbErrores.getSelectedRow()>-1){
					seleccionaFila();
				}
			}
		});
		tbErrores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tbErrores.getSelectedRow()>-1){
					seleccionaFila();
				}
			}
		});
		
		//tbErrores.getTableHeader().setReorderingAllowed(false);
		//tbErrores.getTableHeader().setResizingAllowed(false);
		tbErrores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbErrores.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);

		scpErrores = new JScrollPane(tbErrores);
		scpErrores.setVisible(false);
		scpErrores.setAutoscrolls(true);
		scpErrores.setBounds(784, 146, 380, 200);
		contenedorCenter.add(scpErrores);
		
		lblCantidadDeFilas = new JLabel("Cantidad de filas validadas:");
		lblCantidadDeFilas.setVisible(false);
		lblCantidadDeFilas.setBounds(784, 96, 178, 14);
		contenedorCenter.add(lblCantidadDeFilas);
		
		lblCantidadDeErrores = new JLabel("Cantidad de errores:");
		lblCantidadDeErrores.setVisible(false);
		lblCantidadDeErrores.setBounds(784, 121, 137, 14);
		contenedorCenter.add(lblCantidadDeErrores);
		
		lblCantErr = new JLabel("");
		lblCantErr.setVisible(false);
		lblCantErr.setBounds(982, 121, 46, 14);
		contenedorCenter.add(lblCantErr);
		
		lblCantFil = new JLabel("");
		lblCantFil.setVisible(false);
		lblCantFil.setBounds(982, 96, 46, 14);
		contenedorCenter.add(lblCantFil);
		
		btnCerrarPeriodo = new JButton("Cerrar Periodo");
		btnCerrarPeriodo.setEnabled(false);
		btnCerrarPeriodo.setBounds(165, 40, 104, 23);
		contenedorCenter.add(btnCerrarPeriodo);
		
		txtEjercicio.requestFocus();
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
	
	public JTextField getTxtEjercicio(){
		return txtEjercicio;
	}
	
	public JTextField getTxtPeriodo(){
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

	public JButton getCerrarPeriodo() {
		return btnCerrarPeriodo;
	}
	
	public JProgressBar getProgreso(){
		return pgbProgreso;
	}
	
	public JLabel getTextoProgreso(){
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
		TableColumn colWLRFDO = tbReporte.getColumn(3);
		TableColumn colWLRFVE = tbReporte.getColumn(4);
		TableColumn colWLRTDO = tbReporte.getColumn(5);
		TableColumn colWLRCAD = tbReporte.getColumn(6);
		TableColumn colWLRDUA = tbReporte.getColumn(7);
		TableColumn colWLRCFI = tbReporte.getColumn(8);
		TableColumn colWLRIOD = tbReporte.getColumn(9);
		TableColumn colWLRTDI = tbReporte.getColumn(10);
		TableColumn colERRORES = tbReporte.getColumn(11);
		TableColumn colVale = tbReporte.getColumn(12);
		TableColumn colCorrelativo = tbReporte.getColumn(13);
		TableColumn colAlmacen = tbReporte.getColumn(14);
		TableColumn colMovimiento = tbReporte.getColumn(15);
		TableColumn colTipoMovimiento = tbReporte.getColumn(16);
		TableColumn colFecha = tbReporte.getColumn(17);
		TableColumn colArticulo = tbReporte.getColumn(18);
		TableColumn colCantidad = tbReporte.getColumn(19);
		TableColumn colCodCliente = tbReporte.getColumn(20);
		TableColumn colTDocCliente = tbReporte.getColumn(21);
		TableColumn colNDocCliente = tbReporte.getColumn(22);
		TableColumn colNDocumento = tbReporte.getColumn(23);
		TableColumn colNDestino = tbReporte.getColumn(24);
		TableColumn colNLicencia = tbReporte.getColumn(25);
		TableColumn colNPlaca = tbReporte.getColumn(26);
		
		colWLRSEQ.setPreferredWidth(100);
		colWLRCOD.setPreferredWidth(100);
		colWLRFDO.setPreferredWidth(100);
		colWLRFVE.setPreferredWidth(100);
		colWLRTDO.setPreferredWidth(100);
		colWLRCAD.setPreferredWidth(150);
		colWLRDUA.setPreferredWidth(100);
		colWLRCFI.setPreferredWidth(150);
		colWLRIOD.setPreferredWidth(100);
		colWLRTDI.setPreferredWidth(100);
		colWLREPE.setPreferredWidth(100);
		colERRORES.setPreferredWidth(100);
		
		colVale.setPreferredWidth(100);
		colCorrelativo.setPreferredWidth(100);
		colAlmacen.setPreferredWidth(100);
		colMovimiento.setPreferredWidth(100);
		colTipoMovimiento.setPreferredWidth(100);
		colFecha.setPreferredWidth(100);
		colArticulo.setPreferredWidth(150);
		colCantidad.setPreferredWidth(100);
		colCodCliente.setPreferredWidth(150);
		colTDocCliente.setPreferredWidth(100);
		colNDocCliente.setPreferredWidth(100);
		colNDocumento.setPreferredWidth(100);
		colNDestino.setPreferredWidth(100);
		colNLicencia.setPreferredWidth(100);
		colNPlaca.setPreferredWidth(100);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void cargarTabla(RegistroOperacionDiaria reporte){
		try {
		Object datos[] = { 	
				reporte.getItem(), reporte.getColumna1(), reporte.getColumna2(), 
				reporte.getColumna3(), reporte.getColumna4(), reporte.getColumna5(), 
				reporte.getColumna6(), reporte.getColumna7(), reporte.getColumna8(),
				reporte.getColumna9(), reporte.getColumna10(), reporte.getErrores(),
				reporte.getMHCOMP(),reporte.getMDCORR(),reporte.getMDALMA(),reporte.getMDCMOV(),reporte.getMDTMOV(),
				reporte.getMDFECH(),reporte.getMDCOAR(),reporte.getMDCANR(),reporte.getMHREF1(),
				reporte.getCLTIDE(),reporte.getCLNIDE(),reporte.getMHREF2(),reporte.getMHHRE1(),
				reporte.getLicencia(),reporte.getPlaca()}; 
		dtm.addRow(datos);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void limpiarTablaErrores() {
		try {
		tbErrores.setModel(dtmErrores = new DefaultTableModel(datosErrores, cabeceraErrores));
		TableColumn colFila = tbErrores.getColumn(0);
		TableColumn colColumna = tbErrores.getColumn(1);
		TableColumn colDescripcion = tbErrores.getColumn(2);
		
		colFila.setPreferredWidth(100);
		colColumna.setPreferredWidth(100);
		colDescripcion.setPreferredWidth(400);
	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
	
	public void cargarTablaErrores(List<RegistroOperacionDiaria> reportes,int totalFilas){
		try {
			limpiarTablaErrores();
			lblErrores.setVisible(true);
			lblCantidadDeErrores.setVisible(true);
			lblCantidadDeFilas.setVisible(true);
			lblCantErr.setVisible(true);
			lblCantFil.setVisible(true);
			scpErrores.setVisible(true);
			tbErrores.setVisible(true);
			lblCantFil.setText(totalFilas+"");
			lblCantErr.setText(reportes.size()+"");

			for (RegistroOperacionDiaria reporte : reportes) {
				Object datos[] = { 	
						reporte.getFila(), reporte.getColumna(), reporte.getDescripcion()}; 
				dtmErrores.addRow(datos);
				} 
			}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public JXTable getTabla(){
		return tbReporte;
	}
	
	public JButton getCancelar(){
		return btnCancelar;
	}
	
	public void setControlador(ContabilidadController controlador) {
		btnProcesar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnExportarTxt.addActionListener(controlador);
		btnCerrarPeriodo.addActionListener(controlador);
		txtEjercicio.addActionListener(controlador);
		txtPeriodo.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
	}
		
	private void seleccionaFila(){
		 int fila = Integer.parseInt(tbErrores.getValueAt(
				 tbErrores.getSelectedRow(), 0).toString());
		 int columna = Integer.parseInt(tbErrores.getValueAt(
				 tbErrores.getSelectedRow(), 1).toString());     
		 tbReporte.changeSelection(fila-1, columna, false, false);
	}
	
	public static void close(){
		gui = null;
	}
	
	public void salir(){
		dispose();
	}
}
