package ru.dzen.friends;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends Activity {
    private static final int ACCOUNT_PICK_CODE = 1;
    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;

    private Button vkLogin;
    private Button googleLogin;//22:13:2F:26:DA:60:60:11:7C:9C:88:86:E3:4C:07:B8:4E:47:2D:FD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        /*if (VKSdk.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/

        setupUI();
    }

    private void setupUI() {
        vkLogin = (Button) findViewById(R.id.login_vk_button);
        googleLogin = (Button) findViewById(R.id.login_google_button);

        vkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(LoginActivity.this, sMyScope);
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},
                        false, null, null, null, null);
                Log.d("myLog", intent.getAction());
                startActivityForResult(intent, ACCOUNT_PICK_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCOUNT_PICK_CODE && resultCode == RESULT_OK) {
            final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            /*AsyncTask<Void, Void, String> getToken = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    String token = null;
                    try {
                         token = GoogleAuthUtil.getToken(LoginActivity.this, accountName,
                                SCOPES);
                        return token;

                    } catch (UserRecoverableAuthException userAuthEx) {
                        startActivityForResult(userAuthEx.getIntent(), 123);
                    }  catch (IOException ioEx) {
                        Log.d("myLog", "IOException");
                    }  catch (GoogleAuthException fatalAuthEx)  {
                        Log.d("myLog", "Fatal Authorization Exception" + fatalAuthEx.getLocalizedMessage());
                    }

                    return token;
                }

                @Override
                protected void onPostExecute(String token) {
                    Log.d("myLog", token);
                }

            };
            getToken.execute(null, null, null);
        }*/
            Log.d("myLog", accountName);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d("myLog", "ok");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                //res.userId = id VK
            }

            @Override
            public void onError(VKError error) {
                Log.d("myLog", "neOK");
            }
        })) ;

    }

}
