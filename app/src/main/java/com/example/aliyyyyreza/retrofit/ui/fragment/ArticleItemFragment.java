package com.example.aliyyyyreza.retrofit.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aliyyyyreza.retrofit.R;
import com.example.aliyyyyreza.retrofit.model.rvmodel.RvModel;
import com.squareup.picasso.Picasso;

public class ArticleItemFragment extends Fragment {
    private static final String MODEL = "model";
    private static final String POS = "pos";
    private RvModel rvModel;
    private Toolbar toolbar;
    private int pos;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
                    MainFragment.recyclerAdapter.changeArticle(pos, "Changed!");
        }
    };
    private TextView title;
    private TextView content;
    private ImageView image;

    public static ArticleItemFragment newInstance(RvModel rvModel, int pos) {
        ArticleItemFragment fragment = new ArticleItemFragment();
        Bundle args = new Bundle();
        args.putInt(POS, pos);
        args.putParcelable(MODEL, rvModel);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rvModel = getArguments().getParcelable(MODEL);
            pos = getArguments().getInt(POS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);
        runnable.run();
    }

    private void setUpViews(View view) {
        title = view.findViewById(R.id.textViewTitle);
        content = view.findViewById(R.id.textViewContent);
        image = view.findViewById(R.id.imageView2);
        title.setText(rvModel.getTitle());
        content.setText(rvModel.getContent());
        Picasso.get().load(rvModel.getImageUrl()).into(image);
        toolbar = view.findViewById(R.id.toolbar);
    }
}
