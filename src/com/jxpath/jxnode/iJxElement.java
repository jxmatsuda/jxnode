package com.jxpath.jxnode;

import java.io.IOException;
import java.io.Writer;

public interface iJxElement {
    //-----------------------------
    // public methods
    //-----------------------------
    public eElementType getType();
    public void toWriter(Writer a_writer, int a_depth) throws IOException;
    public String getName();
}
