package agh.cs.DocReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * DocReader
 * Created by luknw on 04 12 2016.
 */

public class Parser implements DocumentParser {
    //This list has to end with a token that will never match
    private static final String[] DEPTH_TOKENS =
            new String[]{"^KONSTYTUCJA.*", "^Rozdział.*", "^Art.*", "^\\d+\\..*", "^\\d+\\).*", "^$"};

    private static final String[] GARBAGE_TOKENS = new String[]{"^©.*", "^2009.*"};
    private static final String SUBCHAPTER_TOKEN = "^\\p{Lu}{2,}.*";
    private static final String WHITESPACE_TOKEN = "\\s+";

    private DocumentNode documentRoot = new Node();
    private Deque<DocumentNode> parsingStack = new ArrayDeque<>();
    private StringBuilder lastLine = null;

    @Override
    public DocumentNode parse(String path) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));
            parsingStack.add(documentRoot);
            reader.lines().filter(line -> !isGarbage(line)).forEachOrdered(this::processLine);
        } catch (IOException | UncheckedIOException e) {
            e.printStackTrace();
            System.out.println("Cannot read document");
        }
        return documentRoot;
    }

    private boolean isGarbage(String line) {
        return line.length() < 2
                || Arrays.stream(GARBAGE_TOKENS).anyMatch(line::matches)
                || isSubchapter(line);
    }

    private boolean isSubchapter(String line) {
        return (parsingStack.size() > 2
                || parsingStack.size() == 2 && parsingStack.peek().getLines().size() == 2)
                && line.matches(SUBCHAPTER_TOKEN);
    }

    private void pushNewNodeOnDepth(int depth, int currentDepth) {
        DocumentNode child = new Node();

        while (depth <= currentDepth--) //pop until we get access to the parent of new node
            parsingStack.pop();

        parsingStack.peek().getChildren().add(child);
        parsingStack.push(child);
    }

    private void processLine(String line) {
        int currentDepth = parsingStack.size() - 1;
        int depth = findLineDepth(currentDepth, line);

        if (depth > 0) pushNewNodeOnDepth(depth, currentDepth);

        //process line content, check line brakes and append to the node on the top of the stack
        if (lastLine != null) {
            //concatenate broken word and write previous line
            String[] firstWordRest = line.split(WHITESPACE_TOKEN, 2);
            lastLine.deleteCharAt(lastLine.length() - 1).append(firstWordRest[0]);
            parsingStack.peek().getLines().add(lastLine.toString());
            lastLine = null;

            if (firstWordRest.length > 1) line = firstWordRest[1];
            else return;
        }
        if (!line.endsWith("-")) {
            parsingStack.peek().getLines().add(line);
        } else {
            lastLine = new StringBuilder(line);
        }

    }

    /**
     * @return Depth >= 0 means that line begins with DEPTH_TOKENS.get(i).
     * Depth < 0 means that line does not begin with any DEPTH_TOKEN.
     */
    private int findLineDepth(int currentDepth, String line) {
        for (int i = currentDepth + 1; i >= 0; --i) {
            if (line.matches(DEPTH_TOKENS[i])) {
                return i;
            }
        }
        return -1;
    }

}
