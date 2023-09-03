package game.slagalica.userFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import game.slagalica.GameActivity;
import game.slagalica.R;
import game.slagalica.RegisterActivity;
import game.slagalica.SocketHandler;
import game.slagalica.UserHomeActivity;
import game.slagalica.model.single.User;

public class HomeFragment extends Fragment {

    private User user;

   public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View rootView = inflater.inflate(R.layout.fragment_home, container, false);
       Button startBtn = rootView.findViewById(R.id.start_btn_user);
        if (getActivity() != null && getActivity() instanceof UserHomeActivity) {
            UserHomeActivity act = (UserHomeActivity) getActivity();
            this.user = act.getUser();
        }
       startBtn.setOnClickListener(v -> {
           try {
               startGame();
           } catch (JSONException e) {
               throw new RuntimeException(e);
           }
       });


       return rootView;
    }

    private void startGame() throws JSONException {
       if (user.getTokens() == 0){
           Toast.makeText(getActivity(), "No Tokens", Toast.LENGTH_SHORT).show();

           return;
       } else {
           Toast.makeText(getActivity(), "Queued", Toast.LENGTH_SHORT).show();
           SocketHandler.getInstance().connect();
           SocketHandler.getInstance().getSocket().emit("join", genPlayer());
           SocketHandler.getInstance().getSocket().on("startGame", (val) -> {
               JSONObject matchData = (JSONObject) val[0];
               Toast.makeText(getActivity(), "Starting Game", Toast.LENGTH_SHORT).show();
               try {
                   String matchId = (String) matchData.get("id");

               } catch (JSONException e) {
                   throw new RuntimeException(e);
               }
           });
       }

    }

    private JSONObject genPlayer() throws JSONException {
        Log.d("Generated player", user.getUsername().toString() + " added to queue");
       JSONObject obj = new JSONObject();
       obj.put("id", user.getId());
       obj.put("points", 0);
       obj.put("username", user.getUsername());
       return obj;
    }
}