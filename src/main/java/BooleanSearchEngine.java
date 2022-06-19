import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    private final Map<String, List<PageEntry>> wordsMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        List<File> pdfFileList = getPdfFileList(pdfsDir);

        PdfDocument doc;
        PdfPage page;
        String text;
        String[] words;
        Map<String, Integer> freqs;

        for (File file : pdfFileList) {
            doc = new PdfDocument(new PdfReader(file));
            for (int i = 1; i < doc.getNumberOfPages() + 1; i++) {
                page = doc.getPage(i);
                text = PdfTextExtractor.getTextFromPage(page);
                words = text.split("\\P{IsAlphabetic}+");
                freqs = countingWords(words);
                addInWordsMap(freqs, file, i);
            }
        }
        sortingWordsMap();
    }

    private List<File> getPdfFileList(File pdfsDir) {
        List<File> pdfFileList = new ArrayList<>();
        for (File file : Objects.requireNonNull(pdfsDir.listFiles())) {
            if (file.isFile())
                pdfFileList.add(file);
        }
        return pdfFileList;
    }

    private void addInWordsMap(Map<String, Integer> freqs, File file, int i) {
        PageEntry pageEntry;
        for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
            pageEntry = new PageEntry(file.getName(), i, entry.getValue());
            if (!wordsMap.containsKey(entry.getKey())) {
                wordsMap.put(entry.getKey(), new ArrayList<>());
            }
            wordsMap.get(entry.getKey()).add(pageEntry);
        }
    }

    private Map<String, Integer> countingWords(String[] words) {
        Map<String, Integer> freqs = new HashMap<>();
        for (var word : words) {
            if (word.isEmpty()) {
                continue;
            }
            freqs.put(word.toLowerCase(), freqs.getOrDefault(word.toLowerCase(), 0) + 1);
        }
        return freqs;
    }

    private void sortingWordsMap() {
        for (Map.Entry<String, List<PageEntry>> entry : wordsMap.entrySet()) {
            Collections.sort(entry.getValue());
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        return wordsMap.get(word);
    }
}
