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

package de.wpsverlinden.ufilan.printers;

import de.wpsverlinden.ufilan.Chunk;
import de.wpsverlinden.ufilan.Result;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.HashMap;

public class DistributionTextPrinter implements Printer {

    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void print(Result r, OutputStream os) {
        HashMap<Chunk, Integer> distribution = (HashMap<Chunk, Integer>)r.getResult();
        PrintStream ps = new PrintStream(os);
        int total = distribution.values().parallelStream()
                .mapToInt((x) -> x)
                .sum();

        ps.format("%10s|%10s|%s\n", "Char code", "Abs count", "% count");
        distribution.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue()))
                .forEachOrdered((e) -> ps.format("%10s|%10s|%s\n", e.getKey().toString(), e.getValue(), df.format((float) e.getValue() / total * 100) + "%"));
    }
}
