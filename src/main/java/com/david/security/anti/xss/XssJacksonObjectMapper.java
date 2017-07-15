package com.david.security.anti.xss;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 具有xss功能的jackson映射器
 * 
 * @author ljingxiong<jingxiong.li@heysroad.com>
 * @version V1.0(2015年8月27日 下午1:30:39)
 */
public class XssJacksonObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = -3448961813323784217L;

	public XssJacksonObjectMapper() {
		SimpleModule module = new SimpleModule("HTML XSS derializer");
		module.addSerializer(new JsonHtmlXssSerializer(String.class));
		module.addDeserializer(String.class, new JsonHtmlXssDeserializer());
		this.registerModule(module);
	}

	class JsonHtmlXssDeserializer extends JsonDeserializer<String> {
		@Override
		public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			if (StringUtils.isNotBlank(jp.getText())) {
				return HtmlUtils.htmlEscape(jp.getText());
			} else {
				return jp.getText();
			}
		}
	}

	class JsonHtmlXssSerializer extends JsonSerializer<String> {

		public JsonHtmlXssSerializer(Class<String> string) {
			super();
		}

		public Class<String> handledType() {
			return String.class;
		}

		@Override
		public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			if (value != null) {
				String encodedValue = HtmlUtils.htmlUnescape(value.toString());
				jgen.writeString(encodedValue);
			}
		}
	}
}

