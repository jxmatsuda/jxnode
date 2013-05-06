package com.jxpath.jxnode.util;

import java.io.IOException;
import java.io.Writer;

public class XmlUtil {
    //----------------------------
    // constants
    //----------------------------
    /** escape of > */ 
    private static final char[] GT = new char[]{'&','g','t',';'};

    /** escape of < */ 
    private static final char[] LT = new char[]{'&','l','t',';'};

    /** escape of ' */ 
    private static final char[] APOS = new char[]{'&','a','p','o','s',';'};

    /** escape of " */ 
    private static final char[] QUOT = new char[]{'&','q','u','o','t',';'};

    /** escape of & */ 
    private static final char[] AMP = new char[]{'&','a','m','p',';'};
    
    //----------------------------
    // constructor
    //----------------------------
    private XmlUtil(){};
    
    //----------------------------
    // public methods
    //---------------------------

    /**
     * output to writer
     * @param aOut output writer
     * @param aText output data
     */
    public static void toWriter(Writer aWriter, String aText)
    throws IOException{
        char[] chars = aText.toCharArray();
        for( int i=0; i<chars.length; i++ ){
            char val = chars[i];
            switch(val){
            case '>':
                aWriter.write( GT );
                break;
            case '<':
                aWriter.write( LT );
                break;
            case '\'':
                aWriter.write( APOS );
                break;
            case '\"':
                aWriter.write( QUOT );
                break;
            case '&':
                aWriter.write( AMP );
                break;
            default:
                aWriter.write(val);
            }
        }
    }
    //----------------------------
    // private methods
    //---------------------------

}
