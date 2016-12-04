package agh.cs.DocReader;

/**
 * DocReader
 * Created by luknw on 02 12 2016.
 */

public class DocReader {
    private static final String CHAPTER_OPTION = "-c";
    private static final String INVALID_ARGUMENTS = "Invalid arguments";

    private static String path;

    private static int chapter;
    private static boolean chapterMode = false;

    private static int firstArticle;

    private static int lastArticle;
    private static boolean rangeMode = false;

    public static void main(String[] args) {
        if (!parseArgs(args)) {
            System.err.println(INVALID_ARGUMENTS);
            return;
        }

        DocumentNode constitution = new Parser().parse(path);

        Printer printer = new Printer();
        if (chapterMode) printer.printChapter(constitution, chapter);
        else if (rangeMode) printer.printArticles(constitution, firstArticle, lastArticle);
        else printer.printArticle(constitution, firstArticle);
    }

    private static boolean parseArgs(String[] args) {
        if (args.length < 2) return false;

        path = args[0];

        if (args[1].equals(CHAPTER_OPTION)) {
            if (args.length < 3) return false;
            chapter = Integer.parseInt(args[2]);
            chapterMode = true;
        } else {
            firstArticle = Integer.parseInt(args[1]);
            if (args.length > 2) {
                lastArticle = Integer.parseInt(args[2]);
                rangeMode = true;
            }
        }
        return true;
    }
}
