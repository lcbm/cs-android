# 📱 Mobile Development with Android Elective Repo

## 👩‍💻 Workspaces

### `/recycler_view_list`

#### Part 1

https://user-images.githubusercontent.com/31375047/115412593-d7262880-a1ca-11eb-963c-245247b4d54e.mp4

This folder contains the application for **part 1** of the **first assignment**:

- Create an Android project from scratch, without relying on any branch presented in class;

- **Main activity**: present a list of items with photo, name and a short description of the item (the list must be created using **RecyclerView**);

- **Main activity**: there must be a FloatingButton, with the "+" symbol. By clicking on this button, a **SecondActivity** [for result](https://stackoverflow.com/questions/10407159) must be opened.

- **SecondActivity**: must contain an **EditText** for the name of the item, an **EditText (Multiline)** for the description, and an **Add Image button**, which must send an implicit intent to acquire the gallery image.

- **SecondActivity**: when clicking on finish, she must send the **Parcelable** object back to the **Main Activity** and then add it to the list;

- When clicking on the image of the item, you should open another activity, **DetailsActivity** which shows details of the item (name, photo, description);

- **DetailsActivity**: must contain a **Remove** button, which when clicked, removes the current item from the list and returns to **MainActivity**;

- If the cell phone is rotated, the listing **should not** be lost.

#### Part 2

- Create a customized **ActionBar** for all activities;

- In the **DetailsActivity**, the **ActionBar** `title` must correspond to the `item name`. Also, an [Up Action](https://developer.android.com/training/appbar/up-action) should be present in the **Action Bar**;

- In the **SecondActivity**, the **ActionBar** `title` must be `Add Item`. Also, an [Up Action](https://developer.android.com/training/appbar/up-action) should be present in the **Action Bar**;

- In the **DetailsActivity**, the **ActionBar** `title` must be `Item List`. Also, the **ActionBar** must be hided whenever the user scrolls the list;

- In the **DetailsActivity**, the **ActionBar** must have a `filter` action which, when clicked, a [AlertDialog](https://developer.android.com/guide/topics/ui/dialogs) must be shown, with the following options:

  - Option to show/hide items with the same `name`;
  - Option to sort items by insertion order;
  - Option to sort items by alphabetical order;
  - Button `Ok` to execute the selected filters.

## 📝 License

Copyright © 2020-present, [CS Android Contributors](https://github.com/lcbm/cs-android/graphs/contributors). This project is [ISC](LICENSE) licensed.
