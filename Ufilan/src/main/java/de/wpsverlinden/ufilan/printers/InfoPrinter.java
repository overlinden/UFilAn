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

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map.Entry;


public class InfoPrinter implements Printer {

    public InfoPrinter() {
    }

    @Override
    public void print(Object o, OutputStream os) {
        HashMap<String, String> info = (HashMap<String, String>)o;
        PrintStream ps = new PrintStream(os);
        for (Entry<String, String> e : info.entrySet()) {
            ps.format("%15s|%s\n", "Name", "Value");
            ps.format("%15s|%s\n", e.getKey(), e.getValue());
        }
    }
}
