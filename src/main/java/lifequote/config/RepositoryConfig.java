package lifequote.config;

/**
 * Created by milanashara on 12/25/16.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import lifequote.domain.Quote;
import lifequote.domain.Virtue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Virtue.class);
        config.exposeIdsFor(Quote.class);
    }

}
