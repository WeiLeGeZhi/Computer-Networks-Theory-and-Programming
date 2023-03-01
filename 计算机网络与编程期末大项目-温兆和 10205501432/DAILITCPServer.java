package WEIZHI;
import java.io.*;
import java.net.*;

interface Responsemachine
{
    String response(String message);
}
//初始服务器，接受代理服务器传来的消息并做出回应
class BEIDALILDEClientHandler implements Responsemachine
{
    private String str1 = "/index.html";
    private String str3 = "/shutdown";
    private String str2 = null;
    @Override
    public String response(String message) {
        if(!message.equals(str3))
        {
            if(message.equals(str1))
            {
                str2="HTTP/1.1 200\nContent-Type: text/html\n\n<h1> ID Number: 10205501432 </h1>";
            }
            else
            {
                str2="HTTP/1.1 404\nContent-Type: text/html\n\n<h1> Sorry, 404 not found </h1>";
            }
        }
        return str2;
    }

}
//代理服务器，对客户端传来的报文进行解析并传给初始服务器处理
class ClientHandler extends Thread implements Responsemachine{
    private final Socket socket;
    private Responsemachine target;
    ClientHandler(Socket socket,Responsemachine target) {
        this.socket = socket;
        this.target = target;
    }
    @Override
    public void run()
    {
        super.run();
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            String info = Request(inputStream);
            String ans = this.target.response(info);
            if(!ans.equals(null))
            {
                outputStream.write(ans.getBytes());
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @Override
    public String response(String message) {
        return this.target.response(message);
    }
    public static String Request(InputStream inputStream)throws IOException
    {
        String[] requestLine =  new BufferedReader(new InputStreamReader(inputStream)).readLine().split(" ");
        System.out.println(requestLine[0]+"and"+requestLine[1]+"and"+requestLine[2]);
        return requestLine[1];
    }
}

public class DAILITCPServer {
    private static final int PORT = 8081;
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        server.setReuseAddress(true);
        server.setReceiveBufferSize(64 * 1024 * 1024);
        server.bind(new InetSocketAddress("127.0.0.1", PORT), 50);
        int clietNum = 0;
        while(true)
        {
            System.out.println("Blocked and waiting for the client connect...");
            Socket client = server.accept();
            BEIDALILDEClientHandler rm = new BEIDALILDEClientHandler();
            clietNum++;
            ClientHandler clientHandler = new ClientHandler(client,rm);
            clientHandler.start();
        }}
        }