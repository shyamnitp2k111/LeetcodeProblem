/*
 * Copyright (c) 2006, 2019, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package OutPrintDrainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Will drain the output stream.
 */
public final class InputStreamDrainer extends Thread {
    private BufferedReader reader;
    private final ArrayList<PrintStream> outs = new ArrayList<>();
    private final ArrayList<PrintStream> plainOuts = new ArrayList<>();
    private String prepend;

    private List<Pattern> expectedPatterns = new ArrayList<>();
    private AtomicInteger unfoundPatternsCount = new AtomicInteger();

    /**
     * Create a drainer which will discard the read lines.
     * @param in The input stream to drain
     */
    public InputStreamDrainer(InputStream in) {
        reader = new BufferedReader(new InputStreamReader(in));
    }

    /**
     * Create a drainer that will echo all read lines to <code>out</code>.
     * @param in The input stream to drain
     * @param out Where to drain the stream into
     */
    public InputStreamDrainer(InputStream in, OutputStream out) {
        this(in);
        this.plainOuts.add(new PrintStream(out));
    }

    /**
     * Create a drainer that will echo all read lines to <code>out</code>.
     * @param in The input stream to drain
     * @param outs Where to drain the stream into
     */
    public InputStreamDrainer(InputStream in, OutputStream[] outs) {
        this(in);
        for (OutputStream out : outs) {
            this.plainOuts.add(new PrintStream(out));
        }
    }

    /**
     * Create a drainer that will echo all read lines to <code>out</code>, and prepend every line with
     * <code>prepend</code>.
     * @param in The input stream to drain
     * @param out Where to drain the stream into
     * @param prepend The string to append to every line
     */
    public InputStreamDrainer(InputStream in, OutputStream out, String prepend) {
        this(in);
        this.outs.add(new PrintStream(out));
        this.prepend = prepend;
    }

    /**
     * Create a drainer that will echo all read lines to <code>out</code>, and prepend every line with
     * <code>prepend</code>.
     * @param in The input stream to drain
     * @param outs Where to drain the stream into
     * @param prepend The string to append to every line
     */
    public InputStreamDrainer(InputStream in, OutputStream[] plainOuts, OutputStream[] outs, String prepend) {
        this(in);
        for (OutputStream out : plainOuts) {
            this.plainOuts.add(new PrintStream(out));
        }
        for (OutputStream out : outs) {
            this.outs.add(new PrintStream(out));
        }
        this.prepend = prepend;
    }

    /**
     * Drain the stream.
     */
    @Override
    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {

                System.out.println("line is ......"+ line);
                for (Pattern pattern : expectedPatterns)
                if (pattern.matcher(line).matches()) {
                    unfoundPatternsCount.decrementAndGet();
                }
                for (PrintStream out : plainOuts) {
                    out.println(line);
                    out.flush();
                }
                for (PrintStream out : outs) {
                    out.println(prepend + line);
                    out.flush();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.out);
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                ioe.printStackTrace(System.out);
            }
        }
    }

    public void setExpectedPatterns(List<String> regexps) {
        this.expectedPatterns = new ArrayList<>();
        for (String regexp : regexps) {
            this.expectedPatterns.add(Pattern.compile(regexp));
        }
        unfoundPatternsCount.set(this.expectedPatterns.size());
    }

    public boolean areAllPatternsFound() {
        return unfoundPatternsCount.intValue() == 0;
    }

}
