# Instrucciones para Codex: Generación de clases Biz, Form, Gui y Guis - Ejemplo para Player

## 💼 Clase BizPlayer

- **Nombre**: `BizPlayer`
- **Tabla**: `gms_player`
- **Prefijo de campos**: `p`
- **Clave primaria**: `id_player`
- **Atributos**: reflejan columnas de la tabla:
  - `company`: JString
  - `id_player`: JLong
  - `name`: JString
  - `password`: JString
  - `id_avatar`: JLong
  - `birthday`: JDate
- **Relaciones**:
  - `id_avatar` → `BizAvatar`
- **Comportamiento**:
  - Generar getters inteligentes con `if (obj != null) return obj;` para entidades relacionadas.
  - Implementa `JBizObject` o clase base equivalente.

### 📦 Ejemplo de clase `BizPlayer`

```java
// Ver código fuente original en el archivo para más detalles
public class BizPlayer extends JRecord {
	protected JLong pIdplayer = new JLong();
	protected JString pName = new JString();
	protected JString pPassword = new JString();
	protected JLong pIdavatar = new JLong();
	protected JDate pBirthday = new JDate();
	protected JString pCompany = new JString();
	public void setIdplayer(long zValue) throws Exception {    pIdplayer.setValue(zValue);  }
	public long getIdplayer()	throws Exception {     return pIdplayer.getValue();  }
	public boolean isNullIdplayer() throws Exception { return  pIdplayer.isNull(); } 
	public void setNullToIdplayer() throws Exception {  pIdplayer.setNull(); } 
	public void setName(String zValue) throws Exception {    pName.setValue(zValue);  }
	public String getName()	throws Exception {     return pName.getValue();  }
	public boolean isNullName() throws Exception { return  pName.isNull(); } 
	public void setNullToName() throws Exception {  pName.setNull(); } 
	public void setPassword(String zValue) throws Exception {    pPassword.setValue(zValue);  }
	public String getPassword()	throws Exception {     return pPassword.getValue();  }
	public boolean isNullPassword() throws Exception { return  pPassword.isNull(); } 
	public void setNullToPassword() throws Exception {  pPassword.setNull(); } 
	public void setIdavatar(long zValue) throws Exception {    pIdavatar.setValue(zValue);  }
	public long getIdavatar()	throws Exception {     return pIdavatar.getValue();  }
	public boolean isNullIdavatar() throws Exception { return  pIdavatar.isNull(); } 
	public void setNullToIdavatar() throws Exception {  pIdavatar.setNull(); } 
	public void setBirthday(Date zValue) throws Exception {    pBirthday.setValue(zValue);  }
	public Date getBirthday()	throws Exception {     return pBirthday.getValue();  }
	public boolean isNullBirthday() throws Exception { return  pBirthday.isNull(); } 
	public void setNullToBirthday() throws Exception {  pBirthday.setNull(); } 
	public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
	public String getCompany()	throws Exception {     return pCompany.getValue();  }
	public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
	public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public BizPlayer() throws Exception {
  }


	public void createProperties() throws Exception {
		addItem( "id_player", pIdplayer );
		addItem( "name", pName );
		addItem( "password", pPassword );
		addItem( "id_avatar", pIdavatar );
		addItem( "birthday", pBirthday );
		addItem( "company", pCompany );
  }
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "id_player", "id", false, false, 64 );
		addFixedItem( FIELD, "name", "Nombre", true, true, 250 );
		addFixedItem( FIELD, "password", "clave", true, true, 250 );
		addFixedItem( FIELD, "id_avatar", "Avatar", true, true, 64 );
		addFixedItem( FIELD, "birthday", "Fecha cumpleaños", true,false, 10 );
		addFixedItem( FIELD, "company", "Compañia", true,false, 15 );
  }
	
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("id_avatar", createControlCombo(GuiAvatars.class,"id_avatar", new JPair<String, String>("company","company") ));
  	super.createControlProperties();
  }
	public String GetTable() { return "gms_player"; }
	transient BizAvatarBase objAvatar;
	public BizAvatarBase getObjAvatar() throws Exception {
		if (isNullIdavatar())
			return null;
		if (objAvatar != null)
			return objAvatar;
		BizAvatarBase fam = new BizAvatarBase();
		fam.dontThrowException(true);
		if (!fam.read(getIdavatar()))
			return null;
		return objAvatar = fam;
	}
	public boolean read( long zIdplayer ) throws Exception { 
		addFilter( "id_player",  zIdplayer ); 
		return read(); 
	} 
	public boolean readByName( String name ) throws Exception { 
		addFilter( "name",  name ); 
		return read(); 
  } 
}
```

---

## 🧾 Clase FormPlayer

- **Nombre**: `FormPlayer`
- **Clase base**: `JForm` o `JBaseForm`
- **Biz asociado**: `BizPlayer`
- **Campos del formulario**:
  - Todos los campos de `BizPlayer`
- **Validaciones**:
  - `name` obligatorio
  - Validar existencia de `id_avatar` si se requiere

### 📦 Ejemplo de clase `FormPlayer`

