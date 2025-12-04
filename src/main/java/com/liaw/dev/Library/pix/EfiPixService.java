package com.liaw.dev.Library.pix;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.liaw.dev.Library.entity.Loan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

@Service
public class EfiPixService {

    @Value("${client_id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    public JSONObject pixCreateCharge(String value, String name, String cpf){
        Credentials credentials = new Credentials();
        JSONObject options = configuration();

        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("cpf", cpf).put("nome", name));
        body.put("valor", new JSONObject().put("original", value));
        body.put("chave", "lucasliaw50@gmail.com");
        body.put("solicitacaoPagador", "Cobrança dos serviços prestados.");

        JSONArray infoAdicionais = new JSONArray();
        infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional 1"));
        body.put("infoAdicionais", infoAdicionais);

        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixCreateImmediateCharge", new HashMap<String, String>(), body);
            System.out.println(response);
            int idFromJson = response.getJSONObject("loc").getInt("id");
            generateQrCode(String.valueOf(idFromJson));
            return response;
        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void generateQrCode(String id){
        JSONObject options = configuration();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        try {
            EfiPay efi= new EfiPay(options);
            Map<String, Object> response = efi.call("pixGenerateQRCode", params, new HashMap<String, Object>());

            System.out.println(response);

            File outputfile = new File("qrCodeImage.png");
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(javax.xml.bind.DatatypeConverter.parseBase64Binary(((String) response.get("imagemQrcode")).split(",")[1]))), "png", outputfile);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(outputfile);

        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private JSONObject configuration(){
        Credentials credentials = new Credentials();
        JSONObject options = new JSONObject();
        options.put("client_id", clientId);
        options.put("client_secret", clientSecret);
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.getSandbox());

        return options;
    }

    public String checkPaymentStatus(String txid){
        JSONObject options = configuration();
        HashMap<String, String> params = new HashMap<>();
        params.put("txid", txid);

        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixDetailCharge", params, new JSONObject());

            if (response.has("pix")) {
                JSONArray pixArray = response.getJSONArray("pix");

                if (pixArray.length() > 0) {
                    return "CONCLUIDA";
                }
            }

            return "PENDENTE";

        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
            return "ERRO";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }


}
