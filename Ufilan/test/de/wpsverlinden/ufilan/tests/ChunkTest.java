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

package de.wpsverlinden.ufilan.tests;

import de.wpsverlinden.ufilan.Chunk;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChunkTest {
    
    public ChunkTest() {
    }
    
    @Test
    public void testEquals() {
        byte[] data1 = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
        byte[] data2 = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
        Chunk c1 = new Chunk(data1);
        Chunk c2 = new Chunk(data2);
        assertEquals(c1, c2);
    }
    
    @Test
    public void testNotEquals() {
        byte[] data1 = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
        byte[] data2 = new byte[]{0x00, 0x02, 0x03, 0x04, 0x05};
        Chunk c1 = new Chunk(data1);
        Chunk c2 = new Chunk(data2);
        assertFalse(c1.equals(c2));
    }
    
    @Test
    public void testToString() {
        byte[] data = new byte[]{0x00, 0x01, 0x02, 0x03, (byte)0xFF};
        Chunk c = new Chunk(data);
        assertEquals("0x00 0x01 0x02 0x03 0xFF", c.toString());
    }
    
    @Test
    public void testGetLength() {
        byte[] data = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04};
        Chunk c = new Chunk(data);
        assertEquals(5, c.getLength());
    }
    
    @Test
    public void testGetData() {
        byte[] data = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04};
        Chunk c = new Chunk(data);
        assertEquals(data, c.getData());
    }
    
    @Test
    public void testGetAvg() {
        byte[] data = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04};
        Chunk c = new Chunk(data);
        assertEquals(2, c.getAvg(), 0.1);
    }
}