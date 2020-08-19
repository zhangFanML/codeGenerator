package com.git.easyloan.utils.utils;

import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLHelper {
    public XMLHelper() {
    }

    public static Document getLoadingDoc(String file) throws FileNotFoundException, SAXException, IOException {
        return getLoadingDoc((InputStream)(new FileInputStream(file)));
    }

    static Document getLoadingDoc(InputStream in) throws SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(false);
        dbf.setValidating(false);
        dbf.setCoalescing(false);
        dbf.setIgnoringComments(false);

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    InputSource is = new InputSource(new StringReader(""));
                    is.setSystemId(systemId);
                    return is;
                }
            });
            InputSource is = new InputSource(in);
            return db.parse(is);
        } catch (ParserConfigurationException var4) {
            throw new Error(var4);
        }
    }

    private static XMLHelper.NodeData treeWalk(Element elm) {
        XMLHelper.NodeData nodeData = new XMLHelper.NodeData();
        nodeData.attributes = attrbiuteToMap(elm.getAttributes());
        nodeData.nodeName = elm.getNodeName();
        nodeData.childs = new ArrayList();
        nodeData.innerXML = childsAsText(elm, new StringBuffer(), true).toString();
        nodeData.outerXML = nodeAsText(elm, new StringBuffer(), true).toString();
        nodeData.nodeValue = getNodeValue(elm);
        NodeList childs = elm.getChildNodes();

        for(int i = 0; i < childs.getLength(); ++i) {
            Node node = childs.item(i);
            if (node.getNodeType() == 1) {
                nodeData.childs.add(treeWalk((Element)node));
            }
        }

        return nodeData;
    }

    private static StringBuffer childsAsText(Element elm, StringBuffer sb, boolean ignoreComments) {
        NodeList childs = elm.getChildNodes();

        for(int i = 0; i < childs.getLength(); ++i) {
            Node child = childs.item(i);
            nodeAsText(child, sb, ignoreComments);
        }

        return sb;
    }

    private static StringBuffer nodeAsText(Node elm, StringBuffer sb, boolean ignoreComments) {
        if (elm.getNodeType() == 4) {
            CDATASection cdata = (CDATASection)elm;
            sb.append("<![CDATA[");
            sb.append(cdata.getData());
            sb.append("]]>");
            return sb;
        } else if (elm.getNodeType() == 8) {
            if (ignoreComments) {
                return sb;
            } else {
                Comment c = (Comment)elm;
                sb.append("<!--");
                sb.append(c.getData());
                sb.append("-->");
                return sb;
            }
        } else if (elm.getNodeType() == 3) {
            Text t = (Text)elm;
            sb.append(StringHelper.escapeXml(t.getData(), "<&"));
            return sb;
        } else {
            NodeList childs = elm.getChildNodes();
            sb.append("<" + elm.getNodeName());
            attributes2String(elm, sb);
            if (childs.getLength() > 0) {
                sb.append(">");

                for(int i = 0; i < childs.getLength(); ++i) {
                    Node child = childs.item(i);
                    nodeAsText(child, sb, ignoreComments);
                }

                sb.append("</" + elm.getNodeName() + ">");
            } else {
                sb.append("/>");
            }

            return sb;
        }
    }

    private static void attributes2String(Node elm, StringBuffer sb) {
        NamedNodeMap attributes = elm.getAttributes();
        if (attributes != null && attributes.getLength() > 0) {
            sb.append(" ");

            for(int j = 0; j < attributes.getLength(); ++j) {
                sb.append(String.format("%s=\"%s\"", attributes.item(j).getNodeName(), StringHelper.escapeXml(attributes.item(j).getNodeValue(), "<&\"")));
                if (j < attributes.getLength() - 1) {
                    sb.append(" ");
                }
            }
        }

    }

    public static Map<String, String> attrbiuteToMap(NamedNodeMap attributes) {
        if (attributes == null) {
            return new LinkedHashMap();
        } else {
            Map<String, String> result = new LinkedHashMap();

            for(int i = 0; i < attributes.getLength(); ++i) {
                result.put(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
            }

            return result;
        }
    }

    public static String getTextValue(Element valueEle) {
        if (valueEle == null) {
            throw new IllegalArgumentException("Element must not be null");
        } else {
            StringBuilder sb = new StringBuilder();
            NodeList nl = valueEle.getChildNodes();

            for(int i = 0; i < nl.getLength(); ++i) {
                Node item = nl.item(i);
                if ((!(item instanceof CharacterData) || item instanceof Comment) && !(item instanceof EntityReference)) {
                    if (item instanceof Element) {
                        sb.append(getTextValue((Element)item));
                    }
                } else {
                    sb.append(item.getNodeValue());
                }
            }

            return sb.toString();
        }
    }

    public static String getNodeValue(Node node) {
        if (node instanceof Comment) {
            return null;
        } else if (node instanceof CharacterData) {
            return ((CharacterData)node).getData();
        } else if (node instanceof EntityReference) {
            return node.getNodeValue();
        } else {
            return node instanceof Element ? getTextValue((Element)node) : node.getNodeValue();
        }
    }

    public XMLHelper.NodeData parseXML(InputStream in) throws SAXException, IOException {
        Document doc = getLoadingDoc(in);
        return treeWalk(doc.getDocumentElement());
    }

    public XMLHelper.NodeData parseXML(File file) throws SAXException, IOException {
        FileInputStream in = new FileInputStream(file);

        XMLHelper.NodeData var3;
        try {
            var3 = this.parseXML((InputStream)in);
        } finally {
            in.close();
        }

        return var3;
    }

    public static String getXMLEncoding(InputStream inputStream) throws UnsupportedEncodingException, IOException {
        return getXMLEncoding(IOHelper.toString("UTF-8", inputStream));
    }

    public static String getXMLEncoding(String s) {
        if (s == null) {
            return null;
        } else {
            Pattern p = Pattern.compile("<\\?xml.*encoding=[\"'](.*)[\"']\\?>");
            Matcher m = p.matcher(s);
            return m.find() ? m.group(1) : null;
        }
    }

    public static String removeXmlns(File file) throws IOException {
        InputStream forEncodingInput = new FileInputStream(file);
        String encoding = getXMLEncoding((InputStream)forEncodingInput);
        forEncodingInput.close();
        InputStream input = new FileInputStream(file);
        String xml = IOHelper.toString(encoding, input);
        xml = removeXmlns(xml);
        input.close();
        return xml;
    }

    public static String removeXmlns(String s) {
        if (s == null) {
            return null;
        } else {
            s = s.replaceAll("(?s)xmlns=['\"].*?['\"]", "");
            s = s.replaceAll("(?s)\\w*:schemaLocation=['\"].*?['\"]", "");
            return s;
        }
    }

    public static Map<String, String> parse2Attributes(String attributes) {
        Map result = new HashMap();
        Pattern p = Pattern.compile("(\\w+?)=['\"](.*?)['\"]");
        Matcher m = p.matcher(attributes);

        while(m.find()) {
            result.put(m.group(1), StringHelper.unescapeXml(m.group(2)));
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException, SAXException, IOException {
        String file = "D:/dev/workspaces/alipay/ali-generator/generator/src/table_test.xml";
        XMLHelper.NodeData nd = (new XMLHelper()).parseXML((InputStream)(new FileInputStream(new File(file))));
        Map table = nd.attributes;
        List columns = nd.childs;
        System.out.println(table);
        System.out.println(columns);
    }

    public static class NodeData {
        public String nodeName;
        public String nodeValue;
        public String innerXML;
        public String outerXML;
        public Map<String, String> attributes = new HashMap();
        public List<NodeData> childs = new ArrayList();

        public NodeData() {
        }

        public String toString() {
            return "nodeName=" + this.nodeName + ",attributes=" + this.attributes + " nodeValue=" + this.nodeValue + " child:\n" + this.childs;
        }

        public Map<String, String> nodeNameAsAttributes(String nodeNameKey) {
            Map map = new HashMap();
            map.putAll(this.attributes);
            map.put(nodeNameKey, this.nodeName);
            return map;
        }

        public List<Map<String, String>> childsAsListMap() {
            List<Map<String, String>> result = new ArrayList();
            Iterator i$ = this.childs.iterator();

            while(i$.hasNext()) {
                XMLHelper.NodeData c = (XMLHelper.NodeData)i$.next();
                Map map = new LinkedHashMap();
                map.put(c.nodeName, c.nodeValue);
                result.add(map);
            }

            return result;
        }
    }
}
