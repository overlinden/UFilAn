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

import de.wpsverlinden.ufilan.ConsolePrinter;
import de.wpsverlinden.ufilan.analyzers.EntropyAnalyzer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class EntropyAnalyzerTest {
    
    public EntropyAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setup() {
        ConsolePrinter.getInstance().disable();
    }
    
    @Test(expected = IOException.class)
    public void testThrowsIOExceptionOnNullInputStream() throws IOException {
        new EntropyAnalyzer(1).analyze(null);
    }
    
    @Test
    public void testReturnsStringArray() throws IOException {
        InputStream is = new ByteArrayInputStream("".getBytes());
        Object result = new EntropyAnalyzer(1).analyze(is);
        assertNotNull(result);
        assertTrue(result instanceof String[]);
    }
    
    @Test
    public void testReturnsZeroForUniformInputCS1() throws IOException {
        InputStream is = new ByteArrayInputStream("1111".getBytes());
        Object result = new EntropyAnalyzer(1).analyze(is);
        String[] length = (String[])result;
        assertEquals("0.0",length[1]);
    }
    
    @Test
    public void testReturnsZeroForUniformInputCS2() throws IOException {
        InputStream is = new ByteArrayInputStream("1111".getBytes());
        Object result = new EntropyAnalyzer(2).analyze(is);
        String[] length = (String[])result;
        assertEquals("0.0",length[1]);
    }
    
    @Test
    public void testReturnsXForSymmetricInputCS1() throws IOException {
        InputStream is = new ByteArrayInputStream("1122".getBytes());
        Object result = new EntropyAnalyzer(1).analyze(is);
        String[] length = (String[])result;
        assertEquals("0.33333334",length[1]);
    }
    
    @Test
    public void testReturnsXForSymmetricInputCS2() throws IOException {
        InputStream is = new ByteArrayInputStream("1122".getBytes());
        Object result = new EntropyAnalyzer(2).analyze(is);
        String[] length = (String[])result;
        assertEquals("0.25",length[1]);
    }
}