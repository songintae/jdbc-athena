package example.athena.implement;

import lombok.Getter;
import lombok.Setter;

/**
 * Properties 가이드
 * https://s3.amazonaws.com/athena-downloads/drivers/JDBC/SimbaAthenaJDBC_2.0.6/docs/Simba+Athena+JDBC+Driver+Install+and+Configuration+Guide.pdf
 */
@Setter
@Getter
public class AthenaClientProperties {
    private String url;
    private String outputLocation;
    private String schema;
    private String awsCredentialsProviderClass;
    private String socketTimeout;
}
