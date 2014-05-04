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
    private String m_text = null;
    
    //-----------------------
    // constactor
    //-----------------------
    /**
     * 
     * @param a_text
     */
    protected JxText(String a_text){
        m_text = a_text.intern();
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
        return m_text;
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
     * @param a_writer output writer
     * @param a_depth indent level(0,1,2...) but ignore hire
     * @throws IOException 
     */
    @Override
    public void toWriter(Writer a_writer, int a_depth) throws IOException {
        XmlUtil.toWriter(a_writer, m_text);
    }
    
}
