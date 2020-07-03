package br.com.bluesoft.desafio.service.impl;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.service.FornecedorService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.List;

public class FornecedorServiceImpl implements FornecedorService {

    @Override
    public List<FornecedorDTO> getFornecedoresByProdutoGtin(String gtin) {

        List<FornecedorDTO> fornecedorList = null;

        String awsUrl = "https://egf1amcv33.execute-api.us-east-1.amazonaws.com/dev/produto/" + gtin;
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");

        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(awsUrl, HttpMethod.GET, requestEntity, String.class);
        HttpStatus status = responseEntity.getStatusCode();

        try {
            fornecedorList = new ObjectMapper().readValue(responseEntity.getBody().toString(), new TypeReference<List<FornecedorDTO>>() {
            });
        } catch (IOException ignored) {
        }
        return fornecedorList;
    }
}
