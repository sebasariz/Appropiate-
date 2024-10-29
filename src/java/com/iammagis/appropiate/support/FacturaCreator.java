/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.support;

import com.iammagis.appropiate.beans.Factura;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Usuario
 */
public class FacturaCreator {

    static SimpleDateFormat simpleDateFormatFile = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    static SimpleDateFormat simpleDateFormaHour = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
    static NumberFormat numberFormat = new DecimalFormat("###,##0.00");
    static PropertiesAcces propertiesAcces = new PropertiesAcces();
    private static String header = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 5.01 Transitional//ES\" \"http://www.w3.org/TR/html4/loose.dtd\">"
            + "<html>"
            + "<head>"
            + "</head><body>";
    private static String footer = "</body></html>";

    public static JSONObject generateFacturaPDF(Factura factura, String context) throws Exception {
        File file = null;
        JSONObject jSONObject = new JSONObject();
        if (factura.getFacturaTemplateIdfacturaTemplate() != null) {
            String buf = factura.getFacturaTemplateIdfacturaTemplate().getTemplate();
            JSONArray campos = new JSONArray(factura.getFacturaTemplateIdfacturaTemplate().getCampos());

            buf = buf.replace("$numero", factura.getReferencia());
            buf = buf.replace("$referencia", factura.getReferencia());
            buf = buf.replace("$fecha-factura", simpleDateFormat.format(new Date(factura.getFecha().longValue())));
            buf = buf.replace("$total", factura.getValor() + "");

            for (int i = 0; i < campos.length(); i++) {
                JSONArray jSONArrayCampo = campos.getJSONArray(i);
                String referencia = jSONArrayCampo.getString(0);
//                buf = buf.replace(referencia, campo.getValor());
            }
            //creamos el PDF desde el HTML
            try {
                file = new File(context + File.separator + "docs" + File.separator + "factura" + System.currentTimeMillis() + ".pdf");
                OutputStream outputStream = new FileOutputStream(file);
                com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.LETTER);
                PdfWriter writer = PdfWriter.getInstance(document, outputStream);
                document.open();

                InputStream is = new ByteArrayInputStream(buf.getBytes());
                XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"));
                document.close();
                outputStream.close();
                buf = null;
                jSONObject.put("url", file.getName());
            } catch (IOException e) {
                e.printStackTrace();
                jSONObject.put("error", e.getMessage());
            }
        }
        return jSONObject;

    }

    public static void createPDFFromString(String html) {

    }
}
