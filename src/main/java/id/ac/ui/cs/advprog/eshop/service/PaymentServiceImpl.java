package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        if (!isValidMethod(method)) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }

        Payment payment = new Payment(method, paymentData, order);

        if (method.equals(PaymentMethod.VOUCHER.getValue()) && !isValidVoucher(paymentData)) {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
            order.setStatus(OrderStatus.FAILED.getValue());
            return payment;
        }

        payment.setStatus(PaymentStatus.WAITING.getValue());
        order.setStatus(OrderStatus.WAITING_PAYMENT.getValue());
        return paymentRepository.addPayment(order, method, paymentData);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.getPayment(paymentId);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }

        payment.setStatus(status);
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
        return payment;
    }

    public boolean isValidMethod(String method) {
        return method.equals(PaymentMethod.VOUCHER.getValue()) ||
                method.equals(PaymentMethod.BANK.getValue());
    }

    public boolean isValidVoucher(Map<String, String> paymentData) {
        if (!paymentData.containsKey("voucherCode")) {
            return false;
        }
        String voucherCode = paymentData.get("voucherCode");
        return voucherCode.matches("^ESHOP\\d{8}[A-Z]{3}\\d{4}$");
    }

    public boolean isValidStatus(String status) {
        return status.equals(PaymentStatus.WAITING.getValue()) ||
                status.equals(PaymentStatus.SUCCESS.getValue()) ||
                status.equals(PaymentStatus.REJECTED.getValue());
    }
}