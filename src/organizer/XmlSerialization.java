package organizer;

import java.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlSerialization {

    /**
     * Default constructor
     */
   public XmlSerialization() {}

    /**
     * Serialize data from repository given as a parameter to a file
     * @param repo Repository object
     */
    public static void serialize(Repository repo) {

        XStream xstream = new XStream(new DomDriver());
        String xml = xstream.toXML(repo);

        try {
            xmlToFile(xml);
        }
        catch (MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save String value to a file
     * @param src String value
     * @throws MyException which is custom made exception, throws an exception when an error connected with saving data to file occurs
     */
    private static void xmlToFile(String src) throws MyException {

        FileWriter wr = null;
        try {
            wr = new FileWriter("EventsSerialization.xml");
            wr.write(src);
            wr.close();
        }
        catch (Exception e) {
            throw new MyException("Serialization to XML error", e);
        }
    }

    /**
     * Deserialize data from file to a repository
     * @return deserialized values of fields in a repository
     */
    public static Repository deserialize() {

        XStream xstream = new XStream(new DomDriver());
        String newXml = "";

        try {
            newXml = readXmlFromFile();
        }
        catch (MyException e) {
            e.printStackTrace();
        }

        Repository newRepo = (Repository)xstream.fromXML(newXml);
        return newRepo;
    }

    /**
     * Read data from file
     * @return String values of fields in a repository
     * @throws MyException which is custom made exception, throws an exception when an error connected with saving data from file occurs
     */
    public static String readXmlFromFile() throws MyException {

        try {
            File xmlFile = new File("EventsSerialization.xml");
            Reader fr = new FileReader(xmlFile);
            BufferedReader buff = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = buff.readLine();

            while(line != null) {
                sb.append(line).append("\n");
                line = buff.readLine();
            }
            buff.close();
            return sb.toString();
        }
        catch (Exception e) {
            throw new MyException("Deserialization from XML error", e);
        }
    }
}
