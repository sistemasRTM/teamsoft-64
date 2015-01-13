package ventanas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXTable;
import controlador.FacturacionController;
import bean.TDFCEPE;
//import delegate.GestionSeguridad;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;
//import servicio.ParametrosService;

public class FIVerificarPercepcion extends MaestroInternalFrame {
	private static final long serialVersionUID = 1L;
	private static FIVerificarPercepcion gui = null;
	private JTextField txtFactura;
	private JLabel lblSerie;
	private JLabel lblFactura;
	private JComboBox cboSerie;
	private JButton btnProcesar;
	private TextFilterDocument tfdFactura = new TextFilterDocument(
			TextFilterDocument.DIGITS, 7);
	private JButton btnAgregar;
	private JButton btnQuitar;
	private String[][] datosDocumentos;
	private String[] cabeceraDocuemntos={"Tipo","Serie","Número","Cliente"};
	private DefaultTableModel dtmDocumentos;
	private JXTable tbDocumentos;
	private JScrollPane scpDocumentos;
	//private ParametrosService servicio= GestionSeguridad.getParametrosService();
	
	public static FIVerificarPercepcion createInstance() {
		if (gui == null) {
			gui = new FIVerificarPercepcion();
		}
		return gui;
	}

	public static FIVerificarPercepcion getInstance() {
		return gui;
	}
	
	public FIVerificarPercepcion() {
		//initialize();
	}
	
	public void initialize() {
		setTitle(getTitle() + "-" + Sesion.tifVerPercepcion);
		setSize(359, 315);
		toolBar.setVisible(true);
		btnSalir.setVisible(true);
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(10, 225, 86, 23);
		contenedorCenter.add(btnProcesar);

		txtFactura = new JTextField();
		txtFactura.setBounds(60, 27, 58, 20);
		txtFactura.setDocument(tfdFactura);
		contenedorCenter.add(txtFactura);

		lblFactura = new JLabel("N\u00FAmero:");
		lblFactura.setBounds(60, 10, 64, 14);
		contenedorCenter.add(lblFactura);

		lblSerie = new JLabel("Serie:");
		lblSerie.setBounds(10, 10, 46, 14);
		contenedorCenter.add(lblSerie);

		cboSerie = new JComboBox();
		cboSerie.setBounds(10, 27, 40, 20);
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
		
		dtmDocumentos = new DefaultTableModel(datosDocumentos,cabeceraDocuemntos);
		tbDocumentos = new JXTable(dtmDocumentos);
		tbDocumentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDocumentos.setEditable(false);
		scpDocumentos = new JScrollPane(tbDocumentos);
		scpDocumentos.setBounds(10, 58, 324, 163);
		contenedorCenter.add(scpDocumentos);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(182, 26, 71, 23);
		contenedorCenter.add(btnAgregar);
		
		btnQuitar = new JButton("Quitar");
		btnQuitar.setBounds(263, 26, 71, 23);
		contenedorCenter.add(btnQuitar);

		limpiarTabla();
		/*
		try {
			TPARAM ptrPorcentaje = servicio.listarParametros("PORPER", "001");
			TPARAM ptrMontoLimite = servicio.listarParametros("MONLIM", "001");
			if(ptrPorcentaje==null || ptrMontoLimite==null){
				Sesion.mensajeError(this, "No se cargaron correctamente los parametros, comuniquese con sistemas");
			}else{
				Sesion.porperCampo1 = Double.parseDouble(ptrPorcentaje.getCampo1());
				Sesion.porperCampo2 = Double.parseDouble(ptrPorcentaje.getCampo2());
				Sesion.monlimCampo1 = Double.parseDouble(ptrMontoLimite.getCampo1());
				Sesion.monlimCampo2 = Double.parseDouble(ptrMontoLimite.getCampo2());
				
				System.out.println(Sesion.porperCampo1);
				System.out.println(Sesion.porperCampo2);
				System.out.println(Sesion.monlimCampo1);
				System.out.println(Sesion.monlimCampo2);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		*/
		Sesion.porperCampo1 = 0.02;
		Sesion.porperCampo2 = 0.005;
		Sesion.monlimCampo1 = 0;
		Sesion.monlimCampo2 = 1500;
	}
	
	public void limpiarTabla(){
		txtFactura.setText("");
		tbDocumentos.setModel(dtmDocumentos = new DefaultTableModel(datosDocumentos,
				cabeceraDocuemntos));
		TableColumn tcTipo = tbDocumentos.getColumn(0);
		TableColumn tcSerie = tbDocumentos.getColumn(1);
		TableColumn tcNumero = tbDocumentos.getColumn(2);
		TableColumn tcCliente = tbDocumentos.getColumn(3);	
		
		tcTipo.setPreferredWidth(30);
		tcSerie.setPreferredWidth(40);
		tcNumero.setPreferredWidth(70);
		tcCliente.setPreferredWidth(350);
	}
	
	public void cargaTabla(TDFCEPE certificado){
		Object datos[] = {certificado.getPdtdoc(),certificado.getPdpvta(),certificado.getPdfabo(),certificado.getClinom()};
		dtmDocumentos.addRow(datos);
	}
	
	public String getFactura(){
		return txtFactura.getText().trim();
	}
	
	public JTextField getTxtFactura(){
		return txtFactura;
	}
	
	public int getPhpvta() {
		return Integer.parseInt(cboSerie.getSelectedItem().toString());
	}

	public int getPdfabo() {
		return Integer.parseInt(txtFactura.getText().trim());
	}

	public JXTable getTablaDocumentos(){
		return tbDocumentos;
	}
	
	public DefaultTableModel getDTM(){
		return dtmDocumentos;
	}
	
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public JButton getBtnProcesar(){
		return btnProcesar;
	}
	
	public JButton getBtnAgregar(){
		return btnAgregar;
	}
	
	public JButton getBtnQuitar(){
		return btnQuitar;
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
	
	public void setControlador(FacturacionController controlador){
		btnSalir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
		btnAgregar.addActionListener(controlador);
		btnQuitar.addActionListener(controlador);
		txtFactura.addActionListener(controlador);
	}
	
}
