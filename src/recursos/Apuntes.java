package recursos;

public class Apuntes {
	
	public void prueba(){
		/*NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		for (int i = 0; i < devices.length; i++) {
			  //print out its name and description
			  System.out.println(i+": "+devices[i].name + "(" + devices[i].description+")");

			  //print out its datalink name and description
			  System.out.println(" datalink: "+devices[i].datalink_name + "(" + devices[i].datalink_description+")");

			  //print out its MAC address
			  System.out.print(" MAC address:");
			  for (byte b : devices[i].mac_address)
			    System.out.print(Integer.toHexString(b&0xff) + ":");
			  System.out.println();

			  //print out its IP address, subnet mask and broadcast address
			  for (NetworkInterfaceAddress a : devices[i].addresses)
			    System.out.println(" address:"+a.address + " " + a.subnet + " "+ a.broadcast);*/
//	}
	}	
/*
 Pedidos
SELECT PHUSAP,PDARTI,SUBSTRING(PDARTI,1,3)AS ORIGEN,ARTFAM,TTABDF.desesp,ARTSFA,TTABDSF.desesps,ARTEQU,PDNVVA,PDNDS2,PDREF1,AGENOM,ARTDES,PDTDOC,PHPVTA,PDFABO,PHFECP 
FROM TPEDH INNER JOIN TPEDD ON TPEDH.PHPVTA = TPEDD.PDPVTA AND TPEDH.PHNUME = TPEDD.PDNUME 
INNER JOIN TARTI ON TPEDD.PDARTI = TARTI.ARTCOD INNER JOIN TAGEN ON TPEDH.PHUSAP=TAGEN.AGECVE LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp FROM TTABD WHERE tbiden='INFAM') AS TTABDF ON TTABDF.tbespe=artfam
LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp desesps FROM TTABD WHERE tbiden='INSFA') AS TTABDSF ON substring(TTABDSF.tbespe,4,6)=artsfa and substring(TTABDSF.tbespe,1,3)=artfam
WHERE PDFECF>=20130426  AND PDFECF<=20130525  AND PHSITU=05
ORDER BY PHUSAP
42054

Notas de crédito
SELECT NHTDOC,PHUSAP,NCARTI,ORIGEN,ARTFAM,TTABDF.desesp,ARTSFA,TTABDSF.desesps,ARTEQU,NCNVVA * -1 NCNVVA,NCNDS2 * -1 NCNDS2,NCREF1,AGENOM,ARTDES,NHNUME,NHPVTA,NHPVTN,NHFABO
FROM TNCDH INNER JOIN TNCDD ON TNCDH.NHPVTA = TNCDD.NCPVTA AND TNCDH.NHNUME = TNCDD.NCNUME 
LEFT OUTER JOIN table(select PDPVTA,PDTDOC,PDFABO,PDARTI,PHUSAP,SUBSTRING(PDARTI,1,3)AS ORIGEN,AGENOM,ARTFAM,ARTSFA,ARTEQU,ARTDES,count(PHUSAP)  from TPEDH INNER JOIN TPEDD ON TPEDH.PHPVTA = TPEDD.PDPVTA AND TPEDH.PHNUME = TPEDD.PDNUME INNER JOIN TAGEN ON TPEDH.PHUSAP=TAGEN.AGECVE 
 INNER JOIN TARTI ON TPEDD.PDARTI = TARTI.ARTCOD group by PDPVTA,PDTDOC,PDFABO,PDARTI,PHUSAP,SUBSTRING(PDARTI,1,3),AGENOM,ARTFAM,ARTSFA,ARTEQU,ARTDES) as TPEDHP on PDTDOC=NHTDOC AND     PDFABO=NHFABO AND PDPVTA=NHPVTN AND PDARTI=NCARTI 
LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp FROM TTABD WHERE tbiden='INFAM') AS TTABDF ON TTABDF.tbespe=artfam
LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp desesps FROM TTABD WHERE tbiden='INSFA') AS TTABDSF ON substring(TTABDSF.tbespe,4,6)=artsfa and substring(TTABDSF.tbespe,1,3)=artfam
WHERE NHFECP>=20130426 AND NHFECP<=20130525 AND NHSITU<>99
584

//proceso normal = 31/05/2013 7 pm
 
Mon Jun 03 09:43:06 COT 2013
Mon Jun 03 09:44:10 COT 2013
1'.4"
--
Mon Jun 03 09:47:13 COT 2013
Mon Jun 03 09:47:53 COT 2013
50"
--
Mon Jun 03 09:49:45 COT 2013
Mon Jun 03 09:51:03 COT 2013
1'.18"
--
Mon Jun 03 13:23:06 COT 2013
Mon Jun 03 13:26:00 COT 2013
3'
--
Mon Jun 03 21:26:07 COT 2013
Mon Jun 03 21:27:32 COT 2013
1´ 25"

Mon Jun 03 21:31:39 COT 2013
Mon Jun 03 21:32:52 COT 2013
 1´ 13"
 */
}


