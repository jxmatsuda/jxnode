package com.jxpath.jxnode;

import java.io.IOException;
import java.io.Writer;

import com.jxpath.jxnode.util.XmlUtil;

/*
 * @autor jxmatsuda
 */
public class JxAttribute implements iJxElement {

    //-----------------------
    // field variables
    //-----------------------
    /** attribute name */
    private String mName = null;
    
    /** text value */
    private String mText = null;
        
    //-----------------------
    // constractor
    //-----------------------
    /**
     * @param aName attribute name
     * @param aText attribute text
     */
    protected JxAttribute(String aName, String aText){
        mName = aName.intern();
        mText = aText.intern();
    }

    //-----------------------
    // public methods
    //-----------------------
    /**
     * return type
     * @return attribute name
     */
    public String getName() {
        return mName;
    }

    /**
     * return this text value
     * @return text value
     */
    protected String getText(){
        return mText;
    }

    /**
     * set text
     */
    protected void setText(String aText){
        mText = aText.intern();
    }
    
    /**
     * return type
     * @return TEXT
     */
    @Override
    public eElementType getType() {
        return eElementType.ATTRIBUTE;
    }

    /**
     * helper for writer
     * @param aWriter output writer
     * @param aDepth don't care
     * @throws IOException 
     */
    @Override
    public void toWriter(Writer aWriter, int aDepth) throws IOException {
        aWriter.write(mName);
        aWriter.write("=\"");
        if( mText != null ){
            XmlUtil.toWriter(aWriter, mText);
        }
        aWriter.append('\"');
    }
}
