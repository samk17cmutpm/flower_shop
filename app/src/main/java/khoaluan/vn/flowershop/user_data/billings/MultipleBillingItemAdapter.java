package khoaluan.vn.flowershop.user_data.billings;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;

/**
 * Created by samnguyen on 9/11/16.
 */
public class MultipleBillingItemAdapter extends BaseMultiItemQuickAdapter<MultipleBillingItem> implements Base{
    private final Activity activity;
    private final SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(1);
    public MultipleBillingItemAdapter(Activity activity, List<MultipleBillingItem> data) {
        super(data);
        this.activity = activity;

        addItemType(MultipleBillingItem.HEADER, R.layout.multiple_billing_item_header);
        addItemType(MultipleBillingItem.PROODUCT, R.layout.multiple_billing_item_product);
        addItemType(MultipleBillingItem.INFO, R.layout.multiple_billing_item_info);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultipleBillingItem item) {

        switch (holder.getItemViewType()) {
            case MultipleBillingItem.HEADER:
                holder.setText(R.id.tv_id, item.getBilling().getOrderCode())
                        .setText(R.id.tv_status, item.getBilling().getOrderStatusString())
                        .setText(R.id.tv_date_odered, item.getBilling().getDateSetPayment())
                        .setText(R.id.tv_date_dilevery, item.getBilling().getExtraInformationDTO().getDataDelivery() + "")
                        .setText(R.id.tv_temp_cost, item.getBilling().getTotalCost() + " VND")
                        .setText(R.id.tv_cost_ship, item.getBilling().getShippingCost() + " VND")
                        .setText(R.id.tv_voucher, item.getBilling().getVoucherCost() + " VND")
                        .setText(R.id.tv_total, item.getBilling().getTotalCost() + " VND")
                        .setText(R.id.tv_method_payment, item.getBilling().getExtraInformationDTO().getPaymentMethodString());
                TextView textViewList = (TextView) holder.getConvertView().findViewById(R.id.tv_list);
                if (item.getBilling().getExtraInformationDTO().getPaymentMethodId() == 2)
                    textViewList.setVisibility(View.VISIBLE);

                textViewList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case MultipleBillingItem.PROODUCT:
                OrderItemAdapter adapter = new OrderItemAdapter(activity, item.getBilling().getCarts());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                RecyclerView recyclerView = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.removeItemDecoration(spacesItemDecoration);
                recyclerView.addItemDecoration(spacesItemDecoration);
                recyclerView.setAdapter(adapter);
                break;
            case MultipleBillingItem.INFO:
                holder.setText(R.id.tv_name_order, item.getBilling().getBillingAddressDTO().getName())
                        .setText(R.id.tv_phone_order, item.getBilling().getBillingAddressDTO().getPhone())
                        .setText(R.id.tv_city_order, item.getBilling().getBillingAddressDTO().getCityString());

                holder.setText(R.id.tv_name_delivery, item.getBilling().getShippingAddressDTO().getName())
                        .setText(R.id.tv_phone_delivery, item.getBilling().getShippingAddressDTO().getPhone())
                        .setText(R.id.tv_city_delivery, item.getBilling().getShippingAddressDTO().getCityString());
                break;
        }

    }
}
