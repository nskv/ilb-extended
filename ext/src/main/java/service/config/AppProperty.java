package main.java.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@SuppressWarnings("WeakerAccess")
@ConfigurationProperties(prefix = "root")
public class AppProperty {

    private Batch batch;

    @Data
    public static class Batch implements Serializable {
        private String account;
        private String url;
        private String key;
        private String jobId;
        private boolean testing;
    }

}
