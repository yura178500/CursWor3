package learn.skypro.Internet.thestore.socks.services;
import java.io.File;
import java.util.function.BooleanSupplier;


public interface SocksFileService {
    boolean cleanSocksListJson();

    File getSocksListJson();

    BooleanSupplier saveSocksListToJsonFile(String json);
    String readSocksListFromJsonFile();

    String getSocksListFilePath();

    String getSocksListFileName();
}
