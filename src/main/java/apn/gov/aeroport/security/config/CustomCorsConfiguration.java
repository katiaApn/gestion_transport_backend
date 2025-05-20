package apn.gov.aeroport.security.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomCorsConfiguration implements CorsConfigurationSource {


    @Value("${CROSS_ORIGINE}")
    private String CROSS_ORIGINE;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        logger.info("CROSS_ORIGINE : --->" + CROSS_ORIGINE);
        logger.info("CORS Configuration applied for origin: " + request.getHeader("Origin"));
        CorsConfiguration config = new CorsConfiguration();
        List<String> allowedOrigins = Arrays.asList(CROSS_ORIGINE.split(","));
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);



        return config;
    }
}
