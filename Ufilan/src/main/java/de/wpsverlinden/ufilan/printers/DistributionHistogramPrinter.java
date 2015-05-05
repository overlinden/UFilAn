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
import de.wpsverlinden.ufilan.ConsolePrinter;
import de.wpsverlinden.ufilan.Result;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.imageio.ImageIO;

public class DistributionHistogramPrinter implements Printer {

    private static final int MAX_HEIGHT = 2000;
    private static final int MIN_HEIGHT = 100;
    private static final int MAX_WIDTH = 2000;
    private static final int MIN_WIDTH = 100;
    
    @Override
    public void print(Result r, OutputStream os) {
        HashMap<Chunk, Integer> distribution = (HashMap<Chunk, Integer>)r.getResult();
        
        ConsolePrinter.getInstance().println("Starting generation of histogram");
        
        int numEntries = distribution.size();
        int chunkLength = distribution.keySet().iterator().next().getLength();
        int imgWidth = (int)Math.pow(2, chunkLength * 8);
        int maxValue = 1;
        for (int i : distribution.values()) {
            maxValue = Math.max(maxValue, i);
        }
        
        double yScaleFactor = 1.0;
        int imgHeight = (int) (maxValue * 1.2);
        if (imgHeight > MAX_HEIGHT) {
            yScaleFactor = (float)MAX_HEIGHT / (float)imgHeight;
        }
        else if (imgHeight < MIN_HEIGHT) {
            yScaleFactor = (float)MIN_HEIGHT / (float)imgHeight;
        }
        
        double xScaleFactor = 1.0;
        if (imgWidth > MAX_WIDTH) {
            xScaleFactor = (float)MAX_WIDTH / (float)imgWidth;
        }
        else if (imgWidth < MIN_WIDTH) {
            xScaleFactor = (float)MIN_WIDTH / (float)imgWidth;
        }
        BufferedImage img = new BufferedImage((int) (imgWidth * xScaleFactor), (int) (imgHeight * yScaleFactor), BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);  
        g.fillRect(0, 0, img.getWidth(), img.getHeight()); 
        g.setColor(Color.BLACK);
        int cnt = 0;
        for (Entry<Chunk, Integer> e : distribution.entrySet()) {
            int xPos = new BigInteger(e.getKey().getData()).intValue();
            g.drawLine((int)(xScaleFactor * xPos), img.getHeight(), (int)(xScaleFactor * xPos), (int)((double)((float)img.getHeight() - yScaleFactor * (float)e.getValue())));
            if (cnt % ((numEntries / 100) + 1) == 0) {
                ProgressBar.printProgBar((int)(100.0 * cnt / numEntries));
            }
            cnt++;
        }
        g.dispose();
        try {
            ImageIO.write(img, "BMP", os);
        } catch (IOException ex) {
            ConsolePrinter.getInstance().println(ex.getMessage());
        }
        
        ConsolePrinter.getInstance().println("\nFinished generation of histogram");
    }
}
