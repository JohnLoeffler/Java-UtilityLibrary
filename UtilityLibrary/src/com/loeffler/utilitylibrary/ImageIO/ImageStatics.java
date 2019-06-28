package com.loeffler.utilitylibrary.ImageIO;

import com.loeffler.utilitylibrary.Logging.Logger;
import com.loeffler.utilitylibrary.Statics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *  <p><strong>ImageStatics</strong></p>
 *  <em>@author</em>  John Loeffler
 *  
 *  <strong>Contact</strong> 
 *    <em>@Email</em>     John.Loeffler@gmail.com
 *    <em>@Twitter</em>   @ThisDotJohn
 *    <em>@LinkedIn</em>  LinkedIn.com/in/JohnLoeffler
 *    <em>@Github</em>    Github.com/JohnLoeffler
 *    <em>@Bitbucket</em> Bitbucket.org/JohnLoeffler
 */
public class ImageStatics {
  private static final Logger LOG = Logger.GetInstance();
  
  /**
   * Finds the Width of a given image
   * @param imageBytes  The image file as a byte array
   * @return An int of the image's width
   */
  public static int GetWidth(byte[] imageBytes){
    InputStream is = new ByteArrayInputStream(imageBytes);
    try {
      BufferedImage bi = ImageIO.read(is);
      return bi.getWidth();
    } catch (IOException ex) {
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(), 
        "Failed to read in image into the image buffer");
      return -1;
    }
  }
  /**
   * Finds the Height of a given image
   * @param imageBytes  The image file as a byte array
   * @return An int of the image's height
   */
  public static int GetHeight(byte[] imageBytes){
    InputStream is = new ByteArrayInputStream(imageBytes);
    try {
      BufferedImage bi = ImageIO.read(is);
      return bi.getHeight();
    } catch (IOException ex) {
      LOG.Log(Statics.Class(), Statics.Method(), Statics.Line(), 
        "Failed to read in image into the image buffer");
      return -1;
    }
  }
}
