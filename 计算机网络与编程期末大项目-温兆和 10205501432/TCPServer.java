package WEIZHI;

import java.io.*;
import java.net.*;

public class TCPServer {
    private static final int PORT = 8081;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.setReceiveBufferSize(64 * 1024 * 1024);
        serverSocket.bind(new InetSocketAddress("127.0.0.1", PORT), 50);
        System.out.println("Blocked and waiting for the client connection...");
        Socket socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = socket.getOutputStream();
        String str1 = "/index.html";
        String str2 = "/shutdown";
        String info = Request(is);
        System.out.println("Received: "+info);
        if(!info.equals(str2))
        {
            if(info.equals(str1)){
                os.write(("HTTP/1.1 200\n"
                        + "Content-Type: text/html\n"
                        + "\n"
                        +"<html><head></head>"
                        + "<body><h1> ID Number: 10205501432 </h1></body></html>").getBytes());
            }
            else {
                os.write(("HTTP/1.1 404\n"
                        + "Content-Type: text/html\n"
                        + "\n"
                        + "<h1> Sorry, 404 not found </h1>").getBytes());
            }
        }
        socket.shutdownInput();
        socket.shutdownOutput();
        os.close();
        br.close();
        isr.close();
        is.close();
        socket.close();
        serverSocket.close();
    }
    public static String Request(InputStream inputStream)throws IOException
    {
        String[] requestLine =  new BufferedReader(new InputStreamReader(inputStream)).readLine().split(" ");
        System.out.println(requestLine[0]+"and"+requestLine[1]+"and"+requestLine[2]);
        return requestLine[1];
    }
}
