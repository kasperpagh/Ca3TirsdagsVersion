package xml;

import entity.ExchangeRates;
import entity.Handler;
import entity.SingleExchangeRate;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlReader extends DefaultHandler implements Runnable
{

    private ExchangeRates er;
    private List<SingleExchangeRate> serList;
    private String refcur = "DKK";
    private String dato;
    private Handler hand = new Handler();

    public XmlReader()
    {
    }

    @Override
    public void startDocument() throws SAXException
    {
        serList = new ArrayList();
        System.out.println("Start Document (Sax-event)");
    }

    @Override
    public void endDocument() throws SAXException
    {
        System.out.println("HER ER SER:" + serList.toString());
        er = new ExchangeRates(dato, refcur, serList);
        hand.persist(er);
        System.out.println("End Document (Sax-event)");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {

        System.out.print("Element: " + localName + ": ");
        for (int i = 0; i < attributes.getLength(); i++)
        {
            if (attributes.getLocalName(i).equalsIgnoreCase("id"))
            {
                System.out.println("jeg er i dato");
                dato = attributes.getValue(i);
                System.out.println("her er dato: " + dato);
            }
            if (attributes.getLocalName(i).equalsIgnoreCase("refcur"))
            {
                System.out.println("jeg er i ref");
                refcur = attributes.getValue(i);
            }
            if (localName.equalsIgnoreCase("currency"))
            {
                System.out.println("jeg er i currency");
                SingleExchangeRate ser = new SingleExchangeRate(attributes.getValue(0), attributes.getValue(1), attributes.getValue(2));
                serList.add(ser);
                System.out.print("[Atribute: NAME: " + attributes.getLocalName(i) + " VALUE: " + attributes.getValue(i) + "] ");
            }
        }

        System.out.println("");
    }
//
//    public static void main(String[] argv)
//    {
//        try
//        {
//            XMLReader xr = XMLReaderFactory.createXMLReader();
//            xr.setContentHandler(new XmlReader());
//            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
//            xr.parse(new InputSource(url.openStream()));
//        }
//        catch (SAXException | IOException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("VI ER I CURRENCY TRÅDEN, tid = :"+System.currentTimeMillis());
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(new XmlReader());
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
        }
        catch (SAXException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
