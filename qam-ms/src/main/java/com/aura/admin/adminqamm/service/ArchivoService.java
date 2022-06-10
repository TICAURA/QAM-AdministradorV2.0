package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.ArchivoLogo;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

@Service
public class ArchivoService {

    Logger LOGGER = LoggerFactory.getLogger(ArchivoService.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ServiceInvoker serviceInvoker;

    @Value("${endpoints.documento.upload}")
    private String uploadDocumentoEndpoint;

    @Value("${endpoints.documento.descargar}")
    private String endpoindDescargarArchivo;

    public ArchivoLogo getArchivo(int idArchivo) throws BusinessException {


        try {
            Properties spCallProps = loadTransferProperties("/efile.properties");

            spCallProps.setProperty("endpoint", spCallProps.getProperty("endpoint")+endpoindDescargarArchivo+idArchivo);
            spCallProps.setProperty("http_method","GET");


            String serviceCallResponse = serviceInvoker.invokeHTTP(spCallProps);

            LOGGER.info("Timbre:" + serviceCallResponse);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(serviceCallResponse);
            ArchivoLogo pdf = new ArchivoLogo();
            pdf.setName(rootNode.path("data").path("nombre").asText());
            pdf.setContent(rootNode.path("data").path("contenido").asText());

            return pdf;
        }catch (IOException e){
            LOGGER.error("Error al guardar PDF: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }

    }

    public int guardarArchivo(String nombre,String archivo) throws BusinessException {

        try {

            Properties spCallProps = loadTransferProperties("/efile.properties");

            spCallProps.setProperty("endpoint", spCallProps.getProperty("endpoint")+uploadDocumentoEndpoint);

            String body = generatePdfSaveRequest(resourceLoader.getResource("classpath:/JSON/archivo.json").getInputStream(), nombre,archivo);

            LOGGER.info("Body:" + body);

            String serviceCallResponse = serviceInvoker.invoke(spCallProps, body);

            LOGGER.info("Timbre:" + serviceCallResponse);

            return generateIdArchivoJson(serviceCallResponse);
        }catch (IOException e){
            LOGGER.error("Error al guardar PDF: "+e.getMessage(),e);
            throw new BusinessException(e.getMessage(),500);
        }
    }

    /**
     * Metodo que genera el request para guardar el json
     * @param in template del request
     * @param nombreArchivo nombre del archivo
     * @param archivoData archivo en base64
     * @return String
     * @throws Exception
     */
    public static String generatePdfSaveRequest(InputStream in, String nombreArchivo, String archivoData) throws  IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(in);
        JsonNode entrada= rootNode.get("entrada");
        JsonNode archivo = entrada.get("archivo");
        ((ObjectNode) archivo).put("nombre", nombreArchivo);
        ((ObjectNode) archivo).put("contenido", archivoData);
        return rootNode.toPrettyString();
    }

    /**
     * Obtiene el id del response
     * @param  json response
     * @return String
     * @throws Exception
     */
    public static int generateIdArchivoJson(String json) throws  IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path("data").path("id").asInt();
    }

    private Properties loadTransferProperties(String propertiesFileName) throws BusinessException {
        String userDirectory = new File("").getAbsolutePath();
        Properties properties = new Properties();
        String execPropertiesPath = userDirectory + propertiesFileName;
        try (InputStream inputStream = new FileInputStream(execPropertiesPath)) {
            properties.load(inputStream);
            return properties;
        } catch (FileNotFoundException e) {
            LOGGER.error("No se encontro el archivo de propiedades de ejecucion: "+propertiesFileName+"   " + e.getMessage(),e);
            throw new BusinessException("No se encontro el archivo de propiedades de ejecucion: "+propertiesFileName,500);
        } catch (IOException e) {
            LOGGER.error("Se produjo un error de lectura de Propiedades de ejecucion: " + propertiesFileName + "   " + e.getMessage(), e);
            throw new BusinessException("Se produjo un error de lectura de Propiedades de ejecucion: " + propertiesFileName, 500);
        }
    }
}
