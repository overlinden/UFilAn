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

package de.wpsverlinden.ufilan.analyzers;

import de.wpsverlinden.ufilan.Result;
import de.wpsverlinden.ufilan.Chunk;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class EntropyAnalyzer implements ContentAnalyzer {

    private final int chunkSize;
    
    public EntropyAnalyzer(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Override
    public Result analyze(InputStream is) throws IOException {

        if (is == null) {
            throw new IOException("Invalid input stream");
        }
        
        ContentAnalyzer ca = new ChunkDistributionAnalyzer(chunkSize);
        HashMap<Chunk, Integer> distribution = (HashMap<Chunk, Integer>) ca.analyze(is).getResult();
        
        int total = distribution.values().stream()
        .mapToInt((x) -> x)
        .sum();
        
        Iterator<Chunk> iter = distribution.keySet().iterator();
        int chunkBit = (iter.hasNext() ? 8 * iter.next().getLength() : 0);
        
        float p;
        double tmpEntropy = distribution.values().stream()
                .mapToDouble((e) -> {float x = (float)e / (float)total; return x * log(chunkBit, x);})
                .sum();
        float entropy = -(float)tmpEntropy;

        return new Result(Result.TYPE.ENTROPY, entropy);
    }
    
    private double log(int base, double val) {
        return Math.log(val)/Math.log(base);
    }
}
