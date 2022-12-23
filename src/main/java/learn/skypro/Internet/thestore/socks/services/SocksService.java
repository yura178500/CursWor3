package learn.skypro.Internet.thestore.socks.services;

import learn.skypro.Internet.thestore.socks.model.SocksColor;
import learn.skypro.Internet.thestore.socks.model.SocksSize;

public interface SocksService {
    int findByCottonPartLessThan(SocksColor socksColor,
                                 SocksSize socksSize,
                                 int cottonMin);
    int findByCottonPartMoreThan(SocksColor socksColor,
                                 SocksSize socksSize,
                                 int cottonMax);

    boolean addToStorage(SocksColor socksColor,
                         SocksSize socksSize,
                         int cottonPart,
                         int quantity);

    boolean delete(SocksColor socksColor,
                   SocksSize socksSize,
                   int cottonPart,
                   int quantity);

    boolean releaseFromStorage(SocksColor socksColor,
                               SocksSize socksSize,
                               int cottonPart,
                               int quantity);
}
