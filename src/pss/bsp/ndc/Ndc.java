package pss.bsp.ndc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.http.client.ClientProtocolException;
import org.iata.ndc.ClientException;
import org.iata.ndc.NdcClient;
import org.iata.ndc.builder.AirShoppingRQBuilder;
import org.iata.ndc.builder.AirShoppingRQBuilder.Traveler;
import org.iata.ndc.schema.AirShoppingRQ;
import org.iata.ndc.schema.AirShoppingRS;
import org.iata.ndc.schema.AirShoppingRS.OffersGroup.AirlineOffers;
import org.iata.ndc.schema.AirShoppingRS.OffersGroup.AirlineOffers.AirlineOffer;

public class Ndc {

    public static void main(String[] args) {
        // Endpoint y API Key de ejemplo (ficticio)
        NdcClient ndcClient = new NdcClient(
            "https://api.airfranceklm.com/opendata/flightoffers", 
            "39en69p659wpywm52ps4nmfh"
        );

        shopping(ndcClient);
    }

    public static void shopping(NdcClient ndcClient){
        AirShoppingRQBuilder builder = new AirShoppingRQBuilder();

        builder.addTravelAgencySender("MiAgenciaDeViajes", "00000000", "AG1234");
        builder.addOriginDestination("MEX", "GDL", new Date());
        builder.addAnonymousTraveler(Traveler.ADT);

        AirShoppingRQ request = builder.build();

        // ***** LOG: Imprimir/registrar el XML que se envía
        System.out.println("=== REQUEST XML ===");
        System.out.println(toXML(request));

        try {
            // Llamar al cliente NDC
            AirShoppingRS response = ndcClient.airShopping(request);

            // ***** LOG: Imprimir/registrar el XML que llega en la respuesta
            System.out.println("=== RESPONSE XML ===");
            System.out.println(toXML(response));

            // Procesar la respuesta (ejemplo)
            for (AirlineOffers offers : response.getOffersGroup().getAirlineOffers()) {
                for (AirlineOffer offer : offers.getAirlineOffer()) {
                    System.out.println("Vuelo (totalPrice): " + offer.getTotalPrice());
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Request AirShoppingRQ construido con éxito");
    }

    // Método auxiliar para convertir un objeto NDC a XML
    private static String toXML(Object jaxbObject) {
        try {
            // Ajusta el paquete según dónde estén generadas tus clases (org.iata.ndc.schema en este caso).
            JAXBContext context = JAXBContext.newInstance("org.iata.ndc.schema");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter writer = new StringWriter();
            marshaller.marshal(jaxbObject, writer);
            return writer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al convertir a XML: " + e.getMessage();
        }
    }
}
