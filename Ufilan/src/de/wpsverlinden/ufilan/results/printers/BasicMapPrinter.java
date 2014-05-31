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

package de.wpsverlinden.ufilan.results.printers;

import de.wpsverlinden.ufilan.Chunk;
import de.wpsverlinden.ufilan.ConsolePrinter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.imageio.ImageIO;

public class BasicMapPrinter implements Printer {
    
    private static int IMG_SIZE = 1000;
    private static int MAX_FIELD_SIZE = 100;
    private static int FIELDS_AVAILABLE = IMG_SIZE * IMG_SIZE;
    
    @Override
    public void print(Object o, OutputStream os) {
        List<Chunk> chunks = (List<Chunk>)o;
        ConsolePrinter.getInstance().println("Starting generation of file map");
        
        int numChunks = chunks.size();
        int fieldSize = Math.min(MAX_FIELD_SIZE, (int) Math.sqrt(FIELDS_AVAILABLE / numChunks));
        int fieldsPerLine = IMG_SIZE / fieldSize;
        
        if (numChunks > FIELDS_AVAILABLE) {
            ConsolePrinter.getInstance().println("Warning: Number of chunks exceeds maximum printable chunks. Printing first " + FIELDS_AVAILABLE + " chunks only");
        }
                
        BufferedImage img = new BufferedImage(IMG_SIZE, IMG_SIZE, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        int row, col, avgColor;
        int chunksToPrint = Math.min(chunks.size(), FIELDS_AVAILABLE);
        for (int i = 0; i < chunksToPrint; i++) {
            if (i % ((chunksToPrint / 100) + 1) == 0) {
                ProgressBar.printProgBar((int)(100.0 * i / chunksToPrint));
            }
            row = i / fieldsPerLine;
            col = i % fieldsPerLine;
            Chunk c = chunks.get(i);
            avgColor = (int) c.getAvg();
            Color color = new Color(avgColor, avgColor, avgColor);
            g.setColor(color);
            g.fillRect(col * fieldSize, row * fieldSize, fieldSize, fieldSize);
        }
        g.dispose();
        try {
            ImageIO.write(img, "BMP", os);
        } catch (IOException ex) {
            ConsolePrinter.getInstance().println(ex.getMessage());
        }
        ConsolePrinter.getInstance().println("\nFinished generation of file map");
    }
}
