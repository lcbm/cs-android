# 📱 Mobile Development with Android Elective Repo

## 👩‍💻 Workspaces

### `/recycler_view_list`

#### Part 1

This folder contains the application for **part 1** of the **first assignment**:

- Create an Android project from scratch, without relying on any branch presented in class;

- **Main activity**: present a list of items with photo, name and a short description of the item (the list must be created using **RecyclerView**);

- **Main activity**: there must be a FloatingButton, with the "+" symbol. By clicking on this button, a **SecondActivity** [for result](https://stackoverflow.com/questions/10407159) must be opened.

- **SecondActivity**: must contain an **EditText** for the name of the item, an **EditText (Multiline)** for the description, and an **Add Image button**, which must send an implicit intent to acquire the gallery image.

- **SecondActivity**: when clicking on finish, she must send the **Parcelable** object back to the **Main Activity** and then add it to the list;

- When clicking on the image of the item, you should open another activity, **DetailsActivity** which shows details of the item (name, photo, description);

- **DetailsActivity**: must contain a **Remove** button, which when clicked, removes the current item from the list and returns to **MainActivity**;

- If the cell phone is rotated, the listing **should not** be lost.

## 📝 License

Copyright © 2020-present, [CS Android Contributors](https://github.com/lcbm/cs-android/graphs/contributors). This project is [ISC](LICENSE) licensed.
