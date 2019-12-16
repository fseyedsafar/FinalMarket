package com.example.afinal.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.afinal.R;
import com.example.afinal.controller.activity.ShoppingCartActivity;
import com.example.afinal.model.category.Category;
import com.example.afinal.model.product.ImagesItem;
import com.example.afinal.model.product.Product;
import com.example.afinal.receiver.BroadcastInternetFragment;
import com.example.afinal.repository.Repository;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailFragment extends BroadcastInternetFragment {

    public static final String ARG_ID_DETAIL_FRAGMENT = "argProductDetailFragment";
    private Product mProduct;
    private SliderLayout mSliderLayout;
    private ImageView mProductImage;
    private TextView mTitle, mDescription, mPrice, mCategory, mStatusSale, mRegularPrice;
    private MaterialButton mShopButton;
    private Repository mRepository;

    public static ProductDetailFragment newInstance(int id) {

        Bundle args = new Bundle();

        args.putInt(ARG_ID_DETAIL_FRAGMENT, id);

        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = Repository.getInstance();
        mProduct = mRepository.getProduct(getArguments().getInt(ARG_ID_DETAIL_FRAGMENT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        initUI(view);

        initListener();

        setUI();

        return view;
    }

    private void initListener() {
        mShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepository.addShoppingProduct(getActivity(), mProduct);
                startActivity(ShoppingCartActivity.newIntent(getActivity()));
            }
        });
    }

    private void initUI(View view) {
        mTitle = view.findViewById(R.id.title_detail_fragment);
        mDescription = view.findViewById(R.id.descreption_detail_fragment);
        mPrice = view.findViewById(R.id.price_detail_fragment);
        mRegularPrice = view.findViewById(R.id.regular_price_detail_fragment);
        mStatusSale = view.findViewById(R.id.status_sale_detail_fragment);
//        mProductImage = view.findViewById(R.id.image_detail_fragment);
        mSliderLayout = view.findViewById(R.id.image_detail_fragment);
        mCategory = view.findViewById(R.id.category_detail_fragment);
        mShopButton = view.findViewById(R.id.sale_detail_fragment);
    }

    private void setUI() {
        mTitle.setText(mProduct.getName());
        mDescription.setText(mProduct.getDescription());
        mPrice.setText(mProduct.getPrice() + " تومان");

        // set regular price
        //if (!mProduct.getRegularPrice().equals("") && !mProduct.getPrice().equals("") && Integer.valueOf(mProduct.getRegularPrice()) > Integer.valueOf(mProduct.getPrice())){
        if (!mProduct.getRegularPrice().equals("") && !mProduct.getPrice().equals("") && Double.valueOf(mProduct.getRegularPrice()) > Double.valueOf(mProduct.getPrice())){
            mRegularPrice.setText(mProduct.getRegularPrice());
        }

        // set category
        mCategory.setText("دسته بندی : " + mProduct.getCategories().get(0).getName());

        // set status sale
        String sale;
        if (mProduct.isOnSale()){
            sale = "موجود" ;
        }else {
            sale = "نامشخص" ;
        }
        mStatusSale.setText("وضعیت فروش : " + sale);

        // set image
//        Picasso.with(getContext()).load(mProduct.getImages().get(0).getSrc()).into(mProductImage);
        setUpSlider();
    }

    private void setUpSlider(){
        Map<String, String> urlMap = new HashMap<>();
        for (ImagesItem imagesItem : mProduct.getImages()) {
            if (imagesItem.getSrc() != null) {
                urlMap.put(imagesItem.getName(), imagesItem.getSrc());
            }
        }

//        initial slider layout
        for (String name : urlMap.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(urlMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

//            add extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setDuration(5000);
    }
}
