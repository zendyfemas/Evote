package com.evote.parser;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.evote.model.Calon;

public class ParsedCalonDataSet {
	private ArrayList<Calon> list=new ArrayList<Calon>();
	private String URL;
	private String KEY_ITEM="calon";
	private String KEY_ID="id_calon";
	private String KEY_NAMA="nama_user";
	private String KEY_NO="no_urut";
	private String KEY_PHOTO="photo";
	private String KEY_VISI="visi";
	private String KEY_MISI="misi";
	private String KEY_HASIL="hasil";
	private String KEY_ANGKATAN="angkatan";
	private String KEY_NIM="nim_user";
	public ParsedCalonDataSet(){
		URL=Constant.url+"calon.php";
	
	}
	public ArrayList<Calon> parse() throws Exception{
		XMLParser parser = new XMLParser();
	    String xml = parser.getXmlFromUrl(URL); // getting XML
	    Document doc = parser.getDomElement(xml); // getting DOM element
	
	    NodeList nl = doc.getElementsByTagName(KEY_ITEM);
	    // looping through all item nodes <item>
	    for (int i = 0; i < nl.getLength(); i++) {
	        // creating new HashMap
	        Calon map=new Calon();
	        Element e = (Element) nl.item(i);
	        // adding each child node to HashMap key => value
	        map.setId_calon(parser.getValue(e, KEY_ID));
	        map.setNama(parser.getValue(e, KEY_NAMA));
	        map.setNo_urut(parser.getValue(e, KEY_NO));
	        map.setPhoto(parser.getValue(e, KEY_PHOTO));
	        map.setHasil(parser.getValue(e, KEY_HASIL));
	        map.setVisi(parser.getValue(e, KEY_VISI));
	        map.setMisi(parser.getValue(e, KEY_MISI));
	        map.setAngkatan(parser.getValue(e, KEY_ANGKATAN));
	        map.setNim(parser.getValue(e, KEY_NIM));
	        // adding HashList to ArrayList
	        list.add(map);
	    }
	    return list;
	}
	
}
