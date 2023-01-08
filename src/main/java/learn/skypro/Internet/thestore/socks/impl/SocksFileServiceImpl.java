package learn.skypro.Internet.thestore.socks.impl;

import learn.skypro.Internet.thestore.socks.services.SocksFileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BooleanSupplier;

@Service
public class SocksFileServiceImpl implements SocksFileService {
    @Value("${path.to.socksListJson.file}")
    private String socksListFilePath;

    @Value("${name.of.socksListJson.file}")
    private String socksListFileName;

    @Override
    public boolean cleanSocksListJson() {
        try {
            Path path = Path.of(socksListFilePath, socksListFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public File getSocksListJson() {
        return new File(socksListFilePath + "/" + socksListFileName);
    }

    @Override
    public BooleanSupplier saveSocksListToJsonFile(String json) {
        try {
            cleanSocksListJson();
            Files.writeString(Path.of(socksListFilePath, socksListFileName), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String readSocksListFromJsonFile() {
        if (Files.exists(Path.of(socksListFilePath, socksListFileName))) {
            try {
                return Files.readString(Path.of(socksListFilePath, socksListFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public String getSocksListFilePath() {
        return socksListFilePath;
    }
    @Override
    public String getSocksListFileName() {
        return socksListFileName;
    }

}

