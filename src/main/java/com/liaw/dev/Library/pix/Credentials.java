package com.liaw.dev.Library.pix;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "efi.pix")
public class Credentials {
    private String clientId;
    private String clientSecret;
    private String certificatePath;
    private Boolean sandboxEnabled;
}
