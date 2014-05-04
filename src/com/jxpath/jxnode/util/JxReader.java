package com.jxpath.jxnode.util;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.XMLReaderAdapter;

import com.jxpath.jxnode.JxNode;

public class JxReader extends XMLReaderAdapter implements LexicalHandler, ErrorHandler{

    //------------------------
    // field vairables
    //------------------------
    private JxNode          m_topNode     = null;
    private JxNode          m_currentNode = null;
    private Stack<JxNode>   m_nodeStack   = null;
    private String          m_errorMsg    = null;
    private Locator         m_locator     = null;
    
    //------------------------
    // constructor
    //------------------------
    public JxReader() throws SAXException {
        super();
        this.setErrorHandler(this);
    }

    //--------------------------------------
    // for appliation
    //--------------------------------------
    /**
     * return parse error msg
     * @return error message. if no error, return null.
     */
    public String getError(){
        return m_errorMsg;
    }
    public JxNode getTopNode(){
        return m_topNode;
    }
    
    //-------------------------------------------
    // methods for Interface of ContentHandler
    //-------------------------------------------
    @Override
    public void setDocumentLocator(Locator a_locator){
        m_locator = a_locator;
    }
    
    /**
     * XML start
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }
    /**
     * XML end
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    /**
     * found tag
     */
    @Override
    public void startElement(String a_uri, String a_localName,
            String a_QName,  Attributes a_atts) throws SAXException {
        
        // create new node
        JxNode node = new JxNode( a_QName );
        for( int i=0; i<a_atts.getLength(); i++ ){
            String name = a_atts.getQName(i);
            String text = a_atts.getValue(i);
            node.setAtt(name,  text);
        }

        // append new node
        if( m_topNode == null ){
            m_topNode = node;
            m_currentNode = node;

            m_nodeStack = new Stack<JxNode>();
            m_nodeStack.push( node );
            
        } else {
            m_currentNode.addNode(node);
            m_nodeStack.push(m_currentNode);
            m_currentNode = node;
        }
    }

    /**
     * end tag, then set text
     */
    @Override
    public void endElement(String a_uri, String a_localName, String a_QName)
            throws SAXException {
        m_currentNode = m_nodeStack.pop();
    }

    @Override
    public void characters(char[] a_chars, int a_begin, int a_length)
            throws SAXException {
        String text = new String(a_chars, a_begin, a_length ).trim();
        if( text.length() > 0 ){
            m_currentNode.addText( text );
        }
    }

    //-------------------------------------------
    // methods for Interface of ErrorHandler
    //-------------------------------------------
    @Override
    public void error(SAXParseException a_exception) throws SAXException {
        m_errorMsg = createErrorMsg( a_exception );
    }
    @Override
    public void fatalError(SAXParseException a_exception) throws SAXException {
        m_errorMsg = createErrorMsg( a_exception );
    }
    @Override
    public void warning(SAXParseException a_exception) throws SAXException {
    }

    //-----------------------
    // private
    //-----------------------
    /**
     * build error message with Line number and Column number
     * @param a_exception 
     * @return error message
     */
    private String createErrorMsg( Exception a_exception){
        StringBuffer buf = new StringBuffer();
        
        if( m_locator != null ){
            int lineNum = m_locator.getLineNumber();
            if( lineNum >= 0 ){
                buf.append( "Line=").append( lineNum );
            }
            int colNum = m_locator.getColumnNumber();
            if( colNum >= 0 ){
                buf.append( ", Column=").append( colNum ).append(" : "); 
            }
        }
        
        buf.append( a_exception.getMessage() );
        return buf.toString();
    }

    //-------------------------------------------
    // methods for Interface of LexicalHandler
    //-------------------------------------------
    @Override
    public void comment(char[] a_char, int a_start, int a_length)
            throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endCDATA() throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endDTD() throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endEntity(String a_name) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startCDATA() throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startDTD(String a_name, String a_publicId, String a_systemId)
            throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startEntity(String a_name) throws SAXException {
        // TODO Auto-generated method stub
        
    }
}
