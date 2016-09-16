package khoaluan.vn.flowershop.realm_data_local;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Billing;
import khoaluan.vn.flowershop.data.model_parse_and_realm.BillingAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.Cart;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ExtraInformationDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.InvoiceAddressDTO;
import khoaluan.vn.flowershop.data.model_parse_and_realm.ShippingAddressDTO;

/**
 * Created by samnguyen on 9/11/16.
 */
public class RealmBillingUtils {

    private static final Realm realm = Realm.getDefaultInstance();
    public static final String TAG = RealmBillingUtils.class.getName();

    public static void save(final List<Billing> billing) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(billing);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save New Billings Successfully");
            }
        });

    }

    public static RealmResults<Billing> all() {
        return realm.where(Billing.class).notEqualTo("flag", RealmFlag.BILLING_CONFIRM).findAll();
    }

    public static void updateAll(final List<Billing> newBillings) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Billing> billings = realm.where(Billing.class).notEqualTo("flag", RealmFlag.BILLING_CONFIRM).findAll();
                billings.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                save(newBillings);
            }
        });
    }

    public static void createConfirmBilling() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Billing> billings = realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findAll();
                billings.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Remove a new Billing Confirm perfectly");
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Billing billing = realm.createObject(Billing.class);
                        billing.setFlag(RealmFlag.BILLING_CONFIRM);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "Create a new Billing Confirm perfectly");
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Billing billing = realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findFirst();
                                RealmResults<Cart> carts = realm.where(Cart.class).findAll();

                                for (Cart cart : carts) {
                                    Cart cartTemp = realm.createObject(Cart.class);

                                    cartTemp.setId(cart.getId());
                                    cartTemp.setCartId(cart.getCartId());
                                    cartTemp.setOrderId(cart.getOrderId());
                                    cartTemp.setProductId(cart.getProductId());
                                    cartTemp.setProductName(cart.getProductName());
                                    cartTemp.setProductImage(cart.getProductImage());
                                    cartTemp.setProductQuantity(cart.getProductQuantity());
                                    cartTemp.setProductCost(cart.getProductCost());

                                    billing.getCarts().add(cartTemp);
                                }
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                Log.e(TAG, "Create Carts Successfully ");
                            }
                        });
                    }
                });
            }
        });
    }

    public static void updateBillingAddressDTO(final BillingAddressDTO billingAddressUpdate) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                BillingAddressDTO billingAddressDTO = realm.createObject(BillingAddressDTO.class);
                billingAddressDTO.setId(billingAddressUpdate.getId());
                billingAddressDTO.setCartId(billingAddressUpdate.getCartId());
                billingAddressDTO.setUserId(billingAddressUpdate.getUserId());
                billingAddressDTO.setName(billingAddressUpdate.getName());
                billingAddressDTO.setPhone(billingAddressUpdate.getPhone());
                billingAddressDTO.setCityId(billingAddressUpdate.getCityId());
                billingAddressDTO.setCityString(billingAddressUpdate.getCityString());
                billingAddressDTO.setDistrictId(billingAddressUpdate.getDistrictId());
                billingAddressDTO.setDistrictString(billingAddressUpdate.getDistrictString());
                billingAddressDTO.setAddress(billingAddressUpdate.getAddress());
                billingAddressDTO.setLongitude(billingAddressUpdate.getLongitude());
                billingAddressDTO.setLatitude(billingAddressUpdate.getLatitude());

                Billing billing = realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findFirst();
                billing.setBillingAddressDTO(billingAddressDTO);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Save BillingAddressDTO Successfully");
            }
        });
    }

    public static void updateShippingAddressDTO(final ShippingAddressDTO update) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ShippingAddressDTO shippingAddressDTO = realm.createObject(ShippingAddressDTO.class);

                shippingAddressDTO.setId(update.getId());
                shippingAddressDTO.setCartId(update.getCartId());
                shippingAddressDTO.setUserId(update.getUserId());
                shippingAddressDTO.setName(update.getName());
                shippingAddressDTO.setPhone(update.getPhone());
                shippingAddressDTO.setCityId(update.getCityId());
                shippingAddressDTO.setCityString(update.getCityString());
                shippingAddressDTO.setDistrictId(update.getDistrictId());
                shippingAddressDTO.setDistrictString(update.getDistrictString());
                shippingAddressDTO.setAddress(update.getAddress());
                shippingAddressDTO.setLongitude(update.getLongitude());
                shippingAddressDTO.setLatitude(update.getLatitude());


                Billing billing = realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findFirst();
                billing.setShippingAddressDTO(shippingAddressDTO);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Save ShippingAddressDTO Successfully");
            }
        });
    }

    public static void updateExtraInformationDTO(final ExtraInformationDTO extraInformationDTOUpdate) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                ExtraInformationDTO extraInformationDTO = realm.createObject(ExtraInformationDTO.class);

                extraInformationDTO.setId(extraInformationDTOUpdate.getId());
                extraInformationDTO.setCartId(extraInformationDTOUpdate.getCartId());
                extraInformationDTO.setUserId(extraInformationDTOUpdate.getUserId());
                extraInformationDTO.setPaymentMethodId(extraInformationDTOUpdate.getPaymentMethodId());
                extraInformationDTO.setPaymentMethodString(extraInformationDTOUpdate.getPaymentMethodString());
                extraInformationDTO.setHideSender(extraInformationDTOUpdate.getHideSender());
                extraInformationDTO.setNote(extraInformationDTOUpdate.getNote());
                extraInformationDTO.setMessage(extraInformationDTOUpdate.getMessage());
                extraInformationDTO.setDeliveryDate(extraInformationDTOUpdate.getDeliveryDate());

                Billing billing = realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findFirst();
                billing.setExtraInformationDTO(extraInformationDTO);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Save ExtraInformationDTO Successfully");
            }
        });
    }

    public static void updateInvoiceAddressDTO(final InvoiceAddressDTO invoiceAddressDTOUpdate) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                InvoiceAddressDTO invoiceAddressDTO = realm.createObject(InvoiceAddressDTO.class);

                invoiceAddressDTO.setId(invoiceAddressDTOUpdate.getId());
                invoiceAddressDTO.setCartId(invoiceAddressDTOUpdate.getCartId());
                invoiceAddressDTO.setUserId(invoiceAddressDTOUpdate.getUserId());
                invoiceAddressDTO.setCompanyName(invoiceAddressDTOUpdate.getCompanyName());
                invoiceAddressDTO.setTaxCode(invoiceAddressDTOUpdate.getTaxCode());
                invoiceAddressDTO.setAddress(invoiceAddressDTOUpdate.getAddress());
                invoiceAddressDTO.setLongitude(invoiceAddressDTOUpdate.getLongitude());
                invoiceAddressDTO.setLatitude(invoiceAddressDTOUpdate.getLatitude());

                Billing billing = realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findFirst();
                billing.setInvoiceAddressDTO(invoiceAddressDTO);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Save InvoiceAddressDTO Successfully");
            }
        });
    }

    public static void updateCarts(final List<Cart> carts) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Billing billing = realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findFirst();
                for (Cart cart : carts) {
                    Cart cartTemp = realm.createObject(Cart.class);

                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Save InvoiceAddressDTO Successfully");
            }
        });
    }

    public static Billing getBillCofirm() {
        return realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findFirst();
    }

    public static void clearBillingConfirm() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Billing billing = realm.where(Billing.class).equalTo("flag", RealmFlag.BILLING_CONFIRM).findFirst();

                BillingAddressDTO billingAddressDTO = billing.getBillingAddressDTO();
                if (billingAddressDTO != null)
                    billingAddressDTO.deleteFromRealm();

                ShippingAddressDTO shippingAddressDTO = billing.getShippingAddressDTO();
                if (shippingAddressDTO != null)
                    shippingAddressDTO.deleteFromRealm();

                InvoiceAddressDTO invoiceAddressDTO = billing.getInvoiceAddressDTO();
                if (invoiceAddressDTO != null)
                    invoiceAddressDTO.deleteFromRealm();

                ExtraInformationDTO extraInformationDTO = billing.getExtraInformationDTO();
                if (extraInformationDTO != null)
                    extraInformationDTO.deleteFromRealm();

                RealmList<Cart> carts = billing.getCarts();
                carts.deleteAllFromRealm();
                billing.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Remove Billing Confirm Successfully");
            }
        });
    }

    public static void clear() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Billing> billings = realm.where(Billing.class).findAll();
                billings.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Remove All Billing Confirm Successfully");
            }
        });
    }


}
