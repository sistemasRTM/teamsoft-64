package ventanas;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import bean.TCTXC;
import controlador.CuentasXCobrarController;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;

public class FILetras extends MaestroInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private static FILetras gui = null;
	private JTextField txtRuc;
	private JLabel lblRuc;
	private JButton btnProcesar;
	private String[][] datosDocumentos;
	private String[] cabeceraDocuemntos={"Número","Fecha Giro","Fecha Venc.","Precio Venta","Pago Acumulado","Saldo","CLINOM","CLIDIR","CLIDIS","CLIPRO","CLIDPT","CLNIDE","CLITE1","CLNANO","CLNADI","CLNATE","CLNARU","CCMONE"};
	private DefaultTableModel dtmDocumentos;
	private JXTable tbDocumentos;
	private JScrollPane scpDocumentos;
	private JLabel lblCliente;
	private MaestroComboBox cboCliente;
	
	public static FILetras createInstance() {
		if (gui == null) {
			gui = new FILetras();
		}
		return gui;
	}

	public static FILetras getInstance() {
		return gui;
	}
	
	public FILetras() {
		//initialize();
	}
	
	public void initialize() {
		setSize(661, 428);
		setTitle(getTitle()+ " - " + Sesion.tifLetras);
		toolBar.setVisible(true);
		btnProcesar = new JButton("Procesar");
		btnProcesar.setVisible(false);
		btnProcesar.setBounds(547, 6, 86, 23);
		contenedorCenter.add(btnProcesar);

		txtRuc = new JTextField();
		txtRuc.setBounds(57, 7, 120, 20);
		contenedorCenter.add(txtRuc);

		lblRuc = new JLabel("Codigo:");
		lblRuc.setBounds(10, 10, 53, 14);
		contenedorCenter.add(lblRuc);
		
		dtmDocumentos = new DefaultTableModel(datosDocumentos,cabeceraDocuemntos);
		tbDocumentos = new JXTable(dtmDocumentos);
		tbDocumentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDocumentos.setEditable(true);
		tbDocumentos.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		scpDocumentos = new JScrollPane(tbDocumentos);
		scpDocumentos.setBounds(10, 40, 623, 329);
		contenedorCenter.add(scpDocumentos);
			
		lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(180, 10, 53, 14);
		contenedorCenter.add(lblCliente);
		
		cboCliente = new MaestroComboBox();
		cboCliente.setBounds(223, 7, 314, 20);
		cboCliente.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					MaestroBean cliente = (MaestroBean) cboCliente
							.getSelectedItem();
					if(cliente!=null){
						txtRuc.setText(cliente.getCodigo());
					}
				}
			}
		});
		contenedorCenter.add(cboCliente);
		
		Thread hilo = new Thread(){
			@Override
			public void run() {
				cboCliente.executeQuery(" tclie "," clicve "," clinom "," clisit='01' and clinom<>'' order by clinom asc ");
			}	
		};
		hilo.start();
		
		
		limpiarTabla();
	}
	
	public void limpiarTabla(){
		tbDocumentos.setModel(dtmDocumentos = new DefaultTableModel(datosDocumentos,
				cabeceraDocuemntos));
		ocultarColumnas();
		TableColumn tcNumero = tbDocumentos.getColumn(0);
		TableColumn tcFGiro = tbDocumentos.getColumn(1);
		TableColumn tcFVenc = tbDocumentos.getColumn(2);
		TableColumn tcVVenta = tbDocumentos.getColumn(3);
		TableColumn tcPAcum = tbDocumentos.getColumn(4);
		TableColumn tcSaldo = tbDocumentos.getColumn(5);
		
		tcNumero.setPreferredWidth(80);
		tcFGiro.setPreferredWidth(90);
		tcFVenc.setPreferredWidth(90);
		tcVVenta.setPreferredWidth(120);
		tcPAcum.setPreferredWidth(120);
		tcSaldo.setPreferredWidth(120);
		
	}
	
	private void ocultarColumnas(){
		
		tbDocumentos.getColumnModel().getColumn(6).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(6).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(7).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(7).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(8).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(8).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(9).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(9).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(9).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(10).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(10).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(10).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(11).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(11).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(12).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(12).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(12).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(13).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(13).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(14).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(14).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(14).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(14).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(15).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(15).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(15).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(15).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(16).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(16).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(17).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(17).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);
	}
	
	public void cargaTabla(List<TCTXC> letras){
		limpiarTabla();
		for (TCTXC tctxc : letras) {
			String ccfech = tctxc.getCCFECH() +"";
			ccfech = ccfech.substring(6, ccfech.length())+"/"+ccfech.substring(4, 6)+"/"+ccfech.substring(0, 4);
			String ccfeve = tctxc.getCCFEVE() + "";
			ccfeve = ccfeve.substring(6, ccfeve.length())+"/"+ccfeve.substring(4, 6)+"/"+ccfeve.substring(0, 4);		
			Object datos[] = {tctxc.getCCNDOC(),ccfech,ccfeve,Sesion.formateaDecimal_2(tctxc.getCCPVTA()),Sesion.formateaDecimal_2(tctxc.getCCPACU()),Sesion.formateaDecimal_2(tctxc.getCCSALD()),
					tctxc.getCLINOM(),tctxc.getCLIDIR(),tctxc.getCLIDIS(),tctxc.getCLIPRO(),tctxc.getCLIDPT(),tctxc.getCLNIDE(),tctxc.getCLITE1(),tctxc.getCLNANO(),tctxc.getCLNADI(),tctxc.getCLNATE(),tctxc.getCLNARU(),tctxc.getCCMONE()};
			dtmDocumentos.addRow(datos);
		}
	}
	
	public String getRUC(){
		return txtRuc.getText().trim();
	}
	
	public JTextField getTxtRUC(){
		return txtRuc;
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
	
	public JButton getBtnImprimir(){
		return btnImprimir;
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
	
	public void setControlador(CuentasXCobrarController controlador){
		btnSalir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
		btnImprimir.addActionListener(controlador);
		txtRuc.addActionListener(controlador);
	}
	
}
