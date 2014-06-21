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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({de.wpsverlinden.ufilan.ChunkTest.class, de.wpsverlinden.ufilan.LoggerTest.class, de.wpsverlinden.ufilan.ChunkDistributionAnalyzerTest.class, de.wpsverlinden.ufilan.EntropyAnalyzerTest.class, de.wpsverlinden.ufilan.ChunkLocationAnalyzerTest.class, de.wpsverlinden.ufilan.LengthAnalyzerTest.class})
public class AllTests {
    
}