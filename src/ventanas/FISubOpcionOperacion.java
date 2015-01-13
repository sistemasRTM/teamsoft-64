package ventanas;

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
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import util.Conexion;
import controlador.PermisosController;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FISubOpcionOperacion extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pnlSubOpcion;
	private JPanel pnlDetalle;
	private JPanel pnlOperacion;
	private JLabel lblDescripcionS;
	private JLabel lblDescripionO;
	private DefaultTableModel dtm;
	private String cabecera[] = { "Código", "Operación" }, datos[][] = {};
	private JScrollPane scpDetalle;
	private JXTable tbDetalle;
	private JComboBox cboSubOpcion;
	private JComboBox cboOperacion;
	private JButton btnAgregar;
	private JButton btnQuitar;

	public FISubOpcionOperacion() {

		setSize(505, 460);
		setTitle(getTitle() + "-" + Sesion.tifSubOpcion_Operacion);
		//
		toolBar.setVisible(true);
		
		pnlSubOpcion = new JPanel();
		pnlSubOpcion.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos de la Sub Opción",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlSubOpcion.setBounds(10, 11, 468, 56);
		pnlSubOpcion.setLayout(null);

		lblDescripcionS = new JLabel("Descripción:");
		lblDescripcionS.setBounds(10, 27, 88, 14);
		pnlSubOpcion.add(lblDescripcionS);
		
		cboSubOpcion = new MaestroComboBox(Sesion.bdProd+"TSUBOPC","COD_SUBOPC","DESC_SUBOPC");
		cboSubOpcion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					MaestroBean subOpcion = (MaestroBean) cboSubOpcion
							.getSelectedItem();
					listarDetalle(subOpcion);
				}
			}

			private void listarDetalle(MaestroBean subOpcion) {
				try {
					PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select tos.COD_OPR,o.desc_opr from "+Sesion.bdProd+"TDETA_OPR_SUBOPC  tos inner join "+Sesion.bdProd+"TOPERACION o on tos.COD_OPR = o.COD_OPR where COD_SUBOPC=?");
					pstm.setInt(1,Integer.parseInt(subOpcion.getCodigo()));
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
		cboSubOpcion.setBounds(83, 24, 211, 20);
		pnlSubOpcion.add(cboSubOpcion);
		
		pnlOperacion = new JPanel();
		pnlOperacion.setLayout(null);
		pnlOperacion.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos de la Operación",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlOperacion.setBounds(10, 78, 468, 56);
		
		lblDescripionO = new JLabel("Descripción:");
		lblDescripionO.setBounds(10, 27, 88, 14);
		pnlOperacion.add(lblDescripionO);

		cboOperacion = new MaestroComboBox(Sesion.bdProd+"TOPERACION","COD_OPR","DESC_OPR");
		cboOperacion.setBounds(83, 24, 211, 20);
		pnlOperacion.add(cboOperacion);

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MaestroBean operacion = (MaestroBean) cboOperacion
						.getSelectedItem();
				boolean flag = false;
				for (int i = 0; i < tbDetalle.getRowCount(); i++) {
					if (operacion.getCodigo().equalsIgnoreCase(tbDetalle
							.getValueAt(i, 0).toString()))
						flag = true;
				}
				
				if(flag==false){
				Object[] dato = { operacion.getCodigo(),
						operacion.getDescripcion() };
				dtm.addRow(dato);
				}else
					Sesion.mensajeError(FISubOpcionOperacion.this, "La operación ya ha sido agregada, seleccione otra.");
				
			}
		});
		btnAgregar.setBounds(306, 23, 71, 23);
		pnlOperacion.add(btnAgregar);

		btnQuitar = new JButton("Quitar");
		btnQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbDetalle.getSelectedRow() > -1) {
					dtm.removeRow(tbDetalle.getSelectedRow());
				}
			}
		});
		btnQuitar.setBounds(387, 23, 71, 23);
		pnlOperacion.add(btnQuitar);
		
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
				
		contenedorCenter.add(pnlSubOpcion);
		contenedorCenter.add(pnlDetalle);
		contenedorCenter.add(pnlOperacion);
		//*****************************************************
	}
	
	public JComboBox getCboSubOpcion(){
		return cboSubOpcion;
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
