package pss.core.winUI.icons;

import java.awt.Dimension;

import pss.JPss;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JFormFiltro;

public class GuiIconos extends JWins {
  public final static long SIZE_DEFA = 0; 
  public final static long SIZE_32x32 = 2; 
  public final static long SIZE_60x60 = 4; 
  public final static long SIZE_120x120 = 8; 
  public final static long SIZE_BW_32x32 = 16; 
  public final static long RESPONSIVE = 32; 

  private static final Dimension DIM_DEFA = new Dimension(16,16);
  private static final Dimension DIM_32x32 = new Dimension(32,32);
  private static final Dimension DIM_60x60 = new Dimension(60,60);
  private static final Dimension DIM_120x120 = new Dimension(120,120);


  private static GuiIconos oGlobal;
  private static JMap<String,String>     oBusiness;
  private static JMap<String,GuiIcon>     oTabla;
  private boolean bIconsLoadedFromDB=false;

  private static boolean bUsesDBIcons = true;
  public static Dimension sizeToDimension(long size) {
  	if (size==SIZE_DEFA) return DIM_DEFA;
  	if (size==SIZE_32x32) return DIM_32x32;
  	if (size==SIZE_60x60) return DIM_60x60;
  	if (size==SIZE_120x120) return DIM_120x120;
  	if (size==SIZE_BW_32x32) return DIM_32x32;
  	return DIM_DEFA;
  }

  public static void setUsesDBIcons(boolean zUsesDBIcons) {
    GuiIconos.bUsesDBIcons = zUsesDBIcons;
  }

  public GuiIconos() throws Exception {
  	
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiIcon.class; }
  @Override
	public int GetNroIcono() throws Exception { return 17; }
  @Override
	public String GetTitle() throws Exception { return "Iconos"; }


  public static synchronized GuiIconos GetGlobal() throws Exception {
    if ( GuiIconos.oGlobal == null ) {
      GuiIconos.oGlobal = new GuiIconos();
      oTabla = JCollectionFactory.createMap(1024);
    }
    if ( bUsesDBIcons && !GuiIconos.oGlobal.bIconsLoadedFromDB ) {
      GuiIconos.oGlobal.loadIconsFromDatabase();
    }
    if (BizUsuario.getUsr()!=null) {
    	String classBuss = BizUsuario.getUsr().getObjBusiness().getClass().getName();
    	if (oBusiness==null) oBusiness = JCollectionFactory.createMap();
    	if (oBusiness.getElement(classBuss)==null) {
    		BizUsuario.getUsr().getObjBusiness().loadIcons(GuiIconos.oGlobal);
    		oBusiness.addElement(classBuss, classBuss);
    	}
    }
    
    return GuiIconos.oGlobal;
  }

