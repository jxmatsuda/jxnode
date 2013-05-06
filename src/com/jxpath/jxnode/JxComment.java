package com.jxpath.jxnode;

import java.io.IOException;
import java.io.Writer;

import com.jxpath.jxnode.util.XmlUtil;

/*
 * @author jxmatsuda
 */
public class JxComment implements iJxElement {

    //-----------------------
    // field variables
    //-----------------------
    /** text value */
    private String mText = null;
        
    //-----------------------
    // constactor
    //-----------------------
    /**
     * @param aText attribute text
     */
    protected JxComment(String aText){
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
        return eElementType.COMMENT;
    }

    /**
     * helper for writer
     * @param aWriter output writer
     * @param aDepth indent level(0,1,2...)
     * @throws IOException 
     */
    @Override
    public void toWriter(Writer aWriter, int aDepth) throws IOException {
        for( int i=0; i<aDepth; i++){
            aWriter.write( "  " );
        }
        aWriter.write("<!--");
        if( mText != null ){
            XmlUtil.toWriter(aWriter, mText);
        }
        aWriter.append("-->");
    }
}
