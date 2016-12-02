package agh.cs.DocReader;

import java.util.List;

/**
 * DocReader
 * Created by luknw on 02 12 2016.
 */

public class Node implements DocumentNode {
    private List<String> value;
    private List<DocumentNode> children;

    public List<String> getLines() {
        return value;
    }

    public List<DocumentNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        getLines().forEach(line -> sb.append(line).append(System.lineSeparator()));
        return sb.toString();
    }
}
