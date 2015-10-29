package com.jonas.autocompletesearch.utills;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Jonas on 2015/10/29.
 */
public class CharacterParserTest extends TestCase {

    public void testGetMatchString() throws Exception{
        CharacterParser parser = CharacterParser.getInstance();
        List 中国 = parser.getMatchString("中国国中过", "zg", true);
        System.out.println(中国.size());
    }
}