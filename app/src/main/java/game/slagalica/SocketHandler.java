package game.slagalica;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.Socket;
import io.socket.client.IO;

public class SocketHandler {

    private static SocketHandler instance;
    private Socket socket;

    private SocketHandler(){
    }

    public static SocketHandler getInstance(){
        if (instance == null){
            instance = new SocketHandler();
        }
        return instance;
    }

    public void connect(){
        try {
            socket = IO.socket("http://192.168.0.15.3000");
            socket.connect();
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    public void disconnect(){
        if (socket != null){
            socket.disconnect();
            socket = null;
        }
    }

    public Socket getSocket(){
        return socket;
    }


}
