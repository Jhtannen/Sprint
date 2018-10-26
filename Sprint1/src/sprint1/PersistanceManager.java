package sprint1;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class PersistanceManager {
   
   public PersistanceManager {
   }
   
   public static save (SiteManager sm, FileOutputStream file) {
      file = new FileOutputStream("SiteManager.java");
      ObjectOutputStream binFile = new ObjectOutputStream(file);
      binFile.writeObject(sm);
      binFile.flush();
      binFile.close();
      
   }
   
   public static read (File file) {
   }
}
