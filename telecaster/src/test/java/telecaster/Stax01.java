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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.junit.Ignore;
import org.junit.Test;

public class Stax01 {

	@Test
	public void demo_03() throws Exception {
		// initialize all variables (attributes) to null
		// initialize all variablesFound to false

		// while we have not reached the end of a Reference Transaction

		// --- if the event type is XMLStreamConstants.START_ELEMENT
		// --- --- case statement to check if this element is an attribute we
		// are looking for
		// --- --- if this element is one we are looking for, mark it as true

		// --- if the event type is XMLStreamConstants.CHARACTERS
		// --- --- if statements for the variablesFound
		// --- --- if a variableFound is true
		// --- --- --- get the value from the element and set the variable
		// --- --- --- set the variableFound to false

		// --- peak to the next element
		// --- if the next element is the end of a Reference Transaction
		// --- --- dataRow.put("variable", variable);
		// --- --- set all variables to null

		// Declaring and initializing variables
		List<TreeMap<String, String>> dataRows = new ArrayList<TreeMap<String, String>>();
		TreeMap<String, String> dataRow = new TreeMap<String, String>();

		String sourceName = "null";
		String updateSource = "null";
		String fromIssuer_issuerId = "null";
		String fromIssuer_issuerName = "null";

		Boolean bool_sourceName = false;
		Boolean bool_updateSource = false;
		Boolean bool_fromIssuer_issuerId = false;
		Boolean bool_fromIssuer_issuerName = false;

		StartElement startElement;
		Characters characters;
		String qName;

		// Setting up STAX
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader r = factory.createXMLEventReader(
				new FileInputStream("C:\\All_Git_03\\stax_01\\telecaster\\src\\test\\resources\\xmlTestData2.xml"));
		XMLEvent e;

		// While we have not reached the end of the XML file
		while (r.hasNext()) {
			e = r.nextEvent();

			// if we have reached the end of a Reference Transaction
			if (e.getEventType() == XMLStreamConstants.END_ELEMENT
					&& (e.asEndElement().getName().getLocalPart().equalsIgnoreCase("ReferenceTransaction"))) {
				// save all values that have been accumulated
				dataRow.put("sourceName", sourceName);
				dataRow.put("updateSource", updateSource);
				dataRow.put("fromIssuer_issuerId", fromIssuer_issuerId);
				dataRow.put("fromIssuer_issuerName", fromIssuer_issuerName);

				// add the dataRow to the list of dataRows
				dataRows.add(dataRow);

				// reset the variables to "null"
				sourceName = "null";
				updateSource = "null";
				fromIssuer_issuerId = "null";
				fromIssuer_issuerName = "null";

				// initialize a new dataRow
				dataRow = new TreeMap<String, String>();
			}

			// If we are at the start of an element
			if (e.getEventType() == XMLStreamConstants.START_ELEMENT) {
				startElement = e.asStartElement();
				qName = startElement.getName().getLocalPart();

				if (qName.equalsIgnoreCase("sourceName")) {
					bool_sourceName = true;
				}

				if (qName.equalsIgnoreCase("updateSource")) {
					bool_updateSource = true;
				}

				if (qName.equalsIgnoreCase("fromIssuer")) {
					// while we have not reached the end of fromIssuer
					while (!reachedEnd(e, "fromIssuer")) {
						if (e.getEventType() == XMLStreamConstants.START_ELEMENT) {
							startElement = e.asStartElement();
							qName = startElement.getName().getLocalPart();

							if (qName.equalsIgnoreCase("issuerId")) {
								bool_fromIssuer_issuerId = true;
							}
							if (qName.equalsIgnoreCase("issuerName")) {
								bool_fromIssuer_issuerName = true;
							}
						}
						if (e.getEventType() == XMLStreamConstants.CHARACTERS) {
							characters = e.asCharacters();
							if (bool_fromIssuer_issuerId == true) {
								bool_fromIssuer_issuerId = false;
								fromIssuer_issuerId = characters.getData();
							}
							if (bool_fromIssuer_issuerName == true) {
								bool_fromIssuer_issuerName = false;
								fromIssuer_issuerName = characters.getData();
							}
						}
						e = r.nextEvent();
					}
				}

			}
			if (e.getEventType() == XMLStreamConstants.CHARACTERS) {
				characters = e.asCharacters();
				if (bool_sourceName == true) {
					bool_sourceName = false;
					sourceName = characters.getData();
				}
				if (bool_updateSource == true) {
					bool_updateSource = false;
					updateSource = characters.getData();
				}
			}

		}

		for (TreeMap<String, String> row : dataRows) {
			System.out.println("============= NEW RECORD =============");
			row.forEach((k, v) -> {
				System.out.println(k + " --- " + v);
			});
		}
	}

	public Boolean reachedEnd(XMLEvent e, String name) {
		if (e.getEventType() == XMLStreamConstants.END_ELEMENT
				&& (e.asEndElement().getName().getLocalPart().equalsIgnoreCase("fromIssuer"))) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("restriction")
	@Test
	@Ignore
	public void demo_02() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader r = factory.createXMLEventReader(
				new FileInputStream("C:\\All_Git_03\\stax_01\\telecaster\\src\\test\\resources\\xmlTestData2.xml"));

		while (r.hasNext()) {
			XMLEvent e = r.nextEvent();
			String eType = getEventTypeString(e.getEventType());
			if (eType.equalsIgnoreCase("END_DOCUMENT")) {
				System.out.println("Demo_02 has reached the end of the document");
			}
		}
	}

	@SuppressWarnings("restriction")
	@Test
	@Ignore
	public void demo_EventReader_01() {

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

	public final static String getEventTypeString(int eventType) {
		switch (eventType) {
		case XMLEvent.START_ELEMENT:
			return "START_ELEMENT";

		case XMLEvent.END_ELEMENT:
			return "END_ELEMENT";

		case XMLEvent.PROCESSING_INSTRUCTION:
			return "PROCESSING_INSTRUCTION";

		case XMLEvent.CHARACTERS:
			return "CHARACTERS";

		case XMLEvent.COMMENT:
			return "COMMENT";

		case XMLEvent.START_DOCUMENT:
			return "START_DOCUMENT";

		case XMLEvent.END_DOCUMENT:
			return "END_DOCUMENT";

		case XMLEvent.ENTITY_REFERENCE:
			return "ENTITY_REFERENCE";

		case XMLEvent.ATTRIBUTE:
			return "ATTRIBUTE";

		case XMLEvent.DTD:
			return "DTD";

		case XMLEvent.CDATA:
			return "CDATA";

		case XMLEvent.SPACE:
			return "SPACE";
		}
		return "UNKNOWN_EVENT_TYPE," + eventType;
	}

}
