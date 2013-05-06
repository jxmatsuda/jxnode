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
    public void test01() {
        try{
            int fileNum = 4;
            for( int i=1; i<=fileNum; i++ ){
            JxNode node = JxParser.parse( new File("testData/test0" + i + ".xml"));
            System.out.println("--------- test0" + i + "xml --------\n"+node.toString());
            }
        }catch( Exception e){
            e.printStackTrace();
            fail();
        }
    }
}