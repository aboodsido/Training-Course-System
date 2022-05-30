/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 *
 * @author Sword
 */
public class SystemLogger {
    
    private Logger logger;
    private static SystemLogger instance = null;
    public  static SystemLogger getInstance () throws IOException{
        if (instance == null)
            instance= new SystemLogger();
        return instance;
    }
    private SystemLogger() throws IOException{
        logger = Logger.getLogger("log_file");
        logger.addHandler(new FileHandler("log.txt"));
    }
    
    public  Logger getLogger(){
        return logger;
    }
    
}
