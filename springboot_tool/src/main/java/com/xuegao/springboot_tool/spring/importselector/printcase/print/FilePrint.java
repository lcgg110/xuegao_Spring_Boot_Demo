package com.xuegao.springboot_tool.spring.importselector.printcase.print;

/**
 * Created by @author yihui in 18:54 19/12/13.
 */
public class FilePrint implements IPrint {
    @Override
    public void print() {
        System.out.println("file print");
    }
}
