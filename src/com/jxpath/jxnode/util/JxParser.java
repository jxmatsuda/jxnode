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

        try {
            in = new FileInputStream(aFile);
            JxReader reader = new JxReader();
            reader.parse(new InputSource(in));

            String error = reader.getError();
            if (error != null) {
                throw new Exception(error);
            }
            node = reader.getTopNode();
            
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
        JxReader reader = new JxReader();
        reader.parse( new InputSource(new StringReader( aXml )));
        
        String error = reader.getError();
        if( error != null ){
            throw new Exception( error );
        }
        
        JxNode node = reader.getTopNode();
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
        JxReader reader = new JxReader();
        reader.parse( new InputSource(aIn));
        
        String error = reader.getError();
        if( error != null ){
            throw new Exception( error );
        }
        
        JxNode node = reader.getTopNode();
        return node;
    }
}
