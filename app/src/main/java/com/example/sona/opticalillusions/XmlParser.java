package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.res.XmlResourceParser;

import com.example.sona.opticalillusions.model.Illusion;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to parse data about illusions from XML file.
 * Created by Soňa on 02-May-17.
 */

public class XmlParser extends Activity {

    private ArrayList<Illusion> list = new ArrayList<>();

    /**
     * Returns list of parsed data.
     *
     * @return ArrayList
     */
    public ArrayList<Illusion> getList() {
        return list;
    }

    /**
     * Reads an XML file with data used in the app.
     *
     * @param parser parser
     * @throws IOException            exception thrown when an error occurs
     * @throws XmlPullParserException exception thrown when an error occurs
     */
    public void processData(XmlResourceParser parser) throws IOException, XmlPullParserException {
        int eventType = -1;
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                String illusionValue = parser.getName();
                if (illusionValue.equals("illusion")) {
                    String name = parser.getAttributeValue(null, "name");
                    String category = parser.getAttributeValue(null, "category");
                    String description = parser.getAttributeValue(null, "description");
                    String animation = parser.getAttributeValue(null, "animation");
                    boolean isFavourite = Boolean.parseBoolean(parser.getAttributeValue(null, "isFavourite"));
                    saveValues(name, category, description, animation, isFavourite);
                }
            }
            eventType = parser.next();
        }
    }

    /**
     * Creates an Illusion object from parsed element in the XML file and saves it into a list.
     *
     * @param name        of the illusion
     * @param category    of the illusion
     * @param description of the illusion
     * @param animation   of the illusion
     * @param isFavourite value of the illusion
     */
    private void saveValues(String name, String category, String description, String animation, boolean isFavourite) {
        Illusion illusion = new Illusion(name, category, description, animation, isFavourite);
        list.add(illusion);
    }
}
