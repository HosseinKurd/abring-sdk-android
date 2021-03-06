# Abring android SDK
Abring as a MBAAS (Mobile Backend As A Service) is a service provider that helps developers to have an unbreakable connection with clients during the mobile app.
</br>
This SDK is a middleware between mobile apps(clients) and Abring webservice.
</br></br>
This SDK consists of two sections:
1. `With Abring UI:`
    This part contains a dialog which you can select your desired fields with a boolean.
2. `Without Abring UI:`
    This part does not contain dialog and any UI elements, And it only supports the functionality.

[![NPM version](https://img.shields.io/badge/Download-0.5-brightgreen.svg)](https://github.com/msddev/abring-sdk-android)
![NPM version](https://img.shields.io/badge/min%20sdk-17-red.svg)

# Preview
![img 1](http://s8.picofile.com/file/8312681284/111.png) 
![img 1](http://s9.picofile.com/file/8312681300/222.png) 

# Requirements
- IDE : android studio
- Minimum SDK : 17

# Installation

**Add permissions in your `AndroidManifest.xml`**
```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/> <!-- optional-read avatar from gallery-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><!-- optional-download update file-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/> <!-- optional-get device id for guest login-->
```

**Adding Dependency**
</br>
Add this to `build.gradle` Project level
</br>
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
Add this to `build.gradle` Module:app
</br>
```gradle
dependencies {
  compile 'com.github.msddev:abring-sdk-android:v0.5'
}

```
Add this to `Application` class:
</br>
```java
new Abring.Builder(this)
                .setPackageName("Your abring package name")
                .build();
```
# Abring services

**User service (Auth)**

**1. Register**
> Register a user with a username, password required and name, phone, email, avatar are optional.

The avatar is from the File Object type.

- Whitout abring UI :
```java
AbringRegister abringUser = new AbringRegister
        .RegisterBuilder()
        .setUsername("string required")
        .setPassword("string required")
        .setName("string optional")
        .setPhone("string optional")
        .setEmail("string optional")
        .setAvatar(File file optional)
        .build();

abringUser.register(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        AbringRegisterModel register = (AbringRegisterModel) response;
        Toast.makeText(activity, "اطلاعات با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
    }
                                                                                                                  
    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```

- Whit abring UI :
```java
AbringRegister abringUser = new AbringRegister
        .DialogBuilder()
        .setName(true/false)
        .setPhone(true/false)
        .setEmail(true/false)
        .setAvatar(true/false)
        .build();
        
abringUser.showDialog(getSupportFragmentManager(), activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        AbringRegisterModel register = (AbringRegisterModel) response;
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
    }
});
```
**2. Mobile register**
> Register a user with a mobile number with this pattern `09123456789` required and name, phone, email, avatar are optional.

The avatar is from the File Object type.

- Whitout abring UI :
```java
AbringMobileRegister abringUser = new AbringMobileRegister
        .MobileRegisterBuilder()
        .setMobile("string required")
        .setUsername("string optional")
        .setPassword("string optional")
        .setName("string optional")
        .setDeviceId("string optional")
        .setAvatar(File file optional)
        .build();

abringUser.mobileRegister(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity, "کد فعالسازی ارسال شد...", Toast.LENGTH_LONG).show();
        isActive = true;
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```

- Whit abring UI :
```java
AbringMobileRegister abringUser = new AbringMobileRegister
        .DialogBuilder()
        .setUsername(true/false)
        .setPassword(true/false)
        .setName(true/false)
        .setDeviceId(true/false)
        .setAvatar(true/false)
        .build();

abringUser.showDialog(getSupportFragmentManager(), activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        AbringRegisterModel register = (AbringRegisterModel) response;
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```

and use this to send **activation code** in `Whitout UI` mode only:

**3. Mobile verify**
> Send activation code after user registration with a mobile number.

```java
AbringMobileRegister.mobileVerify("Your activation code", new AbringCallBack<Object, Object>() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```

and use this to resend **activation code** in `Whitout UI` mode only:

**4. Retry active code**
```
AbringMobileRegister.mobileResendCode(new AbringCallBack<Object, Object>() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity, "منتظر دریافت کد جدید باشید", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```

**5. Login**
> Login registered user in the previous API.

- Whitout abring UI :
```java
final AbringLogin abringUser = new AbringLogin
        .LoginBuilder()
        .setUsername("string required")
        .setPassword("string required")
        .build();

abringUser.login(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity, "ورود با موفقیت انجام شد", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```

- Whit abring UI :
```java
AbringLogin.showDialog(getSupportFragmentManager(), activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        AbringRegisterModel register = (AbringRegisterModel) response;
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```
**6. Login as guest**
> Login with device id and generate token.

```java
AbringLogin.loginAsGuest(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" :
                        apiError.getMessage(), Toast.LENGTH_SHORT).show();
    }
});
```

**7. Logout**
> Logout current user.

- Whitout abring UI :
```java
AbringLogout.logout(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity, "خروج کاربری با موفقیت انجام شد", Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```

- Whit abring UI :
```java
AbringLogout.showDialog(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {

    }

    @Override
    public void onFailure(Object response) {

    }
});
```

**6. Logout all**
> Logout all users.

- Whitout abring UI :
```java
AbringLogout.logoutAll(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity, "خروج کاربری با موفقیت انجام شد", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? "متاسفانه خطایی رخ داده است" : apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```

- Whit abring UI :
```java
AbringLogout.showDialogLogoutAll(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {

    }

    @Override
    public void onFailure(Object response) {

    }
});
```

**7. Check update**
> Check the application update in the `abring system`

- Whitout abring UI :
```java
AbringCheckUpdate.check(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        AbringCheckUpdateModel mUpdateApp = (AbringCheckUpdateModel) response;
        Toast.makeText(activity,
                "عملیات با موفقیت انجام شد",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                AbringCheck.isEmpty(apiError.getMessage()) ? 
                        "متاسفانه خطایی رخ داده است" : 
                        apiError.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
});
```


- Whit abring UI :
```java
AbringCheckUpdate.showDialog(getSupportFragmentManager(), activity,
        new AbringCallBack() {
            @Override
            public void onSuccessful(Object response) {
                AbringCheckUpdateModel mUpdateApp = (AbringCheckUpdateModel) response;
                Toast.makeText(activity,
                        "عملیات با موفقیت انجام شد",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Object response) {
                AbringApiError apiError = (AbringApiError) response;
                Toast.makeText(activity,
                        AbringCheck.isEmpty(apiError.getMessage()) ? 
                                "متاسفانه خطایی رخ داده است" : 
                                apiError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
```

**8. Dynamic Request**
> create a request in abring system with dynamic parameters and custom url and get json responce.
Convert json responce to class with Gson convertor.

This section has two methods : `GET` and `POST`.

> in `GET` method :
1. set `String` parameters into `HashMap` collection.

```java
Map<String, String> params = new HashMap<>();
```
2. set values into builder pattern : parameters and custom url.

- GET request :
```java
URL = "app/check-update"; //send url after ?r= to first &

Map<String, String> params = new HashMap<>();
params.put("variable", "update");

AbringDynamicRequest mAbring = new AbringDynamicRequest
        .DynamicRequestGetBuilder()
        .setUrl(URL)
        .setStringParamsGet(params)
        .build();
        
mAbring.request(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity,
                "عملیات با موفقیت انجام شد",
                Toast.LENGTH_SHORT).show();
                
        if (response != null) {
            Gson gson = new Gson();
            AbringCheckUpdateModel m = gson.fromJson(
                    response.toString(),
                    AbringCheckUpdateModel.class);
        }
    }
    
    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
                Toast.makeText(activity,
                        AbringCheck.isEmpty(apiError.getMessage()) ? 
                                "متاسفانه خطایی رخ داده است" : 
                                apiError.getMessage(),
                        Toast.LENGTH_SHORT).show();
    }
});
```

>in `POST` method :
This method consists of two parts `String` request and `String-Image` request.

for `String` request:
1. set `RequestBody` parameters into `HashMap` collection.

```java
Map<String, RequestBody> params = new HashMap<>();
```
2. set values into builder pattern : parameters and custom url.

- POST request :
```java
URL = "player/register"; //send url after ?r= to first &

Map<String, RequestBody> params = new HashMap<>();
params.put("username", AbringUtils.toRequestBody("Test"));
params.put("password", AbringUtils.toRequestBody("123456"));

AbringDynamicRequest mAbring = new AbringDynamicRequest
        .DynamicRequestPostBuilder()
        .setUrl(URL)
        .setRequestBodyParamsPost(params)
        .build();
        
mAbring.request(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity,
                "عملیات با موفقیت انجام شد",
                Toast.LENGTH_SHORT).show();
                
        if (response != null) {
            Gson gson = new Gson();
            AbringRegisterModel m = gson.fromJson(
                    response.toString(),
                    AbringRegisterModel.class);
        }
    }
    
    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                        AbringCheck.isEmpty(apiError.getMessage()) ? 
                                "متاسفانه خطایی رخ داده است" : 
                                apiError.getMessage(),
                        Toast.LENGTH_SHORT).show();
    }
});
```

for `String-Image` request:
1. set `RequestBody` parameters into `HashMap` for `String` request.
2. set `MultipartBody.Part` parameters into `ArrayList` for `Image` request.

```java
Map<String, RequestBody> params = new HashMap<>();
List<MultipartBody.Part> imageParams = new ArrayList<>();
```
3. set values into builder pattern : parameters and custom url.

- POST request :
```java

URL = "player/register"; //send url after ?r= to first &

Map<String, RequestBody> params = new HashMap<>();
params.put("username", AbringUtils.toRequestBody("Test"));
params.put("password", AbringUtils.toRequestBody("123456"));

List<MultipartBody.Part> imageParams = new ArrayList<>();
imageParams.add(AbringUtils.toMultipartBody("avatar", file));

AbringDynamicRequest mAbring = new AbringDynamicRequest
        .DynamicRequestPostBuilder()
        .setUrl(URL)
        .setRequestBodyParamsPost(params)
        .setImageParamsPost(imageParams)
        .build();
        
mAbring.request(activity, new AbringCallBack() {
    @Override
    public void onSuccessful(Object response) {
        Toast.makeText(activity,
                "عملیات با موفقیت انجام شد",
                Toast.LENGTH_SHORT).show();
                
        if (response != null) {
            Gson gson = new Gson();
            AbringRegisterModel m = gson.fromJson(
                    response.toString(),
                    AbringRegisterModel.class);
        }
    }
    
    @Override
    public void onFailure(Object response) {
        AbringApiError apiError = (AbringApiError) response;
        Toast.makeText(activity,
                        AbringCheck.isEmpty(apiError.getMessage()) ? 
                                "متاسفانه خطایی رخ داده است" : 
                                apiError.getMessage(),
                        Toast.LENGTH_SHORT).show();
    }
});
```

**9. Public method**

1. Get user data :

```java
AbringServices.getUser();
```

2. Get token :

```java
AbringServices.getToken();
```

3. Set user data :

```java
AbringServices.setUser();
```
