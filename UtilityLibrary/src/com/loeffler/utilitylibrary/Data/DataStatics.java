package com.loeffler.utilitylibrary.Data;

import com.loeffler.utilitylibrary.Logging.Logger;
import com.loeffler.utilitylibrary.Statics;
import java.io.ByteArrayOutputStream;

import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 *  <p><strong>DataStatics</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    Github.com/JohnLoeffler
 *    <em>@Bitbucket</em> Bitbucket.org/JohnLoeffler
 */
public class DataStatics {
  private static final Logger LOG = Logger.GetInstance();
  /**
   * Compresses a byte array
   * @param bytes The bytes to compress
   * @return A compressed byte array; returns null if compression fails
   */
  public static byte[] CompressByteArray(byte[] bytes){
    Deflater dfl = new Deflater();
    dfl.setLevel(Deflater.BEST_COMPRESSION);
    dfl.setInput(bytes);
    dfl.finish();
    try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
      byte[] comp = new byte[4096];
      while(!dfl.finished()){
        int size = dfl.deflate(comp);
        bos.write(comp, 0, size);
      }
      return bos.toByteArray();
    }catch(Exception ex){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),
        String.format("Failed to Compress Byte Array: %s", ex.getMessage()));
      return null;
    }
  }
  /**
   * Decompresses a byte array
   * @param bytes The compressed bytes to decompress
   * @return A decompressed byte array, returns null if decompression fails
   */
  public static byte[] DecompressByteArray(byte[] bytes){
    Inflater ifl = new Inflater();
    ifl.setInput(bytes);
    try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
      byte[] comp = new byte[4096];
      while(!ifl.finished()){
        int size = ifl.inflate(comp);
        bos.write(comp, 0, size);
      }
      return bos.toByteArray();
    }catch(Exception ex){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),
        String.format("Failed to Decompress Byte Array: %s", ex.getMessage()));
      return null;
    }
  }
}
