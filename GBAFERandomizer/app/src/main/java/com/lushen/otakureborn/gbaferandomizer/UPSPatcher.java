package com.lushen.otakureborn.gbaferandomizer;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by lushe_000 on 7/17/2015.
 */
public class UPSPatcher {

    public static Boolean patchByteArrayWithUPSFile(byte[] byteArray, InputStream patchInputStream) {
        byte[] originalCopy = Arrays.copyOf(byteArray, byteArray.length);
        byte[] patchByteArray = null;
        InputStream input = null;
        try {
            input = patchInputStream;
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024 *8];
            int bytesRead = 0;

            while ((bytesRead = input.read(buffer)) != -1) {
                byteOutput.write(buffer, 0, bytesRead);
            }

            patchByteArray = byteOutput.toByteArray();

        } catch (FileNotFoundException exception) {
            Log.d("RANDOMIZING", "Source File Not Found!");
        } catch (IOException exception) {
            Log.d("RANDOMIZING", "Failed to read from Source File!");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException exception) {
                    Log.d("RANDOMIZING", "Failed to close input stream.");
                }
            }
        }

        // No error checking for now. Just patch it.
        Integer targetPosition = 0;
        Integer patchPosition = 4;

        DecodedPointerResult result = decodePointer(patchByteArray, patchPosition);
        long inputLength = result.pointer;
        patchPosition += result.byteLength;

        result = decodePointer(patchByteArray, patchPosition);
        long outputLength = result.pointer;
        patchPosition += result.byteLength;

        long relative = 0;

        long targetOffset = 0;
        long lastOffset = 0;

        while (patchPosition < patchByteArray.length - 12) {
            result = decodePointer(patchByteArray, patchPosition);
            patchPosition += result.byteLength;
            relative = relative + result.pointer;

            if (relative > byteArray.length) {
                continue;
            }

            targetPosition = Integer.valueOf((int)relative);

            targetOffset = relative;
            for (long i = relative; i < outputLength - 1; i++) {
                byte delta = patchByteArray[patchPosition];
                patchPosition++;
                relative += 1;
                if (delta == 0) {
                    break;
                }
                if (i < outputLength) {
                    byte currentByte = (i < inputLength ? originalCopy[(int)relative - 1] : 0);
                    byteArray[targetPosition] = Integer.valueOf(delta ^ currentByte).byteValue();
                    targetPosition++;
                }
            }
        }

        return true;
    }

    private static class DecodedPointerResult {
        public long pointer;
        public Integer byteLength;

        public DecodedPointerResult() {
            super();
        }
    }

    private static DecodedPointerResult decodePointer(byte[] byteArray, Integer position) {
        long offset = (long)0;
        long shift = (long)1;

        Integer length = 0;

        Integer currentPosition = position;

        while (true) {
            byte input = byteArray[currentPosition];
            length++;
            currentPosition++;

            offset += (input & 0x7F) * shift;
            if ((input & 0x80) != 0) {
                break;
            }

            shift <<= 7;

            offset += shift;
        }

        DecodedPointerResult result = new DecodedPointerResult();
        result.pointer = offset;
        result.byteLength = length;

        return result;

    }
}
