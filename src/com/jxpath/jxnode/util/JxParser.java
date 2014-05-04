package com.jxpath.jxnode.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;

import org.xml.sax.InputSource;
import com.jxpath.jxnode.JxNode;

public class JxParser {
    /**
     * parse file
     * @param a_file input file
     * @return JxNode from file
     * @throws Exception
     */
    public static JxNode parse( File a_file ) throws Exception{
        FileInputStream in = null;
        JxNode node = null;
        
        if( !a_file.exists() || !a_file.isFile() ){
            throw new Exception( "File( " + a_file.getAbsolutePath() + " ) not found!");
        }
        
        try {
            in = new FileInputStream(a_file);
            node = parse(new InputSource(in));
            
        } finally {
            in.close();
        }
        return node;
    }

    /**
     * parse xml string
     * @param String of Xml
     * @return JxNode from string
     * @throws Exception
     */
    public static JxNode parse( String a_xml ) throws Exception{
        JxNode node = parse( new InputSource(new StringReader( a_xml )));
        return node;
    }

    /**
     * parse from inputstream.
     * (caution) this doesn't close inputstream.
     *
     * @param inputstream
     * @return JxNode from inputstream
     * @throws Exception
     */
    public static JxNode parse( InputStream a_in ) throws Exception{
        JxNode node = parse( new InputSource(a_in));
        return node;
    }
    
    //------------------------
    // private
    //------------------------
    /**
     * parse common
     * @param a_src
     * @return JxNode read from aSrc
     * @throws Exception
     */
    private static JxNode parse( InputSource a_src ) throws Exception{
        JxReader reader = new JxReader();
        JxNode   result = null;
        
        String   error  = null;
        try{
            reader.parse( a_src );
            result = reader.getTopNode();
            error = reader.getError();
            
        }catch( Exception e ){
            error = reader.getError();
            if( error == null ){
                error = e.getMessage();
            }
            
        }finally{
            if( error != null ){
                throw new Exception( error );
            }
        }
        
        return result;
    }
}
