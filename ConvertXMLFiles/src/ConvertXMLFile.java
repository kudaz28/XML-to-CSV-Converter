/**
 * @date 15/09/2017 
 * 
 * Class is used to convert an XML file / an array of XML files and saving the output to a CSV file.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConvertXMLFile
{
	private static String numericClass, barCode, createdDate, lastModified, physicalFormat;
	private static String currentNumericClass, currentBarCode, currentCreatedDate, currentLastModified, currentPhysicalFormat;
	private static String line;

	public static void main(String[] args) throws Exception 
	{
		String directoryName = "src/resources/files";
		convertDocument(directoryName);
	}

	private static List<File> convertDocument(String directoryName) throws IOException 
	{
		// .............list file
		File directory = new File(directoryName);
		
		List<File> resultList = new ArrayList<File>();

		// get all the files from a directory
		File[] fileList = directory.listFiles();
		resultList.addAll(Arrays.asList(fileList));
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		// track the time taken for conversion
		long start = System.currentTimeMillis();

		System.out.println("File Number \t " + "Barcode \t" + "Created Date \t\t\t" + "Last Modified \t\t\t" + "Physical Format \t\n");

		for (File currentFile : fileList) 
		{
			// variables specified in the CSV
			try 
			{
				if (currentFile.isFile()) 
				{
					currentFile.getAbsolutePath(); 
				} 
				else if(currentFile.isDirectory())
				{
					resultList.addAll(convertDocument(currentFile.getAbsolutePath()));
				}
				
				File outputFile = new File("src/resources/conversion.csv");
				Writer writer = new OutputStreamWriter(new FileOutputStream("pathToFile"), "UTF-8");
				bufferedWriter = new BufferedWriter(writer);
				
				if (!outputFile.exists()) 
				{
					outputFile.createNewFile();
				}
				
				fileWriter = new FileWriter(outputFile.getAbsoluteFile(), true);
				bufferedWriter = new BufferedWriter(fileWriter);

				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document document = documentBuilder.parse(currentFile);

				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();

				// store expressions in a list
				List<XPathExpression> expressionList = new ArrayList<>();

				expressionList.add(0, (XPathExpression) xpath.compile("//properties/entry[@key='orgrma:numericClass']"));
				expressionList.add(1, (XPathExpression) xpath.compile("//properties/entry[@key='orgrma:barcode']"));
				expressionList.add(2, (XPathExpression) xpath.compile("//properties/entry[@key='cm:created']"));
				expressionList.add(3, (XPathExpression) xpath.compile("//properties/entry[@key='cm:modified']"));
				expressionList.add(4, (XPathExpression) xpath.compile("//properties/entry[@key='orgrma:physicalFormat']"));
				
				/**The NodeList interface provides the abstraction of an ordered collection of nodes, without defining or constraining how this collection is implemented. NodeList objects in the DOM are live.
				The items in the NodeList are accessible via an integral index, starting from 0.*/

				/*Store the NodeLists in a collection then loop through each nodeList getting the value stored at a particular node*/

				List<NodeList> nodeListCollection = new ArrayList<>();
				
				nodeListCollection.add(0, (NodeList) expressionList.get(0).evaluate(document, XPathConstants.NODESET));
				nodeListCollection.add(1, (NodeList) expressionList.get(1).evaluate(document, XPathConstants.NODESET));
				nodeListCollection.add(2, (NodeList) expressionList.get(2).evaluate(document, XPathConstants.NODESET));
				nodeListCollection.add(3, (NodeList) expressionList.get(3).evaluate(document, XPathConstants.NODESET));
				nodeListCollection.add(4, (NodeList) expressionList.get(4).evaluate(document, XPathConstants.NODESET));
				
				for(NodeList nodeList : nodeListCollection)
				{
					System.out.println(nodeList.item(0).getTextContent());
				}
				
				NodeList nl = (NodeList) expressionList.get(0).evaluate(document, XPathConstants.NODESET);
				NodeList nl2 = (NodeList) expressionList.get(1).evaluate(document, XPathConstants.NODESET);
				NodeList nl3 = (NodeList) expressionList.get(2).evaluate(document, XPathConstants.NODESET);
				NodeList nl4 = (NodeList) expressionList.get(3).evaluate(document, XPathConstants.NODESET);
				NodeList nl5 = (NodeList) expressionList.get(4).evaluate(document, XPathConstants.NODESET);
				
				for (int i = 0; i < nl.getLength(); i++)
				{
				    Node node = nl.item(i);
				    Element element = (Element) node;
				   
				    numericClass = element.getAttributes().getNamedItem("key").getNodeValue();

				    if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.ELEMENT_NODE)
					{
				    	if(numericClass.equals("orgrma:numericClass"))
					    {
				    		currentNumericClass = element.getTextContent();
					    } 
					}
				}
				
				for (int i = 0; i < nl2.getLength(); i++)
				{
				    Node node = nl2.item(i);
				    Element element = (Element) node;
				    
				    barCode = element.getAttributes().getNamedItem("key").getNodeValue();

				    if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.ELEMENT_NODE)
					{
				    	if(barCode.equals("orgrma:barcode"))
					    {
				    		currentBarCode = element.getTextContent();
					    } 
					}
				}
				
				for (int i = 0; i < nl3.getLength(); i++)
				{
				    Node node = nl3.item(i);
				    Element element = (Element) node;
				    
				    createdDate = element.getAttributes().getNamedItem("key").getNodeValue();

				    if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.ELEMENT_NODE)
					{
				    	if(createdDate.equals("cm:created"))
					    {
					    	currentCreatedDate = element.getTextContent();
					    } 
					}
				}
				
				for (int i = 0; i < nl4.getLength(); i++)
				{
				    Node node = nl4.item(i);
				    Element element = (Element) node;
				    
				    lastModified = element.getAttributes().getNamedItem("key").getNodeValue();

				    if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.ELEMENT_NODE)
					{
				    	if(lastModified.equals("cm:modified"))
					    {
					    	currentLastModified = element.getTextContent();
					    } 
					}
				}
				
				for (int i = 0; i < nl5.getLength(); i++)
				{
				    Node node = nl5.item(i);
				    Element element = (Element) node;
				    
				    physicalFormat = element.getAttributes().getNamedItem("key").getNodeValue();

				    if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.ELEMENT_NODE)
					{
				    	if(physicalFormat.equals("orgrma:physicalFormat"))
					    {
					    	currentPhysicalFormat = element.getTextContent();
					    } 
					}
				}

				line = String.format("%s\t%s\t%s\t%s\t%s", currentNumericClass, currentBarCode, currentCreatedDate, currentLastModified, currentPhysicalFormat);
				
				// WRITE TO THE CSV file
				bufferedWriter.write(currentNumericClass + "," + currentBarCode + "," + currentCreatedDate + "," + currentLastModified + "," + currentPhysicalFormat);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				
				System.out.println(line);
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("Error Occured. Check Console logs...");
			} 
			finally 
			{
				bufferedWriter.close();
			}
		}
		long finish = System.currentTimeMillis();
		System.out.println("Time taken for conversion " + (finish - start) + "ms");
		System.out.println("-----------COMPLETED----------");
		
		return resultList;
	}
}

