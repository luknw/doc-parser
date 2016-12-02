package agh.cs.DocReader;

/**
 * DocReader
 * Created by luknw on 02 12 2016.
 */

public interface DocumentPrinter {
    void printChapter(int chapter);

    void printArticle(int article);

    void printArticles(int first, int last);
}
