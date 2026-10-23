package com.jsp.wds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@Configuration
public class AppConfig {

    @Bean
    public WebClient getWebClient() {
        WebClient.Builder builder = WebClient.builder();
        WebClient build = builder.build();
        return build;
    }

    @Bean
    public SAXParserFactory getSAXParserFactory() {
        return SAXParserFactory.newInstance();
    }

    @Bean
    public SAXParser getSAXParser() {
        SAXParser saxParser = null;

        try {
            saxParser = getSAXParserFactory().newSAXParser();
        } catch(SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return saxParser;
    }
}
