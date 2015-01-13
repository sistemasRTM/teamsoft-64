package ventanas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
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
import org.jdesktop.swingx.JXTable;
import bean.TARTCON;
import controlador.ContabilidadController;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class FIMantenimientoTARTCON extends MaestroInternalFrame{

	private static final long serialVersionUID = 1L;
	private JPanel pnBusqueda;
	private JPanel pnDatos;
	private JLabel lblCodigo;
	private JLabel lblArticulo;
	private JTextField txtDCodigo;
	private JTextField txtDArticulo;
	private JTextField txtDValor1;
	private JLabel lblValor1;
	private String[] cabecera={"id","C\u00F3digo","Articulo","Valor 1","Valor 2","Valor 3","Tipo","Sit."};
	private String[][] datos;
	private DefaultTableModel dtm;
	private JXTable tbBusqueda;
	private JScrollPane scpBusqueda;
	private JTextField txtBCodigo;
	private JLabel lblCodigo_1;
	private JLabel lblBSituacion;
	private JComboBox cboBSituacion;
	private JLabel lblValor2;
	private JTextField txtDValor2;
	private JTextField txtDValor3;
	private JLabel lblValor3;
	private JTextField txtDTipo;
	private JLabel lblTipo;
	private JLabel lblSituacion;
	private JCheckBox chkDActivo;
	private int operacion;
	private int id;
	public FIMantenimientoTARTCON() {
		setSize(510, 410);
		
		toolBar.setVisible(true);

		setTitle(getTitle() + "-" + Sesion.tifAsociacionPresentacionComercial);
		pnDatos = new JPanel();
		pnDatos.setBorder(new TitledBorder(null, "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDatos.setBounds(10, 11, 472, 72);
		contenedorCenter.add(pnDatos);
		pnDatos.setLayout(null);
		
		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(10, 18, 49, 14);
		pnDatos.add(lblCodigo);
		
		lblArticulo = new JLabel("Articulo");
		lblArticulo.setBounds(61, 18, 80, 14);
		pnDatos.add(lblArticulo);
		
		lblValor1 = new JLabel("Valor 1");
		lblValor1.setBounds(151, 18, 49, 14);
		pnDatos.add(lblValor1);
		
		txtDCodigo = new JTextField();
		txtDCodigo.setEnabled(false);
		txtDCodigo.setBounds(10, 35, 41, 20);
		pnDatos.add(txtDCodigo);
		
		txtDArticulo = new JTextField();
		txtDArticulo.setBounds(61, 35, 80, 20);
		pnDatos.add(txtDArticulo);
		
		txtDValor1 = new JTextField();
		txtDValor1.setBounds(151, 35, 41, 20);
		pnDatos.add(txtDValor1);
		
		lblSituacion = new JLabel("Situaci\u00F3n");
		lblSituacion.setBounds(406, 18, 56, 14);
		pnDatos.add(lblSituacion);
		
		chkDActivo = new JCheckBox("");
		chkDActivo.setBounds(416, 32, 28, 23);
		pnDatos.add(chkDActivo);
		
		lblValor2 = new JLabel("Valor 2");
		lblValor2.setBounds(204, 18, 46, 14);
		pnDatos.add(lblValor2);
		
		txtDValor2 = new JTextField();
		txtDValor2.setBounds(204, 35, 41, 20);
		pnDatos.add(txtDValor2);
		txtDValor2.setColumns(10);
		
		txtDValor3 = new JTextField();
		txtDValor3.setBounds(255, 35, 41, 20);
		pnDatos.add(txtDValor3);
		txtDValor3.setColumns(10);
		
		lblValor3 = new JLabel("Valor 3");
		lblValor3.setBounds(255, 18, 41, 14);
		pnDatos.add(lblValor3);
		
		txtDTipo = new JTextField();
		txtDTipo.setBounds(306, 35, 86, 20);
		pnDatos.add(txtDTipo);
		txtDTipo.setColumns(10);
		
		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(306, 18, 46, 14);
		pnDatos.add(lblTipo);
		
		pnBusqueda = new JPanel();
		pnBusqueda.setBorder(new TitledBorder(null, "Busqueda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBusqueda.setBounds(10, 107, 472, 233);
		contenedorCenter.add(pnBusqueda);
		pnBusqueda.setLayout(null);
		
		dtm = new DefaultTableModel(datos,cabecera);
		tbBusqueda = new JXTable(dtm);
		tbBusqueda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbBusqueda.getTableHeader().setReorderingAllowed(false);
		//tbBusqueda.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		scpBusqueda.setBounds(10, 46, 452, 174);
		pnBusqueda.add(scpBusqueda);
		
		txtBCodigo = new JTextField();
		txtBCodigo.setBounds(58, 15, 268, 20);
		pnBusqueda.add(txtBCodigo);
		txtBCodigo.setColumns(10);
		
		lblCodigo_1 = new JLabel("C\u00F3digo");
		lblCodigo_1.setBounds(10, 18, 46, 14);
		pnBusqueda.add(lblCodigo_1);
		
		lblBSituacion = new JLabel("Situaci\u00F3n");
		lblBSituacion.setBounds(336, 18, 65, 14);
		pnBusqueda.add(lblBSituacion);
		
		cboBSituacion = new JComboBox();
		cboBSituacion.addItem("Todos");
		cboBSituacion.addItem("01");
		cboBSituacion.addItem("99");
		cboBSituacion.setBounds(402, 15, 60, 20);
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
	
	public boolean getDActivo(){
		return chkDActivo.isSelected();
	}
	
	public String getDCodigo(){
		return txtDCodigo.getText().trim();
	}
	
	public String getDArticulo(){
		return txtDArticulo.getText().trim();
	}
	
	public String getDTipo(){
		return txtDTipo.getText().trim();
	}
	
	public String getValor1(){
		return txtDValor1.getText().trim();
	}
	public String getValor2(){
		return txtDValor2.getText().trim();
	}
	public String getValor3(){
		return txtDValor3.getText().trim();
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
	
	public JTextField getTxtValor1(){
		return txtDValor1;
	}
	public JTextField getTxtValor2(){
		return txtDValor2;
	}
	public JTextField getTxtValor3(){
		return txtDValor3;
	}
	
	public void setCodigo(String codigo){
		txtDCodigo.setText(codigo);
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
		
		txtDArticulo.setEnabled(false);
		txtDTipo.setEnabled(false);
		txtDValor1.setEnabled(false);
		txtDValor2.setEnabled(false);
		txtDValor3.setEnabled(false);
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

		txtDValor1.setEnabled(true);
		txtDValor2.setEnabled(true);
		txtDValor3.setEnabled(true);
		txtDArticulo.setEnabled(true);
		txtDTipo.setEnabled(true);
		chkDActivo.setEnabled(true);
		
		btnCancelar.setEnabled(true);
		btnGuardar.setEnabled(true);
	}
	
	public void limpiarDatos() {
		txtDCodigo.setText("");
		txtDValor1.setText("");
		txtDValor2.setText("");
		txtDValor3.setText("");
		txtDArticulo.setText("");
		txtDTipo.setText("");
	}
	
	public void limpiarBuscar() {
		//txtBCodigo.setText("");
	}
	
	public void limpiarTabla() {
		tbBusqueda.setModel(dtm = new DefaultTableModel(datos, cabecera));
		ocultarColumna();
		TableColumn colCodigo = tbBusqueda.getColumn(1);
		TableColumn colArticulo = tbBusqueda.getColumn(2);
		TableColumn colVal1 = tbBusqueda.getColumn(3);
		TableColumn colVal2 = tbBusqueda.getColumn(4);
		TableColumn colVal3 = tbBusqueda.getColumn(5);
		TableColumn colTipo = tbBusqueda.getColumn(6);
		TableColumn colSit = tbBusqueda.getColumn(7);
		
		colCodigo.setPreferredWidth(50);
		colArticulo.setPreferredWidth(100);
		colVal1.setPreferredWidth(70);
		colVal2.setPreferredWidth(70);
		colVal3.setPreferredWidth(70);
		colTipo.setPreferredWidth(80);
		colSit.setPreferredWidth(45);
	}
	
	private void ocultarColumna(){
		tbBusqueda.getColumnModel().getColumn(0).setMaxWidth(0);
		tbBusqueda.getColumnModel().getColumn(0).setMinWidth(0);
		tbBusqueda.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tbBusqueda.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
	}
	
	public void cargarTabla(List<TARTCON> listado) {
		limpiarTabla();
		for (TARTCON  obj : listado) {
			Object datos[] = {obj.getId(), obj.getCodigo(),obj.getArticulo(),obj.getValor1(),obj.getValor2(),obj.getValor3(),obj.getTipo(),obj.getSituacion()};
			dtm.addRow(datos);
		}
	}
	
	public void cargarTabla(TARTCON  obj) {
		limpiarTabla();
		Object datos[] = {obj.getId(), obj.getCodigo(),obj.getArticulo(),obj.getValor1(),obj.getValor2(),obj.getValor3(),obj.getTipo(),obj.getSituacion()};
		dtm.addRow(datos);
	}
	
	private void pintar(){
		if (tbBusqueda.getSelectedRow() > -1) {
			int id = Integer.parseInt(tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 0).toString());
			String codigo = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 1).toString();
			String articulo = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 2).toString();
			String valor1 = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 3).toString();
			String valor2 = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 4).toString();
			String valor3 = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 5).toString();
			String tipo = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 6).toString();
			String activo = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 7).toString().trim();
			
			setId(id);
			txtDCodigo.setText(codigo);
			txtDValor1.setText(valor1);
			txtDValor2.setText(valor2);
			txtDValor3.setText(valor3);
			txtDArticulo.setText(articulo);
			txtDTipo.setText(tipo);
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
		
		txtDValor1.addKeyListener(controlador);
		txtDValor2.addKeyListener(controlador);
		txtDValor3.addKeyListener(controlador);
		
		txtDValor1.addActionListener(controlador);
		txtDValor2.addActionListener(controlador);
		txtDValor3.addActionListener(controlador);
	}

}
