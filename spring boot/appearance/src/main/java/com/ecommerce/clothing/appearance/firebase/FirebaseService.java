/*
package com.ecommerce.clothing.appearance.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseService {

    @Autowired
    private Storage storage;

    public String uploadImageOnFirebase(MultipartFile file, String folderName, String fileName) throws IOException {

        StorageClient storage = StorageClient.getInstance();
        String destination = folderName + fileName;
        Blob blob = storage.bucket().create(destination, file.getBytes(), file.getContentType());
        return blob.getMediaLink();
    }

}


*/
