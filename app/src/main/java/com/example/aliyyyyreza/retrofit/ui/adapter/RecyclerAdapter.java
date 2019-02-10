package com.example.aliyyyyreza.retrofit.ui.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aliyyyyreza.retrofit.R;
import com.example.aliyyyyreza.retrofit.model.rvmodel.RvModel;
import com.example.aliyyyyreza.retrofit.ui.fragment.ArticleItemFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ADS = 0;
    public static final int NUMBER = 1;
    public static List<RvModel> articles = new ArrayList<>();
    Activity context;

    public RecyclerAdapter(Activity context) {
        this.context = context;
    }


    public static void navigateToContent(Activity context, RvModel rvModel,int pos) {
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        fragment = ArticleItemFragment.newInstance(rvModel,pos);
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
        transaction.hide(((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.fragmentContainer));
        transaction.add(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null).commit();
    }


    public void addArticles(ArrayList<RvModel> models) {
        this.articles.addAll(models);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(view);
     /*   if (i == ADS) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ads, viewGroup, false);
            return new AdsViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
            return new ViewHolder(view);
        }
*/

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolder) viewHolder).title1.setText(articles.get(i).getTitle());
        Picasso.get().load(articles.get(i).getImageUrl()).placeholder(context.getResources().getDrawable(R.drawable.white)).into(((ViewHolder) viewHolder).imageView);
        ((ViewHolder) viewHolder).content.setText(articles.get(i).getContent());
       /* if (getItemViewType(i) == ADS) {
            ((AdsViewHolder) viewHolder).bindAds(models.get(i));
        } else if (getItemViewType(i) == NUMBER) {
            ((ViewHolder) viewHolder).title1.setText(models.get(i).getTitle1());
        }*/

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return NUMBER;
      /*  if (models.get(position).isAds()) {
            return ADS;
        } else {
            return NUMBER;
        }*/
    }

    public void changeArticle(int pos, String title) {
        articles.get(pos).setTitle(title);
        notifyItemChanged(pos);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title1;
        private ImageView imageView;
        private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title1 = itemView.findViewById(R.id.tv_title1);
            imageView = itemView.findViewById(R.id.imageView);
            content = itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int x = this.getPosition();
            navigateToContent(context, new RvModel(articles.get(getAdapterPosition()).getTitle(), articles.get(getAdapterPosition()).getImageUrl(), articles.get(getAdapterPosition()).getContent()),x);
            notifyDataSetChanged();
            //Toast.makeText(context, articles.get(getAdapterPosition()).getTitle(),Toast.LENGTH_LONG).show();
        }
   /*     public class AdsViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public AdsViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = (ImageView) itemView;
            }

            public void bindAds(RecyclerModel recyclerModel) {
                imageView.setImageResource(recyclerModel.getImageResurceId());
            }
        }*/
    }
}