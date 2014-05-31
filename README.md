UFilAn
======

The UFilAn Universal File Analyzer provides a simple to use file analyzing functionality.
Main features:
* Read and write data from/to file or stdin
* Provides grpahical and text output
* Plots histogram and byte distributions
* Easy to use command line to combine ufilan with other unix tools
* ...


````
Usage: java -jar ufilan [-if <inputfile>] [-of <outputfile>] [-c <chunksize>] 
                        [-s <seeksize>] -a <action> [<actionparam>]

-if <inputfile>              path of the input file. If null the stdin is used
-of <outputfile>             path of the output file. If null stdout is used
-c  <chunksize>              chunk size in bytes used for input parsing. Default value: 1 byte
-s  <seeksize>               skips the given number of bytes start parsing. Default value: 0 byte
-a  histogram                generate a text or graphic histogram. Param "text" or "img".
    map                      generate a graphic distribution map
    type                     analyze the input to determine the type
    size                     calculate the input size
    entropy                  calculate the entropy
````

**Simple examples:**
* Read data from stdin and print the type to stdout
````bash
java -jar ufilan -a type
````
* Read data from stdin and save the graphic histogram to /tmp/histogram.bmp
````bash
java -jar ufilan -of /tmp/histogram.bmp -a histogram img
````
* Read data from /tmp/in.xml and print the entropy information to stdout
````bash
java -jar ufilan -if /tmp/in.bmp -a entropy
````

**Advanced examples:**
* Read data from /tmp/input.txt with 2 byte chunks, skip the first 10 byte and print the most recent chunks
````bash
java -jar ufilan -if /tmp/input.txt -c 2 -s 10 -a histogram txt | head -n 11
````
* Calculate the entropy of the unix random number generator
````bash
dd if=/dev/urandom bs=1 count=100k | java -jar ufilan -a entropy
````

Special thanks to [j256](https://github.com/j256/) for the awesome simplemagic library
