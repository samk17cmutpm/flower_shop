package khoaluan.vn.flowershop.order.order_confirm;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import khoaluan.vn.flowershop.Base;
import khoaluan.vn.flowershop.R;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.data.parcelable.ActionDefined;
import khoaluan.vn.flowershop.data.parcelable.ActionForOrder;
import khoaluan.vn.flowershop.lib.SpacesItemDecoration;
import khoaluan.vn.flowershop.order.OrderContract;
import khoaluan.vn.flowershop.user_data.billings.OrderItemAdapter;
import khoaluan.vn.flowershop.utils.ActionUtils;
import khoaluan.vn.flowershop.utils.MoneyUtils;

/**
 * Created by samnguyen on 9/14/16.
 */
public class MultipleOrderBillingItemAdapter extends BaseMultiItemQuickAdapter<MultipleOrderBillingItem> implements Base{
    private final Activity activity;
    private final SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(1);
    private final OrderContract.Presenter presenter;
    public MultipleOrderBillingItemAdapter(Activity activity, List<MultipleOrderBillingItem> data, OrderContract.Presenter presenter) {
        super(data);
        this.activity = activity;
        this.presenter = presenter;
        addItemType(MultipleOrderBillingItem.HEADER, R.layout.multiple_invoice_header);
        addItemType(MultipleOrderBillingItem.PROODUCT, R.layout.multiple_billing_item_product);
        addItemType(MultipleOrderBillingItem.INFO, R.layout.multiple_invoice_info);
        addItemType(MultipleOrderBillingItem.ACTION, R.layout.multiple_invoice_action);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultipleOrderBillingItem item) {
        switch (holder.getItemViewType()) {
            case MultipleOrderBillingItem.HEADER:
                holder.setText(R.id.tv_temp_cost, MoneyUtils.getMoney(countTotalMoney(item.getBilling().getCarts())))
                        .setText(R.id.tv_cost_ship, "0 VND")
                        .setText(R.id.tv_total, MoneyUtils.getMoney(countTotalMoney(item.getBilling().getCarts())));

                if (item.getBilling().getExtraInformationDTO() != null) {
                    holder.setText(R.id.tv_date, item.getBilling().getExtraInformationDTO().getDataDelivery() + "");
                    holder.setText(R.id.tv_method_payment, item.getBilling().getExtraInformationDTO().getPaymentMethodString());
                }
                else {
                    holder.setText(R.id.tv_method_payment, "Hiện tại chưa thể xác định được ...");
                    holder.setText(R.id.tv_date, "Hiện tại chưa thể xác định được ...");
                }


                TextView textViewList = (TextView) holder.getConvertView().findViewById(R.id.tv_list);
                if (item.getBilling().getExtraInformationDTO().getPaymentMethodId() == 2) {
                    textViewList.setVisibility(View.VISIBLE);
                    textViewList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.BANK, false));
                        }
                    });
                }

                TextView tv_change_payment = (TextView) holder.getConvertView().findViewById(R.id.tv_change_payment);
                tv_change_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.EXTRA, true));
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

                    TextView tv_invoice_title = (TextView) holder.getConvertView().findViewById(R.id.tv_invoice_title);
                    RelativeLayout rl_invoice_detail = (RelativeLayout) holder.getConvertView().findViewById(R.id.rl_invoice_detail);
                    rl_invoice_detail.setVisibility(View.GONE);
                    tv_invoice_title.setText("Không yêu cầu xuất hóa đơn thuế");

                }

                TextView tv_change_invoice = (TextView) holder.getConvertView().findViewById(R.id.tv_change_invoice);
                tv_change_invoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.INITIALIZE, true));
                    }
                });

                TextView tv_change = (TextView) holder.getConvertView().findViewById(R.id.tv_change);
                tv_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.INITIALIZE, true));
                    }
                });

                TextView tv_change_rc = (TextView) holder.getConvertView().findViewById(R.id.tv_change_rc);
                tv_change_rc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.INITIALIZE, true));
                    }
                });

                TextView tv_change_info = (TextView) holder.getConvertView().findViewById(R.id.tv_change_info);
                tv_change_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActionUtils.goOrder(activity, new ActionDefined(ActionForOrder.EXTRA, true));
                    }
                });

                if (!item.getBilling().getExtraInformationDTO().getMessage().isEmpty())
                    holder.setText(R.id.tv_mesage, item.getBilling().getExtraInformationDTO().getMessage());
                else
                    holder.setText(R.id.tv_mesage, "Không để lại tin nhắn");

                if (!item.getBilling().getExtraInformationDTO().getNote().isEmpty())
                    holder.setText(R.id.tv_note, item.getBilling().getExtraInformationDTO().getNote());
                else
                    holder.setText(R.id.tv_note, "Không để lại ghi chú");

                break;
            case MultipleOrderBillingItem.ACTION:

                Button buttonAction = (Button) holder.getConvertView().findViewById(R.id.order);
                buttonAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.makeAnOrder();
                    }
                });
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
