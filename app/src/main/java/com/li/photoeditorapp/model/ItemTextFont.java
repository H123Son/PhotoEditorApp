package com.li.photoeditorapp.model;

import android.graphics.Typeface;


public class ItemTextFont {
    private Typeface textFont;
    private String fontName;

    public ItemTextFont() {
    }

    public ItemTextFont(Typeface textFont, String fontName) {
        this.textFont = textFont;
        this.fontName = fontName;
    }

    public Typeface getTextFont() {
        return textFont;
    }

    public void setTextFont(Typeface textFont) {
        this.textFont = textFont;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
}
