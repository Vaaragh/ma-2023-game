package game.slagalica;

import android.util.Log;

import io.socket.client.Socket;
import io.socket.client.IO;

public class SocketHandler {

    static Socket socket;

    public static void setSocket(){
        try{
            socket = IO.socket("http//192.168.0.10:3000");
        }catch (Exception e){
            Log.d("Socket Error", e.getMessage().toString());
        }
    }

    public static Socket getSocket(){
        return socket;
    }


}
