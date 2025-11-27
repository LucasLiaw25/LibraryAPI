package com.liaw.dev.Library.pix;

import br.com.efi.efisdk.EfiPay;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class EfiConfiguration {

    private final Credentials credentials;
    private final ResourceLoader resourceLoader;

    @Bean
    public EfiPay efiPayClient() throws Exception{
        Map<String, Object> options = new HashMap<>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificatePath());
        options.put("sandbox", credentials.getSandboxEnabled());
        return new EfiPay(options);
    }

}
