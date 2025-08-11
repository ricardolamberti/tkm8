package pss.common.restJason;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
//@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperResolver implements ContextResolver<ObjectMapper>
{
    private final ObjectMapper mapper;
    
    public ObjectMapperResolver() 
    {
        mapper = new ObjectMapper();
        //mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
       // mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        //mapper.disable(MapperFeature.USE_GETTERS_AS_SETTERS);
       // mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) 
    {
        return mapper;
    }
    
    
    public ObjectMapper getMapper() {
		return mapper;
	}
    
    
}
