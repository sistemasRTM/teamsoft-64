package ventanas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.jdesktop.swingx.JXTable;
import bean.TTIPTRAN;
import controlador.ContabilidadController;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class FIMantenimientoTTIPTRAN extends MaestroInternalFrame{

	private static final long serialVersionUID = 1L;
	private JPanel pnBusqueda;
	private JPanel pnDatos;
	private JLabel lblCodigo;
	private JLabel lblOperacion;
	private JTextField txtDCodigo;
	private JTextField txtDTipoMovimiento;
	private JLabel lblTipoMovimiento;
	private String[] cabecera={"id","C\u00F3digo","Operacion","Transaccion","Tipo Mov.","Sit."};
	private String[][] datos;
	private DefaultTableModel dtm;
	private JXTable tbBusqueda;
	private JScrollPane scpBusqueda;
	private JTextField txtBCodigo;
	private JLabel lblCodigo_1;
	private JLabel lblBSituacion;
	private JComboBox cboBSituacion;
	private JTextField txtDTransaccion;
	private JLabel lblSituacion;
	private JCheckBox chkDActivo;
	private JLabel lblTransaccion;
	private MaestroComboBox cboDOperacion;
	private int operacion;
	private int id;
	public FIMantenimientoTTIPTRAN() {
		setSize(443, 410);
		
		toolBar.setVisible(true);
	
		setTitle(getTitle() + "-" + Sesion.tifTipoTransaccion);
		pnDatos = new JPanel();
		pnDatos.setBorder(new TitledBorder(null, "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDatos.setBounds(10, 11, 411, 72);
		contenedorCenter.add(pnDatos);
		pnDatos.setLayout(null);
		
		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(10, 18, 49, 14);
		pnDatos.add(lblCodigo);
		
		lblOperacion = new JLabel("Operaci\u00F3n");
		lblOperacion.setBounds(61, 18, 75, 14);
		pnDatos.add(lblOperacion);
		
		lblTipoMovimiento = new JLabel("Tipo Mov.");
		lblTipoMovimiento.setBounds(269, 18, 68, 14);
		pnDatos.add(lblTipoMovimiento);
		
		txtDCodigo = new JTextField();
		txtDCodigo.setBounds(10, 35, 41, 20);
		pnDatos.add(txtDCodigo);
		
		txtDTipoMovimiento = new JTextField();
		txtDTipoMovimiento.setBounds(269, 35, 68, 20);
		pnDatos.add(txtDTipoMovimiento);
		
		lblSituacion = new JLabel("Situaci\u00F3n");
		lblSituacion.setBounds(345, 18, 56, 14);
		pnDatos.add(lblSituacion);
		
		chkDActivo = new JCheckBox("");
		chkDActivo.setBounds(355, 34, 28, 23);
		pnDatos.add(chkDActivo);
		
		cboDOperacion = new MaestroComboBox(Sesion.bdProd+"TREGOP","movimiento","tipo","situacion='01'",false);
		cboDOperacion.setBounds(61, 35, 68, 20);
		pnDatos.add(cboDOperacion);
		
		lblTransaccion = new JLabel("Transaccion");
		lblTransaccion.setBounds(138, 18, 75, 14);
		pnDatos.add(lblTransaccion);
		
		txtDTransaccion = new JTextField();
		txtDTransaccion.setBounds(139, 35, 120, 20);
		pnDatos.add(txtDTransaccion);
		txtDTransaccion.setColumns(10);
		
		pnBusqueda = new JPanel();
		pnBusqueda.setBorder(new TitledBorder(null, "Busqueda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBusqueda.setBounds(10, 107, 411, 233);
		contenedorCenter.add(pnBusqueda);
		pnBusqueda.setLayout(null);
		
		dtm = new DefaultTableModel(datos,cabecera);
		tbBusqueda = new JXTable(dtm);
		tbBusqueda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbBusqueda.getTableHeader().setReorderingAllowed(false);
		tbBusqueda.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbBusqueda.setEnabled(true);
		tbBusqueda.setEditable(false);
		tbBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pintar();
			}
		});
		tbBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				pintar();
			}
		});
		scpBusqueda = new JScrollPane(tbBusqueda);
		scpBusqueda.setBounds(10, 46, 391, 174);
		pnBusqueda.add(scpBusqueda);
		
		txtBCodigo = new JTextField();
		txtBCodigo.setBounds(58, 15, 212, 20);
		pnBusqueda.add(txtBCodigo);
		txtBCodigo.setColumns(10);
		
		lblCodigo_1 = new JLabel("C\u00F3digo");
		lblCodigo_1.setBounds(10, 18, 46, 14);
		pnBusqueda.add(lblCodigo_1);
		
		lblBSituacion = new JLabel("Situaci\u00F3n");
		lblBSituacion.setBounds(280, 18, 65, 14);
		pnBusqueda.add(lblBSituacion);
		
		cboBSituacion = new JComboBox();
		cboBSituacion.addItem("Todos");
		cboBSituacion.addItem("01");
		cboBSituacion.addItem("99");
		cboBSituacion.setBounds(341, 15, 60, 20);
		pnBusqueda.add(cboBSituacion);
		activaControles();
		limpiarTabla();
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}
	
	public JButton getBtnEliminar() {
		return btnEliminar;
	}
	
	public JButton getBtnGuardar() {
		return btnGuardar;
	}
	
	public String getCodigo(){
		return txtDCodigo.getText().trim();
	}
	
	public MaestroBean getDOperacion(){
		return (MaestroBean)cboDOperacion.getSelectedItem();
	}
	
	public String getTransaccion(){
		return txtDTransaccion.getText().trim();
	}
	
	public String getTipoMovimiento(){
		return txtDTipoMovimiento.getText().trim();
	}
	
	public boolean getDActivo(){
		return chkDActivo.isSelected();
	}
	
	public String getBCodigo(){
		return txtBCodigo.getText().trim();
	}
	
	public String getBActivo(){
		return cboBSituacion.getSelectedItem().toString();
	}
	
	public JTextField getTxtBCodigo(){
		return txtBCodigo;
	}
	
	public JXTable getTabla(){
		return tbBusqueda;
	}
	
	public int getOperacion(){
		return operacion;
	}
	
	public void setOperacion(int operacion){
		this.operacion=operacion;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void activaControles() {
		btnNuevo.setEnabled(true);
		btnModificar.setEnabled(true);
		btnEliminar.setEnabled(true);
		
		scpBusqueda.setEnabled(true);
		txtBCodigo.setEnabled(true);
		cboBSituacion.setEnabled(true);
		
		txtDCodigo.setEnabled(false);
		cboDOperacion.setEnabled(false);
		txtDTransaccion.setEnabled(false);
		txtDTipoMovimiento.setEnabled(false);
		chkDActivo.setEnabled(false);
		
		btnCancelar.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		txtBCodigo.requestFocus();
	}
	
	public void desactivaControles() {
		btnNuevo.setEnabled(false);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);

		scpBusqueda.setEnabled(false);
		txtBCodigo.setEnabled(false);
		cboBSituacion.setEnabled(false);
		limpiarTabla();

		txtDCodigo.setEnabled(true);
		cboDOperacion.setEnabled(true);
		txtDTransaccion.setEnabled(true);
		txtDTipoMovimiento.setEnabled(true);
		chkDActivo.setEnabled(true);
		
		btnCancelar.setEnabled(true);
		btnGuardar.setEnabled(true);
	}
	
	public void limpiarDatos() {
		txtDCodigo.setText("");
		txtDTransaccion.setText("");
		txtDTipoMovimiento.setText("");
	}
	
	public void limpiarBuscar() {
		//txtBCodigo.setText("");
	}
	
	public void limpiarTabla() {
		tbBusqueda.setModel(dtm = new DefaultTableModel(datos, cabecera));
		ocultarColumna();
		TableColumn colCodigo = tbBusqueda.getColumn(1);
		TableColumn colOperacion = tbBusqueda.getColumn(2);
		TableColumn colTransaccion = tbBusqueda.getColumn(3);
		TableColumn colTipoMovimiento = tbBusqueda.getColumn(4);
		TableColumn colSit = tbBusqueda.getColumn(5);
		
		colCodigo.setPreferredWidth(50);
		colOperacion.setPreferredWidth(70);
		colTransaccion.setPreferredWidth(145);
		colTipoMovimiento.setPreferredWidth(70);
		colSit.setPreferredWidth(45);
	}
	
	private void ocultarColumna(){
		tbBusqueda.getColumnModel().getColumn(0).setMaxWidth(0);
		tbBusqueda.getColumnModel().getColumn(0).setMinWidth(0);
		tbBusqueda.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tbBusqueda.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
	}
	
	public void cargarTabla(List<TTIPTRAN> listado) {
		limpiarTabla();
		for (TTIPTRAN  obj : listado) {
			Object datos[] = {obj.getId(), obj.getCodigo(),obj.getOperacion(),obj.getTransaccion(),obj.getTipomovimiento(),obj.getSituacion()};
			dtm.addRow(datos);
		}
	}
	
	public void cargarTabla(TTIPTRAN  obj) {
		limpiarTabla();
		Object datos[] = {obj.getId(), obj.getCodigo(),obj.getOperacion(),obj.getTransaccion(),obj.getTipomovimiento(),obj.getSituacion()};
		dtm.addRow(datos);
	}
	
	private void pintar(){
		if (tbBusqueda.getSelectedRow() > -1) {
			int id = Integer.parseInt(tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 0).toString());
			String codigo = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 1).toString();
			String operacion = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 2).toString();
			String transaccion = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 3).toString();
			String tipomovimiento = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 4).toString();
			String activo = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 5).toString().trim();
			
			setId(id);
			txtDCodigo.setText(codigo);
			cboDOperacion.setSelectedItem(operacion);
			txtDTransaccion.setText(transaccion);
			txtDTipoMovimiento.setText(tipomovimiento);
			if(activo.equals("01")){
				chkDActivo.setSelected(true);
			}else{
				chkDActivo.setSelected(false);
			}
		}
	}
	
	public void setControlador(ContabilidadController controlador){
		btnNuevo.addActionListener(controlador);
		btnModificar.addActionListener(controlador);
		btnEliminar.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		txtBCodigo.addKeyListener(controlador);
	}
}
