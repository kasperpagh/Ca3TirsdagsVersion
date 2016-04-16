package xml;

import entity.ExchangeRates;
import facades.CurrencyHandler;
import entity.SingleExchangeRate;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XmlReader extends DefaultHandler implements Runnable
{

    private ExchangeRates er;
    private List<SingleExchangeRate> serList = new ArrayList();
    ;
    private String refcur = "DKK";
    private String dato;
    private final CurrencyHandler hand = new CurrencyHandler();
    SingleExchangeRate ser;

    public XmlReader()
    {
    }

    @Override
    public void startDocument() throws SAXException
    {

//        System.out.println("Start Document (Sax-event)");
    }

    @Override
    public void endDocument() throws SAXException
    {
//        System.out.println("HER ER SER:" + serList.toString() + "HER ER dato:" + dato);
        er = new ExchangeRates(dato, refcur, serList);
        hand.persistExchangeRates(er);
        System.out.println("End Document (Sax-event)");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        ser = new SingleExchangeRate();
//        System.out.print("Element: " + localName + ": ");
        for (int i = 0; i < attributes.getLength(); i++)
        {
    
            if (attributes.getLocalName(i).equalsIgnoreCase("id"))
            {
//                System.out.println("jeg er i dato");
                dato = attributes.getValue(i);
//                System.out.println("her er dato: " + dato);
            }
            if (attributes.getLocalName(i).equalsIgnoreCase("refcur"))
            {
//                System.out.println("jeg er i ref");
                refcur = attributes.getValue(i);
            }
            if (attributes.getLocalName(i).equalsIgnoreCase("code"))
            {
//                System.out.println("code: "+attributes.getLocalName(i));
                ser.setCurrencyCode(attributes.getValue(i));
            }
            if (attributes.getLocalName(i).equalsIgnoreCase("desc"))
            {
//                System.out.println("desc: "+attributes.getLocalName(i));
                ser.setDesc(attributes.getValue(i));
            }
            if (attributes.getLocalName(i).equalsIgnoreCase("rate"))
            {
                
                ser.setRate(attributes.getValue(i));
            }
        }
//        System.out.println("SER: " + ser.getCurrencyCode() + ser.getDesc() + ser.getRate());
        serList.add(ser);
//                System.out.println("jeg er i currency: " + attributes.getValue(0)+ attributes.getValue(1)+ attributes.getValue(2));

//                System.out.print("[Atribute: NAME: " + attributes.getLocalName(i) + " VALUE: " + attributes.getValue(i) + "] ");
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("VI ER I CURRENCY TRÅDEN, tid = :" + System.currentTimeMillis());
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
