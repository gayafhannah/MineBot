package com.shoe.minebot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Utilities {
    public static byte[] writeVarInt(int value, ByteArrayOutputStream outputStream/*byte[] b*/) throws IOException {
        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //outputStream.write(b);
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            outputStream.write(temp);
            //Networking.writeByte(temp);
        } while (value != 0);
        return outputStream.toByteArray();
    }

    public static int readVarInt(ByteArrayInputStream inputStream) throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = (byte)inputStream.read();
            //System.out.printf("Read: (%x) numRead: (%x) result: (%x)\n",read,numRead,result);
            int value = (read & 0b01111111);
            //System.out.printf("Read: (%x) numRead: (%x) value: (%x)\n",read,numRead,result);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static void writeString(String s, ByteArrayOutputStream outputStream) throws IOException {
        int length = s.length();
        writeVarInt(length,outputStream);
        outputStream.write(s.getBytes("UTF-8"));
    }

    public static byte[] doubleToByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }
    public static double byteArrayToDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble(); ///////USER THSIFIEUHGIWUSH
    }



    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
