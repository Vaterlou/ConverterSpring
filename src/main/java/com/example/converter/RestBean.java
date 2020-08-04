package com.example.converter;

import com.example.converter.domain.Currency;
import com.example.converter.repos.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class RestBean {

    @Autowired
    private CurrencyRepo currencyRepo;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            String quote = restTemplate.getForObject(
                    "http://www.cbr.ru/scripts/XML_daily.asp?text=", String.class);
            parse(quote);
        };
    }

    private void parse(String body) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(body.getBytes()));

        Node root = document.getDocumentElement();
        NodeList cbdatas = root.getChildNodes();

        currencyRepo.deleteAll();

        for (int x = 0, size = cbdatas.getLength(); x < size; x++) {
            Node cbdata = cbdatas.item(x);

            if (cbdata.getNodeType() != Node.TEXT_NODE) {

                NodeList cbdataProps = cbdata.getChildNodes();

                ArrayList<String> data = new ArrayList<>();
                data.add(cbdata.getAttributes().getNamedItem("ID").getNodeValue());

                for(int j = 0; j < cbdataProps.getLength(); j++) {

                    Node cbdataProp = cbdataProps.item(j);

                    if (cbdataProp.getNodeType() != Node.TEXT_NODE) {
                        data.add(cbdataProp.getChildNodes().item(0).getTextContent());
                    }
                }

                for(int j = 0; j < data.size(); j++){
                    add_currency_in_db(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5));
                }
            }
        }
    }

    private void add_currency_in_db(String id, String numCode, String charCode, String nominal, String name, String value){
        Currency currency = new Currency(id, numCode, charCode, nominal, name, value);
        currencyRepo.save(currency);
    }
}