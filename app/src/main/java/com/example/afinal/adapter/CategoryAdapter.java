package com.example.afinal.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.controller.activity.CategoryListActivity;
import com.example.afinal.controller.activity.ProductDetailActivity;
import com.example.afinal.model.category.Category;
import com.example.afinal.model.product.Product;
import com.example.afinal.repository.Repository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ButtonCategoryHolder> {

    private Context mContext;
    private List mCategoryList;
    private String whoFragment;
    private int itemLayout;

    public CategoryAdapter(Context context, List categoryList, String whoFragment) {
        mContext = context;
        mCategoryList = categoryList;
        this.whoFragment = whoFragment;

        if (whoFragment == "HomeFragment") {
            this.itemLayout = R.layout.category_button_item;
        } else {
            this.itemLayout = R.layout.category_list_item;
        }
    }

    @NonNull
    @Override
    public ButtonCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(itemLayout, parent, false);
            return new ButtonCategoryHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ButtonCategoryHolder holder, int position) {
        holder.bind(mCategoryList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ButtonCategoryHolder extends RecyclerView.ViewHolder implements CategoryListActivity.InterfaceCategory {

        private ImageView mCategoryImageView;
        private TextView mCategoryNameTextView, mProductNameTextView, mPriceTextView, mRegularPriceTextView;
        private TextView mCategoryImageButton;
        private Category mCategory;
        private Product mProduct;
        private int mCurrentItem;

        public void setCategory(Category category) {
            mCategory = category;
        }

        public ButtonCategoryHolder(@NonNull View itemView) {
            super(itemView);

            initUI(itemView);

            if (itemLayout == R.layout.category_button_item) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(CategoryListActivity.newIntent(mContext, mCategory.getId(), mCurrentItem));
                    }
                });
            }

            if (itemLayout == R.layout.category_list_item) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(ProductDetailActivity.newIntent(mContext, mProduct.getId()));
                    }
                });
            }
        }

        private void initUI(@NonNull View itemView) {
            mCategoryImageButton = itemView.findViewById(R.id.category_button);
            mCategoryImageView = itemView.findViewById(R.id.image_category_list);
            mCategoryNameTextView = itemView.findViewById(R.id.category_name_category_list);
            mProductNameTextView = itemView.findViewById(R.id.product_name_category_list);
            mPriceTextView = itemView.findViewById(R.id.price_category_list);
            mRegularPriceTextView = itemView.findViewById(R.id.regular_price_category_list);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bind(Object object, int currentItem){

            if (itemLayout == R.layout.category_button_item) {
                mCurrentItem = Repository.getInstance().getCategoryPosition((Category) object);
                Category category = (Category) object;
                mCategory = category;
                mCategoryImageButton.setText(category.getName());
            }

            if (itemLayout == R.layout.category_list_item) {
                    Product product = (Product) object;
                    mProduct = product;
                    mProductNameTextView.setText(product.getName());
                    mCategoryNameTextView.setText(product.getCategories().get(0).getName());
                    mPriceTextView.setText(product.getPrice());

//                if (!mProduct.getRegularPrice().equals("") && !mProduct.getPrice().equals("") && Integer.valueOf(mProduct.getRegularPrice()) > Integer.valueOf(mProduct.getPrice())){
//                    mRegularPriceTextView.setText(product.getRegularPrice());
//                }
//                mRegularPriceTextView.setText(product.getRegularPrice());
                    //Picasso.get().load(product.getImages().get(0).getSrc()).into(mCategoryImageView);
                    Picasso.with(mContext).load(product.getImages().get(0).getSrc()).into(mCategoryImageView);
            }
        }

        @Override
        public void setCategoryForScroll(int scrollPage) {
            setCategory(Repository.getInstance().getCategoryPosition(scrollPage));
            method();
            Toast.makeText(mContext, "adapter", Toast.LENGTH_LONG).show();
        }

        public void method(){
            CategoryListActivity mCategoryListActivity = new CategoryListActivity();
            mCategoryListActivity.setInterfaceCategory(this);
        }
    }
}
