/*
 * UFilAn - The Universal File Analyzer
 * Copyright (C) 2014 Oliver Verlinden (http://wps-verlinden.de)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.wpsverlinden.ufilan;

import de.wpsverlinden.ufilan.analyzers.LengthAnalyzer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class LengthAnalyzerTest {
    
    public LengthAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setup() {
        ConsolePrinter.getInstance().disable();
    }
    
    @Test
    public void testReturnsCorrectResultType() throws IOException {
        InputStream is = new ByteArrayInputStream("".getBytes());
        Result result = new LengthAnalyzer().analyze(is);
        assertEquals(result.getType(),Result.TYPE.LENGTH);
    }
    
    @Test(expected = IOException.class)
    public void testThrowsIOExceptionOnNullInputStream() throws IOException {
        new LengthAnalyzer().analyze(null);
    }
    
    @Test
    public void testReturnsInteger() throws IOException {
        InputStream is = new ByteArrayInputStream("".getBytes());
        Result result = new LengthAnalyzer().analyze(is);
        assertNotNull(result.getResult());
        assertTrue(result.getResult() instanceof Integer);
    }
    
    @Test
    public void testReturnsCorrectResult() throws IOException {
        InputStream is = new ByteArrayInputStream("12345".getBytes());
        Result result = new LengthAnalyzer().analyze(is);
        int length = (int)result.getResult();
        assertEquals(5,length);
    }
}