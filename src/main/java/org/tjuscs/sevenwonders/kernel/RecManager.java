package org.tjuscs.sevenwonders.kernel;

import com.thoughtworks.xstream.XStream;
import org.tjuscs.sevenwonders.LocalMessages;
import org.tjuscs.sevenwonders.Manager;

import java.io.*;
import java.util.Date;

public class RecManager {
    private static XStream xStream;
    private File file;
    private Reader reader;
    private FileWriter writer;
    private String TITLE = "<!-- Game Record of 7Wonders(" + LocalMessages.getString("Version").split("\\(")[0] + ") -->\n";

    public RecManager() {
        xStream = new XStream();
        xStream.alias("Game", GameInfo.class);
        xStream.alias("Age", AgeInfo.class);
        xStream.alias("Turn", TurnInfo.class);
        xStream.alias("Buy", BuyInfo.class);
        xStream.aliasField("Player", TurnInfo.class, "playerIndex");
        xStream.aliasField("Card", TurnInfo.class, "chosenCardIndex");
        xStream.aliasField("Command", TurnInfo.class, "chosenCommandType");
    }

    void rec(Object obj) {
        try {
            File dir = new File(System.getProperty("user.dir") + File.separator + "rec");
            if (!dir.exists())
                dir.mkdir();
            file = File.createTempFile("temp", ".xml", dir);
            file.deleteOnExit();
            if (file == null)
                Manager.warn("Record file create failure!");
            writer = new FileWriter(file, true);
            writer.write(TITLE);
            writer.write("<!-- Generated in " + new Date().toString() + " -->\n\n");
            xStream.toXML(obj, writer);
            writer.close();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
        } catch (Throwable e) {
            Manager.error(e);
        }
    }

    public GameInfo read(File readFile) throws FileNotFoundException, IOException {
        Manager.info("Parsing record file...");
        reader = new FileReader(readFile);
        char[] cbuf = new char[TITLE.length()];
        reader.read(cbuf, 0, TITLE.length());
        GameInfo rec = null;
        if (new String(cbuf).equals(TITLE)) {
            rec = (GameInfo) xStream.fromXML(reader);
            Manager.debug(rec);
        } else
            Manager.warn("Record file version error!");
        return rec;
    }

    public static class GameInfo {
        public String[] boardNames, playerNames;
        public int[] boardSides;
        public AgeInfo[] ages;

        @Override
        public String toString() {
            return xStream.toXML(this);
        }
    }

    public static class AgeInfo {
        public int ageNum;
        public String[][] cardNames;
        public TurnInfo[][] turns;

        @Override
        public String toString() {
            return xStream.toXML(this);
        }
    }

    public static class TurnInfo {
        public int turnNum;
        public int playerIndex;
        public int chosenCardIndex;
        public Command chosenCommandType;
        public BuyInfo chosenBuyDecision;

        @Override
        public String toString() {
            return xStream.toXML(this);
        }
    }

    public static class BuyInfo {
        @Override
        public String toString() {
            return xStream.toXML(this);
        }
    }
}
