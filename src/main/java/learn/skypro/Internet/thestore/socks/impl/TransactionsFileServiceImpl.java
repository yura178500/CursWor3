package learn.skypro.Internet.thestore.socks.impl;

import learn.skypro.Internet.thestore.socks.services.TransactionsFileService;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TransactionsFileServiceImpl implements TransactionsFileService {
    @org.springframework.beans.factory.annotation.Value("${path.to.transactionsJson.file}")
    private String transactionsListFilePath;

    @org.springframework.beans.factory.annotation.Value("${name.of.transactionsJson.file}")
    private String transactionsListFileName;

    @org.springframework.beans.factory.annotation.Value("${path.to.transactionsTXT.file}")
    private String transactionsTxtFilePath;

    @org.springframework.beans.factory.annotation.Value("${name.of.transactionsTXT.file}")
    private String transactionsTxtFileName;
    @Override
    public void cleanTransactionsListJson(){
        try {
            Path path = Path.of(transactionsListFilePath, transactionsListFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void saveTransactionsListToJsonFile(String json) {
        try {
            cleanTransactionsListJson();
            Files.writeString(Path.of(transactionsListFilePath, transactionsListFileName), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveTransactionsToTxtFile(String txt) {
        try {
            cleanTransactionsListTxt();
            Files.writeString(Path.of(transactionsTxtFilePath, transactionsTxtFileName), txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}

