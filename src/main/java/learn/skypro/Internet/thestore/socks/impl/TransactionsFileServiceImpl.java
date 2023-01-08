package learn.skypro.Internet.thestore.socks.impl;

import learn.skypro.Internet.thestore.socks.services.TransactionsFileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BooleanSupplier;

@Service
public class TransactionsFileServiceImpl implements TransactionsFileService {
    @Value("${path.to.transactionsJson.file}")
    private String transactionsListFilePath;

    @Value("${name.of.transactionsJson.file}")
    private String transactionsListFileName;

    @Value("${path.to.transactionsTXT.file}")
    private String transactionsTxtFilePath;

    @Value("${name.of.transactionsTXT.file}")
    private String transactionsTxtFileName;

    @Override
    public BooleanSupplier cleanTransactionsListJson(){
        try {
            Path path = Path.of(transactionsListFilePath, transactionsListFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void cleanTransactionsListTxt(){
        try {
            Path path = Path.of(transactionsTxtFilePath, transactionsTxtFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public File getTxtFile() {
        if (Files.exists(Path.of(transactionsTxtFilePath, transactionsTxtFileName))) {
            return new File(transactionsTxtFilePath + "/" + transactionsTxtFileName);
        }
        return null;
    }
    @Override
    public File getTransactionsListJson() {
        if (Files.exists(Path.of(transactionsListFilePath, transactionsListFileName))) {
            return new File(transactionsListFilePath + "/" + transactionsListFileName);
        }
        return null;
    }
    @Override
    public BooleanSupplier saveTransactionsListToJsonFile(String json) {
        try {
            cleanTransactionsListJson();
            Files.writeString(Path.of(transactionsListFilePath, transactionsListFileName), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public BooleanSupplier saveTransactionsToTxtFile(String txt) {
        try {
            cleanTransactionsListTxt();
            Files.writeString(Path.of(transactionsTxtFilePath, transactionsTxtFileName), txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public String readTransactionsListFromJsonFile(){
        if (Files.exists(Path.of(transactionsListFilePath, transactionsListFileName))) {
            try {
                Files.readString(Path.of(transactionsListFilePath, transactionsListFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public String getTransactionsListFilePath() {
        return transactionsListFilePath;
    }
    @Override
    public String getTransactionsListFileName() {
        return transactionsListFileName;
    }
    @Override
    public String getTransactionsTxtFilePath() {
        return transactionsTxtFilePath;
    }

    @Override
    public boolean cleanTransactionsTxt() {
        return false;
    }

    @Override
    public String getTransactionsTxtFileName() {
        return transactionsTxtFileName;
    }
}