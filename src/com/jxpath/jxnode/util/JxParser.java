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
     * @param aFile input file
     * @return JxNode from file
     * @throws Exception
     */
    public static JxNode parse( File aFile ) throws Exception{
        FileInputStream in = null;
        JxNode node = null;
        
        if( !aFile.exists() || !aFile.isFile() ){
            throw new Exception( "File( " + aFile.getAbsolutePath() + " ) not found!");
        }
        
        try {
            in = new FileInputStream(aFile);
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
    public static JxNode parse( String aXml ) throws Exception{
        JxNode node = parse( new InputSource(new StringReader( aXml )));
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
    public static JxNode parse( InputStream aIn ) throws Exception{
        JxNode node = parse( new InputSource(aIn));
        return node;
    }
    
    //------------------------
    // private
    //------------------------
    /**
     * parse common
     * @param aSrc
     * @return JxNode read from aSrc
     * @throws Exception
     */
    private static JxNode parse( InputSource aSrc ) throws Exception{
        JxReader reader = new JxReader();
        JxNode   result = null;
        
        String   error  = null;
        try{
            reader.parse( aSrc );
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
