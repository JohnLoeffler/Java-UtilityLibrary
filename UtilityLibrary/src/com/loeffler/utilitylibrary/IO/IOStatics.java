package com.loeffler.utilitylibrary.IO;

import com.loeffler.utilitylibrary.Logging.Logger;
import com.loeffler.utilitylibrary.Statics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;


/**
 *  <p><strong>IOStatics</strong></p>
 *  <p>A derived class from Statics that deals with IO operations</p>
 *    <em>@author</em>    John Loeffler
 *  
 *    <strong>Contact Info:</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    Github.com/JohnLoeffler
 *    <em>@Bitbucket</em> Bitbucket.com/JohnLoeffler
 */
public class IOStatics{
  private static final Logger LOG = Logger.GetInstance();
  /**
   * Determines whether the path given is valid
   * @param path  A string of the pathname
   * @return true if path is valid, false otherwise
   */
  public static boolean QualifyFilepath(String path){
    File file = new File(path);
    try{
      if(file.getAbsoluteFile().getParentFile().isDirectory()){
        return true;
      }
    }catch(SecurityException se){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(), String.format(
        "Security Exception thrown while attempting to access %s\n  %s", 
          path, se.getMessage()), 3);
      return false;
    }
    return false;
  }
  /**
   * Determines whether the file associated with the given filename exists
   * @param filename  the name of the file to check
   * @return true if the string names a valid file, false otherwise
   */
  public static boolean QualifyFile(String filename){
    File file = new File(filename);
    try{
      if(file.getAbsoluteFile().getParentFile().exists() && file.exists()){
        return true;
      }
    }catch(SecurityException se){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(), String.format(
        "Security Exception thrown while attempting to access %s\n  %s", 
          filename, se.getMessage()), 3);
      return false;
    }
    return false;
  }
  
  /**
   * Returns the contents of a file as a byte array
   * @param filename The filename including the path
   * @return A byte array containing the file contents
   */
  public static synchronized byte[]  GetFileAsBytes(String filename){
    byte[] buf = new byte[2048];
    int read = 0;
    long size = 0;
    
    File file = new File(filename);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
    //  Read in file contents as bytes
    try(FileInputStream fis = new FileInputStream(file)){
      read = fis.read(buf);
      while(read != -1){
        bos.write(buf, 0, read);
        size += read;
        read = fis.read(buf);
      }
      return bos.toByteArray();
    }catch(IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(), String.format(
        "Error reading bytes from file: %s",ioe.getMessage()), 4);
      return null;
    }
  }
  /**
   * Returns the contents of a file as a byte array
   * @param file The file to read in as bytes
   * @return A byte array containing the file contents
   */
  public static synchronized byte[]  GetFileAsBytes(File file){
    byte[] buf = new byte[2048];
    int read = 0;
    long size = 0;
    
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
    //  Read in file contents as bytes
    try(FileInputStream fis = new FileInputStream(file)){
      read = fis.read(buf);
      while(read != -1){
        bos.write(buf, 0, read);
        size += read;
        read = fis.read(buf);
      }
      
      return bos.toByteArray();
    }catch(IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(), String.format(
        "Error reading bytes from file: %s",ioe.getMessage()), 4);
      return null;
    }
  }
  /**
   * Converts a byte array into an object
   * @param bytes The byte array to convert
   * @return An object
   */
  public static synchronized Object  ConvertBytesToObject(byte[] bytes){
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ObjectInput in = null;
    Object o = null;
    try{
      in = new ObjectInputStream(bis);
      try{
        o = in.readObject();
      }catch(ClassNotFoundException ex){
        LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
          "Error converting bytes to object, ClassNotFoundException thrown:\n\t%s" 
            ,ex.getMessage()), 4);
      }
    }catch(IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error converting bytes to object, IOException thrown:\n\t%s",
          ioe.getMessage()), 4);
    }finally{
      try{
        if(in != null){
          in.close();
        }
      }catch(Exception e){
        LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
          "Error closing object input stream:\n\t%s",e.getMessage()), 4);
      }
    }
    return o;
  }
  /**
   * Converts an Object to a byte array
   * @param o The object to convert
   * @return a byte array of the object
   */
  public static synchronized byte[]  ConvertObjectToBytes(Object o){
    ObjectOutput out = null;
    try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
      out = new ObjectOutputStream(bos);
      out.writeObject(o);
      out.flush();
      byte[] bytes = bos.toByteArray();
      return bytes;
    }catch(IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error converting object to bytes, IOException thrown:\n\t%s",
          ioe.getMessage()), 4);
      return null;
    }
  }
  /**
   * Writes a byte array to a file indicated by the given path and filename
   * @param bytes The byte array to write to file
   * @param path  The path to create the file in
   * @param filename  The filename to use for the file
   * @param append  True to append bytes to existing file, false to overwrite
   * @return A File object containing the bytes, if necessary
   */
  public static synchronized File WriteBytesToFile(
                                      byte[] bytes, 
                                      String path, 
                                      String filename, 
                                      boolean append){
    File file = new File(String.format("%s\\%s", path, filename));
    try(FileOutputStream fos = new FileOutputStream(file, append)){
      fos.write(bytes);
      fos.flush();
    }catch(IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error writing bytes to file, IOException thrown:\n\t%s",
          ioe.getMessage()), 4);
    }
    return file;
  }
  /**
   * Writes a byte array to a file indicated by the given filename
   * @param bytes The byte array to write to file
   * @param name  The filename to use for the file
   * @param append  True to append bytes to existing file, false to overwrite
   * @return A File object containing the bytes, if necessary
   */
  public static synchronized File WriteBytesToFile(
                                      byte[] bytes,
                                      String name, 
                                      boolean append){
    File file = new File(name);
    try(FileOutputStream fos = new FileOutputStream(file)){
      fos.write(bytes);
      fos.flush();
    }catch(IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error writing bytes to file, IOException thrown:\n\t%s",
          ioe.getMessage()), 4);
    }
    return file;
  }
  /**
   * Writes a byte array to a file passed as param
   * @param bytes The byte array to write to file
   * @param file  The file to write to
   * @param append  True to append bytes to existing file, false to overwrite
   */
  public static synchronized void WriteBytesToFile(
                                      byte[] bytes, 
                                      File file, 
                                      boolean append){
    try(FileOutputStream fos = new FileOutputStream(file)){
      fos.write(bytes);
      fos.flush();
    }catch(IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error writing bytes to file, IOException thrown:\n\t%s",
          ioe.getMessage()), 4);
    }
  }
  /**
   *  Gets an input stream from a url
   *  @param url The URL to get the input stream from
   *  @return An InputStream from the given url
   */
  public static synchronized InputStream GetURLStream(String url){
    InputStream in = null;
    try{
      URL turl = new URL(url);
      try{
        in = turl.openStream();
      }catch(IOException ioe){
        LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
          "Error opening InputStream from %s:\n\t%s", url, ioe.getMessage()),4);
      }
    }catch(MalformedURLException mue){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error opening InputStream from %s:\n\t%s", url, mue.getMessage()),4);
    }
    return in;
  }
  /**
   *  Gets a byte array from an InputStream
   *  @param is An InputStream to read
   *  @return A byte array
   */
  public static synchronized byte[]  GetBytesFromInputStream(InputStream is){
    try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
        
      //  SETUP A READ BUFFER
      byte[] buf = new byte[2048];

      //  READ IN THE INPUT AS A SERIES OF BYTES
      for(int bytesRead; (bytesRead = is.read(buf)) != -1;){
        bos.write(buf, 0, bytesRead);
      }

      // GET THE BUFFERED IMAGE AS A BYTE ARRAY
      return bos.toByteArray();
    }catch (IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error reading bytes from input stream: %s", ioe.getMessage()),4);
      return null;
    }catch(NullPointerException e){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error reading bytes from input stream, Input Stream is 'null': %s", 
          e.getMessage()),4);
      return null;
    }
  }
  /**
   * Gets an InputStream from a byte array
   * @param bytes The byte array to create the input stream from
   * @return An InputStream for the byte array
   */
  public static synchronized InputStream GetInputStreamFromBytes(byte[] bytes){
    try(ByteArrayInputStream bis = new ByteArrayInputStream(bytes)){
      return bis;
    }catch(IOException ioe){
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(),String.format(
        "Error creating input stream from bytes: %s", ioe.getMessage()),4);
      return null;
    }
  }
}
