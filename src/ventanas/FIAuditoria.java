package ventanas;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import javax.swing.JLabel;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import bean.Auditoria;

import controlador.SeguridadController;
import javax.swing.JCheckBox;

public class FIAuditoria extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FIAuditoria gui = null;
	private JButton btnConsultar;
	private DefaultTableModel dtm;
	private JXTable tbAuditoria;
	private JScrollPane scpAuditoria;
	private MaestroComboBox cboModulo;
	private JLabel lblModulo;
	private JLabel lblOpcin;
	private MaestroComboBox cboOpcion;
	private JLabel lblSubOpcin;
	private MaestroComboBox cboSubOpcion;
	private JLabel lblOperacin;
	private MaestroComboBox cboOperacion;
	private JLabel lblCia;
	private MaestroComboBox cboCIA;
	private JLabel lblUsuario;
	private MaestroComboBox cboUsuario;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JXDatePicker dtpDesde;
	private JXDatePicker dtpHasta;
	private String datos[][];
	private String cabecera[] = { "Fecha", "Usuario", "CIA", "Modulo",
			"Opcion", "Sub Opcion", "Operación","Nº Registros", "Ref1", "Ref2", "Ref3",
			"Ref4", "Ref5", "Ref6", "Ref7", "Ref8", "Ref9", "Ref10", "Ref11",
			"Ref12", "Ref3", "Ref14", "Ref15", "Ref16", "Ref17", "Ref18",
			"Ref19", "Ref20", "Ref21", "Ref22", "Ref23", "Ref24", "Ref25",
			"Ref26", "Ref27", "Ref28", "Ref29", "Ref30", "Ref31", "Ref32",
			"Ref33", "Ref34", "Ref35", "Ref36", "Ref37", "Ref38", "Ref39",
			"Ref40", "Ref41", "Ref42", "Ref43", "Ref44", "Ref45", "Ref46",
			"Ref47", "Ref48", "Ref49", "Ref50" };
	private JLabel lblIncluir;
	private JCheckBox chbIniciarSesion;
	private JCheckBox chbCerrarSesion;

	public static FIAuditoria createInstance() {
		if (gui == null) {
			gui = new FIAuditoria();
		}
		return gui;
	}

	public static FIAuditoria getInstance() {
		return gui;
	}

	public FIAuditoria() {
	//	 initialize();
	}

	public void initialize() {
		setSize(1360, 660);
		setTitle(getTitle() + "-" + Sesion.tifAuditoria);
		//
		toolBar.setVisible(true);
		btnSalir.setVisible(true);
		//
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(1212, 10, 120, 23);
		contenedorCenter.add(btnConsultar);

		dtm = new DefaultTableModel(datos, cabecera);
		tbAuditoria = new JXTable(dtm);
		tbAuditoria.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scpAuditoria = new JScrollPane(tbAuditoria);
		scpAuditoria.setBounds(10, 76, 1322, 517);
		contenedorCenter.add(scpAuditoria);

		final MaestroBean beanCIA = new MaestroBean();
		beanCIA.setCodigo("Todos");
		beanCIA.setDescripcion("Todos");

		final MaestroBean beanModulo = new MaestroBean();
		beanModulo.setCodigo("Todos");
		beanModulo.setDescripcion("Todos");

		final MaestroBean beanOpcion = new MaestroBean();
		beanOpcion.setCodigo("Todos");
		beanOpcion.setDescripcion("Todos");

		final MaestroBean beanSubOpcion = new MaestroBean();
		beanSubOpcion.setCodigo("Todos");
		beanSubOpcion.setDescripcion("Todos");

		final MaestroBean beanOperacion = new MaestroBean();
		beanOperacion.setCodigo("Todos");
		beanOperacion.setDescripcion("Todos");

		final MaestroBean beanUsuario = new MaestroBean();
		beanUsuario.setCodigo("Todos");
		beanUsuario.setDescripcion("Todos");

		lblCia = new JLabel("CIA:");
		lblCia.setBounds(10, 15, 36, 14);
		contenedorCenter.add(lblCia);

		cboCIA = new MaestroComboBox();
		cboCIA.setBounds(70, 11, 121, 20);
		cboCIA.addItem(beanCIA);
		cboCIA.executeQuery(Sesion.bdProd + "tauditoria", "distinct cia",
				"cia", " cia<>''");
		cboCIA.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cboCIA.getSelectedItem() != null) {
						if (!cboCIA.getSelectedItem().toString().equals("")) {
							cboModulo.removeAllItems();
							cboOpcion.removeAllItems();
							cboSubOpcion.removeAllItems();
							cboOperacion.removeAllItems();
							cboUsuario.removeAllItems();
							cboModulo.addItem(beanModulo);
							try {
								MaestroBean beanCIA = (MaestroBean) cboCIA
										.getSelectedItem();
								cboModulo.executeQuery(Sesion.bdProd
										+ "tauditoria", "distinct modulo",
										"modulo", " modulo<>'' and cia='"
												+ beanCIA.getDescripcion()
														.trim() + "'");
							} catch (Exception ex) {
								ex.getMessage();
								System.out.println(ex.getMessage() + " ");
							}
						}
					}
				}
			}
		});
		contenedorCenter.add(cboCIA);

		cboModulo = new MaestroComboBox();
		cboModulo.setBounds(251, 11, 147, 20);
		cboModulo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cboModulo.getSelectedItem() != null) {
						if (!cboModulo.getSelectedItem().toString().equals("")) {
							cboOpcion.removeAllItems();
							cboSubOpcion.removeAllItems();
							cboOperacion.removeAllItems();
							cboUsuario.removeAllItems();
							cboOpcion.addItem(beanOpcion);
							try {
								MaestroBean beanCIA = (MaestroBean) cboCIA
										.getSelectedItem();
								MaestroBean beanModulo = (MaestroBean) cboModulo
										.getSelectedItem();
								cboOpcion.executeQuery(Sesion.bdProd
										+ "tauditoria", "distinct menu",
										"menu", " menu<>'' and modulo='"
												+ beanModulo.getDescripcion()
														.trim()
												+ "' and cia='"
												+ beanCIA.getDescripcion()
														.trim() + "'");
							} catch (Exception ex) {
								ex.getMessage();
								System.out.println(ex.getMessage() + " ");
							}
						}
					}
				}
			}
		});
		contenedorCenter.add(cboModulo);

		cboOpcion = new MaestroComboBox();
		cboOpcion.setBounds(466, 11, 147, 20);
		cboOpcion.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cboOpcion.getSelectedItem() != null) {
						if (!cboOpcion.getSelectedItem().toString().equals("")) {
							cboSubOpcion.removeAllItems();
							cboOperacion.removeAllItems();
							cboUsuario.removeAllItems();
							cboSubOpcion.addItem(beanSubOpcion);
							try {
								MaestroBean beanCIA = (MaestroBean) cboCIA
										.getSelectedItem();
								MaestroBean beanModulo = (MaestroBean) cboModulo
										.getSelectedItem();
								MaestroBean beanOpcion = (MaestroBean) cboOpcion
										.getSelectedItem();
								cboSubOpcion.executeQuery(Sesion.bdProd
										+ "tauditoria", "distinct opcion",
										"opcion", " opcion<>'' and menu='"
												+ beanOpcion.getDescripcion()
														.trim()
												+ "' and modulo='"
												+ beanModulo.getDescripcion()
														.trim()
												+ "'"
												+ " and cia='"
												+ beanCIA.getDescripcion()
														.trim() + "'");
							} catch (Exception ex) {
								ex.getMessage();
								System.out.println(ex.getMessage() + " ");
							}
						}
					}
				}

			}
		});
		contenedorCenter.add(cboOpcion);

		cboSubOpcion = new MaestroComboBox();
		cboSubOpcion.setBounds(698, 10, 179, 20);
		cboSubOpcion.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cboSubOpcion.getSelectedItem() != null) {
						if (!cboSubOpcion.getSelectedItem().toString()
								.equals("")) {
							cboOperacion.removeAllItems();
							cboUsuario.removeAllItems();
							cboOperacion.addItem(beanSubOpcion);
							cboUsuario.addItem(beanUsuario);
							try {
								MaestroBean beanCIA = (MaestroBean) cboCIA
										.getSelectedItem();
								MaestroBean beanModulo = (MaestroBean) cboModulo
										.getSelectedItem();
								MaestroBean beanOpcion = (MaestroBean) cboOpcion
										.getSelectedItem();
								MaestroBean beanSubOpcion = (MaestroBean) cboSubOpcion
										.getSelectedItem();
								cboOperacion.executeQuery(Sesion.bdProd
										+ "tauditoria", "distinct tipo_ope",
										"tipo_ope",
										" tipo_ope<>'' and opcion='"
												+ beanSubOpcion
														.getDescripcion()
														.trim()
												+ "' and menu='"
												+ beanOpcion.getDescripcion()
														.trim()
												+ "' and modulo='"
												+ beanModulo.getDescripcion()
														.trim()
												+ "' and cia='"
												+ beanCIA.getDescripcion()
														.trim() + "'");
								
								cboUsuario.executeQuery(
										Sesion.bdProd + "tauditoria",
										"distinct USU",
										"USU",
										" USU<>'' and opcion='"
												+ beanSubOpcion
														.getDescripcion()
														.trim()
												+ "' and menu='"
												+ beanOpcion.getDescripcion()
														.trim()
												+ "' and modulo='"
												+ beanModulo.getDescripcion()
														.trim()
												+ "' and cia='"
												+ beanCIA.getDescripcion()
														.trim() + "'");
								
							} catch (Exception ex) {
								ex.getMessage();
								System.out.println(ex.getMessage() + " ");
							}
						}
					}
				}
			}
		});
		contenedorCenter.add(cboSubOpcion);

		lblModulo = new JLabel("Modulo:");
		lblModulo.setBounds(201, 15, 63, 14);
		contenedorCenter.add(lblModulo);

		lblOpcin = new JLabel("Opci\u00F3n:");
		lblOpcin.setBounds(411, 15, 46, 14);
		contenedorCenter.add(lblOpcin);

		lblSubOpcin = new JLabel("Sub Opci\u00F3n:");
		lblSubOpcin.setBounds(623, 14, 87, 14);
		contenedorCenter.add(lblSubOpcin);

		lblOperacin = new JLabel("Operaci\u00F3n:");
		lblOperacin.setBounds(896, 14, 80, 14);
		contenedorCenter.add(lblOperacin);

		cboOperacion = new MaestroComboBox();
		cboOperacion.setBounds(966, 10, 147, 20);
		/*cboOperacion.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cboOperacion.getSelectedItem() != null) {
						if (!cboOperacion.getSelectedItem().toString()
								.equals("")) {
							cboUsuario.removeAllItems();
							cboUsuario.addItem(beanUsuario);
							try {
								MaestroBean beanCIA = (MaestroBean) cboCIA
										.getSelectedItem();
								MaestroBean beanModulo = (MaestroBean) cboModulo
										.getSelectedItem();
								MaestroBean beanOpcion = (MaestroBean) cboOpcion
										.getSelectedItem();
								MaestroBean beanSubOpcion = (MaestroBean) cboSubOpcion
										.getSelectedItem();
								MaestroBean beanOperacion = (MaestroBean) cboOperacion
										.getSelectedItem();
								cboUsuario.executeQuery(
										Sesion.bdProd + "tauditoria",
										"distinct USU",
										"USU",
										" USU<>'' and tipo_ope='"
												+ beanOperacion
														.getDescripcion()
												+ "' and opcion='"
												+ beanSubOpcion
														.getDescripcion()
														.trim()
												+ "' and menu='"
												+ beanOpcion.getDescripcion()
														.trim()
												+ "' and modulo='"
												+ beanModulo.getDescripcion()
														.trim()
												+ "'"
												+ " and cia='"
												+ beanCIA.getDescripcion()
														.trim() + "'");
							} catch (Exception ex) {
								ex.getMessage();
								System.out.println(ex.getMessage() + " ");
							}
						}
					}
				}
			}
		});*/
		contenedorCenter.add(cboOperacion);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 51, 57, 14);
		contenedorCenter.add(lblUsuario);

		cboUsuario = new MaestroComboBox();
		cboUsuario.setBounds(70, 45, 120, 20);
		contenedorCenter.add(cboUsuario);

		lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(201, 51, 46, 14);
		contenedorCenter.add(lblDesde);

		dtpDesde = new JXDatePicker();
		dtpDesde.setBounds(251, 45, 147, 22);
		Sesion.formateaDatePicker(dtpDesde);
		contenedorCenter.add(dtpDesde);

		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(411, 51, 46, 14);
		contenedorCenter.add(lblHasta);

		dtpHasta = new JXDatePicker();
		dtpHasta.setBounds(466, 45, 147, 22);
		Sesion.formateaDatePicker(dtpHasta);
		contenedorCenter.add(dtpHasta);
		
		lblIncluir = new JLabel("Incluir:");
		lblIncluir.setBounds(623, 51, 57, 14);
		contenedorCenter.add(lblIncluir);
		
		chbIniciarSesion = new JCheckBox("Log In");
		chbIniciarSesion.setBounds(695, 46, 80, 23);
		contenedorCenter.add(chbIniciarSesion);
		
		chbCerrarSesion = new JCheckBox("Log Out");
		chbCerrarSesion.setBounds(780, 46, 80, 23);
		contenedorCenter.add(chbCerrarSesion);
	}

	public void limpiarTabla() {
		tbAuditoria.setModel(dtm = new DefaultTableModel(datos, cabecera));
		TableColumn colFecha = tbAuditoria.getColumn(0);
		TableColumn colUsuario = tbAuditoria.getColumn(1);
		TableColumn colCIA = tbAuditoria.getColumn(2);
		TableColumn colModulo = tbAuditoria.getColumn(3);
		TableColumn colOpcion = tbAuditoria.getColumn(4);
		TableColumn colSubOpcion = tbAuditoria.getColumn(5);
		TableColumn colOperación = tbAuditoria.getColumn(6);
		TableColumn colCantReg = tbAuditoria.getColumn(7);
		TableColumn colRef1 = tbAuditoria.getColumn(8);
		TableColumn colRef2 = tbAuditoria.getColumn(9);
		TableColumn colRef3 = tbAuditoria.getColumn(10);
		TableColumn colRef4 = tbAuditoria.getColumn(11);
		TableColumn colRef5 = tbAuditoria.getColumn(12);
		TableColumn colRef6 = tbAuditoria.getColumn(13);
		TableColumn colRef7 = tbAuditoria.getColumn(14);
		TableColumn colRef8 = tbAuditoria.getColumn(15);
		TableColumn colRef9 = tbAuditoria.getColumn(16);
		TableColumn colRef10 = tbAuditoria.getColumn(17);
		TableColumn colRef11 = tbAuditoria.getColumn(18);
		TableColumn colRef12 = tbAuditoria.getColumn(19);
		TableColumn colRef13 = tbAuditoria.getColumn(20);
		TableColumn colRef14 = tbAuditoria.getColumn(21);
		TableColumn colRef15 = tbAuditoria.getColumn(22);
		TableColumn colRef16 = tbAuditoria.getColumn(23);
		TableColumn colRef17 = tbAuditoria.getColumn(24);
		TableColumn colRef18 = tbAuditoria.getColumn(25);
		TableColumn colRef19 = tbAuditoria.getColumn(26);
		TableColumn colRef20 = tbAuditoria.getColumn(27);
		TableColumn colRef21 = tbAuditoria.getColumn(28);
		TableColumn colRef22 = tbAuditoria.getColumn(29);
		TableColumn colRef23 = tbAuditoria.getColumn(30);
		TableColumn colRef24 = tbAuditoria.getColumn(31);
		TableColumn colRef25 = tbAuditoria.getColumn(32);
		TableColumn colRef26 = tbAuditoria.getColumn(33);
		TableColumn colRef27 = tbAuditoria.getColumn(34);
		TableColumn colRef28 = tbAuditoria.getColumn(35);
		TableColumn colRef29 = tbAuditoria.getColumn(36);
		TableColumn colRef30 = tbAuditoria.getColumn(37);
		TableColumn colRef31 = tbAuditoria.getColumn(38);
		TableColumn colRef32 = tbAuditoria.getColumn(39);
		TableColumn colRef33 = tbAuditoria.getColumn(40);
		TableColumn colRef34 = tbAuditoria.getColumn(41);
		TableColumn colRef35 = tbAuditoria.getColumn(42);
		TableColumn colRef36 = tbAuditoria.getColumn(43);
		TableColumn colRef37 = tbAuditoria.getColumn(44);
		TableColumn colRef38 = tbAuditoria.getColumn(45);
		TableColumn colRef39 = tbAuditoria.getColumn(46);
		TableColumn colRef40 = tbAuditoria.getColumn(47);
		TableColumn colRef41 = tbAuditoria.getColumn(48);
		TableColumn colRef42 = tbAuditoria.getColumn(49);
		TableColumn colRef43 = tbAuditoria.getColumn(50);
		TableColumn colRef44 = tbAuditoria.getColumn(51);
		TableColumn colRef45 = tbAuditoria.getColumn(52);
		TableColumn colRef46 = tbAuditoria.getColumn(53);
		TableColumn colRef47 = tbAuditoria.getColumn(54);
		TableColumn colRef48 = tbAuditoria.getColumn(55);
		TableColumn colRef49 = tbAuditoria.getColumn(56);
		TableColumn colRef50 = tbAuditoria.getColumn(57);

		colFecha.setPreferredWidth(170);
		colUsuario.setPreferredWidth(120);
		colCIA.setPreferredWidth(40);
		colModulo.setPreferredWidth(110);
		colOpcion.setPreferredWidth(120);
		colSubOpcion.setPreferredWidth(150);
		colOperación.setPreferredWidth(100);
		colCantReg.setPreferredWidth(100);
		colRef1.setPreferredWidth(100);
		colRef2.setPreferredWidth(100);
		colRef3.setPreferredWidth(100);
		colRef4.setPreferredWidth(100);
		colRef5.setPreferredWidth(100);
		colRef6.setPreferredWidth(100);
		colRef7.setPreferredWidth(100);
		colRef8.setPreferredWidth(100);
		colRef9.setPreferredWidth(100);
		colRef10.setPreferredWidth(100);
		colRef11.setPreferredWidth(100);
		colRef12.setPreferredWidth(100);
		colRef13.setPreferredWidth(100);
		colRef14.setPreferredWidth(100);
		colRef15.setPreferredWidth(100);
		colRef16.setPreferredWidth(100);
		colRef17.setPreferredWidth(100);
		colRef18.setPreferredWidth(100);
		colRef19.setPreferredWidth(100);
		colRef20.setPreferredWidth(100);
		colRef21.setPreferredWidth(100);
		colRef22.setPreferredWidth(100);
		colRef23.setPreferredWidth(100);
		colRef24.setPreferredWidth(100);
		colRef25.setPreferredWidth(100);
		colRef26.setPreferredWidth(100);
		colRef27.setPreferredWidth(100);
		colRef28.setPreferredWidth(100);
		colRef29.setPreferredWidth(100);
		colRef30.setPreferredWidth(100);
		colRef31.setPreferredWidth(100);
		colRef32.setPreferredWidth(100);
		colRef33.setPreferredWidth(100);
		colRef34.setPreferredWidth(100);
		colRef35.setPreferredWidth(100);
		colRef36.setPreferredWidth(100);
		colRef37.setPreferredWidth(100);
		colRef38.setPreferredWidth(100);
		colRef39.setPreferredWidth(100);
		colRef40.setPreferredWidth(100);
		colRef41.setPreferredWidth(100);
		colRef42.setPreferredWidth(100);
		colRef43.setPreferredWidth(100);
		colRef44.setPreferredWidth(100);
		colRef45.setPreferredWidth(100);
		colRef46.setPreferredWidth(100);
		colRef47.setPreferredWidth(100);
		colRef48.setPreferredWidth(100);
		colRef49.setPreferredWidth(100);
		colRef50.setPreferredWidth(100);
	}

	public void cargarTabla(List<Auditoria> auditorias) {
		limpiarTabla();
		for (Auditoria auditoria : auditorias) {
			Object datos[] = { auditoria.getFecha(), auditoria.getUsu(),
					auditoria.getCIA(), auditoria.getModulo(),
					auditoria.getMenu(), auditoria.getOpcion(),
					auditoria.getTipo_ope(),auditoria.getCant_reg(),
					auditoria.getRef1(),auditoria.getRef2(),
					auditoria.getRef3(),auditoria.getRef4(),
					auditoria.getRef5(),auditoria.getRef6(),
					auditoria.getRef7(),auditoria.getRef8(),
					auditoria.getRef9(),auditoria.getRef10(),
					auditoria.getRef11(),auditoria.getRef12(),
					auditoria.getRef13(),auditoria.getRef14(),
					auditoria.getRef15(),auditoria.getRef16(),
					auditoria.getRef17(),auditoria.getRef18(),
					auditoria.getRef19(),auditoria.getRef20(),
					auditoria.getRef21(),auditoria.getRef22(),
					auditoria.getRef23(),auditoria.getRef24(),
					auditoria.getRef25(),auditoria.getRef26(),
					auditoria.getRef27(),auditoria.getRef28(),
					auditoria.getRef29(),auditoria.getRef30(),
					auditoria.getRef31(),auditoria.getRef32(),
					auditoria.getRef33(),auditoria.getRef34(),
					auditoria.getRef35(),auditoria.getRef36(),
					auditoria.getRef37(),auditoria.getRef38(),
					auditoria.getRef39(),auditoria.getRef40(),
					auditoria.getRef41(),auditoria.getRef42(),
					auditoria.getRef43(),auditoria.getRef44(),
					auditoria.getRef45(),auditoria.getRef46(),
					auditoria.getRef47(),auditoria.getRef48(),
					auditoria.getRef49(),auditoria.getRef50()};
			dtm.addRow(datos);
		}
	}

	public MaestroBean getCIA() {
		MaestroBean bean = null;
		try {
			bean = (MaestroBean) cboCIA.getSelectedItem();
		} catch (Exception e) {
		}
		return bean;
	}

	public MaestroBean getModulo() {
		MaestroBean bean = null;
		try {
			bean = (MaestroBean) cboModulo.getSelectedItem();
		} catch (Exception e) {
		}
		return bean;
	}

	public MaestroBean getMenu() {
		MaestroBean bean = null;
		try {
			bean = (MaestroBean) cboOpcion.getSelectedItem();
		} catch (Exception e) {
		}
		return bean;
	}

	public MaestroBean getOpcion() {
		MaestroBean bean = null;
		try {
			bean = (MaestroBean) cboSubOpcion.getSelectedItem();
		} catch (Exception e) {
		}
		return bean;
	}

	public MaestroBean getOperacion() {
		MaestroBean bean = null;
		try {
			bean = (MaestroBean) cboOperacion.getSelectedItem();
		} catch (Exception e) {
		}
		return bean;
	}

	public MaestroBean getUsuario() {
		MaestroBean bean = null;
		try {
			bean = (MaestroBean) cboUsuario.getSelectedItem();
		} catch (Exception e) {
		}
		return bean;
	}

	public Date getDesde() {
		return dtpDesde.getDate();
	}

	public Date getHasta() {
		return dtpHasta.getDate();
	}

	public boolean getLogIn(){
		return chbIniciarSesion.isSelected();
	}
	
	public boolean getLogOut(){
		return chbCerrarSesion.isSelected();
	}
	
	public JButton getBtnConsultar() {
		return btnConsultar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public void setControlador(SeguridadController controlador) {
		btnConsultar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
	}

	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
}
