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
import java.util.Set;

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
    private String mEncoding = "UTF-8";
    
    /** node name */
    private String mName;

    /** if only text, use only this */
    private String mText = null;
        
    /** child element list */
    private ArrayList<iJxElement> mChildList = null;

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
    public JxNode(String aName){
        mName = aName.intern();
    }

    /**
     * node name and value
     */
    public JxNode(String aName, String aText){
        mName = aName.intern();
        mText = aText.intern();
    }
    
    //-----------------------------
    // public methods
    //-----------------------------
    /**
     * get name
     * @return node name
     */
    public String getName() {
        return mName;
    }

    /**
     * get text
     * @return node text
     */
    protected String getText() {
        return mText;
    }
    
    /**
     * set text
     * @param aText
     */
    public void setText(String aText){
        mText = aText.intern();
    }
    
    /**
     * set attribute
     * @param aName
     * @param aText
     */
    public void setAtt(String aName, String aText){
        synchronized( this ){
            iJxElement element = getFirstElement( aName, eElementType.ATTRIBUTE);
            if( element != null ){
                // overwrite att value
                JxAttribute att = (JxAttribute)element;
                att.setText( aText );
                
            } else {
                // not exists same att name, then append
                JxAttribute att = new JxAttribute(aName, aText);
                this.addChild(att);
            }
        }
    }
    
    /**
     * return first child node which matches name.
     * @param aName
     * @return child node. if not found, return null.
     */
    public JxNode getFirstNode(String aName){
        synchronized( this ){
            iJxElement element = getFirstElement( aName, eElementType.NODE);
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
     * @param aName
     * @return text. if not found, return null.
     */
    public String getFirstNodeText(String aName){
        synchronized( this ){
            iJxElement element = getFirstElement( aName, eElementType.NODE);
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
     * @param aName node name
     * @return array of child nodes. if no matched child, return array of size=0.
     */
    public JxNode[] getNodes(String aName){
        ArrayList<JxNode> resultList = new ArrayList<JxNode>();
        synchronized( this ){
            if( mChildList == null || aName ==  null){
                return null;
            }
            
            Iterator<iJxElement> iterator = mChildList.iterator();
            while( iterator.hasNext() ){
                iJxElement element = iterator.next();
                if( element.getType() == eElementType.NODE){
                    String name = element.getName();
                    if( name.equals( aName )){
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
     * @param aText
     * @return text( if not exists, return null )
     */
    public String getAtt(String aName, String aText){
        String result = null;
        
        synchronized( this ){
            if( mChildList == null ){
                return null;
            }
            
            Iterator<iJxElement> iterator = mChildList.iterator();
            while( iterator.hasNext() ){
                iJxElement element = iterator.next();
                if( element.getType() == eElementType.ATTRIBUTE ){
                    JxAttribute att = (JxAttribute)element;
                    String name = att.getName();
                    if( name.equals( aName )){
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
            if( mChildList == null ){
                return resultMap;
            }
            Iterator<iJxElement> iterator = mChildList.iterator();
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
     * @param aName
     * @return created node
     */
    public JxNode addNode(String aName){
        JxNode childNode = new JxNode(aName);
        addChild( childNode );
        return childNode;
    }

    /**
     * add child node which name is aName.
     * @param aName
     * @return created node
     */
    public JxNode addNode(String aName, String aText){
        JxNode childNode = new JxNode(aName, aText);
        addChild( childNode );
        return childNode;
    }

    /**
     * add child nodek which name is aName.
     * @param aNode
     */
    public void addNode(JxNode aNode){
        addChild( aNode );
    }
    
    /**
     * add text node
     * @param aName
     */
    public void addText(String aText){
        if( mText == null ){
            mText = aText.intern();
        } else {
            JxText childText = new JxText(aText);
            addChild( childText );
        }
    }

    /**
     * add comment node
     * @param aText
     */
    public void addComment(String aText){
        JxComment childComment = new JxComment(aText);
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
     * @param aOut writer target
     * @throws IOException
     */
    public void toStream(OutputStream aOut)throws IOException{
        Writer writer = new OutputStreamWriter( aOut, mEncoding );
        BufferedWriter bw = new BufferedWriter(writer);
        toWriter( bw, 0);
        writer.close();
    }

    /**
     * write XML to file
     * @param aFile output file
     * @throws IOException
     */
    public void toFile(File aFile)throws IOException{
        OutputStream out = null;
        try{
            out = new FileOutputStream(aFile);
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
     * @param aWriter output writer
     * @param aDepth indent level(0,1,2...)
     * @throws IOException 
     */
    @Override
    public void toWriter(Writer aWriter, int aDepth) throws IOException {
        // begin indent
        for( int i=0 ; i<aDepth; i++ ){
            aWriter.write( "  ");
        }
        
        Map<String, String> attMap = getAttMap();
        boolean hasChildNode = false;
        if( mChildList != null ){
            if( ( mChildList.size() - attMap.size() ) > 0 ){
                hasChildNode = true;
            }
        }
        if( mText == null && !hasChildNode ){
            // empty node
            aWriter.write("<" );
            aWriter.write(mName);
            writeAttribute(aWriter, attMap);
            aWriter.append("/>" );
        } else {
            // start tag
            aWriter.write("<" );
            aWriter.write(mName);
            writeAttribute(aWriter, attMap);
            aWriter.append(">");
            if( mText != null ){
                XmlUtil.toWriter(aWriter, mText);
            }

            if( mChildList != null && mChildList.size() > 0){
                Iterator<iJxElement> iterator = mChildList.iterator();
                while( iterator.hasNext() ){
                    iJxElement element = iterator.next();
                    switch( element.getType() ){
                    case NODE:
                    case COMMENT:
                        aWriter.write("\n");
                        element.toWriter(aWriter, aDepth+1);
                        break;
                    case TEXT:
                        JxText text = (JxText)element;
                        aWriter.write(text.getText());
                        break;
                    default:
                        break;
                    }
                }
                    
                if( hasChildNode ){
                    aWriter.write("\n");
                }
            }
            // end tag
            if( hasChildNode ){
                for( int i=0 ; i<aDepth; i++ ){
                    aWriter.write( "  ");
                }
            }
            aWriter.write("</" );
            aWriter.write(mName);
            aWriter.write( ">");
        }
    }
    
    //-----------------------------
    // private methods
    //-----------------------------
    /**
     * add new child
     * @param aChild add node
     */
    private void addChild( iJxElement aChild ){
        synchronized(this){
            if(mChildList == null ){
                mChildList = new ArrayList<iJxElement>();
            }
            mChildList.add(aChild);
        }
    }

    /**
     * return first element of this node which matches name and type.
     * @param aName element name( if comment or after second text, set "" )
     * @param aType type of element
     * @return found element( if not match, return null )
     */
    private iJxElement getFirstElement(String aName, eElementType aType){
        synchronized( this ){
            if( mChildList == null || aName ==  null){
                return null;
            }
            
            Iterator<iJxElement> iterator = mChildList.iterator();
            while( iterator.hasNext() ){
                iJxElement element = iterator.next();
                if( element.getType() == aType){
                    String name = element.getName();
                    if( name.equals( aName )){
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
     * @param aWriter
     * @throws IOException
     */
    private void writeAttribute(Writer aWriter, Map<String, String> aAttMap) throws IOException{
        Iterator<Entry<String, String>> iterator = aAttMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            String name = entry.getKey();
            String text = entry.getValue();

            aWriter.write(' ');
            aWriter.write(name);
            aWriter.write("=\"");
            if (text != null) {
                XmlUtil.toWriter(aWriter, text);
            }
            aWriter.append('\"');
        }
    }
}
