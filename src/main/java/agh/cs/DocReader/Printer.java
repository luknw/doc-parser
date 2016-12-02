package agh.cs.DocReader;

import java.util.Iterator;

/**
 * DocReader
 * Created by luknw on 02 12 2016.
 */
public class Printer implements DocumentPrinter {

    public void printChapter(DocumentNode root, int chapterIndex) {
        System.out.println(root.getChildren().get(chapterIndex));
    }

    public void printArticle(DocumentNode root, int articleIndex) {
        //skip to proper chapter
        Iterator<DocumentNode> chapterIterator = root.getChildren().iterator();
        DocumentNode currentChapter;

        int skippedArticles = 0;
        int articlesInCurrentChapter = 0;
        while (skippedArticles + articlesInCurrentChapter < articleIndex && chapterIterator.hasNext()) {
            skippedArticles += articlesInCurrentChapter;
            currentChapter = chapterIterator.next();
            articlesInCurrentChapter = currentChapter.getChildren().size();
        }

    }

    public void printArticles(DocumentNode root, int first, int last) {

    }
}
