package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import servicio.MantenimientoCriteriosComisionService;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXTable;
import controlador.RRHHController;
import delegate.GestionComision;
import bean.TCCM;
import bean.TECC;

import java.awt.Font;

public class FIMantenimientoExcepcionCriterio extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	MantenimientoCriteriosComisionService criteriosComisionService = GestionComision
			.getMantenimientoCriteriosComisionService();
	private static FIMantenimientoExcepcionCriterio gui = null;
	private JLabel lblFamilia;
	private MaestroComboBox cboFamilia;
	private String datosBusqueda[][];
	private String cabeceraBusqueda[] = { "Código", "Familia", "Sub-Familia",
			"Origen" };
	private DefaultTableModel dtmBusqueda;
	private JXTable tbCriterioBusqueda;
	private JScrollPane scpCriterioBusqueda;
	MaestroBean beanFam = null;
	MaestroBean beanSubFam = null;
	MaestroBean beanOrigen = null;
	private JTabbedPane tbpExcepciones;
	private JPanel pnlBusqueda;
	private JPanel pnlProceso;
	private JButton btnMantenerExcepciones;

	//
	private JLabel lblSubFamExc;
	private MaestroComboBox cboSubFamExc;
	private JLabel lblOrigenExc;
	private String datosExc[][];
	private String cabeceraExc[] = { "Código", "Excepción" };
	private DefaultTableModel dtmExc;
	private JXTable tbExcepcion;
	private JScrollPane scpExcepcion;
	private MaestroComboBox cboOrigenExc;
	private JButton btnAgregar;
	private JButton btnQuitar;
	private List<MaestroBean> origenes = new ArrayList<MaestroBean>();
	private List<MaestroBean> subFamilias = new ArrayList<MaestroBean>();
	private String codigo = "";
	private List<TECC> excepcionesActuales = new ArrayList<TECC>();

	public static FIMantenimientoExcepcionCriterio createInstance() {
		if (gui == null) {
			gui = new FIMantenimientoExcepcionCriterio();
		}
		return gui;
	}

	public static FIMantenimientoExcepcionCriterio getInstance() {
		return gui;
	}

	public FIMantenimientoExcepcionCriterio() {
		// initialize();
	}

	public void initialize() {
		setSize(489, 400);
		setTitle(getTitle() + "-" + Sesion.tifExcepcionCriterios);
		toolBar.setVisible(true);
		/*
		btnGuardar.setVisible(true);
		btnCancelar.setVisible(true);
		btnSalir.setVisible(true);
		btnGuardar.setEnabled(false);
		*/
		//
		tbpExcepciones = new JTabbedPane();
		tbpExcepciones.setBounds(0, 0, 479, 369);
		contenedorCenter.add(tbpExcepciones);

		pnlProceso = new JPanel();
		pnlProceso.setLayout(null);
		pnlProceso.setAutoscrolls(true);

		pnlBusqueda = new JPanel();
		pnlBusqueda.setLayout(null);
		pnlBusqueda.setAutoscrolls(true);

		tbpExcepciones.addTab("Busqueda", pnlBusqueda);
		tbpExcepciones.addTab("Procesar", pnlProceso);

		tbpExcepciones.setSelectedIndex(0);
		tbpExcepciones.setEnabledAt(1, false);

		crearPanelBusqueda();
		crearPanelProceso();
		listar();
		/*
		 * beanSubFam = new MaestroBean(); beanSubFam.setCodigo("000");
		 * beanSubFam.setDescripcion("Todos");
		 * 
		 * beanOrigen = new MaestroBean(); beanOrigen.setCodigo("000");
		 * beanOrigen.setDescripcion("Todos");
		 */
	}

	private void crearPanelProceso() {
		lblOrigenExc = new JLabel("Origen:");
		pnlProceso.add(lblOrigenExc);

		cboOrigenExc = new MaestroComboBox();
		pnlProceso.add(cboOrigenExc);

		lblSubFamExc = new JLabel("Sub Familia:");
		pnlProceso.add(lblSubFamExc);

		cboSubFamExc = new MaestroComboBox();
		cboSubFamExc.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cboSubFamExc.getSelectedItem() != null) {
						if (!cboSubFamExc.getSelectedItem().toString()
								.equals("")
								&& cboOrigenExc.isVisible() == true) {
							cboOrigenExc.removeAllItems();
							try {
								MaestroBean beanSfam = (MaestroBean) cboSubFamExc
										.getSelectedItem();
								cboOrigenExc.executeQuery(
										"tarti",
										" distinct substring(artcod,1,3)",
										"substring(artcod,1,3)",
										"artfam=" + codigo.subSequence(0, 3)
												+ " and artsfa="
												+ beanSfam.getCodigo());
							} 
							catch (Exception e2) {
								
							}
						}
					}
				}
			}
		});
		pnlProceso.add(cboSubFamExc);

		dtmExc = new DefaultTableModel(datosExc, cabeceraExc);
		tbExcepcion = new JXTable(dtmExc);
		scpExcepcion = new JScrollPane(tbExcepcion);
		pnlProceso.add(scpExcepcion);

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cboOrigenExc.isVisible() == true
						&& cboSubFamExc.isVisible() == true) {
					if (cboSubFamExc.getSelectedItem() != null
							&& cboOrigenExc.getSelectedItem() != null) {
						if (!cboSubFamExc.getSelectedItem().toString()
								.equals("")
								&& !cboOrigenExc.getSelectedItem().toString()
										.equals("")) {
							try {
							MaestroBean subFam = (MaestroBean) cboSubFamExc
									.getSelectedItem();
							MaestroBean origen = (MaestroBean) cboOrigenExc
									.getSelectedItem();
							String excepcion = subFam.getDescripcion().trim()
									+ "-" + origen.getDescripcion().trim()
									+ "-" + codigo.substring(0, 3)
									+ subFam.getCodigo() + origen.getCodigo();
							boolean flag = false;
							for (int i = 0; i < tbExcepcion.getRowCount(); i++) {
								if (excepcion.equalsIgnoreCase(tbExcepcion
										.getValueAt(i, 1).toString()))
									flag = true;
							}
							if (flag == false) {
								Object[] dato = { codigo, excepcion };
								dtmExc.addRow(dato);
							} else {
								Sesion.mensajeError(
										FIMantenimientoExcepcionCriterio.this,
										"La excepción ya ha sido agregada, seleccione otra.");
							}
							}catch (Exception e2) {
							
							}
						}
					}
				} else if (cboSubFamExc.isVisible() == true
						&& cboOrigenExc.isVisible() == false) {
					if (cboSubFamExc.getSelectedItem() != null) {
						if (!cboSubFamExc.getSelectedItem().toString()
								.equals("")) {
							try{
							MaestroBean subFam = (MaestroBean) cboSubFamExc
									.getSelectedItem();
							String excepcion = subFam.getDescripcion().trim()
									+ "-" + codigo.substring(0, 3)
									+ subFam.getCodigo()
									+ codigo.substring(6, 9);

							boolean flag = false;
							for (int i = 0; i < tbExcepcion.getRowCount(); i++) {
								if (excepcion.equalsIgnoreCase(tbExcepcion
										.getValueAt(i, 1).toString()))
									flag = true;
							}
							if (flag == false) {
								Object[] dato = { codigo, excepcion };
								dtmExc.addRow(dato);
							} else {
								Sesion.mensajeError(
										FIMantenimientoExcepcionCriterio.this,
										"La excepción ya ha sido agregada, seleccione otra.");
							}
							}catch (Exception ex) {
							}
						}
					}
				} else if (cboOrigenExc.isVisible() == true
						&& cboSubFamExc.isVisible() == false) {
					if (cboOrigenExc.getSelectedItem() != null) {
						if (!cboOrigenExc.getSelectedItem().toString()
								.equals("")) {
							try{
							MaestroBean origen = (MaestroBean) cboOrigenExc
									.getSelectedItem();
							String excepcion = origen.getDescripcion().trim()
									+ "-" + codigo.substring(0, 3)
									+ codigo.substring(3, 6)
									+ origen.getCodigo().trim();

							boolean flag = false;
							for (int i = 0; i < tbExcepcion.getRowCount(); i++) {
								if (excepcion.equalsIgnoreCase(tbExcepcion
										.getValueAt(i, 1).toString()))
									flag = true;
							}
							if (flag == false) {
								Object[] dato = { codigo, excepcion };
								dtmExc.addRow(dato);
							} else {
								Sesion.mensajeError(
										FIMantenimientoExcepcionCriterio.this,
										"La excepción ya ha sido agregada, seleccione otra.");
							}
							}catch (Exception e2) {
							}
						}
					}
				}
			}
		});
		pnlProceso.add(btnAgregar);

		btnQuitar = new JButton("Quitar");
		btnQuitar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tbExcepcion.getSelectedRow() > -1) {
					dtmExc.removeRow(tbExcepcion.getSelectedRow());
				}else{
					Sesion.mensajeError(
							FIMantenimientoExcepcionCriterio.this,
							"Seleccione la fila que desea quitar");
				}
			}
		});

		pnlProceso.add(btnQuitar);
	}

	public void procesoOrigen(List<MaestroBean> origenes, String codigo,
			List<TECC> excepciones) {
		lblOrigenExc.setVisible(true);
		cboOrigenExc.setVisible(true);
		lblSubFamExc.setVisible(false);
		cboSubFamExc.setVisible(false);

		cboOrigenExc.removeAllItems();
		this.origenes.clear();
		this.origenes = origenes;
		this.codigo = codigo;

		lblOrigenExc.setBounds(21, 18, 46, 14);
		cboOrigenExc.setBounds(77, 15, 95, 20);
		scpExcepcion.setBounds(21, 41, 426, 264);
		btnAgregar.setBounds(182, 15, 77, 23);
		btnQuitar.setBounds(269, 15, 77, 23);

		for (MaestroBean bean : origenes) {
			cboOrigenExc.addItem(bean);
		}
		// lblSubFamExc.setBounds(48, 313, 77, 14);
		// cboSubFamExc.setBounds(132, 310, 196, 20);
		cargarTablaExcepcion(excepciones);
		excepcionesActuales = excepciones;
	}

	public void procesoSubFam(List<MaestroBean> subFamilias, String codigo,
			List<TECC> excepciones) {
		lblSubFamExc.setVisible(true);
		cboSubFamExc.setVisible(true);
		lblOrigenExc.setVisible(false);
		cboOrigenExc.setVisible(false);

		cboSubFamExc.removeAllItems();
		this.subFamilias.clear();
		this.subFamilias = subFamilias;
		this.codigo = codigo;

		lblSubFamExc.setBounds(21, 16, 77, 14);
		cboSubFamExc.setBounds(105, 13, 196, 20);
		scpExcepcion.setBounds(21, 41, 426, 264);
		btnAgregar.setBounds(314, 12, 63, 23);
		btnQuitar.setBounds(384, 12, 63, 23);
		for (MaestroBean maestroBean : subFamilias) {
			cboSubFamExc.addItem(maestroBean);
		}
		// lblOrigenExc.setBounds(255, 316, 46, 14);
		// cboOrigenExc.setBounds(311, 313, 95, 20);
		cargarTablaExcepcion(excepciones);
		excepcionesActuales = excepciones;
	}

	public void procesoSubFamOri(List<MaestroBean> subFamilias, String codigo,
			List<TECC> excepciones) {
		lblOrigenExc.setVisible(true);
		cboOrigenExc.setVisible(true);
		lblSubFamExc.setVisible(true);
		cboSubFamExc.setVisible(true);

		cboSubFamExc.removeAllItems();
		cboOrigenExc.removeAllItems();
		this.subFamilias.clear();
		this.subFamilias = subFamilias;
		this.codigo = codigo;

		lblSubFamExc.setBounds(21, 16, 77, 14);
		cboSubFamExc.setBounds(105, 13, 185, 20);
		lblOrigenExc.setBounds(21, 41, 46, 14);
		cboOrigenExc.setBounds(105, 38, 95, 20);
		scpExcepcion.setBounds(21, 71, 426, 229);
		btnAgregar.setBounds(358, 13, 89, 23);
		btnQuitar.setBounds(358, 38, 89, 23);
		for (MaestroBean maestroBean : subFamilias) {
			cboSubFamExc.addItem(maestroBean);
		}
		cargarTablaExcepcion(excepciones);
		excepcionesActuales = excepciones;
	}

	private void crearPanelBusqueda() {
		lblFamilia = new JLabel("Familia:");
		lblFamilia.setBounds(26, 13, 63, 14);
		pnlBusqueda.add(lblFamilia);

		beanFam = new MaestroBean();
		beanFam.setCodigo("nul");
		beanFam.setDescripcion("Seleccione");

		cboFamilia = new MaestroComboBox();
		cboFamilia.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cboFamilia.addItem(beanFam);
		cboFamilia.executeQuery("ttabd", "tbespe", "desesp",
				"tbiden='INFAM' and tbespe not in('096','998','999')");
		cboFamilia.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (!cboFamilia.getSelectedItem().toString().trim()
							.equals("")
							&& !cboFamilia.getSelectedItem().toString().trim()
									.equals("Seleccione")) {
						try {
							MaestroBean beanFam = (MaestroBean) cboFamilia
									.getSelectedItem();
							TCCM objTccm = new TCCM();
							objTccm.setCodfam(beanFam.getCodigo());

							List<TCCM> listado = criteriosComisionService
									.buscarCriteriosParaExcepcionPorFamilia(objTccm);
							cargarTablaProceso(listado);

						} catch (SQLException e1) {
						}catch (Exception e1) {
						}
					}

					if (cboFamilia.getSelectedItem().toString().trim()
							.equals("Seleccione")) {
						listar();
					}
				}
			}
		});

		cboFamilia.setBounds(105, 11, 174, 20);
		pnlBusqueda.add(cboFamilia);

		dtmBusqueda = new DefaultTableModel(datosBusqueda, cabeceraBusqueda);
		tbCriterioBusqueda = new JXTable(dtmBusqueda);
		tbCriterioBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbCriterioBusqueda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbCriterioBusqueda.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		tbCriterioBusqueda.setEditable(false);
		scpCriterioBusqueda = new JScrollPane(tbCriterioBusqueda);
		scpCriterioBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scpCriterioBusqueda.setBounds(26, 50, 425, 252);
		pnlBusqueda.add(scpCriterioBusqueda);

		btnMantenerExcepciones = new JButton("Mantener Excepciones");
		btnMantenerExcepciones.setEnabled(false);
		btnMantenerExcepciones.setBounds(300, 11, 151, 23);
		pnlBusqueda.add(btnMantenerExcepciones);
	}

	private void listar() {
		try {
			List<TCCM> tccms = criteriosComisionService
					.listarCriteriosParaExcepcion();
			cargarTablaProceso(tccms);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public MaestroBean getFamilia() {
		return (MaestroBean) cboFamilia.getSelectedItem();
	}

	public void limpiarTablaProceso() {
		tbCriterioBusqueda.setModel(dtmBusqueda = new DefaultTableModel(
				datosBusqueda, cabeceraBusqueda));

		TableColumn colCodigo = tbCriterioBusqueda.getColumn(0);
		TableColumn colFam = tbCriterioBusqueda.getColumn(1);
		TableColumn colSfa = tbCriterioBusqueda.getColumn(2);
		TableColumn colOri = tbCriterioBusqueda.getColumn(3);

		colCodigo.setPreferredWidth(80);
		colFam.setPreferredWidth(175);
		colSfa.setPreferredWidth(95);
		colOri.setPreferredWidth(60);
	}

	public void cargarTablaProceso(List<TCCM> tccms) {
		limpiarTablaProceso();
		for (TCCM tccm : tccms) {
			Object datos[] = { tccm.getCodigo(), tccm.getDesfam(),
					tccm.getDessfa(), tccm.getDesori() };
			dtmBusqueda.addRow(datos);
		}
	}

	public void limpiarTablaExcepcion() {
		tbExcepcion.setModel(dtmExc = new DefaultTableModel(datosExc,
				cabeceraExc));

		TableColumn colCodigo = tbExcepcion.getColumn(0);
		TableColumn colDes = tbExcepcion.getColumn(1);

		colCodigo.setPreferredWidth(100);
		colDes.setPreferredWidth(250);

	}

	public void cargarTablaExcepcion(List<TECC> teccs) {
		limpiarTablaExcepcion();
		for (TECC tecc : teccs) {
			Object datos[] = { tecc.getCriterio(), tecc.getExcepcion() };
			dtmExc.addRow(datos);
		}
	}

	public void setControlador(RRHHController controlador) {
		btnGuardar.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnMantenerExcepciones.addActionListener(controlador);
	}

	public void limpiar() {
		cboFamilia.setSelectedIndex(0);
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnMantener() {
		return btnMantenerExcepciones;
	}

	public JTabbedPane getTbpExcepciones() {
		return tbpExcepciones;
	}

	public TCCM getTCCM() {
		TCCM tccm = null;
		if (tbCriterioBusqueda.getSelectedRow() > -1) {
			tccm = new TCCM();
			String codigo = tbCriterioBusqueda.getValueAt(
					tbCriterioBusqueda.getSelectedRow(), 0).toString();
			tccm.setCodigo(codigo);
		}
		return tccm;
	}

	public JXTable getTbExcepciones(){
		return tbExcepcion;
	}
	
	public List<TECC> getExcepcionesActuales(){
		return excepcionesActuales;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
}
