package com.jxpath.jxnode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jxpath.jxnode.util.XmlUtil;

/*
 * Convenient XML Wrapper
 * @author jxmatsuda
 */
public class JxNode implements iJxElement{
    //-----------------------------
    // constants
    //-----------------------------
    
    //-----------------------------
    // static variables
    //-----------------------------

    //-----------------------------
    // field variables
    //-----------------------------
    /** encoding */
    private String m_encoding = "UTF-8";
    
    /** node name */
    private String m_name;

    /** if only text, use only this */
    private String m_text = null;
        
    /** child element list */
    private ArrayList<iJxElement> m_childList = null;

    //-----------------------------
    // constractors
    //-----------------------------
    /**
     * disable default constractor
     */
    @SuppressWarnings("unused")
    private JxNode(){}

    /**
     * node name constractor
     */
    public JxNode(String a_name){
        m_name = a_name.intern();
    }

    /**
     * node name and value
     */
    public JxNode(String a_name, String a_text){
        m_name = a_name.intern();
        m_text = a_text.intern();
    }
    
    //-----------------------------
    // public methods
    //-----------------------------
    /**
     * get name
     * @return node name
     */
    public String getName() {
        return m_name;
    }

    /**
     * get text
     * @return node text
     */
    protected String getText() {
        return m_text;
    }
    
    /**
     * set text
     * @param a_text
     */
    public void setText(String a_text){
        m_text = a_text.intern();
    }
    
    /**
     * set attribute
     * @param a_name
     * @param a_text
     */
    public void setAtt(String a_name, String a_text){
        synchronized( this ){
            iJxElement element = getFirstElement( a_name, eElementType.ATTRIBUTE);
            if( element != null ){
                // overwrite att value
                JxAttribute att = (JxAttribute)element;
                att.setText( a_text );
                
            } else {
                // not exists same att name, then append
                JxAttribute att = new JxAttribute(a_name, a_text);
                this.addChild(att);
            }
        }
    }
    
    /**
     * return first child node which matches name.
     * @param a_name
     * @return child node. if not found, return null.
     */
    public JxNode getFirstNode(String a_name){
        synchronized( this ){
            iJxElement element = getFirstElement( a_name, eElementType.NODE);
            if( element != null ){
                // found
                JxNode child = (JxNode)element;
                return child;
            }
        }
        
        // not found
        return null;
    }

    /**
     * return first child text which matches name.
     * @param a_name
     * @return text. if not found, return null.
     */
    public String getFirstNodeText(String a_name){
        synchronized( this ){
            iJxElement element = getFirstElement( a_name, eElementType.NODE);
            if( element != null ){
                // found
                JxNode child = (JxNode)element;
                return child.getText();
            }
        }
        
        // not found
        return null;
    }
    
    /**
     * return array of child nodes
     * @param a_name node name
     * @return array of child nodes. if no matched child, return array of size=0.
     */
    public JxNode[] getNodes(String a_name){
        ArrayList<JxNode> resultList = new ArrayList<JxNode>();
        synchronized( this ){
            if( m_childList == null || a_name ==  null){
                return null;
            }
            
            Iterator<iJxElement> iterator = m_childList.iterator();
            while( iterator.hasNext() ){
                iJxElement element = iterator.next();
                if( element.getType() == eElementType.NODE){
                    String name = element.getName();
                    if( name.equals( a_name )){
                        // found
                        resultList.add((JxNode)element);
                    }
                }
            }
        }
        
        // return
        JxNode[] result = new JxNode[resultList.size()];
        resultList.toArray(result);
        return result;
    }
    
    /**
     * get attribute
     * @param a_text
     * @return text( if not exists, return null )
     */
    public String getAtt(String a_name, String a_text){
        String result = null;
        
        synchronized( this ){
            if( m_childList == null ){
                return null;
            }
            
            Iterator<iJxElement> iterator = m_childList.iterator();
            while( iterator.hasNext() ){
                iJxElement element = iterator.next();
                if( element.getType() == eElementType.ATTRIBUTE ){
                    JxAttribute att = (JxAttribute)element;
                    String name = att.getName();
                    if( name.equals( a_name )){
                        // found
                        result = att.getText();
                        break;
                    }
                }
            }
        }
        
        return result;
    }

    /**
     * return copy of attribute Map( not sort )
     * @return Map ( if no attribute, return map of size=0 )
     */
    public LinkedHashMap<String, String> getAttMap(){
        LinkedHashMap<String, String> resultMap = new LinkedHashMap<String, String>();
        
        synchronized( this ){
            if( m_childList == null ){
                return resultMap;
            }
            Iterator<iJxElement> iterator = m_childList.iterator();
            while( iterator.hasNext()){
                iJxElement element = iterator.next();
                if( element.getType() == eElementType.ATTRIBUTE ){
                    JxAttribute att = (JxAttribute)element;
                    String name = att.getName();
                    String text = att.getText();
                    resultMap.put(name, text);
                }
            }
        }
        
        return resultMap;
    }
    
    /**
     * add child node which name is aName.
     * @param a_name
     * @return created node
     */
    public JxNode addNode(String a_name){
        JxNode childNode = new JxNode(a_name);
        addChild( childNode );
        return childNode;
    }

