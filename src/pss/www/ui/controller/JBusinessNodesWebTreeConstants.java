/*
 * Created on 22-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.controller;


public interface JBusinessNodesWebTreeConstants {

  //
  //  tree id constants
  //
  public static final String TREE_ID_BY_REGION = "by_region";
  public static final String TREE_ID_USER_DEFINED = "user_defined";
  
  //
  //  tree description constants
  //
  public static final String TREE_DESCRIPTION_BY_REGION = "Regiones geográficas";
  public static final String TREE_DESCRIPTION_USER_DEFINED = "Agrupaciones personalizadas";
  
  //
  //  geographical tree level constants
  //
  public static final int TREE_BY_REGION_REGIONAL_LEVEL_1 = 0;
  public static final int TREE_BY_REGION_REGIONAL_LEVEL_2 = 1;
  public static final int TREE_BY_REGION_REGIONAL_LEVEL_3 = 2;
  public static final int TREE_BY_REGION_REGIONAL_LEVEL_4 = 3;
  public static final int TREE_BY_REGION_LEVEL_BUSINESS_NODE = 4;
  
  //
  //  user defined tree level constants
  //
  public static final int TREE_USER_DEFINED_LEVEL_GROUPING_TYPE = 0;
  public static final int TREE_USER_DEFINED_LEVEL_GROUPING = 1;
  public static final int TREE_USER_DEFINED_LEVEL_BUSINESS_NODE_COUNTRY = 2;
  public static final int TREE_USER_DEFINED_LEVEL_BUSINESS_NODE = 3;
  public static final int TREE_USER_DEFINED_LEVEL_USER = 4;


  //
  //  fields to access tables constants
  //
  public static final String[] TREE_FILTER_FIELDS_BY_REGION = {
    "pais", "provincia", "ciudad", "localidad", "nodo"
  };
  public static final String[] TREE_DESCRIPTIONS_BY_REGION = {
    "País", "1ª división geográfica", "2ª división geográfica", "3ª división geográfica", "Estación de servicio"
  };
  public static final String[] TREE_FILTER_FIELDS_USER_DEFINED = {
    "tipo_agrupacion", "agrupacion", "pais", "nodo", "usuario"
  };


  


}
