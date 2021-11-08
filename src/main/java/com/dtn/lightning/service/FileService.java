package com.dtn.lightning.service;

import java.io.IOException;
import java.util.List;

public interface FileService<T> {

    List<T> readFile() throws IOException;

    void appendFile(T object) throws IOException;

}
