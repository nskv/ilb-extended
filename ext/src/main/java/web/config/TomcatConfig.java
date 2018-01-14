package main.java.web.config;

import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.*;

@Configuration
public class TomcatConfig {

    /**
     * Enable http2 in Tomcat if http2 profile specified
     */
    @Profile("http2")
    @Bean
    public EmbeddedServletContainerCustomizer tomcatCustomizer() {
        return container -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                ((TomcatEmbeddedServletContainerFactory) container)
                        .addConnectorCustomizers(
                                connector -> connector.addUpgradeProtocol(new Http2Protocol()));
            }
        };
    }

}
