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

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class TypeAnalyzer implements ContentAnalyzer {

    private HashMap<String, String> result = new HashMap<>();

    @Override
    public Object analyze(InputStream is) throws IOException {
        getContentInfo(is);
        
        return result;
    }

    private void getContentInfo(InputStream is) throws IOException {
        ContentInfoUtil util = new ContentInfoUtil();
        ContentInfo info = util.findMatch(is);
        if (info != null) {
            result.put("MagicMessage", info.getMessage());
            result.put("MagicName", info.getName());
        }
    }
}