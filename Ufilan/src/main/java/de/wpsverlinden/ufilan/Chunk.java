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

import java.util.Arrays;

public class Chunk {

    private final byte[] data;

    public Chunk(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Chunk)) {
            return false;
        }
        return Arrays.equals(data, ((Chunk)other).data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("0x%02X ", b));
        }
        return sb.toString().trim();
    }
    
    public int getLength() {
        return data.length;
    }
    
    public byte[] getData() {
        return data;
    }
    
    public float getAvg() {
        float ret = 0f;
        for (byte b : data) {
            ret += b & 0xFF;
        }
        ret /= (float)data.length;
        return ret;
    }
}
