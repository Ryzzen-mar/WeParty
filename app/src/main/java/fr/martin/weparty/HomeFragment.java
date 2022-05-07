package fr.martin.weparty;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<Upload> mUploads;
    private ArrayList<String> imagelist;
    RecyclerView recyclerView;
    StorageReference root;
    private ValueEventListener mDBListener;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private FirebaseStorage mStorage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acceuil, container, false);

    }

        @Override
        public void onViewCreated (View view, Bundle savedInstanceState) {
            mProgressCircle = view.findViewById(R.id.progress);
            imagelist = new ArrayList<>();
            mUploads = new ArrayList<>();
            mAdapter = new ImageAdapter(mUploads, imagelist, getContext());
            recyclerView = view.findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(mAdapter);

            StorageReference listRef = FirebaseStorage.getInstance().getReference().child("images");
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
            mStorage = FirebaseStorage.getInstance();
            mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mUploads.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Upload upload = postSnapshot.getValue(Upload.class);
                        upload.setKey(postSnapshot.getKey());
                        mUploads.add(upload);
                    }

                    mAdapter.notifyDataSetChanged();

                    mProgressCircle.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });

            listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    for (StorageReference file : listResult.getItems()) {
                        file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imagelist.add(uri.toString());
                                Log.e("Itemvalue", uri.toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                recyclerView.setAdapter(mAdapter);
                                mProgressCircle.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            });


        }
    }


