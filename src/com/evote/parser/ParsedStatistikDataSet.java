package com.evote.parser;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.evote.model.Calon;
import com.evote.model.Statistik;

public class ParsedStatistikDataSet {
	private ArrayList<Statistik> list=new ArrayList<Statistik>();
	private String URL;
	private String KEY_ITEM="calon";
	private String KEY_NAMA="nama_user";
	private String KEY_WAKTU="waktu";
	private String KEY_JUMLAH="jumlah";
	public ParsedStatistikDataSet(){
		URL=Constant.url+"statistik.php";
	
	}
	public ArrayList<Statistik> parse() throws Exception{
		XMLParser parser = new XMLParser();
	    String xml = parser.getXmlFromUrl(URL); // getting XML
	    Document doc = parser.getDomElement(xml); // getting DOM element
	
	    NodeList nl = doc.getElementsByTagName(KEY_ITEM);
	    // looping through all item nodes <item>
	    for (int i = 0; i < nl.getLength(); i++) {
	        // creating new HashMap
	        Statistik map=new Statistik();
	        Element e = (Element) nl.item(i);
	        // adding each child node to HashMap key => value
	        map.setNama(parser.getValue(e, KEY_NAMA));
	        map.setJumlah(parser.getValue(e, KEY_JUMLAH));
	        map.setTanggal(parser.getValue(e, KEY_WAKTU).split(" ")[0]);
	        // adding HashList to ArrayList
	        list.add(map);
	    }
	    return list;
	}
}
