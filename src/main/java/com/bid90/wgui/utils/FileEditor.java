package com.bid90.wgui.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A utility class for editing files.
 */
public class FileEditor {

    /**
     * Edits the content of a file.
     *
     * @param filePath   The path of the file to be edited.
     * @param newContent The new content to be written to the file.
     * @throws IOException if an error occurs while editing the file.
     */
    public static void editFile(String filePath, String newContent) throws IOException {

            // Create a File object with the specified file path
            File file = new File(filePath);

            // If the file doesn't exist, create a new file
            if (!file.exists()) {
                file.createNewFile();
            }

            // Create a BufferedWriter to write to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            // Write the new content to the file
            writer.write(newContent);

            // Close the writer
            writer.close();


    }
}