```java
public class FormPlayer extends JBaseForm {
  public FormPlayer() throws Exception {}
  public GuiPlayer getWin() { return (GuiPlayer) getBaseWin(); }

  public void InicializarPanel(JWin zWin) throws Exception {
    AddItemEdit(null, CHAR, OPT, "id_player").setHide(true);
    AddItemEdit(null, CHAR, OPT, "company").setHide(true);
    AddItemEdit("Denominación", CHAR, OPT, "name").setComplexColumnClass("col-sm-6");
    AddItemCombo("Avatar", CHAR, OPT, "id_avatar").setComplexColumnClass("col-sm-4").setRefreshForm(true);
    AddItemDateTime("Fecha", DATE, OPT, "birthday").setComplexColumnClass("col-sm-4");
  }
}
```

---

## 🖼️ Clase GuiPlayer

- **Nombre**: `GuiPlayer`
- **Clase base**: `JWin`
- **Propósito**: Pantalla principal de gestión de jugadores
- **Métodos recomendados**:
  - `ObtenerDato()`
  - `GetTitle()`
  - `getFormBase()`
  - `getKeyField()`
  - `getDescripField()`
  - `createActionMap()`
  - `getSubmitFor()`

### 📦 Ejemplo de clase `GuiPlayer`

```java
public class GuiPlayer extends JWin {
  public GuiPlayer() throws Exception {}

  public JRecord ObtenerDato() throws Exception { return new BizPlayer(); }
  public int GetNroIcono() throws Exception { return 10013; }
  public String GetTitle() throws Exception { return "Jugador"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPlayer.class; }
  public String getKeyField() throws Exception { return "id_player"; }
  public String getDescripField() { return "name"; }
  public BizPlayer GetcDato() throws Exception { return (BizPlayer) this.getRecord(); }

  public void createActionMap() throws Exception {
    addAction(10, "Suscripciones", null, 10027, false, false);
    addAction(60, "Agregar Participante", null, 2003, true, true);
    addAction(70, "Eliminar Def.", null, 2003, true, true).setOnlyInForm(true);
    addAction(80, "Ver avatar", KeyEvent.VK_ENTER, null, GuiIcon.CONSULTAR_ICON, false, false);
    super.createActionMap();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
    if (a.getId() == 10) return GetcDato().canEjecutar();
    return super.OkAction(a);
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
    if (a.getId() == 10) return new JActWins(getSuscriptores());
    if (a.getId() == 60) return new JActNew(getNewParticipante(), 0);
    if (a.getId() == 70) return new JActSubmit(this) {
      public void submit() throws Exception {
        GetcDato().execProcessRealDelete();
      }
    };
    if (a.getId() == 50) return new JActQuery(getAvatar());
    return super.getSubmitFor(a);
  }

  public GuiGamePlayers getSuscriptores() throws Exception {
    GuiGamePlayers segs = new GuiGamePlayers();
    segs.getRecords().addFilter("company", GetcDato().getCompany());
    segs.getRecords().addFilter("id_player", GetcDato().getIdplayer());
    segs.getRecords().addOrderBy("date", "DESC");
    segs.SetVision("PLAYER");
    return segs;
  }

  public GuiEventoParticipante getNewParticipante() throws Exception {
    GuiEventoParticipante p = new GuiEventoParticipante();
    p.getRecord().addFilter("id_evento", GetcDato().getIdevento());
    p.getRecord().addFilter("company", GetcDato().getCompany());
    return p;
  }

  public GuiAvatar getAvatar() throws Exception {
    return GetcDato().getObjAvatar();
  }
}
```

---

## 📋 Clase GuiPlayers

- **Nombre**: `GuiPlayers`
- **Clase base**: `JWins`
- **Propósito**: Visualización en lista de jugadores
- **Métodos recomendados**:
  - `ConfigurarColumnasLista`
  - `createActionMap`

### 📦 Ejemplo de clase `GuiPlayers`

```java
public class GuiPlayers extends JWins {
  public GuiPlayers() throws Exception {}

  public int GetNroIcono() throws Exception { return 10013; }
  public String GetTitle() throws Exception { return "Jugadores"; }
  public Class<? extends JWin> GetClassWin() { return GuiPlayer.class; }

  public void createActionMap() throws Exception {
    addActionNew(1, "Nuevo Registro");
  }

  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("name");
    zLista.AddColumnaLista("password");
    zLista.AddColumnaLista("id_avatar");
    zLista.AddColumnaLista("birthday");
  }
}
```

---

## 🧠 Reglas Generales para Codex

- Siempre usa `JString`, `JLong`, `JFloat`, `JBoolean`, `JDate` según el tipo de dato SQL.
- Si el campo termina en `_id`, generar `getObjX()` si hay clase relacionada.
- Los combos deben alimentarse de clases `Biz`.
- Las pantallas deben llamarse `GuiNombre` y sus listados `GuiNombres`.
- todas las tablas de la BD deben tener un Biz, un Form, un Gui y un Guis. y deben estar en una carpeta exclusiva para las 4 clases.
