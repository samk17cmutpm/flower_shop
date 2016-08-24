package khoaluan.vn.flowershop.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.category_detail.CategoryDetailActivity;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Category;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExpandCategory;
import khoaluan.vn.flowershop.main.tab_category.CategoriesAdapter;

/**
 * Created by samnguyen on 8/23/16.
 */
public class MainDrawerAdapter extends RecyclerView.Adapter<MainDrawerAdapter.ViewHolder> implements Base{
    private static final int UNSELECTED = -1;
    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;

    private Activity activity;
    private List<ExpandCategory> expandCategories;

    public MainDrawerAdapter(Activity activity, RecyclerView recyclerView, List<ExpandCategory> expandCategories) {
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.expandCategories = expandCategories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_expandable, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.expandCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ExpandableLayout expandableLayout;
        private RelativeLayout rl_expand;
        private TextView textViewTitle;
        private int position;
        private RecyclerView recyclerViewExpand;
        private LinearLayoutManager layoutManager;
        private CategoriesAdapter adapter;


        public ViewHolder(View itemView) {
            super(itemView);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
            rl_expand = (RelativeLayout) itemView.findViewById(R.id.rl_expand);
            textViewTitle = (TextView) itemView.findViewById(R.id.tv_title);
            recyclerViewExpand = (RecyclerView) itemView.findViewById(R.id.recycler_view);
            rl_expand.setOnClickListener(this);
        }

        public void bind(final int position) {
            this.position = position;
            rl_expand.setSelected(false);
            expandableLayout.collapse(false);
            textViewTitle.setText(expandCategories.get(position).getTitle());

            layoutManager = new LinearLayoutManager(activity);
            adapter = new CategoriesAdapter(activity, expandCategories.get(position).getCategories());
            recyclerViewExpand.setHasFixedSize(true);
            recyclerViewExpand.setLayoutManager(layoutManager);
            adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    Intent intent = new Intent(activity, CategoryDetailActivity.class);
                    intent.putExtra(CATEGORY_PARCELABLE, expandCategories.get(position).getCategories().get(i));
                    activity.startActivity(intent);
                }
            });
            recyclerViewExpand.setAdapter(adapter);

        }

        @Override
        public void onClick(View view) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                holder.rl_expand.setSelected(false);
                holder.expandableLayout.collapse();
            }

            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                rl_expand.setSelected(true);
                expandableLayout.expand();
                selectedItem = position;
            }
        }
    }
}
