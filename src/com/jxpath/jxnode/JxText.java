package com.jxpath.jxnode;

import java.io.IOException;
import java.io.Writer;

import com.jxpath.jxnode.util.XmlUtil;

/*
 * @author jxmatsuda
 */
public class JxText implements iJxElement {

    //-----------------------
    // field variables
    //-----------------------
    /** text value */
    private String mText = null;
    
    //-----------------------
    // constactor
    //-----------------------
    /**
     * 
     * @param aText
     */
    protected JxText(String aText){
        mText = aText.intern();
    }

    //-----------------------
    // public methods
    //-----------------------
    /**
     * @return ""
     */
    public String getName(){
        return "";
    }
    
    /**
     * return this text value
     * @return text value
     */
    protected String getText(){
        return mText;
    }
    
    /**
     * return type
     * @return TEXT
     */
    @Override
    public eElementType getType() {
        return eElementType.TEXT;
    }

    /**
     * helper for writer
     * @param aWriter output writer
     * @param aDepth indent level(0,1,2...) but ignore hire
     * @throws IOException 
     */
    @Override
    public void toWriter(Writer aWriter, int aDepth) throws IOException {
        XmlUtil.toWriter(aWriter, mText);
    }
    
}
