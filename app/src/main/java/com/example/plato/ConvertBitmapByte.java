package com.example.plato;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ConvertBitmapByte {
    public static byte[] bitmapTobyte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public static Bitmap byteTobitmap(byte[]  bytes){
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
        return BitmapFactory.decodeStream(arrayInputStream);
    }
}
