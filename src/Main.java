import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.ArrayList;

public class Main {
    static String ruta = "products.xml";
    static String rutaSerializado ="/home/dam/IdeaProjects/Serializacion2/productos.ser";
    public static void main(String[] args) {
        Products products = deserializar();
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        FileWriter fileWriter = getFileWriter();
        XMLStreamWriter writer = getXMLStreamWriter(fileWriter, factory);
        escribirXML(writer, products);
    }

    public static Products deserializar() {
        try (FileInputStream fileIn = new FileInputStream(rutaSerializado);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            ArrayList<Product> productList = (ArrayList<Product>) in.readObject();
            Products products = new Products();
            products.setProducts(productList);
            return products;
        } catch (IOException e) {
            System.out.println("Error al deserializar el archivo " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase Products no encontrada " + e.getMessage());
        }
        return null;
    }

    public static FileWriter getFileWriter() {
        try {
            return new FileWriter(ruta);
        } catch (IOException e) {
            System.out.println("Error al crear el archivo "+ e.getMessage());
        }
        return null;
    }

    public static XMLStreamWriter getXMLStreamWriter (FileWriter fileWriter, XMLOutputFactory factory) {
        try {
            return factory.createXMLStreamWriter(fileWriter);
        } catch (Exception e) {
            System.out.println("Error al crear el XMLStreamWriter "+ e.getMessage());
        }
        return null;
    }

    public static void escribirXML(XMLStreamWriter writer, Products products) {
        try {
            writer.writeStartDocument("1.0");
            writer.writeStartElement("productos");
            for (Product product : products.getProducts()) {
                writer.writeStartElement("producto");
                writer.writeStartElement("codigo");
                writer.writeCharacters(String.valueOf(product.getCodigo()));
                writer.writeEndElement();
                writer.writeStartElement("nombre");
                writer.writeCharacters(product.getDescripcion());
                writer.writeEndElement();
                writer.writeStartElement("precio");
                writer.writeCharacters(String.valueOf(product.getPrecio()));
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error al escribir el archivo XML "+ e.getMessage());
        }
    }
}
