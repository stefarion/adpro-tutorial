package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String method, Map<String, String> paymentData, Order order) {
        this.id = UUID.randomUUID().toString();
        this.paymentData = paymentData;
        this.setMethod(method);
        this.setStatus("SUCCESS");
        this.setPaymentData(paymentData);
        if (order == null) {
            throw new IllegalArgumentException();
        } else {
            this.order = order;
        }
    }

    public void setMethod(String method) {
        if (method.equals("VOUCHER") || method.equals("COD")) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setStatus(String status) {
        if (status.equals("SUCCESS") || status.equals("REJECTED")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException();
        } else if (this.method.equals("VOUCHER")) {
            if (paymentData.get("voucherCode").length() != 16) {
                throw new IllegalArgumentException();
            } else if (!paymentData.get("voucherCode").startsWith("ESHOP")) {
                throw new IllegalArgumentException();
            } else {
                int digitCount = 0;
                for (int i = 0; i < paymentData.get("voucherCode").length(); i++) {
                    if (Character.isDigit(paymentData.get("voucherCode").charAt(i))) {
                        digitCount += 1;
                    }
                }
                if (digitCount != 8) {
                    throw new IllegalArgumentException();
                }
                this.paymentData = paymentData;
            }
        } else if (this.method.equals("COD")) {
            if (paymentData.get("address") == null || paymentData.get("deliveryFee") == null) {
                throw new IllegalArgumentException();
            } else if (paymentData.get("address").isEmpty() || paymentData.get("deliveryFee").isEmpty()) {
                throw new IllegalArgumentException();
            } else {
                this.paymentData = paymentData;
            }
        }
    }
}
