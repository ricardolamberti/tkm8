package pss.common.version;

import java.util.List;


/**
 * estructura auxiliar que se usa en el Map que almacena los objetos a incluir en un paquete

 *
 */
public class JPacketable {
	
	public JPacketable(IVersionable zparent,IEmpaquetable zpack,  List<IVersionable> zchildVersions) {
		pack = zpack;
		version = zparent;
		childVersions = zchildVersions;
	}
	
	public IEmpaquetable pack;
	public IVersionable version;
	public List<IVersionable> childVersions;
}
 