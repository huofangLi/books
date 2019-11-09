package com.books.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Books.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final FileLocal fileLocal = new FileLocal();

    private final AliyunOSS oss = new AliyunOSS();

    public FileLocal getFileLocal() {
        return fileLocal;
    }

    public AliyunOSS getOss() {
        return oss;
    }

    public static class FileLocal {
        private String path;
        private String prefix;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }

    public static class AliyunOSS {
        private String endpoint;
        private String key;
        private String secret;
        private String bucketName;
        private String prefix;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
