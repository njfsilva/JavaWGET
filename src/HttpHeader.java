import java.io.BufferedReader;
import java.util.StringTokenizer;

class HttpHeader {

    int numBytes;
    String numBytesS = "";
    String transmissionDate;
    String header = "";
    int httpCode;
    String httpCodeS;

   
    public void getHeader(BufferedReader input) {

        try {
            int j = 0;
            while (j == 0) {
                header = input.readLine();
           //     System.out.println(cab);
                if (header.startsWith("HTTP")) {
                    StringTokenizer codeNumber = new StringTokenizer(header, " ");
                    codeNumber.nextElement();
                    httpCodeS = (String) codeNumber.nextElement();
                    Integer value = new Integer(httpCodeS);
                    httpCode = value.intValue();
                }
                if (header.startsWith("Content-Length: ")) {
                    int cabLength = header.length();
                    numBytesS = header.substring(16, cabLength);
                    Integer value = new Integer(numBytesS);
                    numBytes = value.intValue();
                }

                if (header.startsWith("Date: ")) {
                    int cabLength = header.length();
                    transmissionDate = header.substring(6, cabLength);
                }

                if (header.compareTo("") == 0) {
                    j = 1;
                }
            }
        } catch (Exception e) {
        }
        
       /* System.out.println("------- Some Header info ------");
        System.out.println("HTTP Code: " +httpCode);
        System.out.println("Object length: " +numBytes);
        System.out.println("Date: "+transmissionDate);
        System.out.println("--- End of Some Header info ---");
        */
    }
}