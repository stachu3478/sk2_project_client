package pl.put.poznan.sk2_project_client.game;


import pl.put.poznan.sk2_project_client.net.Client;

import java.io.*;

public class ClientConfig {

    private static java.lang.String address="51.75.76.142";
    private static int port=34780;

    public ClientConfig() {

        File file = new File("config.txt");
        if(!file.exists()){
            try(Writer writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("config.txt"),"utf-8") )){
                FileWriter fWriter = new FileWriter(file);
                PrintWriter pWriter = new PrintWriter(fWriter);
                writer.write(address);
                writer.write("\n");
                writer.write(Integer.toString(port));
            }
            catch(IOException e){

            }
        }
        else {
            StringBuilder sb = new StringBuilder();
            String strLine = null;
            String str_data[] = new String[3];
            int i = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader("config.txt"));
                do{
                    strLine = br.readLine();
                    str_data[i] = strLine;
                    i++;
                }while (strLine != null);
                br.close();
                address = str_data[0];
                port = Integer.parseInt(str_data[1]);
            }
            catch(IOException e){
            }
        }

    }

    public java.lang.String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
