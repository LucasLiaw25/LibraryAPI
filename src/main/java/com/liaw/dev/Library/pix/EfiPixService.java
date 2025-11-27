package com.liaw.dev.Library.pix;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EfiPixService {

    private final Credentials credentials;
    private final EfiPay efiClient;

    public JSONObject listPixKey(){
        Map<String, String> params = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        return executeOperation("pixListEvp", params, body);
    }

    public JSONObject createPixCode(BigDecimal price, String name){
        Map<String, Object> body = new HashMap<>();
        String formattedPrice = String.format(Locale.US, "%.2f", price);
        Map<String, Object> calendario = new HashMap<>();
        calendario.put("expiracao", 3600);
        body.put("calendario", calendario);
        Map<String, Object> valor = new HashMap<>();
        valor.put("original", formattedPrice);
        body.put("valor", valor);
        body.put("chave", "lucasliaw50@gmail.com");
        System.out.println(new File("./certs/libraryEfi.p12").getAbsolutePath());
        return executeOperation("pixCreateImmediateCharge", new HashMap<>(), body);
    }

    private JSONObject executeOperation(String operation, Map<String, String> params, Map<String, Object> body){
        var retorno = new JSONObject();
        try {
            JSONObject response = efiClient.call(operation, params, new JSONObject(body));
            log.info("Resultado: {}", response);
            return response;
        }catch (EfiPayException e){
            log.error(e.getError());
            retorno.put("error", e.getErrorDescription());
        }
        catch (Exception e) {
            retorno.put("erro", "não foi possível processar a requisição");
        }
        return retorno;
    }

}
