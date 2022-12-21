package learn.skypro.Internet.thestore.socks.impl;

import learn.skypro.Internet.thestore.socks.model.TransactionsType;
import learn.skypro.Internet.thestore.socks.repository.StorageUnitRepository;
import learn.skypro.Internet.thestore.socks.services.SocksService;
import learn.skypro.Internet.thestore.socks.model.SocksColor;
import learn.skypro.Internet.thestore.socks.model.SocksSize;
import learn.skypro.Internet.thestore.socks.repository.TransactionRepository;
import org.springframework.stereotype.Service;


@Service
public class SocksServiceImpl implements SocksService {
    private final TransactionRepository transactionRepository;

    private final StorageUnitRepository storageUnitRepository;

    public SocksServiceImpl(TransactionRepository transactionRepository, StorageUnitRepository storageUnitRepository) {
        this.transactionRepository = transactionRepository;
        this.storageUnitRepository = storageUnitRepository;
    }
    @Override
    public int findByCottonPartLessThan(SocksColor socksColor,
                                        SocksSize socksSize,
                                        int cottonMin) {
        return storageUnitRepository.findByCottonPartLessThan(socksColor, socksSize, cottonMin);
    }
    @Override
    public int findByCottonPartMoreThan(SocksColor socksColor,
                                        SocksSize socksSize,
                                        int cottonMax) {
        return storageUnitRepository.findByCottonPartMoreThan(socksColor, socksSize, cottonMax);
    }
    @Override
    public boolean addToStorage(SocksColor socksColor,
                                SocksSize socksSize,
                                int cottonPart,
                                int quantity) {
        transactionRepository.addSTransaction(cottonPart,
                socksColor,
                socksSize,
                quantity,
                TransactionsType.INCOMING);
        return storageUnitRepository.addInStorageUnitRepository(cottonPart,
                socksColor,
                socksSize,
                quantity);
    }
    @Override
    public boolean delete(SocksColor socksColor,
                          SocksSize socksSize,
                          int cottonPart,
                          int quantity) {
        transactionRepository.addSTransaction(cottonPart, socksColor, socksSize, quantity, TransactionsType.CANCELLATION);
        return storageUnitRepository.delete(cottonPart,
                socksColor,
                socksSize,
                quantity);
    }
    @Override
    public boolean releaseFromStorage(SocksColor socksColor,
                                      SocksSize socksSize,
                                      int cottonPart,
                                      int quantity) {
        transactionRepository.addSTransaction(cottonPart, socksColor, socksSize, quantity, TransactionsType.OUTGOING);
        return storageUnitRepository.outFromStorage(cottonPart,
                socksColor,
                socksSize,
                quantity);
    }
}

