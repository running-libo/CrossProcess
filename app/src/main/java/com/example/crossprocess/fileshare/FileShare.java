package com.example.crossprocess.fileshare;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileShare {

    public static final String SHARE_FILE = "/sharefile";

    /**
     * 将对象写入文件
     */
    public void writeObject() {
        NoteBook noteBook = new NoteBook();
        noteBook.setName("cr");
        noteBook.setContent("Empty");

        File file = new File(Environment.getExternalStorageDirectory() + SHARE_FILE);
        ObjectOutputStream outputStream = null;
        try{
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(noteBook);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件读取对象
     */
    public void readObject() {
        File file = new File(Environment.getExternalStorageDirectory() + SHARE_FILE);
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            NoteBook noteBook = (NoteBook) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class NoteBook implements Serializable {
    private String mName;
    private String mContent;

    public void setName(String name) {
        mName = name;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
