package telecaster;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import org.junit.Test;

public class Stax01 {

	@SuppressWarnings("restriction")
	@Test
	public void demo_01() {

		Boolean bFirstName = false;
		Boolean bLastName = false;
		Boolean bNickName = false;
		Boolean bMarks = false;

		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = factory.createXMLEventReader(
					new FileReader("C:\\All_Git_03\\stax_01\\telecaster\\src\\test\\resources\\xmlTestData.xml"));

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					StartElement startElement = event.asStartElement();
					String qName = startElement.getName().getLocalPart();
					if (qName.equalsIgnoreCase("student")) {
						System.out.println("Start Element -- student");
						Iterator<Attribute> attributes = startElement.getAttributes();
						String rollNo = attributes.next().getValue();
						System.out.println("rollNo -- " + rollNo);
					} else if (qName.equalsIgnoreCase("firstname")) {
						bFirstName = true;
					} else if (qName.equalsIgnoreCase("lastname")) {
						bLastName = true;
					} else if (qName.equalsIgnoreCase("nickname")) {
						bNickName = true;
					} else if (qName.equalsIgnoreCase("marks")) {
						bMarks = true;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					Characters characters = event.asCharacters();
					if (bFirstName) {
						bFirstName = false;
						System.out.println("First Name... " + characters.getData());
					}
					if (bLastName) {
						bLastName = false;
						System.out.println("Last Name... " + characters.getData());
					}
					if (bNickName) {
						bNickName = false;
						System.out.println("Nick Name... " + characters.getData());
					}
					if (bMarks) {
						bMarks = false;
						System.out.println("Marks... " + characters.getData());
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart().equalsIgnoreCase("student")) {
						System.out.println("End Element >>>>> student");
						System.out.println();
					}
					break;
				default:
					break;
				}
			}
			System.out.println("===> EOF <===");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

	}

}
