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
import java.io.IOException;
import java.io.InputStream;

public class LengthAnalyzer implements ContentAnalyzer {

    private static final int BUFF_SIZE = 4096;
    
    @Override
    public Result analyze(InputStream is) throws IOException {

        if (is == null) {
            throw new IOException("Invalid input stream");
        }

        int size = 0;
        int bytesRead;
        byte[] tmp = new byte[BUFF_SIZE];
        while ((bytesRead = is.read(tmp)) != -1) {
            size += bytesRead;
        }
        return new Result(Result.TYPE.LENGTH, size);
    }
}
