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
import de.wpsverlinden.ufilan.ConsolePrinter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ChunkDistributionAnalyzer implements ContentAnalyzer {

    private HashMap<Chunk, Integer> distribution;
    private int chunkSize;

    public ChunkDistributionAnalyzer(int chunkSize) {
        this.distribution = new HashMap<>();
        this.chunkSize = chunkSize;
    }

    @Override
    public Result analyze(InputStream is) throws IOException {
        
        if (is == null) {
            throw new IOException("Invalid input stream");
        }
        ConsolePrinter.getInstance().println("Starting analyzing of contents");

        Chunk chunk;
        byte[] current = new byte[chunkSize];
        while (is.read(current) != -1) {
            chunk = new Chunk(current);
            int count = (distribution.get(chunk) != null ? distribution.get(chunk) : 0);
            distribution.put(chunk, ++count);
            current = new byte[chunkSize];
        }

        ConsolePrinter.getInstance().println("Finished analyzing of contents");
        return new Result(Result.TYPE.CHUNKDISTRIBUTION, distribution);
    }
}
