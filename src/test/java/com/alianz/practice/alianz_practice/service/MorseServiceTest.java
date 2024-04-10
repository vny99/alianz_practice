package com.alianz.practice.alianz_practice.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import jakarta.annotation.Resource;

public class MorseServiceTest {
    @InjectMocks
    @Resource
    private MorseService service;

    private final Map<String, String> morseCharIndex = new TreeMap();

    private final Set<String> morseDictionary = new TreeSet();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void sampleCodeTest(){
        Set<String> result = service.decode("--.--.---.......-.---.-.-.-..-.....--..-....-.-----..-");
        assertTrue(result.contains("may the for case with you"));
    }


    @Test
    public void morseCodeTest_3(){
        Set<String> result = service.decode(".--.-..-.....-.-..-.........");
        assertTrue(result.contains("and be end be see"));
    }

    @Test
    public void morseCodeTest_4(){
        Set<String> result = service.decode("--.--.---....-.....");
        assertTrue(result.contains("may be the"));
    }

    @Test
    public void morseCodeTest_5(){
        Set<String> result = service.decode("-.-..-....-....-.-..-.....");
        assertTrue(result.contains("case be kids"));
    }

    @Test
    public void morseCodeTest_6(){
        Set<String> result = service.decode("..-.---.-...-.--.--.-..");
        assertTrue(result.contains("for item and"));
    }

    @Test
    public void morseCodeTest_7(){
        List<String> lines = new ArrayList<>();

        // Add the provided lines to the list
        lines.add("maytheforcethemusa");
        lines.add("maytheforcaseadditem");
        lines.add("maytheforcedvddueto");
        lines.add("maytheforcethemeetset");
        lines.add("maytheforcethemusenotnet");
        lines.add("maytheforcebigedit");
        lines.add("maytheforcedvddue");
        lines.add("maytheforcasewithyou");
        lines.add("maytheforcasewithtwo");
        lines.add("maytheforcethemuseyou");
        lines.add("maytheforcethemusenot");
        lines.add("maytheforcedvdduetonet");
        lines.add("maytheforcethemusetwo");
        lines.add("maytheforkidsmeetset");
        lines.add("maytheforcaseaddinto");
        lines.add("maytheforcasewithnot");
        lines.add("maytheforcasewithnotnet");
        lines.add("maytheforcethemeet");
        lines.add("maytheforcasewith");
        lines.add("maytheforcaseaddintonet");
        lines.add("maytheforcedvdtest");
        lines.add("maytheforcebitnetset");
        lines.add("maytheforcasewithyou");
        lines.add("maytheforcebewithyou");
        lines.add("maytheforcebutuseyou");
        service.matchedStrings(lines, "--.--.---.......-.---.-.-.-..-.....--..-....-.-----..-");
    }

}
