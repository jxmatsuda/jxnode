package com.jxpath.jxnode.util;

import java.io.CharArrayWriter;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLReaderAdapter;

import com.jxpath.jxnode.JxNode;

public class JxReader extends XMLReaderAdapter {

    //------------------------
    // field vairables
    //------------------------
    private JxNode          mTopNode     = null;
    private JxNode          mCurrentNode = null;
    private Stack<JxNode>   mNodeStack   = null;
    private String          mErrorMsg    = null;
    
    //------------------------
    // constructor
    //------------------------
    public JxReader() throws SAXException {
        super();
        this.setErrorHandler( new MyErrorHander() );
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
    
    //--------------------------------------
    // public method( need to implement)
    //--------------------------------------
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

    @Override
    public void setErrorHandler(ErrorHandler aArg0) {
    }

    //------------------------
    // inner class
    //------------------------
    class MyErrorHander implements ErrorHandler{
        @Override
        public void error(SAXParseException aException) throws SAXException {
            mErrorMsg = aException.getMessage();
        }
        @Override
        public void fatalError(SAXParseException aException) throws SAXException {
            mErrorMsg = aException.getMessage();
        }
        @Override
        public void warning(SAXParseException aException) throws SAXException {
        }
        
    }
}
