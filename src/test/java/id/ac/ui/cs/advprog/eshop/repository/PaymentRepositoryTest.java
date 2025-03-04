package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;

public class PaymentRepositoryTest {
    private Map<String, String> voucherPaymentData;
    private Map<String, String> codPaymentData;
    private List<Product> products;
    private Order order1;
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        this.paymentRepository = new PaymentRepository();
        this.voucherPaymentData = new HashMap<String, String>();
        this.voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");
        this.codPaymentData = new HashMap<String, String>();
        this.codPaymentData.put("address", "Jl. Salad Buah 3, No. 19");
        this.codPaymentData.put("deliveryFee", "15500");
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sampo Cap Paes");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);
        this.order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products,
                1708560000L, "Safira Sudrajat");
    }

    @Test
    void testAddPayment() {
        paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        assertEquals(1, paymentRepository.getAllPayments().size());
    }

    @Test
    void testVoucherSetStatusSuccess() {
        Payment payment = paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        paymentRepository.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), payment.getOrder().getStatus());
    }

    @Test
    void testCODSetStatusSuccess() {
        Payment payment = paymentRepository.addPayment(order1, PaymentMethod.COD.getValue(), codPaymentData);
        paymentRepository.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), payment.getOrder().getStatus());
    }

    @Test
    void testVoucherSetStatusRejected() {
        Payment payment = paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        paymentRepository.setStatus(payment, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), payment.getOrder().getStatus());
    }

    @Test
    void testAddInvalidPaymentMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, "OVO", new HashMap<String, String>());
        });
    }

    @Test
    void testSetInvalidPaymentStatus() {
        Payment payment = paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.setStatus(payment, "PENDING");
        });
    }

    @Test
    void testGetPayment() {
        Payment payment = paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        assertSame(payment, paymentRepository.getPayment(payment.getId()));
    }

    @Test
    void testGetAllPayments() {
        paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        paymentRepository.addPayment(order1, PaymentMethod.COD.getValue(), codPaymentData);
        assertEquals(2, paymentRepository.getAllPayments().size());
    }

    @Test
    void testInvalidVoucherPaymentData() {
        Map<String, String> paymentDataWithoutESHOPPrefix = new HashMap<String, String>();
        Map<String, String> paymentDataWithoutEightNumbers = new HashMap<String, String>();
        Map<String, String> paymentDataWithLessThanSixteenCharacters = new HashMap<String, String>();
        Map<String, String> paymentDataWithGreaterThanSixteenCharacters = new HashMap<String, String>();
        paymentDataWithoutESHOPPrefix.put("voucherCode", "1234ABC5678");
        paymentDataWithoutEightNumbers.put("voucherCode", "ESHOP1234ABC");
        paymentDataWithLessThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC567");
        paymentDataWithGreaterThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC56781234");
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentDataWithoutESHOPPrefix);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentDataWithoutEightNumbers);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(),
                    paymentDataWithLessThanSixteenCharacters);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(),
                    paymentDataWithGreaterThanSixteenCharacters);
        });
    }

    @Test
    void testInvalidCODPaymentData() {
        Map<String, String> paymentDataWithoutAddress = new HashMap<String, String>();
        Map<String, String> paymentDataWithoutDeliveryFee = new HashMap<String, String>();
        Map<String, String> paymentDataWithEmptyAddress = new HashMap<String, String>();
        Map<String, String> paymentDataWithEmptyDeliveryFee = new HashMap<String, String>();
        paymentDataWithoutAddress.put("deliveryFee", "15500");
        paymentDataWithoutDeliveryFee.put("address", "Jl. Salad Buah 3, No. 19");
        paymentDataWithEmptyAddress.put("address", "");
        paymentDataWithEmptyAddress.put("deliveryFee", "15500");
        paymentDataWithEmptyDeliveryFee.put("deliveryFee", "");
        paymentDataWithEmptyDeliveryFee.put("address", "Jl. Salad Buah 3, No. 19");
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, PaymentMethod.COD.getValue(), paymentDataWithoutAddress);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, PaymentMethod.COD.getValue(), paymentDataWithoutDeliveryFee);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, PaymentMethod.COD.getValue(), paymentDataWithEmptyAddress);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order1, PaymentMethod.COD.getValue(), paymentDataWithEmptyDeliveryFee);
        });
    }
}