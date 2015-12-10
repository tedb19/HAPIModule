/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hapimodule.core.utils;

import hapimodule.core.hapi.ADTProcessor;
import hapimodule.core.hapi.ORUProcessor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Teddy Odhiambo
 */
public class Hl7Dump {
    
    private static final Logger LOGGER = Logger.getLogger(Hl7Dump.class.getName());
    
    private static String createHl7Dump(String hl7, File dumpDir, String msgType) throws IOException {
        Random random = new Random();

        File hl7File = new File(dumpDir, (random.nextInt(Integer.MAX_VALUE) + 1) + "-" + msgType + ".hl7");

        try (FileOutputStream fos = new FileOutputStream(hl7File)) {
            byte[] b = hl7.getBytes();
            fos.write(b);
            fos.flush();
        }
        
        return hl7File.getCanonicalPath();
    }
    
    public static void dumpORU(ORUProcessor oruProcessor, String logPrefix, File dumpDir){
            String hl7 = oruProcessor.generateORU();
            String triggerEvent = "ORU";
            try {
                String fileName = createHl7Dump(hl7, dumpDir, triggerEvent);
                LOGGER.log(Level.INFO, "{0} {1} was successfully created on disk, as below:\n {2}",
                        new Object[]{logPrefix, fileName, hl7});
            } catch (IOException ex) {
                StringBuilder sb = new StringBuilder();
                sb.append(logPrefix)
                        .append(" The following error occurred while creating the message:\n");
                LOGGER.log(Level.SEVERE, sb.toString(), ex);
            }
    }
    
    public static void dumpADT(ADTProcessor adtProcessor, String logPrefix, File dumpDir, String triggerEvent){
        String hl7 = adtProcessor.generateADT(triggerEvent);
        try {
            String fileName = createHl7Dump(hl7, dumpDir, triggerEvent);
            LOGGER.log(Level.INFO, "{0} {1} was successfully created on disk, as below:\n {2}",
                    new Object[]{logPrefix, fileName, hl7});
        } catch (IOException ex) {
            StringBuilder sb = new StringBuilder();
            sb.append(logPrefix)
                    .append(" The following error occurred while creating the message:\n");
            LOGGER.log(Level.SEVERE, sb.toString(), ex);
        }
    }
}
