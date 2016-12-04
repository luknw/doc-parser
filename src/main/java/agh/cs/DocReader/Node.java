package agh.cs.DocReader;

import java.util.LinkedList;
import java.util.List;

/**
 * DocReader
 * Created by luknw on 02 12 2016.
 */

public class Node implements DocumentNode {
    private List<String> header = new LinkedList<>();
    private List<DocumentNode> children = new LinkedList<>();

    public List<String> getLines() {
        return header;
    }

    public List<DocumentNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        getLines().forEach(line -> sb.append(line).append(System.lineSeparator()));
        getChildren().forEach(sb::append);
        return sb.toString();
    }
}
