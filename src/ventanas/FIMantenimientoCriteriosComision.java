package ventanas;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import servicio.MantenimientoCriteriosComisionService;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXTable;
import controlador.RRHHController;
import delegate.GestionComision;
import bean.TCCM;

public class FIMantenimientoCriteriosComision extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FIMantenimientoCriteriosComision gui = null;
	private JLabel lblFamilia;
	private JLabel lblSubfamilia;
	private JLabel lblOrigen;
	private JLabel lblValor;
	private MaestroComboBox cboFamilia;
	private MaestroComboBox cboSubFamilia;
	private MaestroComboBox cboOrigen;
	private JSpinner spValor;
	private String datosProceso[][];
	private String cabeceraProceso[] = { "Código", "Familia", "Sub-Familia",
			"Origen", "Valor x Mayor" };
	private DefaultTableModel dtmProceso;
	private JXTable tbCriterioProceso;
	private JScrollPane scpCriterioProceso;
	private String codigo="";
	MaestroBean beanFam = null;
	MaestroBean beanSubFam = null;
	MaestroBean beanOrigen = null;
	MantenimientoCriteriosComisionService criteriosComisionService = GestionComision
			.getMantenimientoCriteriosComisionService();

	public static FIMantenimientoCriteriosComision createInstance() {
		if (gui == null) {
			gui = new FIMantenimientoCriteriosComision();
		}
		return gui;
	}

	public static FIMantenimientoCriteriosComision getInstance() {
		return gui;
	}

	public FIMantenimientoCriteriosComision() {
		// initialize();
	}

	public void initialize() {
		setSize(610, 369);
		//
		setTitle(getTitle() + "-" + Sesion.tifCriteriosComisionMixta);
		toolBar.setVisible(true);
		/*
		btnEliminar.setVisible(true);
		btnGuardar.setVisible(true);
		btnCancelar.setVisible(true);
		btnSalir.setVisible(true);
		*/
		//

		lblFamilia = new JLabel("Familia:");
		lblFamilia.setBounds(26, 13, 63, 14);
		contenedorCenter.add(lblFamilia);

		beanFam = new MaestroBean();
		beanFam.setCodigo("nul");
		beanFam.setDescripcion("Seleccione");

		beanSubFam = new MaestroBean();
		beanSubFam.setCodigo("000");
		beanSubFam.setDescripcion("Todos");

		beanOrigen = new MaestroBean();
		beanOrigen.setCodigo("000");
		beanOrigen.setDescripcion("Todos");

		cboFamilia = new MaestroComboBox();
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
						cboSubFamilia.removeAllItems();
						cboOrigen.removeAllItems();
						try {
							cboSubFamilia.addItem(beanSubFam);
							MaestroBean beanFam = (MaestroBean) cboFamilia
									.getSelectedItem();
							cboSubFamilia.executeQuery("ttabd",
									"substring(tbespe,4,6)", "desesp",
									"tbiden='INSFA' and substring(tbespe,1,3)="
											+ beanFam.getCodigo());

							TCCM objTccm = new TCCM();
							objTccm.setCodfam(beanFam.getCodigo());

							List<TCCM> listado = criteriosComisionService
									.buscarPorFamilia(objTccm);
							cargarTablaProceso(listado);
							spValor.setValue(0.000);
						} catch (SQLException e1) {
						} catch (Exception e1) {
						}
					}

					if (cboFamilia.getSelectedItem().toString().trim()
							.equals("Seleccione")) {
						cboSubFamilia.removeAllItems();
						cboOrigen.removeAllItems();
						listar();
					}
				}
			}
		});

		cboFamilia.setBounds(119, 11, 226, 20);
		contenedorCenter.add(cboFamilia);

		lblSubfamilia = new JLabel("Sub-Familia:");
		lblSubfamilia.setBounds(26, 44, 83, 14);
		contenedorCenter.add(lblSubfamilia);

		cboSubFamilia = new MaestroComboBox();
		cboSubFamilia.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (!cboFamilia.getSelectedItem().toString().equals("")
							&& !cboFamilia.getSelectedItem().toString().trim()
									.equals("Seleccione")
							&& cboSubFamilia.getSelectedItem() != null) {
						if (!cboSubFamilia.getSelectedItem().toString()
								.equals("")) {
							cboOrigen.removeAllItems();
							cboOrigen.addItem(beanOrigen);
							try {
								MaestroBean beanFam = (MaestroBean) cboFamilia
										.getSelectedItem();
								MaestroBean beanSfam = (MaestroBean) cboSubFamilia
										.getSelectedItem();
								if (cboSubFamilia.getSelectedItem().toString()
										.equals("Todos")) {
									cboOrigen.executeQuery("tarti",
											" distinct substring(artcod,1,3)",
											"substring(artcod,1,3)", "artfam="
													+ beanFam.getCodigo());
								} else {
									cboOrigen.executeQuery(
											"tarti",
											" distinct substring(artcod,1,3)",
											"substring(artcod,1,3)",
											"artfam=" + beanFam.getCodigo()
													+ " and artsfa="
													+ beanSfam.getCodigo());
								}

								TCCM objTccm = new TCCM();
								objTccm.setCodfam(beanFam.getCodigo());
								objTccm.setCodsfa(beanSfam.getCodigo());
								List<TCCM> listado = criteriosComisionService
										.buscarPorFamYSubFam(objTccm);
								cargarTablaProceso(listado);
								spValor.setValue(0.000);
							} catch (SQLException e1) {

							} catch (Exception e1) {
								
							}
						}
					}
				}
			}
		});
		cboSubFamilia.setBounds(119, 42, 226, 20);
		contenedorCenter.add(cboSubFamilia);

		lblOrigen = new JLabel("Origen:");
		lblOrigen.setBounds(388, 16, 46, 14);
		contenedorCenter.add(lblOrigen);

		cboOrigen = new MaestroComboBox();
		cboOrigen.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!cboFamilia.getSelectedItem().toString().equals("")
						&& !cboFamilia.getSelectedItem().toString().trim()
								.equals("Seleccione")
						&& cboSubFamilia.getSelectedItem() != null
						&& cboOrigen.getSelectedItem() != null) {

					if (!cboSubFamilia.getSelectedItem().toString().equals("")
							&& !cboOrigen.getSelectedItem().toString()
									.equals("")) {
						try {
							MaestroBean beanFam = (MaestroBean) cboFamilia
									.getSelectedItem();
							MaestroBean beanSfam = (MaestroBean) cboSubFamilia
									.getSelectedItem();
							MaestroBean beanOri = (MaestroBean) cboOrigen
									.getSelectedItem();

							TCCM objTccm = new TCCM();
							objTccm.setDesfam(beanFam.getDescripcion());
							objTccm.setDessfa(beanSfam.getDescripcion());
							objTccm.setDesori(beanOri.getDescripcion());
							List<TCCM> listado = criteriosComisionService
									.buscarPorFamYSubFamyOri(objTccm);
							cargarTablaProceso(listado);
							if (listado.size() > 0) {								
								spValor.setValue(listado.get(0).getValor());
							} else {
								spValor.setValue(0.000);
							}
						} catch (SQLException ex) {
						} catch (Exception ex) {
						}
					}
				}
			}
		});

		cboOrigen.setBounds(480, 11, 83, 20);
		contenedorCenter.add(cboOrigen);

		lblValor = new JLabel("Valor x Mayor:");
		lblValor.setBounds(388, 50, 97, 14);
		contenedorCenter.add(lblValor);

		Locale myLocale = new Locale("en", "EN");
		spValor = new JSpinner();
		spValor.setModel(new SpinnerNumberModel(0.000, 0.000, 100.000, 0.001));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spValor,
				"0.000");
		DecimalFormat format = editor.getFormat();
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(myLocale));
		spValor.setEditor(editor);
		spValor.setBounds(480, 42, 83, 20);
		contenedorCenter.add(spValor);

		dtmProceso = new DefaultTableModel(datosProceso, cabeceraProceso);
		tbCriterioProceso = new JXTable(dtmProceso);
		tbCriterioProceso.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbCriterioProceso.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		tbCriterioProceso.setEditable(false);
		tbCriterioProceso.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tbCriterioProceso.getSelectedRow() > -1) {
					String codigo = tbCriterioProceso.getValueAt(
							tbCriterioProceso.getSelectedRow(), 0).toString();
					String familia = tbCriterioProceso.getValueAt(
							tbCriterioProceso.getSelectedRow(), 1).toString();
					String subFamilia = tbCriterioProceso.getValueAt(
							tbCriterioProceso.getSelectedRow(), 2).toString();
					String origen = tbCriterioProceso.getValueAt(
							tbCriterioProceso.getSelectedRow(), 3).toString();
					double valor = Double.parseDouble(tbCriterioProceso
							.getValueAt(tbCriterioProceso.getSelectedRow(), 4)
							.toString());
					MaestroBean beanFam = new MaestroBean();
					beanFam.setCodigo(codigo.substring(0, 3));
					beanFam.setDescripcion(familia);
					cboFamilia.setSelectedItem(beanFam);
					MaestroBean beanSubFam = new MaestroBean();
					beanSubFam.setCodigo(codigo.substring(3, 6));
					beanSubFam.setDescripcion(subFamilia);
					cboSubFamilia.setSelectedItem(beanSubFam);
					MaestroBean beanOrigen = new MaestroBean();
					beanOrigen.setCodigo(codigo.substring(6, 9));
					beanOrigen.setDescripcion(origen);
					cboOrigen.setSelectedItem(beanOrigen);
					spValor.setValue(valor);
					setCodigo(codigo);
				}
			}
		});
		tbCriterioProceso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (tbCriterioProceso.getSelectedRow() > -1) {
					String codigo = tbCriterioProceso.getValueAt(
							tbCriterioProceso.getSelectedRow(), 0).toString();
					String familia = tbCriterioProceso.getValueAt(
							tbCriterioProceso.getSelectedRow(), 1).toString();
					String subFamilia = tbCriterioProceso.getValueAt(
							tbCriterioProceso.getSelectedRow(), 2).toString();
					String origen = tbCriterioProceso.getValueAt(
							tbCriterioProceso.getSelectedRow(), 3).toString();
					double valor = Double.parseDouble(tbCriterioProceso
							.getValueAt(tbCriterioProceso.getSelectedRow(), 4)
							.toString());
					MaestroBean beanFam = new MaestroBean();
					beanFam.setCodigo(codigo.substring(0, 3));
					beanFam.setDescripcion(familia);
					cboFamilia.setSelectedItem(beanFam);
					MaestroBean beanSubFam = new MaestroBean();
					beanSubFam.setCodigo(codigo.substring(3, 6));
					beanSubFam.setDescripcion(subFamilia);
					cboSubFamilia.setSelectedItem(beanSubFam);
					MaestroBean beanOrigen = new MaestroBean();
					beanOrigen.setCodigo(codigo.substring(6, 9));
					beanOrigen.setDescripcion(origen);
					cboOrigen.setSelectedItem(beanOrigen);
					spValor.setValue(valor);
					setCodigo(codigo);
				}
			}
		});

		scpCriterioProceso = new JScrollPane(tbCriterioProceso);
		scpCriterioProceso.setBounds(26, 75, 540, 229);
		contenedorCenter.add(scpCriterioProceso);
		listar();
	}

	private void listar() {
		try {
			List<TCCM> tccms = criteriosComisionService.listar();
			cargarTablaProceso(tccms);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public MaestroBean getFamilia() {
		MaestroBean bean = null;
		try {
			bean= (MaestroBean) cboFamilia.getSelectedItem();
		} catch (Exception e) {	}
		return bean ;
	}

	public MaestroBean getSubFamilia() {
		MaestroBean bean = null;
		try {
			bean = (MaestroBean)cboSubFamilia.getSelectedItem();
		} catch (Exception e) {	}
		return bean;
	}

	public MaestroBean getOrigen() {
		MaestroBean bean = null;
		try {
			bean = (MaestroBean)cboOrigen.getSelectedItem();
		} catch (Exception e) {	}
		return bean;
	}

	public double getValor() {
		return Double.parseDouble(spValor.getValue().toString());
	}

	public void limpiarTablaProceso() {
		tbCriterioProceso.setModel(dtmProceso = new DefaultTableModel(
				datosProceso, cabeceraProceso));

		TableColumn colCodigo = tbCriterioProceso.getColumn(0);
		TableColumn colFam = tbCriterioProceso.getColumn(1);
		TableColumn colSfa = tbCriterioProceso.getColumn(2);
		TableColumn colOri = tbCriterioProceso.getColumn(3);
		TableColumn colVal = tbCriterioProceso.getColumn(4);

		colCodigo.setPreferredWidth(80);
		colFam.setPreferredWidth(200);
		colSfa.setPreferredWidth(120);
		colOri.setPreferredWidth(60);
		colVal.setPreferredWidth(100);
	}

	public void cargarTablaProceso(List<TCCM> tccms) {
		limpiarTablaProceso();
		for (TCCM tccm : tccms) {
			Object datos[] = { tccm.getCodigo(), tccm.getDesfam(),
					tccm.getDessfa(), tccm.getDesori(), tccm.getValor() };
			dtmProceso.addRow(datos);
		}
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public JButton getBtnEliminar() {
		return btnEliminar;
	}
	
	public String getCodigo(){	
		return codigo;
	}
	
	private void setCodigo(String codigo){	
		this.codigo = codigo;
	}

	public void setControlador(RRHHController controlador) {
		btnEliminar.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
	}

	public void limpiar() {
		cboFamilia.setSelectedIndex(0);
		spValor.setValue(0.000);
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
}
