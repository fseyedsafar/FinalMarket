package com.example.afinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.controller.activity.ProductDetailActivity;
import com.example.afinal.model.product.Product;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context mContext;
    private List<Product> mProductList ;
    private int mItemLayout;
    private String mWhoFragment;

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public ProductAdapter(Context context, List productList, int itemLayout, String whoFragment) {
        mContext = context;
        mProductList = productList;
        mItemLayout = itemLayout;
        mWhoFragment = whoFragment;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mItemLayout, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bind(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }




    public class ProductHolder extends RecyclerView.ViewHolder{

//        home fragment
        private ImageView mProductImageHomeFragment;
        private TextView mProductNameHomeFragment, mPriceHomeFragment;

//        shopping card fragment
        private TextView mProductNameShoppingCardFragment, mPriceShoppingCardFragment, mTotalPriceShoppingCardFragment, mDelete;
        private ImageView mProductImageShoppingCardFragment;
        private Spinner mSpinner;

        private Product mProduct;
        private View mItemView;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            mItemView = itemView;

            mProductNameHomeFragment = itemView.findViewById(R.id.name_text_view);
            mPriceHomeFragment = itemView.findViewById(R.id.price_text_view);
            mProductImageHomeFragment = itemView.findViewById(R.id.product_imageView);
//            mSpinner = itemView.findViewById(R.id.spinner_text_view_shopping_cart_item);

            mProductNameShoppingCardFragment = itemView.findViewById(R.id.product_name_text_view_shopping_cart_item);
            mPriceShoppingCardFragment = itemView.findViewById(R.id.price_text_view_shopping_cart_item);
            mTotalPriceShoppingCardFragment = itemView.findViewById(R.id.total_price_text_view_shopping_cart_item);
            mDelete = itemView.findViewById(R.id.delete_text_view_shopping_cart_item);
            mProductImageShoppingCardFragment = itemView.findViewById(R.id.image_view_shopping_cart_item);

            initListener(itemView);
        }

        @Subscribe
        private void initListener(@NonNull View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(ProductDetailActivity.newIntent(mContext, mProduct.getId()));
                }
            });
        }

        public void bind(Product product){
            mProduct = product;

            if (mWhoFragment == "HomeFragment") {
                mProductNameHomeFragment.setText(product.getName());
                mPriceHomeFragment.setText(product.getPrice() + " تومان");
                Picasso.with(mContext).load(product.getImages().get(0).getSrc()).into(mProductImageHomeFragment);
            } else if (mWhoFragment == "ShoppingCartFragment"){

                mProductNameHomeFragment.setText(product.getName());
                mPriceHomeFragment.setText(product.getPrice() + " تومان");
                Picasso.with(mContext).load(product.getImages().get(0).getSrc()).into(mProductImageHomeFragment);

//                mProductNameShoppingCardFragment.setText(product.getName());
//
////                String s = mSpinner.getSelectedItem().toString();
////                int count = Integer.parseInt(s);
////                mPriceShoppingCardFragment.setText((Integer.valueOf(product.getPrice()) * count) + " تومان");
////                mTotalPriceShoppingCardFragment.setText((Integer.valueOf(product.getPrice()) * count) + " تومان");
//
//                Picasso.with(mContext).load(product.getImages().get(0).getSrc()).into(mProductImageShoppingCardFragment);
            }
        }
    }
}
