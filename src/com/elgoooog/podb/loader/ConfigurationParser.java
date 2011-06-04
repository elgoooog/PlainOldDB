package com.elgoooog.podb.loader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.*;

/**
 * @author Nicholas Hauschild
 *         Date: 6/3/11
 *         Time: 12:04 AM
 */
public class ConfigurationParser {
    public Map<String, List<String>> parse(InputStream stream) {
        XMLStreamReader streamReader = null;
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            streamReader = inputFactory.createXMLStreamReader(stream);

            return parse(streamReader);
        } catch (XMLStreamException e) {
            throw new RuntimeException("failed to parse stream", e);
        } finally {
            try {
                if (streamReader != null) {
                    streamReader.close();
                }
            } catch (Exception e) {
                // tough luck
            }
        }
    }

    protected Map<String, List<String>> parse(XMLStreamReader streamReader) throws XMLStreamException {
        String currentElement = "";
        Map<String, List<String>> config = new HashMap<String, List<String>>();
        Stack<String> currentXPath = new Stack<String>();

        while(streamReader.hasNext()) {
            int current = streamReader.next();
            switch(current) {
                case XMLStreamReader.START_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    currentXPath.push(currentElement);
                    break;
                case XMLStreamReader.END_ELEMENT:
                    currentXPath.pop();
                    if(currentXPath.size() > 0) {
                        currentElement = currentXPath.peek();
                    }
                    break;
                case XMLStreamReader.CHARACTERS:
                    if(!"podb".equals(currentElement)) {
                        List<String> configValues = config.get(currentElement);
                        if(configValues == null) {
                            configValues = new ArrayList<String>();
                            config.put(currentElement, configValues);
                        }
                        configValues.add(streamReader.getText().trim());
                    }
                    break;
                default:
                    break;
            }
        }
        return config;
    }
}
