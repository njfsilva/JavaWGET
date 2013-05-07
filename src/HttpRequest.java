import java.net.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

public class HttpRequest {

    static HttpHeader header = new HttpHeader();
    static String host;
    static int port;

    HttpRequest(String hostURL, int port) {
        HttpRequest.host = hostURL;
        HttpRequest.port = port;
    }

    public static StringBuffer htmlFile(String filename) {


        BufferedReader input;
        PrintWriter output;
        StringBuffer text;
        Socket socket;


        try {
            socket = new Socket(host, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream());
            text = new StringBuffer();

            output.print("GET " + filename + " HTTP/1.0\n\n");
            output.flush();
            header.getHeader(input);
            
            for (String l = null; (l = input.readLine()) != null;) 
                text.append(l+"\n");
            socket.close();
            
            return text;

        } catch (Exception e) {
            if (e instanceof UnknownHostException) {
                System.err.println("Unable to transfer data!");
            }
            if (e instanceof IOException) {
                System.err.println("Cannot connect to " + host);
            }
            return null;
        }

    }

    /**
     *
     * @param fileToDownload name of the file to download.
     * @param localFolder local folder to store the file.
     * @param localBaseFolder local base folder eg: ".".
     * 
     */
    public static void binaryFile(String fileToDownload, String localfolder, String localbasefolder) {

        InputStream raw;
        BufferedReader input;
        PrintWriter output;
        Socket socket;

        try {
            socket = new Socket(host, port);
            raw = socket.getInputStream();
            input = new BufferedReader(new InputStreamReader(raw));
            output = new PrintWriter(socket.getOutputStream());
 
            output.print("GET " + fileToDownload + " HTTP/1.0\n\n");
            output.flush();

            header.getHeader(input);
            socket.close();

            File newLocalFile;
            if (fileToDownload.startsWith(localfolder)) {
                newLocalFile = new File(localbasefolder + "/", fileToDownload);
            } else {
                newLocalFile = new File(localbasefolder + "/" + localfolder, fileToDownload);
            }

            URL url = new URL("http", host, port, fileToDownload);
            try {
                    Files.createDirectories((newLocalFile.getParentFile()).toPath());
                } catch (FileAlreadyExistsException ex) {
                }
            byte[] buffer = new byte[1024 * 16];
            try {
                InputStream is = url.openStream();
                FileOutputStream fos = new FileOutputStream(newLocalFile);
                while (true) {
                    int len = is.read(buffer);
                    if (len < 0) {
                        break;
                    }
                    fos.write(buffer, 0, len);
                }
                fos.close();
                is.close();
            } catch (IOException e) {

                System.out.println("Cannot create: " + newLocalFile + " "+e);
            }
        } catch (Exception e) {
            if (e instanceof UnknownHostException) {
                System.err.println("Data error!");
            }
            if (e instanceof IOException) {
                System.err.println("Cannot connect to " + host);
            }
        }

    }
}