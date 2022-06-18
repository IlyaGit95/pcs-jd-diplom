import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
//        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
//        System.out.println(engine.search("бизнес"));

        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате


//        File dir = new File("pdfs");
//        List<File> pdfFileList = new ArrayList<>();
//
//        Map<String, List<PageEntry>> resultMap = new HashMap<>();
//
//        for (File file : Objects.requireNonNull(dir.listFiles())) {
//            if (file.isFile())
//                pdfFileList.add(file);
//        }
//
//        for (File file : pdfFileList) {
//            PdfDocument doc = new PdfDocument(new PdfReader(file));
//
//            for (int i = 1; i < doc.getNumberOfPages() + 1; i++) {
//                PdfPage page = doc.getPage(i);
//                String text = PdfTextExtractor.getTextFromPage(page);
//                String[] words = text.split("\\P{IsAlphabetic}+");
//                Map<String, Integer> freqs = new HashMap<>();
//
//                for (var word : words) {
//                    if (word.isEmpty()) {
//                        continue;
//                    }
//                    freqs.put(word.toLowerCase(), freqs.getOrDefault(word.toLowerCase(), 0) + 1);
//                }
//
//                for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
//                    PageEntry pageEntry = new PageEntry(file.getName(), i, entry.getValue());
//                    if (!resultMap.containsKey(entry.getKey())) {
//                        resultMap.put(entry.getKey(), new ArrayList<>());
//                    }
//                    resultMap.get(entry.getKey()).add(pageEntry);
//                }
//            }
//        }
//
//        for (Map.Entry<String, List<PageEntry>> entry : resultMap.entrySet()) {
//            Collections.sort(entry.getValue());
//        }
//
//        for (Map.Entry<String, List<PageEntry>> entry : resultMap.entrySet()) {
//            if (entry.getKey().equals("бизнес")) {
//                System.out.println(entry.getKey());
//                for (PageEntry pageEntry : entry.getValue()) {
//                    System.out.println(pageEntry);
//                }
//                System.out.println(entry.getValue().size());
//            }

//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue());
//            System.out.println("------------------------------");

        BooleanSearchEngine booleanSearchEngine = new BooleanSearchEngine(new File("pdfs"));

        System.out.println(booleanSearchEngine.search("бизнес"));
    }
}