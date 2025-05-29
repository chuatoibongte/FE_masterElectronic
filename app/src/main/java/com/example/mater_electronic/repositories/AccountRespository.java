package com.example.mater_electronic.repositories;

import android.net.Uri;

import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.Address;
import com.example.mater_electronic.models.account.ChangePassAccountRequest;
import com.example.mater_electronic.models.account.ChangePassAccountResponse;
import com.example.mater_electronic.models.account.GetAccountResponse;
import com.example.mater_electronic.models.account.UpdateAccountResponse;
import com.example.mater_electronic.models.account.UpdateAddressRequest;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.account.AccountService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AccountRespository {
    private AccountService accountServiceApi = ApiClient.getAccountService();

    //Lấy dữ liệu Account
    public void getAccount(String accessToken, Callback<GetAccountResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        accountServiceApi.getAccount(authHeader).enqueue(callback);
    }

    //Thay đổi password Account
    public void changePasss(String accessToken, ChangePassAccountRequest request, Callback<ChangePassAccountResponse> callback){
        String authHeader = "Bearer " + accessToken;
        accountServiceApi.changePass(authHeader, request).enqueue(callback);
    }

    //Update địa chỉ mới
    public void updateAddressList(String accessToken, List<Address> addressList, Callback<UpdateAccountResponse> callback){
        String authHeader = "Bearer " + accessToken;

        //Create RequestBody
        UpdateAddressRequest request = new UpdateAddressRequest(addressList);
        accountServiceApi.updateAddressList(authHeader, request).enqueue(callback);
    }

    //Update dữ liệu Account with image
    public void updateAccount(String accessToken, Account account, Uri imageUri, android.content.Context context, Callback<UpdateAccountResponse> callback){
        String authHeader = "Bearer " + accessToken;

        // Create RequestBody for text fields
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), account.getUsername());
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), account.getName());
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), account.getPhone() != null ? account.getPhone() : "");
        RequestBody genderBody = RequestBody.create(MediaType.parse("text/plain"), account.getGender() != null ? account.getGender() : "");

        // Format birthday
        String birthdayString = "";
        if (account.getBirthday() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            birthdayString = sdf.format(account.getBirthday());
        }
        RequestBody birthdayBody = RequestBody.create(MediaType.parse("text/plain"), birthdayString);

        // Handle image if provided
        if (imageUri != null) {
            try {
                File imageFile = com.example.mater_electronic.utils.FileUtils.getFileFromUri(context, imageUri);
                if (imageFile != null && imageFile.exists()) {
                    RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                    MultipartBody.Part imagePart = MultipartBody.Part.createFormData("avatar", imageFile.getName(), imageRequestBody);

                    accountServiceApi.updateAccount(authHeader, usernameBody, nameBody, phoneBody, birthdayBody, genderBody, imagePart).enqueue(callback);
                } else {
                    // If image file is null, update without image
                    accountServiceApi.updateAccountWithoutImage(authHeader, usernameBody, nameBody, phoneBody, birthdayBody, genderBody).enqueue(callback);
                }
            } catch (Exception e) {
                // If error processing image, update without image
                accountServiceApi.updateAccountWithoutImage(authHeader, usernameBody, nameBody, phoneBody, birthdayBody, genderBody).enqueue(callback);
            }
        } else {
            accountServiceApi.updateAccountWithoutImage(authHeader, usernameBody, nameBody, phoneBody, birthdayBody, genderBody).enqueue(callback);
        }
    }

    //Update dữ liệu Account without image
    public void updateAccount(String accessToken, Account account, android.content.Context context, Callback<UpdateAccountResponse> callback){
        updateAccount(accessToken, account, null, context, callback);
    }
}