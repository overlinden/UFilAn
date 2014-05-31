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

import de.wpsverlinden.ufilan.analyzers.LengthAnalyzer;
import de.wpsverlinden.ufilan.results.printers.BasicMapPrinter;
import de.wpsverlinden.ufilan.analyzers.ChunkDistributionAnalyzer;
import de.wpsverlinden.ufilan.analyzers.ChunkLocationAnalyzer;
import de.wpsverlinden.ufilan.analyzers.ContentAnalyzer;
import de.wpsverlinden.ufilan.analyzers.EntropyAnalyzer;
import de.wpsverlinden.ufilan.analyzers.TypeAnalyzer;
import de.wpsverlinden.ufilan.results.printers.DistributionHistogramPrinter;
import de.wpsverlinden.ufilan.results.printers.DistributionTextPrinter;
import de.wpsverlinden.ufilan.results.printers.InfoPrinter;
import de.wpsverlinden.ufilan.results.printers.Printer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Ufilan {

    private String[] args;
    private OutputStream output = System.out;
    private InputStream input = System.in;
    private int chunkSize = 1;
    private int seekSize = 0;
    private String action = "";
    private String actionParameter = "";
    private ContentAnalyzer ca;
    private Printer p;

    public static void main(String[] args) {
        Ufilan u = new Ufilan(args);
        u.start();
    }

    private Ufilan(String[] args) {
        this.args = args;
        ConsolePrinter.getInstance().disable(); //disable log output by default
    }

    private void start() {
        System.out.println("UFilAn 1.0 - written by Oliver Verlinden (http://wps-verlinden.de)");
        try {
            parseParameter();
            run();
            System.exit(0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }

    private void parseParameter() throws Exception {

        if (args.length == 0) {
            printHelp();
            System.exit(0);
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-if")) {
                input = new FileInputStream(new File(args[++i]));
            }
            if (args[i].equals("-of")) {
                output = new FileOutputStream(new File(args[++i]));
                ConsolePrinter.getInstance().enable(); //enable log output on console when writing data stream to file
            }
            if (args[i].equals("-c")) {
                try {
                    chunkSize = Integer.parseInt(args[++i]);
                } catch (NumberFormatException numberFormatException) {
                    throw new Exception("Invalid chunk size: " + args[i]);
                }
                if (chunkSize <= 0) {
                    throw new Exception("Chunk size must be positive.");
                }
            }
           if (args[i].equals("-s")) {
                try {
                    seekSize = Integer.parseInt(args[++i]);
                } catch (NumberFormatException numberFormatException) {
                    throw new Exception("Invalid chunk size: " + args[i]);
                }
                if (seekSize <= 0) {
                    throw new Exception("Chunk size must be positive.");
                }
            }
            if (args[i].equals("-a")) {
                action = args[++i];
                if (i < args.length - 1 ) actionParameter = args[++i];
            }
        }
    }

    private void run() throws Exception {
        switch (action) {
            case "histogram":
                ca = new ChunkDistributionAnalyzer(chunkSize);
                switch (actionParameter) {
                    case "text":
                        p = new DistributionTextPrinter();
                        break;
                    case "img":
                        p = new DistributionHistogramPrinter();
                        break;
                    default:
                        throw new Exception("Invalid parameter for action \"" + action +"\".");
                }
                break;
            case "map":
                ca = new ChunkLocationAnalyzer(chunkSize);
                p = new BasicMapPrinter();

                break;
            case "type":
                ca = new TypeAnalyzer();
                p = new InfoPrinter();
                break;
            case "size":
                ca = new LengthAnalyzer();
                p = new InfoPrinter();
                break;
            case "entropy":
                ca = new EntropyAnalyzer(chunkSize);
                p = new InfoPrinter();
                break;
            default:
                throw new Exception("Invalid action: " + action);
        }
        while (seekSize > 0) {
            seekSize -= input.skip(chunkSize);
        }
        Object result = ca.analyze(input);
        p.print(result, output);
    }

    private void printHelp() {
        ConsolePrinter.getInstance().enable();
        System.out.println("Usage: java -jar ufilan [-if <inputfile>] [-of <outputfile>] [-c <chunksize>] [-s <seeksize>] -a <action> [<actionparam>]");
        System.out.println();
        System.out.println("-if <inputfile>              path of the input file. If null the stdin is used");
        System.out.println("-of <outputfile>             path of the output file. If null stdout is used");
        System.out.println("-c <chunksize>               chunk size in bytes used for input parsing. Default value: 1 byte");
        System.out.println("-s <seeksize>                skips the given number of bytes start parsing. Default value: 0 byte");
        System.out.println("-a histogram                 generate a text or graphic histogram. Param \"text\" or \"img\".");
        System.out.println("   map                       generate a graphic distribution map");
        System.out.println("   type                      analyze the input to determine the type");
        System.out.println("   size                      calculate the input size");
        System.out.println("   entropy                   calculate the entropy");
        System.out.println();
        System.out.println("Simple examples:");
        System.out.println("Read data from stdin and print the type to stdout");
        System.out.println("java -jar ufilan -a type");
        System.out.println();
        System.out.println("Read data from stdin and save the graphic histogram to /tmp/histogram.bmp");
        System.out.println("java -jar ufilan -of /tmp/histogram.bmp -a histogram img");
        System.out.println();
        System.out.println("Read data from /tmp/in.xml and print the entropy information to stdout");
        System.out.println("java -jar ufilan -if /tmp/in.bmp -a entropy");
        System.out.println();
        System.out.println("Advanced examples:");
        System.out.println("Read data from /tmp/input.txt with 2 byte chunks, skip the first 10 byte and print the most recent chunks");
        System.out.println("java -jar ufilan -if /tmp/input.txt -c 2 -s 10 -a histogram txt | head -n 11");
        System.out.println();
        System.out.println("Calculate the entropy of the unix random number generator");
        System.out.println("dd if=/dev/urandom bs=1 count=100k | java -jar ufilan -a entropy");
        System.out.println();
    }
}
