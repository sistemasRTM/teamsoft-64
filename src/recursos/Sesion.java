package recursos;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXDatePicker;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import bean.Modulo;
import bean.Opcion;
import bean.Operacion;
import bean.SubOpcion;

public class Sesion {

	// parametros
	public static String ip="";
	public static String impresoraMatricial = "";
	public static String impresoraLaserOTinta = "";
	public static int impcerCampo1 = 0;
	public static double porperCampo0 = 0.0;
	public static double porperCampo1 = 0.0;
	public static double porperCampo2 = 0.0;
	public static double monlimCampo1 = 0.0;
	public static double monlimCampo2 = 0.0;
	public static double pperc = -2;
	public static String serie = "";
	// superponer internal frame
	public static int show = 0;
	// datos principales de la sesion
	public static String usuario;
	public static String contraseña;
	public static String cia;
	public static String ruc = "20100990998";
	public static String nombreCIA;
	public static String sistema = "RTM";
	public static Locale locale;
	// referencia de imagenes
	public static String imgRTM = "/imagenes/rtm.png";
	public static String iconRTM = "/imagenes/rtmIcon.png";
	public static String imgNuevo = "/imagenes/nuevo.png";
	public static String imgModificar = "/imagenes/modificar.png";
	public static String imgEliminar = "/imagenes/eliminar.png";
	public static String imgGuardar = "/imagenes/guardar.png";
	public static String imgCancelar = "/imagenes/cancelar.png";
	public static String imgImprimir = "/imagenes/imprimir.png";
	public static String imgTXT = "/imagenes/txt.png";
	public static String imgExcel = "/imagenes/excel.png";
	public static String imgPDF = "/imagenes/pdf.gif";
	public static String imgSalir = "/imagenes/salir2.png";
	public static String imgConfigurar = "/imagenes/configurar.png";
	public static String imgConectar = "/imagenes/conectar.png";
	// titulo para todas las ventanas(nombre del sistema)
	public static String titulo = "TeamSoft";
	// titulos de ventanas frame
	public static String tfFacturacion = "Facturación";
	public static String tfContabilidad = "Contabilidad";
	public static String tfCuentasXCobrar = "Cuentas Por Cobrar";
	public static String tfInventario = "Inventario";
	public static String tfRRHH = "RR.HH";
	public static String tfADM = "Administración del Sistema";
	public static String tfLogin = "Login";
	public static String tfCIA = "CIA";
	public static String tfGeneral = "Modulos";
	// titulos de ventanas internal frame
	public static String tifAuditoria = "Auditoria";
	public static String tifExcepcionCriterios = "Mantenimiento de Excepciones de Criterios de Comisión Mixta";
	public static String tifCriteriosComisionMixta = "Mantenimiento de Criterios de Comisión Mixta";
	public static String tifGestionPermisos = "Gestión de Permisos";
	public static String tifListaPrecio = "Gestión Lista de Precios";
	public static String tifCliente = "Mantenimiento de Cliente";
	public static String tifOperacion = "Mantenimiento de Operación";
	public static String tifSubOpcion = "Mantenimiento de Sub-Opción";
	public static String tifOpcion = "Mantenimiento de Opción";
	public static String tifModulo = "Mantenimiento de Modulo";
	public static String tifMenu = "Mantenimiento de Menú";
	public static String tifPerfil = "Mantenimiento de Perfil";
	public static String tifComision = "Mantenimiento de Comisiones";
	public static String tifPerfilEmpleado = "Mantenimiento de Perfil x Empleado";
	public static String tifSubOpcion_Operacion = "Detalle Sub-Opción";
	public static String tifOpcion_SubOpcion = "Detalle Opción";
	public static String tifMenu_Opcion = "Detalle Menú";
	public static String tifPerfil_Modulo = "Detalle Perfil";
	public static String tifCalculoComision = "Cálculo de Comisiones";
	public static String tifAbrirPeriodo = "Abrir Periodo";
	public static String tifDetalleCalculoComision = "Detalle Cálculo de Comisiones";
	public static String tifRegistroCompras = "Registro de Compras";
	public static String tifRegistroVentas = "Registro de Ventas";
	public static String tifCertificadoPercepcion = "Certificado de Percepción";
	public static String tifGenerarCertificadoPercepcion = "Generar Certificado de Percepción";
	public static String tifPedidosFacturados = "Imprimir Pedidos Facturados";
	public static String tifLetras = "Imprimir Letras";
	public static String tifMovimientoInventario = "Consultas de Movimientos de Inventario";
	public static String tifEstadoCuenta = "Estado de Cuentas";
	public static String tifVerPercepcion = "Verificar Percepción";
	public static String tifRegistroOperacionDiaria = "Registro de Operaciones Diarias de Productos Fiscalizados";
	public static String tifHistorialComprasCliente = "Historial de Compras por Cliente";
	public static String tifReporteLibroKardex = "Reporte Libro Kardex";
	public static String tifReporteLibroDiario = "Reporte Libro Diario";
	public static String tifReporteLibroMayor = "Reporte Libro Mayor";
	public static String tifReporteClienteSunat = "Reporte de Clientes Sunat";
	public static String tifReporteProveedorSunat = "Reporte de Proveedores Sunat";
	public static String tifReporteArticulosSunat = "Reporte de Articulos Sunat";
	public static String tifDetallePedido = "Detalle de Pedido";
	public static String tifConsultaNotaCredito = "Consulta de Nota de credito";
	public static String tifRegistroPresentacionComercial = "Mantenimiento de Registro de Presentación Comercial";
	public static String tifRegistroOperacion = "Mantenimiento de Registro de Operaciones";
	public static String tifEstablecimientoComercial = "Mantenimiento de Establecimiento Comeracial";
	public static String tifTipoTransaccion = "Mantenimiento de Tipo de Transacción";
	public static String tifAsociacionPresentacionComercial = "Mantenimiento de Asociación de Presentación Comercial";
	public static String tifAsociacionDocumentoTransaccion = "Mantenimiento de Asociación de Documento de Transacción";
	public static String tifAsociacionDocumentoCliente = "Mantenimiento de Asociación de Documento de Cliente";
	public static String tifReImpresion = "Imprimir Documentos";
	public static String tifRegistroMovimiento = "Registrar Movimiento";
	// nombre de bd produccion personal
	public static String bdProd = "PRODTECNI.";
	// datos del vendedor maestro
	// public static String codVendedorMaestro="001";
	// public static double porcentajeAcumulador=0.00095;
	// datos para tener en memoria los permisos segun usuario, mientras la
	// sesion esta activa
	public static List<Modulo> modulos;
	public static List<Opcion> opciones;
	public static List<SubOpcion> subOpciones;
	public static List<Operacion> operaciones;
	//
	private static DecimalFormat formateador;
	private static DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
	public static final SimpleDateFormat FORMATO_FECHA_PERSONAL = new SimpleDateFormat(
			"dd/MM/yyyy");
	public static final SimpleDateFormat FORMATO_FECHA_COMPLETO = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm:ss");
	public static final SimpleDateFormat FORMATO_FECHA_NUMERICA_BD = new SimpleDateFormat("yyyyMMdd");
	// configura el color del sistema
	public static Properties getTheme() {
		Properties blueProps = new Properties();
		blueProps.put("logoString", "RTM");
		/*
		 * blueProps.setProperty("windowTitleForegroundColor", "255 255 255");
		 * blueProps.setProperty("windowTitleBackgroundColor", "1 49 157");
		 * blueProps.setProperty("windowTitleColorLight", "1 40 131");
		 * blueProps.setProperty("windowTitleColorDark", "1 30 97");
		 * blueProps.setProperty("windowBorderColor", "0 24 83");
		 * blueProps.setProperty("windowInactiveTitleForegroundColor",
		 * "255 255 255");
		 * blueProps.setProperty("windowInactiveTitleBackgroundColor",
		 * "55 87 129"); blueProps.setProperty("windowInactiveTitleColorLight",
		 * "1 35 116"); blueProps.setProperty("windowInactiveTitleColorDark",
		 * "1 30 97"); blueProps.setProperty("windowInactiveBorderColor",
		 * "0 24 83"); blueProps.setProperty("backgroundColor", "228 235 243");
		 * blueProps.setProperty("backgroundColorLight", "255 255 255");
		 * blueProps.setProperty("backgroundColorDark", "188 204 226");
		 * blueProps.setProperty("alterBackgroundColor", "208 220 234");
		 * blueProps.setProperty("frameColor", "120 153 197");
		 * blueProps.setProperty("disabledForegroundColor", "96 96 96");
		 * blueProps.setProperty("disabledBackgroundColor", "225 232 240");
		 * blueProps.setProperty("selectionForegroundColor", "255 255 255");
		 * blueProps.setProperty("selectionBackgroundColor", "1 38 124");
		 * blueProps.setProperty("controlBackgroundColor", "228 235 243");
		 * blueProps.setProperty("controlColorLight", "1 40 131");
		 * blueProps.setProperty("controlColorDark", "1 30 98");
		 * blueProps.setProperty("controlDarkShadowColor", "64 100 149");
		 * blueProps.setProperty("buttonColorLight", "255 255 255");
		 * blueProps.setProperty("buttonColorDark", "228 235 243");
		 * blueProps.setProperty("menuBackgroundColor", "255 255 255");
		 * blueProps.setProperty("menuColorDark", "208 220 234");
		 * blueProps.setProperty("menuColorLight", "228 235 243");
		 * blueProps.setProperty("menuSelectionBackgroundColor", "72 51 0");
		 * blueProps.setProperty("toolbarBackgroundColor", "228 235 243");
		 * blueProps.setProperty("toolbarColorLight", "228 235 243");
		 * blueProps.setProperty("toolbarColorDark", "188 204 226");
		 */
		return blueProps;
	}
	
