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

import de.wpsverlinden.ufilan.Result;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class InfoPrinter implements Printer {

    public InfoPrinter() {
    }

    @Override
    public void print(Result r, OutputStream os) {
        HashMap<String, String> info = (HashMap<String, String>) r.getResult();
        PrintStream ps = new PrintStream(os);
        ps.format("%15s | %s\n", "Name", "Value");
        info.entrySet().stream()
                .forEachOrdered((e) -> ps.format("%15s | %s\n", e.getKey(), e.getValue())); 
    }
}
