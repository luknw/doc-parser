package agh.cs.DocReader;

import java.util.ListIterator;

/**
 * DocReader
 * Created by luknw on 02 12 2016.
 */
public class Printer implements DocumentPrinter {
    private static final String INVALID_ARTICLE_RANGE = "Invalid article range";
    private static final String INVALID_CHAPTER_NUMBER = "Invalid chapter number";
    private static final String INVALID_ARTICLE_NUMBER = "Invalid article number";

    private DocumentNode root = null;
    private ListIterator<DocumentNode> chapterIterator = null;
    private ListIterator<DocumentNode> articleIterator = null;
    private int articlesToPrint;

    private void iterateBeforeArticle(int articleNumber) {
        //skip to proper chapter
        chapterIterator = root.getChildren().listIterator();
        DocumentNode currentChapter = null;
        int articlesInCurrentChapter = 0;
        int skippedArticles = 0;
        while (skippedArticles + articlesInCurrentChapter < articleNumber && chapterIterator.hasNext()) {
            skippedArticles += articlesInCurrentChapter;
            currentChapter = chapterIterator.next();
            articlesInCurrentChapter = currentChapter.getChildren().size();
        }
        //check if the chapter contains searched article and if so, set currentArticleIndex
        int articlesToSkip = articleNumber - skippedArticles;
        if (currentChapter == null || articlesToSkip > articlesInCurrentChapter)
            throw new IndexOutOfBoundsException(INVALID_ARTICLE_NUMBER);
        //set chapter iterator one before chapter containing current article
        chapterIterator.previous();
        //set article iterator one before searched article
        articleIterator = currentChapter.getChildren().listIterator(articlesToSkip - 1);
    }

    private void printArticlesLeftInChapter() {
        while (articlesToPrint > 0 && articleIterator.hasNext()) {
            System.out.println(articleIterator.next());
            --articlesToPrint;
        }
    }

    private void printArticleRange(int first) {
        if (first < 1) {
            System.err.println(INVALID_ARTICLE_RANGE);
            return;
        }

        try {
            iterateBeforeArticle(first);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(INVALID_ARTICLE_RANGE);
            return;
        }
        printArticlesLeftInChapter();
        chapterIterator.next(); //advance chapter iterator past already printed chapter

        while (articlesToPrint > 0 && chapterIterator.hasNext()) { //print the rest of the articles
            articleIterator = chapterIterator.next().getChildren().listIterator();
            printArticlesLeftInChapter();
        }
        if (articlesToPrint > 0) System.err.println(INVALID_ARTICLE_RANGE);
    }

    public void printChapter(DocumentNode root, int chapterNumber) {
        if (chapterNumber > root.getChildren().size() || chapterNumber < 1) {
            System.err.println(INVALID_CHAPTER_NUMBER);
            return;
        }
        System.out.println(root.getChildren().get(chapterNumber - 1));
    }

    public void printArticle(DocumentNode root, int articleNumber) {
        this.root = root;
        this.articlesToPrint = 1;
        printArticleRange(articleNumber);
    }

    public void printArticles(DocumentNode root, int first, int last) {
        this.root = root;
        this.articlesToPrint = last - first + 1;
        printArticleRange(first);
    }
}
