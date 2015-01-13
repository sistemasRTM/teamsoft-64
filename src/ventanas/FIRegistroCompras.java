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
import bean.RegistroCompras;
import controlador.ComprasController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FIRegistroCompras extends MaestroInternalFrame {
	private static final long serialVersionUID = 1L;
	private static FIRegistroCompras gui=null;
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
			"WLRSEQ","WLREPE_1","WLRCOD_2","WLRFDO_3","WLRFVE_4","WLRTDO_5",
			"WLRCAD_6","WLRDUA_7","WLRCFI_8","WLRIOD_9","WLRTDI_10","WLRNRI_11",
			"WLRNCL_12","WLRBI1_13","WLRIG1_14","WLRBI2_15","WLRIG2_16","WLRBI3_17",
			"WLRIG3_18","WLRVAG_19","WLRISC_20","WLROTC_21","WLRITO_22","WLRTCA_23",
			"WLRFER_24","WLRTDR_25","WLRSRR_26","WLRNRR_27","WLRNDN_28","WLRDFE_29",
			"WLRDNR_30","WLRMPR_31","WLREST_32","ERRORES"};
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
	
	public static FIRegistroCompras createInstance() {
		if (gui == null) {
			gui = new FIRegistroCompras();
		}
		return gui;
	}

	public static FIRegistroCompras getInstance() {
		return gui;
	}
	
	public void initialize() {
		setSize(1198, 560);
		setTitle(Sesion.titulo+"-"+Sesion.tifRegistroCompras);
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
		btnProcesar.setBounds(180, 11, 89, 23);
		contenedorCenter.add(btnProcesar);

		dtm = new DefaultTableModel(datos, cabecera);

		tbReporte = new JXTable(dtm);
		tbReporte.getTableHeader().setReorderingAllowed(false);
		tbReporte.getTableHeader().setResizingAllowed(false);
		tbReporte.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbReporte.setEditable(false);
		tbReporte.setSortable(false);
		
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
		
		tbErrores.getTableHeader().setReorderingAllowed(false);
		tbErrores.getTableHeader().setResizingAllowed(false);
		tbErrores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbErrores.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		tbErrores.setEditable(false);
	
		
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
		
		//highlighters = new HighlighterPipeline();
	//	highlighters.addHighlighter(new AlternateRowHighlighter());
	//	tbReporte.setHighlighters(highlighters);
	//	tbReporte.repaint();
		//PatternHighlighter pattern = new PatternHighlighter(Color.RED, 
				//	        Color.WHITE, "[.*1.*.*2.*.*3.*.*4.*.*5.*.*6.*.*7.*.*8.*.*9.*]", 0, 33);
				//highlighters.addHighlighter(pattern);
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
		TableColumn colWLRNRI = tbReporte.getColumn(11);
		TableColumn colWLRNCL = tbReporte.getColumn(12);
		TableColumn colWLRBI1 = tbReporte.getColumn(13);
		TableColumn colWLRIG1 = tbReporte.getColumn(14);
		TableColumn colWLRBI2 = tbReporte.getColumn(15);
		TableColumn colWLRIG2 = tbReporte.getColumn(16);
		TableColumn colWLRBI3 = tbReporte.getColumn(17);
		TableColumn colWLRIG3 = tbReporte.getColumn(18);
		TableColumn colWLRVAG = tbReporte.getColumn(19);
		TableColumn colWLRISC = tbReporte.getColumn(20);
		TableColumn colWLROTC = tbReporte.getColumn(21);
		TableColumn colWLRITO = tbReporte.getColumn(22);
		TableColumn colWLRTCA = tbReporte.getColumn(23);
		TableColumn colWLRFER = tbReporte.getColumn(24);	
		TableColumn colWLRTDR = tbReporte.getColumn(25);
		TableColumn colWLRSRR = tbReporte.getColumn(26);
		TableColumn colWLRNRR = tbReporte.getColumn(27);
		TableColumn colWLRNDN = tbReporte.getColumn(28);
		TableColumn colWLRDFE = tbReporte.getColumn(29);
		TableColumn colWLRDNR = tbReporte.getColumn(30);
		TableColumn colWLRMPR = tbReporte.getColumn(31);
		TableColumn colWLREST = tbReporte.getColumn(32);
		TableColumn colERRORES = tbReporte.getColumn(33);
		
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
		colWLRNRI.setPreferredWidth(100);
		colWLRNCL.setPreferredWidth(350);
		colWLRBI1.setPreferredWidth(100);
		colWLRIG1.setPreferredWidth(100);
		colWLRBI2.setPreferredWidth(100);
		colWLRIG2.setPreferredWidth(100);
		colWLRBI3.setPreferredWidth(100);
		colWLRIG3.setPreferredWidth(100);
		colWLRVAG.setPreferredWidth(100);
		colWLRISC.setPreferredWidth(100);
		colWLROTC.setPreferredWidth(100);
		colWLRITO.setPreferredWidth(100);
		colWLRDNR.setPreferredWidth(100);
		colWLRDFE.setPreferredWidth(100);
		colWLRTCA.setPreferredWidth(100);
		colWLRFER.setPreferredWidth(100);
		colWLRTDR.setPreferredWidth(100);
		colWLRSRR.setPreferredWidth(100);
		colWLRNRR.setPreferredWidth(100);
		colWLREPE.setPreferredWidth(100);
		colWLRNDN.setPreferredWidth(100);
		colWLRMPR.setPreferredWidth(100);
		colWLREST.setPreferredWidth(100);
		colERRORES.setPreferredWidth(100);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void cargarTabla(RegistroCompras reporte){
		try {
		Object datos[] = { 	
				reporte.getWLRSEQ_0(), reporte.getWLREPE_1(), reporte.getWLRCOD_2(), 
				reporte.getWLRFDO_3(), reporte.getWLRFVE_4(), reporte.getWLRTDO_5(), 
				reporte.getWLRCAD_6(), reporte.getWLRDUA_7(), reporte.getWLRCFI_8(),
				reporte.getWLRIOD_9(), reporte.getWLRTDI_10(), reporte.getWLRNRI_11(), 
				reporte.getWLRNCL_12(), reporte.getWLRBI1_13(), reporte.getWLRIG1_14(), 
				reporte.getWLRBI2_15(), reporte.getWLRIG2_16(), reporte.getWLRBI3_17(), 
				reporte.getWLRIG3_18(), reporte.getWLRVAG_19(), reporte.getWLRISC_20(), 
				reporte.getWLROTC_21(), reporte.getWLRITO_22(), reporte.getWLRTCA_23(), 
				reporte.getWLRFER_24(), reporte.getWLRTDR_25(), reporte.getWLRSRR_26(), 
				reporte.getWLRNRR_27(), reporte.getWLRNDN_28(), reporte.getWLRDFE_29(), 
				reporte.getWLRDNR_30(), reporte.getWLRMPR_31(), reporte.getWLREST_32(),
				reporte.getError_0()}; 
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
	
	public void cargarTablaErrores(List<RegistroCompras> reportes,int totalFilas){
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

			for (RegistroCompras reporte : reportes) {
				Object datos[] = { 	
						reporte.getFila(), reporte.getColumna(), reporte.getDescripcion()}; 
				dtmErrores.addRow(datos);
				} 
			}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setControlador(ComprasController controlador) {
		btnProcesar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnExportarTxt.addActionListener(controlador);
		txtEjercicio.addActionListener(controlador);
		txtPeriodo.addActionListener(controlador);
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
