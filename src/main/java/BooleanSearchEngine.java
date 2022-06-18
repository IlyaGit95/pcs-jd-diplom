import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    private Map<String, List<PageEntry>> wordMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
//        pdfsDir = new File("pdfs");
        List<File> pdfFileList = new ArrayList<>();
        for (File file : Objects.requireNonNull(pdfsDir.listFiles())) {
            if (file.isFile())
                pdfFileList.add(file);
        }
        PdfDocument doc;
        PdfPage page;
        String text;
        String[] words;
        Map<String, Integer> freqs;
        PageEntry pageEntry;
        for (File file : pdfFileList) {
            doc = new PdfDocument(new PdfReader(file));
            for (int i = 1; i < doc.getNumberOfPages() + 1; i++) {
                page = doc.getPage(i);
                text = PdfTextExtractor.getTextFromPage(page);
                words = text.split("\\P{IsAlphabetic}+");
                freqs = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    freqs.put(word.toLowerCase(), freqs.getOrDefault(word.toLowerCase(), 0) + 1);
                }
                for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                    pageEntry = new PageEntry(file.getName(), i, entry.getValue());
                    if (!wordMap.containsKey(entry.getKey())) {
                        wordMap.put(entry.getKey(), new ArrayList<>());
                    }
                    wordMap.get(entry.getKey()).add(pageEntry);
                }
            }
        }
        for (Map.Entry<String, List<PageEntry>> entry : wordMap.entrySet()) {
            Collections.sort(entry.getValue());
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        return wordMap.get(word);
    }
}
