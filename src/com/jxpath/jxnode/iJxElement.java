package com.jxpath.jxnode;

import java.io.IOException;
import java.io.Writer;

public interface iJxElement {
    //-----------------------------
    // public methods
    //-----------------------------
    public eElementType getType();
    public void toWriter(Writer aWriter, int aDepth) throws IOException;
    public String getName();
}
