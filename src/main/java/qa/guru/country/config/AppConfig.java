package qa.guru.country.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.text.SimpleDateFormat;

@Configuration
@EnableWs
public class AppConfig {

    public static final String SOAP_NAMESPACE_URI = "country";

    @Value("${country.base-uri}")
    private String countryUserdataBaseUri;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return objectMapper;
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "country")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countrySchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountryPort");
        wsdl11Definition.setLocationUri(countryUserdataBaseUri + "/ws");
        wsdl11Definition.setTargetNamespace(SOAP_NAMESPACE_URI);
        wsdl11Definition.setSchema(countrySchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countrySchema() {
        return new SimpleXsdSchema(new ClassPathResource("country.xsd"));
    }
}
