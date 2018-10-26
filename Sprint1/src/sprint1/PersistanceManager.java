package sprint1;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

class PersistanceManager {
   
   public PersistanceManager {
   }
   
   //Static method to save the entire system. Hint: a binary file is the easiest way to do this
   public static save (SiteManager sm, FileOutputStream file) {
      try {
         ObjectOutputStream binFile = new ObjectOutputStream(file);
         binFile.writeObject(sm);
         binFile.flush();
         binFile.close();
      }
      catch (Exception e) {
         System.out.println(e);
      }
             
   }
   
   //Static method to read the system from file and return a SiteManager object
   public static read (FileInputStream file) {
      ObjectInputStream binFile = new ObjectInputStream(file);
      SiteManager savedPersistanceManager = (SiteManager) binFile.readObject();
   }
}
