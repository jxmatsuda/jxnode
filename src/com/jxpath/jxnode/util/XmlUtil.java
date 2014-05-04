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
     * @param a_text output data
     */
    public static void toWriter(Writer a_writer, String a_text)
    throws IOException{
        char[] chars = a_text.toCharArray();
        for( int i=0; i<chars.length; i++ ){
            char val = chars[i];
            switch(val){
            case '>':
                a_writer.write( GT );
                break;
            case '<':
                a_writer.write( LT );
                break;
            case '\'':
                a_writer.write( APOS );
                break;
            case '\"':
                a_writer.write( QUOT );
                break;
            case '&':
                a_writer.write( AMP );
                break;
            default:
                a_writer.write(val);
            }
        }
    }
    //----------------------------
    // private methods
    //---------------------------

}
