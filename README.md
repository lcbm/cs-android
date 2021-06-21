# 📱 Mobile Development with Android Elective Repo

## 👩‍💻 Workspaces

### `/recycler_view_list`

This folder contains the application for **parts 1 and 2** of the **first assignment**.

#### Part 1

https://user-images.githubusercontent.com/31375047/115412593-d7262880-a1ca-11eb-963c-245247b4d54e.mp4

- Create an Android project from scratch, without relying on any branch presented in class;

- **Main activity**: present a list of items with photo, name and a short description of the item (the list must be created using **RecyclerView**);

- **Main activity**: there must be a FloatingButton, with the "+" symbol. By clicking on this button, a **SecondActivity** [for result](https://stackoverflow.com/questions/10407159) must be opened.

- **SecondActivity**: must contain an **EditText** for the name of the item, an **EditText (Multiline)** for the description, and an **Add Image button**, which must send an implicit intent to acquire the gallery image.

- **SecondActivity**: when clicking on finish, she must send the **Parcelable** object back to the **Main Activity** and then add it to the list;

- When clicking on the image of the item, you should open another activity, **DetailsActivity** which shows details of the item (name, photo, description);

- **DetailsActivity**: must contain a **Remove** button, which when clicked, removes the current item from the list and returns to **MainActivity**;

- If the cell phone is rotated, the listing **should not** be lost.

#### Part 2

https://user-images.githubusercontent.com/31375047/116270065-26350600-a755-11eb-87de-1e7ef35a43a2.mp4

- Create a customized **ActionBar** for all activities;

- In the **DetailsActivity**, the **ActionBar** `title` must correspond to the `item name`. Also, an [Up Action](https://developer.android.com/training/appbar/up-action) should be present in the **Action Bar**;

- In the **SecondActivity**, the **ActionBar** `title` must be `Add Item`. Also, an [Up Action](https://developer.android.com/training/appbar/up-action) should be present in the **Action Bar**;

- In the **DetailsActivity**, the **ActionBar** `title` must be `Item List`. Also, the **ActionBar** must be hided whenever the user scrolls the list;

- In the **DetailsActivity**, the **ActionBar** must have a `filter` action which, when clicked, a [AlertDialog](https://developer.android.com/guide/topics/ui/dialogs) must be shown, with the following options:

  - Option to show/hide items with the same `name`;
  - Option to sort items by insertion order;
  - Option to sort items by alphabetical order;
  - Button `Ok` to execute the selected filters.

### `/storage`

This folder contains the application for the **second assignment**.

https://user-images.githubusercontent.com/31375047/117996620-ae500980-b318-11eb-87ce-3bae02ab310f.mp4

- **Main activity**: present a list of items with photo, name and a short description of the item (the list must be created using **RecyclerView**);

- **RecyclerView**: must have the item name, storage type and a remove image that, when clicked, will remove the item from the list;

- **Main activity**: there must be a FloatingButton, with the "+" symbol. By clicking on this button, a **SecondActivity** [for result](https://stackoverflow.com/questions/10407159) must be opened.

- In the **Second Activity**, allow the user to inform the file with name and content, as well as choose the storage type (internal or external) via  Radio Buttons in a Radio Group. Finally, there should also be a checkbox with the option to encrypt the file with [Jetpack](https://developer.android.com/jetpack/androidx/releases/security);

- When clicking on the image of the item, you should open another activity, **DetailsActivity** which shows details of the item (file name, storage, content, encryption);

### `/shared_storage`

This folder contains the application for the **third assignment**.

https://user-images.githubusercontent.com/31375047/119281344-c7f02b80-bc0b-11eb-9bdd-01f7234e0806.mp4

- **Main activity**: show all files in the `MediaStore.Images` collection, from the shared storage;

- **Main activity**: files must be presented in a **RecyclerView** with **2** columns;

- **Main activity**: each item in the recycler view should have a `TextView` with `black` background and its text to the value of `MediaStore.Images.Media.DISPLAY_NAME`;

- **Main activity**: make sure to prompt the the user for `READ_EXTERNAL_STORAGE` permission.

### `/shared_preferences`

This folder contains the application for the **fourth assignment**.

https://user-images.githubusercontent.com/31375047/119504404-5070e800-bd42-11eb-86c1-df1e30f2ec24.mp4

- **Main activity**: must have a **text view** for the user to inform their `name` (String);

- **Main activity**: must have a **text view** for the user to inform their `age` (Int);

- **Main activity**: must have a **button** `ESCREVER` that saves the `name` and `age`, informed by the user, in a file, using [shared preferences](https://developer.android.com/training/data-storage/shared-preferences);

- **Main activity**: must have a **button** `READ` that reads the `name` and `age` saved in a file, using [shared preferences](https://developer.android.com/training/data-storage/shared-preferences), in the format:
  - `O seu nome é $name e sua idade é $age.`

### `/weather_app`

This folder contains the application for the **fifth assignment**.

- Create a **Bottom Navigation View**:
  - Add [Material](https://material.io/components/bottom-navigation/android#using-bottom-navigation) dependency;
  - Create os **Bottom Navigation** items: `Search`, `Favorite` and `Settings`;
  - Add **BottomNavigationView** to the layout;
  - Listen to the item clicks;
  - Add 3 fragments, one for each **Bottom Navigation** items.

- In the **Settings** fragment:
  - Add a **Radio Groups** which allows the user to select the `temperature unit` (`celsius` or `fahrenheit`);
  - Add a **Radio Group** which allows the user to select the `description language` (`portuguese` or `english`);
  - Add a `SAVE` button to persist data in **SharedPreferences**.

- In the **Search** fragment:
  - Add a **EditText** to allow the user to enter a `city` name;
  - Add a `SEARCH` **Button**;
  - Implement **button** click event;
  - Check internet connection: in case the device is not connected to the internet, show a **Toast** informing the user that the device is `offline` -- otherwise, `online`.

- In the **Search** fragment, the `SEARCH` button must make a `find` request to [OpenWeatherMap Current Weather API](https://openweathermap.org/current):
  - Create an interface for the [OpenWeatherMap Current Weather API](https://openweathermap.org/current);
  - Create a class to map the response `JSON` data, from the [OpenWeatherMap Current Weather API](https://openweathermap.org/current);
  - Create a **RetrofitManager** class to obtaina an Retrofit instance;
  - Make request asynchronously and show a **ProgressBar** when necessary.

- In the **Search** fragment, add a **RecyclerView** to show the request results:
  - Add **RecyclerView** to the layout;
  - Create the item layout;
  - Create and Adapter;
  - Create a `RecyclerView.ItemDecoration` (adds margin to the **RecyclerView**);
  - Download icons for the weather, using [Glide](https://github.com/bumptech/glide).

## 📝 License

Copyright © 2020-present, [CS Android Contributors](https://github.com/lcbm/cs-android/graphs/contributors). This project is [ISC](LICENSE) licensed.
