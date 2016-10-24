package com.example.victor.testvk;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiFriends;
import com.vk.sdk.api.model.VKApiApplicationContent;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKUsersArray;
import com.vk.sdk.util.VKUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vk.sdk.api.VKParameters.*;

public class MainActivity extends AppCompatActivity {

    private String[] scope = new String[] {VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};
    private List<Integer> list_ID = new ArrayList<Integer>();
    private List<Integer> mutual_ID = new ArrayList<Integer>();
    private ListView listView;
    private EditText txt;
    private VKList<VKApiDialog> tre;
    private int id_Group = 1488;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (EditText) findViewById(R.id.txtBox);
    }
        // Авторизация
    public void btnVKclick(View view) {
        VKSdk.login(this, scope);
        //txt.setText("Helo12");
    }
        // Отправка Сообщений всем
    public void clickBtnSendMes(View view) {
        //for (int id: list_ID) {
        //    sendMsg(id, "message");
        //}
        sendMsg(13709788, txt.getText().toString());
        //sendMsg(7699715);
    }

    private void sendMsg(int id, String txt) {
        VKRequest request1 = new VKRequest("messages.send", VKParameters.from(
                VKApiConst.USER_ID, id,
                VKApiConst.MESSAGE, txt));

        request1.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
            }
        });
    }
        // Пост на стенах друзей
    public void clickWallPost(View view) {
        for (int id: list_ID) {
            wallPost(id);
        }
    }

    private void wallPost(int oid) {
        VKRequest request = VKApi.wall().post(VKParameters.from(
                VKApiConst.OWNER_ID, oid,
                VKApiConst.MESSAGE, "TEST POST"));
    }
        // Добавлние в группу всех друзей
    public void clickGoGroup(View view) {
        for (int id: list_ID) {
            inviteGroup(id_Group, id);
        }
    }

    private void inviteGroup(int id_Group, int id_user) {
        VKRequest request = VKApi.groups().getInvites(VKParameters.from(
                VKApiConst.GROUP_ID, id_Group,
                VKApiConst.USER_ID, id_user));
    }
        // Добавление Возможных друзей
    public void clickAddFriend(View view) {
        getMutual();
        //for (int mid: mutual_ID) {
        //    addFriend(mid);
        //}
        System.out.print(mutual_ID);
    }

    public void getMutual() {
        VKRequest request = VKApi.friends().getMutual(VKParameters.from(
                VKApiConst.COUNT, 10));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKUsersArray ty6 = (VKUsersArray) response.parsedModel;
                System.out.print(response.responseString);

                int i = 0;
                for (VKApiUserFull b: ty6
                        ) {
                    System.out.println(b.id);
                    mutual_ID.add(b.id);
                    i++;
                }
            }
        });
    }

    private void  addFriend(int mid) {
        VKRequest request = VKApi.friends().add(VKParameters.from(
                VKApiConst.USER_ID, mid));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                listView = (ListView) findViewById(R.id.listVKfriends);

                //VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.USER_ID, null));
                VKRequest request = VKApi.friends().get(VKParameters.from(
                        VKApiConst.FIELDS, "uid"));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        //VKList list = response.parsedModel;
                        //VKUsersArray list1 = (VKUsersArray) response.parsedModel;
                        //VKList<VKApiFriends> = () response.parsedModel;
                        //for (int i = 0; i < response.le)

                        //VKApiFriends ftre34 = (VKApiFriends) response.parsedModel;
                        //ftre34.get(VKParameters.)

                        /// ЕБАННЫЙ КАСТЫЛЬ!
                        VKUsersArray ty6 = (VKUsersArray) response.parsedModel;

                        VKList list = (VKList) response.parsedModel;
                        String[] idte = new String[ty6.getCount()];
                        int i = 0;
                        for (VKApiUserFull b: ty6
                             ) {
                            System.out.println(b.id);
                            list_ID.add(b.id);
                            idte[i] = String.format("%d",b.id);
                            i++;
                        }
                        //System.out.print(list);
                        //ArrayAdapter<Integer>

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_expandable_list_item_1, idte);

                        listView.setAdapter(arrayAdapter);
                    }
                });

                Toast.makeText(getApplicationContext(), "GOOD", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
