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

import de.wpsverlinden.ufilan.analyzers.ChunkDistributionAnalyzer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class ChunkDistributionAnalyzerTest {
    
    public ChunkDistributionAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setup() {
        ConsolePrinter.getInstance().disable();
    }
    
    @Test
    public void testReturnsCorrectResultType() throws IOException {
        InputStream is = new ByteArrayInputStream("".getBytes());
        Result result = new ChunkDistributionAnalyzer(1).analyze(is);
        assertEquals(result.getType(),Result.TYPE.CHUNKDISTRIBUTION);
    }
    
    @Test(expected = IOException.class)
    public void testThrowsIOExceptionOnNullInputStream() throws IOException {
        new ChunkDistributionAnalyzer(1).analyze(null);
    }
    
    @Test
    public void testReturnsHashMap() throws IOException {
        InputStream is = new ByteArrayInputStream("".getBytes());
        Result result = new ChunkDistributionAnalyzer(1).analyze(is);
        assertNotNull(result);
        assertTrue(result.getResult() instanceof HashMap);
    }
    
    @Test
    public void testReturnsCorrectDistributionChunkSize1() throws IOException {
        InputStream is = new ByteArrayInputStream("112".getBytes());
        Result result = new ChunkDistributionAnalyzer(1).analyze(is);
        HashMap<Chunk, Integer> map = (HashMap<Chunk, Integer>)result.getResult();
        assertEquals(2, map.size());
        assertEquals(2, (int)map.get(new Chunk(new byte[]{49})));
        assertEquals(1, (int)map.get(new Chunk(new byte[]{50})));
    }
    
    @Test
    public void testReturnsCorrectDistributionChunkSize2() throws IOException {
        InputStream is = new ByteArrayInputStream("111123".getBytes());
        Result result = new ChunkDistributionAnalyzer(2).analyze(is);
        HashMap<Chunk, Integer> map = (HashMap<Chunk, Integer>)result.getResult();
        assertEquals(2, map.size());
        assertEquals(2, (int)map.get(new Chunk(new byte[]{49, 49})));
        assertEquals(1, (int)map.get(new Chunk(new byte[]{50, 51})));
    }
}