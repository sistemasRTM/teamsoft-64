package ventanas;

import java.util.Date;
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
import bean.TPEDH;
import controlador.FacturacionController;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;
import javax.swing.JComboBox;
import org.jdesktop.swingx.JXDatePicker;

public class FIImprimirPedidos extends MaestroInternalFrame{
	private static final long serialVersionUID = 1L;
	private static FIImprimirPedidos gui = null;
	private JTextField txtNumero;
	private JLabel lblNumero;
	private JButton btnProcesar;
	private TextFilterDocument tfdFactura = new TextFilterDocument(
			TextFilterDocument.DIGITS, 12);
	private String[][] datosDocumentos;
	private String[] cabeceraDocuemntos={"Seleccione","Serie","Número","Fech Pedido","Vendedor","RUC - DNI","Cliente","Neto a Pagar"};
	private DefaultTableModel dtmDocumentos;
	private JXTable tbDocumentos;
	private JScrollPane scpDocumentos;
	private JLabel lblSerie;
	private JComboBox cboSerie;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JXDatePicker dtpDesde;
	private JXDatePicker dtpHasta;
	
	public static FIImprimirPedidos createInstance() {
		if (gui == null) {
			gui = new FIImprimirPedidos();
		}
		return gui;
	}

	public static FIImprimirPedidos getInstance() {
		return gui;
	}
	
	public FIImprimirPedidos() {
		//initialize();
	}
	
	@SuppressWarnings("serial")
	public void initialize() {
		setSize(912, 594);
		setTitle(getTitle() + " - " +  Sesion.tifPedidosFacturados);
		toolBar.setVisible(true);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(639, 6, 86, 23);
		contenedorCenter.add(btnProcesar);

		txtNumero = new JTextField();
		txtNumero.setBounds(178, 7, 120, 20);
		txtNumero.setDocument(tfdFactura);
		contenedorCenter.add(txtNumero);

		lblNumero = new JLabel("Pedido:");
		lblNumero.setBounds(134, 10, 64, 14);
		contenedorCenter.add(lblNumero);
		
		dtmDocumentos = new DefaultTableModel(datosDocumentos,cabeceraDocuemntos);
		
		tbDocumentos = new JXTable(dtmDocumentos){
			 @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
	            public Class getColumnClass(int column) {
	                switch (column) {
	                    case 0:
	                        return Boolean.class;
	                    case 1:
	                        return Object.class;
	                    case 2:
	                        return Object.class;
	                    case 3:
	                        return Object.class;
	                    case 4:
	                        return Object.class;
	                    case 5:
	                        return Object.class;
	                    case 6:
	                        return Object.class;
	                    case 7:
	                        return Object.class;
	                    default:
	                        return Object.class;
	                }
	            }			 	
		};
		tbDocumentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDocumentos.setEditable(true);
		tbDocumentos.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		scpDocumentos = new JScrollPane(tbDocumentos);
		scpDocumentos.setBounds(10, 40, 874, 481);
		contenedorCenter.add(scpDocumentos);
		
		lblSerie = new JLabel("Serie:");
		lblSerie.setBounds(10, 10, 46, 14);
		contenedorCenter.add(lblSerie);
		
		cboSerie = new JComboBox();
		cboSerie.setBounds(66, 7, 58, 20);
		cboSerie.addItem("1");
		cboSerie.addItem("5");
		cboSerie.addItem("8");
		cboSerie.addItem("9");
		cboSerie.addItem("10");
		cboSerie.addItem("11");
		cboSerie.addItem("12");
		cboSerie.addItem("13");
		cboSerie.addItem("14");
		cboSerie.addItem("15");
		cboSerie.addItem("16");
		cboSerie.addItem("17");
		contenedorCenter.add(cboSerie);
		
		dtpDesde = new JXDatePicker();
		dtpDesde.setBounds(356, 6, 101, 22);
		Sesion.formateaDatePicker(dtpDesde);
		contenedorCenter.add(dtpDesde);
		
		dtpHasta = new JXDatePicker();
		dtpHasta.setBounds(528, 6, 101, 22);
		Sesion.formateaDatePicker(dtpHasta);
		contenedorCenter.add(dtpHasta);
		
		lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(308, 10, 58, 14);
		contenedorCenter.add(lblDesde);
		
		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(472, 10, 58, 14);
		contenedorCenter.add(lblHasta);
		
		limpiarTabla();
	}
	
	public void limpiarTabla(){
		tbDocumentos.setModel(dtmDocumentos = new DefaultTableModel(datosDocumentos,
				cabeceraDocuemntos));
		TableColumn tcSel = tbDocumentos.getColumn(0);
		TableColumn tcSerie = tbDocumentos.getColumn(1);
		TableColumn tcNumero = tbDocumentos.getColumn(2);
		TableColumn tcFecp = tbDocumentos.getColumn(3);
		TableColumn tcVend = tbDocumentos.getColumn(4);
		TableColumn tcRuc = tbDocumentos.getColumn(5);
		TableColumn tcNomc = tbDocumentos.getColumn(6);
		TableColumn tcNeto = tbDocumentos.getColumn(7);		
		
		tcSel.setPreferredWidth(80);
		tcSerie.setPreferredWidth(50);
		tcNumero.setPreferredWidth(80);
		tcFecp.setPreferredWidth(80);
		tcVend.setPreferredWidth(75);
		tcRuc.setPreferredWidth(100);
		tcNomc.setPreferredWidth(350);
		tcNeto.setPreferredWidth(120);
	}
	
	public void cargaTabla(List<TPEDH> pedidos){
		limpiarTabla();
		for (TPEDH tpedh : pedidos) {
			String fechp = tpedh.getPHFECP() +"";
			fechp = fechp.substring(6, fechp.length())+"/"+fechp.substring(4, 6)+"/"+fechp.substring(0, 4);
			Object datos[] = {new Boolean(false),tpedh.getPHPVTA(),tpedh.getPHNUME(),fechp,tpedh.getPHUSAP(),tpedh.getCLNIDE(),tpedh.getPHNOMC(),tpedh.getPHNPVT()};
			dtmDocumentos.addRow(datos);
		}
	}
	
	public JButton getBtnSalir() {
		return btnSalir;
	}
	
	public JButton getBtnImprimir() {
		return btnImprimir;
	}
	
	public JButton getBtnProcesar() {
		return btnProcesar;
	}
	
	public JXTable getTable() {
		return tbDocumentos;
	}
	
	public int getSerie(){
		return Integer.parseInt(cboSerie.getSelectedItem().toString());
	}
	
	public String getNumero(){
		return txtNumero.getText().trim();
	}
	
	public String getFechaDesdeText() {
		return dtpDesde.getEditor().getText();
	}

	public String getFechaHastaText() {
		return dtpHasta.getEditor().getText();
	}
	
	public Date getFechaDesde() {
		return dtpDesde.getDate();
	}

	public Date getFechaHasta() {
		return dtpHasta.getDate();
	}
	
	public JXDatePicker getDtpFechaDesde() {
		return dtpDesde;
	}

	public JXDatePicker getDtpFechaHasta() {
		return dtpHasta;
	}
	
	public void setControlador(FacturacionController controlador){
		btnSalir.addActionListener(controlador);
		btnImprimir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
}
