package pss.core.data.interfaces.structure;

import java.io.Serializable;

import pss.core.services.records.JRecord;

public interface IOrderByControl<TRecord extends JRecord> extends Serializable {
     public String getDescription(TRecord rec) throws Exception;
     public String getID(TRecord rec) throws Exception;
}