  @Override
	public void createActionMap() throws Exception {
    addActionNew(1, "Nuevo Icono" );
  }

  
	@SuppressWarnings("static-access")
	public void readAll() throws Exception {
		//this.SetEstatico(true);

		GuiIconos.GetGlobal().getRecords().setStatic(true);
		
		String filtro=getRecords().getFilterValue("descripcion");
		//this.getRecords().setStatic(true);
		
		JRecords<BizIcon> ico = new JRecords<BizIcon>(BizIcon.class);
		JIterator<GuiIcon> it = GuiIconos.GetGlobal().oTabla.getValueIterator();
		while ( it.hasMoreElements() ) {
			GuiIcon i = it.nextElement();
			if (filtro!=null && i.GetcDato().pDescrip.getValue().toLowerCase().indexOf(filtro.toLowerCase())!=-1)
				ico.addItem(i.GetcDato());
		}
		ico.setStatic(true);
		this.setRecords(ico);
	}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Descripcion",JBaseForm.CHAR, "descripcion","ilike");
		super.ConfigurarFiltros(zFiltros);
	}
  
  private synchronized void loadIconsFromDatabase() throws Exception {

    PssLogger.logWait("Cargando tabla de íconos...");
    try {
    	this.cacheIcon(0,"Anular","Anular.gif",RESPONSIVE,"fa fa-times");
    	this.cacheIcon(1,"arbol","world.png", SIZE_BW_32x32|SIZE_32x32|RESPONSIVE,"fas fa-globe");   
    	this.cacheIcon(2,"Aplicar","aplicar.gif",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-check");  
    	this.cacheIcon(3,"Habilitar Permisos","habilitar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-check-circle");
    	this.cacheIcon(4,"Procesando","indicator.gif");
    	this.cacheIcon(5,"Printer","printer.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-print");
    	this.cacheIcon(6,"SpeedKeys Pay","casa.gif");
    	this.cacheIcon(7,"Cancelar","cancel.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-times");
    	this.cacheIcon(9,"Consultar","consultar.gif",RESPONSIVE,"fa fa-eye");
    	this.cacheIcon(10,"Core","atom.gif",RESPONSIVE,"fa fa-atom");
    	this.cacheIcon(11,"Nuevo Item","mas.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-plus");
    	this.cacheIcon(12,"Eliminar","menos.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-trash");
    	this.cacheIcon(13,"Modificar","modificar2.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-pencil-alt");
    	this.cacheIcon(14,"Mundo","mundo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-globe");
    	this.cacheIcon(15,"Opciones","opciones.gif",RESPONSIVE,"fa fa-check-circle");
    	this.cacheIcon(16,"Tipos de concepto","nuve.gif");
    	this.cacheIcon(17,"Refresh","refresh.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-sync-alt");
    	this.cacheIcon(18,"Salir","salir.gif",RESPONSIVE,"fa fa-times");
    	this.cacheIcon(19,"Tarjeta Credito","visa.gif",RESPONSIVE,"fa fa-cc-visa");
    	this.cacheIcon(20,"Sucursal","sucursal.gif",RESPONSIVE,"fa fa-home");
    	this.cacheIcon(21,"Combustible","combustible.gif",RESPONSIVE,"fa fa-fill");
    	this.cacheIcon(22,"Tarjetas","creditcards.png",RESPONSIVE, "fab fa-cc-visa");
    	this.cacheIcon(23,"Articulo","pizza.gif",RESPONSIVE,"fa fa-codiepie");
    	this.cacheIcon(24,"Seguridad Pos","alarma.GIF",RESPONSIVE,"fa fa-bell");
    	this.cacheIcon(25,"DifPrecio","mp.gif");
    	this.cacheIcon(26,"Seguridad","candado.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-lock");
    	this.cacheIcon(27,"Folder","menu.gif",RESPONSIVE,"fa fa-folder");
    	this.cacheIcon(28,"Tipo Articulo","burger.gif",RESPONSIVE,"fa fa-burn");
    	this.cacheIcon(29,"Articulo Basico","pizza.gif",RESPONSIVE,"fa fa-codipie");
    	this.cacheIcon(30,"Seguridad","aplicar.gif",RESPONSIVE,"fa fa-check-square");
    	this.cacheIcon(31,"Departamentos","visa.gif",RESPONSIVE,"fa fa-credit-card");
    	this.cacheIcon(32,"Promocion","burger.gif",RESPONSIVE,"fa fa-born");
    	this.cacheIcon(33,"Agrupacion Articulos","shopping.gif",RESPONSIVE,"fa fa-home");
    	this.cacheIcon(34,"Lista Diferenciados","Book.gif",RESPONSIVE,"fa fa-book");
    	this.cacheIcon(35,"POS Boton","visa.gif",RESPONSIVE,"fa fa-credit-card");
    	this.cacheIcon(36,"POS Grupo","menu.gif",RESPONSIVE,"fa fa-elementor");
    	this.cacheIcon(37,"POS","visa.gif",RESPONSIVE,"fa fa-credit-card");
    	this.cacheIcon(38,"Usuario ByP","fry.gif",RESPONSIVE,"fa fa-user");
    	this.cacheIcon(39,"Grupos BYP","visa.gif",RESPONSIVE,"fa fa-credit-card");
    	this.cacheIcon(40,"Codigos Claves","visa.gif",RESPONSIVE,"fa fa-credit-card");
    	this.cacheIcon(41,"Monedas","chart_bar.png",RESPONSIVE,"fa fa-chart-pie");
    	this.cacheIcon(42,"Motivo inhabilitacion","visa.gif",RESPONSIVE,"fa fa-credit-card");
    	this.cacheIcon(43,"Roles","seg_rol.gif",RESPONSIVE,"fa fa-check");
    	this.cacheIcon(44,"Operacion","alarma.GIF",RESPONSIVE,"fa fa-bell");
    	this.cacheIcon(45,"Usuario","seg_login.gif",RESPONSIVE,"fa fa-user");
    	this.cacheIcon(46,"Cambio PassWord","cambiopassword.gif",RESPONSIVE,"fa fa-key");
    	this.cacheIcon(47,"Acciones","visa.gif",RESPONSIVE,"fa fa-credit-card");
    	this.cacheIcon(48,"Restringir","inhabilitar.gif", SIZE_BW_32x32|RESPONSIVE, "fas fa-times-circle");
    	this.cacheIcon(49,"Habilitar","habilitar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-check");
    	this.cacheIcon(50,"Cambiar Icono","cambioicono.gif",RESPONSIVE,"fa fa-exchange-alt");
    	this.cacheIcon(51,"Blanqueo PassWord","blanqueopassword.gif",RESPONSIVE,"fa fa-eraser");
    	this.cacheIcon(52,"Ejecutar Proceso (Scheduler)","semverde.gif");
    	this.cacheIcon(53,"Procesos ( Scheduler)","ruleman.gif");
    	this.cacheIcon(54,"Scheduler","reloj.gif",RESPONSIVE,"fa fa-clock");
    	this.cacheIcon(55,"Status (Scheduler)","admiracion.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-exclamation-triangle");
    	this.cacheIcon(56,"Conectividad (Core)","net.gif",RESPONSIVE,"fa fa-connectdevelop");
    	this.cacheIcon(57,"Conn - Cliente (Core)","Telefono1.gif",RESPONSIVE,"fa fa-phone");
    	this.cacheIcon(58,"Conn - Server (Core)","Telefono2.gif",RESPONSIVE,"fa fa-phone-square");
    	this.cacheIcon(59,"Sobre abierto","carta.gif",RESPONSIVE,"far fa-envelope-open");
    	this.cacheIcon(60,"Dispacher (Scheduler)","semverde.gif");
    	this.cacheIcon(61,"Tipo Comando (Scheduler)","Ok.gif",RESPONSIVE,"fa fa-check");
    	this.cacheIcon(62,"Tcp (Core- Conn)","tcp.gif",RESPONSIVE,"fa fa-connectdevelop");
    	this.cacheIcon(63,"Enviar Mensaje (Core - Conn)","email.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"far fa-envelope");
    	this.cacheIcon(64,"Conectar (Core - Conn)","conectar.gif",RESPONSIVE,"fa fa-plug");
    	this.cacheIcon(65,"Desconectr (Core - Conn) ","desconectar.gif",RESPONSIVE,"fa fa-unlock-alt");
    	this.cacheIcon(66,"Frecuencia (Scheduler)","reloj.gif",RESPONSIVE,"fa fa-clock");
    	this.cacheIcon(67,"Modulo Mensaje","carta.gif",RESPONSIVE,"fa fa-envelope-square");
    	this.cacheIcon(68,"Tipo Mensajes","carta.gif",RESPONSIVE,"fa fa-envelope-open");
    	this.cacheIcon(69,"Mensaje","carta2.gif",RESPONSIVE,"fa fa-envelope");
    	this.cacheIcon(70,"Mje Cliente","phone.gif",RESPONSIVE,"fa fa-phone");
    	this.cacheIcon(71,"Mje Servidor","computer.gif",RESPONSIVE,"fa fa-desktop");
    	this.cacheIcon(72,"Paises","world.png",RESPONSIVE,"fa fa-globe");
    	this.cacheIcon(73,"Provincias","Servicio.gif",RESPONSIVE,"fa fa-concierge-bell");
    	this.cacheIcon(74,"Divisiones","Servicio.gif",RESPONSIVE,"fa fa-concierge-bell");
    	this.cacheIcon(75,"Modulo Regiones","flag_usa.gif",RESPONSIVE,"fa fa-flag");
    	this.cacheIcon(76,"Regiones","bolaverde.png",RESPONSIVE,"fa fa-circle text-green");
    	this.cacheIcon(77,"Nodo","CEM_Estacion.gif",RESPONSIVE,"fa fa-warehouse");
    	this.cacheIcon(78,"Componentes","chip.gif",RESPONSIVE,"fa fa-microchip");
    	this.cacheIcon(79,"Habilitar","Concluir.gif",RESPONSIVE,"fa fa-flag");
    	this.cacheIcon(80,"Inhabilitar","anular.gif",RESPONSIVE,"fa fa-ban");
    	this.cacheIcon(81,"Origen ","autoverde.gif",RESPONSIVE,"fa fa-circle text-green");
    	this.cacheIcon(82,"Cambio Pin","cambiopassword.gif",RESPONSIVE,"fa fa-unlock");
    	this.cacheIcon(83,"Rol Jerarquico","jerarquia.gif",RESPONSIVE,"fas fa-sitemap" );
    	this.cacheIcon(84,"multi insert","plusplus.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-cart-plus");
    	this.cacheIcon(85,"Datos","datos.gif");
    	this.cacheIcon(86,"Lista","Lista.gif", RESPONSIVE, "fas fa-file-invoice-dollar");
    	this.cacheIcon(87,"Barras","Lista.gif", RESPONSIVE, "fas fa-bars");
    	this.cacheIcon(88,"Pagar","pagar.png" ,SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-hand-holding-usd");
    	this.cacheIcon(89,"Tarjeta Inhabilitada","admiracion.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-exclamation-circle");
    	this.cacheIcon(90,"Clase","clase.gif",RESPONSIVE,"fa fa-ban");
    	this.cacheIcon(91,"Vincular Region","bolaverde.png");
    	this.cacheIcon(92,"Enviar","enviar.gif");
    	this.cacheIcon(93,"Modulo Adhesion","manos.gif");
    	this.cacheIcon(94,"Unidad de Medida","rtl_impuesto.gif");
    	this.cacheIcon(95,"Tipos de unidades","atom.gif");
    	this.cacheIcon(96,"Unidades","rtl_impuesto.gif");
    	this.cacheIcon(97,"Tipo Origen","autoverde.gif");
    	this.cacheIcon(98,"Next","next.gif");
    	this.cacheIcon(99,"Prev","prev.gif");
    	this.cacheIcon(100,"Medida","medidas.gif");
    	this.cacheIcon(109,"Filtrar","lupa.gif");
    	this.cacheIcon(110,"Stop","stop sign.gif");
    	this.cacheIcon(111,"Ordenar","ordenar2.gif");
    	this.cacheIcon(112,"A Derecha","aderecha.gif",RESPONSIVE,"fa fa-arrow-right");
    	this.cacheIcon(113,"Todos a Derecha","todosaderecha.gif");
    	this.cacheIcon(114,"A Izquierda","aizquierda.gif");
    	this.cacheIcon(115,"Todos a Izquierda","todosaizquierda.gif");
    	this.cacheIcon(116,"Carpeta","carpeta.gif");
    	this.cacheIcon(117,"Consultar 2","consultar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-eye");
    	this.cacheIcon(118,"Semaforo","semaforo.gif");
    	this.cacheIcon(119,"Usuarios","usuarios.gif", RESPONSIVE, "fas fa-user" );
    	this.cacheIcon(120,"Seguridad","seguridad.gif");
    	this.cacheIcon(121,"Generacion Automatica","semaforo.gif");
    	this.cacheIcon(122,"Marconi M","marconi_m.gif");
    	this.cacheIcon(123,"Mundo Animado","amundo.gif");
    	this.cacheIcon(150,"Clave Maestra para Adhesion","cambiopassword.gif");
    	this.cacheIcon(151,"Next","next.gif");
    	this.cacheIcon(152,"Prev","prev.gif");
    	this.cacheIcon(153,"Start","play.gif");
    	this.cacheIcon(154,"Stop","stop.gif");
    	this.cacheIcon(155,"Categorias","byp_admin2.gif");
    	this.cacheIcon(156,"Video","camara.gif");
    	this.cacheIcon(157,"Seleccionar","Ok.gif");
    	this.cacheIcon(158,"Enter Query","consultar.gif");
    	this.cacheIcon(159,"Execute Query","refresh.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-sync-alt");
    	this.cacheIcon(183,"esso","esso_icon.gif");
    	this.cacheIcon(200,"Configuracion","ts_configuracion.gif");
    	this.cacheIcon(201,"Layout Campo","database.gif");
    	this.cacheIcon(202,"Emisores","printer.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(203,"Contribuyente","seg_login.gif");
    	this.cacheIcon(204,"Layout","printer2.gif");
    	this.cacheIcon(205,"Layout Config","VentaPos.gif");
    	this.cacheIcon(206,"Numeraciones","ordenar2.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "sort-numeric-down");
    	this.cacheIcon(207,"Relacion Comp","ticket.gif");
    	this.cacheIcon(208,"Config Tc","tarjetas2.gif");
    	this.cacheIcon(209,"Tipos Comprobantes","ticket.gif");
    	this.cacheIcon(210,"Modulo Comprobante","ticket.gif");
    	this.cacheIcon(300,"Modulo Setup","visa.gif");
    	this.cacheIcon(301,"Componentes","visa.gif");
    	this.cacheIcon(350,"Modulo Tag Service","servicio.gif");
    	this.cacheIcon(351,"Tag Readers","tagverde.gif",RESPONSIVE, "fas fa-tag");
    	
    	this.cacheIcon(352,"Turnos","byp_report.gif");
    	this.cacheIcon(353,"Configuracion TS","configuracion.gif");
    	this.cacheIcon(354,"Info Local","byp_inhabilit.gif");
    	this.cacheIcon(355,"Codigos Habilitacion","seguridad.gif");
    	this.cacheIcon(356,"Tipos Ventas TS","byp_billetera.GIF");
    	this.cacheIcon(357,"Tipos Estados TS","semverde.gif");
    	this.cacheIcon(358,"Tipos Readers","visa.gif");
    	this.cacheIcon(359,"Alarma","byp_alarma.gif");
    	this.cacheIcon(360,"TS_Cierres","visa.gif");
    	this.cacheIcon(361,"TS_Sistemas","visa.gif");
    	this.cacheIcon(362,"TS_Tags","visa.gif");
    	this.cacheIcon(363,"Contingencia","contingencia.gif");
    	this.cacheIcon(364,"Normal","normal.gif");
    	this.cacheIcon(365,"Dañado","dañado.gif");
    	this.cacheIcon(366,"Sistemas","linux.gif");
    	this.cacheIcon(367,"Perdido","perdido.gif");
    	this.cacheIcon(368,"Disponible","carita.gif");
    	this.cacheIcon(369,"Sonido","sonido.gif");
    	this.cacheIcon(370,"NoSonido","nosonido.gif");
    	this.cacheIcon(371,"Inhabilitar","inhabilitar.gif");
    	this.cacheIcon(372,"baja","baja.gif");
    	this.cacheIcon(374,"Habilitar","habilitar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(380,"Todos","mundo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-globe");
    	this.cacheIcon(400,"ePay","tarjetas.gif", RESPONSIVE, "far fa-newspaper" );
    	this.cacheIcon(401,"Tablas","tarjetas.gif",RESPONSIVE, "far fa-newspaper" );
    	this.cacheIcon(402,"Directorio","carpeta.gif");
    	this.cacheIcon(403,"Equipos","rtl_Pos.gif");
    	this.cacheIcon(404,"Grupos Fiscales","grupos.gif");
    	this.cacheIcon(405,"Tipo Contribuyente","rtl_formapago.gif");
    	this.cacheIcon(406,"Lotes","database.gif");
    	this.cacheIcon(407,"Operadores","manos.gif");
    	this.cacheIcon(408,"Importar","importar.gif");
    	this.cacheIcon(409,"Sucursales","autoverde.gif");
    	this.cacheIcon(410,"Exportar","exportar.gif");
    	this.cacheIcon(411,"Pagos","BankCheck.png");
    	this.cacheIcon(412,"Planes de Pago","tarjetas2.gif");
    	this.cacheIcon(413,"Prefijos","estampilla.gif");
    	this.cacheIcon(414,"Tarjeta","visa.gif");
    	this.cacheIcon(415," Departamentos","casa.gif");
    	this.cacheIcon(416,"Colegio","rtl_bancos.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-school");
    	this.cacheIcon(417,"Guardar","diskette.gif");
    	this.cacheIcon(418,"mensaje de display","mensaje.gif");
    	this.cacheIcon(419,"Registraciones","registraciones.gif");
    	this.cacheIcon(420,"Funciones","funciones.gif");
    	this.cacheIcon(421,"Link","connect.png",RESPONSIVE, "fas fa-link"  );
    	this.cacheIcon(422,"Link broken","disconnect.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-unlink");
    	this.cacheIcon(423,"Operaciones pendientes","Fichero-azul.gif");
    	this.cacheIcon(424,"Entregas","page.png");
    	this.cacheIcon(425,"Facturas","billete-pesos.gif", SIZE_BW_32x32);
    	this.cacheIcon(426,"Computadora","computer.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-desktop");
    	this.cacheIcon(430,"rojorojo","rojo-rojo.png");
    	this.cacheIcon(431,"rojoverde","rojo-verde.png");	
    	this.cacheIcon(432,"rojoamarillo","rojo-amarillo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(433,"verdeverde","verde-verde.png");
    	this.cacheIcon(434,"amarillo-verde","amarillo-verde.png");
    	this.cacheIcon(435,"amarriloamarillo","amarillo-amarillo.png");
    	this.cacheIcon(436,"verdeamarillo","verde-amarillo.png");
    	this.cacheIcon(437,"verderojo","verde-rojo.png");//
    	this.cacheIcon(438,"verde","verde-verde.png");
    	this.cacheIcon(439,"amarillo-rojo","amarillo-rojo.png");//
    	this.cacheIcon(440,"Amex","amex.png",SIZE_BW_32x32|RESPONSIVE, "fab fa-cc-amex");
    	this.cacheIcon(500,"ByP","byp.gif");
    	this.cacheIcon(501,"Usuario","byp_usuario.gif");
    	this.cacheIcon(502,"Grupo","byp_grupo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(503,"Tirada buzon","byp_tirada.gif");
    	this.cacheIcon(504,"Billetera","byp_billetera.GIF");
    	this.cacheIcon(505,"Estados Usuario ByP","visa.gif");
    	this.cacheIcon(506,"Venta","byp_venta.gif");
    	this.cacheIcon(507,"Inhabilitacion","byp_inhabilit.gif");
    	this.cacheIcon(508,"Estado","byp_estado.gif");
    	this.cacheIcon(509,"productos","byp_producto.gif");
    	this.cacheIcon(510,"Facturas Impagas","fichero-rojo.gif");
    	this.cacheIcon(511,"configuracion","baja.gif");
    	this.cacheIcon(512,"reportes","byp_report.gif");
    	this.cacheIcon(513,"Habilitar billetera","byp_habilitarbill.gif");
    	this.cacheIcon(514,"Deshabilitar Billetera","byp_deshabilitarbill.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(515,"baja usuario","byp_usuariobaja.gif");
    	this.cacheIcon(516,"Usuario Activo","byp_activo.gif");
    	this.cacheIcon(517,"Usuario Inactivo","byp_inactivo.gif");
    	this.cacheIcon(518,"Usuario Baja","byp_baja.gif");
    	this.cacheIcon(519,"Billetera Activa","byp_billeteraactiva.gif");
    	this.cacheIcon(520,"Billetera baja","byp_billeterabaja.gif");
    	this.cacheIcon(521,"Billetera Inactiva","byp_billeterainactiva.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(522,"Billeter habilitada","byp_billeterahabilitada.gif");
    	this.cacheIcon(523,"word","word.gif", RESPONSIVE, "far fa-file-word");
    	this.cacheIcon(524,"excel","excel.gif", RESPONSIVE, "far fa-file-excel");
    	this.cacheIcon(525,"txt","txt.gif");
    	this.cacheIcon(526,"impresora","printer.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-print");
    	this.cacheIcon(555,"impresoras","printer.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-print");
    	this.cacheIcon(600,"Modulo MPC","mp.gif");
    	this.cacheIcon(601,"ATOS","semaforo.gif");
    	this.cacheIcon(700,"Modulo Retail","rtl_Pos.gif");
    	this.cacheIcon(701,"Detener Proceso","anular.gif");
    	this.cacheIcon(702,"Reprocesar","cancelar.gif");
    	this.cacheIcon(703,"Forzar Ok","aplicar.gif");
    	this.cacheIcon(704,"Separador","separator.gif");
    	this.cacheIcon(705,"Botonera","Botonera.gif");
    	this.cacheIcon(706,"Grupo de Botones","Carpetas.gif");
    	this.cacheIcon(707,"Back","back.gif");
    	this.cacheIcon(708,"Formas Pago","creditcards_conf.png");
    	this.cacheIcon(709,"Pos Pago","rtl_pospago.gif");
    	this.cacheIcon(710,"Anular Item Venta Pos","anular.gif");
    	this.cacheIcon(711,"Incrementar","mas.gif");
    	this.cacheIcon(712,"Decrementar","menos.gif");
    	this.cacheIcon(713,"Modulo Stock","jerarquia.gif", RESPONSIVE,"fab fa-stack-overflow");
    	this.cacheIcon(714,"Rechazos","cancelar.gif");
    	this.cacheIcon(715,"Combo 1","burger.gif");
    	this.cacheIcon(716,"calculadora","calculator.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-calculator");
    	this.cacheIcon(717,"Depositos","fichero.gif");
    	this.cacheIcon(718,"Tipos de Conceptos","Ok.gif");
    	this.cacheIcon(719,"Articulo","paste_plain.png",RESPONSIVE, "fab fa-product-hunt" );
    	this.cacheIcon(720,"Vender","cart.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-shopping-cart");
    	this.cacheIcon(721,"Lista de Precios","notepad.gif");
    	this.cacheIcon(722,"Departamento","casa.gif");
    	this.cacheIcon(723,"Combustible","combustibles.gif",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(724,"Clipboardb","modificar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-clipboard");
    	this.cacheIcon(725,"Categoria","carpeta.gif");
    	this.cacheIcon(726,"Categorias","carpetas.gif");
    	this.cacheIcon(727,"Tiendas","rtl_bancos.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(728,"Zonas","bolaverde.png");
    	this.cacheIcon(729,"Venta Pos","VentaPos.gif");
    	this.cacheIcon(730,"Lista Precios","rtl_listaprecios.gif");
    	this.cacheIcon(731,"Pos","rtl_Pos.gif", RESPONSIVE, "fas fa-digital-tachograph");
    	this.cacheIcon(732,"Anular Pos","anular.gif");
    	this.cacheIcon(733,"Concluir Pos","Concluir.gif");
    	this.cacheIcon(734,"Comenzar Pos","comenzar.gif");
    	this.cacheIcon(735,"Logout","logout.gif");
    	this.cacheIcon(743,"Grupo Fiscal","atom.gif");
    	this.cacheIcon(744,"botonera de pagos","byp_billeteraactiva2.gif");
    	this.cacheIcon(745,"Male","personas.gif", RESPONSIVE, "fas fa-male");
    	this.cacheIcon(746,"Impuestos","Impuesto.png", SIZE_BW_32x32|RESPONSIVE,"fas fa-stamp");
    	this.cacheIcon(747,"Modulo Proveedores","byp_grupo.GIF");
    	this.cacheIcon(748,"Proveedores","byp_grupo.gif");
    	this.cacheIcon(749,"Mov Cuenta Corriente","documents.png", SIZE_BW_32x32|RESPONSIVE,"fa fa-pen-square");
    	this.cacheIcon(750,"Movimiento Cta.Cte.","billete-pesos.gif" ,SIZE_BW_32x32);
    	this.cacheIcon(751,"Cuenta Moneda","byp_billeteraactiva.gif");
    	this.cacheIcon(752,"Codigos de Movimiento","Ok.gif");
    	this.cacheIcon(753,"Tags Maestros","tagrojo.gif");
    	this.cacheIcon(754,"Regalos","regalo.gif");
    	this.cacheIcon(755,"Regalos","regalo.gif");
    	this.cacheIcon(756,"Mensajes Regalos","carta2.gif");
    	this.cacheIcon(757,"Tags","tagverde.gif");
    	this.cacheIcon(758,"Servicios","telefono1.gif");
    	this.cacheIcon(759,"Modulo Tags","tagrojo.gif");
    	this.cacheIcon(760,"Regalo Entregado","regalook.gif");
    	this.cacheIcon(761,"Personas","byp_grupo.gif");
    	this.cacheIcon(762,"Telefono","conectar.gif");
    	this.cacheIcon(763,"Domicilio","rtl_bancos.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(764,"Cui","carta.gif");
    	this.cacheIcon(765,"Documento","book.gif");
    	this.cacheIcon(766,"CEM","cem.gif");
    	this.cacheIcon(767,"Cem_Config","ruleman.gif");
    	this.cacheIcon(768,"Cem_dispositivos","dispositivos.gif");
    	this.cacheIcon(769,"EESS","car1.gif");
    	this.cacheIcon(770,"Admin_surt","admin_surt.gif");
    	this.cacheIcon(771,"Grupo_Surt","grupo_surt.gif");
    	this.cacheIcon(772,"Surtidor","surtidor.gif", RESPONSIVE, "fas fa-gas-pump");
    	this.cacheIcon(773,"surt_crin","surtidor_crin.gif");
    	this.cacheIcon(774,"grupo_surt","grupo_surt.gif");
    	this.cacheIcon(775,"grupo_surt_crin","grupo_surt_crin.gif");
    	this.cacheIcon(776,"surt_crin","grupo_surt_crin.gif");
    	this.cacheIcon(777,"Connect","connect.gif");
    	this.cacheIcon(778,"grupo_surt_crin","grupo_surt_crin.gif");
    	this.cacheIcon(779,"grupo_surt","grupo_surt.gif");
    	this.cacheIcon(780,"crin","crin.gif");
    	this.cacheIcon(781,"crinds","crins.gif");
    	this.cacheIcon(782,"crind_config","crin_config.gif");
    	this.cacheIcon(783,"Codigos de actividad","devices.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-file-download" );
    	this.cacheIcon(784,"config_crin","crin_config.gif");
    	this.cacheIcon(785,"crin","crin.gif");
    	this.cacheIcon(786,"Dispacher","rtl_Pos.gif");
    	this.cacheIcon(787,"medio de pago","byp_billetera3.gif");
    	this.cacheIcon(788,"Lote Cerrado","lote_cerrado.gif");
    	this.cacheIcon(789,"Precio Historico","ByP_BilleteraActiva.GIF");
    	this.cacheIcon(790,"Configuracion BO","config_bo.gif");
    	this.cacheIcon(800,"Offline Monitoreo","bolaroja.png");
    	this.cacheIcon(801,"Online Monitoreo","bolaverde.png");
    	this.cacheIcon(802,"Connect Monitoreo","bolaamarilla.png");
    	this.cacheIcon(803,"Modulo Monitoreo","telefono1.gif");
    	this.cacheIcon(804,"bola negra","bolanegra.gif");
    	this.cacheIcon(805,"bola azul","bolanaranja.png");
    	this.cacheIcon(806,"Female", "", RESPONSIVE, "fas fa-female");
    	this.cacheIcon(807,"Manito derecha", "", RESPONSIVE, "far fa-hand-point-right");
    	this.cacheIcon(808,"doble derecha", "", RESPONSIVE, "fas fa-angle-double-right");
    	this.cacheIcon(809,"doble Izquierda", "", RESPONSIVE, "fas fa-angle-double-left");
    	this.cacheIcon(853,"Cierre Historico Caja","historico.gif");
    	this.cacheIcon(854,"Movimiento Caja","rtl_listaprecios.gif");
    	this.cacheIcon(855,"Apertura Caja","Concluir.gif");
    	this.cacheIcon(856,"Cierre Caja","baja.gif");
    	this.cacheIcon(857,"Valores de Caja","atom.gif");
    	this.cacheIcon(858,"Cod Movs de Caja","Ok.gif");
    	this.cacheIcon(859,"Caja Cerrada","byp_billeterainactiva2.gif");
    	this.cacheIcon(860,"Surtidor","surtidor2.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-gas-pump");
    	this.cacheIcon(861,"Tanque","ball.gif");
    	this.cacheIcon(862,"Producto comb","combustible.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(863,"Manguera Surtidor","tagrojo.gif");
    	this.cacheIcon(864,"Surtidores","cem_grupos.gif");
    	this.cacheIcon(865,"Blender","cem_surt_crind.gif");
    	this.cacheIcon(866,"Despachos Surtidores","rtl_carrito.gif");
    	this.cacheIcon(867,"Autorizar Surtidor","aplicar.gif");
    	this.cacheIcon(868,"Parar Surtidor","anular.gif");
    	this.cacheIcon(869,"Categoria","carpeta.gif");
    	this.cacheIcon(870,"Niveles Categorias","carpetas.gif");
    	this.cacheIcon(890,"Controlador","CEM_devices.gif");
    	this.cacheIcon(891,"Despachos GNC","rtl_carrito.gif");
    	this.cacheIcon(892,"Surtidores GNC","cem_surt_crind.gif");
    	this.cacheIcon(893,"GNC Tipo Despacho","rtl_formapago.gif");
    	this.cacheIcon(894,"Protocolos GNC","telefono2.gif");
    	this.cacheIcon(895,"GNC Precios","byp_billeteraactiva.gif");
    	this.cacheIcon(896,"GNC Turno Abierto","Concluir.gif");
    	this.cacheIcon(897,"GNC Turno Cerrado","baja.gif");
    	this.cacheIcon(898,"Historico de PRecios","byp_billetera3.gif");
    	this.cacheIcon(899,"Tipos","tipos.gif");
    	this.cacheIcon(900,"Terminal_Verfone","Term_ico.gif",RESPONSIVE, "fa fa-mobile-alt");
    	this.cacheIcon(901,"Fichero","fichero.gif");
    	this.cacheIcon(902,"foguiman","foguiman.gif");
    	this.cacheIcon(904,"Elemento de Skin","table_lightning.png");
    	this.cacheIcon(905,"Skin","skin.gif");
    	this.cacheIcon(906,"Skins","skins.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(907,"Guardar Skin","save_skin.gif");
    	this.cacheIcon(908,"Dial Up","dial_up.gif");
    	this.cacheIcon(909,"Tools","tools.gif",RESPONSIVE,"fas fa-tools"); 
    	this.cacheIcon(910,"Alerta","alert.gif");
    	this.cacheIcon(911,"yingyang","yinyang.gif");
    	this.cacheIcon(912,"keys","key.gif",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-key");
    	this.cacheIcon(913,"Dinamita","dynamite.gif");
    	this.cacheIcon(914,"Consignatario","skins.gif");
    	this.cacheIcon(915,"Tirada Buzon","byp_billeteraactiva2.gif");
    	this.cacheIcon(916,"Carpeta","directoryicon.gif");
    	this.cacheIcon(917,"Archivo","fileicon.gif");
    	this.cacheIcon(918,"Computadora","computericon.gif");
    	this.cacheIcon(919,"Disco duro","harddriveicon.gif");
    	this.cacheIcon(920,"Diskette","floppydriveicon.gif");
    	this.cacheIcon(921,"Nueva carpeta","newfoldericon.gif");
    	this.cacheIcon(922,"Carpeta arriba","upfoldericon.gif");
    	this.cacheIcon(923,"Carpeta inicio","homefoldericon.gif");
    	this.cacheIcon(924,"Vista detalles","detailsviewicon.gif");
    	this.cacheIcon(925,"Vista lista","listviewicon.gif");
    	this.cacheIcon(926,"HTML","html.gif");
    	this.cacheIcon(927,"HTML dinámico","dhtml.gif");
    	this.cacheIcon(928,"PDF","pdf.gif", RESPONSIVE, "far fa-file-pdf");
    	this.cacheIcon(929,"Ir al siguiente","aderecha.gif");
    	this.cacheIcon(930,"Ir al anterior","aizquierda.gif");
    	this.cacheIcon(931,"Ir al final","todosaderecha.gif");
    	this.cacheIcon(932,"Ir al comienzo","todosaizquierda.gif");
    	this.cacheIcon(933,"Zoom in","zoomin.gif");
    	this.cacheIcon(934,"Zoom out","zoomout.gif");
    	this.cacheIcon(935,"impresora con opciones","printerwo.gif");
    	this.cacheIcon(936,"disk violeta","icondisk1.gif");
    	this.cacheIcon(937,"disk violeta oscuro","icondisk2.gif");
    	this.cacheIcon(938,"disk violeta claro","icondisk3.gif");
    	this.cacheIcon(939,"disk rojo ","icondisk4.gif");
    	this.cacheIcon(940,"disk naranja","icondisk5.gif" , RESPONSIVE, "fas fa-clipboard-check");
    	this.cacheIcon(941,"disk azul","icondisk6.gif");
    	this.cacheIcon(942,"disk azul claro","icondisk7.gif");
    	this.cacheIcon(943,"disk verde","icondisk8.gif");
    	this.cacheIcon(944,"disk verde claro","icondisk9.gif");
    	this.cacheIcon(945,"death star","deathstar.gif");
    	this.cacheIcon(946,"death star 2","deathstar2.gif");
    	this.cacheIcon(947,"darth vader","darthvader.gif");
    	this.cacheIcon(948,"cosmos","cosmo.gif");
    	this.cacheIcon(949,"module","module.gif");
    	this.cacheIcon(950,"brasil","brazil.gif");
    	this.cacheIcon(951,"estado: pendiente","status_pending.gif");
    	this.cacheIcon(952,"estado: esperando input","status_waitinput.gif");
    	this.cacheIcon(953,"estado: esperando confirmación","status_waitconf.gif");
    	this.cacheIcon(954,"estado: cancelado","status_cancelled.gif");
    	this.cacheIcon(955,"estado: ejecutando","status_running.gif");
    	this.cacheIcon(956,"estado: terminado","status_done.gif");
    	this.cacheIcon(957,"estado: terminado con error","status_failed.gif");
    	this.cacheIcon(958,"setup: ir a próximo paso","setup_go_next.gif");
    	this.cacheIcon(959,"setup: ir a paso anteior","setup_go_back.gif");
    	this.cacheIcon(960,"setup: terminar","setup_finish.gif");
    	this.cacheIcon(961,"estado: salteado","status_skipped.gif");
    	this.cacheIcon(962,"copiar","copy.gif");
    	this.cacheIcon(963,"copiar todos","copy_all.gif");
    	this.cacheIcon(964,"borrar","remove.gif");
    	this.cacheIcon(965,"borrar todos","remove_all.gif");
    	this.cacheIcon(966,"mensaje de info","info_message.gif");
    	this.cacheIcon(967,"mensaje de error","error_message.gif", RESPONSIVE, "far fa-times");
    	this.cacheIcon(968,"mensaje de debug","debug_message.gif");
    	this.cacheIcon(969,"mensaje SQL","debug_sql_message.gif");
    	this.cacheIcon(970,"posicionar al final","text_pane_autoscroll.gif");
    	this.cacheIcon(971,"agregar columna","add_column.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(972,"eliminar columna","drop_column.gif");
    	this.cacheIcon(973,"alterar tabla","alter_table.gif");
    	this.cacheIcon(974,"agregar índice","add_index.gif");
    	this.cacheIcon(975,"eliminar índice","drop_index.gif");
    	this.cacheIcon(976,"crear tabla","create_table.gif");
    	this.cacheIcon(977,"eliminar tabla","drop_table.gif");
    	this.cacheIcon(978,"truncar tabla","truncate_table.gif");
    	this.cacheIcon(979,"agregar datos","add_data.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(980,"borrar datos","delete_data.gif");
    	this.cacheIcon(981,"exportar","export.gif");
    	this.cacheIcon(982,"exportar todo","export_all.gif");
    	this.cacheIcon(983,"z-up","z-up.gif");
    	this.cacheIcon(984,"Key Customization","keycustomization.gif");
    	this.cacheIcon(985,"total","box.gif");
    	this.cacheIcon(986,"hobbies","hobbies.gif");
    	this.cacheIcon(987,"estadoCivil","skins.gif");
    	this.cacheIcon(988,"eClub Work Position","eclubworkposition.gif");
    	this.cacheIcon(989,"eClub Transaccion","eclubtransaction.gif", RESPONSIVE, "far fa-file-alt");
    	this.cacheIcon(990,"eClub","eclub.gif");
    	this.cacheIcon(991,"eClub Commercial Region","eclubcommercialregion.gif");
    	this.cacheIcon(992,"eClub Client","eclubclient.gif");
    	this.cacheIcon(993,"Unidad de CD","cdrom.gif");
    	this.cacheIcon(994,"Unidad Zip","zipdrive.gif");
    	this.cacheIcon(995,"eClubShop","eclubshop.gif");
    	this.cacheIcon(996,"eClubPremio","eclubpremio.gif");
    	this.cacheIcon(997,"eClubActivar","eclubactivar.gif");
    	this.cacheIcon(998,"eClubDesActivar","eclubdesactivar.gif");
    	this.cacheIcon(999,"eClubProducto","eclubproducto.gif");
    	this.cacheIcon(1000,"core.nodosremotos.flags","atom.gif");
    	this.cacheIcon(1001,"pump.historicalprice","historico.gif");
    	this.cacheIcon(1002,"pump.productflagrelation","atom.gif");
    	this.cacheIcon(1003,"core.nodosremotos.nodeflag","atom.gif");
    	this.cacheIcon(1004,"pump.historicalprice.queries.formnodedateproducts","atom.gif");
    	this.cacheIcon(1005,"eClubGetUltima","Tarjetas2.gif");
    	this.cacheIcon(1006,"Speedkeys de preset","pump.preset.speedkeys.gif");
    	this.cacheIcon(1012,"Bugs","bug.png",RESPONSIVE, "fa fa-bug");
    	this.cacheIcon(1043,"Inicializar Terminal","Desconectar.gif");
    	this.cacheIcon(1100,"articulos","paste_plain.png",RESPONSIVE, "fab fa-product-hunt" );
    	this.cacheIcon(1101,"Chile","Chile.gif");
    	this.cacheIcon(1102,"Empresa","empresa.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(1103,"Shop","shop.gif", RESPONSIVE, "fas fa-store");
    	this.cacheIcon(1104,"Turism","turism.gif");
    	this.cacheIcon(1105,"Airport","airport.gif");
    	this.cacheIcon(1106,"Confirmar Viaje","Concluir.gif");
    	this.cacheIcon(1107,"Delete Sale","trash.gif");
    	this.cacheIcon(1108,"crucero","cruise.gif");
    	this.cacheIcon(1110,"Summer","summer.gif");
    	this.cacheIcon(1111,"Saturno","saturno.gif",RESPONSIVE,"fa fa-cogs");
    	this.cacheIcon(1112,"Herramientas","herramientas.gif",RESPONSIVE,"fas fa-cogs");
    	this.cacheIcon(1113,"Avion","plane.gif");
    	this.cacheIcon(1114,"Colombia","colombia.gif");
    	this.cacheIcon(1115,"Venezuela","venezuela.gif");
    	this.cacheIcon(1116,"Camion","lorry.png",RESPONSIVE, "fas fa-truck" );
    	this.cacheIcon(1117,"Bolsa Dinero","bolsa_dinero.gif", RESPONSIVE, "fas fa-piggy-bank");
    	this.cacheIcon(1118,"expand","max.png");
    	this.cacheIcon(1119,"unexpand","min.png");
    	this.cacheIcon(1120,"up","up.png");
    	this.cacheIcon(1121,"down","down.png");
    	this.cacheIcon(2001,"tramo aéreo","modificar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(2002,"aéreo","aeroplane.gif");
    	this.cacheIcon(2003,"air_ticket","modificar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(2004,"pnr","pnr.gif");
    	this.cacheIcon(2005,"Arbol","mundo_explorador.gif");
    	
    	this.cacheIcon(2100,"Restar","building.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-minus");
    	
    	this.cacheIcon(5000,"Building","building.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"far fa-building");
    	this.cacheIcon(5001,"World","world.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(5002,"home","house.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(5003,"coins","coins.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-coins");
    	this.cacheIcon(5004,"tools","wrench.png",RESPONSIVE, "fas fa-wrench");
    	this.cacheIcon(5005,"server","server.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-cogs");
    	this.cacheIcon(5006,"server database","server_database.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-server");
    	this.cacheIcon(5007,"cog","cog.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-cog");
    	this.cacheIcon(5008,"table multiple","table_multiple.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,	"fas fa-images");
    	this.cacheIcon(5009,"transmit","transmit.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-satellite-dish");
    	this.cacheIcon(5010,"credit cards","creditcards.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fab fa-cc-visa");
    	this.cacheIcon(5011,"bricks","bricks.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(5012,"char line","chart_line.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-chart-line");
    	this.cacheIcon(5013,"credit card conf","creditcards_conf.png");
    	this.cacheIcon(5014,"list number","text_list_numbers.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-list-ol");
    	this.cacheIcon(5015,"shield","shield.png", RESPONSIVE,"fas fa-shield-alt");
    	this.cacheIcon(5016,"proto","database_add.png");
    	this.cacheIcon(5017,"basket","basket.png", RESPONSIVE, "fas fa-gas-pump");
    	this.cacheIcon(5018,"basket add","basket_add.png", RESPONSIVE,"fas fa-cart-plus");
    	this.cacheIcon(5019,"calculator","calculator.png");
    	this.cacheIcon(5020,"calculator add","calculator_add.png", RESPONSIVE, "fas fa-tablet-alt");
    	this.cacheIcon(5021,"application terminal","application_xp_terminal.png");
    	this.cacheIcon(5022,"book","book.png");
    	this.cacheIcon(5023,"lock_edit","lock_edit.png", RESPONSIVE, "fas fa-lock-open");
    	this.cacheIcon(5024,"arrow left","arrow_left.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-arrow-left");
    	this.cacheIcon(5025,"weather_lightning.png","weather_lightning.png");
    	this.cacheIcon(5026,"house add","house_add.png", RESPONSIVE,"fas fa-cart-plus");
    	this.cacheIcon(5027,"house delete","house_delete.png");
    	this.cacheIcon(5028,"accept","accept.png", RESPONSIVE,"fab fa-rev");
    	this.cacheIcon(5029,"cancel","cancel.png", RESPONSIVE, "fas fa-ban");
    	this.cacheIcon(5030,"application home","application_home.png", RESPONSIVE, "fas fa-city");
    	this.cacheIcon(5031,"surtidor","surtidor.gif", RESPONSIVE,"fas fa-eye-dropper");
    	this.cacheIcon(5032,"bandera verde","flag_green.png");
    	this.cacheIcon(5033,"bandera amarilla","flag_yellow.png");
    	this.cacheIcon(5034,"bandera roja","flag_red.png");
    	this.cacheIcon(5035,"taza cafe","cup.png");
    	this.cacheIcon(5036,"arrow circle","arrow_rotate_clockwise.png", RESPONSIVE, "fas fa-exchange-alt");
    	this.cacheIcon(5037,"bandera rosa","flag_pink.png");
    	this.cacheIcon(5038,"cd","cd.png", RESPONSIVE, "fas fa-compact-disc");
    	this.cacheIcon(5039,"chart bar","chart_bar.png", RESPONSIVE,"fas fa-chart-line");
    	this.cacheIcon(5040,"money","money.png", RESPONSIVE, "fas fa-money-check-alt");
    	this.cacheIcon(5041,"money dollar","money_dollar.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-dollar-sign");
    	this.cacheIcon(5042,"page edit","page_edit.png");
    	this.cacheIcon(5043,"clock","clock.png", RESPONSIVE,"far fa-clock");
    	this.cacheIcon(5044,"help","help.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-question-circle");
    	this.cacheIcon(5045,"script go","script_go.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-marker");
    	this.cacheIcon(5046,"libro abierto","database_go.png", RESPONSIVE,"fas fa-book-open");
    	this.cacheIcon(5047,"database edit","database_edit.png", RESPONSIVE, "far fa-edit");
    	this.cacheIcon(5048,"user delete","user_delete.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(5049,"key delete","key_delete.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(5050,"key go","key_go.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(5051,"key","key.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-key");
    	this.cacheIcon(5052,"report","report.png", RESPONSIVE, "far fa-file-alt");
    	this.cacheIcon(5053,"pie chart","chart_pie.png" , RESPONSIVE, "fas fa-chart-pie");
    	this.cacheIcon(5054,"house group","house_group.png");
    	this.cacheIcon(5055,"database go red","database_go_red.png");
    	this.cacheIcon(5056,"script go red","script_go_red.png");
    	this.cacheIcon(5057,"truck link","lorry_link.png", RESPONSIVE, "fas fa-truck-loading");
    	this.cacheIcon(5058,"user edit","user_edit.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-user-edit");
    	this.cacheIcon(5059,"user add","user_add.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-user-plus");
      this.cacheIcon(5060,"logo change","logo_change.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-random");
    	this.cacheIcon(5061,"bandera azul","flag_blue.png");
    	this.cacheIcon(5062,"bandera naranja","flag_orange.png");
    	this.cacheIcon(5063,"bandera lila","flag_purple.png");
    	this.cacheIcon(5064,"ie","ie.png");
    	this.cacheIcon(5065,"firefox","firefox.png");
    	this.cacheIcon(5066,"acrobat","acrobat.png");
    	this.cacheIcon(5067,"user gray","user_gray.png", RESPONSIVE, "fas fa-user-astronaut");
    	this.cacheIcon(5068,"gota","gota.png");
    	this.cacheIcon(5069,"tanque","tanque.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fa fa-bong");
    	this.cacheIcon(5070,"truck error","lorry_error.png");
    	this.cacheIcon(5071,"email error","email_error.png");
    	this.cacheIcon(5072,"warning","warning.png", RESPONSIVE, "fas fa-exclamation-triangle" );
    	this.cacheIcon(5073,"warn_delete","warn_delete.png");
    	this.cacheIcon(5074,"warn_go","warn_go.png", RESPONSIVE, "fas fa-exclamation-circle");
    	this.cacheIcon(5075,"camion tachado","lorry_tachado.png");
    	this.cacheIcon(5076,"user red","user_red.png", RESPONSIVE, "fas fa-user-tie" );
    	this.cacheIcon(5077,"warn grouped","application_error.png");
     	this.cacheIcon(5078,"clients","clients.png", RESPONSIVE, "fas fa-user-friends");
     	this.cacheIcon(5079,"suppliers","suppliers.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-user-tie");
     	this.cacheIcon(5080,"coins_add","coins_add.png");
     	this.cacheIcon(5081,"coins_delete","coins_delete.png");
     	this.cacheIcon(5082,"coins_go","coins_go.png");
     	this.cacheIcon(5083,"coins_edit","coins_edit.png");
     	this.cacheIcon(5084,"package_add","package_add.png");
     	this.cacheIcon(5085,"package_delete","package_delete.png");
     	this.cacheIcon(5086,"package_green","package_green.png");
     	this.cacheIcon(5087,"package_link","package_link.png");
     	this.cacheIcon(5088,"package_go","package_go.png");
     	this.cacheIcon(5089,"package_disk","package_disk.png");
     	this.cacheIcon(5090,"package_put","package_put.png");
     	this.cacheIcon(5091,"package_remove","package_remove.png");
     	this.cacheIcon(5092,"page_remove_green","page_remove_green.png");
     	this.cacheIcon(5093,"page_remove_yellow","page_remove_yellow.png");
     	this.cacheIcon(5094,"page_put_magenta","page_put_magenta.png");
     	this.cacheIcon(5095,"page_put_red","page_put_red.png");
     	this.cacheIcon(5096,"page_gear","page_gear.png");
     	this.cacheIcon(5097,"paste_plain","paste_plain.png",RESPONSIVE, "fab fa-product-hunt" );
    	this.cacheIcon(5098,"bug_remove","bug_remove.png");
     	this.cacheIcon(5099,"bug_put","bug_put.png", RESPONSIVE, "fas fa-code-branch");
     	this.cacheIcon(5100,"bug_star","bug_star.png");
     	this.cacheIcon(5101,"money_link","money_link.png", RESPONSIVE, "fas fa-money-bill-wave");
     	this.cacheIcon(5102,"table_lightning","table_lightning.png");
     	this.cacheIcon(5103,"sitemap","sitemap.png");
     	this.cacheIcon(5104,"date","date.png",SIZE_BW_32x32);
     	this.cacheIcon(5105,"date_lock","date_lock.png");
     	this.cacheIcon(5106,"date_go","date_go.png");
     	this.cacheIcon(5107,"brick","brick.png");
     	this.cacheIcon(5108,"link_error","link_error.png");
     	this.cacheIcon(5109,"clients_error","clients_error.png");
     	this.cacheIcon(5110,"monitor","monitor.png");
     	this.cacheIcon(5111,"briefcase","briefcase.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-briefcase");
     	this.cacheIcon(5112,"supplier_link","supplier_link.png");
     	this.cacheIcon(5113,"zoom","zoom.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-search");
     	this.cacheIcon(5114,"page add","page_add.png");
     	this.cacheIcon(5115,"red truck slash","lorry_red_tachado.png");
     	this.cacheIcon(5116,"disk multiple","disk_multiple.png", RESPONSIVE, "fas fa-upload" );
     	this.cacheIcon(5117,"briefcase open","briefcase_open.png");
     	this.cacheIcon(5118,"briefcase add","briefcase_add.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-briefcase-medical");
     	this.cacheIcon(5119,"briefcase open","briefcase_open.png", RESPONSIVE, "fas fa-stopwatch");
     	this.cacheIcon(5120,"Hotel","Hotel.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-bed");
     	this.cacheIcon(5121,"Crucero","Crucero.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-anchor");
     	this.cacheIcon(5122,"Auto","Auto.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-car");
     	this.cacheIcon(5123,"Ferry","Ferry.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-ship");
     	this.cacheIcon(5124,"Varios","Varios.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fab fa-microsoft");
     	this.cacheIcon(5125,"Paquete","Paquete.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-gift");
     	this.cacheIcon(5126,"Excursiones","Excursiones.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-parachute-box");
     	this.cacheIcon(5127,"Tren","Tren.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-train");
     	this.cacheIcon(5128,"Transporte","Transporte.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-shuttle-van");
     	this.cacheIcon(5129,"Aereo","Aereo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-plane");
     	this.cacheIcon(5130,"Pasaje Aereo","AereoPasaje.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(5131,"Lamparita","Concepto.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-lightbulb");
     	this.cacheIcon(5132,"Bloques","Bloques.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-cubes");
     	this.cacheIcon(5133,"Dinero","MoneyBag.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-coins");
    	this.cacheIcon(5134,"Prestador","prestador.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-user-cog");
    	this.cacheIcon(5135,"Auto","car.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(5136,"Prohibido","prohibido.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-thumbs-down");
    	this.cacheIcon(5137,"Copy","tramite_copy.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-clone");
    	this.cacheIcon(5138,"Banco","banco.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(5143,"Central Pasajes","central_pasajes.png");
    	this.cacheIcon(5144,"plataforma 10","plataforma10.png");
   
    	this.cacheIcon(5500,"Campo extra","tramite_copy.png", RESPONSIVE,"fas fa-folder-plus");
    	this.cacheIcon(5510,"user slash","user-slash.png", RESPONSIVE,"fas fa-user-slash");
     	this.cacheIcon(5511,"user add","user-add.png", RESPONSIVE,"fas fa-user-plus");
     	this.cacheIcon(5512,"user secret","user-secret.png", RESPONSIVE,"fas fa-user-secret");
     	this.cacheIcon(5513,"user erase","user-erase.png", RESPONSIVE,"fas fa-eraser");
     	this.cacheIcon(5514,"mail bulk","mail-bulk.png", RESPONSIVE,"fas fa-mail-bulk");
    	this.cacheIcon(5515,"internet bulk","internet-bulk.png", RESPONSIVE,"fab fa-internet-explorer");
  
     	this.cacheIcon(5600,"address card","internet-bulk.png", RESPONSIVE,"far fa-address-card");
     	this.cacheIcon(5601,"tasks","internet-bulk.png", RESPONSIVE,"fas fa-tasks");
     	this.cacheIcon(5602,"tasks","internet-bulk.png", RESPONSIVE,"fas fa-users");
     	this.cacheIcon(5603,"user-clock","internet-bulk.png", RESPONSIVE,"fas fa-user-clock");
     	this.cacheIcon(5604,"business-clock","internet-bulk.png", RESPONSIVE,"fas fa-business-time");
     	this.cacheIcon(5605,"briefcase","internet-bulk.png", RESPONSIVE,"fas fa-briefcase");
      	this.cacheIcon(5606,"video","internet-bulk.png", RESPONSIVE,"fas fa-video");
      	this.cacheIcon(5607,"Tipo Comando (Scheduler)","Ok.gif",RESPONSIVE,"fa fa-check green");
       	this.cacheIcon(5610,"Lista","lista", RESPONSIVE, "fas fa-list-ul");


    	this.cacheIcon(6000,"siti","siti.png");
    	this.cacheIcon(6010,"Chanquito","basket_put.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-cart-plus");
    	this.cacheIcon(6011,"bandeja Salida","basket_remove.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fa fa-inbox");
    	this.cacheIcon(6012,"Cheque","cheque.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-money-check-alt");
    	this.cacheIcon(6013,"Alcancia","alcancia.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(6014,"reciclar","reciclar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(6015,"Posnet","posnet.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-fax");
     	this.cacheIcon(6016,"Cheque 3ros","cheque.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-money-check");
    	this.cacheIcon(6017,"Cheque3ros rech","cheque3ros_rechazo.png",SIZE_BW_32x32);
    	this.cacheIcon(6018,"Cheque3ros devol","cheque3ros_devol.png",SIZE_BW_32x32);
    	this.cacheIcon(6019,"Cheque3ros deposito","cheque3ros_deposito.png",SIZE_BW_32x32);
    	this.cacheIcon(6020,"Cheque3ros ventanilla","cheque3ros_ventanilla.png",SIZE_BW_32x32);
    	this.cacheIcon(6021,"Cambiar Ctz","cambiar_pesos.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-search-dollar");
    	this.cacheIcon(6022,"Candado Abierto","candado_open.png",SIZE_BW_32x32);
     	this.cacheIcon(6023,"Pasajero","pasajero.png", SIZE_BW_32x32);
     	this.cacheIcon(6024,"Empleados","empleados.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-user-tie");
     	this.cacheIcon(6025,"Flechas Internas 1","flechas_internas1.png", SIZE_BW_32x32|RESPONSIVE,"fa fa-long-arrow-alt-right fa-2x");
     	this.cacheIcon(6026,"Flechas Internas 2","flechas_internas2.png", SIZE_BW_32x32|RESPONSIVE,"fa fa-long-arrow-alt-left fa-2x");
     	this.cacheIcon(6027,"Ajuste", "ajuste.png", SIZE_BW_32x32);
     	this.cacheIcon(6028,"Boleto", "boleto.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-ticket-alt");
     	this.cacheIcon(6029,"Regalo","PaquetePropio.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-gift");
     	this.cacheIcon(6030,"Circular","dinero_circular.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-sync-alt");
    	this.cacheIcon(6031,"Banco","banco.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-university");
    	this.cacheIcon(6032,"Alcancia Pig","banco_in.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-piggy-bank");
    	this.cacheIcon(6033,"Banco OUT","banco_out.png",SIZE_BW_32x32);
    	this.cacheIcon(6034,"Banco TRANSF","banco_transf.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-arrows-alt-h");
    	this.cacheIcon(6035,"Doble Flecha","banco_mov.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-exchange-alt");
    	this.cacheIcon(6036,"Dinero","dinero.png",SIZE_BW_32x32);
    	this.cacheIcon(6037,"Caja Trans","caja_transf.png",SIZE_BW_32x32);
    	this.cacheIcon(6038,"Dinero Mano","dinero_enmano.png",SIZE_BW_32x32);
    	this.cacheIcon(6039,"Dinero Apilado","dinero_apilado.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-funnel-dollar");
    	this.cacheIcon(6040,"Presupuesto","presupuesto.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-file-signature");
    	this.cacheIcon(6041,"Grafico Dinero","chart_money.png",SIZE_BW_32x32);
    	this.cacheIcon(6042,"Caja Registradora","caja_registradora.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-cash-register");
    	this.cacheIcon(6043,"Interfaz","Interfaz.png",SIZE_BW_32x32|RESPONSIVE, "fas fa-bolt");
    	this.cacheIcon(6044,"passport","passport.png",RESPONSIVE, "fas fa-passport");
    	this.cacheIcon(6045,"Carta","carta.png");
    	this.cacheIcon(6047,"Sobre Abierto","sobre_abierto.png");
    	this.cacheIcon(6048,"Sobre Ok","sobre_ok.png");
    	this.cacheIcon(6049,"Sobre Anulado","sobre_anulado.png");
    	this.cacheIcon(6050,"Sobre Modificado","sobre_modificado.png");
    	this.cacheIcon(6051,"Sobre Respondido","sobre_respondido.png");
    	this.cacheIcon(6052,"Sobre ReRespondido","sobre_abierto_ok.png");
    	this.cacheIcon(6053,"Sobre Rojo","sobre_rojo.png");
    	this.cacheIcon(6054,"Sobre Rojo Abierto","sobre_rojo_open.png");
    	this.cacheIcon(6056,"Sobre Conversacion","sobre_conversacion.png", SIZE_BW_32x32);
    	this.cacheIcon(6057,"Sobre Modificar","sobre_modificar.png", SIZE_BW_32x32);
    	this.cacheIcon(6058,"Sobre Mas","sobre_mas.png", SIZE_BW_32x32);
    	this.cacheIcon(6070,"Cuotas","cuotas.png", SIZE_BW_32x32);
    	this.cacheIcon(6071,"Cuota Cruz","cuota_cruz.png");
    	this.cacheIcon(6072,"Chequera","chequera.png", RESPONSIVE,"fas fa-money-check");
    	this.cacheIcon(6073,"Chequera Roja","chequera_roja.png");
    	this.cacheIcon(6074,"Chequera Verde","chequera_verde.png");
    	this.cacheIcon(6075,"Admiracion","event.high.gif");
    	this.cacheIcon(6076,"Medico","cruz.png", SIZE_BW_32x32|RESPONSIVE, "fas fa-plus-circle");
    	this.cacheIcon(6077,"Etiqueta","", RESPONSIVE, "fas fa-tags");
    	this.cacheIcon(6078,"Flecha der en circulo","", RESPONSIVE, "far fa-arrow-alt-circle-right");
    	this.cacheIcon(6079,"Diaspora","", RESPONSIVE, "fab fa-diaspora");
    	this.cacheIcon(6080,"Circulo","", RESPONSIVE, "fas fa-circle");
    	this.cacheIcon(6081,"Caret down","", RESPONSIVE, "fas fa-caret-down");
    	this.cacheIcon(6082,"Despeque","", RESPONSIVE, "fas fa-plane-departure");
    	this.cacheIcon(6083,"Arrivo","", RESPONSIVE, "fas fa-plane-arrival");
    	this.cacheIcon(6084,"Caminante","", RESPONSIVE, "fas fa-walking");
    	this.cacheIcon(6085,"marca","", RESPONSIVE, "fas fa-bookmark");
    	this.cacheIcon(6086,"note plus","", RESPONSIVE, "fas fa-notes-medical");
    	this.cacheIcon(6087,"auto de lado","", RESPONSIVE, "fas fa-car-side");
    	this.cacheIcon(6088,"angulo abajo","", RESPONSIVE, "fas fa-angle-down");
    	this.cacheIcon(6089,"angulo arriba","", RESPONSIVE, "fas fa-angle-up");
    	this.cacheIcon(6090,"bebe","", RESPONSIVE, "fas fa-baby");
    	this.cacheIcon(6091,"nene","", RESPONSIVE, "fas fa-child");
    	this.cacheIcon(6092,"usuarios","",RESPONSIVE, "fas fa-users-cog");
    	this.cacheIcon(6093,"wifi","",RESPONSIVE, "fas fa-wifi");
    	this.cacheIcon(6094,"file-image","",RESPONSIVE, "far fa-file-image");
    	this.cacheIcon(6096,"menos circulo","", RESPONSIVE, "fas fa-minus-circle");
    	this.cacheIcon(6098,"recibo", "", RESPONSIVE, "fas fa-receipt");
    	this.cacheIcon(6099,"history", "", RESPONSIVE, 	"fas fa-history");
    	this.cacheIcon(6100,"paper clip", "", RESPONSIVE, "fas fa-paperclip");
    	this.cacheIcon(6101,"puntos suspensivos", "", RESPONSIVE, "fas fa-ellipsis-h");
    	this.cacheIcon(6102,"Sobre abierto text", "", RESPONSIVE, "fas fa-envelope-open-text");
    	this.cacheIcon(6103,"Reply", "", RESPONSIVE, "fas fa-reply");
    	this.cacheIcon(6104,"Ojo cruzado", "", RESPONSIVE, "fas fa-eye-slash");
    	this.cacheIcon(6105,"Angulo doble abajo", "", RESPONSIVE, "fa fa-angle-double-down");
    	this.cacheIcon(6106,"Angulo doble arriba", "", RESPONSIVE, "fa fa-angle-double-up");
    	this.cacheIcon(6107,"Ok", "", RESPONSIVE, "far fa-thumbs-up");
    	this.cacheIcon(6108,"Almacen", "", RESPONSIVE, "fa-warehouse-full");
    	this.cacheIcon(6109,"Ingresar", "", RESPONSIVE, "fas fa-sign-in-alt");
    	this.cacheIcon(6110,"Salir", "", RESPONSIVE, "fas fa-sign-out-alt");

    	this.cacheIcon(7001,"Bandera Arg", "Flag_Argentina.png");
    	this.cacheIcon(7002,"Bandera USA","Flag_EstadosUnidos.png");
    	this.cacheIcon(7003,"Bandera Brasil","Flag_Brasil.png");
    	this.cacheIcon(7004,"Bandera Mexico","Flag_Mexico.png");
    	this.cacheIcon(7005,"Bandera Euro","Flag_Euro.png");
    	this.cacheIcon(7006,"Bandera UK","Flag_ReinoUnido.png");
    	this.cacheIcon(7007,"Bandera China","Flag_China.png");
    	this.cacheIcon(7008,"Bandera Taiwan","Flag_Taiwan.png");
    	this.cacheIcon(7009,"Bandera Sudafrica","Flag_Sudafrica.png");
    	this.cacheIcon(7010,"Bandera Uruguay","Flag_Uruguay.png");
    	/* iconos con varios tamaños*/
    	this.cacheIcon(10000,"Documento","document.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-book" );      
    	this.cacheIcon(10001,"Agenda","agenda.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10002,"Pizarra","pizarra.png",SIZE_60x60);
    	this.cacheIcon(10003,"DocTilde","documento_tildes.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|RESPONSIVE, "fas fa-file-signature");
    	this.cacheIcon(10004,"CarpAmar","carpeta_amarilla.png",SIZE_60x60);
    	this.cacheIcon(10005,"Flujo","flujoDoc.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60);
    	this.cacheIcon(10006,"FlujoRegla","flujoRegla.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|RESPONSIVE, "fa fa-broadcast-tower");
    	this.cacheIcon(10007,"FlujoReglaDetalle","flujoReglaDetalle.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60);
    	this.cacheIcon(10008,"FlujoReglaDetalle2","flujoReglaDetalle.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60);
    	this.cacheIcon(10009,"Tramite","tramite.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|RESPONSIVE, "fa fa-tasks");
    	this.cacheIcon(10010,"TramiteUrg","tramUrgente.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60);
    	this.cacheIcon(10011,"TramiteMed","tramMed.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60);
    	this.cacheIcon(10012,"TramiteNor","tramNormal.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|RESPONSIVE, "fas fa-file-invoice");
    	this.cacheIcon(10013,"Cliente","cliente.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|RESPONSIVE, "fas fa-user-alt");
    	this.cacheIcon(10014,"Hogar","home.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10015,"Correr","run.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10016,"Salir","exit.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|RESPONSIVE, "fas fa-power-off");
    	this.cacheIcon(10017,"Hitos","hitos.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60);
    	this.cacheIcon(10018,"Tipos","lists.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60);
    	this.cacheIcon(10019,"Corriente","current.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60);
    	this.cacheIcon(10020,"Documentos","documents.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-copy");
    	this.cacheIcon(10021,"Equipo Trabajo","teamwork.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10022,"Caja con cosas","cajaconcosas.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10023,"Piezas","piezas.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-cog");
    	this.cacheIcon(10024,"Formulario","formulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10025,"CarpetaOk","carpeta_ok.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10026,"Correo","correo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-envelope");
    	this.cacheIcon(10027,"Cuaderno y lapiz","cuaderno_lapiz.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-pen-square");
    	this.cacheIcon(10028,"Floppy","floppy.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-save");
    	this.cacheIcon(10029,"Cuaderno calculo","graficos_calculo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10030,"Hoja calculo","hojacalculo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10031,"Balanza","justicia.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-balance-scale");
    	this.cacheIcon(10032,"Mundo boton","mundoboton.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-globe");
    	this.cacheIcon(10033,"OK","ok.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10034,"Organizador","organizador.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10035,"Pinza y cortacable","pinzaycortacable.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-cogs");
    	this.cacheIcon(10036,"Tipos documento","tiposDocs.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-binoculars");
    	this.cacheIcon(10037,"Zonas","zonas.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10038,"Caja vacia","caja_vacia.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10039,"Caja cerrada","caja_cerrada.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10040,"Doc vacio","documento_vacio.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10041,"Caja tachada","caja_tachada.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10042,"Doc vigilado","doc_vigilado.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-binoculars");
    	this.cacheIcon(10043,"Doc no vigilado","doc_no_vigilado.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10044,"Doc persona","mi_doc.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10045,"Doc sector","doc_sector.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-money-bill-alt");
    	this.cacheIcon(10046,"Doc link","doc_link.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10047,"Doc Lupa","doc_lupa.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-search-plus");
    	this.cacheIcon(10048,"scanner","scanner.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10049,"Doc 2","documento2.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10050,"Impresora","fileprint.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10051,"Largavista","largavista.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-binoculars");
    	this.cacheIcon(10052,"Scanner ok","ok_scanner.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10053,"Scanner Fallo","no_scanner.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-code");
    	this.cacheIcon(10054,"XML","xml.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-code");
    	this.cacheIcon(10055,"Mundo verde","mundoverde.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-globe");
    	this.cacheIcon(10056,"BSP","bsp.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-list-alt");
    	this.cacheIcon(10057,"Palm","palm.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-tablet");
    	this.cacheIcon(10058,"fichero_big","fichero_big.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-database");
    	this.cacheIcon(10059,"runner","runner.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10060,"Balanza Inclinada derecha", "", RESPONSIVE, "fas fa-balance-scale-right");

    	this.cacheIcon(10061,"Ordenamiento","ordenamiento.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-sort-alpha-down");
    	this.cacheIcon(10062,"SQL","type_sql.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(10063,"Filtros","filtert.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-filter");
     	this.cacheIcon(10064,"Campos","widget_doc.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(10065,"fullscreen","fullscreen.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(10066,"nofullscreen","nofullscreen.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10067,"cajacorreo","caja_correo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10068,"cajaespera","caja_espera.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10069,"canasta","canasta.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(10070,"surtidor","surtidor.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(10071,"surtidor_add","surtidor_add.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(10072,"edificio","edificio.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-store");
     	this.cacheIcon(10073,"edificio_add","edificio_add.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(10074,"persona_ident","persona_ident.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-user");
     	this.cacheIcon(10075,"succion","succion.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10076,"BI","type_bi.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10077,"MASBI","masbi.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10078,"compartir","compartir.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(10079,"add","type_bi.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-plus-circle fa-5x");
    	this.cacheIcon(10080,"addfolder","masbi.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-folder-plus fa-5x");
    	this.cacheIcon(10081,"Egresado","egresado.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-graduation-cap");

    	this.cacheIcon(10082,"Calendario","calendar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-calendar-alt");      
    	this.cacheIcon(10083,"Calendario Check","calendar-check.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-calendar-check");      
    	this.cacheIcon(10084,"Buffer","xx",RESPONSIVE, "fab fa-buffer");      
    	this.cacheIcon(10085,"eye","", RESPONSIVE, "fas fa-eye");
    	this.cacheIcon(10086,"eye slash","", RESPONSIVE, "fas fa-eye-slash");
    	
    	this.cacheIcon(15000,"persona_ident","persona_ident.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15001,"hidefilters","hidefilters.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15002,"showfilters","showfilters.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15003,"showhidefilters","showfilter.png");
    	this.cacheIcon(15004,"add_formulario","addformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-file-medical");
    	this.cacheIcon(15005,"del_formulario","delformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(15006,"hipoteca","hipoteca.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(15007,"embargo","embargo.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(15008,"propietario","propietario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-address-card");
    	this.cacheIcon(15009,"edit formulario","editformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-file-signature");
    	this.cacheIcon(15010,"ok formulario","okformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15011,"alert formulario","alertformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15012,"Cond formulario","condformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15013,"Prev formulario","prevformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15014,"Prev.Cond. formulario","prevcondformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15015,"Cancelar formulario","cancelformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15016,"Reversar formulario","revformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15017,"Prorroga formulario","prorrogaformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15018,"Lupa formulario","lupaformulario.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fas fa-search");
    	this.cacheIcon(15019,"Rogatoria","rogatoria.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(15020,"Habitaciones","habitaciones.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(15021,"Posicion","ubicar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15022,"addFavorito","fav.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15023,"desFavorito","unfav.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);

    	this.cacheIcon(15024,"graficos","lineal2d.png");
    	this.cacheIcon(15025,"graficos","multilineal2d.png");
    	this.cacheIcon(15026,"graficos","area2d.png");
    	this.cacheIcon(15027,"graficos","starea2d.png");
    	this.cacheIcon(15028,"graficos","multiaread2d.png");
    	this.cacheIcon(15029,"graficos","barra2d.png");
    	this.cacheIcon(15030,"graficos","stbarra2d.png");
    	this.cacheIcon(15031,"graficos","multibarra2d.png");
    	this.cacheIcon(15032,"graficos","barra3d.png");
    	this.cacheIcon(15033,"graficos","barra2dlinea.png");
    	this.cacheIcon(15034,"graficos","barra3dlinea.png");
    	this.cacheIcon(15035,"graficos","stbarra3d.png");
    	this.cacheIcon(15036,"graficos","xytemporal.png");
    	this.cacheIcon(15037,"graficos","multibarra3d.png");
    	this.cacheIcon(15038,"graficos","pie2d.png");
    	this.cacheIcon(15039,"graficos","pie3d.png");
    	this.cacheIcon(15040,"graficos","dona2d.png");
    	this.cacheIcon(15041,"graficos","render3d.png");
    	this.cacheIcon(15042,"graficos","relojes.png");
    	this.cacheIcon(15043,"graficos","arcosglobo.png");
    	this.cacheIcon(15044,"graficos","mapacirculos.png");
    	this.cacheIcon(15045,"graficos","mapalineas.png");
    	this.cacheIcon(15046,"graficos","multipie.png");
    	this.cacheIcon(15047,"graficos","temporal.png");
    	this.cacheIcon(15048,"graficos","newbarra.png");
    	this.cacheIcon(15049,"graficos","bullet.png");
    	this.cacheIcon(15050,"Favorito","estrella.png");
    	
    	this.cacheIcon(15051,"Folder","folder.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15052,"Folder open","folderopen.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "far fa-folder-open");
    	this.cacheIcon(15053,"Tree header","treeheader.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15054,"Tree hoja","leafdoc.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
    	this.cacheIcon(15055,"funcion","function.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120);
     	this.cacheIcon(15056,"subir","subir.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-arrow-up");
     	this.cacheIcon(15057,"bajar","bajar.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE, "fas fa-arrow-down");
     	this.cacheIcon(15058,"izq","left.png",SIZE_BW_32x32);
     	this.cacheIcon(15059,"der","right.png",SIZE_BW_32x32);
       	
    	
    	this.cacheIcon(15060,"logo aerolineas","aerolineas.png");
    	this.cacheIcon(15061,"logo iberia","iberia.png");
    	this.cacheIcon(15062,"logo airfrance","airfrance.png");
    	this.cacheIcon(15063,"logo delta","delta.png");
    	this.cacheIcon(15064,"logo mexicana","mexicana.png");
    	this.cacheIcon(15065,"logo lan","lan.png");
    	this.cacheIcon(15066,"logo aa","aa.png");
    	this.cacheIcon(15067,"logo klm","klm.png");
    	this.cacheIcon(15068,"logo alitalia","alitalia.png");
    	this.cacheIcon(15069,"logo copa","copa.png");
    	this.cacheIcon(15070,"logo emirates","emirates.png");
    	this.cacheIcon(15071,"logo tame","tame.png");
    	this.cacheIcon(15072,"logo airfrnaceklmalitalia","airfrnaceklmalitalia.png");
    	this.cacheIcon(15073,"logo aeromexico","aeromexico.png");
    	this.cacheIcon(15074,"logo united","united.png");
    	this.cacheIcon(15075,"logo lufthansa","lufthansa.png");
    	this.cacheIcon(15076,"logo air canada","aircanada.png");
    	this.cacheIcon(15077,"logo avianca","avianca.png");
    	this.cacheIcon(15078,"logo air europa","aireuropa.png");
    	this.cacheIcon(15079,"logo british","british.png");
    	this.cacheIcon(15080,"logo finnair","finnair.png");
    	this.cacheIcon(15081,"logo openskies","openskies.png");
    	this.cacheIcon(15082,"logo jab","jab.png");
    	this.cacheIcon(15083,"logo myaustrian","myaustrian.png");
 	  	this.cacheIcon(15084,"logo swiss","swiss.png");
 	  	this.cacheIcon(15085,"logo lufthansa swiss austrian","luftswissmyaustrian.png");
 	  	this.cacheIcon(15086,"logo turkish","turkish.png");
 	  	this.cacheIcon(15087,"logo boa","boa.png");
 	  	this.cacheIcon(15088,"logo qantas","qantas.png");
 	  	this.cacheIcon(15089,"logo qatar","qatar.png");
 	  	this.cacheIcon(15090,"logo airfranceklm","airfranceklm.png");
 	  	this.cacheIcon(15091,"logo peruvian","peruvian.png");
 	  	this.cacheIcon(15092,"logo lcperu","lcperu.png");
 	  	this.cacheIcon(15093,"logo plusultra","plusultra.png");
 	  	this.cacheIcon(15094,"logo starperu","starperu.png");
 	  	this.cacheIcon(15095,"logo sky","sky.png");
 	  	this.cacheIcon(15096,"logo gol","gol.png");
 	  	this.cacheIcon(15097,"logo airnz","airnz.png");
 	  	this.cacheIcon(15098,"logo southwest","southwest.png");
	  	this.cacheIcon(15099,"logo interjet","interjet.png");
	  	 
    
    	
 	  	
    	this.cacheIcon(15100,"graficos","areanew.png");
    	this.cacheIcon(15101,"graficos","rosca.png");
	
    	this.cacheIcon(15102,"dm","fondodm.jpg");
    	this.cacheIcon(15103,"ct","fondoct.jpg");
    	this.cacheIcon(15104,"dm2","fondodm2.jpg");
    	this.cacheIcon(15105,"ct2","fondoct2.jpg");
    	this.cacheIcon(15110,"Liquid graph","liquid.png");
    	this.cacheIcon(15111,"graficos","radialbar.png");

    	
    	this.cacheIcon(15200,"graficos","dato.png");
    	this.cacheIcon(15201,"graficos","semireloj.png");
    	this.cacheIcon(15202,"graficos","tortai.png");
    	this.cacheIcon(15203,"graficos","tipolista.png");
    	this.cacheIcon(15204,"graficos","listaagrup.png");
    	this.cacheIcon(15205,"graficos","listamatrix.png");
    	this.cacheIcon(15210,"graficos","2filas.png");
    	this.cacheIcon(15211,"graficos","3columnas.png");
    	this.cacheIcon(15212,"graficos","2columnas1fila.png");
    	this.cacheIcon(15213,"graficos","freedistr.png");
    	this.cacheIcon(15214,"graficos","espiral.png");
    	
	  	this.cacheIcon(15300,"logo chinasouth","chinasouth.png");
    	
    	this.cacheIcon(15500,"sort abajo","", RESPONSIVE, "fa fa-sort-down");
    	this.cacheIcon(15501,"sort arriba","", RESPONSIVE, "fa fa-sort-up");
    	this.cacheIcon(15502,"sort","", RESPONSIVE, "fa fa-sort");
    	this.cacheIcon(15503,"sort2","", RESPONSIVE, "fa fa-sliders-h");

    	
     	this.cacheIcon(16000,"ESssmapaok","eessmapaok.png",SIZE_32x32|SIZE_60x60|SIZE_120x120);
        this.cacheIcon(16001,"ESssmapawarning","eessmapawarning.png",SIZE_32x32|SIZE_60x60|SIZE_120x120);
        this.cacheIcon(16002,"ESssmapaerror","eessmapaerror.png",SIZE_32x32|SIZE_60x60|SIZE_120x120);
        this.cacheIcon(16003,"ESssmapadesc","eessmapadesconect.png",SIZE_32x32|SIZE_60x60|SIZE_120x120);
                   	
  
      	this.cacheIcon(17000,"Eliminar rojo","menos.png",SIZE_BW_32x32|SIZE_32x32|SIZE_60x60|SIZE_120x120|RESPONSIVE,"fa fa-trash red");
      	this.cacheIcon(50000,"support","chatbutton1.jpg");

    	

    	PssLogger.logInfo("Tabla de íconos cargada");
      bIconsLoadedFromDB = true;
    } catch (Exception ex) {
      PssLogger.logError(ex, "No se pudo cargar tabla de íconos");
      throw ex;
    }

  }


  public static String makeFileName(long size, String zFile) {
  	if (size==RESPONSIVE) return zFile;
  	if (size==SIZE_DEFA) return zFile;
  	if (size==SIZE_32x32) return "32x32/"+zFile;
  	if (size==SIZE_BW_32x32) return "BW32x32/"+zFile;
  	if (size==SIZE_60x60) return "60x60/"+zFile;
  	if (size==SIZE_120x120) return "120x120/"+zFile;
  	return zFile;
  }

  private static int makeIdIcono(long size, int zNroIcono) {
  	if (size==SIZE_DEFA) return zNroIcono;
  	if (size==SIZE_32x32) return -(1000000+zNroIcono);
  	if (size==SIZE_60x60) return -(2000000+zNroIcono);
  	if (size==SIZE_120x120) return -(3000000+zNroIcono);
  	if (size==SIZE_BW_32x32) return -(4000000+zNroIcono);
  	if (size==RESPONSIVE) return -(5000000+zNroIcono);
  	return zNroIcono;
  }
  //-------------------------------------------------------------------------- //
  // Busco si el icono ya lo tengo en memoria
  //-------------------------------------------------------------------------- //
  public GuiIcon buscarIcono(  int zNroIcono ) throws Exception {
  	return buscarIcono(SIZE_DEFA,zNroIcono);
  }
  public GuiIcon buscarIconoSize( long size, int zNroIcono ) throws Exception {
    GuiIcon oIcon = this.getCacheIconSize(size, zNroIcono );
    return oIcon;
  }
  public GuiIcon buscarIcono( long size, int zNroIcono ) throws Exception {
    // traigo el ícono de la tabla
    GuiIcon oIcon = this.getCachedIcon(size, zNroIcono );
    if (oIcon != null) return oIcon;

    // si no estaba, busca uno fijo, que no esté en la base
    oIcon = GuiIcon.ConvertNroIcono(zNroIcono);
    if (oIcon == null) {
      oIcon = this.getCachedIcon(size, GuiIcon.DEFAULT_ICON );
      if (oIcon == null) {
        oIcon = GuiIcon.GetDefaultIcon();
        this.cacheIcon(GuiIcon.DEFAULT_ICON, oIcon );
      }
    } else {
      this.cacheIcon( zNroIcono, oIcon );
    }
    return oIcon;
  }
  /* por compatibilidad solo un tamaño*/
  public void cacheIcon(int zIconNumber, String zIcon, String zFile) throws Exception {
  	cacheIconSize(SIZE_DEFA,zIconNumber,zIcon,zFile,false);
  }
  
  /* levanta el default y permite elgir si autodetectar los tamaños restantes (mas lento)*/
  public void cacheIcon(int zIconNumber, String zIcon, String zFile,boolean autodetect) throws Exception {
  	cacheIconSize(SIZE_DEFA,zIconNumber,zIcon,zFile,false);
  	cacheIconSize(SIZE_32x32,zIconNumber,zIcon,zFile,autodetect);
  	cacheIconSize(SIZE_60x60,zIconNumber,zIcon,zFile,autodetect);
  	cacheIconSize(SIZE_120x120,zIconNumber,zIcon,zFile,autodetect);
  	cacheIconSize(SIZE_BW_32x32,zIconNumber,zIcon,zFile,autodetect);
  }
  
  /* sin autodetect de tamaños, se envia una mascara con los tamaños que se saben que estan*/ 
  public void cacheIcon(int zIconNumber, String zIcon, String zFile,long mask) throws Exception {
  	cacheIconSize(SIZE_DEFA,zIconNumber,zIcon,zFile,false);
  	if ((mask&SIZE_32x32)!=0) cacheIconSize(SIZE_32x32,zIconNumber,zIcon,zFile,false);
  	if ((mask&SIZE_60x60)!=0) cacheIconSize(SIZE_60x60,zIconNumber,zIcon,zFile,false);
  	if ((mask&SIZE_120x120)!=0) cacheIconSize(SIZE_120x120,zIconNumber,zIcon,zFile,false);
  	if ((mask&SIZE_BW_32x32)!=0) cacheIconSize(SIZE_BW_32x32,zIconNumber,zIcon,zFile,false);
  }
  
  public void cacheIcon(int zIconNumber, String zIcon, String zFile,long mask, String responsive) throws Exception {
  	cacheIconSize(SIZE_DEFA,zIconNumber,zIcon,zFile,false);
  	if ((mask&SIZE_32x32)!=0) cacheIconSize(SIZE_32x32,zIconNumber,zIcon,zFile,false);
  	if ((mask&SIZE_60x60)!=0) cacheIconSize(SIZE_60x60,zIconNumber,zIcon,zFile,false);
  	if ((mask&SIZE_120x120)!=0) cacheIconSize(SIZE_120x120,zIconNumber,zIcon,zFile,false);
  	if ((mask&SIZE_BW_32x32)!=0) cacheIconSize(SIZE_BW_32x32,zIconNumber,zIcon,zFile,false);
  	if ((mask&RESPONSIVE)!=0) cacheIconSize(RESPONSIVE,zIconNumber,zIcon,responsive,false);
  }
  
  public void cacheIconSize(long size,int zIconNumber, String zIcon, String zFile, boolean detect) throws Exception {
    if (detect&&JPss.class.getResource("core/ui/Images/"+makeFileName(size, zFile))==null) return;

    GuiIcon ico=new GuiIcon();
		ico.GetcDato().pNroIcono.setValue(zIconNumber);
		ico.GetcDato().pDescrip.setValue(zIcon);
		ico.GetcDato().pFile.setValue(makeFileName(size, zFile));
		cacheIcon(makeIdIcono(size, zIconNumber), ico);
  }
  
/*
 * private void cacheIcon(int zIconNumber, String zIcon, String zFile) throws Exception { GuiIcon ico = new GuiIcon(); ico.GetcDato().pNroIcono.setValue(zIconNumber); ico.GetcDato().pDescrip.setValue(zIcon); ico.GetcDato().pFile.setValue(zFile); cacheIcon(zIconNumber, ico); }
 */
  public void cacheIcon(int zIconNumber, GuiIcon zIcon) {
    oTabla.addElement( String.valueOf( zIconNumber ), zIcon );
  }
  public GuiIcon getCacheIconSize(long size, int zIconNumber) {
		return (GuiIcon) oTabla.getElement( String.valueOf( makeIdIcono(RESPONSIVE,zIconNumber) ) );
  }  
  public GuiIcon getCachedIcon(long size,int zIconNumber) throws Exception {
  	GuiIcon ico = null;
  	if (size==RESPONSIVE) 
  		ico = (GuiIcon) oTabla.getElement( String.valueOf( makeIdIcono(RESPONSIVE,zIconNumber) ) );
  	if (size==SIZE_120x120)
  		ico = (GuiIcon) oTabla.getElement( String.valueOf( makeIdIcono(SIZE_120x120,zIconNumber) ) );
  	if (ico==null && size==SIZE_60x60)
  		ico = (GuiIcon) oTabla.getElement( String.valueOf( makeIdIcono(SIZE_60x60,zIconNumber) ) );
  	if (ico==null && size==SIZE_BW_32x32)
  		ico = (GuiIcon) oTabla.getElement( String.valueOf( makeIdIcono(SIZE_BW_32x32,zIconNumber) ) );
  	if (ico==null && size==SIZE_32x32)
  		ico = (GuiIcon) oTabla.getElement( String.valueOf( makeIdIcono(SIZE_32x32,zIconNumber) ) );
  	if (ico==null)
  		ico = (GuiIcon) oTabla.getElement( String.valueOf( makeIdIcono(SIZE_DEFA,zIconNumber) ) );
    return ico;
  }


}

