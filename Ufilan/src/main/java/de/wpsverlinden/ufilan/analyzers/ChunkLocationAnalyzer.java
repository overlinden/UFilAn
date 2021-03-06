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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChunkLocationAnalyzer implements ContentAnalyzer {

    private final int chunkSize;
    private final List<Chunk> chunks;

    public ChunkLocationAnalyzer(int chunkSize) {
        this.chunks = new ArrayList<>();
        this.chunkSize = chunkSize;
    }

    @Override
    public Result analyze(InputStream is) throws IOException {

        if (is == null) {
            throw new IOException("Invalid input stream");
        }
        ConsolePrinter.getInstance().println("Starting analyzing of contents");

        byte[] current = new byte[chunkSize];
        while (is.read(current) != -1) {
            chunks.add(new Chunk(current));
            current = new byte[chunkSize];
        }
        ConsolePrinter.getInstance().println("Finished analyzing of contents");
        return new Result(Result.TYPE.CHUNKLOCATION, chunks);
    }
}