/* 
try {
	Class.forName("com.ibm.as400.access.AS400JDBCDriver");
	//192.168.1.4
	Connection cn = DriverManager.getConnection("jdbc:as400://192.168.1.4/PRODTECNI", "DParreno", "david2014");
	pstm.executeUpdate();
	pstm.close();
	cn.close();
} catch (ClassNotFoundException ex) {
	System.out.println(ex);
}

//Sesion.locale = Locale.getDefault();

/*
ToolTip.font
ToolTip.borderInactive
ToolTipUI
ToolTip.background
ToolTip.foreground
ToolTip.border
ToolTip.hideAccelerator
ToolTip.backgroundInactive
ToolTip.foregroundInactive
ToolTipManager.enableToolTipMode
 */
//Ejecución de un jar: java -Xms128m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=256m  -jar example.jar
//Runtime runtime = Runtime.getRuntime();
//System.out.println(runtime.freeMemory()+" "+ (runtime.freeMemory()/1024)/1024);
//System.out.println(runtime.maxMemory()+" "+ (runtime.maxMemory()/1024)/1024);
//System.out.println(runtime.totalMemory()+" "+ (runtime.totalMemory()/1024)/1024);
/*AS400 as = new AS400("190.81.163.11","mruiz","s3ri4l");
System.out.println(as.getVersion());
System.out.println(as.getProxyServer() + " - ");
System.out.println(as.SIGNON);*/
//call ma1003/qgpl
//190.81.163.13
//AS400 as = new AS400("192.168.1.4","dparreno","david2014");

/*	AS400 as = new AS400("192.168.1.4","dparreno","david2014");
//as.setGuiAvailable(false);
//try{
	//System.out.println(as.isConnected());
	System.out.println(as.authenticate("dparreno","david2014"));
	System.out.println(as.getSystemName());
	try {
		System.out.println(as.validateSignon("david2015"));
	} catch (AS400SecurityException  e) {
		System.out.println("incorrecto");
	}
	*/
//}catch (Exception e) {

//	}

/*	AS400 as = new AS400("192.168.1.4","dparreno","david2014");
as.setGuiAvailable(false);
CommandCall command = new CommandCall(as);
AS400Message[] messageList=null;
System.out.println(command.run("CALL SPEED407/MA1004 PARM('TC')"));*/
//	as.disconnectAllServices();
/*	Conexion.usuario = "DParreno";
Conexion.password = "david2014";
Connection cn = Conexion.obtenerConexionSpeed407();
CallableStatement cst = cn.prepareCall("CALL MA1004(?)");
cst.setString(1, "TC");
System.out.println(cst.execute());
cst.close();
cn.close();*/
//Empleado e = new Empleado();
//e.setStrUsuario("DParreno");
//e.
//Connection cn = Conexion.obtenerConexion(objEmpleado);
/*
jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
jFrame.setUndecorated(true); 
jFrame.setResizable(false); 
*/


/*
 JDialog modal = new JDialog(FMenuPrincipalAdmin.getInstance(),ModalityType.DOCUMENT_MODAL);
 modal.setSize(200, 200);
 modal.setVisible(true);
 
 --
*/
/*
else if (e.getSource() == mCalculoComisiones.getBtnDetalle()) {

if (pedidosCalculados.size() > 0) {
	if (procesando == false) {
		if (FIDetalleCalculos.getInstance() == null) {
			mDetalleCalculos = FIDetalleCalculos.createInstance();
			mDetalleCalculos.initialize(pedidosXCalcular,
					notaCreditosXCalcular);
			mDetalleCalculos.setVisible(true);
			mDetalleCalculos.requestFocus();
			setVista(mDetalleCalculos);
			mDetalleCalculos.setControlador(this);
			FICalculoComisiones.dpEscritorio.add(mDetalleCalculos,Sesion.show);

		} else {
			Sesion.mensajeError(
					mCalculoComisiones,
					"si desea visualizar el detalle del nuevo calculo cierre \nla ventana de detalle de calculo de comisiones ");
		}
	} else {
		Sesion.mensajeError(mCalculoComisiones,
				"El proceso de calculo aun no termina");
	}
} else
	Sesion.mensajeError(mCalculoComisiones,
			"No exiten detalles que mostrar");

}
*/

/*	PageFormat pf = job.defaultPage();
Paper paper = pf.getPaper();
System.out.println(paper.getHeight() + " " + paper.getWidth());
System.out.println(paper.getImageableHeight() + " " + paper.getImageableWidth());
System.out.println(paper.getImageableX() + " " + paper.getImageableY());*/

/*
 AttributeSet attributeSet = new HashAttributeSet();
					attributeSet.add(new PrinterName("CertificadoPercepcion", null));
					DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
					PrintService[] impresoras = PrintServiceLookup
							.lookupPrintServices(docFormat, attributeSet);
					PrinterJob job = PrinterJob.getPrinterJob();
					job.setPrintService(impresoras[0]);
					PageFormat pf = job.defaultPage();
					Paper paper = new Paper();
					paper.setSize(1000,1000);
					paper.setImageableArea(1, 1, paper.getWidth()-1.0, paper.getHeight()-1.0);
					pf.setPaper(paper);
					job.setPrintable( new PrintObject(),pf);
					job.setJobName("Certificado de Percepción");
					job.print();
 */
//	System.out.println(0.03527848101265822784810126582278 * 14);

/*
FileOutputStream fos = new FileOutputStream("D:/fichero.txt");
ObjectOutputStream out = new ObjectOutputStream(fos);
out.writeObject("192.168.1.4");
out.close();
fos.close();
*/

/*select *
from MI_TABLA
fetch first 10 rows only
*/