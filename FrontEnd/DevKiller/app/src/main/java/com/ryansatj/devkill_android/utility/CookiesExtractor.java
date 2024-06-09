package com.ryansatj.devkill_android.utility;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ryansatj.devkill_android.model.User;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashSet;
import java.util.Set;

public class CookiesExtractor {

    public boolean cookiesExist(SharedPreferences sharedPreferences) {
        Set<String> cookies = sharedPreferences.getStringSet("cookies", new HashSet<>());
        return !cookies.isEmpty();
    }

    public void deleteCookies(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("cookies");
        editor.apply();
    }

    public User extractUserFromCookies(SharedPreferences sharedPreferences){
        String email = extractEmailFromCookies(sharedPreferences);
        Integer _id = Integer.parseInt(extract_idFromCookies(sharedPreferences));
        String name = extractNameFromCookies(sharedPreferences);
        String username = extractUsernameFromCookies(sharedPreferences);
        Integer id = Integer.parseInt(extractIdFromCookies(sharedPreferences));

        return new User(id, _id, name, email, username);
    }
    public String extractEmailFromCookies(SharedPreferences sharedPreferences) {
        Set<String> cookies = sharedPreferences.getStringSet("cookies", new HashSet<>());
        for (String cookie : cookies) {
            if (cookie.startsWith("usersession")) {
                try {
                    String decodedCookie = java.net.URLDecoder.decode(cookie, "UTF-8");
                    int startIndex = decodedCookie.indexOf("\"email\":\"") + "\"email\":\"".length();
                    int endIndex = decodedCookie.indexOf("\"", startIndex);
                    return decodedCookie.substring(startIndex, endIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "Email Not Found";
    }

    public String extractUsernameFromCookies(SharedPreferences sharedPreferences) {
        Set<String> cookies = sharedPreferences.getStringSet("cookies", new HashSet<>());
        for (String cookie : cookies) {
            if (cookie.startsWith("usersession")) {
                try {
                    String decodedCookie = java.net.URLDecoder.decode(cookie, "UTF-8");
                    int startIndex = decodedCookie.indexOf("\"username\":\"") + "\"username\":\"".length();
                    int endIndex = decodedCookie.indexOf("\"", startIndex);
                    return decodedCookie.substring(startIndex, endIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "Username Not Found";
    }

    public String extractNameFromCookies(SharedPreferences sharedPreferences) {
        Set<String> cookies = sharedPreferences.getStringSet("cookies", new HashSet<>());
        for (String cookie : cookies) {
            if (cookie.startsWith("usersession")) {
                try {
                    String decodedCookie = java.net.URLDecoder.decode(cookie, "UTF-8");
                    int startIndex = decodedCookie.indexOf("\"name\":\"") + "\"name\":\"".length();
                    int endIndex = decodedCookie.indexOf("\"", startIndex);
                    return decodedCookie.substring(startIndex, endIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "Name Not Found";
    }

    public String extract_idFromCookies(SharedPreferences sharedPreferences) {
        Set<String> cookies = sharedPreferences.getStringSet("cookies", new HashSet<>());
        for (String cookie : cookies) {
            if (cookie.startsWith("usersession")) {
                try {
                    String decodedCookie = java.net.URLDecoder.decode(cookie, "UTF-8");
                    int outerStartIndex = decodedCookie.indexOf("\"_id\":{") + "\"_id\":{".length();
                    int startIndex = decodedCookie.indexOf("\"_id\":", outerStartIndex) + "\"_id\":".length();
                    int endIndex = decodedCookie.indexOf(",", startIndex);
                    if (endIndex == -1) {
                        endIndex = decodedCookie.indexOf("}", startIndex);
                    }
                    return decodedCookie.substring(startIndex, endIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public String extractIdFromCookies(SharedPreferences sharedPreferences) {
        Set<String> cookies = sharedPreferences.getStringSet("cookies", new HashSet<>());
        for (String cookie : cookies) {
            if (cookie.startsWith("usersession")) {
                try {
                    String decodedCookie = java.net.URLDecoder.decode(cookie, "UTF-8");
                    int startIndex = decodedCookie.indexOf("\"id\":") + "\"id\":".length();
                    int endIndex = decodedCookie.indexOf(",", startIndex);
                    if (endIndex == -1) {
                        endIndex = decodedCookie.indexOf("}", startIndex);
                    }
                    return decodedCookie.substring(startIndex, endIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "Not Found";
    }

}
