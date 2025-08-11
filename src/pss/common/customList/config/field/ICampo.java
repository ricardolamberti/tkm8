package  pss.common.customList.config.field;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.relation.JRelation;
import pss.core.services.fields.JObject;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;

public interface ICampo {

	public abstract void setCompany(String zValue) throws Exception;

	public abstract String getCompany() throws Exception;

	public abstract String getFormatParam() throws Exception;

	public abstract String getRecordOwner() throws Exception;
	public abstract String getRecordSource() throws Exception;

	public abstract String getRelId() throws Exception;

	public abstract void setRelId(String zValue) throws Exception;

	public abstract void setListId(long zValue) throws Exception;

	public abstract long getListId() throws Exception;

	public abstract String getForceNombreColumna() throws Exception;
	
	public abstract String getSerialDeep() throws Exception;	

	public abstract void setCampo(String zValue) throws Exception;

	public abstract String getCampo() throws Exception;

	public abstract String getGeoCampo() throws Exception;

	public abstract boolean hasGeoCampo() throws Exception;

	public abstract String getFuncion() throws Exception;

	public abstract void setRecordOwner(String zValue) throws Exception;
	public abstract void setRecordSource(String zValue) throws Exception;
	public JRecord getObjRecordGeoOwner() throws Exception;
	public abstract void createProperties() throws Exception;

	public abstract String getOperador() throws Exception;
  public abstract boolean hasOperador() throws Exception ;
  public abstract String getValor() throws Exception;
  public abstract String getValor2() throws Exception;
  public abstract boolean hasValor2() throws Exception;
  public abstract boolean hasRelId() throws Exception;
  public abstract boolean isCheckInput() throws Exception;
	public abstract boolean isLOVInput() throws Exception;
  public abstract boolean isDateTimeInput() throws Exception;
	public abstract boolean isDateInput() throws Exception;


	/**
	 * Returns the table name
	 */
	public abstract String GetTable();

	/**
	 * Default read() method
	 */
	public abstract boolean read(String zCompany, long zListId, long zCampo) throws Exception;

	public abstract BizCustomList getObjCustomList() throws Exception;

	public abstract String getDescrCampo() throws Exception;

	public abstract void processInsert() throws Exception;

	public abstract boolean hasFunction() throws Exception;

	public abstract boolean hasFormat() throws Exception;

	public abstract String getTargetAlias() throws Exception;

	public abstract String getNameField() throws Exception;

	public abstract String getNameField(String field) throws Exception;

	public abstract JRecord getObjRecordTarget() throws Exception;

	public abstract JRecord getObjRecordOwner() throws Exception;

	public abstract JRelation getObjRelation() throws Exception;

//  public abstract JRecords<BizCampoGallery> getCamposGallery() throws Exception;

//	public abstract JMap<String, String> getGeoCamposGallery() throws Exception;

	public abstract void execSubir(final long pos) throws Exception;

	public abstract void subir(long pos) throws Exception;

	public abstract void execBajar(final long pos) throws Exception;

	public abstract void bajar(long pos) throws Exception;

	public abstract boolean hasCampo() throws Exception;

	public abstract boolean hasRanking() throws Exception;

//	public abstract void prepareRanking(JBaseRecord r) throws Exception;

	public abstract void prepareField(JBaseRecord r) throws Exception;

	public abstract void prepareGeoField(JBaseRecord r) throws Exception;

	public abstract JObject<?> createProp() throws Exception;

	public abstract boolean hasDiferencia() throws Exception;

	public abstract void prepareDiferencia(JBaseRecord r) throws Exception;

}