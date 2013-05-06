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
    private JxNode          mTopNode     = null;
    private JxNode          mCurrentNode = null;
    private Stack<JxNode>   mNodeStack   = null;
    private String          mErrorMsg    = null;
    private Locator         mLocator     = null;
    
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
        return mErrorMsg;
    }
    public JxNode getTopNode(){
        return mTopNode;
    }
    
    //-------------------------------------------
    // methods for Interface of ContentHandler
    //-------------------------------------------
    @Override
    public void setDocumentLocator(Locator aLocator){
        mLocator = aLocator;
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
    public void startElement(String aUri, String aLocalName,
            String aQName,  Attributes aAtts) throws SAXException {
        
        // create new node
        JxNode node = new JxNode( aQName );
        for( int i=0; i<aAtts.getLength(); i++ ){
            String name = aAtts.getQName(i);
            String text = aAtts.getValue(i);
            node.setAtt(name,  text);
        }

        // append new node
        if( mTopNode == null ){
            mTopNode = node;
            mCurrentNode = node;

            mNodeStack = new Stack<JxNode>();
            mNodeStack.push( node );
            
        } else {
            mCurrentNode.addNode(node);
            mNodeStack.push(mCurrentNode);
            mCurrentNode = node;
        }
    }

    /**
     * end tag, then set text
     */
    @Override
    public void endElement(String aUri, String aLocalName, String aQName)
            throws SAXException {
        mCurrentNode = mNodeStack.pop();
    }

    @Override
    public void characters(char[] aChars, int aBegin, int aLength)
            throws SAXException {
        String text = new String(aChars, aBegin, aLength ).trim();
        if( text.length() > 0 ){
            mCurrentNode.addText( text );
        }
    }

    //-------------------------------------------
    // methods for Interface of ErrorHandler
    //-------------------------------------------
    @Override
    public void error(SAXParseException aException) throws SAXException {
        mErrorMsg = createErrorMsg( aException );
    }
    @Override
    public void fatalError(SAXParseException aException) throws SAXException {
        mErrorMsg = createErrorMsg( aException );
    }
    @Override
    public void warning(SAXParseException aException) throws SAXException {
    }

    //-----------------------
    // private
    //-----------------------
    /**
     * build error message with Line number and Column number
     * @param aException 
     * @return error message
     */
    private String createErrorMsg( Exception aException){
        StringBuffer buf = new StringBuffer();
        
        if( mLocator != null ){
            int lineNum = mLocator.getLineNumber();
            if( lineNum >= 0 ){
                buf.append( "Line=").append( lineNum );
            }
            int colNum = mLocator.getColumnNumber();
            if( colNum >= 0 ){
                buf.append( ", Column=").append( colNum ).append(" : "); 
            }
        }
        
        buf.append( aException.getMessage() );
        return buf.toString();
    }

    //-------------------------------------------
    // methods for Interface of LexicalHandler
    //-------------------------------------------
    @Override
    public void comment(char[] aCh, int aStart, int aLength)
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
    public void endEntity(String aName) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startCDATA() throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startDTD(String aName, String aPublicId, String aSystemId)
            throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startEntity(String aName) throws SAXException {
        // TODO Auto-generated method stub
        
    }
}
