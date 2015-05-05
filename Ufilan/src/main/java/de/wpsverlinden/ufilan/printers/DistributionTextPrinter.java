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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

public class DistributionTextPrinter implements Printer {

    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void print(Result r, OutputStream os) {
        HashMap<Chunk, Integer> distribution = (HashMap<Chunk, Integer>)r.getResult();

        PrintStream ps = new PrintStream(os);
        int total = 0;
        for (int num : distribution.values()) {
            total += num;
        }

        ps.format("%10s|%10s|%s\n", "Char code", "Abs count", "% count");
        for (Entry<Chunk, Integer> e : entriesSortedByValues(distribution)) {
            ps.format("%10s|%10s|%s\n", e.getKey().toString(), e.getValue(), df.format((float) e.getValue() / total * 100) + "%");
        }
    }

    private String align(String s) {
        int i = 0;
        while (s.charAt(i) == '0') {
            i++;
        }
        return s.substring(0, i + 1).replace('0', ' ') + s.substring(i + 1);
    }

    private <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>(
                new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                int res = e2.getValue().compareTo(e1.getValue());
                return res != 0 ? res : 1; // Special fix to preserve items with equal values
            }
        });
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
