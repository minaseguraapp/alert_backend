package co.minasegura.alert.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class CommonsUtilTest {

    private CommonsUtil commonsUtil;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = mock(ObjectMapper.class);
        commonsUtil = new CommonsUtil(objectMapper);
    }

    @Test
    public void testToJson() throws JsonProcessingException {
        Object value = new Object();
        String expectedJson = "{\"key\":\"value\"}";
        when(objectMapper.writeValueAsString(value)).thenReturn(expectedJson);

        String actualJson = commonsUtil.toJson(value);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testToObject() throws JsonProcessingException {
        String json = "{\"key\":\"value\"}";
        Object expectedObject = new Object();
        when(objectMapper.readValue(json, Object.class)).thenReturn(expectedObject);

        Object actualObject = commonsUtil.toObject(json, Object.class);

        assertEquals(expectedObject, actualObject);
    }
}