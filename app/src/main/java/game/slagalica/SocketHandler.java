package game.slagalica;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

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
            Log.d("Start", "connection");
            socket = IO.socket("http://192.168.0.10:3002");
            socket.on(Socket.EVENT_CONNECT, args -> {
                socket.emit("ping", "pong");
                Log.d("Sent", "ping");
                socket.on("pong", args1 -> Log.d("Received", "pong"));
            });
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
