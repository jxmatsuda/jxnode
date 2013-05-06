package com.jxpath.jxnode.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.jxpath.jxnode.JxNode;

public class JxParserTest {

    /**
     * simpe XML
     */
    @Test
    public void test0x() {
        try{
            int fileNum = 4;
            for( int i=1; i<=fileNum; i++ ){
            JxNode node = JxParser.parse( new File("testData/test0" + i + ".xml"));
            System.out.println("--------- test0" + i + ".xml --------\n"+node.toString());
            }
        }catch( Exception e){
            e.printStackTrace();
            fail();
        }
    }

    /**
     * simpe XML
     */
    @Test
    public void test1x() {
        int fileNum = 2;
        for( int i=1; i<=fileNum; i++ ){
            try{
                System.out.println("--------- test1" + i + "_err.xml --------\n");
                JxNode node = JxParser.parse( new File("testData/test1" + i + "_err.xml"));
                fail();
                
            }catch( Exception e){
                System.out.println( e.getMessage() );
            }
        }
    }
}