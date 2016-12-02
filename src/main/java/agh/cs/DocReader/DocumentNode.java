package agh.cs.DocReader;

import java.util.List;

/**
 * DocReader
 * Created by luknw on 02 12 2016.
 */

public interface DocumentNode {
    List<String> getValue();

    List<DocumentNode> getChildren();
}
