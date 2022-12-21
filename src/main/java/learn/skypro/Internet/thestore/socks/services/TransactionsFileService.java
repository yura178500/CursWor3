package learn.skypro.Internet.thestore.socks.services;

import java.io.File;

public interface TransactionsFileService {
    void cleanTransactionsListJson();

    void cleanTransactionsListTxt();

    File getTxtFile();

    File getTransactionsListJson();

    void saveTransactionsListToJsonFile(String json);

    void saveTransactionsToTxtFile(String txt);

    String readTransactionsListFromJsonFile();
}
