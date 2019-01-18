package edu.montana.cerg.tempsignal.heat;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.neosimulation.neo.framework.NEOException;
import org.neosimulation.neo.user.NEORuntime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TempSignalModel extends NEORuntime {

    public TempSignalModel(String[] args) throws NEOException
    {
        super(args);
    }
    
    public static void main(String[] args)
    {    	    
        String workingDir = System.getProperty("user.dir");
        Document simEnv = null;
        try
        {
            simEnv = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                    new File(System.getProperty("user.dir") + File.separator + "configs" +
                    File.separator + "config.xml"));
        }
        catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(-1);
        }
        
        // Delete old logs if requested in environment configuration
        Element runconfig = (Element)simEnv.getElementsByTagName("runconfig").item(0); 
        File logDir = new File(workingDir + File.separator + "logs");
        if (logDir.exists())
        {
            if (Boolean.valueOf(runconfig.getAttribute("overwritelogs")))
            {
                for (File file: logDir.listFiles())
                {
                    file.delete();
                }
            }
        }
        else
        {
            logDir.mkdir();
        }
        
        try
        {
            new TempSignalModel(args);
        }
        catch(NEOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
}