    /**
     * add child node which name is aName.
     * @param a_name
     * @return created node
     */
    public JxNode addNode(String a_name, String a_text){
        JxNode childNode = new JxNode(a_name, a_text);
        addChild( childNode );
        return childNode;
    }

    /**
     * add child nodek which name is aName.
     * @param a_node
     */
    public void addNode(JxNode a_node){
        addChild( a_node );
    }
    
    /**
     * add text node
     * @param aName
     */
    public void addText(String a_text){
        if( m_text == null ){
            m_text = a_text.intern();
        } else {
            JxText childText = new JxText(a_text);
            addChild( childText );
        }
    }

    /**
     * add comment node
     * @param a_text
     */
    public void addComment(String a_text){
        JxComment childComment = new JxComment(a_text);
        addChild( childComment );
    }
    
    /**
     * toString() without declaration.
     * Usefull for IDE debugger
     * @return XML AS String
     */
    public String toString(){
        StringWriter writer = new StringWriter();
        try{
            toWriter( writer, 0);
            writer.close();
        }catch( Exception e ){}
        return writer.toString();
    }
    
    /**
     * write XML to stream.
     * This method doesn't close aOut.
     * @param a_out writer target
     * @throws IOException
     */
    public void toStream(OutputStream a_out)throws IOException{
        Writer writer = new OutputStreamWriter( a_out, m_encoding );
        BufferedWriter bw = new BufferedWriter(writer);
        toWriter( bw, 0);
        writer.close();
    }

    /**
     * write XML to file
     * @param a_file output file
     * @throws IOException
     */
    public void toFile(File a_file)throws IOException{
        OutputStream out = null;
        try{
            out = new FileOutputStream(a_file);
            toStream( out );
        }finally{
            if( out != null ){
                out.close();
            }
        }
        
    }
    
    /**
     * type = NODE
     */
    @Override
    public eElementType getType() {
        return eElementType.NODE;
    }

    /**
     * helper for writer
     * @param a_writer output writer
     * @param a_depth indent level(0,1,2...)
     * @throws IOException 
     */
    @Override
    public void toWriter(Writer a_writer, int a_depth) throws IOException {
        // begin indent
        for( int i=0 ; i<a_depth; i++ ){
            a_writer.write( "  ");
        }
        
        Map<String, String> attMap = getAttMap();
        boolean hasChildNode = false;
        if( m_childList != null ){
            if( ( m_childList.size() - attMap.size() ) > 0 ){
                hasChildNode = true;
            }
        }
        if( m_text == null && !hasChildNode ){
            // empty node
            a_writer.write("<" );
            a_writer.write(m_name);
            writeAttribute(a_writer, attMap);
            a_writer.append("/>" );
        } else {
            // start tag
            a_writer.write("<" );
            a_writer.write(m_name);
            writeAttribute(a_writer, attMap);
            a_writer.append(">");
            if( m_text != null ){
                XmlUtil.toWriter(a_writer, m_text);
            }

            if( m_childList != null && m_childList.size() > 0){
                Iterator<iJxElement> iterator = m_childList.iterator();
                while( iterator.hasNext() ){
                    iJxElement element = iterator.next();
                    switch( element.getType() ){
                    case NODE:
                    case COMMENT:
                        a_writer.write("\n");
                        element.toWriter(a_writer, a_depth+1);
                        break;
                    case TEXT:
                        JxText text = (JxText)element;
                        a_writer.write(text.getText());
                        break;
                    default:
                        break;
                    }
                }
                    
                if( hasChildNode ){
                    a_writer.write("\n");
                }
            }
            // end tag
            if( hasChildNode ){
                for( int i=0 ; i<a_depth; i++ ){
                    a_writer.write( "  ");
                }
            }
            a_writer.write("</" );
            a_writer.write(m_name);
            a_writer.write( ">");
        }
    }
    
    //-----------------------------
    // private methods
    //-----------------------------
    /**
     * add new child
     * @param a_child add node
     */
    private void addChild( iJxElement a_child ){
        synchronized(this){
            if(m_childList == null ){
                m_childList = new ArrayList<iJxElement>();
            }
            m_childList.add(a_child);
        }
    }

    /**
     * return first element of this node which matches name and type.
     * @param a_name element name( if comment or after second text, set "" )
     * @param a_type type of element
     * @return found element( if not match, return null )
     */
    private iJxElement getFirstElement(String a_name, eElementType a_type){
        synchronized( this ){
            if( m_childList == null || a_name ==  null){
                return null;
            }
            
            Iterator<iJxElement> iterator = m_childList.iterator();
            while( iterator.hasNext() ){
                iJxElement element = iterator.next();
                if( element.getType() == a_type){
                    String name = element.getName();
                    if( name.equals( a_name )){
                        // found
                        return element;
                    }
                }
            }
        }
        
        // not found
        return null;
    }

    /**
     * write attribute to aWriter
     * @param a_writer
     * @throws IOException
     */
    private void writeAttribute(Writer a_writer, Map<String, String> a_attMap) throws IOException{
        Iterator<Entry<String, String>> iterator = a_attMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            String name = entry.getKey();
            String text = entry.getValue();

            a_writer.write(' ');
            a_writer.write(name);
            a_writer.write("=\"");
            if (text != null) {
                XmlUtil.toWriter(a_writer, text);
            }
            a_writer.append('\"');
        }
    }
}