	//cargar parametros
	public static void cargarParametros(){
		SAXBuilder builder = new SAXBuilder();
		String path = System.getProperty("user.dir");
		String archivo = path + "\\config.xml";
		if(path.substring(path.length()-1, path.length()).equals("\\")){
			archivo = path + "config.xml";
		}
		File xmlFileC = new File("c:\\config.xml");
		File xmlFileD = new File("d:\\java\\config.xml");
		File xmlFile = new File(archivo);
		
		//Se crea el documento a traves del archivo
        try {
        	if(xmlFileD.exists()){
        		xmlFile=xmlFileD;
        	}else if(xmlFileC.exists()){
        		xmlFile=xmlFileC;
        	}else if(!xmlFile.exists()){
        		Sesion.mensajeError(null, "Archivo de configuración del sistema no encontrado,\ncomuniquese con el área de sistemas.");
        	}
        	
			Document document = (Document) builder.build(xmlFile);
			 //Se obtiene la raiz 'tables'
	        Element rootNode = document.getRootElement();
	        //Se obtiene la lista de hijos de la raiz 'tables'
	        List<Element> list = rootNode.getChildren( "tabla" );
	        for ( int i = 0; i < list.size(); i++ ){
	        	 Element tabla = (Element) list.get(i);
	        	 //Se obtiene la lista de hijos del tag 'tabla'
		         List<Element> lista_campos = tabla.getChildren();
		         for ( int j = 0; j < lista_campos.size(); j++ ){
		        	 //Se obtiene el elemento 'campo'
		             Element campo = (Element)lista_campos.get( j );
		             //Se obtiene el valor que esta entre los tags '<nombre></nombre>'
		             ip = campo.getChildTextTrim("ip");
		             //Se obtiene el valor que esta entre los tags '<tipo></tipo>'
		             impresoraMatricial = campo.getChildTextTrim("impresoraMatricial");
		             impresoraLaserOTinta = campo.getChildTextTrim("impresoraLaserOTinta");
		         }
	        }
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// cargar imagen
	public static Image cargarImagen(String ruta) {
		return Toolkit.getDefaultToolkit().getImage(
				Sesion.class.getResource(ruta));
	}

	// cargar icono
	public static ImageIcon cargarIcono(String ruta) {
		return new ImageIcon(Sesion.class.getResource(ruta));
	}

	// menseje de error
	public static void mensajeError(Component c, String s) {
		JOptionPane.showMessageDialog(c, s, "Mensaje de Error",
				JOptionPane.ERROR_MESSAGE);
	}

	// mensaje de informacion
	public static void mensajeInformacion(Component c, String s) {
		JOptionPane.showMessageDialog(c, s, "Mensaje de Información",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// mensaje de confirmacion si y no
	public static int mensajeConfirmacion(Component c, String s) {
		return JOptionPane.showConfirmDialog(c, s, "Mensaje de Confirmación",
				JOptionPane.YES_NO_OPTION);
	}

	// mensaje de confirmacion si, no
	@SuppressWarnings("deprecation")
	public static int mensajeConfirmacion2(Component c, String s) {
		int resultado = -1;
		final JOptionPane optionPane = new JOptionPane(s,
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(c),
				"Mensaje de Confirmación", true);
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				if (dialog.isVisible()
						&& (e.getSource() == optionPane)
						&& (prop.equals(JOptionPane.VALUE_PROPERTY) || prop
								.equals(JOptionPane.INPUT_VALUE_PROPERTY))) {
					dialog.dispose();
				}
			}
		});

		dialog.pack();
		dialog.setLocationRelativeTo(c);
		dialog.show();
		int value = ((Integer) optionPane.getValue()).intValue();
		if (value == JOptionPane.YES_OPTION) {
			resultado = 0;
		} else if (value == JOptionPane.NO_OPTION) {
			resultado = 1;
		}

		return resultado;
	}
	
	public static String formatoDecimalVista(double n) {
		NumberFormat numero = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatoNum = (DecimalFormat) numero;
		formatoNum.applyPattern("###,###,###,###,###.###");
		formatoNum.setMaximumFractionDigits(3);
		formatoNum.setMinimumFractionDigits(3);
		return formatoNum.format(n);
	}
	
	public static String formatoDecimalVista_2_digitos(double n) {
		NumberFormat numero = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatoNum = (DecimalFormat) numero;
		formatoNum.applyPattern("###,###,###,###,###.###");
		formatoNum.setMaximumFractionDigits(2);
		formatoNum.setMinimumFractionDigits(2);
		return formatoNum.format(n);
	}
	
	public static String formatoDecimalVista_2(double n) {
		simbolos.setDecimalSeparator('.');
		formateador = new DecimalFormat("###,###,###,###,###.##", simbolos);
		formateador.setMaximumFractionDigits(2);
		formateador.setMinimumFractionDigits(2);
		return formateador.format(n);
	}

	// formatear decimales
	public static String formateaDecimal_2(double numero) {
		simbolos.setDecimalSeparator('.');
		formateador = new DecimalFormat("###.##", simbolos);
		formateador.setMaximumFractionDigits(2);
		formateador.setMinimumFractionDigits(2);
		// formateador.setRoundingMode(roundingMode)
		return formateador.format(numero);
	}

	public static String formateaDecimal_3(double numero) {
		simbolos.setDecimalSeparator('.');
		formateador = new DecimalFormat("###.###", simbolos);
		formateador.setMaximumFractionDigits(3);
		formateador.setMinimumFractionDigits(3);
		return formateador.format(numero);
	}
	
	public static String formateaDecimal_4(double numero) {
		simbolos.setDecimalSeparator('.');
		formateador = new DecimalFormat("###,###,###,###,###.####", simbolos);
		formateador.setMaximumFractionDigits(4);
		formateador.setMinimumFractionDigits(4);
		return formateador.format(numero);
	}

	// metodos para el tratamiento del JXDatePicker y la fecha
	public static void deshabilitarDatePicker(JXDatePicker datePicker) {
		datePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		datePicker.getEditor().setEnabled(false);
		datePicker.setEnabled(false);

	}

	public static void habilitarDatePicker(JXDatePicker datePicker) {
		datePicker.setEnabled(true);
		datePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		datePicker.getEditor().setEnabled(false);
	}

	public static void limpiarDatePicker(JXDatePicker datePicker) {
		datePicker.setFormats(FORMATO_FECHA_PERSONAL);
		datePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		datePicker.getEditor().setEnabled(false);
		datePicker.getEditor().setText("");
	}

	public static void formateaDatePicker(JXDatePicker datePicker) {
		datePicker.setFormats(FORMATO_FECHA_PERSONAL);
		datePicker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		datePicker.getEditor().setEnabled(false);
	}

	public static String formateaFechaVista(Date fecha) {
		Date fechaIngreso = fecha;
		SimpleDateFormat formateaFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fechaModificada = formateaFecha.format(fechaIngreso);
		return fechaModificada;
	}

	public static int convertirFecha(int anos, int meses) {
		return (anos * 12) + meses;
	}

	public static int convertirFecha(int anos, int meses, int dias) {
		return (anos * 360) + (meses * 30) + dias;
	}

	public static Date getFechaActualBD() {
		return Calendar.getInstance().getTime();
	}

	public static String getFechaActualVista() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	public static String getFechaActualVista2() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	public static int fechaActualNumerica(){
		return Integer.parseInt(FORMATO_FECHA_NUMERICA_BD.format(Calendar.getInstance().getTime()));
	}
	
	public static String fechaActualCompleta(){
		return FORMATO_FECHA_COMPLETO.format(Calendar.getInstance().getTime());
	}
	
	public static String fechaParametrizada(int tipo,int cantidad){
		Calendar calendario = Calendar.getInstance();
		calendario.add(tipo, cantidad);
		return formateaFechaVista(calendario.getTime());
	}
	
	public static File elegirDestino() {
		File archivo = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			archivo = fileChooser.getSelectedFile();
		}
		return archivo;
	}
}
