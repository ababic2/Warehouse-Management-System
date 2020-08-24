//package ba.unsa.etf.rpr;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class TimeServer {
//    private ServerSocket serverSocket;
//    private int port = 8080;
//    public TimeServer() throws Exception {
//        initializeSocket();
//
//    }
//
//    private void initializeSocket() throws Exception {
//        try {
//            serverSocket = new ServerSocket(port);
//            while(true) {
//                Socket konekcija = serverSocket.accept();
//                InputStream ulaz = konekcija.getInputStream();
//                OutputStream izlaz = konekcija.getOutputStream();
//            }
//        } catch (IOException e) {
//            this.port = this.port + 1;
//            if(port > 10000){
//                throw new Exception();
//            }
//            initializeSocket();
//        }
//    }
//}
