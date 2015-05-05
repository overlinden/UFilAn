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

public class Result {
    
    public enum TYPE {CHUNKLOCATION, CHUNKDISTRIBUTION, CONTENT, ENTROPY, LENGTH, TYPE}
    
    private final TYPE type;
    private final Object result;

    public Result(TYPE type, Object result) {
        this.type = type;
        this.result = result;
    }

    public TYPE getType() {
        return type;
    }

    public Object getResult() {
        return result;
    }
}
