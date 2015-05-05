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

import de.wpsverlinden.ufilan.Chunk;
import de.wpsverlinden.ufilan.ConsolePrinter;
import de.wpsverlinden.ufilan.analyzers.ChunkLocationAnalyzer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class ChunkLocationAnalyzerTest {
    
    public ChunkLocationAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setup() {
        ConsolePrinter.getInstance().disable();
    }
    
    @Test(expected = IOException.class)
    public void testThrowsIOExceptionOnNullInputStream() throws IOException {
        new ChunkLocationAnalyzer(1).analyze(null);
    }
    
    @Test
    public void testReturnsList() throws IOException {
        InputStream is = new ByteArrayInputStream("".getBytes());
        Result result = new ChunkLocationAnalyzer(1).analyze(is);
        assertNotNull(result.getResult());
        assertTrue(result.getResult() instanceof List);
    }
    
    @Test
    public void testReturnsCorrectResultCS1() throws IOException {
        InputStream is = new ByteArrayInputStream("112".getBytes());
        Result result = new ChunkLocationAnalyzer(1).analyze(is);
        List<Chunk> list = (List<Chunk>)result.getResult();
        assertEquals(3, list.size());
        assertEquals(new Chunk(new byte[]{0x31}), list.get(0));
        assertEquals(new Chunk(new byte[]{0x31}), list.get(1));
        assertEquals(new Chunk(new byte[]{0x32}), list.get(2));
    }
    
    @Test
    public void testReturnsCorrectResultCS2() throws IOException {
        InputStream is = new ByteArrayInputStream("111122".getBytes());
        Result result = new ChunkLocationAnalyzer(2).analyze(is);
        List<Chunk> list = (List<Chunk>)result.getResult();
        assertEquals(3, list.size());
        assertEquals(new Chunk(new byte[]{0x31, 0x31}), list.get(0));
        assertEquals(new Chunk(new byte[]{0x31, 0x31}), list.get(1));
        assertEquals(new Chunk(new byte[]{0x32, 0x32}), list.get(2));
    }
}