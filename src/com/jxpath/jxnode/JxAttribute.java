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
    private String m_name = null;
    
    /** text value */
    private String m_text = null;
        
    //-----------------------
    // constractor
    //-----------------------
    /**
     * @param a_name attribute name
     * @param a_text attribute text
     */
    protected JxAttribute(String a_name, String a_text){
        m_name = a_name.intern();
        m_text = a_text.intern();
    }

    //-----------------------
    // public methods
    //-----------------------
    /**
     * return type
     * @return attribute name
     */
    public String getName() {
        return m_name;
    }

    /**
     * return this text value
     * @return text value
     */
    protected String getText(){
        return m_text;
    }

    /**
     * set text
     */
    protected void setText(String a_text){
        m_text = a_text.intern();
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
     * @param a_writer output writer
     * @param a_depth don't care
     * @throws IOException 
     */
    @Override
    public void toWriter(Writer a_writer, int a_depth) throws IOException {
        a_writer.write(m_name);
        a_writer.write("=\"");
        if( m_text != null ){
            XmlUtil.toWriter(a_writer, m_text);
        }
        a_writer.append('\"');
    }
}
