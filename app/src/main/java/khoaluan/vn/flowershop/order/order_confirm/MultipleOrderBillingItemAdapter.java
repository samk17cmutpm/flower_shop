package khoaluan.vn.flowershop.order.order_confirm;

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
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.user_data.billings.OrderItemAdapter;

/**
 * Created by samnguyen on 9/14/16.
 */
public class MultipleOrderBillingItemAdapter extends BaseMultiItemQuickAdapter<MultipleOrderBillingItem> implements Base{
    private final Activity activity;
    private final SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(1);
    public MultipleOrderBillingItemAdapter(Activity activity, List<MultipleOrderBillingItem> data) {
        super(data);
        this.activity = activity;

        addItemType(MultipleOrderBillingItem.HEADER, R.layout.multiple_invoice_header);
        addItemType(MultipleOrderBillingItem.PROODUCT, R.layout.multiple_billing_item_product);
        addItemType(MultipleOrderBillingItem.INFO, R.layout.multiple_invoice_info);
        addItemType(MultipleOrderBillingItem.ACTION, R.layout.multiple_invoice_action);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultipleOrderBillingItem item) {
        switch (holder.getItemViewType()) {
            case MultipleOrderBillingItem.HEADER:
                holder.setText(R.id.tv_temp_cost, countTotalMoney(item.getBilling().getCarts()) + " VND")
                        .setText(R.id.tv_cost_ship, "0 VND")
                        .setText(R.id.tv_total, countTotalMoney(item.getBilling().getCarts()) + " VND")
                        .setText(R.id.tv_date, item.getBilling().getExtraInformationDTO().getDataDelivery() + "");

                TextView textViewList = (TextView) holder.getConvertView().findViewById(R.id.tv_list);
                if (item.getBilling().getExtraInformationDTO().getPaymentMethodId() == 2)
                    textViewList.setVisibility(View.VISIBLE);

                textViewList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case MultipleOrderBillingItem.PROODUCT:
                OrderItemAdapter adapter = new OrderItemAdapter(activity, item.getBilling().getCarts());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                RecyclerView recyclerView = (RecyclerView) holder.getConvertView().findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.removeItemDecoration(spacesItemDecoration);
                recyclerView.addItemDecoration(spacesItemDecoration);
                recyclerView.setAdapter(adapter);
                break;
            case MultipleOrderBillingItem.INFO:
                holder.setText(R.id.tv_name_order, item.getBilling().getBillingAddressDTO().getName())
                        .setText(R.id.tv_phone_order, item.getBilling().getBillingAddressDTO().getPhone())
                        .setText(R.id.tv_city_order, item.getBilling().getBillingAddressDTO().getCityString());

                holder.setText(R.id.tv_name_delivery, item.getBilling().getShippingAddressDTO().getName())
                        .setText(R.id.tv_phone_delivery, item.getBilling().getShippingAddressDTO().getPhone())
                        .setText(R.id.tv_city_delivery, item.getBilling().getShippingAddressDTO().getCityString());


                if (item.getBilling().getInvoiceAddressDTO() != null) {
                    holder.setText(R.id.tv_company_name, item.getBilling().getInvoiceAddressDTO().getCompanyName())
                            .setText(R.id.tv_id_invoice, item.getBilling().getInvoiceAddressDTO().getTaxCode())
                            .setText(R.id.company_address, item.getBilling().getInvoiceAddressDTO().getAddress());
                } else {

                }
                break;
            case MultipleOrderBillingItem.ACTION:
                break;
        }
    }

    public int countTotalMoney(List<Cart> carts) {
        int total = 0;
        for (Cart cart : carts) {
            total = total + (cart.getProductCost() * cart.getProductQuantity());
        }
        return total;
    }
}
