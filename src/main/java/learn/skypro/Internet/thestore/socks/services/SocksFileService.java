package learn.skypro.Internet.thestore.socks.services;

import java.io.File;

public interface SocksFileService {
    void cleanSocksListJson();
    File getSocksListJson();

    void saveSocksListToJsonFile(String json);

    String readSocksListFromJsonFile();
}
