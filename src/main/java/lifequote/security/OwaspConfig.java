package lifequote.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Milan Ashara
 *
 */
@Configuration
public class OwaspConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        JsonFactory factory = new JsonFactory();
        factory.setCharacterEscapes(new OwaspCharacterEscapes());
        return new ObjectMapper(factory);
    }
}
