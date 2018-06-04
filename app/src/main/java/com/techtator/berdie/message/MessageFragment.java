package com.techtator.berdie.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBMessage;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;

import java.util.List;


public class MessageFragment extends Fragment implements MessageContract.View {

    private MessagePresenter presenter;

    private RecyclerView recyclerView;
    private TextView someoneName;
    private ImageView someoneImage;
    private EditText messageText;
    private Button sendButton;
    private MessageRecyclerViewAdapter adapter;
    private List<FBMessage> messageList;

    private static final String SOMEONE = "someone";
    private FBUser me;
    private FBUser someone;

    public MessageFragment() {
    }

    public static MessageFragment newInstance(FBUser someone) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putSerializable(SOMEONE, someone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            someone = (FBUser)  getArguments().getSerializable(SOMEONE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Message");

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setStackFromEnd(true);

        presenter = new MessagePresenter(this, someone, this);
        me = UserAuthManager.getInstance().getUser();

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_messageList);
        someoneName = (TextView)view.findViewById(R.id.username_messageListT);
        someoneImage = (ImageView)view.findViewById(R.id.userPic_messageList);
        messageText = (EditText) view.findViewById(R.id.editText_messageList);
        sendButton = (Button)view.findViewById(R.id.sendButton_messageList);
        someoneName.setText("From: " + someone.getFirstName());
        Picasso.get().load(someone.getProfilePic())
                .error(R.drawable.icons8_gender_neutral_user)
                .into(someoneImage);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(messageText.getText().toString().equals(""))) {
                    presenter.createMessage(me.getId(), someone.getId(), messageText.getText().toString());
                    messageText.setText("");
                    LinearLayoutManager mm = new LinearLayoutManager(getActivity());
                    mm.setStackFromEnd(true);
                    mm.setSmoothScrollbarEnabled(true);
                    recyclerView.setLayoutManager(mm);
                }
            }
        });

        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Set the adapter
        adapter = new MessageRecyclerViewAdapter(presenter);
        recyclerView.setAdapter(adapter);

//        presenter.makeMessageRead(presenter.getMessageEntities());


//        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (adapter.recyclerViewReadyCallback != null) { //protected...?
//                    adapter.recyclerViewReadyCallback.onLayoutReady();
//                }
//                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
//        adapter.recyclerViewReadyCallback = new MessageRecyclerViewAdapter.RecyclerViewReadyCallback() {
//            @Override
//            public void onLayoutReady() {
//                //here comes your code that will be executed after all items has are laid down
//                presenter.makeMessageRead(presenter.getMessageEntities());
//            }
//        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

}
