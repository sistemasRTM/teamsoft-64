package ventanas;

import recursos.MaestroInternalFrame;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXTable;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.Sesion;
import util.Conexion;
import controlador.PermisosController;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FIOpcionSubOpcion extends MaestroInternalFrame{

	private static final long serialVersionUID = 1L;
	private JPanel pnlOpcion;
	private JPanel pnlDetalle;
	private JPanel pnlSubOpcion;
	private JLabel lblDescripcionS;
	private JLabel lblDescripionO;
	private DefaultTableModel dtm;
	private String cabecera[] = { "Código", "Sub Opción" }, datos[][] = {};
	private JScrollPane scpDetalle;
	private JXTable tbDetalle;

	private JComboBox cboOpcion;
	private JComboBox cboSubOpcion;
	private JButton btnAgregar;
	private JButton btnQuitar;

	public FIOpcionSubOpcion() {

		setSize(505, 460);
		setTitle(getTitle() + "-" + Sesion.tifOpcion_SubOpcion);
		//
		toolBar.setVisible(true);
		//
		pnlOpcion = new JPanel();
		pnlOpcion.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos de la Opción",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlOpcion.setBounds(10, 11, 468, 56);
		pnlOpcion.setLayout(null);

		lblDescripcionS = new JLabel("Descripción:");
		lblDescripcionS.setBounds(10, 27, 88, 14);
		pnlOpcion.add(lblDescripcionS);
		
		cboOpcion = new MaestroComboBox(Sesion.bdProd+"TOPCION","COD_OPC","DESC_OPC");
		cboOpcion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					MaestroBean opcion = (MaestroBean) cboOpcion
							.getSelectedItem();
					listarDetalle(opcion);
				}
			}

			private void listarDetalle(MaestroBean opcion) {
				try {
					PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select tos.COD_SUBOPC,o.DESC_SUBOPC from "+Sesion.bdProd+"TDETA_OPC_SUBOPC  tos inner join "+Sesion.bdProd+"TSUBOPC o on tos.COD_SUBOPC = o.COD_SUBOPC  where COD_OPC=?");
					pstm.setInt(1,Integer.parseInt(opcion.getCodigo()));
					ResultSet rs = pstm.executeQuery();
					limpiarTabla();
					while(rs.next()){
						Object[] dato = { rs.getInt(1),
								rs.getString(2) };
						dtm.addRow(dato);
					}
					rs.close();
					pstm.close();
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
			}
		});
		cboOpcion.setBounds(83, 24, 211, 20);
		pnlOpcion.add(cboOpcion);
		
		pnlSubOpcion = new JPanel();
		pnlSubOpcion.setLayout(null);
		pnlSubOpcion.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos de la Sub Opción",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlSubOpcion.setBounds(10, 78, 468, 56);
		
		lblDescripionO = new JLabel("Descripción:");
		lblDescripionO.setBounds(10, 27, 88, 14);
		pnlSubOpcion.add(lblDescripionO);

		cboSubOpcion = new MaestroComboBox(Sesion.bdProd+"TSUBOPC","COD_SUBOPC","DESC_SUBOPC");
		cboSubOpcion.setBounds(83, 24, 211, 20);
		pnlSubOpcion.add(cboSubOpcion);

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MaestroBean subOperacion = (MaestroBean) cboSubOpcion
						.getSelectedItem();
				boolean flag = false;
				for (int i = 0; i < tbDetalle.getRowCount(); i++) {
					if (subOperacion.getCodigo().equalsIgnoreCase(tbDetalle
							.getValueAt(i, 0).toString()))
						flag = true;
				}
				
				if(flag==false){
				Object[] dato = { subOperacion.getCodigo(),
						subOperacion.getDescripcion() };
				dtm.addRow(dato);
				}else
					Sesion.mensajeError(FIOpcionSubOpcion.this, "La sub opción ya ha sido agregada, seleccione otra.");
			}
		});
		btnAgregar.setBounds(306, 23, 71, 23);
		pnlSubOpcion.add(btnAgregar);

		btnQuitar = new JButton("Quitar");
		btnQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbDetalle.getSelectedRow() > -1) {
					dtm.removeRow(tbDetalle.getSelectedRow());
				}
			}
		});
		btnQuitar.setBounds(387, 23, 71, 23);
		pnlSubOpcion.add(btnQuitar);
		
		pnlDetalle = new JPanel();
		pnlDetalle.setLayout(null);
		pnlDetalle.setBounds(10, 145, 468, 215);
		pnlDetalle.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));

		dtm = new DefaultTableModel(datos, cabecera);

		tbDetalle = new JXTable(dtm);
		tbDetalle.getTableHeader().setReorderingAllowed(false);
		tbDetalle.getTableHeader().setResizingAllowed(false);
		tbDetalle.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDetalle.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		tbDetalle.setEnabled(true);
		tbDetalle.setEditable(false);

		scpDetalle = new JScrollPane(tbDetalle);
		scpDetalle.setBounds(10, 11, 452, 194);
		pnlDetalle.add(scpDetalle);

		limpiarTabla();
				
		contenedorCenter.add(pnlOpcion);
		contenedorCenter.add(pnlDetalle);
		contenedorCenter.add(pnlSubOpcion);
		//*****************************************************
	}
	
	public JComboBox getCboOpcion(){
		return cboOpcion;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public DefaultTableModel getModeloTable() {
		return dtm;
	}

	public JXTable getTable() {
		return tbDetalle;
	}

	public void setControlador(PermisosController controlador) {
		btnCancelar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
	}

	public void limpiarTabla() {
		tbDetalle.setModel(dtm = new DefaultTableModel(datos, cabecera));

		TableColumn colCod = tbDetalle.getColumn(0);
		TableColumn colOpr = tbDetalle.getColumn(1);

		colCod.setPreferredWidth(100);
		colOpr.setPreferredWidth(350);
	}

}
