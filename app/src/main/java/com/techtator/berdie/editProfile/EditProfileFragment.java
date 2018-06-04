package com.techtator.berdie.editProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;
import com.techtator.berdie.model.UserDataModel;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment implements EditProfileContract.View {

    private EditProfilePresenter presenter;
    private Button doneBTN, cameraBTN, uplodeBTN, chooseBTN;
    private EditText schoolEdit, majorEdit, descriptionEdit;
    private ImageView profile_pic;
    private Uri filePath;
    private final static int RESULT_CAMERA = 1001;
    private final int PICK_IMAGE_REQUEST = 71;
    private String userId;
    private String uuid;
    String profileUrlImage;
    StorageReference ref;
    DatabaseReference mDatabase;
    UserDataModel userDataModel;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;




    public static EditProfileFragment newInstance(String userId) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString("USER_ID", userId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        View view = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Profile");
        userId = getArguments().getString("USER_ID");
        presenter  = new EditProfilePresenter(this, userId, this);

        profile_pic = (ImageView) view.findViewById(R.id.edit_profile_pg_pic);
        schoolEdit = (EditText) view.findViewById(R.id.edit_school);
        majorEdit = (EditText) view.findViewById(R.id.edit_major);
        descriptionEdit = (EditText) view.findViewById(R.id.edit_description);
        doneBTN = (Button) view.findViewById(R.id.edit_profile_done_button);
//        cameraBTN = (Button) view.findViewById(R.id.camera_edit_button);
        uplodeBTN = (Button) view.findViewById(R.id.upload_button);
        chooseBTN = (Button) view.findViewById(R.id.choose_btn);


        doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String school = schoolEdit.getText().toString();
                String major = majorEdit.getText().toString();
                String description = descriptionEdit.getText().toString();
                presenter.updateProfile(school,major,description,userId);
                Toast.makeText(getActivity(), "Your profile edited successfully!", Toast.LENGTH_LONG).show();

            }
        });

//        cameraBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, RESULT_CAMERA);
//            }
//        });

        chooseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                Toast.makeText(getActivity(), "Please tap upload button to finish updating", Toast.LENGTH_LONG).show();

//                uploadImage();


            }
        });


        uplodeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        return view;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profile_pic.setImageBitmap(bitmap);
                System.out.println("bitmapppppppp=====================" + bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            uuid = UUID.randomUUID().toString();
            System.out.println("uuid=====================" + uuid);

            ref = storageReference.child("users/"+ userId + "/" + "profile_pic/" + uuid);
//            StorageReference ref = storageReference.child("users").child(userId).child("profile_pic") + "/" + uuid);
            System.out.println("ref=====================" + ref);


            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Profile Picture Uploaded!", Toast.LENGTH_SHORT).show();
                            mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId);
//                            String uploadId = mDatabase.push().getKey();
//
//
//                            System.out.println("%%%%%%%%" + uploadId);

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            profileUrlImage = downloadUrl.toString();

                            presenter.updateUser(profileUrlImage, userId);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");

                        }
                    });
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RESULT_CAMERA && resultCode == RESULT_OK) {
//            Bitmap bitmap;
//            if( data.getExtras() == null){
//                Log.d("debug","cancel ?");
//                return;
//            }
//            else{
//                bitmap = (Bitmap) data.getExtras().get("data");
//
//                int bmpWidth = bitmap.getWidth();
//                int bmpHeight = bitmap.getHeight();
//                Log.d("debug",String.format("w= %d",bmpWidth));
//                Log.d("debug",String.format("h= %d",bmpHeight));
//            }
//
//
//            profile_pic.setImageBitmap(bitmap);
//        }
//    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void notifyUserChanged(FBUser user) {

        Picasso.get()
                .load(user.getProfilePic())
                .placeholder(R.drawable.icons8_gender_neutral_user)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(profile_pic);
    }

    @Override
    public void notifyProfileChanged(FBProfile profile) {
        schoolEdit.setText(profile.getUniversity());
        majorEdit.setText(profile.getMajor());
        descriptionEdit.setText(profile.getBody());

    }

}
