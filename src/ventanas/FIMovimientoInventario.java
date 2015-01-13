package ventanas;

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
import bean.TMOVD;
import controlador.InventarioController;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import javax.swing.JComboBox;

public class FIMovimientoInventario extends MaestroInternalFrame{

	private static final long serialVersionUID = 1L;
	private static FIMovimientoInventario gui = null;
	private JTextField txtMotor;
	private JLabel lblSituacion;
	private JButton btnProcesar;
	private String[][] datosDocumentos;
	private String[] cabeceraDocuemntos={"Almacen","Fecha Mov.","Artículo","Clase","Lote","Nro. Vale Almacen","Tipo Mov.","Referencia 1","Referencia 2","Refrencia 3","Motor"};
	private DefaultTableModel dtmDocumentos;
	private JXTable tbDocumentos;
	private JScrollPane scpDocumentos;
	private JComboBox cboSituacion;
	private JLabel lblMotor;
	private JLabel lblMotocicleta;
	
	public static FIMovimientoInventario createInstance() {
		if (gui == null) {
			gui = new FIMovimientoInventario();
		}
		return gui;
	}

	public static FIMovimientoInventario getInstance() {
		return gui;
	}
	
	public FIMovimientoInventario() {
		//initialize();
	}
	
	public void initialize() {
		setSize(978, 482);
		setTitle(getTitle()+ " - " + Sesion.tifMovimientoInventario);
		toolBar.setVisible(true);
		btnSalir.setVisible(false);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(498, 34, 86, 23);
		contenedorCenter.add(btnProcesar);

		txtMotor = new JTextField();
		txtMotor.setBounds(307, 35, 170, 20);
		contenedorCenter.add(txtMotor);

		lblSituacion = new JLabel("Situación:");
		lblSituacion.setBounds(10, 38, 64, 14);
		contenedorCenter.add(lblSituacion);
		
		dtmDocumentos = new DefaultTableModel(datosDocumentos,cabeceraDocuemntos);
		tbDocumentos = new JXTable(dtmDocumentos);
		tbDocumentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDocumentos.setEditable(true);
		tbDocumentos.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		scpDocumentos = new JScrollPane(tbDocumentos);
		scpDocumentos.setBounds(10, 68, 940, 342);
		contenedorCenter.add(scpDocumentos);
		
		cboSituacion = new JComboBox();
		cboSituacion.addItem("REGISTRADO");
		cboSituacion.addItem("PRODUCCION");
		cboSituacion.addItem("PRODUCIDO");
		cboSituacion.addItem("ANULADO");
		cboSituacion.setBounds(76, 35, 177, 20);
		contenedorCenter.add(cboSituacion);
		
		lblMotor = new JLabel("Motor:");
		lblMotor.setBounds(263, 38, 46, 14);
		contenedorCenter.add(lblMotor);
		
		lblMotocicleta = new JLabel("Datos de Motocicleta:");
		lblMotocicleta.setBounds(10, 13, 177, 14);
		contenedorCenter.add(lblMotocicleta);
		
		limpiarTabla();
	}
	
	public void limpiarTabla(){
		tbDocumentos.setModel(dtmDocumentos = new DefaultTableModel(datosDocumentos,
				cabeceraDocuemntos));

		TableColumn tcAlmacen = tbDocumentos.getColumn(0);
		TableColumn tcFecha = tbDocumentos.getColumn(1);
		TableColumn tcArti = tbDocumentos.getColumn(2);
		TableColumn tcClase = tbDocumentos.getColumn(3);
		TableColumn tcLote = tbDocumentos.getColumn(4);
		TableColumn tcVale = tbDocumentos.getColumn(5);
		TableColumn tcTMov = tbDocumentos.getColumn(6);
		TableColumn tcRef1 = tbDocumentos.getColumn(7);
		TableColumn tcRef2 = tbDocumentos.getColumn(8);
		TableColumn tcRef3 = tbDocumentos.getColumn(9);
		TableColumn tcMotor = tbDocumentos.getColumn(10);
		
		tcAlmacen.setPreferredWidth(60);
		tcFecha.setPreferredWidth(80);
		tcArti.setPreferredWidth(100);
		tcClase.setPreferredWidth(40);
		tcLote.setPreferredWidth(100);
		tcVale.setPreferredWidth(115);
		tcTMov.setPreferredWidth(80);
		tcRef1.setPreferredWidth(100);
		tcRef2.setPreferredWidth(100);
		tcRef3.setPreferredWidth(100);
		tcMotor.setPreferredWidth(130);
		
	}
	
	public void cargaTabla(List<TMOVD> movimientos){
		limpiarTabla();
		for (TMOVD mov : movimientos) {
			String mdfech = mov.getMdfech() +"";
			mdfech = mdfech.substring(6, mdfech.length())+"/"+mdfech.substring(4, 6)+"/"+mdfech.substring(0, 4);	
			Object datos[] = {mov.getMdalma(),mdfech,mov.getMdcoar(),mov.getMdcmov(),mov.getMdlote(),mov.getMdcomp(),mov.getMdtmov(),
					mov.getMhref1(),mov.getMhref2(),mov.getMhref3(),mov.getPimmot()};
			dtmDocumentos.addRow(datos);
		}
	}
	
	public String getMotor(){
		return txtMotor.getText().toUpperCase().trim();
	}
	
	public String getSituacion(){
		return cboSituacion.getSelectedItem().toString();
	}
	
	public JTextField getTxtMotor(){
		return txtMotor;
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
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
	
	public void setControlador(InventarioController controlador){
		btnSalir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
		txtMotor.addActionListener(controlador);
	}
}
