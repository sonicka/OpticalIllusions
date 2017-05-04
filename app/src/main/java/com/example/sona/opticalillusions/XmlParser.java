package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.res.XmlResourceParser;

import com.example.sona.opticalillusions.model.Illusion;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Soňa on 02-May-17.
 */

public class XmlParser extends Activity {

    private ArrayList<Illusion> list = new ArrayList<>();

    public ArrayList<Illusion> getList() {
        return list;
    }

    public void processData(XmlResourceParser parser) throws IOException, XmlPullParserException {
        int eventType = -1;
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                String illusionValue = parser.getName();
                if (illusionValue.equals("illusion")) {
                    String name = parser.getAttributeValue(null, "name");
                    String category = parser.getAttributeValue(null, "category");
                    String description = parser.getAttributeValue(null, "description");
                    String thumbnail = parser.getAttributeValue(null, "thumbnail");
                    String picture = parser.getAttributeValue(null, "picture");
                    String animation = parser.getAttributeValue(null, "animation");
                    boolean isFavourite = Boolean.parseBoolean(parser.getAttributeValue(null, "isFavourite"));
                    saveValues(name, category, description, thumbnail, picture, animation, isFavourite);
                }
            }
            eventType = parser.next();
        }
    }

    private void saveValues(String name, String category, String description, String thumbnail, String picture, String animation, boolean isFavourite) {
        Illusion illusion = new Illusion(name, category, description, thumbnail, picture, animation, isFavourite);
        list.add(illusion);
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(illusion);
//            }
//        });
    }
}
