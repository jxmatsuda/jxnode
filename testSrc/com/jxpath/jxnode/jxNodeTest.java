package com.jxpath.jxnode;

import static org.junit.Assert.*;

import org.junit.Test;

public class jxNodeTest {

    /**
     * ノード生成テスト
     */
    @Test
    public void test001() {
        JxNode node = new JxNode("test");
        assertEquals(node.toString(), "<test/>");
    }

    /**
     * リーフ生成テスト
     */
    @Test
    public void test002() {
        JxNode node = new JxNode("test", "text<>&'\"");
        assertEquals(node.toString(), "<test>text&lt;&gt;&amp;&apos;&quot;</test>");
    }

    /**
     * リーフに属性追加テスト
     */
    @Test
    public void test003() {
        // add att
        JxNode node = new JxNode("test", "text<>&'\"");
        node.setAtt("att1", "attValue1<>&'\"");
        assertEquals(node.toString(), "<test att1=\"attValue1&lt;&gt;&amp;&apos;&quot;\">text&lt;&gt;&amp;&apos;&quot;</test>");
    }
    
    /**
     * リーフに複数属性追加テスト+属性上書きテスト
     */
    @Test
    public void test004() {
        // add multiple att
        JxNode node = new JxNode("test", "text<>&'\"");
        node.setAtt("att1", "attValue1<>&'\"");
        node.setAtt("att2", "attValue2");
        assertEquals(node.toString(), "<test att1=\"attValue1&lt;&gt;&amp;&apos;&quot;\" att2=\"attValue2\">text&lt;&gt;&amp;&apos;&quot;</test>");

        // overwrite att
        node.setAtt("att1", "attValue1new");
        assertEquals(node.toString(), "<test att1=\"attValue1new\" att2=\"attValue2\">text&lt;&gt;&amp;&apos;&quot;</test>");
    }

    /**
     * ノードに子リーフ追加テスト
     */
    @Test
    public void test005() {
        JxNode node = new JxNode("test", "text");
        JxNode child = node.addNode("child");
        assertEquals(child.toString(), "<child/>" );
        assertEquals(node.toString(), "<test>text\n  <child/>\n</test>" );
    }
    
    /**
     * ノードに同名の複数子リーフ追加テスト
     */
    @Test
    public void test006() {
        JxNode node = new JxNode("test", "text");
        JxNode child = node.addNode("child");
        assertEquals(child.toString(), "<child/>" );

        child = node.addNode("child2", "child2Text");
        assertEquals(child.toString(), "<child2>child2Text</child2>" );
        assertEquals(node.toString(), "<test>text\n  <child/>\n  <child2>child2Text</child2>\n</test>" );
        
        child = node.addNode("child2", "child2Text2");
        assertEquals(child.toString(), "<child2>child2Text2</child2>" );
        assertEquals(node.toString(), "<test>text\n  <child/>\n  <child2>child2Text</child2>\n  <child2>child2Text2</child2>\n</test>" );
                
    }
        
}
