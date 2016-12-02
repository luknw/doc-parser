package agh.cs.DocReader;

/**
 * DocReader
 * Created by luknw on 02 12 2016.
 */

public interface DocumentPrinter {
    void printChapter(DocumentNode root, int chapterIndex);

    void printArticle(DocumentNode root, int articleIndex);

    void printArticles(DocumentNode root, int first, int last);
}
