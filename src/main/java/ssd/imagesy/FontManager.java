package ssd.imagesy;

import ssd.imagesy.FontLoader;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FontManager {

    private static final Map<String, FontLoader> caches = new HashMap<String, FontLoader>();

    static {
        caches.put("founderblack", new FontLoader("founderblack", "/founderblack.ttf"));
    }

    public static Font getFont(String name) {
        FontLoader loader = caches.get(name);
        if (loader == null) {
            return null;
        }

        return loader.getFont();
    }

    public static void main(String[] args) {
        System.out.println(getFont("founderblack"));
    }
}


