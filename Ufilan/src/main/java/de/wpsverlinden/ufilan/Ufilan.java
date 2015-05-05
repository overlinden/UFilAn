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
import de.wpsverlinden.ufilan.printers.BasicMapPrinter;
import de.wpsverlinden.ufilan.analyzers.ChunkDistributionAnalyzer;
import de.wpsverlinden.ufilan.analyzers.ChunkLocationAnalyzer;
import de.wpsverlinden.ufilan.analyzers.ContentAnalyzer;
import de.wpsverlinden.ufilan.analyzers.EntropyAnalyzer;
import de.wpsverlinden.ufilan.analyzers.TypeAnalyzer;
import de.wpsverlinden.ufilan.printers.DistributionHistogramPrinter;
import de.wpsverlinden.ufilan.printers.DistributionTextPrinter;
import de.wpsverlinden.ufilan.printers.InfoPrinter;
import de.wpsverlinden.ufilan.printers.Printer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class Ufilan {

    private final String[] args;
    private Options options;
    private OutputStream output = System.out;
    private InputStream input = System.in;
    private int chunkSize = 1;
    private int seekSize = 0;
    private String action = "";
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
        System.out.println("UFilAn 1.1-SNAPSHOT - written by Oliver Verlinden (http://wps-verlinden.de)");
        try {
            CommandLine cmd = initCli();
            prepareParameter(cmd);
            run();
        } catch (ParseException pex) {
            System.out.println(pex.getMessage());
            printHelp();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private CommandLine initCli() throws ParseException {
        options = new Options();
        options.addOption(OptionBuilder
                .isRequired()
                .hasArg()
                .withDescription("action to perform (histogram_text, histogram_img, map, type, size, entropy)")
                .create("a")
        );
        options.addOption(OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("path of the input file. If null, stdin is used")
                .create("if")
        );
        options.addOption(OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("path of the output file. If null, stdout is used")
                .create("of")
        );
        options.addOption(OptionBuilder.withArgName("byte")
                .hasArg()
                .withDescription("chunk size in bytes used for input parsing. Default value: 1 byte")
                .create("c")
        );
        options.addOption(OptionBuilder.withArgName("byte")
                .hasArg()
                .withDescription("skips the given number of bytes start parsing. Default value: 0 byte")
                .create("s")
        );
        
        CommandLineParser parser = new PosixParser();
        return parser.parse(options, args);
    }

    private void prepareParameter(CommandLine cmd) throws Exception {

        if (cmd.getOptions().length == 0) {
            printHelp();
            System.exit(0);
        }

        action = cmd.getOptionValue("a");
        
        if (cmd.hasOption("if")) {
            input = new BufferedInputStream(new FileInputStream(new File(cmd.getOptionValue("if"))));
        }
        if (cmd.hasOption("of")) {
            output = new BufferedOutputStream(new FileOutputStream(new File(cmd.getOptionValue("of"))));
            ConsolePrinter.getInstance().enable(); //enable log output on console when writing data stream to file
        }
        if (cmd.hasOption("c")) {
            try {
                chunkSize = Integer.parseInt(cmd.getOptionValue("c"));
            } catch (NumberFormatException numberFormatException) {
                throw new Exception("Invalid chunk size: " + cmd.getOptionValue("c"));
            }
        }
        if (cmd.hasOption("s")) {
            try {
                seekSize = Integer.parseInt(cmd.getOptionValue("s"));
            } catch (NumberFormatException numberFormatException) {
                throw new Exception("Invalid seek size: " + cmd.getOptionValue("s"));
            }
        }
    }

    private void run() throws Exception {
        switch (action) {
            case "histogram_text":
                ca = new ChunkDistributionAnalyzer(chunkSize);
                p = new DistributionTextPrinter();
                break;
            case "histogram_img":
                ca = new ChunkDistributionAnalyzer(chunkSize);
                p = new DistributionHistogramPrinter();
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
        Result result = ca.analyze(input);
        p.print(result, output);
    }

    private void printHelp() {
        ConsolePrinter.getInstance().enable();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar ufilan.jar", options, true);
    }
}