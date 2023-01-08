package learn.skypro.Internet.thestore.socks.services;

import java.io.File;
import java.util.function.BooleanSupplier;


public interface TransactionsFileService {
    BooleanSupplier cleanTransactionsListJson();
    void cleanTransactionsListTxt();
    File getTxtFile();

    File getTransactionsListJson();

    BooleanSupplier saveTransactionsListToJsonFile(String json);

    BooleanSupplier saveTransactionsToTxtFile(String txt);
    String readTransactionsListFromJsonFile();

    String getTransactionsListFileName();

    String getTransactionsListFilePath();

    String getTransactionsTxtFileName();

    String getTransactionsTxtFilePath();

    boolean cleanTransactionsTxt();
}