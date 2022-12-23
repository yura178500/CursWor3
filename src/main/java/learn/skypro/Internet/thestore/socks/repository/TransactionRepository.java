package learn.skypro.Internet.thestore.socks.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import learn.skypro.Internet.thestore.socks.model.SocksColor;
import learn.skypro.Internet.thestore.socks.model.SocksSize;
import learn.skypro.Internet.thestore.socks.model.TransactionSocks;
import learn.skypro.Internet.thestore.socks.model.TransactionsType;
import learn.skypro.Internet.thestore.socks.services.TransactionsFileService;
import learn.skypro.Internet.thestore.socks.model.*;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
@Repository
public class TransactionRepository {
    private final TransactionsFileService transactionsFileService;

    public TransactionRepository(TransactionsFileService transactionsFileService) {
        this.transactionsFileService = transactionsFileService;
    }

    static Long idCounter = 1L;
    TreeMap<Long, TransactionSocks> transactionList = new TreeMap<>();

    public void addSTransaction(int cottonPart,
                                SocksColor socksColor,
                                SocksSize socksSize,
                                int quantity,
                                TransactionsType transactionsType
    ) {
        idCounter = 1L;
        while (transactionList.containsKey(idCounter)) {
            idCounter++;
        }
        String fullDateCreateTransaction = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        String onlyDate = fullDateCreateTransaction.substring(6, 16);
        String onlyTime = fullDateCreateTransaction.substring(0, 5);

        transactionList.put(idCounter, new TransactionSocks(cottonPart,
                socksColor,
                socksSize,
                quantity,
                onlyDate,
                onlyTime,
                transactionsType));
        transactionsFileService.saveTransactionsListToJsonFile(jsonFromList());
        transactionsFileService.saveTransactionsToTxtFile(viewAllTransactions());
    }

    private String jsonFromList() {
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(transactionList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    private TreeMap<Long, TransactionSocks> listFromFile() {
        try {
            String json = transactionsFileService.readTransactionsListFromJsonFile();
            if (StringUtils.isNotEmpty(json) || StringUtils.isNotBlank(json)) {
                transactionList = new ObjectMapper().readValue(json, new TypeReference<>() {
                });
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return transactionList;
    }

    public String viewAllTransactions() {
        String result = "";
        int counter = 0;
        String commonHeader = "Список транзакций (движение товара по складу):";
        String incomingHeader = "Поступление на склад:";
        String outgoingHeader = "Выдача со склада:";
        String cancellationHeader = "Списание брака со склада:";
        String incomingBody = "";
        String outgoingBody = "";
        String cancellationBody = "";

        //сформируем список уникальных дат
        TreeSet<String> sortedDates = new TreeSet<>();
        for (TransactionSocks transaction : getTransactionList().values()) {
            sortedDates.add(transaction.getDateCreateTransaction());
        }
        //Теперь у нас есть список уникальных дат
        //На его основе и будем формировать текст
        for (String date : sortedDates) {
            result += "дата - " + date + "\n";
            for (TransactionSocks transaction : getTransactionList().values()) {
                if (transaction.getDateCreateTransaction().equals(date)) {
                    if (transaction.getTransactionsType().equals(TransactionsType.INCOMING)) {
                        incomingBody += transaction.getTimeCreateTransaction() + " " +
                                "цвет " + transaction.getSocksColor() + ", " +
                                "размер " + transaction.getSocksSize() + ", " +
                                "содержание хлопка " + transaction.getCottonPart() + "%, " +
                                "количество " + transaction.getQuantity() + " пар.\n";
                    }
                    if (transaction.getTransactionsType().equals(TransactionsType.OUTGOING)) {
                        outgoingBody += transaction.getTimeCreateTransaction() + " " +
                                "цвет " + transaction.getSocksColor() + ", " +
                                "размер " + transaction.getSocksSize() + ", " +
                                "содержание хлопка " + transaction.getCottonPart() + "%, " +
                                "количество " + transaction.getQuantity() + " пар.\n";
                    }
                    if (transaction.getTransactionsType().equals(TransactionsType.CANCELLATION)) {
                        cancellationBody += transaction.getTimeCreateTransaction() + " " +
                                "цвет " + transaction.getSocksColor() + ", " +
                                "размер " + transaction.getSocksSize() + ", " +
                                "содержание хлопка " + transaction.getCottonPart() + "%, " +
                                "количество " + transaction.getQuantity() + " пар.\n";
                    }
                }
            }
            result += incomingHeader + "\n" +
                    incomingBody + "\n" +
                    outgoingHeader + "\n" +
                    outgoingBody + "\n" +
                    cancellationHeader + "\n" +
                    cancellationBody + "\n";
        }
        return result;
    }

    @PostConstruct
    private void init() {
        transactionList = listFromFile();
    }
}

